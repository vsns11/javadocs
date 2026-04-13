# Task Orchestrator

A Spring Boot service that orchestrates multi-batch, multi-action process flows on top of TMF-701. It consumes flow-initiation events from pamconsumer, drives task-runner instances batch-by-batch, tracks completion with a database-backed barrier, and PATCHes TMF-701 as work progresses.

## Table of Contents

* Architecture Overview
* Component Responsibilities
* Topic Design
* Uniform Message Envelope
* Field Reference
* Per-Message-Name Field Matrix
* Database Schema
* Barrier Lifecycle and Lazy Seeding
* Concurrency, Ordering, Idempotency
* End-to-End Example: SYNC Action
* End-to-End Example: ASYNC Action
* Combined E2E Walkthrough
* DAG YAML Format
* Action Registry
* TMF-701 PATCH Contract
* Orchestrator Dispatch Table
* Operational Notes

## Architecture Overview

The system is composed of four cooperating components communicating over Kafka, with TMF-701 as the source of truth for processFlow and taskFlow state.

**Flow:**

1. TTD calls TMF-701 over HTTP to create a processFlow.
2. TMF-701 publishes a `processFlow.created` event to the `notification-management` topic.
3. pamconsumer reads `notification-management` (both TMF events and external async responses) and publishes `processFlow.initiated` and `task.signal` messages to the `task.command` topic.
4. task-orchestrator consumes from `task.command`, resolves the DAG, manages the barrier lifecycle, publishes `task.execute` commands, and PATCHes the parent processFlow on TMF-701.
5. task-runner consumes `task.execute` and `task.signal` messages, runs its action pipeline (POST taskFlow, downstream call, PATCH taskFlow), and publishes `task.event` messages back to `task.command`.

## Component Responsibilities

| Component | Reads | Writes | Owns |
|---|---|---|---|
| TMF-701 | HTTP from TTD | `processFlow.created` to `notification-management` | Source of truth for processFlow and taskFlow state |
| pamconsumer | `notification-management` (TMF events plus external async responses) | `processFlow.initiated` and `task.signal` to `task.command` | Filtering and signal correlation via the `waiting_task` table |
| task-orchestrator | `task.command` (all messages except its own commands) | `task.execute` to `task.command`, PATCH parent processFlow on TMF-701 | DAG resolution, barrier lifecycle, batch promotion |
| task-runner | `task.command` (`task.execute` and `task.signal`) | `task.event` to `task.command`, POST/PATCH taskFlow on TMF-701 | Action execution pipeline |

## Topic Design

There is one topic for the orchestration boundary: `task.command`.

| Topic | Producers | Consumers |
|---|---|---|
| `notification-management` (external) | TMF-701, external async systems | pamconsumer |
| `task.command` (this system) | pamconsumer, task-orchestrator, task-runner | task-orchestrator, task-runner |

### Why one topic

* All orchestration messages share the same envelope.
* The orchestrator is the single state owner and must see everything in order.
* A single partition key (correlationId) gives total ordering per processFlow.
* Producers tag messages with a `source` field so each consumer can ignore its own messages.

### Partition strategy

* Partition key: correlationId, which equals processFlow.id.
* Effect: all messages for the same processFlow land on the same partition and are consumed sequentially, eliminating intra-flow races.
* Recommended starting partition count: 12.

## Uniform Message Envelope

Every message on `task.command`, regardless of producer or purpose, conforms to this single JSON shape. Fields not relevant to a given messageName are omitted.

```json
{
  "eventId":       "01HRZX...",
  "correlationId": "bbf5e84d-cf41-44ba-b0d4-45e4e8b0a152",
  "schemaVersion": "1.0",
  "eventTime":     "2026-04-12T10:00:00.123Z",

  "messageType":   "EVENT | COMMAND | SIGNAL",
  "messageName":   "processFlow.initiated | task.execute | task.event | task.signal",
  "source":        "pamconsumer | task-orchestrator | task-runner",

  "dagKey":        "Auto_Remediation",
  "intent":        "EXECUTE | CANCEL | RETRY",
  "status":        "INITIAL | IN_PROGRESS | WAITING | COMPLETED | FAILED | CANCELLED",

  "batch":   { "index": 0, "total": 2 },
  "action":  { "ref": "a1", "actionCode": "...", "actionName": "...", "dcxActionCode": "..." },
  "task":    { "id": "tf-9a1b", "href": "..." },

  "execution": {
    "mode":        "SYNC | ASYNC",
    "attempt":     1,
    "maxAttempts": 3,
    "timeoutMs":   30000,
    "startedAt":   "2026-04-12T10:00:03.500Z",
    "finishedAt":  "2026-04-12T10:05:22.100Z",
    "durationMs":  318600
  },

  "downstream":     { "id": "...", "href": "..." },
  "awaitingSignal": { "businessTxnId": "..." },
  "trigger":        { "externalEventId": "...", "externalType": "...", "reportingSystem": "..." },

  "inputs":  { "processFlow": { }, "downstream": { } },
  "result":  { },
  "error":   { "code": "...", "message": "...", "retryable": false, "details": { } }
}
```

### Kafka headers

The headers mirror selected envelope fields to make routing and filtering cheap.

```
key:            <correlationId>
eventId:        <eventId>
messageType:    EVENT | COMMAND | SIGNAL
messageName:    <messageName>
source:         <producer name>
schemaVersion:  1.0
```

## Field Reference

### Envelope fields (always present)

| Field | Type | Notes |
|---|---|---|
| eventId | ULID string | Unique per message. Used for idempotency. |
| correlationId | UUID string | Equal to processFlow.id. Drives partition assignment. |
| schemaVersion | string | Currently "1.0". |
| eventTime | RFC 3339 timestamp | UTC. |
| messageType | enum | EVENT, COMMAND, or SIGNAL. |
| messageName | string | Logical message name. Primary dispatch key. |
| source | string | Producing component name. |

### Domain fields (conditional)

| Field | Type | When populated |
|---|---|---|
| dagKey | string | On `processFlow.initiated`. The orchestrator looks up the DAG by this key. |
| intent | enum | On `task.execute`. |
| status | enum | On `task.event` only. |
| batch.index | int | On `task.execute`, `task.event`, `task.signal`. |
| batch.total | int | On `task.execute` only. |
| action.ref | string | DAG-local action key, for example "a1". On `task.execute`, `task.event`, `task.signal`. |
| action.actionCode | string | TMF action code, for example "VOICE_SERVICE_DIAGNOSTIC". |
| action.actionName | string | Human-readable action name. |
| action.dcxActionCode | string | DCX-side mapping code. |
| task.id | string | TMF taskFlow id. Only present after task-runner creates the taskFlow, that is on `task.event` and `task.signal`. Never on `task.execute`. |
| task.href | URL | TMF taskFlow href, same conditions as task.id. |
| execution.mode | enum | SYNC or ASYNC. |
| execution.attempt | int | 1-indexed retry counter. |
| execution.maxAttempts | int | From action config. |
| execution.timeoutMs | long | Per-attempt timeout. |
| execution.startedAt, finishedAt, durationMs | timestamps and long | Populated as the runner executes. |
| downstream.id | string | Identifier returned by the downstream system, for example "VOICE_transactionId_...". |
| downstream.href | URL | URL the runner uses to fetch the downstream result. |
| awaitingSignal.businessTxnId | string | Key pamconsumer uses to correlate the eventual external signal back to this task. Same value as downstream.id for async actions. |
| trigger | object | On `task.signal`. Identifies which external event triggered the signal. |
| inputs.processFlow | object | Full TMF-701 processFlow object passed forward to the runner. |
| inputs.downstream | object | On `task.signal`. Tells the runner which downstream URL to fetch. |
| result | object | Free-form, runner-defined and action-defined. Present on `task.event` with status COMPLETED. |
| error | object | Present on `task.event` with status FAILED. |

## Per-Message-Name Field Matrix

Required, optional or conditional, must be omitted.

| Field | processFlow.initiated | task.execute | task.event | task.signal |
|---|---|---|---|---|
| envelope (eventId, correlationId, etc.) | required | required | required | required |
| messageType | EVENT | COMMAND | EVENT | SIGNAL |
| source | pamconsumer | task-orchestrator | task-runner | pamconsumer |
| dagKey | required | optional | omitted | omitted |
| intent | omitted | required | omitted | omitted |
| status | omitted | omitted | required | omitted |
| batch | omitted | required (index, total) | required (index) | required (index) |
| action | omitted | required (full triplet) | required (full triplet) | required (full triplet) |
| task | omitted | omitted | required | required |
| execution | omitted | required (mode, attempt, max, timeout) | required (mode, attempt, timestamps) | omitted |
| downstream | omitted | omitted | conditional | omitted |
| awaitingSignal | omitted | omitted | conditional (status WAITING) | omitted |
| trigger | omitted | omitted | omitted | required |
| inputs.processFlow | required | required | omitted | omitted |
| inputs.downstream | omitted | omitted | omitted | required |
| result | omitted | omitted | conditional (status COMPLETED) | omitted |
| error | omitted | omitted | conditional (status FAILED) | omitted |

## Database Schema

### batch_barrier

Holds at most one row per correlationId and batch_index. Never holds future PENDING rows. Lazily seeded one batch at a time.

| Column | Type | Notes |
|---|---|---|
| correlation_id | UUID | Primary key part |
| flow_id | VARCHAR(64) | Primary key part. Equals correlationId. |
| batch_index | SMALLINT | Primary key part |
| dag_key | VARCHAR(64) | |
| task_total | INT | |
| task_completed | INT, default 0 | |
| task_failed | INT, default 0 | |
| status | VARCHAR(16) | OPEN, CLOSED, FAILED |
| opened_at | TIMESTAMPTZ | |
| closed_at | TIMESTAMPTZ, nullable | |
| created_at, updated_at | TIMESTAMPTZ | |
| version | BIGINT | JPA Version field for optimistic locking |

The pending count is computed in code as `task_total - task_completed - task_failed`.

### task_execution

Per-task audit trail.

| Column | Type | Notes |
|---|---|---|
| correlation_id | UUID | Primary key part |
| flow_id | VARCHAR(64) | Primary key part |
| task_id | VARCHAR(64) | Primary key part |
| attempt | SMALLINT | Primary key part |
| action_ref | VARCHAR(32) | |
| batch_index | SMALLINT | |
| status | VARCHAR(16) | Last seen status |
| downstream_id | VARCHAR(128), nullable | |
| downstream_href | TEXT, nullable | |
| started_at, finished_at, duration_ms | timestamps and int | |
| result_json, error_json | JSONB, nullable | |
| created_at, updated_at, version | | |

### processed_event

Idempotency table.

| Column | Type | Notes |
|---|---|---|
| event_id | VARCHAR(40) | Primary key |
| processed_at | TIMESTAMPTZ | Default now() |

### waiting_task

Owned by pamconsumer or runner, not the orchestrator. Lookup table populated when the runner emits a WAITING event. Used by pamconsumer to correlate incoming external async responses to the right task.

| Column | Type | Notes |
|---|---|---|
| business_txn_id | VARCHAR(128) | Primary key |
| correlation_id | UUID | |
| task_id | VARCHAR(64) | |
| task_href | TEXT | |
| action_ref | VARCHAR(32) | |
| registered_at | TIMESTAMPTZ | |
| signaled_at | TIMESTAMPTZ, nullable | |

## Barrier Lifecycle and Lazy Seeding

* On `processFlow.initiated`, the orchestrator creates one barrier row for batch 0 with status OPEN.
* On each `task.event` with status COMPLETED, increment task_completed. If pending equals 0:
  1. Set status CLOSED, closed_at to now().
  2. Look up the next batch in the DAG by batch_index plus 1.
  3. If a next batch exists, insert a new barrier row for it with status OPEN, then publish task.execute for each of its actions.
  4. If no next batch exists, PATCH the parent processFlow with state completed on TMF-701.
* On `task.event` with status FAILED and error.retryable false, set status FAILED, abort the flow, PATCH processFlow with state failed.
* WAITING does not mutate the barrier.

Lifecycle: OPEN to CLOSED on success, or OPEN to FAILED on terminal failure.

## Concurrency, Ordering, Idempotency

### Layered protection

| Threat | Protection |
|---|---|
| Two events for the same flow processed in parallel | Partition key correlationId, so all events for one flow land on one partition and are consumed serially |
| Brief overlap during consumer rebalance | JPA Version optimistic lock plus Spring Retry, three attempts with 50 ms backoff |
| Kafka at-least-once duplicate delivery | processed_event table. Insert event_id first. Primary key violation means duplicate, so skip. |
| Listener crash mid-processing | Manual ack only after the database transaction commits. Idempotent producer with acks=all and enable.idempotence=true. |
| Out-of-order events for the same flow | Single partition guarantees Kafka-level ordering. |

### Required Kafka client config (orchestrator)

```yaml
spring:
  kafka:
    producer:
      acks: all
      properties:
        enable.idempotence: true
    consumer:
      enable-auto-commit: false
      isolation-level: read_committed
    listener:
      ack-mode: MANUAL
      concurrency: 6
```

The listener concurrency must be less than or equal to the partition count.

### Handler skeleton

```java
@Transactional
@Retryable(retryFor = OptimisticLockingFailureException.class,
           maxAttempts = 3, backoff = @Backoff(50))
public void handle(TaskCommandEnvelope env) {
    if (!idempotency.markProcessed(env.eventId)) return;
    switch (env.messageName) {
        case "processFlow.initiated" -> flowService.initiate(env);
        case "task.event"            -> barrierService.applyTaskEvent(env);
        case "task.signal"           -> log.info("signal observed: {}", env.eventId);
        case "task.execute"          -> { /* own message, ignore */ }
    }
}
```

## End-to-End Example: SYNC Action

A SYNC action's runner pipeline:

1. POST /processFlow/{id}/taskFlow, which mints task.id and task.href.
2. Calls the downstream system, blocking.
3. PATCH /processFlow/{id}/taskFlow/{taskId} with the result.
4. Publishes a single task.event with status COMPLETED and the full result payload.

The runner emits one event for the entire SYNC lifecycle. The orchestrator sees COMPLETED and increments the barrier directly.

### Sample messages for a SYNC action a2 (INTERNET_CHECK)

#### task.execute (orchestrator to task.command)

```json
{
  "eventId": "01HRZX6N...",
  "correlationId": "bbf5e84d-cf41-44ba-b0d4-45e4e8b0a152",
  "schemaVersion": "1.0",
  "eventTime": "2026-04-12T10:00:02.000Z",
  "messageType": "COMMAND",
  "messageName": "task.execute",
  "source": "task-orchestrator",
  "intent": "EXECUTE",
  "dagKey": "Auto_Remediation",
  "action": {
    "ref": "a2",
    "actionCode": "INTERNET_CHECK",
    "actionName": "runInternetCheck",
    "dcxActionCode": "DCX-INT-04"
  },
  "batch":     { "index": 0, "total": 2 },
  "execution": { "mode": "SYNC", "attempt": 1, "maxAttempts": 3, "timeoutMs": 15000 },
  "inputs":    { "processFlow": { } }
}
```

#### task.event (task-runner to task.command)

```json
{
  "eventId": "01HRZX7P...",
  "correlationId": "bbf5e84d-cf41-44ba-b0d4-45e4e8b0a152",
  "schemaVersion": "1.0",
  "eventTime": "2026-04-12T10:00:05.000Z",
  "messageType": "EVENT",
  "messageName": "task.event",
  "source": "task-runner",
  "task": {
    "id": "tf-7c3d",
    "href": "https://tmf-process-flow.../processFlow/bbf5e84d-.../taskFlow/tf-7c3d"
  },
  "action": {
    "ref": "a2",
    "actionCode": "INTERNET_CHECK",
    "actionName": "runInternetCheck",
    "dcxActionCode": "DCX-INT-04"
  },
  "batch":  { "index": 0 },
  "status": "COMPLETED",
  "execution": {
    "mode": "SYNC",
    "attempt": 1,
    "startedAt":  "2026-04-12T10:00:03.200Z",
    "finishedAt": "2026-04-12T10:00:05.000Z",
    "durationMs": 1800
  },
  "downstream": {
    "id":   "INT-CHK-9981",
    "href": "https://internet-check.../checks/INT-CHK-9981"
  },
  "result": {
    "diagnosticResult": "OK",
    "metrics": { "latencyMs": 42, "packetLoss": 0.0 }
  },
  "error": null
}
```

### Orchestrator effect

* task_execution row upserted: a2 COMPLETED.
* Barrier batch 0: task_completed plus 1.
* PATCH parent processFlow to register the new taskFlow ref.
* If batch is now closed, seed the next batch.

## End-to-End Example: ASYNC Action

An ASYNC action requires three messages across two runner invocations, separated by an external signal:

1. First invocation, triggered by task.execute:
   * POST /processFlow/{id}/taskFlow, which mints task.id and task.href.
   * Dispatches the downstream call, non-blocking.
   * Publishes task.event with status WAITING, including downstream.id and downstream.href.
   * Writes a waiting_task row keyed by downstream.id so pamconsumer can correlate later.
   * Stops.
2. External system completes the work and publishes a final response on notification-management.
3. pamconsumer receives that external event, looks up waiting_task by the business transaction id, and publishes a task.signal on task.command.
4. Second invocation, triggered by task.signal:
   * GET inputs.downstream.href to fetch the full result payload.
   * PATCH /processFlow/{id}/taskFlow/{taskId} with that result.
   * Publishes task.event with status COMPLETED and the full result.

### Sample messages for an ASYNC action a1 (VOICE_SERVICE_DIAGNOSTIC)

#### task.execute (orchestrator to task.command)

```json
{
  "eventId": "01HRZX6M...",
  "correlationId": "bbf5e84d-cf41-44ba-b0d4-45e4e8b0a152",
  "schemaVersion": "1.0",
  "eventTime": "2026-04-12T10:00:02.000Z",
  "messageType": "COMMAND",
  "messageName": "task.execute",
  "source": "task-orchestrator",
  "intent": "EXECUTE",
  "dagKey": "Auto_Remediation",
  "action": {
    "ref": "a1",
    "actionCode": "VOICE_SERVICE_DIAGNOSTIC",
    "actionName": "runVoiceDiagnostic",
    "dcxActionCode": "DCX-VSD-01"
  },
  "batch":     { "index": 0, "total": 2 },
  "execution": { "mode": "ASYNC", "attempt": 1, "maxAttempts": 3, "timeoutMs": 30000 },
  "inputs":    { "processFlow": { } }
}
```

#### task.event WAITING (task-runner to task.command)

```json
{
  "eventId": "01HRZX7Q...",
  "correlationId": "bbf5e84d-cf41-44ba-b0d4-45e4e8b0a152",
  "schemaVersion": "1.0",
  "eventTime": "2026-04-12T10:00:03.500Z",
  "messageType": "EVENT",
  "messageName": "task.event",
  "source": "task-runner",
  "task": {
    "id":   "tf-9a1b",
    "href": "https://tmf-process-flow.../processFlow/bbf5e84d-.../taskFlow/tf-9a1b"
  },
  "action": {
    "ref": "a1",
    "actionCode": "VOICE_SERVICE_DIAGNOSTIC",
    "actionName": "runVoiceDiagnostic",
    "dcxActionCode": "DCX-VSD-01"
  },
  "batch":  { "index": 0 },
  "status": "WAITING",
  "execution": {
    "mode": "ASYNC",
    "attempt": 1,
    "startedAt": "2026-04-12T10:00:03.500Z"
  },
  "downstream": {
    "id":   "VOICE_transactionId_A267E5877F47467993818722C",
    "href": "https://sharp-oneside-task.../findVoiceServiceDiagnosticFromCacheByTransactionId/VOICE_transactionId_A267..."
  },
  "awaitingSignal": {
    "businessTxnId": "VOICE_transactionId_A267E5877F47467993818722C"
  },
  "result": null,
  "error":  null
}
```

#### task.signal (pamconsumer to task.command)

```json
{
  "eventId": "01HRZX9S...",
  "correlationId": "bbf5e84d-cf41-44ba-b0d4-45e4e8b0a152",
  "schemaVersion": "1.0",
  "eventTime": "2026-04-12T10:05:21.500Z",
  "messageType": "SIGNAL",
  "messageName": "task.signal",
  "source": "pamconsumer",
  "task": {
    "id":   "tf-9a1b",
    "href": "https://tmf-process-flow.../processFlow/bbf5e84d-.../taskFlow/tf-9a1b"
  },
  "action": {
    "ref": "a1",
    "actionCode": "VOICE_SERVICE_DIAGNOSTIC",
    "actionName": "runVoiceDiagnostic",
    "dcxActionCode": "DCX-VSD-01"
  },
  "batch": { "index": 0 },
  "inputs": {
    "downstream": {
      "id":   "VOICE_transactionId_A267E5877F47467993818722C",
      "href": "https://sharp-oneside-task.../findVoiceServiceDiagnosticFromCacheByTransactionId/VOICE_transactionId_A267..."
    },
    "trigger": {
      "externalEventId": "985367b9-12f3-4cdd-b919-db9696ccfafa",
      "externalType":    "TaskFinalAsyncResponseSend",
      "reportingSystem": "ACUT"
    }
  }
}
```

#### task.event COMPLETED (task-runner to task.command)

```json
{
  "eventId": "01HRZXAT...",
  "correlationId": "bbf5e84d-cf41-44ba-b0d4-45e4e8b0a152",
  "schemaVersion": "1.0",
  "eventTime": "2026-04-12T10:05:22.100Z",
  "messageType": "EVENT",
  "messageName": "task.event",
  "source": "task-runner",
  "task": {
    "id":   "tf-9a1b",
    "href": "https://tmf-process-flow.../processFlow/bbf5e84d-.../taskFlow/tf-9a1b"
  },
  "action": {
    "ref": "a1",
    "actionCode": "VOICE_SERVICE_DIAGNOSTIC",
    "actionName": "runVoiceDiagnostic",
    "dcxActionCode": "DCX-VSD-01"
  },
  "batch":  { "index": 0 },
  "status": "COMPLETED",
  "execution": {
    "mode": "ASYNC",
    "attempt": 1,
    "startedAt":  "2026-04-12T10:00:03.500Z",
    "finishedAt": "2026-04-12T10:05:22.100Z",
    "durationMs": 318600
  },
  "downstream": {
    "id":   "VOICE_transactionId_A267E5877F47467993818722C",
    "href": "https://sharp-oneside-task.../findVoiceServiceDiagnosticFromCacheByTransactionId/VOICE_transactionId_A267..."
  },
  "result": {
    "voiceDiagnostic": {
      "status":      "PASS",
      "lineQuality": "GOOD",
      "noiseLevel":  -55,
      "testedAt":    "2026-04-12T10:05:18.000Z"
    }
  },
  "error": null
}
```

### Orchestrator effect across the ASYNC lifecycle

| Message received | Barrier change | Other effect |
|---|---|---|
| task.event WAITING | none | Upsert task_execution. PATCH parent processFlow with taskFlow ref. |
| task.signal | none | Log only. |
| task.event COMPLETED | task_completed plus 1. Close and seed-next if pending equals 0. | Upsert task_execution. The taskFlow ref was already added on WAITING. |

## Combined E2E Walkthrough

Scenario: Auto_Remediation flow with two batches.

* Batch 0 contains a1 (ASYNC voice diagnostic) and a2 (SYNC internet check).
* Batch 1 contains a3 (SYNC notify user).

processFlow.id is bbf5e84d-cf41-44ba-b0d4-45e4e8b0a152.

| Step | Producer to Topic | messageName | Effect |
|---|---|---|---|
| 1 | TTD to TMF-701 (HTTP) | n/a | processFlow created, id bbf5e84d-... |
| 2 | TMF-701 to notification-management | processFlow.created | pamconsumer ingests |
| 3 | pamconsumer (filter) | n/a | Spec Auto_Remediation passes |
| 4 | pamconsumer to task.command | processFlow.initiated | Carries dagKey and inputs.processFlow |
| 5 | orchestrator | n/a | Seeds barrier batch 0 (OPEN, total 2) |
| 6a | orchestrator to task.command | task.execute (a1, ASYNC) | Runner picks up |
| 6b | orchestrator to task.command | task.execute (a2, SYNC) | Runner picks up |
| 7 | runner | n/a | Runs both pipelines in parallel |
| 8a | runner to task.command | task.event COMPLETED (a2) | Barrier completed 1, pending 1 |
| 8b | runner to task.command | task.event WAITING (a1) | Barrier unchanged. Ref added to processFlow. |
| 9 | orchestrator to TMF-701 (PATCH) | n/a | Parent processFlow gains taskFlow refs |
| 10 | external system to notification-management | TaskFinalAsyncResponseSend | pamconsumer correlates by businessTxnId |
| 11 | pamconsumer to task.command | task.signal | Runner picks up |
| 12 | runner to task.command | task.event COMPLETED (a1) | Barrier completed 2, pending 0, batch 0 CLOSED |
| 13 | orchestrator | n/a | Lazy-seed batch 1 (OPEN, total 1). Publish a3. |
| 14 | orchestrator to task.command | task.execute (a3, SYNC) | Runner picks up |
| 15 | runner to task.command | task.event COMPLETED (a3) | Barrier batch 1 completed 1, pending 0, CLOSED |
| 16 | orchestrator to TMF-701 (PATCH) | n/a | processFlow state completed |

### Barrier table progression

After step 5:

| batch_index | total | completed | failed | pending | status |
|---|---|---|---|---|---|
| 0 | 2 | 0 | 0 | 2 | OPEN |

After step 8a:

| batch_index | total | completed | failed | pending | status |
|---|---|---|---|---|---|
| 0 | 2 | 1 | 0 | 1 | OPEN |

After step 12, batch 1 lazy-seeded:

| batch_index | total | completed | failed | pending | status |
|---|---|---|---|---|---|
| 0 | 2 | 2 | 0 | 0 | CLOSED |
| 1 | 1 | 0 | 0 | 1 | OPEN |

After step 15:

| batch_index | total | completed | failed | pending | status |
|---|---|---|---|---|---|
| 0 | 2 | 2 | 0 | 0 | CLOSED |
| 1 | 1 | 1 | 0 | 0 | CLOSED |

## DAG YAML Format

DAGs live in src/main/resources/dag/{dagKey}.yml. The orchestrator loads all of them at startup into the DagRegistry.

```yaml
dagKey: Auto_Remediation
match:
  processFlowSpecification: Auto_Remediation
batches:
  - index: 0
    actions:
      - ref: a1
        actionCode: VOICE_SERVICE_DIAGNOSTIC
      - ref: a2
        actionCode: INTERNET_CHECK
  - index: 1
    actions:
      - ref: a3
        actionCode: NOTIFY_USER
```

The DAG only references actions by actionCode. The full action triplet (actionName, dcxActionCode, executionMode, timeoutMs, maxAttempts) is hydrated at command-build time from the Action Registry.

### The ref field

ref is a DAG-local key (a1, a2, etc.) used to correlate a task.execute command with the task.event it produces. It is required because task.id does not exist at command time, since the runner mints it.

## Action Registry

At startup the orchestrator loads action metadata from the TMF action-code API and caches it in memory.

```json
{
  "actionCode":    "VOICE_SERVICE_DIAGNOSTIC",
  "actionName":    "runVoiceDiagnostic",
  "dcxActionCode": "DCX-VSD-01",
  "executionMode": "ASYNC",
  "timeoutMs":     30000,
  "maxAttempts":   3
}
```

When the orchestrator builds a task.execute message, it joins:

* The DAG entry, which gives ref and actionCode.
* The Action Registry entry, which gives actionName, dcxActionCode, executionMode, timeoutMs, and maxAttempts.

This way the DAG stays small and action metadata has a single source of truth.

## TMF-701 PATCH Contract

### Per task: register taskFlow ref on parent processFlow

When a new taskFlow is observed, on WAITING or first-seen COMPLETED, the orchestrator PATCHes the parent processFlow's relatedEntity array.

```
PATCH /tmf-api/processFlowManagement/v4/processFlow/{processFlowId}
Content-Type: application/json
```

```json
{
  "relatedEntity": [
    {
      "id":            "tf-7c3d",
      "href":          "https://tmf-process-flow.../taskFlow/tf-7c3d",
      "role":          "TaskFlow",
      "@type":         "RelatedEntity",
      "@referredType": "TaskFlow",
      "name":          "INTERNET_CHECK"
    }
  ]
}
```

### When all batches close: mark processFlow completed

```
PATCH /tmf-api/processFlowManagement/v4/processFlow/{processFlowId}
```

```json
{ "state": "completed" }
```

### On terminal failure

```json
{ "state": "failed" }
```

The per-taskFlow PATCH, writing the actual result back to the taskFlow itself, is the runner's responsibility, not the orchestrator's.

## Orchestrator Dispatch Table

| messageName | source | Action |
|---|---|---|
| processFlow.initiated | pamconsumer | Resolve DAG by dagKey. Seed batch 0 only with status OPEN. Publish task.execute per action in batch 0. |
| task.execute | task-orchestrator | Ignore. Own message. |
| task.event (status INITIAL or IN_PROGRESS) | task-runner | Upsert task_execution. No barrier change. |
| task.event (status WAITING) | task-runner | Upsert task_execution. PATCH parent processFlow with new taskFlow ref. No barrier change. |
| task.event (status COMPLETED) | task-runner | Upsert task_execution. PATCH parent processFlow if needed. Barrier task_completed plus 1. If pending equals 0, CLOSE batch, lazy-seed next batch if any and publish its task.execute messages, otherwise PATCH processFlow with state completed. |
| task.event (status FAILED, retryable false) | task-runner | Upsert task_execution. Barrier task_failed plus 1, status FAILED. PATCH processFlow with state failed. |
| task.signal | pamconsumer | Log only. Observability. |

## Operational Notes

### Topic provisioning

| Concurrent processFlows | Partitions for task.command |
|---|---|
| Less than 100 | 6 |
| 100 to 1k | 12 |
| 1k to 10k | 24 |
| More than 10k | 48 or more |

Partitions can grow but never shrink. Start a notch above today's need.

### Consumer concurrency

Set spring.kafka.listener.concurrency less than or equal to the partition count. Each thread owns one or more partitions, and partition affinity ensures all events for a single processFlow stay serialized on one thread.

### Producer guarantees

* acks all
* enable.idempotence true
* Always set the Kafka record key to correlationId so the partition assignment matches the consumer's expectation of per-flow ordering.

### Schema evolution

* schemaVersion is on every message.
* Add new optional fields freely. Never remove or rename existing fields without bumping the major version.
* Consumers should ignore unknown fields, for example with @JsonIgnoreProperties(ignoreUnknown = true).

### Observability

Recommended structured-log fields on every handled message:

* correlationId
* eventId
* messageName
* source
* batch.index
* action.ref
* status

This makes a single processFlow's lifecycle trivially greppable end-to-end.

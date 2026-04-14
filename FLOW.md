# Task Orchestrator — End-to-End Message Flow

This document walks through every message produced and consumed during
the execution of the `Auto_Remediation` DAG. Each step shows the full
JSON payload, which component publishes it, which component reads it,
and what side effects happen (database writes, HTTP calls).

---

## Components

| Component | What it does |
|-----------|-------------|
| **DemoFlowTrigger** | HTTP endpoint that simulates TTD creating a processFlow on TMF-701. Publishes a real TMF-701 processFlow payload to `notification.management` topic. |
| **MockPamConsumer** | Kafka consumer on `notification.management`. Reads real TMF-701 events, extracts `processFlowSpecification` as the DAG key, transforms into `processFlow.initiated` TaskCommand and publishes to `task.command`. Also injects async signals when called by MockTaskRunner. |
| **Orchestrator** | Kafka consumer on `task.command` (group: `task-orchestrator`). Manages batch barriers, seeds batches, publishes `task.execute` commands, advances the DAG. |
| **MockTaskRunner** | Kafka consumer on `task.command` (group: `mock-task-runner`). Executes actions: SYNC actions complete immediately, ASYNC actions go through WAITING → signal → COMPLETED. |
| **MockTmf701Controller** | HTTP mock at `/mock/tmf701`. Stores processFlow state in memory. The orchestrator PATCHes it to register taskFlow references and update lifecycle state. |
| **MockActionRegistryController** | HTTP mock at `/mock/actionregistry`. Returns action identity triplets (actionCode, dcxActionCode) keyed by actionName at startup. |

## Kafka Topics

| Topic | Payload type | Who writes | Who reads |
|-------|-------------|-----------|----------|
| `notification.management` | `NotificationEvent` (real TMF-701 native JSON) | DemoFlowTrigger (simulating TMF-701) | MockPamConsumer |
| `task.command` | `TaskCommand` (orchestrator's uniform DTO) | MockPamConsumer, Orchestrator, MockTaskRunner | Orchestrator, MockTaskRunner |

## DAG Definition (Auto_Remediation.yml)

```yaml
dagKey: Auto_Remediation
batches:
  - index: 0
    actions:
      - actionName: runVoiceDiagnostic    # ASYNC, 30s timeout, 3 retries
      - actionName: runInternetCheck      # SYNC, 15s timeout, 3 retries
  - index: 1
    actions:
      - actionName: sendNotification      # SYNC, 10s timeout, 2 retries
```

---

## Step 1 — processFlow (TMF-701 native payload)

**Topic:** `notification.management`
**Published by:** DemoFlowTrigger (simulating TMF-701)
**Consumed by:** MockPamConsumer
**Payload type:** `ProcessFlowEvent` (real TMF-701 structure)

This is the entry point. In production, TMF-701 publishes this when
a processFlow is created via HTTP POST from TTD. The payload follows
the TMF-701 processFlowManagement v4 API structure exactly.

```json
{
  "id": "bbf5e84d-cf41-44ba-b0d4-45e4e8b0a152",
  "href": "https://tmf-process-flow/tmf-api/processFlowManagement/v4/processFlow/bbf5e84d-cf41-44ba-b0d4-45e4e8b0a152",
  "@type": "processFlow",
  "state": "active",
  "@baseType": "processFlow",
  "channel": [],
  "relatedParty": [],
  "relatedEntity": [
    {
      "id": "B54CCE7C0E0840FF86689103A",
      "href": "https://sharp-oneside-task/onesideTaskCatalog/findServiceDiagnosticFromCacheByTransactionId/B54CCE7C0E0840FF86689103A",
      "name": "Internet Service Diagnostic",
      "role": "RelatedEntity",
      "@type": "RelatedEntity",
      "@referredType": "InternetServiceDiagnostic"
    }
  ],
  "characteristic": [
    {
      "id": "B54CCE7C0E0840FF86689103A",
      "name": "SDT Transaction ID",
      "value": "",
      "valueType": "string",
      "characteristicRelationship": []
    },
    {
      "id": "N/A",
      "name": "internetSubscription",
      "value": "",
      "valueType": "string",
      "characteristicRelationship": []
    },
    {
      "id": "EZ82449",
      "name": "peinNumber",
      "value": "",
      "valueType": "string",
      "characteristicRelationship": []
    }
  ],
  "processFlowSpecification": "Auto_Remediation"
}
```

**What MockPamConsumer does when it reads this:**
- Checks `@type` → `"processFlow"` → this is a processFlow.created event
- Extracts `id` → `"bbf5e84d-..."` → uses as `correlationId`
- Extracts `processFlowSpecification` → `"Auto_Remediation"` → uses as `dagKey`
- Converts the entire payload into a Map and puts it into `inputs.processFlow`
- Builds a `processFlow.initiated` TaskCommand and publishes to `task.command`

---

## Step 2 — processFlow.initiated

**Topic:** `task.command`
**Published by:** MockPamConsumer (after transforming the TMF-701 event)
**Consumed by:** Orchestrator
**Payload type:** `TaskCommand`

```json
{
  "eventId": "e5f6a7b8-...",
  "correlationId": "bbf5e84d-cf41-44ba-b0d4-45e4e8b0a152",
  "schemaVersion": "1.0",
  "eventTime": "2026-04-14T01:41:26.524Z",
  "messageType": "EVENT",
  "messageName": "processFlow.initiated",
  "source": "pamconsumer",
  "dagKey": "Auto_Remediation",
  "inputs": {
    "processFlow": {
      "id": "bbf5e84d-cf41-44ba-b0d4-45e4e8b0a152",
      "href": "https://tmf-process-flow/tmf-api/processFlowManagement/v4/processFlow/bbf5e84d-...",
      "type": "processFlow",
      "state": "active",
      "processFlowSpecification": "Auto_Remediation",
      "relatedEntity": [
        {
          "id": "B54CCE7C0E0840FF86689103A",
          "name": "Internet Service Diagnostic",
          "role": "RelatedEntity"
        }
      ],
      "characteristic": [
        { "id": "EZ82449", "name": "peinNumber", "value": "" }
      ]
    }
  }
}
```

**What the Orchestrator does when it reads this:**
1. Looks up DAG `Auto_Remediation` → finds 2 batches
2. Creates a `batch_barrier` row in PostgreSQL for batch 0:

   | correlation_id | batch_index | task_total | task_completed | task_failed | status |
   |---|---|---|---|---|---|
   | bbf5e84d-... | 0 | 2 | 0 | 0 | OPEN |

3. Looks up each action's `actionCode` and `dcxActionCode` from the ActionRegistry by `actionName`
4. Publishes 2 `task.execute` commands — one per action in batch 0

---

## Step 3a — task.execute (runVoiceDiagnostic, ASYNC)

**Topic:** `task.command`
**Published by:** Orchestrator
**Consumed by:** MockTaskRunner
**Payload type:** `TaskCommand`

```json
{
  "eventId": "c9d0e1f2-...",
  "correlationId": "bbf5e84d-cf41-44ba-b0d4-45e4e8b0a152",
  "schemaVersion": "1.0",
  "eventTime": "2026-04-14T01:41:26.530Z",
  "messageType": "COMMAND",
  "messageName": "task.execute",
  "source": "task-orchestrator",
  "dagKey": "Auto_Remediation",
  "intent": "EXECUTE",
  "action": {
    "actionName": "runVoiceDiagnostic",
    "actionCode": "VOICE_SERVICE_DIAGNOSTIC",
    "dcxActionCode": "DCX-VSD-01"
  },
  "batch": {
    "index": 0,
    "total": 2
  },
  "execution": {
    "mode": "ASYNC",
    "attempt": 1,
    "maxAttempts": 3,
    "timeoutMs": 30000
  },
  "inputs": {
    "processFlow": {
      "id": "bbf5e84d-...",
      "processFlowSpecification": "Auto_Remediation"
    }
  }
}
```

**Where each field comes from:**
- `action.actionName` → from DAG YAML
- `action.actionCode` and `action.dcxActionCode` → looked up from ActionRegistry by actionName at startup
- `execution.mode`, `execution.timeoutMs`, `execution.maxAttempts` → from DAG YAML
- `inputs.processFlow` → passed through from the processFlow.initiated event

**What MockTaskRunner does:**
- Sees `execution.mode = ASYNC`
- Mints a taskFlow ID: `tf-31c94387`
- Publishes `task.event` with status `WAITING` (step 5)
- Tells MockPamConsumer to inject a signal after 300-900ms delay (step 6)

---

## Step 3b — task.execute (runInternetCheck, SYNC)

**Topic:** `task.command`
**Published by:** Orchestrator
**Consumed by:** MockTaskRunner
**Payload type:** `TaskCommand`

```json
{
  "eventId": "d1e2f3a4-...",
  "correlationId": "bbf5e84d-cf41-44ba-b0d4-45e4e8b0a152",
  "schemaVersion": "1.0",
  "eventTime": "2026-04-14T01:41:26.531Z",
  "messageType": "COMMAND",
  "messageName": "task.execute",
  "source": "task-orchestrator",
  "dagKey": "Auto_Remediation",
  "intent": "EXECUTE",
  "action": {
    "actionName": "runInternetCheck",
    "actionCode": "INTERNET_CHECK",
    "dcxActionCode": "DCX-INT-04"
  },
  "batch": {
    "index": 0,
    "total": 2
  },
  "execution": {
    "mode": "SYNC",
    "attempt": 1,
    "maxAttempts": 3,
    "timeoutMs": 15000
  },
  "inputs": {
    "processFlow": {
      "id": "bbf5e84d-...",
      "processFlowSpecification": "Auto_Remediation"
    }
  }
}
```

**What MockTaskRunner does:**
- Sees `execution.mode = SYNC`
- Mints a taskFlow ID: `tf-ca72d013`
- Calls downstream (simulated), gets result immediately
- Publishes `task.event` with status `COMPLETED` (step 4)

---

## Step 4 — task.event COMPLETED (runInternetCheck)

**Topic:** `task.command`
**Published by:** MockTaskRunner (SYNC action completes immediately)
**Consumed by:** Orchestrator
**Payload type:** `TaskCommand`

```json
{
  "eventId": "f5a6b7c8-...",
  "correlationId": "bbf5e84d-cf41-44ba-b0d4-45e4e8b0a152",
  "schemaVersion": "1.0",
  "eventTime": "2026-04-14T01:41:26.850Z",
  "messageType": "EVENT",
  "messageName": "task.event",
  "source": "task-runner",
  "status": "COMPLETED",
  "task": {
    "id": "tf-ca72d013",
    "href": "http://mock-tmf701/processFlow/bbf5e84d-.../taskFlow/tf-ca72d013"
  },
  "action": {
    "actionName": "runInternetCheck",
    "actionCode": "INTERNET_CHECK",
    "dcxActionCode": "DCX-INT-04"
  },
  "batch": {
    "index": 0
  },
  "execution": {
    "mode": "SYNC",
    "attempt": 1,
    "startedAt": "2026-04-14T01:41:25.850Z",
    "finishedAt": "2026-04-14T01:41:26.850Z",
    "durationMs": 1000
  },
  "result": {
    "syncResult": "OK",
    "actionName": "runInternetCheck",
    "echoedAt": "2026-04-14T01:41:26.850Z"
  }
}
```

**What the Orchestrator does:**
1. Inserts a `task_execution` row:

   | task_id | action_name | action_code | status | result_json |
   |---|---|---|---|---|
   | tf-ca72d013 | runInternetCheck | INTERNET_CHECK | COMPLETED | {"syncResult":"OK",...} |

2. PATCHes mock TMF-701 to register the taskFlow reference:
   ```
   PATCH /mock/tmf701/processFlow/bbf5e84d-...
   {
     "relatedEntity": [{
       "id": "tf-ca72d013",
       "href": "http://mock-tmf701/.../tf-ca72d013",
       "role": "TaskFlow",
       "@type": "RelatedEntity",
       "@referredType": "TaskFlow",
       "name": "runInternetCheck"
     }]
   }
   ```

3. Updates `batch_barrier`: `task_completed = 1`, `pending = 1` (still waiting for runVoiceDiagnostic)

---

## Step 5 — task.event WAITING (runVoiceDiagnostic)

**Topic:** `task.command`
**Published by:** MockTaskRunner (ASYNC action can't complete yet)
**Consumed by:** Orchestrator
**Payload type:** `TaskCommand`

```json
{
  "eventId": "a1b2c3d4-...",
  "correlationId": "bbf5e84d-cf41-44ba-b0d4-45e4e8b0a152",
  "schemaVersion": "1.0",
  "eventTime": "2026-04-14T01:41:26.879Z",
  "messageType": "EVENT",
  "messageName": "task.event",
  "source": "task-runner",
  "status": "WAITING",
  "task": {
    "id": "tf-31c94387",
    "href": "http://mock-tmf701/processFlow/bbf5e84d-.../taskFlow/tf-31c94387"
  },
  "action": {
    "actionName": "runVoiceDiagnostic",
    "actionCode": "VOICE_SERVICE_DIAGNOSTIC",
    "dcxActionCode": "DCX-VSD-01"
  },
  "batch": {
    "index": 0
  },
  "execution": {
    "mode": "ASYNC",
    "attempt": 1,
    "startedAt": "2026-04-14T01:41:26.879Z"
  },
  "downstream": {
    "id": "ASYNC_1e4ec1e0-68a",
    "href": "http://mock-downstream/queries/ASYNC_1e4ec1e0-68a"
  },
  "awaitingSignal": {
    "businessTxnId": "ASYNC_1e4ec1e0-68a"
  }
}
```

**What the Orchestrator does:**
1. Inserts a `task_execution` row with `status = WAITING` and `downstream_id = ASYNC_1e4ec1e0-68a`
2. PATCHes mock TMF-701 to register the taskFlow reference (same pattern as step 4)
3. Does NOT change the barrier — `pending` stays at 1. The barrier only advances on COMPLETED or FAILED.

---

## Step 6 — task.signal (async callback)

**Topic:** `task.command`
**Published by:** MockPamConsumer (called by MockTaskRunner after a 300-900ms delay)
**Consumed by:** MockTaskRunner
**Payload type:** `TaskCommand`

In production, this signal originates from a real `TaskFinalAsyncResponseSend`
event on `notification.management` (see "Async Response Event" section below).
The real pamconsumer would correlate by `event.id` (the businessTxnId) with
the `waiting_task` table to find the matching task and flow. Here, MockTaskRunner
calls `MockPamConsumer.publishSignalAfterDelay()` directly for simplicity.

```json
{
  "eventId": "b3c4d5e6-...",
  "correlationId": "bbf5e84d-cf41-44ba-b0d4-45e4e8b0a152",
  "schemaVersion": "1.0",
  "eventTime": "2026-04-14T01:41:27.552Z",
  "messageType": "SIGNAL",
  "messageName": "task.signal",
  "source": "pamconsumer",
  "task": {
    "id": "tf-31c94387",
    "href": "http://mock-tmf701/processFlow/bbf5e84d-.../taskFlow/tf-31c94387"
  },
  "action": {
    "actionName": "runVoiceDiagnostic",
    "actionCode": "VOICE_SERVICE_DIAGNOSTIC",
    "dcxActionCode": "DCX-VSD-01"
  },
  "batch": {
    "index": 0
  },
  "inputs": {
    "downstream": {
      "id": "ASYNC_1e4ec1e0-68a",
      "href": "http://mock-downstream/queries/ASYNC_1e4ec1e0-68a"
    }
  },
  "trigger": {
    "externalEventId": "e7f8a9b0-...",
    "externalType": "TaskFinalAsyncResponseSend",
    "reportingSystem": "ACUT"
  }
}
```

**What the Orchestrator does:** Logs the signal for observability. Does NOT process it — only the task-runner acts on signals.

**What MockTaskRunner does:**
1. Reads `inputs.downstream.href` — in production, would GET this URL to fetch the result
2. PATCHes the taskFlow on TMF-701 with the fetched result (simulated)
3. Publishes `task.event` COMPLETED (step 7)

---

## Step 7 — task.event COMPLETED (runVoiceDiagnostic)

**Topic:** `task.command`
**Published by:** MockTaskRunner (after receiving signal)
**Consumed by:** Orchestrator
**Payload type:** `TaskCommand`

```json
{
  "eventId": "c5d6e7f8-...",
  "correlationId": "bbf5e84d-cf41-44ba-b0d4-45e4e8b0a152",
  "schemaVersion": "1.0",
  "eventTime": "2026-04-14T01:41:27.576Z",
  "messageType": "EVENT",
  "messageName": "task.event",
  "source": "task-runner",
  "status": "COMPLETED",
  "task": {
    "id": "tf-31c94387",
    "href": "http://mock-tmf701/processFlow/bbf5e84d-.../taskFlow/tf-31c94387"
  },
  "action": {
    "actionName": "runVoiceDiagnostic",
    "actionCode": "VOICE_SERVICE_DIAGNOSTIC",
    "dcxActionCode": "DCX-VSD-01"
  },
  "batch": {
    "index": 0
  },
  "execution": {
    "mode": "ASYNC",
    "attempt": 1,
    "startedAt": "2026-04-14T01:41:26.552Z",
    "finishedAt": "2026-04-14T01:41:27.552Z",
    "durationMs": 1000
  },
  "downstream": {
    "id": "ASYNC_1e4ec1e0-68a",
    "href": "http://mock-downstream/queries/ASYNC_1e4ec1e0-68a"
  },
  "result": {
    "asyncResult": "PASS",
    "fetchedFrom": "http://mock-downstream/queries/ASYNC_1e4ec1e0-68a",
    "completedAt": "2026-04-14T01:41:27.552Z"
  }
}
```

**What the Orchestrator does:**
1. Updates `task_execution` row: `status = COMPLETED`, adds `result_json`
2. Updates `batch_barrier`: `task_completed = 2`, `pending = 0`
3. Since `pending = 0` → closes the barrier: `status = CLOSED`
4. Calls `promoteNextBatch()`:
   - Looks up DAG → batch 1 exists
   - Creates a new `batch_barrier` row for batch 1:

     | correlation_id | batch_index | task_total | task_completed | task_failed | status |
     |---|---|---|---|---|---|
     | bbf5e84d-... | 1 | 1 | 0 | 0 | OPEN |

   - Publishes 1 `task.execute` command for batch 1

---

## Step 8 — task.execute (sendNotification, SYNC)

**Topic:** `task.command`
**Published by:** Orchestrator (after batch 0 closed, batch 1 seeded)
**Consumed by:** MockTaskRunner
**Payload type:** `TaskCommand`

```json
{
  "eventId": "d7e8f9a0-...",
  "correlationId": "bbf5e84d-cf41-44ba-b0d4-45e4e8b0a152",
  "schemaVersion": "1.0",
  "eventTime": "2026-04-14T01:41:27.578Z",
  "messageType": "COMMAND",
  "messageName": "task.execute",
  "source": "task-orchestrator",
  "dagKey": "Auto_Remediation",
  "intent": "EXECUTE",
  "action": {
    "actionName": "sendNotification",
    "actionCode": "NOTIFY_USER",
    "dcxActionCode": "DCX-NOT-09"
  },
  "batch": {
    "index": 1,
    "total": 1
  },
  "execution": {
    "mode": "SYNC",
    "attempt": 1,
    "maxAttempts": 2,
    "timeoutMs": 10000
  },
  "inputs": {
    "processFlow": {}
  }
}
```

Note: `inputs.processFlow` is empty for batch 1+ because the original
processFlow data was only passed to batch 0.

---

## Step 9 — task.event COMPLETED (sendNotification)

**Topic:** `task.command`
**Published by:** MockTaskRunner
**Consumed by:** Orchestrator
**Payload type:** `TaskCommand`

```json
{
  "eventId": "e9f0a1b2-...",
  "correlationId": "bbf5e84d-cf41-44ba-b0d4-45e4e8b0a152",
  "schemaVersion": "1.0",
  "eventTime": "2026-04-14T01:41:27.864Z",
  "messageType": "EVENT",
  "messageName": "task.event",
  "source": "task-runner",
  "status": "COMPLETED",
  "task": {
    "id": "tf-4b6b61e2",
    "href": "http://mock-tmf701/processFlow/bbf5e84d-.../taskFlow/tf-4b6b61e2"
  },
  "action": {
    "actionName": "sendNotification",
    "actionCode": "NOTIFY_USER",
    "dcxActionCode": "DCX-NOT-09"
  },
  "batch": {
    "index": 1
  },
  "execution": {
    "mode": "SYNC",
    "attempt": 1,
    "startedAt": "2026-04-14T01:41:26.814Z",
    "finishedAt": "2026-04-14T01:41:27.814Z",
    "durationMs": 1000
  },
  "result": {
    "syncResult": "OK",
    "actionName": "sendNotification",
    "echoedAt": "2026-04-14T01:41:27.814Z"
  }
}
```

**What the Orchestrator does:**
1. Updates `batch_barrier` batch 1: `task_completed = 1`, `pending = 0`
2. Closes the barrier: `status = CLOSED`
3. Calls `promoteNextBatch()` → no batch 2 in DAG → flow is done
4. PATCHes mock TMF-701 to mark the processFlow completed:
   ```
   PATCH /mock/tmf701/processFlow/bbf5e84d-...
   { "state": "completed" }
   ```

---

## Async Response Event (production reference)

In production, when an external system completes an async operation, it publishes
a `TaskFinalAsyncResponseSend` event to `notification.management`. This is what
triggers the pamconsumer to publish the `task.signal` in step 6.

**Topic:** `notification.management`
**Published by:** External downstream system (via notification-management)
**Consumed by:** Real pamconsumer (or MockPamConsumer's `onNotificationEvent` listener)
**Payload type:** `AsyncResponseEvent` (real production structure)

```json
{
  "correlationId": "VOICE_transactionId_A267E5B77F4746799381872C",
  "description": "",
  "domain": "bell-it-sa",
  "eventId": "0E530799-32F3-4cd8-8919-db9696ccfafa",
  "eventTime": "2026-04-01T18:34:34.149335894-04:00",
  "eventType": null,
  "priority": "NORMAL",
  "timeOccurred": "2026-04-01T18:34:34.149318241-04:00",
  "title": "vsdt-task-request",
  "analyticCharacteristic": [],
  "event": {
    "id": "VOICE_transactionId_A267E5B77F4746799381872C",
    "href": "https://sharp-oneside-task.apps.ocp-prd-wvn.bell.corp.bce.ca/onesideTaskCatalog/findVoiceServiceDiagnosticFromCacheByTransactionId/VOICE_transactionId_A267E5B77F4746799381872C",
    "callerIdentifier": []
  },
  "relatedParty": [],
  "reportingSystem": {
    "id": "ACUT",
    "href": null,
    "name": "ACUT",
    "@baseType": null,
    "@schemaLocation": null,
    "@type": null,
    "@referredType": null
  },
  "source": null,
  "@baseType": "event",
  "@schemaLocation": null,
  "@type": "TaskFinalAsyncResponseSend"
}
```

**What pamconsumer does when it reads this:**
1. Checks `@type` → `"TaskFinalAsyncResponseSend"` → this is an async completion event
2. Extracts `event.id` → `"VOICE_transactionId_A267..."` → this is the `businessTxnId`
3. Looks up `waiting_task` table by `businessTxnId` to find the matching `taskId` and `correlationId`
4. Extracts `event.href` → the URL to fetch the result from
5. Extracts `reportingSystem.id` → `"ACUT"`
6. Publishes a `task.signal` TaskCommand to `task.command` (step 6 above)

In the local mock, step 6 is triggered directly by MockTaskRunner calling
`MockPamConsumer.publishSignalAfterDelay()` instead of going through
`notification.management`, since the mock doesn't have a real `waiting_task` table.

---

## Final State

### batch_barrier table (PostgreSQL)

```sql
SELECT batch_index, task_total, task_completed, task_failed, status
FROM batch_barrier WHERE correlation_id = 'bbf5e84d-...';
```

| batch_index | task_total | task_completed | task_failed | status |
|-------------|-----------|----------------|-------------|--------|
| 0 | 2 | 2 | 0 | CLOSED |
| 1 | 1 | 1 | 0 | CLOSED |

### task_execution table (PostgreSQL)

```sql
SELECT action_name, action_code, batch_index, status, downstream_id
FROM task_execution WHERE correlation_id = 'bbf5e84d-...'
ORDER BY batch_index, action_name;
```

| action_name | action_code | batch_index | status | downstream_id |
|------------|-------------|-------------|--------|---------------|
| runInternetCheck | INTERNET_CHECK | 0 | COMPLETED | — |
| runVoiceDiagnostic | VOICE_SERVICE_DIAGNOSTIC | 0 | COMPLETED | ASYNC_1e4ec1e0-68a |
| sendNotification | NOTIFY_USER | 1 | COMPLETED | — |

### Mock TMF-701 processFlow state

```
GET /mock/tmf701/processFlow/bbf5e84d-...
```

```json
{
  "state": "completed",
  "relatedEntity": [
    {
      "id": "tf-ca72d013",
      "name": "runInternetCheck",
      "role": "TaskFlow",
      "@type": "RelatedEntity",
      "@referredType": "TaskFlow"
    },
    {
      "id": "tf-31c94387",
      "name": "runVoiceDiagnostic",
      "role": "TaskFlow",
      "@type": "RelatedEntity",
      "@referredType": "TaskFlow"
    },
    {
      "id": "tf-4b6b61e2",
      "name": "sendNotification",
      "role": "TaskFlow",
      "@type": "RelatedEntity",
      "@referredType": "TaskFlow"
    }
  ]
}
```

### Kafka UI (http://localhost:9091)

**notification.management topic — 1 message:**

| Offset | @type | Key |
|--------|-------|-----|
| 0 | processFlow | bbf5e84d-... |

Payload is the real TMF-701 native JSON with `relatedEntity`, `characteristic`, `processFlowSpecification`.

**task.command topic — 9 messages:**

| Offset | messageName | source | status |
|--------|-------------|--------|--------|
| 0 | processFlow.initiated | pamconsumer | — |
| 1 | task.execute | task-orchestrator | — |
| 2 | task.execute | task-orchestrator | — |
| 3 | task.event | task-runner | COMPLETED |
| 4 | task.event | task-runner | WAITING |
| 5 | task.signal | pamconsumer | — |
| 6 | task.event | task-runner | COMPLETED |
| 7 | task.execute | task-orchestrator | — |
| 8 | task.event | task-runner | COMPLETED |

### Consumer groups (all lag = 0)

| Group | Topic | Partitions |
|-------|-------|-----------|
| task-orchestrator | task.command | 0, 1, 2 |
| mock-task-runner | task.command | 0, 1, 2 |
| mock-pamconsumer | notification.management | 0, 1, 2 |

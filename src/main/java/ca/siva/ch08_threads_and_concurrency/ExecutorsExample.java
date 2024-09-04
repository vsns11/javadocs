package ca.siva.ch08_threads_and_concurrency;

import java.util.concurrent.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExecutorsExample {

    public static void main(String[] args) {
        log.info("Starting Executors Examples...");

        executeFixedThreadPool();
        executeCachedThreadPool();
        executeSingleThreadExecutor();
        executeScheduledThreadPool();
        executeSingleThreadScheduledExecutor();
        executeScheduleCallable();
        executeScheduleAtFixedRate();
        executeScheduleWithFixedDelay();
    }

    // Example 1: FixedThreadPool
    public static void executeFixedThreadPool() {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 5; i++) {
            int taskNumber = i;
            fixedThreadPool.execute(() -> {
                log.info("FixedThreadPool - Task {} running in {}", taskNumber, Thread.currentThread().getName());
                try {
                    Thread.sleep(1000); // Simulate work
                } catch (InterruptedException e) {
                    log.error("Task {} was interrupted", taskNumber, e);
                }
            });
        }

        fixedThreadPool.shutdown();
        try {
            fixedThreadPool.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("FixedThreadPool was interrupted during shutdown", e);
        }
    }

    // Example 2: CachedThreadPool
    public static void executeCachedThreadPool() {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        for (int i = 0; i < 5; i++) {
            int taskNumber = i;
            cachedThreadPool.execute(() -> {
                log.info("CachedThreadPool - Task {} running in {}", taskNumber, Thread.currentThread().getName());
                try {
                    Thread.sleep(1000); // Simulate work
                } catch (InterruptedException e) {
                    log.error("Task {} was interrupted", taskNumber, e);
                }
            });
        }

        cachedThreadPool.shutdown();
        try {
            cachedThreadPool.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("CachedThreadPool was interrupted during shutdown", e);
        }
    }

    // Example 3: SingleThreadExecutor
    public static void executeSingleThreadExecutor() {
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

        for (int i = 0; i < 5; i++) {
            int taskNumber = i;
            singleThreadExecutor.execute(() -> {
                log.info("SingleThreadExecutor - Task {} running in {}", taskNumber, Thread.currentThread().getName());
                try {
                    Thread.sleep(1000); // Simulate work
                } catch (InterruptedException e) {
                    log.error("Task {} was interrupted", taskNumber, e);
                }
            });
        }

        singleThreadExecutor.shutdown();
        try {
            singleThreadExecutor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("SingleThreadExecutor was interrupted during shutdown", e);
        }
    }

    // Example 4: ScheduledThreadPool
    public static void executeScheduledThreadPool() {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(2);

        for (int i = 0; i < 5; i++) {
            int taskNumber = i;
            scheduledThreadPool.schedule(() -> {
                log.info("ScheduledThreadPool - Task {} running in {}", taskNumber, Thread.currentThread().getName());
                try {
                    Thread.sleep(1000); // Simulate work
                } catch (InterruptedException e) {
                    log.error("Task {} was interrupted", taskNumber, e);
                }
            }, 2, TimeUnit.SECONDS);
        }

        scheduledThreadPool.shutdown();
        try {
            scheduledThreadPool.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("ScheduledThreadPool was interrupted during shutdown", e);
        }
    }

    // Example 5: SingleThreadScheduledExecutor
    public static void executeSingleThreadScheduledExecutor() {
        ScheduledExecutorService singleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();

        for (int i = 0; i < 5; i++) {
            int taskNumber = i;
            singleThreadScheduledExecutor.schedule(() -> {
                log.info("SingleThreadScheduledExecutor - Task {} running in {}", taskNumber, Thread.currentThread().getName());
                try {
                    Thread.sleep(1000); // Simulate work
                } catch (InterruptedException e) {
                    log.error("Task {} was interrupted", taskNumber, e);
                }
            }, 2, TimeUnit.SECONDS);
        }

        singleThreadScheduledExecutor.shutdown();
        try {
            singleThreadScheduledExecutor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("SingleThreadScheduledExecutor was interrupted during shutdown", e);
        }
    }

    // Example 6: Schedule a Callable task
    public static void executeScheduleCallable() {
        ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(1);

        Callable<String> callableTask = () -> {
            log.info("Scheduled Callable Task running in {}", Thread.currentThread().getName());
            return "Callable Result";
        };

        ScheduledFuture<String> future = scheduledExecutor.schedule(callableTask, 3, TimeUnit.SECONDS);

        try {
            log.info("Scheduled Callable Result: {}", future.get());
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error during Scheduled Callable execution", e);
        }

        scheduledExecutor.shutdown();
    }

    // Example 7: Schedule a task at a fixed rate
    public static void executeScheduleAtFixedRate() {
        ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(1);

        Runnable periodicTask = () -> log.info("Fixed Rate Task running in {}", Thread.currentThread().getName());

        ScheduledFuture<?> future = scheduledExecutor.scheduleAtFixedRate(periodicTask, 1, 2, TimeUnit.SECONDS);

        scheduledExecutor.schedule(() -> {
            future.cancel(false);
            log.info("Fixed Rate Task cancelled");
        }, 10, TimeUnit.SECONDS);

        scheduledExecutor.shutdown();
        try {
            scheduledExecutor.awaitTermination(15, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("ScheduledExecutor was interrupted during shutdown", e);
        }
    }

    // Example 8: Schedule a task with a fixed delay
    public static void executeScheduleWithFixedDelay() {
        ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(1);

        Runnable delayedTask = () -> log.info("Fixed Delay Task running in {}", Thread.currentThread().getName());

        ScheduledFuture<?> future = scheduledExecutor.scheduleWithFixedDelay(delayedTask, 1, 3, TimeUnit.SECONDS);

        scheduledExecutor.schedule(() -> {
            future.cancel(false);
            log.info("Fixed Delay Task cancelled");
        }, 10, TimeUnit.SECONDS);

        scheduledExecutor.shutdown();
        try {
            scheduledExecutor.awaitTermination(15, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("ScheduledExecutor was interrupted during shutdown", e);
        }
    }
}

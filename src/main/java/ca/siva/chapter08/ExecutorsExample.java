package ca.siva.chapter08;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
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
}

package ca.siva.chapter08;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AtomicExamples {

    private static AtomicBoolean atomicBoolean = new AtomicBoolean(false);
    private static AtomicInteger atomicInteger = new AtomicInteger(0);
    private static AtomicLong atomicLong = new AtomicLong(0L);

    public static void main(String[] args) {
        log.info("Starting Atomic Examples...");

        Thread t1 = new Thread(new BooleanTask(), "Boolean-Thread-1");
        Thread t2 = new Thread(new BooleanTask(), "Boolean-Thread-2");
        Thread t3 = new Thread(new IntegerTask(), "Integer-Thread-1");
        Thread t4 = new Thread(new IntegerTask(), "Integer-Thread-2");
        Thread t5 = new Thread(new LongTask(), "Long-Thread-1");
        Thread t6 = new Thread(new LongTask(), "Long-Thread-2");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
    }

    // Example 1: Using AtomicBoolean
    static class BooleanTask implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                boolean currentFlag = atomicBoolean.get();
                log.info("{} - Flag: {}", Thread.currentThread().getName(), currentFlag);
                if (atomicBoolean.compareAndSet(currentFlag, !currentFlag)) {
                    log.info("{} - Updated Flag to: {}", Thread.currentThread().getName(), !currentFlag);
                }
                try {
                    Thread.sleep(1000); // Simulate work
                } catch (InterruptedException e) {
                    log.error("{} was interrupted", Thread.currentThread().getName(), e);
                }
            }
        }
    }

    // Example 2: Using AtomicInteger
    static class IntegerTask implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                int newCount = atomicInteger.incrementAndGet();
                log.info("{} - Count: {}", Thread.currentThread().getName(), newCount);
                try {
                    Thread.sleep(500); // Simulate work
                } catch (InterruptedException e) {
                    log.error("{} was interrupted", Thread.currentThread().getName(), e);
                }
            }
        }
    }

    // Example 3: Using AtomicLong
    static class LongTask implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                long newCount = atomicLong.incrementAndGet();
                log.info("{} - Count: {}", Thread.currentThread().getName(), newCount);
                try {
                    Thread.sleep(700); // Simulate work
                } catch (InterruptedException e) {
                    log.error("{} was interrupted", Thread.currentThread().getName(), e);
                }
            }
        }
    }
}

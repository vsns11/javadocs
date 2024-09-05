package ca.siva.ch08_threads_and_concurrency;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import lombok.extern.slf4j.Slf4j;

/*
NOTE:
1) the fact that a thread may reacquire the lock that it already owns and that it must release the lock (by calling the unlock method) the same number of times as the number of times it has called lock or tryLock.
2) Lock interface has lock() and unlock() methods, whereas for ReadWriteLock, it has to use lock.readLock().lock() and lock.readLock().unlock()
 */

@Slf4j
public class LockExamples {

    // Shared resources
    private static int sharedResource = 0;

    // ReentrantLock example
    private static final ReentrantLock reentrantLock = new ReentrantLock();

    // StampedLock example
    private static final StampedLock stampedLock = new StampedLock();

    // CyclicBarrier example
    private static final CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> {
        log.info("All threads have reached the barrier. Proceeding...");
    });

    public static void main(String[] args) {
        log.info("Starting Lock Examples...");

        Thread t1 = new Thread(LockExamples::reentrantLockExample, "ReentrantLock-Thread-1");
        Thread t2 = new Thread(LockExamples::reentrantLockExample, "ReentrantLock-Thread-2");
        Thread t3 = new Thread(LockExamples::cyclicBarrierExample, "CyclicBarrier-Thread-1");
        Thread t4 = new Thread(LockExamples::cyclicBarrierExample, "CyclicBarrier-Thread-2");
        Thread t5 = new Thread(LockExamples::cyclicBarrierExample, "CyclicBarrier-Thread-3");
        Thread t6 = new Thread(LockExamples::stampedLockExample, "StampedLock-Thread-1");
        Thread t7 = new Thread(LockExamples::stampedLockExample, "StampedLock-Thread-2");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
    }

    // Example 1: Using ReentrantLock
    public static void reentrantLockExample() {
        for (int i = 0; i < 5; i++) {
            reentrantLock.lock();
            try {
                log.info("{} - Locked and incrementing shared resource.", Thread.currentThread().getName());
                sharedResource++;
                log.info("{} - Shared Resource: {}", Thread.currentThread().getName(), sharedResource);
            } finally {
                log.info("{} - Unlocking.", Thread.currentThread().getName());
                reentrantLock.unlock();
            }
            try {
                Thread.sleep(1000); // Simulate work
            } catch (InterruptedException e) {
                log.error("{} was interrupted", Thread.currentThread().getName(), e);
            }
        }
    }

    // Example 3: Using StampedLock
    public static void stampedLockExample() {
        for (int i = 0; i < 5; i++) {
            if (Thread.currentThread().getName().contains("StampedLock-Thread-1")) {
                long stamp = stampedLock.tryOptimisticRead();
                int currentValue = sharedResource;
                log.info("{} - Optimistic Read: {}", Thread.currentThread().getName(), currentValue);
                if (!stampedLock.validate(stamp)) {
                    stamp = stampedLock.readLock();
                    try {
                        currentValue = sharedResource;
                        log.info("{} - Converted to Read Lock: {}", Thread.currentThread().getName(), currentValue);
                    } finally {
                        stampedLock.unlockRead(stamp);
                    }
                }
            } else {
                long stamp = stampedLock.writeLock();
                try {
                    log.info("{} - Write Lock: {}", Thread.currentThread().getName());
                    sharedResource++;
                    log.info("{} - Shared Resource: {}", Thread.currentThread().getName(), sharedResource);
                } finally {
                    stampedLock.unlockWrite(stamp);
                }
            }
            try {
                Thread.sleep(1000); // Simulate work
            } catch (InterruptedException e) {
                log.error("{} was interrupted", Thread.currentThread().getName(), e);
            }
        }
    }

    // Example 4: Using CyclicBarrier
    public static void cyclicBarrierExample() {
        try {
            log.info("{} - Performing some work before barrier", Thread.currentThread().getName());
            Thread.sleep((int) (Math.random() * 3000)); // Simulate work
            log.info("{} - Waiting at the barrier", Thread.currentThread().getName());
            cyclicBarrier.await(); // Wait at the barrier
            log.info("{} - Proceeding after the barrier", Thread.currentThread().getName());
        } catch (InterruptedException | BrokenBarrierException e) {
            log.error("{} was interrupted or barrier was broken", Thread.currentThread().getName(), e);
        }
    }
}

package ca.siva.ch08_threads_and_concurrency;
import lombok.extern.slf4j.Slf4j;

/*
NOTE:
1) Thread Lifecycle Summary
NEW → RUNNABLE: When start() is called on the thread.
RUNNABLE → BLOCKED: When the thread tries to acquire an object lock.
RUNNABLE → WAITING/TIMED_WAITING: When waiting for another thread or sleeping.
BLOCKED/WAITING/TIMED_WAITING → RUNNABLE: When the thread acquires the lock or is notified.
RUNNABLE → TERMINATED: When the run() method finishes or an unhandled exception occurs.

2)
What it is: Livelock happens when two or more threads keep reacting to each other in a way that prevents any progress. Unlike deadlock, the threads are not stuck, but they keep changing their states in response to each other and don't accomplish their tasks.
What it is: Starvation occurs when a thread is constantly prevented from accessing the resources it needs to run because other threads keep getting priority. As a result, the starved thread waits indefinitely.
What it is: Deadlock happens when two or more threads are stuck because they are waiting for each other to release a resource (like a lock), but none of them can move forward.

3)  List<Runnable> shutdownNow() This method attempts to stop all actively executing tasks, halts the processing of waiting tasks, and returns a list of the tasks that were awaiting execution.
This method does not wait for actively executing tasks to terminate. Use awaitTermination to do that.

 */
@Slf4j
public class ThreadExamples {

    // Example 1: Creating a Thread by Extending Thread Class
    public static void exampleExtendThread() {
        class MyThread extends Thread {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    log.info("{} - Count: {}", Thread.currentThread().getName(), i);
                    try {
                        Thread.sleep(1000); // Simulate work by sleeping for 1 second
                    } catch (InterruptedException e) {
                        log.info("{} was interrupted", Thread.currentThread().getName());
                        return; // Exit the loop if interrupted
                    }
                }
            }
        }

        MyThread thread = new MyThread();
        thread.start();
        try {
            Thread.sleep(3000); // Let the thread run for a while
            thread.interrupt(); // Interrupt the thread
        } catch (InterruptedException e) {
            log.error("Main thread was interrupted", e);
        }
    }

    // Example 2: Creating a Thread by Implementing Runnable Interface
    public static void exampleImplementRunnable() {
        Runnable task = () -> {
            for (int i = 0; i < 5; i++) {
                log.info("{} - Count: {}", Thread.currentThread().getName(), i);
                try {
                    Thread.sleep(1000); // Simulate work by sleeping for 1 second
                } catch (InterruptedException e) {
                    log.info("{} was interrupted", Thread.currentThread().getName());
                    return; // Exit the loop if interrupted
                }
            }
        };

        Thread thread = new Thread(task);
        thread.start();
        try {
            Thread.sleep(3000); // Let the thread run for a while
            thread.interrupt(); // Interrupt the thread
        } catch (InterruptedException e) {
            log.error("Main thread was interrupted", e);
        }
    }

    // Example 3: Handling interrupt()
    public static void exampleInterruptHandling() {
        Thread thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                log.info("{} is running", Thread.currentThread().getName());
                try {
                    Thread.sleep(1000); // Simulate work by sleeping for 1 second
                } catch (InterruptedException e) {
                    log.info("{} was interrupted during sleep", Thread.currentThread().getName());
                    Thread.currentThread().interrupt(); // Re-interrupt the thread
                }
            }
            log.info("{} has stopped", Thread.currentThread().getName());
        });

        thread.start();
        try {
            Thread.sleep(3000); // Let the thread run for a while
            thread.interrupt(); // Interrupt the thread
        } catch (InterruptedException e) {
            log.error("Main thread was interrupted", e);
        }
    }

    // Main method to execute all examples
    public static void main(String[] args) {
        log.info("Example: Extend Thread Class");
        exampleExtendThread();
        try { Thread.sleep(6000); } catch (InterruptedException ignored) {} // Pause between examples

        log.info("\nExample: Implement Runnable Interface");
        exampleImplementRunnable();
        try { Thread.sleep(6000); } catch (InterruptedException ignored) {} // Pause between examples

        log.info("\nExample: Handling interrupt()");
        exampleInterruptHandling();
    }
}

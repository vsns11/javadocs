package ca.siva.chapter08;
import lombok.extern.slf4j.Slf4j;

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

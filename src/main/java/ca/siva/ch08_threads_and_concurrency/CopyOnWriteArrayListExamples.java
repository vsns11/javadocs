package ca.siva.ch08_threads_and_concurrency;


import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/*
NOTE:
1) Each modification (like add(), remove(), etc.) on CopyOnWriteArrayList creates a new copy of the underlying array.
Therefore, the iterator continues to iterate over the old copy, while any modifications affect the new copy.
2) CopyOnWriteArrayList is thread-safe and does not throw ConcurrentModificationException.
3) It is efficient for scenarios with a large number of reads and fewer writes, as writes cause array copying.
 */

@Slf4j
public class CopyOnWriteArrayListExamples {

    public static void main(String[] args) {
        log.info("Starting CopyOnWriteArrayList Examples...");

        exampleBasicUsage();
        exampleSafeIterationDuringModification();
        exampleConcurrentAccess();
        exampleIterationWithIterator();
    }

    // Example 1: Basic Usage of CopyOnWriteArrayList
    public static void exampleBasicUsage() {
        List<String> copyOnWriteList = new CopyOnWriteArrayList<>();
        copyOnWriteList.add("Item1");
        copyOnWriteList.add("Item2");
        copyOnWriteList.add("Item3");

        log.info("Basic Usage - Initial List: {}", copyOnWriteList);

        // Modifying the list
        copyOnWriteList.add("NewItem");
        log.info("Basic Usage - After adding NewItem: {}", copyOnWriteList);

        // Removing an item
        copyOnWriteList.remove("Item2");
        log.info("Basic Usage - After removing Item2: {}", copyOnWriteList);
    }

    // Example 2: Safe Iteration While Modifying CopyOnWriteArrayList
    public static void exampleSafeIterationDuringModification() {
        List<String> copyOnWriteList = new CopyOnWriteArrayList<>();
        copyOnWriteList.add("Item1");
        copyOnWriteList.add("Item2");
        copyOnWriteList.add("Item3");

        log.info("Safe Iteration - Initial List: {}", copyOnWriteList);

        // Iterating and modifying
        for (String item : copyOnWriteList) {
            log.info("Safe Iteration - Processing item: {}", item);
            if (item.equals("Item2")) {
                // Modifying the list during iteration
                copyOnWriteList.add("NewItem");
                log.info("Safe Iteration - Added NewItem during iteration");
            }
        }

        // After iteration, the new item is added to the list
        log.info("Safe Iteration - Updated List after iteration: {}", copyOnWriteList);
    }

    // Example 3: Concurrent Access with CopyOnWriteArrayList
    public static void exampleConcurrentAccess() {
        List<String> copyOnWriteList = new CopyOnWriteArrayList<>();
        copyOnWriteList.add("Item1");
        copyOnWriteList.add("Item2");
        copyOnWriteList.add("Item3");

        log.info("Concurrent Access - Initial List: {}", copyOnWriteList);

        // Creating two threads that modify the list concurrently
        Thread writerThread = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                copyOnWriteList.add("WriterItem" + i);
                log.info("Writer Thread added WriterItem{}", i);
                try {
                    Thread.sleep(500); // Simulate some work
                } catch (InterruptedException e) {
                    log.error("Writer Thread interrupted", e);
                }
            }
        });

        Thread readerThread = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                log.info("Reader Thread - List content: {}", copyOnWriteList);
                try {
                    Thread.sleep(500); // Simulate some work
                } catch (InterruptedException e) {
                    log.error("Reader Thread interrupted", e);
                }
            }
        });

        writerThread.start();
        readerThread.start();

        try {
            writerThread.join();
            readerThread.join();
        } catch (InterruptedException e) {
            log.error("Main thread interrupted", e);
        }

        log.info("Concurrent Access - Final List: {}", copyOnWriteList);
    }

    // Example 4: Iterating Using an Iterator and Modifying During Iteration
    public static void exampleIterationWithIterator() {
        List<String> copyOnWriteList = new CopyOnWriteArrayList<>();
        copyOnWriteList.add("Item1");
        copyOnWriteList.add("Item2");
        copyOnWriteList.add("Item3");

        log.info("Iterator - Initial List: {}", copyOnWriteList);

        // Obtaining an iterator
        Iterator<String> iterator = copyOnWriteList.iterator();

        // Iterating and modifying at the same time
        while (iterator.hasNext()) {
            String item = iterator.next();
            log.info("Iterator - Processing item: {}", item);

            // Modify the list while iterating (Iterator works on a snapshot, so this won't affect the current iteration)
            if (item.equals("Item1")) {
                copyOnWriteList.add("NewItem");
                log.info("Iterator - Added NewItem during iteration");
            }
        }

        // After iteration, the new item is added to the list
        log.info("Iterator - Updated List after iteration: {}", copyOnWriteList);
    }
}

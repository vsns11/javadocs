package ca.siva.ch05_collections;

import java.util.LinkedList;
import java.util.ListIterator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LinkedListExamples {

    public static void main(String[] args) {
        // Example 1: Basic Operations on LinkedList
        basicLinkedListOperations();

        // Example 2: Using LinkedList as a Queue
        linkedListAsQueue();

        // Example 3: Using LinkedList as a Deque
        linkedListAsDeque();

        // Example 4: Iterating through a LinkedList
        iterateThroughLinkedList();

        // Example 5: Reversing a LinkedList
        reverseLinkedList();

        // Example 6: Cloning a LinkedList
        cloneLinkedList();
    }

    // Example 1: Basic LinkedList Operations
    public static void basicLinkedListOperations() {
        LinkedList<String> list = new LinkedList<>();

        // Add elements
        list.add("Element 1");
        list.add("Element 2");
        list.addLast("Element 3"); // Add to the end
        list.addFirst("Element 0"); // Add to the beginning

        // Access elements
        log.info("First Element: {}", list.getFirst());
        log.info("Last Element: {}", list.getLast());

        // Remove elements
        list.removeFirst(); // Remove the first element
        list.removeLast(); // Remove the last element

        // Print final list
        log.info("List after operations: {}", list);
    }

    // Example 2: Using LinkedList as a Queue (FIFO)
    public static void linkedListAsQueue() {
        LinkedList<String> queue = new LinkedList<>();

        // Add elements to the queue (end of the list)
        queue.add("Task 1");
        queue.add("Task 2");
        queue.add("Task 3");

        // Processing tasks in FIFO order
        while (!queue.isEmpty()) {
            log.info("Processing: {}", queue.poll()); // Retrieve and remove head
        }
    }

    // Example 3: Using LinkedList as a Deque (Double-Ended Queue)
    public static void linkedListAsDeque() {
        LinkedList<String> deque = new LinkedList<>();

        // Add elements at both ends
        deque.addFirst("First Element");
        deque.addLast("Last Element");

        // Access elements at both ends
        log.info("Deque First: {}", deque.getFirst());
        log.info("Deque Last: {}", deque.getLast());

        // Remove elements from both ends
        deque.removeFirst();
        deque.removeLast();

        log.info("Deque after removing both ends: {}", deque);
    }

    // Example 4: Iterating through LinkedList (Using ListIterator)
    public static void iterateThroughLinkedList() {
        LinkedList<String> list = new LinkedList<>();
        list.add("Item A");
        list.add("Item B");
        list.add("Item C");

        // Using ListIterator to traverse LinkedList
        ListIterator<String> iterator = list.listIterator();
        log.info("Forward iteration:");
        while (iterator.hasNext()) {
            log.info(iterator.next());
        }

        // Iterating backward using ListIterator
        log.info("Backward iteration:");
        while (iterator.hasPrevious()) {
            log.info(iterator.previous());
        }
    }

    // Example 5: Reversing a LinkedList
    public static void reverseLinkedList() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        log.info("Original List: {}", list);

        // Using a ListIterator to reverse
        LinkedList<Integer> reversedList = new LinkedList<>();
        ListIterator<Integer> iterator = list.listIterator(list.size()); // Start at the end

        while (iterator.hasPrevious()) {
            reversedList.add(iterator.previous());
        }

        log.info("Reversed List: {}", reversedList);
    }

    // Example 6: Cloning a LinkedList
    public static void cloneLinkedList() {
        LinkedList<String> original = new LinkedList<>();
        original.add("Element 1");
        original.add("Element 2");
        original.add("Element 3");

        // Shallow copy of the LinkedList
        LinkedList<String> cloned = (LinkedList<String>) original.clone();

        // Print both original and cloned lists
        log.info("Original List: {}", original);
        log.info("Cloned List: {}", cloned);

        // Check that they are different objects but contain the same elements
        log.info("Are both lists equal? {}", original.equals(cloned));
    }
}

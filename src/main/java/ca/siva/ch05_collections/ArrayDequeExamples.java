package ca.siva.ch05_collections;

import lombok.extern.slf4j.Slf4j;
import java.util.ArrayDeque;
import java.util.Deque;

/*
  NOTE:
  1) In ArrayDeque, Push operation inserts element at the beginning of the queue.
  2) addFirst() and addLast() are part of Deque interface but not in queue.
 */
@Slf4j
public class ArrayDequeExamples {

    public static void main(String[] args) {
        ArrayDequeExamples examples = new ArrayDequeExamples();

        examples.exampleAddElements();
        examples.exampleRemoveElements();
        examples.exampleAddFirstAddLast();
        examples.exampleRemoveFirstRemoveLast();
        examples.exampleOfferPoll();
        examples.exampleDequeAsStack();
        examples.exampleIterateDeque();
    }

    // Example of adding elements to an ArrayDeque
    // Output: ArrayDeque after adding elements: [one, two, three]
    public void exampleAddElements() {
        Deque<String> deque = new ArrayDeque<>();
        deque.add("one");
        deque.add("two");
        deque.add("three");

        log.info("ArrayDeque after adding elements: {}", deque);
    }

    // Example of removing elements from an ArrayDeque
    // Output:
    // Removed element: one
    // ArrayDeque after removing an element: [two, three]
    public void exampleRemoveElements() {
        Deque<String> deque = new ArrayDeque<>();
        deque.add("one");
        deque.add("two");
        deque.add("three");

        String removedElement = deque.remove();
        log.info("Removed element: {}", removedElement);
        log.info("ArrayDeque after removing an element: {}", deque);
    }

    // Example of adding elements to the front and end of an ArrayDeque
    // Output: ArrayDeque after adding elements to the front and end: [zero, one, two]
    public void exampleAddFirstAddLast() {
        Deque<String> deque = new ArrayDeque<>();
        deque.addFirst("one");
        deque.addLast("two");
        deque.addFirst("zero");

        log.info("ArrayDeque after adding elements to the front and end: {}", deque);
    }

    // Example of removing elements from the front and end of an ArrayDeque
    // Output:
    // Removed first element: one
    // Removed last element: three
    // ArrayDeque after removing first and last elements: [two]
    public void exampleRemoveFirstRemoveLast() {
        Deque<String> deque = new ArrayDeque<>();
        deque.add("one");
        deque.add("two");
        deque.add("three");

        String firstElement = deque.removeFirst();
        String lastElement = deque.removeLast();

        log.info("Removed first element: {}", firstElement);
        log.info("Removed last element: {}", lastElement);
        log.info("ArrayDeque after removing first and last elements: {}", deque);
    }

    // Example of using offer and poll methods with an ArrayDeque
    // Output:
    // Polled element: one
    // ArrayDeque after polling an element: [two, three]
    public void exampleOfferPoll() {
        Deque<String> deque = new ArrayDeque<>();
        deque.offer("one");
        deque.offer("two");
        deque.offer("three");

        String polledElement = deque.poll();
        log.info("Polled element: {}", polledElement);
        log.info("ArrayDeque after polling an element: {}", deque);
    }

    // Example of using an ArrayDeque as a stack
    // Output:
    // ArrayDeque as a stack after pushing elements: [three, two, one]
    // Popped element: three
    // ArrayDeque as a stack after popping an element: [two, one]
    public void exampleDequeAsStack() {
        Deque<String> stack = new ArrayDeque<>();
        stack.push("one");
        stack.push("two");
        stack.push("three");

        log.info("ArrayDeque as a stack after pushing elements: {}", stack);

        String poppedElement = stack.pop();
        log.info("Popped element: {}", poppedElement);
        log.info("ArrayDeque as a stack after popping an element: {}", stack);
    }

    // Example of iterating over an ArrayDeque
    // Output:
    // Iterating over ArrayDeque:
    // Element: one
    // Element: two
    // Element: three
    public void exampleIterateDeque() {
        Deque<String> deque = new ArrayDeque<>();
        deque.add("one");
        deque.add("two");
        deque.add("three");

        log.info("Iterating over ArrayDeque:");
        for (String element : deque) {
            log.info("Element: {}", element);
        }
    }
}

package ca.siva.chapter05;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class CollectionsExamples {

    public static void main(String[] args) {
        CollectionsExamples examples = new CollectionsExamples();

        examples.exampleSort();
        examples.exampleReverse();
        examples.exampleShuffle();
        examples.exampleMinMax();
        examples.exampleBinarySearch();
        examples.exampleFill();
    }

    // Example of sorting a list using Collections.sort()
    // Output: List before sorting: [banana, apple, cherry]
    // List after sorting: [apple, banana, cherry]
    public void exampleSort() {
        List<String> list = new ArrayList<>(List.of("banana", "apple", "cherry"));
        log.info("List before sorting: {}", list);
        Collections.sort(list);
        log.info("List after sorting: {}", list);
    }

    // Example of reversing a list using Collections.reverse()
    // Output: List before reversing: [apple, banana, cherry]
    // List after reversing: [cherry, banana, apple]
    public void exampleReverse() {
        List<String> list = new ArrayList<>(List.of("apple", "banana", "cherry"));
        log.info("List before reversing: {}", list);
        Collections.reverse(list);
        log.info("List after reversing: {}", list);
    }

    // Example of shuffling a list using Collections.shuffle()
    // Output: List before shuffling: [apple, banana, cherry]
    // List after shuffling: [cherry, apple, banana] (output may vary)
    public void exampleShuffle() {
        List<String> list = new ArrayList<>(List.of("apple", "banana", "cherry"));
        log.info("List before shuffling: {}", list);
        Collections.shuffle(list);
        log.info("List after shuffling: {}", list); // Note: Output may vary
    }

    // Example of finding the minimum and maximum in a list using Collections.min() and Collections.max()
    // Output: List: [apple, banana, cherry]
    // Minimum element: apple
    // Maximum element: cherry
    public void exampleMinMax() {
        List<String> list = new ArrayList<>(List.of("apple", "banana", "cherry"));
        String minElement = Collections.min(list);
        String maxElement = Collections.max(list);
        log.info("List: {}", list);
        log.info("Minimum element: {}", minElement);
        log.info("Maximum element: {}", maxElement);
    }

    // Example of binary search in a sorted list using Collections.binarySearch()
    // Output: Sorted List: [apple, banana, cherry]
    // Element 'banana' found at index: 1
    public void exampleBinarySearch() {
        List<String> list = new ArrayList<>(List.of("apple", "banana", "cherry"));
        int index = Collections.binarySearch(list, "banana");
        log.info("Sorted List: {}", list);
        log.info("Element 'banana' found at index: {}", index);
    }

    // Example of filling a list with a specific value using Collections.fill()
    // Output: List before filling: [apple, banana, cherry]
    // List after filling: [filled, filled, filled]
    public void exampleFill() {
        List<String> list = new ArrayList<>(List.of("apple", "banana", "cherry"));
        log.info("List before filling: {}", list);
        Collections.fill(list, "filled");
        log.info("List after filling: {}", list);
    }
}

package ca.siva.ch05_collections;

import lombok.extern.slf4j.Slf4j;
import java.util.HashSet;
import java.util.Set;

/*
 NOTE:
 1) Set.of(xx) or Set.copyOf(xx) creates an immutable set where add/remove/update operations are not allowed.
 2) Where in HashSet elements can be used to perform add/remove/update.

 */
@Slf4j
public class HashSetExamples {

    public static void main(String[] args) {
        HashSetExamples examples = new HashSetExamples();

        examples.exampleAddElements();
        examples.exampleRemoveElements();
        examples.exampleIterateHashSet();
        examples.exampleCheckContains();
        examples.exampleGetSize();
        examples.exampleClearHashSet();
    }

    // Example of adding elements to a HashSet
    // Output: HashSet after adding elements: [banana, cherry, apple]
    public void exampleAddElements() {
        Set<String> hashSet = new HashSet<>();
        hashSet.add("apple");
        hashSet.add("banana");
        hashSet.add("cherry");
        log.info("HashSet after adding elements: {}", hashSet);
    }

    // Example of removing elements from a HashSet
    // Output: HashSet after removing 'banana': [cherry, apple]
    public void exampleRemoveElements() {
        Set<String> hashSet = new HashSet<>();
        hashSet.add("apple");
        hashSet.add("banana");
        hashSet.add("cherry");

        hashSet.remove("banana");
        log.info("HashSet after removing 'banana': {}", hashSet);
    }

    // Example of iterating over a HashSet
    // Output: Iterating over HashSet:
    // Element: apple
    // Element: banana
    // Element: cherry
    public void exampleIterateHashSet() {
        Set<String> hashSet = new HashSet<>();
        hashSet.add("apple");
        hashSet.add("banana");
        hashSet.add("cherry");

        log.info("Iterating over HashSet:");
        for (String element : hashSet) {
            log.info("Element: {}", element);
        }
    }

    // Example of checking if a HashSet contains an element
    // Output: Does HashSet contain 'banana'? true
    public void exampleCheckContains() {
        Set<String> hashSet = new HashSet<>();
        hashSet.add("apple");
        hashSet.add("banana");
        hashSet.add("cherry");

        boolean containsBanana = hashSet.contains("banana");
        log.info("Does HashSet contain 'banana'? {}", containsBanana);
    }

    // Example of getting the size of a HashSet
    // Output: Size of HashSet: 3
    public void exampleGetSize() {
        Set<String> hashSet = new HashSet<>();
        hashSet.add("apple");
        hashSet.add("banana");
        hashSet.add("cherry");

        int size = hashSet.size();
        log.info("Size of HashSet: {}", size);
    }

    // Example of clearing a HashSet
    // Output: HashSet after clearing: []
    public void exampleClearHashSet() {
        Set<String> hashSet = new HashSet<>();
        hashSet.add("apple");
        hashSet.add("banana");
        hashSet.add("cherry");

        hashSet.clear();
        log.info("HashSet after clearing: {}", hashSet);
    }
}

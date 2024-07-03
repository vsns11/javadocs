package ca.siva.chapter05;

import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.TreeSet;

@Slf4j
public class TreeSetExamples {

    public static void main(String[] args) {
        TreeSetExamples examples = new TreeSetExamples();

        examples.exampleAddElements();
        examples.exampleRemoveElements();
        examples.exampleIterateTreeSet();
        examples.exampleHeadSetTailSetSubSet();
        examples.exampleFirstLast();
        examples.exampleHigherLower();
        examples.exampleNavigableSet();
        examples.exampleNaturalOrdering();
        examples.exampleCustomOrdering();
    }

    // Example of adding elements to a TreeSet
    // Output: TreeSet after adding elements: [apple, banana, cherry]
    public void exampleAddElements() {
        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.add("apple");
        treeSet.add("banana");
        treeSet.add("cherry");

        log.info("TreeSet after adding elements: {}", treeSet);
    }

    // Example of removing elements from a TreeSet
    // Output: TreeSet after removing 'banana': [apple, cherry]
    public void exampleRemoveElements() {
        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.add("apple");
        treeSet.add("banana");
        treeSet.add("cherry");

        treeSet.remove("banana");
        log.info("TreeSet after removing 'banana': {}", treeSet);
    }

    // Example of iterating over a TreeSet
    // Output: Iterating over TreeSet:
    // Element: apple
    // Element: banana
    // Element: cherry
    public void exampleIterateTreeSet() {
        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.add("apple");
        treeSet.add("banana");
        treeSet.add("cherry");

        log.info("Iterating over TreeSet:");
        for (String element : treeSet) {
            log.info("Element: {}", element);
        }
    }

    // Example of using headSet, tailSet, and subSet in a TreeSet
    // Output:
    // HeadSet (elements less than 'cherry'): [apple, banana]
    // TailSet (elements from 'banana' onwards): [banana, cherry, date, fig]
    // SubSet (elements between 'apple' and 'cherry'): [apple, banana]
    public void exampleHeadSetTailSetSubSet() {
        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.add("apple");
        treeSet.add("banana");
        treeSet.add("cherry");
        treeSet.add("date");
        treeSet.add("fig");

        log.info("HeadSet (elements less than 'cherry'): {}", treeSet.headSet("cherry"));
        log.info("TailSet (elements from 'banana' onwards): {}", treeSet.tailSet("banana"));
        log.info("SubSet (elements between 'apple' and 'cherry'): {}", treeSet.subSet("apple", "cherry"));
    }

    // Example of getting the first and last elements in a TreeSet
    // Output: First element in TreeSet: apple
    // Last element in TreeSet: fig
    public void exampleFirstLast() {
        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.add("apple");
        treeSet.add("banana");
        treeSet.add("cherry");
        treeSet.add("date");
        treeSet.add("fig");

        log.info("First element in TreeSet: {}", treeSet.first());
        log.info("Last element in TreeSet: {}", treeSet.last());
    }

    // Example of using higher and lower methods in a TreeSet
    // Output:
    // Element higher than 'banana': cherry
    // Element lower than 'cherry': banana
    public void exampleHigherLower() {
        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.add("apple");
        treeSet.add("banana");
        treeSet.add("cherry");
        treeSet.add("date");
        treeSet.add("fig");

        log.info("Element higher than 'banana': {}", treeSet.higher("banana"));
        log.info("Element lower than 'cherry': {}", treeSet.lower("cherry"));
    }

    // Example of getting a navigable set from a TreeSet
    // Output: Navigable Set in descending order:
    // Element: fig
    // Element: date
    // Element: cherry
    // Element: banana
    // Element: apple
    public void exampleNavigableSet() {
        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.add("apple");
        treeSet.add("banana");
        treeSet.add("cherry");
        treeSet.add("date");
        treeSet.add("fig");

        log.info("Navigable Set in descending order:");
        for (String element : treeSet.descendingSet()) {
            log.info("Element: {}", element);
        }
    }

    // Example of natural ordering in a TreeSet
    // Output: TreeSet with natural ordering: [apple, banana, cherry]
    public void exampleNaturalOrdering() {
        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.add("banana");
        treeSet.add("apple");
        treeSet.add("cherry");

        log.info("TreeSet with natural ordering: {}", treeSet);
    }

    // Example of custom ordering in a TreeSet using a Comparator
    // Output: TreeSet with custom ordering (by length): [date, apple, banana, cherry]
    public void exampleCustomOrdering() {
        // Custom comparator to order strings by length
        // both are the same notations
//        Comparator<String> lengthComparator = (s1, s2) -> Integer.compare(s1.length(), s2.length());
        Comparator<String> lengthComparator = Comparator.comparingInt(String::length);

        TreeSet<String> treeSet = new TreeSet<>(lengthComparator);
        treeSet.add("banana");
        treeSet.add("apple");
        treeSet.add("cherry");
        treeSet.add("date");

        log.info("TreeSet with custom ordering (by length): {}", treeSet);
    }
}

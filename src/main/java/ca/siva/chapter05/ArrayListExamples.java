package ca.siva.chapter05;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
NOTE:
1) ArrayList can expand its size when it reaches the initial size limit. To define initial size as below:
    new ArrayList<>(1)
2) Diamond operator <>, cannot be used on the left-hand side of the assignment, as the code won't compile.
3) Also, you cannot sort an ImmutableList(List<>) but you can sort an arrayList though.
4) When unboxing for Integer, compiler automatically calls .intValue() method to retrieve the primitive value.
5) The thenComparing method is used in Java to build compound comparators. When sorting collections, thenComparing allows you to specify multiple levels of sorting criteria. If two elements are equal based on the primary comparator, thenComparing allows you to specify secondary comparators to break the tie.

 */
@Slf4j
public class ArrayListExamples {

    public static void main(String[] args) {
        ArrayListExamples examples = new ArrayListExamples();

        examples.exampleRemoveByIndex();
        examples.exampleRemoveByValue();
        examples.exampleAddElements();
        examples.exampleIterateList();
        examples.exampleSortList();
        examples.exampleSearchElement();
        examples.exampleConvertArrayToList();
        examples.exampleConvertListToArray();
        examples.exampleClearList();
    }

    // Example of removing an element by index
    public void exampleRemoveByIndex() {
        List<String> list = new ArrayList<>(Arrays.asList("Apple", "Banana", "Cherry", "Date"));
        log.info("Original list: {}", list);

        String removedElement = list.remove(2);
        log.info("Removed element at index 2: {}", removedElement);
        log.info("List after removal: {}", list);
    }

    // Example of removing an element by value
    public void exampleRemoveByValue() {
        List<String> list = new ArrayList<>(Arrays.asList("Apple", "Banana", "Cherry", "Date"));
        log.info("Original list: {}", list);

        boolean isRemoved = list.remove("Banana");
        log.info("Element 'Banana' removed: {}", isRemoved);
        log.info("List after removal: {}", list);
    }

    // Example of adding elements to an ArrayList
    public void exampleAddElements() {
        List<String> list = new ArrayList<>();
        list.add("Apple");
        list.add("Banana");
        list.add("Cherry");
        List<Long> longList = new ArrayList<>();
        longList.add((long)1);
        longList.add((long)1.5);
        //This won't compile
        //longList.add(1.2);
        log.info("List after adding elements: {}, longList:{}", list, longList);
    }

    // Example of iterating over an ArrayList
    public void exampleIterateList() {
        List<String> list = new ArrayList<>(Arrays.asList("Apple", "Banana", "Cherry", "Date"));
        log.info("Iterating over list:");
        for (String fruit : list) {
            log.info(fruit);
        }
    }

    // Example of sorting an ArrayList
    public void exampleSortList() {
        List<String> list = new ArrayList<>(Arrays.asList("Banana", "Apple", "Date", "Cherry"));
        log.info("List before sorting: {}", list);
        Collections.sort(list);
        log.info("List after sorting: {}", list);
    }

    // Example of searching for an element in an ArrayList
    public void exampleSearchElement() {
        List<String> list = new ArrayList<>(Arrays.asList("Apple", "Banana", "Cherry", "Date"));
        boolean containsCherry = list.contains("Cherry");
        log.info("List contains 'Cherry': {}", containsCherry);
    }

    // Example of converting an array to an ArrayList
    public void exampleConvertArrayToList() {
        String[] array = {"Apple", "Banana", "Cherry", "Date"};
        List<String> list = new ArrayList<>(Arrays.asList(array));
        log.info("Converted array to list: {}", list);
    }

    // Example of converting an ArrayList to an array
    public void exampleConvertListToArray() {
        List<String> list = new ArrayList<>(Arrays.asList("Apple", "Banana", "Cherry", "Date"));
        String[] array = list.toArray(new String[0]);
        log.info("Converted list to array: {}", Arrays.toString(array));
    }

    // Example of clearing all elements in an ArrayList
    public void exampleClearList() {
        List<String> list = new ArrayList<>(Arrays.asList("Apple", "Banana", "Cherry", "Date"));
        log.info("List before clearing: {}", list);
        list.clear();
        log.info("List after clearing: {}", list);
    }
}

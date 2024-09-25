package ca.siva.ch05_collections;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Comparator;

/*
NOTE:
1) varargs cannot be used in variable declaration as it can be used only in method declarations.
2) When Arrays.binarySearch is executed for a number that's missing, it returns (-index-1)
3) The Arrays.mismatch method is used to find the first position where two arrays differ.
    If the arrays are identical up to the length of the shorter array, the method returns -1.
4) Arrays.compare to lexicographically compare array from starting element if mismatch occurs,
    the return output value follows below rules:
    i) Negative integer if the first array is less than the second array.
    ii) Zero if both arrays are equal.
    iii) Positive integer if the first array is greater than the second array.
5) You don't have var[] for varargs, by default declaring var is enough for arrays as well to infer the type during the compile-time.
6) With Arrays.asList("a", "b") you can change the value at the index, but you cannot insert elements to a primitive array backed.
 */
@Slf4j
public class ArraysExample {

    public static void main(String[] args) {
        ArraysExample examples = new ArraysExample();

        examples.exampleInitializeArray();
        examples.exampleArrayIteration();
        examples.exampleArraySort();
        examples.exampleArraySearch();
        examples.exampleArrayCopy();
        examples.exampleMultidimensionalArray();
        examples.exampleArrayMismatch();
        examples.exampleArrayCompare();
        examples.exampleSortWithComparator();
        examples.exampleSortByLength();
        examples.exampleCustomObjectSort();
        examples.exampleSortWithNaturalOrder();
        examples.exampleSortWithReversedOrder();
        examples.exampleSortByCustomCondition();
        examples.exampleBinarySearchWithComparator();
        examples.exampleBinarySearchDescendingOrder();
    }

    // Example of initializing an array
    // Output: Array elements: [1, 2, 3, 4, 5]
    public void exampleInitializeArray() {
        int[] array = {1, 2, 3, 4, 5};
        log.info("Array elements: {}", Arrays.toString(array));
    }

    // Example of iterating over an array
    // Output: Iterating over array:
    // Element: 1
    // Element: 2
    // Element: 3
    // Element: 4
    // Element: 5
    public void exampleArrayIteration() {
        int[] array = {1, 2, 3, 4, 5};
        log.info("Iterating over array:");
        for (int element : array) {
            log.info("Element: {}", element);
        }
    }

    // Example of sorting an array
    // Output: Array before sorting: [5, 3, 4, 1, 2]
    // Array after sorting: [1, 2, 3, 4, 5]
    public void exampleArraySort() {
        int[] array = {5, 3, 4, 1, 2};
        log.info("Array before sorting: {}", Arrays.toString(array));
        Arrays.sort(array);
        log.info("Array after sorting: {}", Arrays.toString(array));
    }

    // Example of searching in an array
    // Output: Array: [1, 2, 3, 4, 5]
    // Element 3 found at index: 2, returns a -ve number if the element is not found
    // If the element is not found, it returns -(insertion point + 1).
    // The insertion point is the index where the element would be inserted to maintain sorted order.
    public void exampleArraySearch() {
        int[] array = {1, 2, 3, 4, 5};
        int index = Arrays.binarySearch(array, 3);
        log.info("Array: {}", Arrays.toString(array));
        log.info("Element 3 found at index: {}", index);
    }

    // Example of copying an array
    // Output: Original array: [1, 2, 3, 4, 5]
    // Copied array: [1, 2, 3, 4, 5, 0, 0, 0, 0, 0]
    public void exampleArrayCopy() {
        int[] originalArray = {1, 2, 3, 4, 5};
        int[] copiedArray = Arrays.copyOf(originalArray, 10);
        log.info("Original array: {}", Arrays.toString(originalArray));
        log.info("Copied array: {}", Arrays.toString(copiedArray));
    }

    // Example of using a multidimensional array
    // Output: Multidimensional array:
    // Row 0: [1, 2, 3]
    // Row 1: [4, 5, 6]
    // Row 2: [7, 8, 9]
    public void exampleMultidimensionalArray() {
        int[][] multiArray = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        log.info("Multidimensional array:");
        for (int i = 0; i < multiArray.length; i++) {
            log.info("Row {}: {}", i, Arrays.toString(multiArray[i]));
        }
    }

    // Example of using Arrays.mismatch
    // Output: Array1: [1, 2, 3, 4, 5]
    // Array2: [1, 2, 3, 4, 6]
    // First mismatch at index: 4, if arrays are identical it returns -1
    public void exampleArrayMismatch() {
        int[] array1 = {1, 2, 3, 4, 5};
        int[] array2 = {1, 2, 3, 4, 6};
        int mismatchIndex = Arrays.mismatch(array1, array2);
        log.info("Array1: {}", Arrays.toString(array1));
        log.info("Array2: {}", Arrays.toString(array2));
        log.info("First mismatch at index: {}", mismatchIndex);
    }

    // Example of using Arrays.compare
    // Output: Array1: [1, 2, 3, 4, 5]
    // Array2: [1, 2, 3, 4, 6]
    // Comparison result: -1
    /*
    A negative integer if the first array is lexicographically less than the second array.
    Zero if both arrays are equal.
    A positive integer if the first array is lexicographically greater than the second array.
     */
    public void exampleArrayCompare() {
        int[] array1 = {1, 2, 3, 4, 5};
        int[] array2 = {1, 2, 3, 4, 6};
        int compareResult = Arrays.compare(array1, array2);
        log.info("Array1: {}", Arrays.toString(array1));
        log.info("Array2: {}", Arrays.toString(array2));
        log.info("Comparison result: {}", compareResult);
    }

    // Example of sorting an array with a custom comparator (reverse order)
    // Output: Array before custom sorting: [5, 3, 4, 1, 2]
    // Array after custom sorting: [5, 4, 3, 2, 1]
    public void exampleSortWithComparator() {
        Integer[] array = {5, 3, 4, 1, 2};
        log.info("Array before custom sorting: {}", Arrays.toString(array));
        Arrays.sort(array, Comparator.reverseOrder());
        log.info("Array after custom sorting: {}", Arrays.toString(array));
    }

    // Example of sorting an array of strings by length using a custom comparator
    // Output: Array before sorting by length: [apple, banana, fig, date]
    // Array after sorting by length: [fig, date, apple, banana]
    public void exampleSortByLength() {
        String[] array = {"apple", "banana", "fig", "date"};
        log.info("Array before sorting by length: {}", Arrays.toString(array));
        Arrays.sort(array, Comparator.comparingInt(String::length));
        log.info("Array after sorting by length: {}", Arrays.toString(array));
    }

    // Example of sorting an array of custom objects using a comparator
    // Output: Array before custom object sorting: [Person{name='John', age=30}, Person{name='Jane', age=25}, Person{name='Doe', age=35}]
    // Array after custom object sorting by age: [Person{name='Jane', age=25}, Person{name='John', age=30}, Person{name='Doe', age=35}]
    public void exampleCustomObjectSort() {
        Person[] people = {
                new Person("John", 30),
                new Person("Jane", 25),
                new Person("Doe", 35)
        };
        log.info("Array before custom object sorting: {}", Arrays.toString(people));
        Arrays.sort(people, Comparator.comparingInt(Person::getAge));
        log.info("Array after custom object sorting by age: {}", Arrays.toString(people));
    }

    // Example of sorting an array of strings using natural order
    // Output: Array before natural order sorting: [banana, apple, cherry, date]
    // Array after natural order sorting: [apple, banana, cherry, date]
    public void exampleSortWithNaturalOrder() {
        String[] array = {"banana", "apple", "cherry", "date"};
        log.info("Array before natural order sorting: {}", Arrays.toString(array));
        Arrays.sort(array, Comparator.naturalOrder());
        log.info("Array after natural order sorting: {}", Arrays.toString(array));
    }

    // Example of sorting an array of strings using reversed order
    // Output: Array before reversed order sorting: [banana, apple, cherry, date]
    // Array after reversed order sorting: [date, cherry, banana, apple]
    public void exampleSortWithReversedOrder() {
        String[] array = {"banana", "apple", "cherry", "date"};
        log.info("Array before reversed order sorting: {}", Arrays.toString(array));
        Arrays.sort(array, Comparator.reverseOrder());
        log.info("Array after reversed order sorting: {}", Arrays.toString(array));
    }

    // Example of sorting an array of strings by custom condition (by length, then by natural order)
    // Output: Array before custom condition sorting: [banana, fig, date, apple]
    // Array after custom condition sorting: [fig, date, apple, banana]
    public void exampleSortByCustomCondition() {
        String[] array = {"banana", "fig", "date", "apple"};
        log.info("Array before custom condition sorting: {}", Arrays.toString(array));
        Arrays.sort(array, Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder()));
        log.info("Array after custom condition sorting: {}", Arrays.toString(array));
    }

    // Example of using binarySearch with a custom comparator
    // Output: Array: [5, 3, 4, 1, 2]
    // Sorted Array with custom comparator: [5, 4, 3, 2, 1]
    // Element 3 found at index: 2
    public void exampleBinarySearchWithComparator() {
        Integer[] array = {5, 3, 4, 1, 2};
        log.info("Array: {}", Arrays.toString(array));
        Arrays.sort(array, Comparator.reverseOrder());
        log.info("Sorted Array with custom comparator: {}", Arrays.toString(array));
        int index = Arrays.binarySearch(array, 3, Comparator.reverseOrder());
        log.info("Element 3 found at index: {}", index);
    }

    // Example of using binarySearch on a descending ordered array
    // Output: Array before sorting in descending order: [1, 3, 5, 2, 4]
    // Array after sorting in descending order: [5, 4, 3, 2, 1]
    // Element 3 found at index: 2
    public void exampleBinarySearchDescendingOrder() {
        Integer[] array = {1, 3, 5, 2, 4};
        log.info("Array before sorting in descending order: {}", Arrays.toString(array));
        Arrays.sort(array, Comparator.reverseOrder());
        log.info("Array after sorting in descending order: {}", Arrays.toString(array));
        int index = Arrays.binarySearch(array, 3, Comparator.reverseOrder());
        log.info("Element 3 found at index: {}", index);
    }

    // Custom class for demonstrating object sorting
    static class Person {
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        @Override
        public String toString() {
            return "Person{name='" + name + "', age=" + age + '}';
        }
    }
}

package ca.siva.chapter06;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.*;

@Slf4j
public class StreamExamples {

    /**
     * Example of filter operation.
     * Input: List of names ["Alice", "Bob", "Charlie", "David"]
     * Output: List of names that start with "A" ["Alice"]
     */
    public static void filterExample() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        List<String> result = names.stream().filter(name -> name.startsWith("A")).collect(Collectors.toList());
        log.info("Filter result: {}", result);
    }

    /**
     * Example of map operation.
     * Input: List of names ["Alice", "Bob", "Charlie", "David"]
     * Output: List of lengths of each name [5, 3, 7, 5]
     */
    public static void mapExample() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        List<Integer> result = names.stream().map(String::length).collect(Collectors.toList());
        log.info("Map result: {}", result);
    }

    /**
     * Example of mapToInt operation.
     * Input: List of names ["Alice", "Bob", "Charlie", "David"]
     * Output: Array of lengths of each name [5, 3, 7, 5]
     */
    public static void mapToIntExample() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        int[] result = names.stream().mapToInt(String::length).toArray();
        log.info("MapToInt result: {}", Arrays.toString(result));
    }

    /**
     * Example of mapToLong operation.
     * Input: List of names ["Alice", "Bob", "Charlie", "David"]
     * Output: Array of lengths of each name as long [5, 3, 7, 5]
     */
    public static void mapToLongExample() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        long[] result = names.stream().mapToLong(String::length).toArray();
        log.info("MapToLong result: {}", Arrays.toString(result));
    }

    /**
     * Example of mapToDouble operation.
     * Input: List of names ["Alice", "Bob", "Charlie", "David"]
     * Output: Array of lengths of each name as double [5.0, 3.0, 7.0, 5.0]
     */
    public static void mapToDoubleExample() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        double[] result = names.stream().mapToDouble(String::length).toArray();
        log.info("MapToDouble result: {}", Arrays.toString(result));
    }

    /**
     * Example of flatMap operation.
     * Input: List of lists [["one", "two"], ["three", "four"]]
     * Output: Flattened list ["one", "two", "three", "four"]
     */
    public static void flatMapExample() {
        List<List<String>> nestedList = Arrays.asList(Arrays.asList("one", "two"), Arrays.asList("three", "four"));
        List<String> result = nestedList.stream().flatMap(List::stream).collect(Collectors.toList());
        log.info("FlatMap result: {}", result);
    }

    /**
     * Example of flatMapToInt operation.
     * Input: List of names ["Alice", "Bob", "Charlie", "David"]
     * Output: Flattened array of each name's length and double of the length [5, 10, 3, 6, 7, 14, 5, 10]
     */
    public static void flatMapToIntExample() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        int[] result = names.stream().flatMapToInt(name -> IntStream.of(name.length(), name.length() * 2)).toArray();
        log.info("FlatMapToInt result: {}", Arrays.toString(result));
    }

    /**
     * Example of flatMapToLong operation.
     * Input: List of names ["Alice", "Bob", "Charlie", "David"]
     * Output: Flattened array of each name's length and double of the length as long [5, 10, 3, 6, 7, 14, 5, 10]
     */
    public static void flatMapToLongExample() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        long[] result = names.stream().flatMapToLong(name -> LongStream.of(name.length(), name.length() * 2L)).toArray();
        log.info("FlatMapToLong result: {}", Arrays.toString(result));
    }

    /**
     * Example of flatMapToDouble operation.
     * Input: List of names ["Alice", "Bob", "Charlie", "David"]
     * Output: Flattened array of each name's length and double of the length as double [5.0, 10.0, 3.0, 6.0, 7.0, 14.0, 5.0, 10.0]
     */
    public static void flatMapToDoubleExample() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        double[] result = names.stream().flatMapToDouble(name -> DoubleStream.of(name.length(), name.length() * 2.0)).toArray();
        log.info("FlatMapToDouble result: {}", Arrays.toString(result));
    }

    /**
     * Example of distinct operation.
     * Input: List of names ["Alice", "Bob", "Alice", "David"]
     * Output: List of distinct names ["Alice", "Bob", "David"]
     */
    public static void distinctExample() {
        List<String> names = Arrays.asList("Alice", "Bob", "Alice", "David");
        List<String> result = names.stream().distinct().collect(Collectors.toList());
        log.info("Distinct result: {}", result);
    }

    /**
     * Example of sorted operation.
     * Input: List of names ["David", "Bob", "Alice", "Charlie"]
     * Output: List of names sorted in natural order ["Alice", "Bob", "Charlie", "David"]
     */
    public static void sortedExample() {
        List<String> names = Arrays.asList("David", "Bob", "Alice", "Charlie");
        List<String> result = names.stream().sorted().collect(Collectors.toList());
        log.info("Sorted result: {}", result);
    }

    /**
     * Example of sorted with comparator operation.
     * Input: List of names ["David", "Bob", "Alice", "Charlie"]
     * Output: List of names sorted in reverse order ["David", "Charlie", "Bob", "Alice"]
     */
    public static void sortedWithComparatorExample() {
        List<String> names = Arrays.asList("David", "Bob", "Alice", "Charlie");
        List<String> result = names.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        log.info("Sorted with Comparator result: {}", result);
    }

    /**
     * Example of peek operation.
     * Input: List of names ["Alice", "Bob", "Charlie", "David"]
     * Output: Logs each name during the stream processing, then collects the names into a list
     */
    public static void peekExample() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        List<String> result = names.stream().peek(name -> log.info("Peek: {}", name)).collect(Collectors.toList());
        log.info("Peek result: {}", result);
    }

    /**
     * Example of limit operation.
     * Input: List of names ["Alice", "Bob", "Charlie", "David"]
     * Output: List of the first 2 names ["Alice", "Bob"]
     */
    public static void limitExample() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        List<String> result = names.stream().limit(2).collect(Collectors.toList());
        log.info("Limit result: {}", result);
    }

    /**
     * Example of skip operation.
     * Input: List of names ["Alice", "Bob", "Charlie", "David"]
     * Output: List of names after skipping the first 2 ["Charlie", "David"]
     */
    public static void skipExample() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        List<String> result = names.stream().skip(2).collect(Collectors.toList());
        log.info("Skip result: {}", result);
    }

    /**
     * Example of takeWhile operation.
     * Input: List of numbers [1, 2, 3, 4, 5, 1, 2]
     * Output: List of numbers taken while they are less than 4 [1, 2, 3]
     */
    public static void takeWhileExample() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 1, 2);
        List<Integer> result = numbers.stream().takeWhile(n -> n < 4).collect(Collectors.toList());
        log.info("TakeWhile result: {}", result);
    }

    /**
     * Example of dropWhile operation.
     * Input: List of numbers [1, 2, 3, 4, 5, 1, 2]
     * Output: List of numbers after dropping those less than 4 [4, 5, 1, 2]
     */
    public static void dropWhileExample() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 1, 2);
        List<Integer> result = numbers.stream().dropWhile(n -> n < 4).collect(Collectors.toList());
        log.info("DropWhile result: {}", result);
    }

    /**
     * Example of forEach operation.
     * Input: List of names ["Alice", "Bob", "Charlie", "David"]
     * Output: Logs each name in the stream
     */
    public static void forEachExample() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        names.stream().forEach(name -> log.info("ForEach: {}", name));
    }

    /**
     * Example of forEachOrdered operation.
     * Input: List of names ["Alice", "Bob", "Charlie", "David"]
     * Output: Logs each name in the stream in encounter order
     */
    public static void forEachOrderedExample() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        names.stream().forEachOrdered(name -> log.info("ForEachOrdered: {}", name));
    }

    /**
     * Example of toArray operation.
     * Input: List of names ["Alice", "Bob", "Charlie", "David"]
     * Output: Array of names ["Alice", "Bob", "Charlie", "David"]
     */
    public static void toArrayExample() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        Object[] result = names.stream().toArray();
        log.info("ToArray result: {}", Arrays.toString(result));
    }

    /**
     * Example of toArray with generator operation.
     * Input: List of names ["Alice", "Bob", "Charlie", "David"]
     * Output: Array of names ["Alice", "Bob", "Charlie", "David"]
     */
    public static void toArrayWithGeneratorExample() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        String[] result = names.stream().toArray(String[]::new);
        log.info("ToArray with generator result: {}", Arrays.toString(result));
    }

    /**
     * Example of reduce with identity operation.
     * Input: List of numbers [1, 2, 3, 4, 5]
     * Output: Sum of the numbers 15
     */
    public static void reduceWithIdentityExample() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        int result = numbers.stream().reduce(0, Integer::sum);
        log.info("Reduce with identity result: {}", result);
    }

    /**
     * Example of reduce without identity operation.
     * Input: List of numbers [1, 2, 3, 4, 5]
     * Output: Optional containing the sum of the numbers Optional[15]
     */
    public static void reduceWithoutIdentityExample() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        Optional<Integer> result = numbers.stream().reduce(Integer::sum);
        result.ifPresent(res -> log.info("Reduce without identity result: {}", res));
    }

    /**
     * Example of reduce with combiner operation.
     * Input: List of numbers [1, 2, 3, 4, 5]
     * Output: Sum of the numbers 15
     */
    public static void reduceWithCombinerExample() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        int result = numbers.stream().reduce(0, Integer::sum, Integer::sum);
        log.info("Reduce with combiner result: {}", result);
    }

    /**
     * Example of collect with supplier operation.
     * Input: List of names ["Alice", "Bob", "Charlie", "David"]
     * Output: List of names ["Alice", "Bob", "Charlie", "David"]
     */
    public static void collectWithSupplierExample() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        List<String> result = names.stream().collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        log.info("Collect with supplier result: {}", result);
    }

    /**
     * Example of collect with collector operation.
     * Input: List of names ["Alice", "Bob", "Charlie", "David"]
     * Output: List of names ["Alice", "Bob", "Charlie", "David"]
     */
    public static void collectWithCollectorExample() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        List<String> result = names.stream().collect(Collectors.toList());
        log.info("Collect with collector result: {}", result);
    }

    /**
     * Example of toList operation.
     * Input: List of names ["Alice", "Bob", "Charlie", "David"]
     * Output: List of names ["Alice", "Bob", "Charlie", "David"]
     */
    public static void toListExample() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        List<String> result = names.stream().toList();
        log.info("ToList result: {}", result);
    }

    /**
     * Example of min operation.
     * Input: List of names ["Alice", "Bob", "Charlie", "David"]
     * Output: Optional containing the minimum name by natural order Optional["Alice"]
     */
    public static void minExample() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        Optional<String> result = names.stream().min(Comparator.naturalOrder());
        result.ifPresent(res -> log.info("Min result: {}", res));
    }

    /**
     * Example of max operation.
     * Input: List of names ["Alice", "Bob", "Charlie", "David"]
     * Output: Optional containing the maximum name by natural order Optional["David"]
     */
    public static void maxExample() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        Optional<String> result = names.stream().max(Comparator.naturalOrder());
        result.ifPresent(res -> log.info("Max result: {}", res));
    }

    /**
     * Example of count operation.
     * Input: List of names ["Alice", "Bob", "Charlie", "David"]
     * Output: Count of names 4
     */
    public static void countExample() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        long result = names.stream().count();
        log.info("Count result: {}", result);
    }

    /**
     * Example of anyMatch operation.
     * Input: List of names ["Alice", "Bob", "Charlie", "David"]
     * Output: True if any name starts with "A" true
     */
    public static void anyMatchExample() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        boolean result = names.stream().anyMatch(name -> name.startsWith("A"));
        log.info("AnyMatch result: {}", result);
    }

    /**
     * Example of allMatch operation.
     * Input: List of names ["Alice", "Bob", "Charlie", "David"]
     * Output: True if all names have length greater than 2 true
     */
    public static void allMatchExample() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        boolean result = names.stream().allMatch(name -> name.length() > 2);
        log.info("AllMatch result: {}", result);
    }

    /**
     * Example of noneMatch operation.
     * Input: List of names ["Alice", "Bob", "Charlie", "David"]
     * Output: True if no name has length greater than 5 true
     */
    public static void noneMatchExample() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        boolean result = names.stream().noneMatch(name -> name.length() > 5);
        log.info("NoneMatch result: {}", result);
    }

    /**
     * Example of findFirst operation.
     * Input: List of names ["Alice", "Bob", "Charlie", "David"]
     * Output: Optional containing the first name Optional["Alice"]
     */
    public static void findFirstExample() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        Optional<String> result = names.stream().findFirst();
        result.ifPresent(res -> log.info("FindFirst result: {}", res));
    }

    /**
     * Example of findAny operation.
     * Input: List of names ["Alice", "Bob", "Charlie", "David"]
     * Output: Optional containing any name (non-deterministic) Optional["Alice"]
     */
    public static void findAnyExample() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        Optional<String> result = names.stream().findAny();
        result.ifPresent(res -> log.info("FindAny result: {}", res));
    }

    /**
     * Example of concat operation.
     * Input: List 1 ["Alice", "Bob"], List 2 ["Charlie", "David"]
     * Output: Concatenated list ["Alice", "Bob", "Charlie", "David"]
     */
    public static void concatExample() {
        List<String> list1 = Arrays.asList("Alice", "Bob");
        List<String> list2 = Arrays.asList("Charlie", "David");
        List<String> result = Stream.concat(list1.stream(), list2.stream()).collect(Collectors.toList());
        log.info("Concat result: {}", result);
    }

    /**
     * Example of using Spliterator with trySplit and tryAdvance methods.
     * Input: List of names ["Alice", "Bob", "Charlie", "David"]
     * Output: Logs each name processed by tryAdvance and demonstrates splitting work using trySplit
     */
    public static void spliteratorExample() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

        Spliterator<String> spliterator = names.spliterator();
        Spliterator<String> secondHalf = spliterator.trySplit();

        log.info("Processing first half with tryAdvance:");
        while (spliterator.tryAdvance(name -> log.info("First half: {}", name)));

        log.info("Processing second half with tryAdvance:");
        if (secondHalf != null) {
            while (secondHalf.tryAdvance(name -> log.info("Second half: {}", name)));
        }
    }

    /**
     * Example of IntegerSummaryStatistics.
     * Input: List of integers [3, 5, 7, 9, 11]
     * Output: Logs count, sum, min, average, and max of the list
     */
    public static void intSummaryStatisticsExample() {
        List<Integer> numbers = Arrays.asList(3, 5, 7, 9, 11);

        IntSummaryStatistics stats = numbers.stream()
                .mapToInt(x -> x)
                .summaryStatistics();

        log.info("Count: {}", stats.getCount());
        log.info("Sum: {}", stats.getSum());
        log.info("Min: {}", stats.getMin());
        log.info("Average: {}", stats.getAverage());
        log.info("Max: {}", stats.getMax());
    }

    public static void main(String[] args) {
        filterExample();
        mapExample();
        mapToIntExample();
        mapToLongExample();
        mapToDoubleExample();
        flatMapExample();
        flatMapToIntExample();
        flatMapToLongExample();
        flatMapToDoubleExample();
        distinctExample();
        sortedExample();
        sortedWithComparatorExample();
        peekExample();
        limitExample();
        skipExample();
        takeWhileExample();
        dropWhileExample();
        forEachExample();
        forEachOrderedExample();
        toArrayExample();
        toArrayWithGeneratorExample();
        reduceWithIdentityExample();
        reduceWithoutIdentityExample();
        reduceWithCombinerExample();
        collectWithSupplierExample();
        collectWithCollectorExample();
        toListExample();
        minExample();
        maxExample();
        countExample();
        anyMatchExample();
        allMatchExample();
        noneMatchExample();
        findFirstExample();
        findAnyExample();
        concatExample();
        spliteratorExample();
        intSummaryStatisticsExample();
    }
}

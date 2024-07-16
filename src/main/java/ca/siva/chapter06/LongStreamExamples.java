package ca.siva.chapter06;

import java.util.*;
import java.util.stream.*;

public class LongStreamExamples {
    public static void main(String[] args) {
        filterExample();
        mapExample();
        mapToObjExample();
        mapToIntExample();
        mapToDoubleExample();
        flatMapExample();
        mapMultiExample();
        distinctExample();
        sortedExample();
        peekExample();
        limitExample();
        skipExample();
        takeWhileExample();
        dropWhileExample();
        forEachExample();
        forEachOrderedExample();
        toArrayExample();
        reduceWithIdentityExample();
        reduceWithoutIdentityExample();
        collectExample();
        sumExample();
        minExample();
        maxExample();
        countExample();
        averageExample();
        summaryStatisticsExample();
        anyMatchExample();
        allMatchExample();
        noneMatchExample();
        findFirstExample();
        findAnyExample();
        asDoubleStreamExample();
        boxedExample();
    }

    /**
     * Example of filter operation.
     * Input: Range of numbers 1 to 10
     * Output: Array of even numbers [2, 4, 6, 8]
     */
    public static void filterExample() {
        long[] result = LongStream.range(1, 10)
                .filter(n -> n % 2 == 0)
                .toArray();
        System.out.println("Filter result: " + Arrays.toString(result));
    }

    /**
     * Example of map operation.
     * Input: Range of numbers 1 to 4
     * Output: Array of squared numbers [1, 4, 9, 16]
     */
    public static void mapExample() {
        long[] result = LongStream.range(1, 5)
                .map(n -> n * n)
                .toArray();
        System.out.println("Map result: " + Arrays.toString(result));
    }

    /**
     * Example of mapToObj operation.
     * Input: Range of numbers 1 to 4
     * Output: List of strings ["1", "2", "3", "4"]
     */
    public static void mapToObjExample() {
        List<String> result = LongStream.range(1, 5)
                .mapToObj(Long::toString)
                .collect(Collectors.toList());
        System.out.println("MapToObj result: " + result);
    }

    /**
     * Example of mapToInt operation.
     * Input: Range of numbers 1 to 4
     * Output: Array of integers [1, 2, 3, 4]
     */
    public static void mapToIntExample() {
        int[] result = LongStream.range(1, 5)
                .mapToInt((long x) -> (int) x)
                .toArray();
        System.out.println("MapToInt result: " + Arrays.toString(result));
    }

    /**
     * Example of mapToDouble operation.
     * Input: Range of numbers 1 to 4
     * Output: Array of doubles [1.0, 2.0, 3.0, 4.0]
     */
    public static void mapToDoubleExample() {
        double[] result = LongStream.range(1, 5)
                .mapToDouble((long l) -> (double) l)
                .toArray();
        System.out.println("MapToDouble result: " + Arrays.toString(result));
    }

    /**
     * Example of flatMap operation.
     * Input: Range of numbers 1 to 3
     * Output: Array with each number and its square [1, 1, 2, 4, 3, 9]
     */
    public static void flatMapExample() {
        long[] result = LongStream.range(1, 4)
                .flatMap(n -> LongStream.of(n, n * n))
                .toArray();
        System.out.println("FlatMap result: " + Arrays.toString(result));
    }

    /**
     * Example of mapMulti operation.
     * Input: Range of numbers 1 to 3
     * Output: Array with each number and its square [1, 1, 2, 4, 3, 9]
     */
    public static void mapMultiExample() {
        long[] result = LongStream.range(1, 4)
                .mapMulti((value, consumer) -> {
                    consumer.accept(value);
                    consumer.accept(value * value);
                })
                .toArray();
        System.out.println("MapMulti result: " + Arrays.toString(result));
    }

    /**
     * Example of distinct operation.
     * Input: Array of numbers [1, 2, 2, 3, 3, 3]
     * Output: Array of distinct numbers [1, 2, 3]
     */
    public static void distinctExample() {
        long[] result = LongStream.of(1, 2, 2, 3, 3, 3)
                .distinct()
                .toArray();
        System.out.println("Distinct result: " + Arrays.toString(result));
    }

    /**
     * Example of sorted operation.
     * Input: Array of numbers [5, 3, 1, 4, 2]
     * Output: Array of sorted numbers [1, 2, 3, 4, 5]
     */
    public static void sortedExample() {
        long[] result = LongStream.of(5, 3, 1, 4, 2)
                .sorted()
                .toArray();
        System.out.println("Sorted result: " + Arrays.toString(result));
    }

    /**
     * Example of peek operation.
     * Input: Range of numbers 1 to 4
     * Output: Logs each number and returns array [1, 2, 3, 4]
     */
    public static void peekExample() {
        long[] result = LongStream.range(1, 5)
                .peek(System.out::println)
                .toArray();
        System.out.println("Peek result: " + Arrays.toString(result));
    }

    /**
     * Example of limit operation.
     * Input: Range of numbers 1 to 9
     * Output: Array of limited numbers [1, 2, 3]
     */
    public static void limitExample() {
        long[] result = LongStream.range(1, 10)
                .limit(3)
                .toArray();
        System.out.println("Limit result: " + Arrays.toString(result));
    }

    /**
     * Example of skip operation.
     * Input: Range of numbers 1 to 9
     * Output: Array of numbers after skipping first 3 [4, 5, 6, 7, 8, 9]
     */
    public static void skipExample() {
        long[] result = LongStream.range(1, 10)
                .skip(3)
                .toArray();
        System.out.println("Skip result: " + Arrays.toString(result));
    }

    /**
     * Example of takeWhile operation.
     * Input: Range of numbers 1 to 9
     * Output: Array of numbers while condition is true [1, 2, 3, 4]
     */
    public static void takeWhileExample() {
        long[] result = LongStream.range(1, 10)
                .takeWhile(n -> n < 5)
                .toArray();
        System.out.println("TakeWhile result: " + Arrays.toString(result));
    }

    /**
     * Example of dropWhile operation.
     * Input: Range of numbers 1 to 9
     * Output: Array of numbers after condition is false [5, 6, 7, 8, 9]
     */
    public static void dropWhileExample() {
        long[] result = LongStream.range(1, 10)
                .dropWhile(n -> n < 5)
                .toArray();
        System.out.println("DropWhile result: " + Arrays.toString(result));
    }

    /**
     * Example of forEach operation.
     * Input: Range of numbers 1 to 4
     * Output: Logs each number
     */
    public static void forEachExample() {
        LongStream.range(1, 5)
                .forEach(System.out::println);
    }

    /**
     * Example of forEachOrdered operation.
     * Input: Range of numbers 1 to 4
     * Output: Logs each number in order
     */
    public static void forEachOrderedExample() {
        LongStream.range(1, 5)
                .parallel()
                .forEachOrdered(System.out::println);
    }

    /**
     * Example of toArray operation.
     * Input: Range of numbers 1 to 4
     * Output: Array of numbers [1, 2, 3, 4]
     */
    public static void toArrayExample() {
        long[] result = LongStream.range(1, 5)
                .toArray();
        System.out.println("ToArray result: " + Arrays.toString(result));
    }

    /**
     * Example of reduce with identity operation.
     * Input: Range of numbers 1 to 4
     * Output: Sum of numbers 10
     */
    public static void reduceWithIdentityExample() {
        long result = LongStream.range(1, 5)
                .reduce(0, Long::sum);
        System.out.println("Reduce with identity result: " + result);
    }

    /**
     * Example of reduce without identity operation.
     * Input: Range of numbers 1 to 4
     * Output: Sum of numbers OptionalLong[10]
     */
    public static void reduceWithoutIdentityExample() {
        OptionalLong result = LongStream.range(1, 5)
                .reduce(Long::sum);
        result.ifPresent(r -> System.out.println("Reduce without identity result: " + r));
    }

    /**
     * Example of collect operation.
     * Input: Range of numbers 1 to 4
     * Output: List of boxed numbers [1, 2, 3, 4]
     */
    public static void collectExample() {
        List<Long> result = LongStream.range(1, 5)
                .boxed()
                .collect(Collectors.toList());
        System.out.println("Collect result: " + result);
    }

    /**
     * Example of sum operation.
     * Input: Range of numbers 1 to 4
     * Output: Sum of numbers 10
     */
    public static void sumExample() {
        long result = LongStream.range(1, 5)
                .sum();
        System.out.println("Sum result: " + result);
    }

    /**
     * Example of min operation.
     * Input: Range of numbers 1 to 4
     * Output: Minimum number OptionalLong[1]
     */
    public static void minExample() {
        OptionalLong result = LongStream.range(1, 5)
                .min();
        result.ifPresent(r -> System.out.println("Min result: " + r));
    }

    /**
     * Example of max operation.
     * Input: Range of numbers 1 to 4
     * Output: Maximum number OptionalLong[4]
     */
    public static void maxExample() {
        OptionalLong result = LongStream.range(1, 5)
                .max();
        result.ifPresent(r -> System.out.println("Max result: " + r));
    }

    /**
     * Example of count operation.
     * Input: Range of numbers 1 to 4
     * Output: Count of numbers 4
     */
    public static void countExample() {
        long result = LongStream.range(1, 5)
                .count();
        System.out.println("Count result: " + result);
    }

    /**
     * Example of average operation.
     * Input: Range of numbers 1 to 4
     * Output: Average of numbers OptionalDouble[2.5]
     */
    public static void averageExample() {
        OptionalDouble result = LongStream.range(1, 5)
                .average();
        result.ifPresent(r -> System.out.println("Average result: " + r));
    }

    /**
     * Example of summaryStatistics operation.
     * Input: Range of numbers 1 to 4
     * Output: LongSummaryStatistics{count=4, sum=10, min=1, average=2.500000, max=4}
     */
    public static void summaryStatisticsExample() {
        LongSummaryStatistics stats = LongStream.range(1, 5)
                .summaryStatistics();
        System.out.println("SummaryStatistics result: " + stats);
    }

    /**
     * Example of anyMatch operation.
     * Input: Range of numbers 1 to 4
     * Output: true (since 3 is present in the range)
     */
    public static void anyMatchExample() {
        boolean result = LongStream.range(1, 5)
                .anyMatch(n -> n == 3);
        System.out.println("AnyMatch result: " + result);
    }

    /**
     * Example of allMatch operation.
     * Input: Range of numbers 1 to 4
     * Output: true (since all numbers are less than 5)
     */
    public static void allMatchExample() {
        boolean result = LongStream.range(1, 5)
                .allMatch(n -> n < 5);
        System.out.println("AllMatch result: " + result);
    }

    /**
     * Example of noneMatch operation.
     * Input: Range of numbers 1 to 4
     * Output: true (since no numbers are greater than 5)
     */
    public static void noneMatchExample() {
        boolean result = LongStream.range(1, 5)
                .noneMatch(n -> n > 5);
        System.out.println("NoneMatch result: " + result);
    }

    /**
     * Example of findFirst operation.
     * Input: Range of numbers 1 to 4
     * Output: OptionalLong[1]
     */
    public static void findFirstExample() {
        OptionalLong result = LongStream.range(1, 5)
                .findFirst();
        result.ifPresent(r -> System.out.println("FindFirst result: " + r));
    }

    /**
     * Example of findAny operation.
     * Input: Range of numbers 1 to 4
     * Output: OptionalLong[1] (could be any number in the range)
     */
    public static void findAnyExample() {
        OptionalLong result = LongStream.range(1, 5)
                .findAny();
        result.ifPresent(r -> System.out.println("FindAny result: " + r));
    }

    /**
     * Example of asDoubleStream operation.
     * Input: Range of numbers 1 to 4
     * Output: Array of doubles [1.0, 2.0, 3.0, 4.0]
     */
    public static void asDoubleStreamExample() {
        double[] result = LongStream.range(1, 5)
                .asDoubleStream()
                .toArray();
        System.out.println("AsDoubleStream result: " + Arrays.toString(result));
    }

    /**
     * Example of boxed operation.
     * Input: Range of numbers 1 to 4
     * Output: List of boxed numbers [1, 2, 3, 4]
     */
    public static void boxedExample() {
        List<Long> result = LongStream.range(1, 5)
                .boxed()
                .collect(Collectors.toList());
        System.out.println("Boxed result: " + result);
    }
}

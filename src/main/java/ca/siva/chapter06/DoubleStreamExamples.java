package ca.siva.chapter06;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.OptionalDouble;
import java.util.PrimitiveIterator;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DoubleStreamExamples {
    private static final Logger log = LoggerFactory.getLogger(DoubleStreamExamples.class);

    public static void main(String[] args) {
        filterExample();
        mapExample();
        mapToObjExample();
        mapToIntExample();
        mapToLongExample();
        flatMapExample();
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
        boxedExample();
    }

    /**
     * Example of filter operation.
     * Input: Range of numbers 1.0 to 10.0
     * Output: Filtered numbers greater than 5.0 [6.0, 7.0, 8.0, 9.0, 10.0]
     */
    public static void filterExample() {
        double[] result = DoubleStream.iterate(1.0, n -> n + 1.0)
                .limit(10)
                .filter(n -> n > 5.0)
                .toArray();
        log.info("Filter result: {}", Arrays.toString(result));
    }

    /**
     * Example of map operation.
     * Input: Range of numbers 1.0 to 5.0
     * Output: Each number squared [1.0, 4.0, 9.0, 16.0, 25.0]
     */
    public static void mapExample() {
        double[] result = DoubleStream.iterate(1.0, n -> n + 1.0)
                .limit(5)
                .map(n -> n * n)
                .toArray();
        log.info("Map result: {}", Arrays.toString(result));
    }

    /**
     * Example of mapToObj operation.
     * Input: Range of numbers 1.0 to 3.0
     * Output: Each number converted to string ["1.0", "2.0", "3.0"]
     */
    public static void mapToObjExample() {
        String[] result = DoubleStream.iterate(1.0, n -> n + 1.0)
                .limit(3)
                .mapToObj(Double::toString)
                .toArray(String[]::new);
        log.info("MapToObj result: {}", Arrays.toString(result));
    }

    /**
     * Example of mapToInt operation.
     * Input: Range of numbers 1.0 to 3.0
     * Output: Each number converted to int [1, 2, 3]
     */
    public static void mapToIntExample() {
        int[] result = DoubleStream.iterate(1.0, n -> n + 1.0)
                .limit(3)
                .mapToInt(n -> (int) n)
                .toArray();
        log.info("MapToInt result: {}", Arrays.toString(result));
    }

    /**
     * Example of mapToLong operation.
     * Input: Range of numbers 1.0 to 3.0
     * Output: Each number converted to long [1, 2, 3]
     */
    public static void mapToLongExample() {
        long[] result = DoubleStream.iterate(1.0, n -> n + 1.0)
                .limit(3)
                .mapToLong(n -> (long) n)
                .toArray();
        log.info("MapToLong result: {}", Arrays.toString(result));
    }

    /**
     * Example of flatMap operation.
     * Input: Range of numbers 1.0 to 3.0
     * Output: Each number repeated twice [1.0, 1.0, 2.0, 2.0, 3.0, 3.0]
     */
    public static void flatMapExample() {
        double[] result = DoubleStream.iterate(1.0, n -> n + 1.0)
                .limit(3)
                .flatMap(n -> DoubleStream.of(n, n))
                .toArray();
        log.info("FlatMap result: {}", Arrays.toString(result));
    }

    /**
     * Example of distinct operation.
     * Input: Array with duplicates [1.0, 2.0, 2.0, 3.0, 3.0, 3.0]
     * Output: Array with distinct elements [1.0, 2.0, 3.0]
     */
    public static void distinctExample() {
        double[] result = DoubleStream.of(1.0, 2.0, 2.0, 3.0, 3.0, 3.0)
                .distinct()
                .toArray();
        log.info("Distinct result: {}", Arrays.toString(result));
    }

    /**
     * Example of sorted operation.
     * Input: Unsorted array [3.0, 1.0, 2.0]
     * Output: Sorted array [1.0, 2.0, 3.0]
     */
    public static void sortedExample() {
        double[] result = DoubleStream.of(3.0, 1.0, 2.0)
                .sorted()
                .toArray();
        log.info("Sorted result: {}", Arrays.toString(result));
    }

    /**
     * Example of peek operation.
     * Input: Range of numbers 1.0 to 3.0
     * Output: Logs each element as it flows through the pipeline
     */
    public static void peekExample() {
        double[] result = DoubleStream.iterate(1.0, n -> n + 1.0)
                .limit(3)
                .peek(n -> log.info("Peek: {}", n))
                .toArray();
        log.info("Peek result: {}", Arrays.toString(result));
    }

    /**
     * Example of limit operation.
     * Input: Range of numbers 1.0 to 10.0
     * Output: First 3 numbers [1.0, 2.0, 3.0]
     */
    public static void limitExample() {
        double[] result = DoubleStream.iterate(1.0, n -> n + 1.0)
                .limit(3)
                .toArray();
        log.info("Limit result: {}", Arrays.toString(result));
    }

    /**
     * Example of skip operation.
     * Input: Range of numbers 1.0 to 10.0
     * Output: Skips the first 3 numbers [4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0]
     */
    public static void skipExample() {
        double[] result = DoubleStream.iterate(1.0, n -> n + 1.0)
                .limit(10)
                .skip(3)
                .toArray();
        log.info("Skip result: {}", Arrays.toString(result));
    }

    /**
     * Example of takeWhile operation.
     * Input: Range of numbers 1.0 to 10.0
     * Output: Takes numbers while they are less than 5.0 [1.0, 2.0, 3.0, 4.0]
     */
    public static void takeWhileExample() {
        double[] result = DoubleStream.iterate(1.0, n -> n + 1.0)
                .takeWhile(n -> n < 5.0)
                .toArray();
        log.info("TakeWhile result: {}", Arrays.toString(result));
    }

    /**
     * Example of dropWhile operation.
     * Input: Range of numbers 1.0 to 10.0
     * Output: Drops numbers while they are less than 5.0 [5.0, 6.0, 7.0, 8.0, 9.0, 10.0]
     */
    public static void dropWhileExample() {
        double[] result = DoubleStream.iterate(1.0, n -> n + 1.0)
                .dropWhile(n -> n < 5.0)
                .toArray();
        log.info("DropWhile result: {}", Arrays.toString(result));
    }

    /**
     * Example of forEach operation.
     * Input: Range of numbers 1.0 to 3.0
     * Output: Logs each number
     */
    public static void forEachExample() {
        DoubleStream.iterate(1.0, n -> n + 1.0)
                .limit(3)
                .forEach(n -> log.info("ForEach: {}", n));
    }

    /**
     * Example of forEachOrdered operation.
     * Input: Range of numbers 1.0 to 3.0
     * Output: Logs each number in order
     */
    public static void forEachOrderedExample() {
        DoubleStream.iterate(1.0, n -> n + 1.0)
                .limit(3)
                .forEachOrdered(n -> log.info("ForEachOrdered: {}", n));
    }

    /**
     * Example of toArray operation.
     * Input: Range of numbers 1.0 to 3.0
     * Output: Array of numbers [1.0, 2.0, 3.0]
     */
    public static void toArrayExample() {
        double[] result = DoubleStream.iterate(1.0, n -> n + 1.0)
                .limit(3)
                .toArray();
        log.info("ToArray result: {}", Arrays.toString(result));
    }

    /**
     * Example of reduce with identity operation.
     * Input: Range of numbers 1.0 to 5.0
     * Output: Sum of numbers 15.0
     */
    public static void reduceWithIdentityExample() {
        double result = DoubleStream.iterate(1.0, n -> n + 1.0)
                .limit(5)
                .reduce(0.0, Double::sum);
        log.info("Reduce with identity result: {}", result);
    }

    /**
     * Example of reduce without identity operation.
     * Input: Range of numbers 1.0 to 5.0
     * Output: Sum of numbers OptionalDouble[15.0]
     */
    public static void reduceWithoutIdentityExample() {
        OptionalDouble result = DoubleStream.iterate(1.0, n -> n + 1.0)
                .limit(5)
                .reduce(Double::sum);
        result.ifPresent(res -> log.info("Reduce without identity result: {}", res));
    }

    /**
     * Example of collect operation.
     * Input: Range of numbers 1.0 to 5.0
     * Output: List of boxed doubles [1.0, 2.0, 3.0, 4.0, 5.0]
     */
    public static void collectExample() {
        var result = DoubleStream.iterate(1.0, n -> n + 1.0)
                .limit(5)
                .boxed()
                .collect(Collectors.toList());
        log.info("Collect result: {}", result);
    }

    /**
     * Example of sum operation.
     * Input: Range of numbers 1.0 to 5.0
     * Output: Sum of numbers 15.0
     */
    public static void sumExample() {
        double result = DoubleStream.iterate(1.0, n -> n + 1.0)
                .limit(5)
                .sum();
        log.info("Sum result: {}", result);
    }

    /**
     * Example of min operation.
     * Input: Range of numbers 1.0 to 5.0
     * Output: Minimum number OptionalDouble[1.0]
     */
    public static void minExample() {
        OptionalDouble result = DoubleStream.iterate(1.0, n -> n + 1.0)
                .limit(5)
                .min();
        result.ifPresent(res -> log.info("Min result: {}", res));
    }

    /**
     * Example of max operation.
     * Input: Range of numbers 1.0 to 5.0
     * Output: Maximum number OptionalDouble[5.0]
     */
    public static void maxExample() {
        OptionalDouble result = DoubleStream.iterate(1.0, n -> n + 1.0)
                .limit(5)
                .max();
        result.ifPresent(res -> log.info("Max result: {}", res));
    }

    /**
     * Example of count operation.
     * Input: Range of numbers 1.0 to 5.0
     * Output: Count of numbers 5
     */
    public static void countExample() {
        long result = DoubleStream.iterate(1.0, n -> n + 1.0)
                .limit(5)
                .count();
        log.info("Count result: {}", result);
    }

    /**
     * Example of average operation.
     * Input: Range of numbers 1.0 to 5.0
     * Output: Average of numbers OptionalDouble[3.0]
     */
    public static void averageExample() {
        OptionalDouble result = DoubleStream.iterate(1.0, n -> n + 1.0)
                .limit(5)
                .average();
        result.ifPresent(res -> log.info("Average result: {}", res));
    }

    /**
     * Example of summaryStatistics operation.
     * Input: Range of numbers 1.0 to 5.0
     * Output: Summary statistics including count, sum, min, average, and max
     */
    public static void summaryStatisticsExample() {
        DoubleSummaryStatistics result = DoubleStream.iterate(1.0, n -> n + 1.0)
                .limit(5)
                .summaryStatistics();
        log.info("SummaryStatistics result: {}", result);
    }

    /**
     * Example of anyMatch operation.
     * Input: Range of numbers 1.0 to 5.0
     * Output: true if any number is greater than 3.0
     */
    public static void anyMatchExample() {
        boolean result = DoubleStream.iterate(1.0, n -> n + 1.0)
                .limit(5)
                .anyMatch(n -> n > 3.0);
        log.info("AnyMatch result: {}", result);
    }

    /**
     * Example of allMatch operation.
     * Input: Range of numbers 1.0 to 5.0
     * Output: true if all numbers are less than 6.0
     */
    public static void allMatchExample() {
        boolean result = DoubleStream.iterate(1.0, n -> n + 1.0)
                .limit(5)
                .allMatch(n -> n < 6.0);
        log.info("AllMatch result: {}", result);
    }

    /**
     * Example of noneMatch operation.
     * Input: Range of numbers 1.0 to 5.0
     * Output: true if no number is greater than 5.0
     */
    public static void noneMatchExample() {
        boolean result = DoubleStream.iterate(1.0, n -> n + 1.0)
                .limit(5)
                .noneMatch(n -> n > 5.0);
        log.info("NoneMatch result: {}", result);
    }

    /**
     * Example of findFirst operation.
     * Input: Range of numbers 1.0 to 5.0
     * Output: OptionalDouble containing the first number OptionalDouble[1.0]
     */
    public static void findFirstExample() {
        OptionalDouble result = DoubleStream.iterate(1.0, n -> n + 1.0)
                .limit(5)
                .findFirst();
        result.ifPresent(res -> log.info("FindFirst result: {}", res));
    }

    /**
     * Example of findAny operation.
     * Input: Range of numbers 1.0 to 5.0
     * Output: OptionalDouble containing any number (non-deterministic) OptionalDouble[1.0]
     */
    public static void findAnyExample() {
        OptionalDouble result = DoubleStream.iterate(1.0, n -> n + 1.0)
                .limit(5)
                .findAny();
        result.ifPresent(res -> log.info("FindAny result: {}", res));
    }

    /**
     * Example of boxed operation.
     * Input: Range of numbers 1.0 to 3.0
     * Output: Stream of boxed doubles [1.0, 2.0, 3.0]
     */
    public static void boxedExample() {
        var result = DoubleStream.iterate(1.0, n -> n + 1.0)
                .limit(3)
                .boxed()
                .collect(Collectors.toList());
        log.info("Boxed result: {}", result);
    }
}

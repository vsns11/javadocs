package ca.siva.chapter06;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.PrimitiveIterator;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntStreamExamples {
    private static final Logger log = LoggerFactory.getLogger(IntStreamExamples.class);

    public static void main(String[] args) {
        filterExample();
        mapExample();
        mapToObjExample();
        mapToLongExample();
        mapToDoubleExample();
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
        asLongStreamExample();
        asDoubleStreamExample();
        boxedExample();
    }

    /**
     * Example of filter operation.
     * Input: Range of numbers 1 to 10
     * Output: Filtered numbers greater than 5 [6, 7, 8, 9, 10]
     */
    public static void filterExample() {
        int[] result = IntStream.rangeClosed(1, 10)
                .filter(n -> n > 5)
                .toArray();
        log.info("Filter result: {}", Arrays.toString(result));
    }

    /**
     * Example of map operation.
     * Input: Range of numbers 1 to 5
     * Output: Each number squared [1, 4, 9, 16, 25]
     */
    public static void mapExample() {
        int[] result = IntStream.rangeClosed(1, 5)
                .map(n -> n * n)
                .toArray();
        log.info("Map result: {}", Arrays.toString(result));
    }

    /**
     * Example of mapToObj operation.
     * Input: Range of numbers 1 to 3
     * Output: Each number converted to string ["1", "2", "3"]
     */
    public static void mapToObjExample() {
        String[] result = IntStream.rangeClosed(1, 3)
                .mapToObj(Integer::toString)
                .toArray(String[]::new);
        log.info("MapToObj result: {}", Arrays.toString(result));
    }

    /**
     * Example of mapToLong operation.
     * Input: Range of numbers 1 to 3
     * Output: Each number converted to long [1, 2, 3]
     */
    public static void mapToLongExample() {
        long[] result = IntStream.rangeClosed(1, 3)
                .mapToLong(n -> (long) n)
                .toArray();
        log.info("MapToLong result: {}", Arrays.toString(result));
    }

    /**
     * Example of mapToDouble operation.
     * Input: Range of numbers 1 to 3
     * Output: Each number converted to double [1.0, 2.0, 3.0]
     */
    public static void mapToDoubleExample() {
        double[] result = IntStream.rangeClosed(1, 3)
                .mapToDouble(n -> (double) n)
                .toArray();
        log.info("MapToDouble result: {}", Arrays.toString(result));
    }

    /**
     * Example of flatMap operation.
     * Input: Range of numbers 1 to 3
     * Output: Each number repeated twice [1, 1, 2, 2, 3, 3]
     */
    public static void flatMapExample() {
        int[] result = IntStream.rangeClosed(1, 3)
                .flatMap(n -> IntStream.of(n, n))
                .toArray();
        log.info("FlatMap result: {}", Arrays.toString(result));
    }

    /**
     * Example of distinct operation.
     * Input: Array with duplicates [1, 2, 2, 3, 3, 3]
     * Output: Array with distinct elements [1, 2, 3]
     */
    public static void distinctExample() {
        int[] result = IntStream.of(1, 2, 2, 3, 3, 3)
                .distinct()
                .toArray();
        log.info("Distinct result: {}", Arrays.toString(result));
    }

    /**
     * Example of sorted operation.
     * Input: Unsorted array [3, 1, 2]
     * Output: Sorted array [1, 2, 3]
     */
    public static void sortedExample() {
        int[] result = IntStream.of(3, 1, 2)
                .sorted()
                .toArray();
        log.info("Sorted result: {}", Arrays.toString(result));
    }

    /**
     * Example of peek operation.
     * Input: Range of numbers 1 to 3
     * Output: Logs each element as it flows through the pipeline
     */
    public static void peekExample() {
        int[] result = IntStream.rangeClosed(1, 3)
                .peek(n -> log.info("Peek: {}", n))
                .toArray();
        log.info("Peek result: {}", Arrays.toString(result));
    }

    /**
     * Example of limit operation.
     * Input: Range of numbers 1 to 10
     * Output: First 3 numbers [1, 2, 3]
     */
    public static void limitExample() {
        int[] result = IntStream.rangeClosed(1, 10)
                .limit(3)
                .toArray();
        log.info("Limit result: {}", Arrays.toString(result));
    }

    /**
     * Example of skip operation.
     * Input: Range of numbers 1 to 10
     * Output: Skips the first 3 numbers [4, 5, 6, 7, 8, 9, 10]
     */
    public static void skipExample() {
        int[] result = IntStream.rangeClosed(1, 10)
                .skip(3)
                .toArray();
        log.info("Skip result: {}", Arrays.toString(result));
    }

    /**
     * Example of takeWhile operation.
     * Input: Range of numbers 1 to 10
     * Output: Takes numbers while they are less than 5 [1, 2, 3, 4]
     */
    public static void takeWhileExample() {
        int[] result = IntStream.rangeClosed(1, 10)
                .takeWhile(n -> n < 5)
                .toArray();
        log.info("TakeWhile result: {}", Arrays.toString(result));
    }

    /**
     * Example of dropWhile operation.
     * Input: Range of numbers 1 to 10
     * Output: Drops numbers while they are less than 5 [5, 6, 7, 8, 9, 10]
     */
    public static void dropWhileExample() {
        int[] result = IntStream.rangeClosed(1, 10)
                .dropWhile(n -> n < 5)
                .toArray();
        log.info("DropWhile result: {}", Arrays.toString(result));
    }

    /**
     * Example of forEach operation.
     * Input: Range of numbers 1 to 3
     * Output: Logs each number
     */
    public static void forEachExample() {
        IntStream.rangeClosed(1, 3)
                .forEach(n -> log.info("ForEach: {}", n));
    }

    /**
     * Example of forEachOrdered operation.
     * Input: Range of numbers 1 to 3
     * Output: Logs each number in order
     */
    public static void forEachOrderedExample() {
        IntStream.rangeClosed(1, 3)
                .forEachOrdered(n -> log.info("ForEachOrdered: {}", n));
    }

    /**
     * Example of toArray operation.
     * Input: Range of numbers 1 to 3
     * Output: Array of numbers [1, 2, 3]
     */
    public static void toArrayExample() {
        int[] result = IntStream.rangeClosed(1, 3)
                .toArray();
        log.info("ToArray result: {}", Arrays.toString(result));
    }

    /**
     * Example of reduce with identity operation.
     * Input: Range of numbers 1 to 5
     * Output: Sum of the numbers 15
     */
    public static void reduceWithIdentityExample() {
        int result = IntStream.rangeClosed(1, 5)
                .reduce(0, Integer::sum);
        log.info("Reduce with identity result: {}", result);
    }

    /**
     * Example of reduce without identity operation.
     * Input: Range of numbers 1 to 5
     * Output: Optional containing the sum of the numbers Optional[15]
     */
    public static void reduceWithoutIdentityExample() {
        OptionalInt result = IntStream.rangeClosed(1, 5)
                .reduce(Integer::sum);
        result.ifPresent(res -> log.info("Reduce without identity result: {}", res));
    }

    /**
     * Example of collect operation.
     * Input: Range of numbers 1 to 5
     * Output: List of boxed integers [1, 2, 3, 4, 5]
     */
    public static void collectExample() {
        var result = IntStream.rangeClosed(1, 5)
                .boxed()
                .collect(Collectors.toList());
        log.info("Collect result: {}", result);
    }

    /**
     * Example of sum operation.
     * Input: Range of numbers 1 to 5
     * Output: Sum of the numbers 15
     */
    public static void sumExample() {
        int result = IntStream.rangeClosed(1, 5)
                .sum();
        log.info("Sum result: {}", result);
    }

    /**
     * Example of min operation.
     * Input: Range of numbers 1 to 5
     * Output: Minimum number Optional[1]
     */
    public static void minExample() {
        OptionalInt result = IntStream.rangeClosed(1, 5)
                .min();
        result.ifPresent(res -> log.info("Min result: {}", res));
    }

    /**
     * Example of max operation.
     * Input: Range of numbers 1 to 5
     * Output: Maximum number Optional[5]
     */
    public static void maxExample() {
        OptionalInt result = IntStream.rangeClosed(1, 5)
                .max();
        result.ifPresent(res -> log.info("Max result: {}", res));
    }

    /**
     * Example of count operation.
     * Input: Range of numbers 1 to 5
     * Output: Count of numbers 5
     */
    public static void countExample() {
        long result = IntStream.rangeClosed(1, 5)
                .count();
        log.info("Count result: {}", result);
    }

    /**
     * Example of average operation.
     * Input: Range of numbers 1 to 5
     * Output: Average of numbers OptionalDouble[3.0]
     */
    public static void averageExample() {
        OptionalDouble result = IntStream.rangeClosed(1, 5)
                .average();
        result.ifPresent(res -> log.info("Average result: {}", res));
    }

    /**
     * Example of summaryStatistics operation.
     * Input: Range of numbers 1 to 5
     */
    public static void summaryStatisticsExample() {
        IntSummaryStatistics result = IntStream.rangeClosed(1, 5)
                .summaryStatistics();
        log.info("SummaryStatistics result: {}", result);
    }

    /**
     * Example of anyMatch operation.
     * Input: Range of numbers 1 to 5
     * Output: true if any number is greater than 3
     */
    public static void anyMatchExample() {
        boolean result = IntStream.rangeClosed(1, 5)
                .anyMatch(n -> n > 3);
        log.info("AnyMatch result: {}", result);
    }

    /**
     * Example of allMatch operation.
     * Input: Range of numbers 1 to 5
     * Output: true if all numbers are less than 6
     */
    public static void allMatchExample() {
        boolean result = IntStream.rangeClosed(1, 5)
                .allMatch(n -> n < 6);
        log.info("AllMatch result: {}", result);
    }

    /**
     * Example of noneMatch operation.
     * Input: Range of numbers 1 to 5
     * Output: true if no number is greater than 5
     */
    public static void noneMatchExample() {
        boolean result = IntStream.rangeClosed(1, 5)
                .noneMatch(n -> n > 5);
        log.info("NoneMatch result: {}", result);
    }

    /**
     * Example of findFirst operation.
     * Input: Range of numbers 1 to 5
     * Output: OptionalInt containing the first number OptionalInt[1]
     */
    public static void findFirstExample() {
        OptionalInt result = IntStream.rangeClosed(1, 5)
                .findFirst();
        result.ifPresent(res -> log.info("FindFirst result: {}", res));
    }

    /**
     * Example of findAny operation.
     * Input: Range of numbers 1 to 5
     * Output: OptionalInt containing any number (non-deterministic) OptionalInt[1]
     */
    public static void findAnyExample() {
        OptionalInt result = IntStream.rangeClosed(1, 5)
                .findAny();
        result.ifPresent(res -> log.info("FindAny result: {}", res));
    }

    /**
     * Example of asLongStream operation.
     * Input: Range of numbers 1 to 3
     * Output: LongStream of numbers [1, 2, 3]
     */
    public static void asLongStreamExample() {
        long[] result = IntStream.rangeClosed(1, 3)
                .asLongStream()
                .toArray();
        log.info("AsLongStream result: {}", Arrays.toString(result));
    }

    /**
     * Example of asDoubleStream operation.
     * Input: Range of numbers 1 to 3
     * Output: DoubleStream of numbers [1.0, 2.0, 3.0]
     */
    public static void asDoubleStreamExample() {
        double[] result = IntStream.rangeClosed(1, 3)
                .asDoubleStream()
                .toArray();
        log.info("AsDoubleStream result: {}", Arrays.toString(result));
    }

    /**
     * Example of boxed operation.
     * Input: Range of numbers 1 to 3
     * Output: Stream of boxed integers [1, 2, 3]
     */
    public static void boxedExample() {
        var result = IntStream.rangeClosed(1, 3)
                .boxed()
                .collect(Collectors.toList());
        log.info("Boxed result: {}", result);
    }
}

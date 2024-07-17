package ca.siva.chapter06;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.*;

@Slf4j
public class CollectorsExamples {

    public static void main(String[] args) {
        collectToListExample();
        collectToSetExample();
        collectToMapExample();
        collectJoiningExample();
        collectSummarizingIntExample();
        collectPartitioningByExample();
        collectGroupingByExample();
        collectGroupingByConcurrentExample(); // Add the groupingByConcurrent example
        collectSummingIntExample(); // Add the summingInt example
        collectGroupingByAndSummingIntExample(); // Add the groupingBy and summingInt example
        collectTeeingExample(); // Add the teeing example
    }

    // Existing methods...

    /**
     * Example of Collectors.summingInt().
     * Input: Stream of strings ["a", "bb", "ccc", "dddd"]
     * Output: Sum of the lengths of the strings 10
     */
    public static void collectSummingIntExample() {
        int result = Stream.of("a", "bb", "ccc", "dddd")
                .collect(Collectors.summingInt(String::length));
        log.info("SummingInt result: {}", result);
    }

    /**
     * Example of Collectors.groupingBy() and Collectors.summingInt().
     * Input: Stream of strings ["apple", "banana", "cherry", "apricot"]
     * Output: Map grouped by first letter and summed by length {a=12, b=6, c=6}
     */
    public static void collectGroupingByAndSummingIntExample() {
        Map<Character, Integer> result = Stream.of("apple", "banana", "cherry", "apricot")
                .collect(Collectors.groupingBy(
                        s -> s.charAt(0),
                        Collectors.summingInt(String::length)
                ));
        log.info("GroupingBy and SummingInt result: {}", result);
    }

    /**
     * Example of Collectors.toList().
     * Input: Stream of strings ["a", "b", "c", "d"]
     * Output: List of strings ["a", "b", "c", "d"]
     */
    public static void collectToListExample() {
        List<String> result = Stream.of("a", "b", "c", "d")
                .collect(Collectors.toList());
        log.info("ToList result: {}", result);
    }

    /**
     * Example of Collectors.toSet().
     * Input: Stream of strings ["a", "b", "c", "a"]
     * Output: Set of strings ["a", "b", "c"]
     */
    public static void collectToSetExample() {
        Set<String> result = Stream.of("a", "b", "c", "a")
                .collect(Collectors.toSet());
        log.info("ToSet result: {}", result);
    }

    /**
     * Example of Collectors.toMap().
     * Input: Stream of strings ["a", "b", "c"]
     * Output: Map of string to its length {a=1, b=1, c=1}
     */
    public static void collectToMapExample() {
        Map<String, Integer> result = Stream.of("a", "b", "c")
                .collect(Collectors.toMap(Function.identity(), String::length));
        log.info("ToMap result: {}", result);
    }

    /**
     * Example of Collectors.joining().
     * Input: Stream of strings ["a", "b", "c"]
     * Output: Concatenated string "a, b, c"
     */
    public static void collectJoiningExample() {
        String result = Stream.of("a", "b", "c")
                .collect(Collectors.joining(", "));
        log.info("Joining result: {}", result);
    }

    /**
     * Example of Collectors.summarizingInt().
     * Input: Stream of integers [1, 2, 3, 4, 5]
     * Output: IntSummaryStatistics {count=5, sum=15, min=1, average=3.0, max=5}
     */
    public static void collectSummarizingIntExample() {
        IntSummaryStatistics result = Stream.of(1, 2, 3, 4, 5)
                .collect(Collectors.summarizingInt(Integer::intValue));
        log.info("SummarizingInt result: {}", result);
    }

    /**
     * Example of Collectors.partitioningBy().
     * Input: Stream of integers [1, 2, 3, 4, 5]
     * Output: Map partitioned by even and odd {false=[1, 3, 5], true=[2, 4]}
     */
    public static void collectPartitioningByExample() {
        Map<Boolean, List<Integer>> result = Stream.of(1, 2, 3, 4, 5)
                .collect(Collectors.partitioningBy(n -> n % 2 == 0));
        log.info("PartitioningBy result: {}", result);
    }

    /**
     * Example of Collectors.groupingBy().
     * Input: Stream of strings ["apple", "banana", "cherry", "apricot"]
     * Output: Map grouped by first letter {a=[apple, apricot], b=[banana], c=[cherry]}
     */
    public static void collectGroupingByExample() {
        Map<Character, List<String>> result = Stream.of("apple", "banana", "cherry", "apricot")
                .collect(Collectors.groupingBy(s -> s.charAt(0)));
        log.info("GroupingBy result: {}", result);
    }

    /**
     * Example of Collectors.groupingByConcurrent().
     * Input: Stream of strings ["apple", "banana", "cherry", "apricot"]
     * Output: ConcurrentMap grouped by first letter {a=[apple, apricot], b=[banana], c=[cherry]}
     */
    public static void collectGroupingByConcurrentExample() {
        ConcurrentMap<Character, List<String>> result = Stream.of("apple", "banana", "cherry", "apricot")
                .collect(Collectors.groupingByConcurrent(s -> s.charAt(0)));

        result.forEach((a, b) -> log.info("GroupingByConcurrent a: {}, b:{}", a, b));

    }

    /**
     * Example of Collectors.teeing().
     * Input: Stream of integers [1, 2, 3, 4, 5]
     * Output: Custom summary object combining sum and count
     */
    public static void collectTeeingExample() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        // Using Collectors.teeing to compute sum and count
        CustomSummary summary = numbers.stream()
                .collect(Collectors.teeing(
                        Collectors.summingInt(Integer::intValue),
                        Collectors.counting(),
                        (sum, count) -> new CustomSummary(sum, count)
                ));

        log.info("CustomSummary: Sum={}, Count={}", summary.getSum(), summary.getCount());
    }

    // Custom summary class
    public static class CustomSummary {
        private final int sum;
        private final long count;

        public CustomSummary(int sum, long count) {
            this.sum = sum;
            this.count = count;
        }

        public int getSum() {
            return sum;
        }

        public long getCount() {
            return count;
        }
    }
}

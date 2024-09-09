package ca.siva.ch06_streams_and_lambda;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;

import java.util.stream.*;

@Slf4j
public class CustomCollectorExamples {

    public static void main(String[] args) {
        customCollectorToListExample();
        customCollectorToSetExample();
        customCollectorToMapExample();
        customCollectorJoiningExample();
        customCollectorSummarizingIntExample();
        customCollectorPartitioningByExample();
        customCollectorGroupingByExample();
        customConcurrentCollectorExample();
        customGroupingByConcurrentExample();
        customGroupingByConcurrentWithSupplierExample();
    }

    /**
     * Example of a custom Collector to collect elements into a List.
     * Input: Stream of strings ["a", "b", "c", "d"]
     * Output: List of strings ["a", "b", "c", "d"]
     */
    public static void customCollectorToListExample() {
        Collector<String, List<String>, List<String>> toListCollector = Collector.of(
                ArrayList::new,
                List::add,
                (left, right) -> { left.addAll(right); return left; }
        );

        List<String> result = Stream.of("a", "b", "c", "d")
                .collect(toListCollector);
        log.info("Custom ToList result: {}" ,result);
    }

    /**
     * Example of a custom Collector to collect elements into a Set.
     * Input: Stream of strings ["a", "b", "c", "a"]
     * Output: Set of strings ["a", "b", "c"]
     */
    public static void customCollectorToSetExample() {
        Collector<String, Set<String>, Set<String>> toSetCollector = Collector.of(
                HashSet::new,
                Set::add,
                (left, right) -> { left.addAll(right); return left; }
        );

        Set<String> result = Stream.of("a", "b", "c", "a")
                .collect(toSetCollector);
        log.info("Custom ToSet result: {}" ,result);
    }

    /**
     * Example of a custom Collector to collect elements into a Map.
     * Input: Stream of strings ["a", "b", "c"]
     * Output: Map of string to its length {a=1, b=1, c=1}
     */
    public static void customCollectorToMapExample() {
        Collector<String, Map<String, Integer>, Map<String, Integer>> toMapCollector = Collector.of(
                HashMap::new,
                (map, element) -> map.put(element, element.length()),
                (left, right) -> { left.putAll(right); return left; }
        );

        Map<String, Integer> result = Stream.of("a", "b", "c")
                .collect(toMapCollector);
        log.info("Custom ToMap result: {}" ,result);
    }

    /**
     * Example of a custom Collector to join strings.
     * Input: Stream of strings ["a", "b", "c"]
     * Output: Concatenated string "a, b, c"
     */
    public static void customCollectorJoiningExample() {
        Collector<CharSequence, StringJoiner, String> joiningCollector = Collector.of(
                () -> new StringJoiner(", "),
                StringJoiner::add,
                StringJoiner::merge,
                StringJoiner::toString
        );

        String result = Stream.of("a", "b", "c")
                .collect(joiningCollector);
        log.info("Custom Joining result: {}" ,result);
    }

    /**
     * Example of a custom Collector to summarize integer statistics.
     * Input: Stream of integers [1, 2, 3, 4, 5]
     * Output: IntSummaryStatistics {count=5, sum=15, min=1, average=3.0, max=5}
     */
    public static void customCollectorSummarizingIntExample() {
        Collector<Integer, IntSummaryStatistics, IntSummaryStatistics> summarizingIntCollector = Collector.of(
                IntSummaryStatistics::new,
                IntSummaryStatistics::accept,
                (left, right) -> { left.combine(right); return left; }
        );

        IntSummaryStatistics result = Stream.of(1, 2, 3, 4, 5)
                .collect(summarizingIntCollector);
        log.info("Custom SummarizingInt result: {}, min: {}, max: {}, count: {}, sum: {}" ,result, result.getMin(),
                result.getMax(), result.getCount(), result.getSum());
    }

    /**
     * Example of a custom Collector to partition elements by a predicate.
     * Input: Stream of integers [1, 2, 3, 4, 5]
     * Output: Map partitioned by even and odd {false=[1, 3, 5], true=[2, 4]}
     */
    public static void customCollectorPartitioningByExample() {
        Collector<Integer, Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> partitioningByCollector = Collector.of(
                () -> {
                    Map<Boolean, List<Integer>> map = new HashMap<>();
                    map.put(true, new ArrayList<>());
                    map.put(false, new ArrayList<>());
                    return map;
                },
                (map, element) -> map.get(element % 2 == 0).add(element),
                (left, right) -> {
                    left.get(true).addAll(right.get(true));
                    left.get(false).addAll(right.get(false));
                    return left;
                }
        );

        Map<Boolean, List<Integer>> result = Stream.of(1, 2, 3, 4, 5)
                .collect(partitioningByCollector);
        log.info("Custom PartitioningBy result: {}" ,result);
    }

    /**
     * Example of a custom Collector to group elements by a classifier function.
     * Input: Stream of strings ["apple", "banana", "cherry", "apricot"]
     * Output: Map grouped by first letter {a=[apple, apricot], b=[banana], c=[cherry]}
     */
    public static void customCollectorGroupingByExample() {
        Collector<String, Map<Character, List<String>>, Map<Character, List<String>>> groupingByCollector = Collector.of(
                HashMap::new,
                (map, element) -> map.computeIfAbsent(element.charAt(0), k -> new ArrayList<>()).add(element),
                (left, right) -> {
                    right.forEach((key, value) -> left.merge(key, value, (l, r) -> { l.addAll(r); return l; }));
                    return left;
                }
        );

        Map<Character, List<String>> result = Stream.of("apple", "banana", "cherry", "apricot")
                .collect(groupingByCollector);
        log.info("Custom GroupingBy result: {}" ,result);
    }

    /**
     * Example of a custom concurrent Collector to collect elements into a ConcurrentMap.
     * Input: Stream of strings ["a", "b", "c"]
     * Output: ConcurrentMap of string to its length {a=1, b=1, c=1}
     */
    public static void customConcurrentCollectorExample() {
        Collector<String, ConcurrentMap<String, Integer>, ConcurrentMap<String, Integer>> concurrentMapCollector = Collector.of(
                ConcurrentHashMap::new,
                (map, element) -> map.put(element, element.length()),
                (left, right) -> { left.putAll(right); return left; },
                Collector.Characteristics.CONCURRENT
        );

        ConcurrentMap<String, Integer> result = Stream.of("a", "b", "c")
                .collect(concurrentMapCollector);
        log.info("Custom ConcurrentMap result: {}" ,result);
    }

    /**
     * Example of a custom concurrent Collector to group elements by a classifier function into a custom ConcurrentMap.
     * Input: Stream of strings ["apple", "banana", "cherry", "apricot"]
     * Output: ConcurrentMap grouped by first letter {a=[apple, apricot], b=[banana], c=[cherry]}
     */
    public static void customGroupingByConcurrentWithSupplierExample() {
        Collector<String, ?, ConcurrentMap<Character, List<String>>> groupingByConcurrentWithSupplierCollector = Collectors.groupingByConcurrent(
                str -> str.charAt(0),
                ConcurrentHashMap::new,
                Collectors.toList()
        );

        ConcurrentMap<Character, List<String>> result = Stream.of("apple", "banana", "cherry", "apricot")
                .collect(groupingByConcurrentWithSupplierCollector);
        log.info("Custom GroupingByConcurrent with Supplier result: {}" ,result);
    }

    // Other examples here...

    /**
     * Example of a custom concurrent Collector to group elements by a classifier function.
     * Input: Stream of strings ["apple", "banana", "cherry", "apricot"]
     * Output: ConcurrentMap grouped by first letter {a=[apple, apricot], b=[banana], c=[cherry]}
     */
    public static void customGroupingByConcurrentExample() {
        Collector<String, ?, ConcurrentMap<Character, List<String>>> groupingByConcurrentCollector = Collectors.groupingByConcurrent(
                str -> str.charAt(0),
                Collectors.toList()
        );

        ConcurrentMap<Character, List<String>> result = Stream.of("apple", "banana", "cherry", "apricot")
                .collect(groupingByConcurrentCollector);
        log.info("Custom GroupingByConcurrent result: {}" ,result);
    }
}

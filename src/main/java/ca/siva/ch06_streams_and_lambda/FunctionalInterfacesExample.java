package ca.siva.ch06_streams_and_lambda;

import lombok.extern.slf4j.Slf4j;

import java.util.function.*;
import java.util.stream.*;

@Slf4j
public class FunctionalInterfacesExample {

    // BiConsumer Example
    public static void biConsumerExample() {
        /*
        These all are valid to have final keyword as arguments in a lambda expression.
        You can have var only when the argument is 1, if more than 1, it is not possible to define var.
         */
        BiConsumer<String, String> x = (final String w, final String y) -> {};
        Consumer<String> f = (final var w) -> {};
        Predicate<String> t = (final var s) -> true;

        BiConsumer<Integer, Integer> biConsumer = (a, b) -> log.info("Sum: {}", a + b);
        Stream.of(1, 2, 3).forEach(i -> biConsumer.accept(i, i * 2));
    }

    // BiFunction Example
    public static void biFunctionExample() {
        BiFunction<Integer, Integer, Integer> biFunction = (a, b) -> a + b;
        int result = Stream.of(1, 2, 3)
                .map(i -> biFunction.apply(i, i * 2))
                .reduce(0, Integer::sum);
        log.info("Result: {}", result);
    }

    // BinaryOperator Example
    public static void binaryOperatorExample() {
        BinaryOperator<Integer> binaryOperator = (a, b) -> a * b;
        int result = Stream.of(1, 2, 3, 4)
                .reduce(1, binaryOperator);
        log.info("Product: {}", result);
    }

    // BiPredicate Example
    public static void biPredicateExample() {
        BiPredicate<Integer, Integer> biPredicate = (a, b) -> a > b;
        Stream.of(1, 2, 3).forEach(i -> log.info("Is {} > {}: {}", i, i - 1, biPredicate.test(i, i - 1)));
    }

    // BooleanSupplier Example
    public static void booleanSupplierExample() {
        BooleanSupplier booleanSupplier = () -> true;
        log.info("Boolean Supplier: {}", booleanSupplier.getAsBoolean());
    }

    // Consumer Example
    public static void consumerExample() {
        Consumer<String> consumer = s -> log.info("Hello, {}", s);
        Stream.of("Alice", "Bob", "Charlie").forEach(consumer);
    }

    // DoubleBinaryOperator Example
    public static void doubleBinaryOperatorExample() {
        DoubleBinaryOperator doubleBinaryOperator = (a, b) -> a + b;
        double result = DoubleStream.of(1.0, 2.0, 3.0)
                .reduce(0.0, doubleBinaryOperator);
        log.info("Sum: {}", result);
    }

    // DoubleConsumer Example
    public static void doubleConsumerExample() {
        DoubleConsumer doubleConsumer = d -> log.info("Double value: {}", d);
        DoubleStream.of(1.1, 2.2, 3.3).forEach(doubleConsumer);
    }

    // DoubleFunction Example
    public static void doubleFunctionExample() {
        DoubleFunction<String> doubleFunction = d -> "Double value: " + d;
        DoubleStream.of(1.1, 2.2, 3.3)
                .mapToObj(doubleFunction)
                .forEach(log::info);
    }

    // DoublePredicate Example
    public static void doublePredicateExample() {
        DoublePredicate doublePredicate = d -> d > 2.0;
        DoubleStream.of(1.1, 2.2, 3.3)
                .filter(doublePredicate)
                .forEach(d -> log.info("Filtered double: {}", d));
    }

    // DoubleSupplier Example
    public static void doubleSupplierExample() {
        DoubleSupplier doubleSupplier = () -> 3.14;
        log.info("Double Supplier: {}", doubleSupplier.getAsDouble());
    }

    // DoubleToIntFunction Example
    public static void doubleToIntFunctionExample() {
        DoubleToIntFunction doubleToIntFunction = d -> (int) d;
        DoubleStream.of(1.1, 2.2, 3.3)
                .mapToInt(doubleToIntFunction)
                .forEach(i -> log.info("Integer value: {}", i));
    }

    // DoubleToLongFunction Example
    public static void doubleToLongFunctionExample() {
        DoubleToLongFunction doubleToLongFunction = d -> (long) d;
        DoubleStream.of(1.1, 2.2, 3.3)
                .mapToLong(doubleToLongFunction)
                .forEach(l -> log.info("Long value: {}", l));
    }

    // DoubleUnaryOperator Example
    public static void doubleUnaryOperatorExample() {
        DoubleUnaryOperator doubleUnaryOperator = d -> d * 2;
        DoubleStream.of(1.1, 2.2, 3.3)
                .map(doubleUnaryOperator)
                .forEach(d -> log.info("Double value: {}", d));
    }

    // Function Example
    public static void functionExample() {
        Function<String, Integer> function = String::length;
        Stream.of("Alice", "Bob", "Charlie")
                .map(function)
                .forEach(len -> log.info("Length: {}", len));
    }

    // IntBinaryOperator Example
    public static void intBinaryOperatorExample() {
        IntBinaryOperator intBinaryOperator = (a, b) -> a + b;
        int result = IntStream.of(1, 2, 3)
                .reduce(0, intBinaryOperator);
        log.info("Sum: {}", result);
    }

    // IntConsumer Example
    public static void intConsumerExample() {
        IntConsumer intConsumer = i -> log.info("Int value: {}", i);
        IntStream.of(1, 2, 3).forEach(intConsumer);
    }

    // IntFunction Example
    public static void intFunctionExample() {
        IntFunction<String> intFunction = i -> "Value: " + i;
        IntStream.of(1, 2, 3)
                .mapToObj(intFunction)
                .forEach(log::info);
    }

    // IntPredicate Example
    public static void intPredicateExample() {
        IntPredicate intPredicate = i -> i > 2;
        IntStream.of(1, 2, 3)
                .filter(intPredicate)
                .forEach(i -> log.info("Filtered int: {}", i));
    }

    // IntSupplier Example
    public static void intSupplierExample() {
        IntSupplier intSupplier = () -> 42;
        log.info("Int Supplier: {}", intSupplier.getAsInt());
    }

    // IntToDoubleFunction Example
    public static void intToDoubleFunctionExample() {
        IntToDoubleFunction intToDoubleFunction = i -> i * 2.0;
        IntStream.of(1, 2, 3)
                .mapToDouble(intToDoubleFunction)
                .forEach(d -> log.info("Double value: {}", d));
    }

    // IntToLongFunction Example
    public static void intToLongFunctionExample() {
        IntToLongFunction intToLongFunction = i -> i * 1000L;
        IntStream.of(1, 2, 3)
                .mapToLong(intToLongFunction)
                .forEach(l -> log.info("Long value: {}", l));
    }

    // IntUnaryOperator Example
    public static void intUnaryOperatorExample() {
        IntUnaryOperator intUnaryOperator = i -> i * 2;
        IntStream.of(1, 2, 3)
                .map(intUnaryOperator)
                .forEach(i -> log.info("Int value: {}", i));
    }

    // LongBinaryOperator Example
    public static void longBinaryOperatorExample() {
        LongBinaryOperator longBinaryOperator = (a, b) -> a + b;
        long result = LongStream.of(1L, 2L, 3L)
                .reduce(0L, longBinaryOperator);
        log.info("Sum: {}", result);
    }

    // LongConsumer Example
    public static void longConsumerExample() {
        LongConsumer longConsumer = l -> log.info("Long value: {}", l);
        LongStream.of(1L, 2L, 3L).forEach(longConsumer);
    }

    // LongFunction Example
    public static void longFunctionExample() {
        LongFunction<String> longFunction = l -> "Long value: " + l;
        LongStream.of(1L, 2L, 3L)
                .mapToObj(longFunction)
                .forEach(log::info);
    }

    // LongPredicate Example
    public static void longPredicateExample() {
        LongPredicate longPredicate = l -> l > 2;
        LongStream.of(1L, 2L, 3L)
                .filter(longPredicate)
                .forEach(l -> log.info("Filtered long: {}", l));
    }

    // LongSupplier Example
    public static void longSupplierExample() {
        LongSupplier longSupplier = () -> 123L;
        log.info("Long Supplier: {}", longSupplier.getAsLong());
    }

    // LongToDoubleFunction Example
    public static void longToDoubleFunctionExample() {
        LongToDoubleFunction longToDoubleFunction = l -> l * 2.0;
        LongStream.of(1L, 2L, 3L)
                .mapToDouble(longToDoubleFunction)
                .forEach(d -> log.info("Double value: {}", d));
    }

    // LongToIntFunction Example
    public static void longToIntFunctionExample() {
        LongToIntFunction longToIntFunction = l -> (int) l;
        LongStream.of(1L, 2L, 3L)
                .mapToInt(longToIntFunction)
                .forEach(i -> log.info("Int value: {}", i));
    }

    // LongUnaryOperator Example
    public static void longUnaryOperatorExample() {
        LongUnaryOperator longUnaryOperator = l -> l * 2;
        LongStream.of(1L, 2L, 3L)
                .map(longUnaryOperator)
                .forEach(l -> log.info("Long value: {}", l));
    }

    // ObjDoubleConsumer Example
    public static void objDoubleConsumerExample() {
        ObjDoubleConsumer<String> objDoubleConsumer = (s, d) -> log.info("{}: {}", s, d);
        Stream.of("A", "B", "C")
                .forEach(s -> objDoubleConsumer.accept(s, s.charAt(0) * 1.1));
    }

    // ObjIntConsumer Example
    public static void objIntConsumerExample() {
        ObjIntConsumer<String> objIntConsumer = (s, i) -> log.info("{}: {}", s, i);
        Stream.of("A", "B", "C")
                .forEach(s -> objIntConsumer.accept(s, s.charAt(0)));
    }

    // ObjLongConsumer Example
    public static void objLongConsumerExample() {
        ObjLongConsumer<String> objLongConsumer = (s, l) -> log.info("{}: {}", s, l);
        Stream.of("A", "B", "C")
                .forEach(s -> objLongConsumer.accept(s, (long) s.charAt(0)));
    }

    // Predicate Example
    public static void predicateExample() {
        Predicate<String> predicate = s -> s.length() > 3;
        Stream.of("Alice", "Bob", "Charlie")
                .filter(predicate)
                .forEach(s -> log.info("Filtered: {}", s));
    }

    // Supplier Example
    public static void supplierExample() {
        Supplier<String> supplier = () -> "Hello, Supplier!";
        log.info(supplier.get());
    }

    // ToDoubleBiFunction Example
    public static void toDoubleBiFunctionExample() {
        ToDoubleBiFunction<Integer, Integer> toDoubleBiFunction = (a, b) -> a + b;
        double result = Stream.of(1, 2, 3)
                .mapToDouble(i -> toDoubleBiFunction.applyAsDouble(i, i * 2))
                .sum();
        log.info("Result: {}", result);
    }

    // ToDoubleFunction Example
    public static void toDoubleFunctionExample() {
        ToDoubleFunction<String> toDoubleFunction = String::length;
        Stream.of("Alice", "Bob", "Charlie")
                .mapToDouble(toDoubleFunction)
                .forEach(d -> log.info("Length: {}", d));
    }

    // ToIntBiFunction Example
    public static void toIntBiFunctionExample() {
        ToIntBiFunction<Integer, Integer> toIntBiFunction = (a, b) -> a + b;
        int result = Stream.of(1, 2, 3)
                .mapToInt(i -> toIntBiFunction.applyAsInt(i, i * 2))
                .sum();
        log.info("Result: {}", result);
    }

    // ToIntFunction Example
    public static void toIntFunctionExample() {
        ToIntFunction<String> toIntFunction = String::length;
        Stream.of("Alice", "Bob", "Charlie")
                .mapToInt(toIntFunction)
                .forEach(len -> log.info("Length: {}", len));
    }

    // ToLongBiFunction Example
    public static void toLongBiFunctionExample() {
        ToLongBiFunction<Integer, Integer> toLongBiFunction = (a, b) -> a + b;
        long result = Stream.of(1, 2, 3)
                .mapToLong(i -> toLongBiFunction.applyAsLong(i, i * 2))
                .sum();
        log.info("Result: {}", result);
    }

    // ToLongFunction Example
    public static void toLongFunctionExample() {
        ToLongFunction<String> toLongFunction = s -> s.length();
        Stream.of("Alice", "Bob", "Charlie")
                .mapToLong(toLongFunction)
                .forEach(len -> log.info("Length: {}", len));
    }

    // UnaryOperator Example
    public static void unaryOperatorExample() {
        UnaryOperator<String> unaryOperator = String::toUpperCase;
        Stream.of("Alice", "Bob", "Charlie")
                .map(unaryOperator)
                .forEach(log::info);
    }

    public static void main(String[] args) {
        biConsumerExample();
        biFunctionExample();
        binaryOperatorExample();
        biPredicateExample();
        booleanSupplierExample();
        consumerExample();

        doubleBinaryOperatorExample();
        doubleConsumerExample();
        doubleFunctionExample();
        doublePredicateExample();
        doubleSupplierExample();

        doubleToIntFunctionExample();
        doubleToLongFunctionExample();
        doubleUnaryOperatorExample();

        functionExample();

        intBinaryOperatorExample();
        intConsumerExample();
        intFunctionExample();
        intPredicateExample();
        intSupplierExample();

        intToDoubleFunctionExample();
        intToLongFunctionExample();
        intUnaryOperatorExample();

        longBinaryOperatorExample();
        longConsumerExample();
        longFunctionExample();
        longPredicateExample();
        longSupplierExample();

        longToDoubleFunctionExample();
        longToIntFunctionExample();
        longUnaryOperatorExample();

        objDoubleConsumerExample();
        objIntConsumerExample();
        objLongConsumerExample();

        predicateExample();
        supplierExample();
        unaryOperatorExample();

        toDoubleBiFunctionExample();
        toDoubleFunctionExample();
        toIntBiFunctionExample();
        toIntFunctionExample();
        toLongBiFunctionExample();
        toLongFunctionExample();

    }
}

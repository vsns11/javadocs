package ca.siva.chapter02;

import lombok.extern.slf4j.Slf4j;

import java.util.stream.Stream;

@Slf4j
public class InfiniteLoopExamples {

    public static void main(String[] args) {
        infiniteWhileLoop();
        infiniteForLoop();
        infiniteDoWhileLoop();
        infiniteRecursion();
        infiniteStream();
        infiniteIntLoop();
    }

    // Infinite while loop
    public static void infiniteWhileLoop() {
        log.info("Infinite while loop started...");
        while (true) {
            log.info("This is an infinite while loop");
            // Uncomment the break statement to exit the loop
            // break;
        }
    }

    // Infinite for loop
    public static void infiniteForLoop() {
        log.info("Infinite for loop started...");
        for (;;) {
            log.info("This is an infinite for loop");
            // Uncomment the break statement to exit the loop
            // break;
        }
    }

    // Infinite do-while loop
    public static void infiniteDoWhileLoop() {
        log.info("Infinite do-while loop started...");
        do {
            log.info("This is an infinite do-while loop");
            // Uncomment the break statement to exit the loop
            // break;
        } while (true);
    }

    // Infinite recursion
    public static void infiniteRecursion() {
        log.info("This is an infinite recursion");
        // Uncomment the break statement to exit the loop (not possible in recursion)
        infiniteRecursion(); // Recursive call
    }

    // Infinite stream
    public static void infiniteStream() {
        log.info("Infinite stream started...");
        Stream.generate(() -> "This is an infinite stream")
                .forEach(System.out::println);
    }

    // Infinite loop using int variable
    public static void infiniteIntLoop() {
        log.info("Infinite loop with int variable started...");
        int i = 0;
        while (i >= 0) {
            log.info("This is an infinite loop with int variable: " + i);
            // Uncomment the break statement to exit the loop
            // break;
        }
    }

    public static void infiniteIntLoop2() {
        log.info("Infinite loop with int variable started...");
        int i = 1;
        /*
        This will give compilation error.
        while (i) {
            log.info("This is an infinite loop with int variable: " + i);
           i++;
        }
        */

    }
}

package ca.siva.chapter02;

import java.util.stream.Stream;

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
        System.out.println("Infinite while loop started...");
        while (true) {
            System.out.println("This is an infinite while loop");
            // Uncomment the break statement to exit the loop
            // break;
        }
    }

    // Infinite for loop
    public static void infiniteForLoop() {
        System.out.println("Infinite for loop started...");
        for (;;) {
            System.out.println("This is an infinite for loop");
            // Uncomment the break statement to exit the loop
            // break;
        }
    }

    // Infinite do-while loop
    public static void infiniteDoWhileLoop() {
        System.out.println("Infinite do-while loop started...");
        do {
            System.out.println("This is an infinite do-while loop");
            // Uncomment the break statement to exit the loop
            // break;
        } while (true);
    }

    // Infinite recursion
    public static void infiniteRecursion() {
        System.out.println("This is an infinite recursion");
        // Uncomment the break statement to exit the loop (not possible in recursion)
        infiniteRecursion(); // Recursive call
    }

    // Infinite stream
    public static void infiniteStream() {
        System.out.println("Infinite stream started...");
        Stream.generate(() -> "This is an infinite stream")
                .forEach(System.out::println);
    }

    // Infinite loop using int variable
    public static void infiniteIntLoop() {
        System.out.println("Infinite loop with int variable started...");
        int i = 0;
        while (i >= 0) {
            System.out.println("This is an infinite loop with int variable: " + i);
            // Uncomment the break statement to exit the loop
            // break;
        }
    }

    public static void infiniteIntLoop2() {
        System.out.println("Infinite loop with int variable started...");
        int i = 1;
        /*
        This will give compilation error.
        while (i) {
            System.out.println("This is an infinite loop with int variable: " + i);
           i++;
        }
        */

    }
}

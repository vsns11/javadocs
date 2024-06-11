package ca.siva.chapter01;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringBuilderExamples {

    public static void main(String[] args) {
        // Example 1: Basic StringBuilder usage
        basicUsage();

        // Example 2: Append method
        appendExample();

        // Example 3: Insert method
        insertExample();

        // Example 4: Delete and DeleteCharAt methods
        deleteExample();

        // Example 5: Replace method
        replaceExample();

        // Example 6: Reverse method
        reverseExample();

        // Example 7: Substring method
        substringExample();

        // Example 8: Capacity and Length methods
        capacityAndLengthExample();

        // Example 9: CharAt and SetCharAt methods
        charAtAndSetCharAtExample();
    }

    private static void basicUsage() {
        StringBuilder sb = new StringBuilder("Hello");
        sb.append(" World");
        log.info("Basic Usage: " + sb.toString());
    }

    private static void appendExample() {
        StringBuilder sb = new StringBuilder();
        sb.append("StringBuilder ").append("is ").append("efficient.").append(100);
        log.info("Append Example: " + sb.toString());
    }

    private static void insertExample() {
        StringBuilder sb = new StringBuilder("Java");
        sb.insert(4, " Programming");
        log.info("Insert Example: " + sb.toString());
    }

    private static void deleteExample() {
        StringBuilder sb = new StringBuilder("0123456789");
        sb.delete(2, 5);
        log.info("Delete Example: " + sb.toString());
        sb.deleteCharAt(0);
        log.info("DeleteCharAt Example: " + sb.toString());
    }

    private static void replaceExample() {
        StringBuilder sb = new StringBuilder("Hello, World!");
        sb.replace(7, 12, "Java");
        log.info("Replace Example: " + sb.toString());
    }

    private static void reverseExample() {
        StringBuilder sb = new StringBuilder("ABCDE");
        sb.reverse();
        log.info("Reverse Example: " + sb.toString());
    }

    private static void substringExample() {
        StringBuilder sb = new StringBuilder("Hello, World!");
        String sub = sb.substring(7, 12);
        log.info("Substring Example: " + sub);
    }

    private static void capacityAndLengthExample() {
        StringBuilder sb = new StringBuilder(50);
        log.info("Initial Capacity: " + sb.capacity());
        sb.append("Hello, World!");
        log.info("After Append, Capacity: " + sb.capacity());
        log.info("Length: " + sb.length());
        sb.ensureCapacity(100);
        log.info("After Ensure Capacity, Capacity: " + sb.capacity());
        sb.setLength(5);
        log.info("After Set Length: " + sb.toString());
    }

    private static void charAtAndSetCharAtExample() {
        StringBuilder sb = new StringBuilder("Java");
        char ch = sb.charAt(1);
        log.info("CharAt Example: " + ch);
        sb.setCharAt(1, 'u');
        log.info("SetCharAt Example: " + sb.toString());
    }
}

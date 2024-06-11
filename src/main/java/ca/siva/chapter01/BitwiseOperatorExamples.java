package ca.siva.chapter01;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BitwiseOperatorExamples {

    // Bitwise NOT (~)
    public void bitwiseNotExample() {
        int a = 8;            // 0000 1000
        int result = ~a;      // 1111 0111 (in two's complement: -9)
        log.info("Bitwise NOT (~) of " + a + " is " + result);
    }

    // Bitwise AND (&)
    public void bitwiseAndExample() {
        int a = 8;            // 0000 1000
        int b = 5;            // 0000 0101
        int result = a & b;   // 0000 0000
        log.info("Bitwise AND (&) of " + a + " and " + b + " is " + result);
    }

    // Bitwise OR (|)
    public void bitwiseOrExample() {
        int a = 8;            // 0000 1000
        int b = 5;            // 0000 0101
        int result = a | b;   // 0000 1101
        log.info("Bitwise OR (|) of " + a + " and " + b + " is " + result);
    }

    // Bitwise XOR (^)
    public void bitwiseXorExample() {
        int a = 8;            // 0000 1000
        int b = 5;            // 0000 0101
        int result = a ^ b;   // 0000 1101
        log.info("Bitwise XOR (^) of " + a + " and " + b + " is " + result);
    }

    // Left Shift (<<)
    public void leftShiftExample() {
        int a = 8;            // 0000 1000
        int result = a << 1;  // 0001 0000 (16)
        log.info("Left Shift (<<) of " + a + " by 1 is " + result);
    }

    // Right Shift (>>)
    public void rightShiftExample() {
        int a = 8;            // 0000 1000
        int result = a >> 1;  // 0000 0100 (4)
        log.info("Right Shift (>>) of " + a + " by 1 is " + result);
    }

    // Unsigned Right Shift (>>>)
    public void unsignedRightShiftExample() {
        int a = -8;           // 1111 1000
        int result = a >>> 1; // 0111 1100
        log.info("Unsigned Right Shift (>>>) of " + a + " by 1 is " + result);
    }

    // Main method to execute all examples
    public static void main(String[] args) {
        BitwiseOperatorExamples examples = new BitwiseOperatorExamples();

        log.info("Bitwise NOT Example:");
        examples.bitwiseNotExample();

        log.info("\nBitwise AND Example:");
        examples.bitwiseAndExample();

        log.info("\nBitwise OR Example:");
        examples.bitwiseOrExample();

        log.info("\nBitwise XOR Example:");
        examples.bitwiseXorExample();

        log.info("\nLeft Shift Example:");
        examples.leftShiftExample();

        log.info("\nRight Shift Example:");
        examples.rightShiftExample();

        log.info("\nUnsigned Right Shift Example:");
        examples.unsignedRightShiftExample();
    }
}
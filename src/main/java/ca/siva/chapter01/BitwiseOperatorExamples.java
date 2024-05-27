package ca.siva.chapter01;

public class BitwiseOperatorExamples {

    // Bitwise NOT (~)
    public void bitwiseNotExample() {
        int a = 8;            // 0000 1000
        int result = ~a;      // 1111 0111 (in two's complement: -9)
        System.out.println("Bitwise NOT (~) of " + a + " is " + result);
    }

    // Bitwise AND (&)
    public void bitwiseAndExample() {
        int a = 8;            // 0000 1000
        int b = 5;            // 0000 0101
        int result = a & b;   // 0000 0000
        System.out.println("Bitwise AND (&) of " + a + " and " + b + " is " + result);
    }

    // Bitwise OR (|)
    public void bitwiseOrExample() {
        int a = 8;            // 0000 1000
        int b = 5;            // 0000 0101
        int result = a | b;   // 0000 1101
        System.out.println("Bitwise OR (|) of " + a + " and " + b + " is " + result);
    }

    // Bitwise XOR (^)
    public void bitwiseXorExample() {
        int a = 8;            // 0000 1000
        int b = 5;            // 0000 0101
        int result = a ^ b;   // 0000 1101
        System.out.println("Bitwise XOR (^) of " + a + " and " + b + " is " + result);
    }

    // Left Shift (<<)
    public void leftShiftExample() {
        int a = 8;            // 0000 1000
        int result = a << 1;  // 0001 0000 (16)
        System.out.println("Left Shift (<<) of " + a + " by 1 is " + result);
    }

    // Right Shift (>>)
    public void rightShiftExample() {
        int a = 8;            // 0000 1000
        int result = a >> 1;  // 0000 0100 (4)
        System.out.println("Right Shift (>>) of " + a + " by 1 is " + result);
    }

    // Unsigned Right Shift (>>>)
    public void unsignedRightShiftExample() {
        int a = -8;           // 1111 1000
        int result = a >>> 1; // 0111 1100
        System.out.println("Unsigned Right Shift (>>>) of " + a + " by 1 is " + result);
    }

    // Main method to execute all examples
    public static void main(String[] args) {
        BitwiseOperatorExamples examples = new BitwiseOperatorExamples();

        System.out.println("Bitwise NOT Example:");
        examples.bitwiseNotExample();

        System.out.println("\nBitwise AND Example:");
        examples.bitwiseAndExample();

        System.out.println("\nBitwise OR Example:");
        examples.bitwiseOrExample();

        System.out.println("\nBitwise XOR Example:");
        examples.bitwiseXorExample();

        System.out.println("\nLeft Shift Example:");
        examples.leftShiftExample();

        System.out.println("\nRight Shift Example:");
        examples.rightShiftExample();

        System.out.println("\nUnsigned Right Shift Example:");
        examples.unsignedRightShiftExample();
    }
}
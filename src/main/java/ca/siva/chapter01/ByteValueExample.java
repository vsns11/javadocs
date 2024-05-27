package ca.siva.chapter01;


public class ByteValueExample {

    public static void main(String[] args) {
        byteValueFromNumericClasses();
        byteValueFromCharacter();
        byteValueFromString();
    }

    /**
     * Demonstrates the use of byteValue() method with different numeric classes.
     * This method converts the value of various numeric objects to a byte and prints the result.
     */
    private static void byteValueFromNumericClasses() {
        Byte byteObj = 123;
        Short shortObj = 12345;
        Integer intObj = 1234567;
        Long longObj = 123456789L;
        Float floatObj = 123.45f;
        Double doubleObj = 123.456;

        byte byteValue = byteObj.byteValue();
        byte shortValue = shortObj.byteValue();
        byte intValue = intObj.byteValue();
        byte longValue = longObj.byteValue();
        byte floatValue = floatObj.byteValue();
        byte doubleValue = doubleObj.byteValue();

        System.out.println("Byte value: " + byteValue); // 123
        System.out.println("Short value as byte: " + shortValue); // 57 (12345 % 256)
        System.out.println("Integer value as byte: " + intValue); // 87 (1234567 % 256)
        System.out.println("Long value as byte: " + longValue); // 21 (123456789 % 256)
        System.out.println("Float value as byte: " + floatValue); // 123
        System.out.println("Double value as byte: " + doubleValue); // 123
    }

    /**
     * Demonstrates converting a character to its byte value.
     * This method converts the ASCII value of a character to a byte and prints the result.
     */
    private static void byteValueFromCharacter() {
        char ch = 'A'; // ASCII value of 'A' is 65
        byte byteValue = (byte) ch;
        System.out.println("Character 'A' as byte: " + byteValue); // 65
    }

    /**
     * Demonstrates converting a string to a byte value.
     * This method parses a numeric string to a byte and prints the result.
     * It also converts each character in a string to its byte value and prints the results.
     */
    private static void byteValueFromString() {
        String str = "123";
        byte byteValue = Byte.parseByte(str);
        System.out.println("String '123' as byte: " + byteValue); // 123

        // Converting each character of the string to byte values
        String str2 = "ABC";
        for (char ch : str2.toCharArray()) {
            byte byteChar = (byte) ch;
            System.out.println("Character '" + ch + "' as byte: " + byteChar);
        }
        // Output:
        // Character 'A' as byte: 65
        // Character 'B' as byte: 66
        // Character 'C' as byte: 67
    }
}

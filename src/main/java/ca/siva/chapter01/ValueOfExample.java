package ca.siva.chapter01;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValueOfExample {

    public static void main(String[] args) {
        valueOfByte();
        valueOfShort();
        valueOfInteger();
        valueOfLong();
        valueOfFloat();
        valueOfDouble();
        valueOfBoolean();
        valueOfCharacter();
        trickyScenarios();
    }

    /**
     * Demonstrates the use of valueOf() method with Byte.
     */
    private static void valueOfByte() {
        String str = "123";
        Byte byteValue = Byte.valueOf(str);
        log.info("String '123' to Byte: " + byteValue); // Output: 123
    }

    /**
     * Demonstrates the use of valueOf() method with Short.
     */
    private static void valueOfShort() {
        String str = "12345";
        Short shortValue = Short.valueOf(str);
        log.info("String '12345' to Short: " + shortValue); // Output: 12345
    }

    /**
     * Demonstrates the use of valueOf() method with Integer.
     */
    private static void valueOfInteger() {
        String str = "123456";
        Integer intValue = Integer.valueOf(str);
        log.info("String '123456' to Integer: " + intValue); // Output: 123456
    }

    /**
     * Demonstrates the use of valueOf() method with Long.
     */
    private static void valueOfLong() {
        String str = "123456789";
        Long longValue = Long.valueOf(str);
        log.info("String '123456789' to Long: " + longValue); // Output: 123456789
    }

    /**
     * Demonstrates the use of valueOf() method with Float.
     */
    private static void valueOfFloat() {
        String str = "123.45";
        Float floatValue = Float.valueOf(str);
        log.info("String '123.45' to Float: " + floatValue); // Output: 123.45
    }

    /**
     * Demonstrates the use of valueOf() method with Double.
     */
    private static void valueOfDouble() {
        String str = "123.456";
        Double doubleValue = Double.valueOf(str);
        log.info("String '123.456' to Double: " + doubleValue); // Output: 123.456
    }

    /**
     * Demonstrates the use of valueOf() method with Boolean.
     */
    private static void valueOfBoolean() {
        String strTrue = "true";
        String strFalse = "false";
        Boolean boolTrue = Boolean.valueOf(strTrue);
        Boolean boolFalse = Boolean.valueOf(strFalse);
        log.info("String 'true' to Boolean: " + boolTrue); // Output: true
        log.info("String 'false' to Boolean: " + boolFalse); // Output: false
    }

    /**
     * Demonstrates the use of valueOf() method with Character.
     */
    private static void valueOfCharacter() {
        char charValue = 'A';
        Character character = Character.valueOf(charValue);
        log.info("Character 'A' to Character: " + character); // Output: A
    }

    /**
     * Demonstrates tricky scenarios with valueOf() methods.
     */
    private static void trickyScenarios() {
        // Handling invalid number format
        try {
            String invalidNumber = "123abc";
            Integer invalidInt = Integer.valueOf(invalidNumber);
            log.info("String '123abc' to Integer: " + invalidInt);
        } catch (NumberFormatException e) {
            log.info("Exception: " + e.getMessage()); // Output: Exception: For input string: "123abc"
        }

        // Boolean valueOf with case sensitivity
        String trueString = "TrUe";
        String falseString = "FaLsE";
        Boolean trueBool = Boolean.valueOf(trueString);
        Boolean falseBool = Boolean.valueOf(falseString);
        log.info("String 'TrUe' to Boolean: " + trueBool); // Output: true
        log.info("String 'FaLsE' to Boolean: " + falseBool); // Output: false

        // Handling whitespace
        String whiteSpaceString = " 123 ";
        Integer whiteSpaceInt = Integer.valueOf(whiteSpaceString.trim());
        log.info("String ' 123 ' to Integer: " + whiteSpaceInt); // Output: 123

        // Hexadecimal parsing
        String hexString = "7F";
        Integer hexInt = Integer.valueOf(hexString, 16);
        log.info("String '7F' to Integer (hex): " + hexInt); // Output: 127

        // Binary parsing
        String binaryString = "1010";
        Integer binaryInt = Integer.valueOf(binaryString, 2);
        log.info("String '1010' to Integer (binary): " + binaryInt); // Output: 10
    }
}

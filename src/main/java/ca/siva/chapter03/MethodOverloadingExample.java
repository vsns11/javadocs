package ca.siva.chapter03;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MethodOverloadingExample {

    // Method with int parameter
    public void printValue(int value) {
        log.info("Method with int parameter: " + value);
    }

    // Method with Integer parameter
    public void printValue(Integer value) {
        log.info("Method with Integer parameter: " + value);
    }

    // Method with Object parameter
    public void printValue(Object value) {
        log.info("Method with Object parameter: " + value);
    }

    public static void main(String[] args) {
        MethodOverloadingExample example = new MethodOverloadingExample();

        // Call method with int parameter
        example.printValue((short)10); // Method with int parameter: 10

        // Call method with Integer parameter
        example.printValue(Integer.valueOf(20)); // Method with Integer parameter: 20

        // Call method with Object parameter
        example.printValue("Hello"); // Method with Object parameter: Hello
        example.printValue(30.5);    // Method with Object parameter: 30.5

        // Demonstrate method calls with automatic type conversion
        int intValue = 40;
        Integer integerValue = 50;
        Double doubleValue = 60.5;

        example.printValue(intValue);        // Method with int parameter: 40
        example.printValue(integerValue);    // Method with Integer parameter: 50
        example.printValue(doubleValue);     // Method with Object parameter: 60.5
    }
}

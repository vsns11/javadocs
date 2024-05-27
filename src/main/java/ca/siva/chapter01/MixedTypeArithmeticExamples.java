package ca.siva.chapter01;


public class MixedTypeArithmeticExamples {

    // Adding int, float, and double
    public static void additionExample() {
        int intValue = 10;
        float floatValue = 15.5f;
        double doubleValue = 20.25;

        float floatResult = intValue + floatValue; // int promoted to float
        double doubleResult = intValue + doubleValue; // int promoted to double
        double mixedResult = floatValue + doubleValue; // float promoted to double

        System.out.println("Addition (int + float): " + floatResult); // 25.5
        System.out.println("Addition (int + double): " + doubleResult); // 30.25
        System.out.println("Addition (float + double): " + mixedResult); // 35.75
    }

    // Subtracting int, float, and double
    public static void subtractionExample() {
        int intValue = 50;
        float floatValue = 15.5f;
        double doubleValue = 20.25;

        float floatResult = intValue - floatValue; // int promoted to float
        double doubleResult = intValue - doubleValue; // int promoted to double
        double mixedResult = floatValue - doubleValue; // float promoted to double

        System.out.println("Subtraction (int - float): " + floatResult); // 34.5
        System.out.println("Subtraction (int - double): " + doubleResult); // 29.75
        System.out.println("Subtraction (float - double): " + mixedResult); // -4.75
    }

    // Multiplying int, float, and double
    public static void multiplicationExample() {
        int intValue = 5;
        float floatValue = 15.5f;
        double doubleValue = 2.5;

        float floatResult = intValue * floatValue; // int promoted to float
        double doubleResult = intValue * doubleValue; // int promoted to double
        double mixedResult = floatValue * doubleValue; // float promoted to double

        System.out.println("Multiplication (int * float): " + floatResult); // 77.5
        System.out.println("Multiplication (int * double): " + doubleResult); // 12.5
        System.out.println("Multiplication (float * double): " + mixedResult); // 38.75
    }

    // Dividing int, float, and double
    public static void divisionExample() {
        int intValue = 100;
        float floatValue = 15.5f;
        double doubleValue = 2.5;

        float floatResult = intValue / floatValue; // int promoted to float
        double doubleResult = intValue / doubleValue; // int promoted to double
        double mixedResult = floatValue / doubleValue; // float promoted to double

        System.out.println("Division (int / float): " + floatResult); // ~6.4516129
        System.out.println("Division (int / double): " + doubleResult); // 40.0
        System.out.println("Division (float / double): " + mixedResult); // 6.2
    }

    // Mixed operations with int, float, and double
    public static void mixedOperationsExample() {
        int intValue = 10;
        float floatValue = 15.5f;
        double doubleValue = 2.5;

        double mixedResult = intValue + floatValue * doubleValue - doubleValue / floatValue;
        // Order of operations: floatValue * doubleValue -> promoted to double
        // doubleValue / floatValue -> promoted to double
        // intValue + (result of multiplication) -> promoted to double
        // (result of addition) - (result of division)

        System.out.println("Mixed operations result: " + mixedResult); // 10 + (15.5 * 2.5) - (2.5 / 15.5)
        // 10 + 38.75 - 0.16129032
        // 48.58870968
    }

    public static void main(String[] args) {
        additionExample();
        subtractionExample();
        multiplicationExample();
        divisionExample();
        mixedOperationsExample();
    }
}

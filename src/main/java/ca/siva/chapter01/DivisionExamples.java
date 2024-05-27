package ca.siva.chapter01;

public class DivisionExamples {

    public static void main(String[] args) {
        integerDivisionExample();
        divisionByZeroExample();
        floatingPointDivisionExample();
    }

    private static void integerDivisionExample() {
        int a = 10;
        int b = 3;

        int result1 = a / b; // Integer division
        System.out.println("10 / 3 = " + result1); // Output: 10 / 3 = 3

        int c = 10;
        int d = 4;

        int result2 = c / d; // Integer division
        System.out.println("10 / 4 = " + result2); // Output: 10 / 4 = 2
    }


    private static void divisionByZeroExample() {
        int a = 10;
        try {
            int result = a / 0;
            System.out.println("10 / 0 = " + result);
        } catch (ArithmeticException e) {
            System.out.println("Division by zero error: " + e.getMessage()); // Output: Division by zero error: / by zero
        }
    }

    /*
    if you cast one of the operands to double, the result would become double; same with float.
    In Integer division assignment, the fraction part is always excluded
    */
    private static void floatingPointDivisionExample() {
        int a = 10;
        int b = 3;

        // Casting one operand to double for floating-point division
        double result1 = (double) a / b;
        System.out.println("10 / 3 (floating-point) = " + result1); // Output: 10 / 3 (floating-point) = 3.3333333333333335

        // Casting both operands to double
        double result2 = (double) a / (double) b;
        System.out.println("10 / 3 (both operands cast to double) = " + result2); // Output: 10 / 3 (both operands cast to double) = 3.3333333333333335

        // Another example with different values
        int c = 10;
        int d = 4;
        double result3 = (double) c / d;
        System.out.println("10 / 4 (floating-point) = " + result3); // Output: 10 / 4 (floating-point) = 2.5
    }
}

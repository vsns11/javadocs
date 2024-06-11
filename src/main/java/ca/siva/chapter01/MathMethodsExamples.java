package ca.siva.chapter01;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MathMethodsExamples {

    // Returns the absolute value of an int value
    public int exampleAbsInt(int value) {
        return Math.abs(value);
    }

    // Returns the absolute value of a double value
    public double exampleAbsDouble(double value) {
        return Math.abs(value);
    }

    // Returns the larger of two int values
    public int exampleMaxInt(int a, int b) {
        return Math.max(a, b);
    }

    // Returns the larger of two double values
    public double exampleMaxDouble(double a, double b) {
        return Math.max(a, b);
    }

    // Returns the smaller of two int values
    public int exampleMinInt(int a, int b) {
        return Math.min(a, b);
    }

    // Returns the smaller of two double values
    public double exampleMinDouble(double a, double b) {
        return Math.min(a, b);
    }

    // Returns the trigonometric cosine of an angle
    public double exampleCos(double angle) {
        return Math.cos(angle);
    }

    // Returns the trigonometric sine of an angle
    public double exampleSin(double angle) {
        return Math.sin(angle);
    }

    // Returns the trigonometric tangent of an angle
    public double exampleTan(double angle) {
        return Math.tan(angle);
    }

    // Returns the arc cosine of a value
    public double exampleAcos(double value) {
        return Math.acos(value);
    }

    // Returns the arc sine of a value
    public double exampleAsin(double value) {
        return Math.asin(value);
    }

    // Returns the arc tangent of a value
    public double exampleAtan(double value) {
        return Math.atan(value);
    }

    // Returns the value of the first argument raised to the power of the second argument
    public double examplePow(double base, double exponent) {
        return Math.pow(base, exponent);
    }

    // Returns the correctly rounded positive square root of a double value
    public double exampleSqrt(double value) {
        return Math.sqrt(value);
    }

    // Returns the natural logarithm (base e) of a double value
    public double exampleLog(double value) {
        return Math.log(value);
    }

    // Returns the base 10 logarithm of a double value
    public double exampleLog10(double value) {
        return Math.log10(value);
    }

    // Returns the smallest (closest to negative infinity) double value that is greater than or equal to the argument and is equal to a mathematical integer
    public double exampleCeil(double value) {
        return Math.ceil(value);
    }

    // Returns the largest (closest to positive infinity) double value that is less than or equal to the argument and is equal to a mathematical integer
    public double exampleFloor(double value) {
        return Math.floor(value);
    }

    // Returns the closest int to the argument, with ties rounding up
    public int exampleRoundFloat(float value) {
        return Math.round(value);
    }

    // Returns the closest long to the argument, with ties rounding up
    public long exampleRoundDouble(double value) {
        return Math.round(value);
    }

    // Returns the unbiased exponent used in the representation of a double
    public int exampleGetExponentDouble(double value) {
        return Math.getExponent(value);
    }

    // Returns the unbiased exponent used in the representation of a float
    public int exampleGetExponentFloat(float value) {
        return Math.getExponent(value);
    }

    // Converts an angle measured in radians to an approximately equivalent angle measured in degrees
    public double exampleToDegrees(double angle) {
        return Math.toDegrees(angle);
    }

    // Converts an angle measured in degrees to an approximately equivalent angle measured in radians
    public double exampleToRadians(double angle) {
        return Math.toRadians(angle);
    }

    public static void main(String[] args) {
        /*
        Math has a private constructor and final so it cannot be instantiated and also extended
         */
        MathMethodsExamples examples = new MathMethodsExamples();

        // Demonstrate each method
        log.info("abs(-10): " + examples.exampleAbsInt(-10));
        log.info("abs(-10.5): " + examples.exampleAbsDouble(-10.5));
        log.info("max(5, 10): " + examples.exampleMaxInt(5, 10));
        log.info("max(5.5, 10.5): " + examples.exampleMaxDouble(5.5, 10.5));
        log.info("min(5, 10): " + examples.exampleMinInt(5, 10));
        log.info("min(5.5, 10.5): " + examples.exampleMinDouble(5.5, 10.5));
        log.info("cos(Math.PI): " + examples.exampleCos(Math.PI));
        log.info("sin(Math.PI): " + examples.exampleSin(Math.PI));
        log.info("tan(Math.PI): " + examples.exampleTan(Math.PI));
        log.info("acos(1.0): " + examples.exampleAcos(1.0));
        log.info("asin(1.0): " + examples.exampleAsin(1.0));
        log.info("atan(1.0): " + examples.exampleAtan(1.0));
        log.info("pow(2, 3): " + examples.examplePow(2, 3));
        log.info("sqrt(16): " + examples.exampleSqrt(16));
        log.info("log(Math.E): " + examples.exampleLog(Math.E));
        log.info("log10(100): " + examples.exampleLog10(100));
        log.info("ceil(5.1): " + examples.exampleCeil(5.1));
        log.info("floor(5.9): " + examples.exampleFloor(5.9));
        log.info("round(5.5f): " + examples.exampleRoundFloat(5.5f));
        log.info("round(5.5): " + examples.exampleRoundDouble(5.5));
        log.info("getExponent(1024.0): " + examples.exampleGetExponentDouble(1024.0));
        log.info("getExponent(1024.0f): " + examples.exampleGetExponentFloat(1024.0f));
        log.info("toDegrees(Math.PI): " + examples.exampleToDegrees(Math.PI));
        log.info("toRadians(180): " + examples.exampleToRadians(180));
    }
}

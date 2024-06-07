package ca.siva.chapter03;

/*
 NOTE:
  1) A class defined in permits must extend its parent class.
 */
// Correct scenario: Declaring a sealed class with permitted subclasses
abstract sealed class Shape permits Circle, Square, Triangle {
    // Common properties and methods for all shapes
    public abstract double area();
}

// Permitted subclass extending the sealed class
final class Circle extends Shape {
    private final double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }
}

// Permitted subclass extending the sealed class
final class Square extends Shape {
    private final double side;

    public Square(double side) {
        this.side = side;
    }

    @Override
    public double area() {
        return side * side;
    }
}

// Permitted subclass extending the sealed class
non-sealed class Triangle extends Shape {
    private final double base;
    private final double height;

    public Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    @Override
    public double area() {
        return 0.5 * base * height;
    }
}

// Class extending the non-sealed subclass
class EquilateralTriangle extends Triangle {
    public EquilateralTriangle(double side) {
        super(side, Math.sqrt(3) / 2 * side);
    }

    @Override
    public double area() {
        return super.area();
    }
}

// Incorrect scenario: This class is listed in the permits clause but does not extend Shape
// Uncommenting this class and adding it to the permits clause of Shape will cause a compilation error
/*
public final class Rectangle {
    private final double length;
    private final double breadth;

    public Rectangle(double length, double breadth) {
        this.length = length;
        this.breadth = breadth;
    }

    public double area() {
        return length * breadth;
    }
}
*/

// Main class to test the implementation
public class SealedClassExample {
    public static void main(String[] args) {
        // Correct usage: Instances of permitted subclasses
        Shape circle = new Circle(5);
        System.out.println("Circle area: " + circle.area());

        Shape square = new Square(4);
        System.out.println("Square area: " + square.area());

        Shape triangle = new Triangle(3, 6);
        System.out.println("Triangle area: " + triangle.area());

        Shape equilateralTriangle = new EquilateralTriangle(3);
        System.out.println("Equilateral Triangle area: " + equilateralTriangle.area());

        // Incorrect usage: Attempting to instantiate a non-permitted subclass (if it were allowed)
        // Uncommenting the following lines will cause a compilation error if Rectangle is listed in permits
        // Shape rectangle = new Rectangle(4, 5);
        // System.out.println("Rectangle area: " + rectangle.area());
    }
}

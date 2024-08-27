package ca.siva.chapter03;

import lombok.extern.slf4j.Slf4j;

/*
 NOTE:
  1) A class defined in permits must extend its parent class.
  2) A sealed applied to a class does not have to define permits if all the subclasses are defined in the same class file.
  3) Nested static classes can also have sealed, non-sealed and final modifiers.
  4) Final Enums: Enums without constants that define class bodies are implicitly final, meaning they cannot be subclassed.
  5) Sealed Enums: Enums with at least one constant that defines a class body are implicitly sealed, and only the anonymous inner classes (enum constants with class bodies) are allowed to subclass the enum.
  6) Why sealed Modifier is Illegal: The language enforces these rules implicitly, so there's no need to mark an enum as sealed explicitly. Doing so is redundant and not allowed.
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

/*
 >> This is illegal, as here there's no class body for enum constants, means Color enum is implicitly final.
public sealed enum Color {
    RED, GREEN, BLUE;
}
*/

// Here Operation is sealed class that permits ADD and SUBTRACT anonymous classes to implement.
enum Operation {
    ADD {
        @Override
        public int apply(int a, int b) {
            return a + b;
        }
    },
    SUBTRACT {
        @Override
        public int apply(int a, int b) {
            return a - b;
        }
    };

    public abstract int apply(int a, int b);
}


sealed class Shape1 {
    public sealed class Circle1 extends Shape1 {
        public final class CircleSegment extends Circle1 {}
    }

    public final class Rectangle extends Shape1 {}

    public sealed class Triangle1 extends Shape1 permits Triangle1.RightTriangle1 {
        public final class RightTriangle1 extends Triangle1 {}
    }
}


sealed class Outer permits Outer.Inner1, Outer.Inner2 {
    public static final class Inner1 extends Outer {}

    public static sealed class Inner2 extends Outer permits Outer.Inner3 {
        // Inner3 is a nested static class permitted to extend Inner2
    }

    public static non-sealed class Inner3 extends Inner2 {
        // Inner3 is non-sealed and can have subclasses
    }
}


// Main class to test the implementation
@Slf4j
public class SealedClassExample {
    public static void main(String[] args) {
        // Correct usage: Instances of permitted subclasses
        Shape circle = new Circle(5);
        log.info("Circle area: " + circle.area());

        Shape square = new Square(4);
        log.info("Square area: " + square.area());

        Shape triangle = new Triangle(3, 6);
        log.info("Triangle area: " + triangle.area());

        Shape equilateralTriangle = new EquilateralTriangle(3);
        log.info("Equilateral Triangle area: " + equilateralTriangle.area());

        // Incorrect usage: Attempting to instantiate a non-permitted subclass (if it were allowed)
        // Uncommenting the following lines will cause a compilation error if Rectangle is listed in permits
        // Shape rectangle = new Rectangle(4, 5);
        // log.info("Rectangle area: " + rectangle.area());
    }
}


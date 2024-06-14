package ca.siva.chapter03;

import lombok.extern.slf4j.Slf4j;

/*
NOTE:
1) You can access and modify a private field of an object within the same class using a public or protected method,
even from a static context like the main method. However, you cannot directly access a private field from outside the class without using reflection.
2) It is fine to define a static or instance method with the same name as class name.
3) "var" will not compile when declared in the class as a field data type.
4) "var" can be declared as a variable name. (var var = 5) this is valid.
5) It is fine to instantiate in the form of anonymous class with the actual class.
This means anonymous class is extending the defined class and overriding them method.
6) It is fine to have constructor with same variable name as the static variable with the same name.
   Same goes with instance variables as well. Also, you cannot have same variable name for static and instance variable.
7) When an abstract class implements an interface,
it can leave the method not implemented and let the actual class that extends the abstract class take care of its implementation.
8) You cannot have a class to implement 2 interfaces which has a same method(default or abstract) defined.
9) Always when using "var" as type, it's value need to be defined as well inorder to be inferred during the compile time.
10) You cannot define a static method and an instance method with the same name. The code will not compile.
11) In Java, the term package-private (or default access level) refers to a method, variable, or class member that is accessible only within its own package. It is not accessible from any other package, including subpackages.
12) In Java, when a constructor in a child class calls another constructor of the same class using this(args), the call to the superclass constructor (super()) is not automatically inserted.

 */
@Slf4j
public class ClassExample {
    // Outer class variables
    private static final String staticOuterVariable = "Static Outer Variable";
    // Static variable example from user request
    private static String staticVar;
    private String instanceOuterVariable = "Instance Outer Variable";

    // Constructor with parameter having the same name as the static variable
    public ClassExample(String staticVar) {
        // Assigning parameter to static variable using the class name to differentiate
        ClassExample.staticVar = staticVar;
    }

    // Static method with the same name as an instance method
    public static void exampleMethod() {
        log.info("Static method called.");
    }

    // Instance method with the same name as a static method
    public void exampleMethod(String message) {
        log.info("Instance method called with message: " + message);
    }

    // Scenario from the image
    public static void abstractUseCase() {
        interface CanBurrow {
            void burrow();
        }

        @FunctionalInterface
        interface HasHardShell extends CanBurrow {
        }

        abstract class Tortoise implements HasHardShell {
            public abstract int toughness();
        }

        class DesertTortoise extends Tortoise {
            public int toughness() {
                return 11;
            }

            public void burrow() {
                // Implementation of burrow method
            }
        }
    }

    // Scenario from question 61
    public static void interfaceUseCase() {
        interface Building {
            default Double getHeight() {
                return 1.0;
            } // m1

            Double getWidth();
        }

        interface Office {
            default String getHeight() {
                return null;
            } // m2

            String getWidth();
        }

        // This will not work since you have same method defined in both the interfaces and also methods that are not default as well.
        // abstract class Tower implements Building, Office { // m3
        // }

        // class Restaurant extends Tower {
        //     @Override
        //     public Double getHeight() {
        //         return super.getHeight();
        //     } // m4
    }

    public static void main(String[] args) {
        // Using enum from the outer class
        ClassExample.OuterEnum outerEnum = ClassExample.OuterEnum.ONE;
        log.info("OuterEnum: " + outerEnum); // Output: OuterEnum: ONE

        // Static nested class instance
        ClassExample.StaticNestedClass staticNested = new ClassExample.StaticNestedClass();
        staticNested.display(); // Static Outer Variable

        // Using enum from the static nested class
        ClassExample.StaticNestedClass.NestedStaticEnum staticEnum = ClassExample.StaticNestedClass.NestedStaticEnum.A;
        log.info("NestedStaticEnum: " + staticEnum); // Output: NestedStaticEnum: A

        // Non-static inner class instance
        ClassExample outer = new ClassExample("New Static Variable Value");
        outer.instanceOuterVariable = "Instance Outer Variable Modified";
        ClassExample.InnerClass inner = outer.new InnerClass();
        inner.display(); // Static Outer Variable
        // Instance Outer Variable Modified

        // Using enum from the non-static inner class
        ClassExample.InnerClass.NestedInnerEnum innerEnum = ClassExample.InnerClass.NestedInnerEnum.X;
        log.info("NestedInnerEnum: " + innerEnum); // Output: NestedInnerEnum: X

        // Construct innerclass object
        ClassExample ce = new ClassExample("Another Static Variable Value");
        ClassExample.InnerClass ic = ce.new InnerClass();

        // Local class usage
        outer.methodWithLocalClass(); // Local Variable
        // Instance Outer Variable Modified
        // Static Outer Variable

        // Anonymous class usage
        outer.methodWithAnonymousClass(); // Local Variable
        // Instance Outer Variable Modified
        // Static Outer Variable

        // This is valid
        var var = "1";

        // Running example from question 48
        outer.exampleFromQuestion48(); // Output: GiantRobot GiantRobot

        // Displaying the static variable value
        log.info("StaticVar: " + staticVar); // Output: StaticVar: Another Static Variable Value

        // Running scenario from the image
        abstractUseCase(); // Output: Compilation error (since it won't compile)

        // Running scenario from question 61
        interfaceUseCase(); // This will highlight the compilation error

        // Calling the static method
        ClassExample.exampleMethod();

        // Calling the instance method
        outer.exampleMethod("Hello from instance method");
    }

    public void ClassExample() {
        // This is a valid declaration
        int Integer;

        //This is an invalid declaration
        // Integer int;
        // this is fine to define a method with the same class name
    }

    // Method containing a local class
    public void methodWithLocalClass() {
        // Local variable
        final String localVariable = "Local Variable";

        // Local class
        class LocalClass {
            public void display() {
                // Can access local variables (must be final or effectively final)
                log.info(localVariable);
                // Can access instance variables of the outer class
                log.info(instanceOuterVariable);
                // Can access static variables of the outer class
                log.info(staticOuterVariable);
            }
        }

        LocalClass local = new LocalClass();
        local.display();
    }

    // Method containing an anonymous class
    public void methodWithAnonymousClass() {
        // Local variable
        final String localVariable = "Local Variable";

        // Anonymous class
        Runnable anonymousClass = new Runnable() {
            @Override
            public void run() {
                // Can access local variables (must be final or effectively final)
                log.info(localVariable);
                // Can access instance variables of the outer class
                log.info(instanceOuterVariable);
                // Can access static variables of the outer class
                log.info(staticOuterVariable);
            }
        };

        anonymousClass.run();
    }

    // Example from question 48
    public void exampleFromQuestion48() {
        interface Toy {
            String play();
        }

        abstract class Robot implements Toy {
        }

        class Transformer extends Robot {
            public final String name = "GiantRobot";

            public String play() {
                return "DinosaurRobot";
            } // y1
        }

        Transformer prime = new Transformer() {
            public String play() {
                return name;
            } // y2
        };

        System.out.print(prime.play() + " " + prime.name);
    }

    // Enum in the outer class
    public enum OuterEnum {
        ONE, TWO, THREE
    }

    // Static nested class
    public static class StaticNestedClass {
        public void display() {
            // Can access static variables of the outer class
            log.info(staticOuterVariable);
            // Cannot access instance variables of the outer class
            // log.info(instanceOuterVariable); // Compilation error
        }

        // Enum inside static nested class
        public enum NestedStaticEnum {
            A, B, C
        }
    }

    // Non-static inner class
    public class InnerClass {
        public void display() {
            // Can access both static and instance variables of the outer class
            log.info(staticOuterVariable);
            log.info(instanceOuterVariable);
        }

        // Enum inside non-static inner class
        public enum NestedInnerEnum {
            X, Y, Z
        }
    }
}

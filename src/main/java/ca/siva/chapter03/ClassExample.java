package ca.siva.chapter03;

import lombok.extern.slf4j.Slf4j;

/*
NOTE:
1) You can access and modify a private field of an object within the same class using a public or protected method,
even from a static context like the main method. However, you cannot directly access a private field from outside the class without using reflection.
2) It is fine to define a static or instance method with the same name as class name.
3) "var" will not compile when declared in the class as a field data type.
4) "var" can be declared as a variable name. (var var = 5) this is valid.
 */
@Slf4j
public class ClassExample {
    // Outer class variables
    private static String staticOuterVariable = "Static Outer Variable";
    private String instanceOuterVariable = "Instance Outer Variable";

    // Enum in the outer class
    public enum OuterEnum {
        ONE, TWO, THREE
    }

    public void ClassExample() {
        // this is fine to define a method with the same class name
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

    public static void main(String[] args) {
        // Using enum from the outer class
        OuterEnum outerEnum = OuterEnum.ONE;
        log.info("OuterEnum: " + outerEnum); // Output: OuterEnum: ONE

        // Static nested class instance
        ClassExample.StaticNestedClass staticNested = new ClassExample.StaticNestedClass();
        staticNested.display(); // Static Outer Variable

        // Using enum from the static nested class
        StaticNestedClass.NestedStaticEnum staticEnum = StaticNestedClass.NestedStaticEnum.A;
        log.info("NestedStaticEnum: " + staticEnum); // Output: NestedStaticEnum: A

        // Non-static inner class instance
        ClassExample outer = new ClassExample();
        outer.instanceOuterVariable = "Instance Outer Variable Modified";
        ClassExample.InnerClass inner = outer.new InnerClass();
        inner.display(); // Static Outer Variable
        // Instance Outer Variable Modified

        // Using enum from the non-static inner class
        InnerClass.NestedInnerEnum innerEnum = InnerClass.NestedInnerEnum.X;
        log.info("NestedInnerEnum: " + innerEnum); // Output: NestedInnerEnum: X

        // Construct innerclass object
        ClassExample ce = new ClassExample();
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
    }
}

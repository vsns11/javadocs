package ca.siva.chapter03;

/*
NOTE:
1)  you can access and modify a private field of an object within the same class using a public or protected method,
even from a static context like the main method.
 However, you cannot directly access a private field from outside the class without using reflection.
 */
public class ClassExample {
    // Outer class variables
    private static String staticOuterVariable = "Static Outer Variable";
    private String instanceOuterVariable = "Instance Outer Variable";

    // Static nested class
    public static class StaticNestedClass {
        public void display() {
            // Can access static variables of the outer class
            System.out.println(staticOuterVariable);
            // Cannot access instance variables of the outer class
            // System.out.println(instanceOuterVariable); // Compilation error
        }
    }

    // Non-static inner class
    public class InnerClass {
        public void display() {
            // Can access both static and instance variables of the outer class
            System.out.println(staticOuterVariable);
            System.out.println(instanceOuterVariable);
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
                System.out.println(localVariable);
                // Can access instance variables of the outer class
                System.out.println(instanceOuterVariable);
                // Can access static variables of the outer class
                System.out.println(staticOuterVariable);
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
                System.out.println(localVariable);
                // Can access instance variables of the outer class
                System.out.println(instanceOuterVariable);
                // Can access static variables of the outer class
                System.out.println(staticOuterVariable);
            }
        };

        anonymousClass.run();
    }

    public static void main(String[] args) {
        // Static nested class instance
        ClassExample.StaticNestedClass staticNested = new ClassExample.StaticNestedClass();
        staticNested.display(); // Static Outer Variable

        // Non-static inner class instance
        ClassExample outer = new ClassExample();
        outer.instanceOuterVariable = "";
        ClassExample.InnerClass inner = outer.new InnerClass();
        inner.display(); // Static Outer Variable
        // Instance Outer Variable

        // Local class usage
        outer.methodWithLocalClass(); // Local Variable
        // Instance Outer Variable
        // Static Outer Variable

        // Anonymous class usage
        outer.methodWithAnonymousClass(); // Local Variable
        // Instance Outer Variable
        // Static Outer Variable
    }
}

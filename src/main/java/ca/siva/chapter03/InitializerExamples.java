package ca.siva.chapter03;

/*
Note:
1) Inline Initialization (Instance Variables): Instance variables are initialized first, in the order they are declared.
Instance Initializer Blocks: These blocks are executed next, in the order they appear in the class.
Constructor: Finally, the constructor is executed.

 */
public class InitializerExamples {
    // Static variable
    private static String staticVariable;

    // Instance variable
    private String instanceVariable;

    // Static initializer block
    static {
        staticVariable = "Initialized in static initializer";
        System.out.println("Static initializer executed.");
    }

    // Instance initializer block
    {
        instanceVariable = "Initialized in instance initializer";
        System.out.println("Instance initializer executed.");
    }

    // Constructor
    public InitializerExamples(String value) {
        instanceVariable = value;
        System.out.println("Constructor executed.");
    }

    // Method to demonstrate that instance initializers cannot be used within methods
    public void methodWithLocalClass() {
        System.out.println("-- ENTER methodWithLocalClass() --");
        // Local variable
        final String localVariable = "Local Variable";

        // Local class
        class LocalClass {
            public void display() {
                System.out.println(localVariable); // Accesses local variable
                System.out.println(instanceVariable); // Accesses instance member
                System.out.println(staticVariable); // Accesses static member
            }
        }

        LocalClass local = new LocalClass();
        local.display();
        System.out.println("-- EXIT methodWithLocalClass() --");
    }

    // Method to demonstrate that instance initializers cannot be used within methods
    public void methodWithAnonymousClass() {
        // Local variable
        final String localVariable = "Local Variable";

        // Anonymous class
        Runnable anonymousClass = new Runnable() {
            @Override
            public void run() {
                System.out.println(localVariable); // Accesses local variable
                System.out.println(instanceVariable); // Accesses instance member
                System.out.println(staticVariable); // Accesses static member
            }
        };

        anonymousClass.run();
    }

    public static void testStaticInitializer() {
        {
            System.out.println("Inside an initializer#1");
        }
        {
            System.out.println("Inside an initializer#2");
        }
    }

    public void testInitializer() {
        {
            System.out.println("Inside an initializer#1");
        }
        {
            System.out.println("Inside an initializer#2");
        }
    }
    public static void main(String[] args) {
        // Static initializer block will be executed when the class is loaded

        // Instance initializer and constructor execution
        InitializerExamples example1 = new InitializerExamples("Value set in constructor");
        // Output:
        // Static initializer executed.
        // Instance initializer executed.
        // Constructor executed.

        // Local class usage
        example1.methodWithLocalClass();
        // Output:
        // Local Variable
        // Value set in constructor
        // Initialized in static initializer

        // Anonymous class usage
//        example1.methodWithAnonymousClass();
        // Output:
        // Local Variable
        // Value set in constructor
        // Initialized in static initializer

        // Creating another instance to demonstrate instance initializer execution
//        InitializerExamples example2 = new InitializerExamples("Another value");
        // Output:
        // Instance initializer executed.
        // Constructor executed.

        example1.testInitializer();
        InitializerExamples.testStaticInitializer();
    }
}

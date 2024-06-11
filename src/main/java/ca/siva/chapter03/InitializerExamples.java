package ca.siva.chapter03;

import lombok.extern.slf4j.Slf4j;

/*
Note:
1) Inline Initialization (Instance Variables): Instance variables are initialized first, in the order they are declared.
Instance Initializer Blocks: These blocks are executed next, in the order they appear in the class.
Constructor: Finally, the constructor is executed.

 */
@Slf4j
public class InitializerExamples {
    // Static variable
    private static String staticVariable;

    // Instance variable
    private String instanceVariable;

    // Static initializer block
    static {
        staticVariable = "Initialized in static initializer";
        log.info("Static initializer executed.");
    }

    // Instance initializer block
    {
        instanceVariable = "Initialized in instance initializer";
        log.info("Instance initializer executed.");
    }

    // Constructor
    public InitializerExamples(String value) {
        instanceVariable = value;
        log.info("Constructor executed.");
    }

    // Method to demonstrate that instance initializers cannot be used within methods
    public void methodWithLocalClass() {
        log.info("-- ENTER methodWithLocalClass() --");
        // Local variable
        final String localVariable = "Local Variable";

        // Local class
        class LocalClass {
            public void display() {
                log.info(localVariable); // Accesses local variable
                log.info(instanceVariable); // Accesses instance member
                log.info(staticVariable); // Accesses static member
            }
        }

        LocalClass local = new LocalClass();
        local.display();
        log.info("-- EXIT methodWithLocalClass() --");
    }

    // Method to demonstrate that instance initializers cannot be used within methods
    public void methodWithAnonymousClass() {
        // Local variable
        final String localVariable = "Local Variable";

        // Anonymous class
        Runnable anonymousClass = new Runnable() {
            @Override
            public void run() {
                log.info(localVariable); // Accesses local variable
                log.info(instanceVariable); // Accesses instance member
                log.info(staticVariable); // Accesses static member
            }
        };

        anonymousClass.run();
    }

    public static void testStaticInitializer() {
        {
            log.info("Inside an initializer#1");
        }
        {
            log.info("Inside an initializer#2");
        }
    }

    public void testInitializer() {
        {
            log.info("Inside an initializer#1");
        }
        {
            log.info("Inside an initializer#2");
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

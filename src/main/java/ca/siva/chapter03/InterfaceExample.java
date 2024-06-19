package ca.siva.chapter03;

import lombok.extern.slf4j.Slf4j;

// Interface with various kinds of methods and modifiers
/*
  NOTE:
  1) `In interface, declared variables are implicitly public static final`
  2) When 2 interfaces contains same method, always same default method's returntype should be same (if different means can't compile).
  Also, if given in the class with same method, that will get override at runtime.
  In order to access interface method, use <Interface>.<method>()
  3) To extend step 2, if method names are same in 2 interfaces and the same method is not overridden in class, then it won't compile.
    However, it also won't work for having the method with the same name and different return type.
  4) Private method cannot be called in a static method.
  5) It is not mandatory for the class to override a default method that's defined in an interface.
  6) In Java, you can define abstract methods in an interface. However, there are specific methods from the Object class (such as toString(), hashCode(), equals(), etc.) that you cannot declare as abstract methods in an interface. This is because every class in Java implicitly extends Object, and thus, these methods are always available. T

 */
interface ExampleInterface {

    static void testSameMethod() {

    }
    String CONSTA = "a";

    // Abstract method (implicitly public and abstract)
    void abstractMethod();

    // Default method (can have a body)
    default void defaultMethod() {
        System.out.println("This is a default method from ExampleInterface.");
    }

    // Static method (can have a body, belongs to the interface)
    static void staticMethod() {
        System.out.println("This is a static method.");
    }

    // Private method (can have a body, can be called from default methods)
    private void privateMethod() {
        System.out.println("This is a private method.");
    }

    // Private static method (can have a body, can be called from static methods)
    private static void privateStaticMethod() {
        System.out.println("This is a private static method.");
    }

    // Default method calling a private method
    default void defaultMethodWithPrivateCall() {
        privateMethod();
    }

    // Static method calling a private static method
    static void staticMethodWithPrivateCall() {
        privateStaticMethod();
    }
}

// Another interface with a default method having the same name
interface AnotherInterface {
    default void defaultMethod() {
        System.out.println("This is a default method from AnotherInterface.");
    }
    static void testSameMethod() {

    }
}

// Class implementing both interfaces
public class InterfaceExample implements ExampleInterface, AnotherInterface {

    // Implementing the abstract method
    @Override
    public void abstractMethod() {
        System.out.println("Implementing abstract method.");
    }

    // Overriding the default method to resolve conflict
    @Override
    public void defaultMethod() {
        System.out.println("Overriding the default method.");
    }

    // New method with the same name but different signature
    public void defaultMethod(String... args) {
        //This is the right way to call the specific interface method using super.
        ExampleInterface.super.defaultMethod();
        System.out.println("Method with varargs: " + String.join(", ", args));
    }

    private static void testSameMethod2() {
        // This will not compile because you have to define which method this call need to happen for
        //testSameMethod();
        ExampleInterface.testSameMethod(); // this works
        AnotherInterface.testSameMethod(); // this works
    }

    // Resolving the conflict by explicitly calling the default method from AnotherInterface
    public void callAnotherInterfaceDefaultMethod() {
         AnotherInterface.super.defaultMethod();
    }

    // Main method to test the implementation
    public static void main(String[] args) {
        InterfaceExample example = new InterfaceExample();
        example.abstractMethod(); // Implementing abstract method.
        example.defaultMethod(); // Overriding the default method.
        example.defaultMethod("arg1", "arg2"); // Method with varargs: arg1, arg2
        example.defaultMethodWithPrivateCall(); // This is a private method.

        ExampleInterface.staticMethod(); // This is a static method.
        ExampleInterface.staticMethodWithPrivateCall(); // This is a private static method.

        // Calling the resolved default method from AnotherInterface
        example.callAnotherInterfaceDefaultMethod();
    }
}

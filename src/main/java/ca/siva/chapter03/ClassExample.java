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
8)
   i. If two interfaces define the same abstract method (same method name, return type, and parameters),
a class that implements both interfaces only needs to provide one implementation for that method. There's no ambiguity here, and Java allows this.
   ii. If two interfaces define the same method as a default method, and a class implements both interfaces,
   the compiler will throw an error because it doesn't know which default method implementation to use. In this case, the implementing class must override the method and provide its own implementation to resolve the conflict.

9) Always when using "var" as type, it's value need to be defined as well inorder to be inferred during the compile time.
10) You cannot define a static method and an instance method with the same name with the exact same signature. The code will not compile.
    However, same method name with different parameters having different signature does allow it as java treats it as method overloading.
11) In Java, the term package-private (or default access level) refers to a method, variable, or class member that is accessible only within its own package.
 It is not accessible from any other package, including subpackages.
12) Constructor chaining: In Java, when a constructor in a child class calls another constructor of the same class using this(args),
the call to the superclass constructor (super()) is not automatically inserted.
13) In nested classes, when referring to variables with the same name, `this` refers to the innermost class variable,
    `OuterClassName.this` refers to the variable in the outer class, and
    `OuterClassName.InnerClassName.this` refers to the variable in the intermediate inner class.
14) To access instance variables in lambda expressions, they need to be effectively final or explicitly final rule applies to local variables within the enclosing scope of the lambda.
However, instance variables (fields of the class) and static variables do not have this restriction and can be accessed and modified freely within the lambda expression.
15) Static methods are not subject to polymorphism in Java, and they cannot be overridden in the same way that instance methods can.
    Because static methods are bound to the class, they cannot be overridden.
   If a subclass defines a static method with the same signature as a static method in the superclass,
   it hides the super class's static method rather than overriding it.
16) Same goes with abstract classes as well for static method, where method hiding is applied.
17) Method resolution process:
     1) Exact match: Compiler looks for a method where parameter types exactly match the argument types.
     2) Widening conversion: If no exact match, compiler tries to convert arguments to parameter types through widening byte to short, int, long, float, or double
            byte to short, int, long, float, or double
            short to int, long, float, or double
            char to int, long, float, or double
            int to long, float, or double
            long to float or double
            float to double
     3) Boxing and un-boxing: Next, compiler considers methods with boxing/unboxing conversions if needed.
     4) Varargs: Finally, compiler looks for methods accepting arguments via varargs if no other match found.
18) Instance method with same name cannot override the static method with same name.
19) When a field is declared as final, the initialization must be part of constructor, if not given the code won't compile.
20) In class name rules:
     1) _ can be defined in class name but not just _ as a class name won't work. Can start with "_"
     2) $ can be used in the class name as well. Can start with "$"
     3) Class name can be lowercase, it does not have to start with upper case.
     4) Numbers are allowed as well but not as a first character.
21) Object can be used to call both static and instance method having same, so cannot be overridden unless different method signature, hence it won't compile.
22) When using "protected" method in parent class, all the static(use object of child or parent),
instance methods in child classes able to access protected method parent class.
23) In an Immutable class, all the fields inside the class should be final and the getter methods should be final as well.
24) Super can be used to access parent class fields and methods when they are hidden by the child class.
25) Abstract method cannot be overridden by a final method, while in inheritance you can still define final on child class.
26) Compiler can detect circular dependency in the constructors, if such exists, the code won't compile.
27) Explicit casting from a parent class to a child class in Java is known as down-casting.
This is only safe when the actual object being referenced is an instance of the child class.
If the object is not an instance of the child class, a ClassCastException will be thrown at runtime.
It is only safe when the actual object is an instance of the child class.
28) If parent class method does not have "throws" and a child class's overriding method can have "throws <RuntimeException or unchecked exception or its subclasses>".
 In this case, child case cannot throw a checked exception since it is not defined in the parent class.
29) In interfaces/extending abstract classes, when a class implements an interface, the overriding method in the implementing class can:
    1) Throw the same checked exceptions as the interface method.
    2) Throw any unchecked exceptions( subclass of RuntimeException).
    3) Throw no exceptions at all applies even if the interface method throws a checked exception.
30) If you don’t explicitly call super() in a constructor,
the compiler will automatically insert a no-argument call to the superclass constructor (super())
 unless you call another constructor in the same class using this(args) (constructor chaining).
31) If the interface does not throw any checked exception,
the impl class of this interface cannot throw a new checked exceptions.
32) In java, a class can be named as abstract without defining an abstract method.
 */

@Slf4j
public class ClassExample {
    // Outer class variables
    private static final String staticOuterVariable = "Static Outer Variable";
    // Static variable example from user request
    private static String staticVar;
    private String instanceOuterVariable = "Instance Outer Variable";

    public ClassExample() {}
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

    static class Parent {
        public void display() {
            System.out.println("Display method in Parent");
        }
    }

    static class Child extends Parent {
        public void display() {
            System.out.println("Display method in Child");
        }

        public void specificMethod() {
            System.out.println("Specific method in Child");
        }
    }

    // Scenario from the image
    public static void nestedClassUseCase() {
        class Matrix {
            private int level = 1;

            class Deep {
                private int level = 2;

                class Deeper {
                    private int level = 5;

                    public void printReality(int level) {
                        System.out.print(this.level + " ");          // this.level refers to Deeper's level
                        System.out.print(Matrix.Deep.this.level + " "); // Matrix.Deep.this.level refers to Deep's level
                        System.out.print(Deep.this.level);           // Deep.this.level also refers to Deep's level
                    }
                }
            }

            public static void main(String[] bots) {
                Matrix.Deep.Deeper simulation = new Matrix().new Deep().new Deeper();
                simulation.printReality(6);
            }
        }

        // Simulating the main method call
        Matrix.main(new String[]{});
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

    // Scenario from question 130
    public static void chooseWiselyUseCase() {
        class ChooseWisely {
            public ChooseWisely() {
                super();
            }

            public int choose(int choice) {
                return 5;
            }

            public int choose(short choice) {
                return 2;
            }

            public int choose(long choice) {
                return 11;
            }

            public int choose(double choice) {
                return 6;
            }

            public int choose(Float choice) {
                return 8;
            }

            public static void main(String[] path) {
                ChooseWisely c = new ChooseWisely();
                System.out.println(c.choose(2f)); // Output: 6
                // Arithmetic operations by default convert both the operators to int, so calls choose method having int signature.
                System.out.println(c.choose((byte) 2 + 1)); // Output: 5
            }
        }

         class A {
            // This is good because the method is private. But wont work if you change to public.
            private static void test1() {}
             // This is good too
             private void test2() {}
         }
         class B extends  A {
            public void test1() {}
             public void test2() {}
         }

        // Simulating the main method call
        ChooseWisely.main(new String[]{});
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
        nestedClassUseCase(); // Output: 5 2 2

        // Running scenario from question 61
        interfaceUseCase(); // This will highlight the compilation error

        // Running scenario from question 130
        chooseWiselyUseCase(); // Output: 6 5

        // Calling the static method
        ClassExample.exampleMethod();

        // Calling the instance method
        outer.exampleMethod("Hello from instance method");

        // anonymous class that extends ClassExample class.
        new ClassExample(){ public  static final String test = "123";};

        // Running scenario where getEqualSides is removed from Square
        testSquareProgram();

        Parent parent = new Child(); // Upcasting
        parent.display(); // This will call the Child's display method

        // Downcasting to access child-specific methods
        if (parent instanceof Child) {
            Child child = (Child) parent;
            child.display(); // This will call the Child's display method
            child.specificMethod(); // This will call the Child's specific method
        }
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

    abstract class Trapezoid {
        private int getEqualSides() { return 0; } // x1
    }

    abstract class Rectangle extends Trapezoid {
        public static int getEqualSides() { return 2; } // x2
    }



    // Method to demonstrate the Square program with removed method
    static void testSquareProgram() {

        abstract class Trapezoid {
            private int getEqualSides() { return 0; }
        }

        abstract class Rectangle extends Trapezoid {
            public static int getEqualSides() { return 2; } // x1
        }

        class Square extends Rectangle {
            // Removed the getEqualSides method from Square

            public static void main(String[] corners) {
                final Square myFigure = new Square(); // x3
                System.out.print(myFigure.getEqualSides()); // This will cause a compilation error
            }
        }

        // Simulating the main method call
        Square.main(new String[]{});
    }
}

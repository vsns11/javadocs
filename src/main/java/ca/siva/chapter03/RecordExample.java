package ca.siva.chapter03;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.ArrayList;

/*
NOTE:
1) Record cannot extend other classes because it implicitly extends abstract Record class.
   They cannot be extended because they are implicitly final, which means they cannot be subclassed.
2) However, it can implement interfaces.
3) All the fields declared in Record are final declared variables.
4) Records do not allow instance initializers as well,
   as fields are final always so value need to be provided always at the time of initialization.
5) "sealed" classes will tell which classes or interfaces are allowed to extend/implement a given class.
6) "final" means the subclass cannot be extended by other classes.
7) Setting a value in a compact constructor is allowed.
8) In a compact constructor, the main use of it is to have checks before the field assignment.
   Records automatically generate the assignment initialization in the compact constructor at the end.
9) Only one compact constructor should need to be defined per class.
10) The compact constructor is called before the canonical constructor, ensuring that validation and logic are executed first,
    followed by the actual assignment of field values.
11) Example of sealed classes:
    public sealed interface Time permits Hour, Minute, Second {}
    record Hour() implements Time {}
    interface Minute extends Time {}
    non-sealed class Second implements Time {}
    class Micro extends Second {}
12) you can re-assign the variable twice in a compact constructor
13) Non-canonical constructor must always delegate the call to canonical constructor.
14) Only an interface or class can be marked as sealed.
15) Java records do not allow both a compact constructor and a canonical constructor to be defined at the same time. You must choose one or the other.
16) If both constructors are provided, the compiler will throw an error due to the ambiguity in how the fields should be initialized.
17) It is a compile-time error for a record declaration to declare a record component with the name clone, finalize, getClass, hashCode, notify, notifyAll, toString, or wait. Observe that all these are public or protected methods of Object class.
18) It can define static fields as well as instance and static methods.
19) record is allowed to have at most one varargs field and if present, it should be at the end of the header.
20) You may provide any number of constructors in a record. The first line of every non-canonical constructor must invoke another constructor.
 */

@Slf4j
public class RecordExample {

    public static void main(String[] args) {
        basicRecordExample();
        recordInCollectionExample();
        recordWithPatternMatchingExample(new Person("Alice", 30, Gender.FEMALE));
        recordWithPatternMatchingExample(new Person("Bob", 25, Gender.MALE));
        recordInSwitchExample(new Person("Alice", 30, Gender.FEMALE));
        recordInSwitchExample(new Person("Bob", 25, Gender.MALE));
        recordWithLabelsExample(List.of(
                new Person("Alice", 30, Gender.FEMALE),
                new Person("Bob", 25, Gender.MALE),
                new Person("Carol", 35, Gender.FEMALE),
                new Person("Siva", 27, Gender.MALE),
                new Person("John", -1), // Example with negative age
                new Person("Jane", -2) // Example with name only
        ));
        staticVariableExample();
        recordImplementingInterfaceExample();
        new RecordExample().sealedClassExample();
    }

    // 1. Basic record example
    public static void basicRecordExample() {
        Person person = new Person("Alice", 30, Gender.FEMALE);
        log.info("Basic record example: " + person);
    }

    // 2. Using records in collections
    public static void recordInCollectionExample() {
        List<Person> people = new ArrayList<>();
        people.add(new Person("Alice", 30, Gender.FEMALE));
        people.add(new Person("Bob", 25, Gender.MALE));
        people.add(new Person("Carol", 35, Gender.FEMALE));

        log.info("Records in collections example:");
        for (Person person : people) {
            log.info("{}", person);
        }
    }

    // 3. Using records with pattern matching and instanceof
    public static void recordWithPatternMatchingExample(Object obj) {
        if (obj instanceof Person p) {
            log.info("Pattern matching with instanceof: " + p.name() + " is " + p.age() + " years old and is " + p.gender());
        } else {
            log.info("Unknown object type.");
        }
    }

    // 4. Using records in switch expressions
    public static void recordInSwitchExample(Person person) {
        String description = switch (person.name()) {
            case "Alice" -> "Person is Alice";
            case "Bob" -> "Person is Bob";
            default -> "Person is someone else";
        };
        log.info("Record in switch example: " + description);
    }

    // 5. Demonstrating records in nested loops with labels
    public static void recordWithLabelsExample(List<Person> people) {
        String skipName = "Bob";

        outerLoop:
        for (Person person : people) {
            if (person.name().equals(skipName)) {
                log.info("Skipping person: " + person);
                continue outerLoop; // Use label to skip processing this person
            }
            log.info("Processing person: " + person);

            for (int i = 0; i < 3; i++) {
                if (i == 1 && person.name().equals("Alice")) {
                    log.info("Breaking inner loop for: " + person);
                    break; // Break inner loop
                }
                log.info("Inner loop iteration " + i + " for: " + person);
            }
        }
    }

    // 6. Static variables example
    public static void staticVariableExample() {
        log.info("Total Persons created: " + Person.personCount);
    }

    // 7. Records implementing interfaces
    public static void recordImplementingInterfaceExample() {
        Greetable greeter = new Person("Dave", 40, Gender.MALE);
        greeter.greet();
    }

    // 8. Example of sealed classes and interfaces
    public void sealedClassExample() {
        Time hour = new Hour();
        Time minute = new Minute() {};
        Time second = new Second();
        Second micro = new Micro();

        log.info("Sealed class example: Hour instance created: {}", hour);
        log.info("Sealed class example: Minute instance created: {}", minute);
        log.info("Sealed class example: Second instance created: {}", second);
        log.info("Sealed class example: Micro instance created: {}", micro);
    }

    // Interface definition
    public interface Greetable {
        void greet();
    }

    // Record definition
    public record Person(String name, int age, Gender gender) implements Greetable {
        // Static variable
        public static int personCount = 0;

        // Static initializer
        static {
            personCount++;
            log.info("Executing the static initializer");
        }

        // Compact Constructor with additional validation and assignment
        public Person {
            age = 1;
            age = 3;
            log.info("Inside compact constructor");
            if (age < 0) {
                log.warn("Age is negative, setting age to 0");
                age = 0; // Assignment within the compact constructor
            }
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Name cannot be null or blank");
            }
        }

        // this will not compile because you can have a chained constructor + compact, but not compact and canonical constructor doing assii

//        public Person(String name, int age, Gender gender) {
//            this.name = name;
//            this.age = age;
//            this.gender = gender;
//        }


        //This is how you can override the getter inside a record.

        @Override
        public int age() {
            return age;
        }
        // Custom Constructor (name only)

        // Constructor with transformations (optional)
        public Person(String name, int age) {
            this(name, age, Gender.OTHER); // Default gender to OTHER
        }

        // Implementing the interface method
        @Override
        public void greet() {
            log.info("Hello, my name is " + name + " and I am " + age + " years old.");
        }
    }

    // Enum definition
    public enum Gender {
        MALE, FEMALE, OTHER
    }

    // Sealed interface and classes example
    public sealed interface Time permits Hour, Minute, Second {}

    public record Hour() implements Time {}

    public non-sealed interface Minute extends Time {}

    public non-sealed class Second implements Time {}

    public class Micro extends Second {}
}

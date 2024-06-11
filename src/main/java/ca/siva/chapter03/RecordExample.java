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
4) Records does not allow instance initializers as well,
    as fields are final always so value need to be provided always at the time of initialization.
5) "sealed" classes will tell which classes or interfaces are allowed to extend/implement a given class.
6) "final" means the subclass cannot be extended by other classes
7) Setting a value in compact constructor is not allowed.
8) In a compact constructor, the main use of it is to have checks before the field assignment.
Records automatically generates the assignment initialization in the compact constructor at the end.

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
                new Person("Carol", 35, Gender.FEMALE)
        ));
        staticVariableExample();
        recordImplementingInterfaceExample();
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

        // Constructor with additional validation
        public Person {
            if (age < 0) {
                throw new IllegalArgumentException("Age cannot be negative");
            }
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
}

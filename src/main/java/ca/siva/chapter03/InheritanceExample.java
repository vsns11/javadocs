package ca.siva.chapter03;

import lombok.extern.slf4j.Slf4j;

// Base class
@Slf4j
class Animal {
    public void eat() {
        log.info("This animal eats food.");
    }

    protected void sleep() {
        log.info("This animal sleeps.");
    }

    private void breath() {
        log.info("This animal breathes.");
    }

    void roam() {
        log.info("This animal roams around.");
    }
}

// Derived class
@Slf4j
class Dog extends Animal {
    // Overriding the public method
    @Override
    public void eat() {
        log.info("The dog eats bones.");
    }

    // Overriding the protected method and expanding visibility to public
    @Override
    public void sleep() {
        log.info("The dog sleeps in the kennel.");
    }

    // Declaring a new method with the same name as the private method in the superclass
    // This is method hiding, not method overriding
    public String breath() {
        log.info("The dog breathes quickly.");
        return "Dog is breathing";
    }

    // Overriding the default access method and expanding visibility to protected
    @Override
    protected void roam() {
        log.info("The dog roams around the house.");
    }

    // Overriding, you cannot reduce the modifier, while you can increase the modifier
//    @Override
//    private void roam() {
//        log.info("The dog roams around the house.");
//
//    }
}

@Slf4j
public class InheritanceExample {
    public static void main(String[] args) {
        Animal genericAnimal = new Animal();
        Dog dog = new Dog();
        Animal polyDog = new Dog(); // Polymorphic reference

        // Testing methods on Animal instance
        log.info("Animal:");
        genericAnimal.eat();   // This animal eats food.
        genericAnimal.sleep(); // This animal sleeps.
        // genericAnimal.breath(); // Compilation error: breath() has private access in Animal
        genericAnimal.roam();  // This animal roams around.

        // Testing methods on Dog instance
        log.info("\nDog:");
        dog.eat();   // The dog eats bones.
        dog.sleep(); // The dog sleeps in the kennel.
        log.info(dog.breath()); // The dog breathes quickly. -> Dog is breathing
        dog.roam();  // The dog roams around the house.

        // Testing methods on polymorphic reference
        log.info("\nPolymorphic Dog:");
        polyDog.eat();   // The dog eats bones.
        polyDog.sleep(); // The dog sleeps in the kennel.
        // polyDog.breath(); // Compilation error: breath() has private access in Animal
        polyDog.roam();  // The dog roams around the house.
    }
}

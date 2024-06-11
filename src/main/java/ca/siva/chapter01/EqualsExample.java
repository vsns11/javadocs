package ca.siva.chapter01;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class EqualsExample {

    static class Person {
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return age == person.age && Objects.equals(name, person.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age);
        }
    }

    /*
     Always remember to override both hashcode
    (when used with hash tables to make sure same key has same hash value) and equals method.

     The default equals method in object is to compare only memory addresses of objects not values.
     So, most of the classes they override to provide value based comparison.

     */
    public static void main(String[] args) {
        Person p1 = new Person("Alice", 30);
        Person p2 = new Person("Alice", 30);
        Person p3 = new Person("Bob", 25);

        log.info("{}", p1.equals(p2)); // true
        log.info("{}", p1.equals(p3)); // false
    }

}

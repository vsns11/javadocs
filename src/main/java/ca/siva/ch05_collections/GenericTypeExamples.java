package ca.siva.ch05_collections;
import java.util.*;

import lombok.extern.slf4j.Slf4j;

/*
 1) You can use ? as a wildcard in variable declarations, method parameters, and return types to represent an unknown type.
 2) When adding elements to arrayList using ? super cls, the element can be cls type or subtype of cls.
 3) When using a wildcard like ? or ? extends T, you can read elements as type T, but you cannot add elements to the collection because the exact type of the elements is unknown (only that they are at least of type T).
 */
@Slf4j
public class GenericTypeExamples {

    public static void main(String[] args) {
        GenericTypeExamples examples = new GenericTypeExamples();

        examples.exampleArrayListWithoutGenericType();
        examples.exampleArrayListWithGenericType();
        examples.exampleMapWithoutGenericType();
        examples.exampleMapWithGenericType();
        examples.exampleRawTypeMapWithParameterizedType();
    }

    // Example of ArrayList without generic type (raw type)
    // Output: ArrayList elements: [Hello, 123]
    // Output: Retrieved element: Hello
    // Output: Retrieved element: 123
    public void exampleArrayListWithoutGenericType() {
        List list = new ArrayList();  // Raw type
        list.add("Hello");
        list.add(123);  // Adding Integer to raw type list

        log.info("ArrayList elements: {}", list);

        for (Object element : list) {
            log.info("Retrieved element: {}", element);
        }
    }

    // Example of ArrayList with generic type
    // Output: ArrayList elements: [Hello, World]
    // Output: Retrieved element: Hello
    // Output: Retrieved element: World
    public void exampleArrayListWithGenericType() {
        List<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("World");
        // list.add(123);  // Compile-time error

        log.info("ArrayList elements: {}", list);

        for (String element : list) {
            log.info("Retrieved element: {}", element);
        }

    }

    // Example of Map without generic type (raw type)
    // Output: Map elements: {Key1=Hello, Key2=123}
    // Output: Retrieved value: Hello
    // Output: Retrieved value: 123
    public void exampleMapWithoutGenericType() {
        Map map = new HashMap();  // Raw type
        map.put("Key1", "Hello");
        map.put("Key2", 123);  // Adding Integer to raw type map

        log.info("Map elements: {}", map);

        for (Object key : map.keySet()) {
            log.info("Retrieved value: {}", map.get(key));
        }
    }

    // Example of Map with generic type
    // Output: Map elements: {Key1=Hello, Key2=World}
    // Output: Retrieved value: Hello
    // Output: Retrieved value: World
    public void exampleMapWithGenericType() {
        Map<String, String> map = new HashMap<>();
        map.put("Key1", "Hello");
        map.put("Key2", "World");
        // map.put("Key3", 123);  // Compile-time error

        log.info("Map elements: {}", map);

        for (String key : map.keySet()) {
            log.info("Retrieved value: {}", map.get(key));
        }
    }

    // Example of raw type Map with parameterized type HashMap
    // Output: Map elements: {Key1=Hello, Key2=123}
    // Output: Retrieved value: Hello
    // Output: Retrieved value: 123
    public void exampleRawTypeMapWithParameterizedType() {
        Map map = new HashMap<String, String>();  // Raw type variable, parameterized constructor
        map.put("Key1", "Hello");
        map.put("Key2", 123);  // Adding Integer to raw type map

        log.info("Map elements: {}", map);

        for (Object key : map.keySet()) {
            log.info("Retrieved value: {}", map.get(key));
        }
    }

    public void exampleGetExceptions(Collection<? super RuntimeException> coll) {
        coll.add(new RuntimeException());
//        coll.add(new Exception());  // This will not compile because only subclasses are allowed
    }
}

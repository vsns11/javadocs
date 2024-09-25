package ca.siva.ch05_collections;

import lombok.extern.slf4j.Slf4j;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class HashMapExamples {

    public static void main(String[] args) {
        HashMapExamples examples = new HashMapExamples();

        examples.exampleAddElements();
        examples.exampleRemoveElements();
        examples.exampleIterateHashMap();
        examples.exampleCheckKeyOrValue();
        examples.exampleGetValues();
        examples.exampleReplaceElement();
    }

    // Example of adding elements to a HashMap
    // Output: HashMap after adding elements: {apple=1, banana=2, cherry=3}
    public void exampleAddElements() {
        Map<String, Integer> hashMap = new HashMap<>();
        hashMap.put("apple", 1);
        hashMap.put("banana", 2);
        hashMap.put("cherry", 3);

        log.info("HashMap after adding elements: {}", hashMap);
    }

    // Example of removing elements from a HashMap
    // Output: HashMap after removing 'banana': {apple=1, cherry=3}
    public void exampleRemoveElements() {
        Map<String, Integer> hashMap = new HashMap<>();
        hashMap.put("apple", 1);
        hashMap.put("banana", 2);
        hashMap.put("cherry", 3);

        hashMap.remove("banana");
        log.info("HashMap after removing 'banana': {}", hashMap);
    }

    // Example of iterating over a HashMap
    // Output: Iterating over HashMap:
    // Key: apple, Value: 1
    // Key: banana, Value: 2
    // Key: cherry, Value: 3
    public void exampleIterateHashMap() {
        Map<String, Integer> hashMap = new HashMap<>();
        hashMap.put("apple", 1);
        hashMap.put("banana", 2);
        hashMap.put("cherry", 3);

        log.info("Iterating over HashMap:");
        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            log.info("Key: {}, Value: {}", entry.getKey(), entry.getValue());
        }
    }

    // Example of checking if a key or value exists in a HashMap
    // Output: Does key 'banana' exist? true
    // Does value 3 exist? true
    public void exampleCheckKeyOrValue() {
        Map<String, Integer> hashMap = new HashMap<>();
        hashMap.put("apple", 1);
        hashMap.put("banana", 2);
        hashMap.put("cherry", 3);

        boolean keyExists = hashMap.containsKey("banana");
        boolean valueExists = hashMap.containsValue(3);

        log.info("Does key 'banana' exist? {}", keyExists);
        log.info("Does value 3 exist? {}", valueExists);
    }

    // Example of getting values from a HashMap
    // Output: Values in HashMap: [1, 2, 3]
    public void exampleGetValues() {
        Map<String, Integer> hashMap = new HashMap<>();
        hashMap.put("apple", 1);
        hashMap.put("banana", 2);
        hashMap.put("cherry", 3);

        log.info("Values in HashMap: {}", hashMap.values());
    }

    // Example of replacing an element in a HashMap
    // Output: HashMap after replacing 'banana' value: {apple=1, banana=5, cherry=3}
    public void exampleReplaceElement() {
        Map<String, Integer> hashMap = new HashMap<>();
        hashMap.put("apple", 1);
        hashMap.put("banana", 2);
        hashMap.put("cherry", 3);

        hashMap.replace("banana", 5);
        log.info("HashMap after replacing 'banana' value: {}", hashMap);
    }
}

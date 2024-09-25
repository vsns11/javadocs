package ca.siva.ch05_collections;

import lombok.extern.slf4j.Slf4j;
import java.util.Map;
import java.util.TreeMap;

/*
NOTE:
1) TreeMap cannot allow null value as a key, but can have null values for any given key
2) Maintains a sorted order of all the keys. In contrast, hashmap won't preserve the order and allows null value of a key.
 */
@Slf4j
public class TreeMapExamples {

    public static void main(String[] args) {
        TreeMapExamples examples = new TreeMapExamples();

        examples.exampleAddElements();
        examples.exampleRemoveElements();
        examples.exampleIterateTreeMap();
        examples.exampleGetFirstLastEntry();
        examples.exampleHeadMapTailMapSubMap();
        examples.exampleNavigableKeySet();
    }

    // Example of adding elements to a TreeMap
    public void exampleAddElements() {
        TreeMap<String, Integer> treeMap = new TreeMap<>();
        treeMap.put("apple", 1);
        treeMap.put("banana", 2);
        treeMap.put("cherry", 3);

        log.info("TreeMap after adding elements: {}", treeMap);
    }

    // Example of removing elements from a TreeMap
    public void exampleRemoveElements() {
        TreeMap<String, Integer> treeMap = new TreeMap<>();
        treeMap.put("apple", 1);
        treeMap.put("banana", 2);
        treeMap.put("cherry", 3);
        // will remove the entry for a given key, if key not found, returns null by not removing any entry.
        treeMap.remove("banana");
        log.info("TreeMap after removing 'banana': {}", treeMap);
    }

    // Example of iterating over a TreeMap
    public void exampleIterateTreeMap() {
        TreeMap<String, Integer> treeMap = new TreeMap<>();
        treeMap.put("apple", 1);
        treeMap.put("banana", 2);
        treeMap.put("cherry", 3);

        log.info("Iterating over TreeMap:");
        for (Map.Entry<String, Integer> entry : treeMap.entrySet()) {
            log.info("Key: {}, Value: {}", entry.getKey(), entry.getValue());
        }
    }

    // Example of getting the first and last entry in a TreeMap
    public void exampleGetFirstLastEntry() {
        TreeMap<String, Integer> treeMap = new TreeMap<>();
        treeMap.put("apple", 1);
        treeMap.put("banana", 2);
        treeMap.put("cherry", 3);

        Map.Entry<String, Integer> firstEntry = treeMap.firstEntry();
        Map.Entry<String, Integer> lastEntry = treeMap.lastEntry();

        log.info("First entry in TreeMap: {}", firstEntry);
        log.info("Last entry in TreeMap: {}", lastEntry);
    }

    // Example of using headMap, tailMap, and subMap in a TreeMap
    public void exampleHeadMapTailMapSubMap() {
        TreeMap<String, Integer> treeMap = new TreeMap<>();
        treeMap.put("apple", 1);
        treeMap.put("banana", 2);
        treeMap.put("cherry", 3);
        treeMap.put("date", 4);
        treeMap.put("fig", 5);

        Map<String, Integer> headMap = treeMap.headMap("cherry");
        Map<String, Integer> tailMap = treeMap.tailMap("cherry");
        Map<String, Integer> subMap = treeMap.subMap("banana", "fig");

        log.info("HeadMap (elements less than 'cherry'): {}", headMap);
        log.info("TailMap (elements from 'cherry' onwards): {}", tailMap);
        log.info("SubMap (elements between 'banana' and 'fig'): {}", subMap);
    }

    // Example of getting a navigable key set from a TreeMap
    public void exampleNavigableKeySet() {
        TreeMap<String, Integer> treeMap = new TreeMap<>();
        treeMap.put("apple", 1);
        treeMap.put("banana", 2);
        treeMap.put("cherry", 3);
        treeMap.put("date", 4);
        treeMap.put("fig", 5);

        log.info("Navigable Key Set:");
        for (String key : treeMap.navigableKeySet()) {
            log.info("Key: {}", key);
        }
    }
}

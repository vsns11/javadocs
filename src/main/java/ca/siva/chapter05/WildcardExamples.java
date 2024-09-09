package ca.siva.chapter05;

import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;

/*
NOTE:
1. ? extends <class> => allows all subtypes of given class and itself.Only used for reading.
Except that it allows to write null values.
2. ? super <class> => allows all super types of given class and itself. Only used for writing
It can also hold any super types of <class> and also can add any subclass of <class>.
3. ? cannot be used in the class declaration e.g. class Siva<?> => this won't compile.
4. when using 1 and 2 in variables,  ? extends <class>, and just ? won't work in adding any elements to the data structure, since compiler can't guarantee the type safety
   so, only .add(null) works good. Whereas ? super <class> works good with ds to add elements.
5. Covariance (? extends T)
allows reading because you know the type is always T or a subtype of T. So, you are guaranteed to get at least a T.
Contravariance (? super T)
 allows writing because you can put in T or anything that extends T, but you can't be sure what you get back.
 Therefore, the type is generalized to Object.

 */
@Slf4j
public class WildcardExamples {

    public static void main(String[] args) {
        WildcardExamples examples = new WildcardExamples();

        examples.exampleUnboundedWildcard();
        examples.exampleUpperBoundedWildcard();
        examples.exampleLowerBoundedWildcard();
        examples.exampleGenericMethodWildcard();
        examples.exampleCollectionsWildcard();
        examples.exampleWhyAddNotAllowedInUpperBoundedWildcard();

        try {
            examples.exampleThrowsCheckedException();
        } catch (CustomCheckedException e) {
            log.error("Caught CustomCheckedException: {}", e.getMessage(), e);
        }

        examples.exampleThrowsUncheckedException();
    }

    // Example of unbounded wildcard
    public void exampleUnboundedWildcard() {
        List<String> stringList = List.of("one", "two", "three");
        List<Integer> intList = List.of(1, 2, 3);

        log.info("Unbounded Wildcard:");
        printList(stringList);
        printList(intList);
    }

    public void printList(List<?> list) {
        for (Object elem : list) {
            log.info("Element: {}", elem);
        }
    }

    // Example of upper bounded wildcard
    public void exampleUpperBoundedWildcard() {
        List<Integer> intList = List.of(1, 2, 3);
        List<Double> doubleList = List.of(1.1, 2.2, 3.3);

        log.info("Upper Bounded Wildcard:");
        log.info("Sum of integers: {}", sumOfList(intList));
        log.info("Sum of doubles: {}", sumOfList(doubleList));
    }

    public double sumOfList(List<? extends Number> list) {
        double sum = 0.0;
        for (Number num : list) {
            sum += num.doubleValue();
        }
        return sum;
    }

    // Example of lower bounded wildcard
    public void exampleLowerBoundedWildcard() {
        List<Number> numList = new ArrayList<>();
        addNumbers(numList);
        log.info("Lower Bounded Wildcard:");
        log.info("List after adding numbers: {}", numList);
    }

    public void addNumbers(List<? super Integer> list) {
        list.add(1);
        list.add(2);
        list.add(3);

    }

    // Example of generic method with wildcard
    public void exampleGenericMethodWildcard() {
        List<Integer> src = List.of(1, 2, 3);
        List<Number> dest = new ArrayList<>();

        copyList(src, dest);
        log.info("Generic Method with Wildcard:");
        log.info("Destination list: {}", dest);
    }

    public <T> void copyList(List<? extends T> src, List<? super T> dest) {
        for (T item : src) {
            dest.add(item);
        }
    }

    // Example of collections wildcard
    public void exampleCollectionsWildcard() {
        List<String> stringList = new ArrayList<>();
        stringList.add("hello");
        stringList.add("world");

        log.info("Collections Wildcard:");
        printList(stringList);
    }

    // Example of why adding is not allowed in upper bounded wildcard
    public void exampleWhyAddNotAllowedInUpperBoundedWildcard() {
        List<? extends Number> numberList = new ArrayList<Integer>();
        List<? extends Number> doubleList = new ArrayList<Double>();
        List<? extends Number> booleanList = new ArrayList<Long>();

        // The following line would cause a compilation error because you cannot add elements to a collection
        // defined with an upper bounded wildcard. The compiler cannot guarantee the type safety.
        // numberList.add(1); // Compilation error

        // We can, however, read elements from the collection
        for (Number number : numberList) {
            log.info("Number: {}", number);
        }
    }

    // Example of throws checked exception
    public void exampleThrowsCheckedException() throws CustomCheckedException {
        riskyMethod(true); // Change to false to avoid the exception
    }

    public void riskyMethod(boolean throwError) throws CustomCheckedException {
        if (throwError) {
            throw new CustomCheckedException("A custom checked exception occurred.");
        }
        log.info("Checked Exception: Task completed successfully.");
    }

    // Example of throws unchecked exception
    public void exampleThrowsUncheckedException() {
        try {
            methodThatThrowsUncheckedException();
        } catch (CustomUncheckedException e) {
            log.error("Caught CustomUncheckedException: {}", e.getMessage(), e);
        }
    }

    public void methodThatThrowsUncheckedException() {
        throw new CustomUncheckedException("A custom unchecked exception occurred.");
    }
}

// Custom checked exception
class CustomCheckedException extends Exception {
    public CustomCheckedException(String message) {
        super(message);
    }
}

// Custom unchecked exception
class CustomUncheckedException extends RuntimeException {
    public CustomUncheckedException(String message) {
        super(message);
    }
}

package ca.siva.chapter02;

public class LoopWithLabelsExamples {

    public static void main(String[] args) {
        findFirstPositiveNumberInWhileLoop(new int[]{-1, -2, -3, 4, -5});
        findFirstPositiveNumberInDoWhileLoop(new int[]{-1, -2, -3, 4, -5});
        findFirstPositiveNumberInForLoop(new int[]{-1, -2, -3, 4, -5});
        breakOuterLoopExample();
        continueOuterLoopExample();
    }

    // Example with while loop and label
    public static void findFirstPositiveNumberInWhileLoop(int[] numbers) {
        int i = 0;
        positiveNumberSearch:
        while (i < numbers.length) {
            if (numbers[i] > 0) {
                System.out.println("First positive number (while loop): " + numbers[i]);
                break positiveNumberSearch; // Use label to break out of the loop
            }
            i++;
        }
    }

    // Example with do-while loop and label
    public static void findFirstPositiveNumberInDoWhileLoop(int[] numbers) {
        int i = 0;
        if (numbers.length == 0) {
            System.out.println("No positive number found (do-while loop): -1");
            return;
        }
        positiveNumberSearch:
        do {
            if (numbers[i] > 0) {
                System.out.println("First positive number (do-while loop): " + numbers[i]);
                break positiveNumberSearch; // Use label to break out of the loop
            }
            i++;
        } while (i < numbers.length);
    }

    // Example with for loop and label
    public static void findFirstPositiveNumberInForLoop(int[] numbers) {
        positiveNumberSearch:
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] > 0) {
                System.out.println("First positive number (for loop): " + numbers[i]);
                break positiveNumberSearch; // Use label to break out of the loop
            }
        }
    }

    // Example with nested loops and break label
    public static void breakOuterLoopExample() {
        outerLoop:
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == 1 && j == 1) {
                    System.out.println("Breaking out of outer loop at i=" + i + ", j=" + j);
                    break outerLoop; // Use label to break out of the outer loop
                }
                System.out.println("i=" + i + ", j=" + j);
            }
        }
    }

    // Example with nested loops and continue label
    public static void continueOuterLoopExample() {
        outerLoop:
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == 1 && j == 1) {
                    System.out.println("Continuing outer loop at i=" + i + ", j=" + j);
                    continue outerLoop; // Use label to continue the outer loop
                }
                System.out.println("i=" + i + ", j=" + j);
            }
        }
    }
}

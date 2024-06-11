package ca.siva.chapter03;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VarargsExample {

    public static void printStrings(String... strings) {
        if (strings == null) {
            System.out.println("No strings passed (null)");
        } else {
            System.out.println("Number of strings passed: " + strings.length);
            for (String str : strings) {
                System.out.println(str);
            }
        }
    }

    public static void printStringsWithArray(String[] strings) {
        if (strings == null) {
            System.out.println("No strings passed (null)");
        } else {
            System.out.println("Number of strings passed: " + strings.length);
            for (String str : strings) {
                System.out.println(str);
            }
        }
    }

    public static void main(String[] args) {
        printStrings();               // No arguments
        printStrings("A", "B", "C");  // Multiple arguments
        printStrings(null);  // Single null argument
        printStrings(null, "A", "B"); // Multiple arguments including null
        printStrings((String[]) null); // Null array
        printStrings(new String[]{"A", "B", "C"});  // Multiple arguments


        printStringsWithArray(new String[]{"A", "B", "C"});  // Multiple arguments
        printStringsWithArray(null);  // Single null argument
        printStringsWithArray(new String[]{null, "A", "B"}); // Multiple arguments including null
        printStringsWithArray((String[]) null); // Null array
    }
}

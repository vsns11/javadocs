package ca.siva.chapter01;

import lombok.extern.slf4j.Slf4j;

/*
NOTE:
  1) String literals are stored in stringPool. Also, known as intern-pool.
  2) Equals() checks whether the content is matched or not and it's case-sensitive.
 */
@Slf4j
public class StringMethodsExample {

    public static void main(String[] args) {
        lengthExample();
        charAtExample();
        substringExample();
        indexOfExample();
        caseConversionExample();
        trimExample();
        replaceExample();
        splitExample();
        equalsExample();
        concatExample();
        stripExample();
        stripLeadingExample();
        stripTrailingExample();
        stripIndentExample();
        translateEscapesExample();
        indentExample();
        varyingIndentExample();
    }

    private static void lengthExample() {
        String str = "Hello, World!";
        int length = str.length();
        log.info("Length: " + length); // Output: 13
    }

    private static void charAtExample() {
        String str = "Hello, World!";
        char ch = str.charAt(1);
        log.info("Character at index 1: " + ch); // Output: e
    }

    private static void substringExample() {
        String str = "Hello, World!";
        String substr1 = str.substring(7);
        String substr2 = str.substring(0, 5);
        log.info("Substring from index 7: " + substr1); // Output: World!
        log.info("Substring from index 0 to 5: " + substr2); // Output: Hello
    }

    private static void indexOfExample() {
        String str = "Hello, World!";
        int firstIndex = str.indexOf("o");
        int lastIndex = str.lastIndexOf("o");
        log.info("First index of 'o': " + firstIndex); // Output: 4
        log.info("Last index of 'o': " + lastIndex); // Output: 8
    }

    private static void caseConversionExample() {
        String str = "Hello, World!";
        String upper = str.toUpperCase();
        String lower = str.toLowerCase();
        log.info("Upper case: " + upper); // Output: HELLO, WORLD!
        log.info("Lower case: " + lower); // Output: hello, world!
    }

    private static void trimExample() {
        String str = "   Hello, World!   ";
        String trimmed = str.trim();
        log.info("Trimmed: '" + trimmed + "'"); // Output: 'Hello, World!'
    }

    private static void replaceExample() {
        String str = "Hello, World!";
        String replaced = str.replace("World", "Java");
        log.info("Replaced: " + replaced); // Output: Hello, Java!
    }

    private static void splitExample() {
        String str = "apple,banana,cherry";
        String[] fruits = str.split(",");
        for (String fruit : fruits) {
            log.info("Fruit: " + fruit);
        }
        // Output:
        // Fruit: apple
        // Fruit: banana
        // Fruit: cherry
    }

    private static void equalsExample() {
        String str1 = "Hello";
        String str2 = "hello";
        boolean isEqual = str1.equals(str2);
        boolean isEqualIgnoreCase = str1.equalsIgnoreCase(str2);
        log.info("Equals: " + isEqual); // Output: false
        log.info("Equals Ignore Case: " + isEqualIgnoreCase); // Output: true
    }

    private static void concatExample() {
        String str1 = "Hello";
        String str2 = "World";
        String concatenated = str1.concat(", ").concat(str2).concat("!");
        log.info("Concatenated: " + concatenated); // Output: Hello, World!
    }

    private static void stripExample() {
        String str = "\u2005 Hello, World! \u2005";
        String stripped = str.strip();
        log.info("Stripped: '" + stripped + "'"); // Output: 'Hello, World!'
    }

    private static void stripLeadingExample() {
        String str = "\u2005 Hello, World! \u2005";
        String strippedLeading = str.stripLeading();
        log.info("Stripped Leading: '" + strippedLeading + "'"); // Output: 'Hello, World! \u2005'
    }

    private static void stripTrailingExample() {
        String str = "\u2005 Hello, World! \u2005";
        String strippedTrailing = str.stripTrailing();
        log.info("Stripped Trailing: '" + strippedTrailing + "'"); // Output: '\u2005 Hello, World!'
    }

    /*
     The stripIndent method adjusts the indentation of each line by removing the common leading whitespace.
     Original:
        Line 1
        Line 2
          Line 3

    Stripped Indent:
    Line 1
    Line 2
      Line 3

     */
    private static void stripIndentExample() {
        String textBlock = """
                        Line 1
                        Line 2
                          Line 3
                """;
        String stripped = textBlock.stripIndent();
        log.info("Original:\n" + textBlock);
        log.info("Stripped Indent:\n" + stripped);
    }

    /*
    which translates escape sequences into their actual characters.
    Original: Hello\nWorld!\tHow are you?
    Translated Escapes: Hello
    World!    How are you?

     */
    private static void translateEscapesExample() {
        String text = "Hello\\nWorld!\\tHow are you?";
        String translated = text.translateEscapes();
        log.info("Original: " + text);
        log.info("Translated Escapes: " + translated);
    }

    /*
    indent(4)  means adds 4 spaces at the beginning each line of a string
    indent(-4) means removes 4 spaces at the beginning each line of a string
     */
    private static void indentExample() {
        String str = "Hello\nWorld";
        String indented = str.indent(4);
        log.info("Original:\n" + str);
        log.info("Indented:\n" + indented);

        String outdented = indented.indent(-2);
        log.info("Outdented:\n" + outdented);
    }

    private static void varyingIndentExample() {
        String textBlock = """
                    Line 1
                Line 2
                      Line 3
        Line 4
    """;
        String stripped = textBlock.stripIndent();
        log.info("Original:\n" + textBlock);
        log.info("Stripped Indent:\n" + stripped);
    }
}

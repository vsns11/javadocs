package ca.siva.chapter01;

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
        System.out.println("Length: " + length); // Output: 13
    }

    private static void charAtExample() {
        String str = "Hello, World!";
        char ch = str.charAt(1);
        System.out.println("Character at index 1: " + ch); // Output: e
    }

    private static void substringExample() {
        String str = "Hello, World!";
        String substr1 = str.substring(7);
        String substr2 = str.substring(0, 5);
        System.out.println("Substring from index 7: " + substr1); // Output: World!
        System.out.println("Substring from index 0 to 5: " + substr2); // Output: Hello
    }

    private static void indexOfExample() {
        String str = "Hello, World!";
        int firstIndex = str.indexOf("o");
        int lastIndex = str.lastIndexOf("o");
        System.out.println("First index of 'o': " + firstIndex); // Output: 4
        System.out.println("Last index of 'o': " + lastIndex); // Output: 8
    }

    private static void caseConversionExample() {
        String str = "Hello, World!";
        String upper = str.toUpperCase();
        String lower = str.toLowerCase();
        System.out.println("Upper case: " + upper); // Output: HELLO, WORLD!
        System.out.println("Lower case: " + lower); // Output: hello, world!
    }

    private static void trimExample() {
        String str = "   Hello, World!   ";
        String trimmed = str.trim();
        System.out.println("Trimmed: '" + trimmed + "'"); // Output: 'Hello, World!'
    }

    private static void replaceExample() {
        String str = "Hello, World!";
        String replaced = str.replace("World", "Java");
        System.out.println("Replaced: " + replaced); // Output: Hello, Java!
    }

    private static void splitExample() {
        String str = "apple,banana,cherry";
        String[] fruits = str.split(",");
        for (String fruit : fruits) {
            System.out.println("Fruit: " + fruit);
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
        System.out.println("Equals: " + isEqual); // Output: false
        System.out.println("Equals Ignore Case: " + isEqualIgnoreCase); // Output: true
    }

    private static void concatExample() {
        String str1 = "Hello";
        String str2 = "World";
        String concatenated = str1.concat(", ").concat(str2).concat("!");
        System.out.println("Concatenated: " + concatenated); // Output: Hello, World!
    }

    private static void stripExample() {
        String str = "\u2005 Hello, World! \u2005";
        String stripped = str.strip();
        System.out.println("Stripped: '" + stripped + "'"); // Output: 'Hello, World!'
    }

    private static void stripLeadingExample() {
        String str = "\u2005 Hello, World! \u2005";
        String strippedLeading = str.stripLeading();
        System.out.println("Stripped Leading: '" + strippedLeading + "'"); // Output: 'Hello, World! \u2005'
    }

    private static void stripTrailingExample() {
        String str = "\u2005 Hello, World! \u2005";
        String strippedTrailing = str.stripTrailing();
        System.out.println("Stripped Trailing: '" + strippedTrailing + "'"); // Output: '\u2005 Hello, World!'
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
        System.out.println("Original:\n" + textBlock);
        System.out.println("Stripped Indent:\n" + stripped);
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
        System.out.println("Original: " + text);
        System.out.println("Translated Escapes: " + translated);
    }

    /*
    indent(4)  means adds 4 spaces at the beginning each line of a string
    indent(-4) means removes 4 spaces at the beginning each line of a string
     */
    private static void indentExample() {
        String str = "Hello\nWorld";
        String indented = str.indent(4);
        System.out.println("Original:\n" + str);
        System.out.println("Indented:\n" + indented);

        String outdented = indented.indent(-2);
        System.out.println("Outdented:\n" + outdented);
    }

    private static void varyingIndentExample() {
        String textBlock = """
                    Line 1
                Line 2
                      Line 3
        Line 4
    """;
        String stripped = textBlock.stripIndent();
        System.out.println("Original:\n" + textBlock);
        System.out.println("Stripped Indent:\n" + stripped);
    }
}

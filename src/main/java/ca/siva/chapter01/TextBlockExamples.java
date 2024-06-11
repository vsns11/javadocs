package ca.siva.chapter01;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TextBlockExamples {

    public static void main(String[] args) {
        TextBlockExamples examples = new TextBlockExamples();
        examples.basicTextBlock();
        examples.textBlockWithWhitespace();
        examples.captureInitializationError();
        examples.textBlockWithSpecialCharacters();
        examples.textBlockWithFormattedString();
        examples.misalignedClosingDelimiter();
        examples.illegalEscapeSequence();
        examples.unclosedTextBlock();
        examples.consistentIndentation();
        examples.inconsistentIndentation();
        examples.preservingWhitespace();
        examples.preservingNewLines();
        examples.escapeSequences();
        examples.omitNewLine();
        examples.preservingSpaces();
        examples.spacesAtEndOfLines();
    }

    // Basic text block example
    /*
    Always when using text block, text should start in a new line if it starts with an opening quote,
    the code won't compile:
    e.g., """abc -> won't compile
     */
    public void basicTextBlock() {
        String textBlock = """
                This is a text block
                that spans multiple
                lines.
                """;
        log.info("Basic Text Block:\n" + textBlock);
    }

    // Text block with leading and trailing whitespaces
    public void textBlockWithWhitespace() {
        String textBlock = """
                Line with leading whitespace
                  Line with indentation
                Line with trailing whitespace   
                """;
        log.info("Text Block with Whitespace:\n" + textBlock);
    }

    // Text block with special characters
    public void textBlockWithSpecialCharacters() {
        String textBlock = """
                Special characters: \t, \n, ", \\
                """;
        log.info("Text Block with Special Characters:\n" + textBlock);
    }

    // Text block with formatted string
    public void textBlockWithFormattedString() {
        String textBlock = String.format("""
                Hi %s,
                Welcome to Java %d!
                """, "Alice", 17);
        log.info("Text Block with Formatted String:\n" + textBlock);
    }

    // Error: Misaligned closing delimiter
    public void misalignedClosingDelimiter() {
        // This will cause a compile-time error
        // String textBlock = """
        //         This is a text block
        //         with a misaligned closing delimiter
        //        """;
        // log.info(textBlock);
        log.info("Error Example: Misaligned Closing Delimiter - Uncomment to see compile-time error");
    }

    // Error: Illegal escape sequence
    public void illegalEscapeSequence() {
        // This will cause a compile-time error
        // String textBlock = """
        //         Illegal escape sequence: \q
        //         """;
        // log.info(textBlock);
        log.info("Error Example: Illegal Escape Sequence - Uncomment to see compile-time error");
    }

    // Error: Unclosed text block
    public void unclosedTextBlock() {
        // This will cause a compile-time error
        // String textBlock = """
        //         This is an unclosed text block
        //        """;
        // log.info(textBlock);
        log.info("Error Example: Unclosed Text Block - Uncomment to see compile-time error");
    }

    // Text block with consistent indentation
    public void consistentIndentation() {
        //Prints output of 4 lines because of keeping quotes at the end in a new line
        String textBlock = """
                Line 1
                Line 2
                Line 3
                """;
        log.info("Consistent Indentation:\n" + textBlock);
    }

    // Text block with inconsistent indentation
    public void inconsistentIndentation() {
        String textBlock = """
                Line 1
                    Line 2
                Line 3
                """;
        log.info("Inconsistent Indentation:\n" + textBlock);
    }

    // Preserving whitespace explicitly
    public void preservingWhitespace() {
        String textBlock = """
                Line 1\s
                Line 2\s
                Line 3\s
                """;
        log.info("Preserving Whitespace:\n" + textBlock);
    }

    // Preserving new lines explicitly
    public void preservingNewLines() {
        String textBlock = """
                Line 1
                
                Line 2
                
                Line 3
                
                """;
        log.info("Preserving New Lines:\n" + textBlock);
    }

    //Compilation Error case where quotes text is defined next
    public void captureInitializationError() {
//        String textBlock = """Line""";
//        log.info("captureInitializationError:\n" + textBlock);
    }

    // Example: Regular String vs. Text Block with escape sequences
    public void escapeSequences() {
        String regularString = "\" \"";
        String textBlock = """
                " "
                """;
        log.info("Regular String with escaped quotes: " + regularString);
        log.info("Text Block with quotes: " + textBlock);
    }

    // Example: Omitting new line using \ at end of line
    public void omitNewLine() {
        String textBlock = """
                This is a text block \
                that omits new lines \
                using backslashes.
                """;
        log.info("Text Block omitting new lines:\n" + textBlock);
    }

    // Example: Spaces at the end of lines
    public void spacesAtEndOfLines() {
        String regularString = "Line with space at end ";
        String textBlock = """
                Line with space at end \
                Line without space at end
                """;
        log.info("Regular String with space at end: '" + regularString + "'");
        log.info("Text Block with and without spaces at end:\n'" + textBlock + "'");
    }

    //Example: Always draw a vertical access at non-null character(including the quotes),
    // The left side of that axis is called Incidental space i.e., unnecessary space.
    // Rest of the space in text is Essential space.
    public void spacesAtEndOfLinesConsideringQuotes() {
        String textBlock = """
                green
                yellow
            """;
        log.info("Text Block with and without spaces at end:\n'" + textBlock + "'");
    }


    // Example: Using \s to preserve spaces
    public void preservingSpaces() {
        String textBlock = """
                Line with two spaces\s\s
                Line with one space\s
                Line with no space
                """;
        log.info("Text Block preserving spaces:\n'" + textBlock + "'");
    }
}

package ca.siva.ch09_io_and_nio;

import lombok.extern.slf4j.Slf4j;

import java.io.Console;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

@Slf4j
public class ConsoleExample {

    /**
     * About: Reads a line of text from the console using System.console().readLine().
     * Input: Console input from the user.
     * Output: Logs the user's input.
     */
    public static void readFromConsoleWithConsoleReadLine() {
        Console console = System.console();
        if (console != null) {
            String input = console.readLine("Enter a line of text: ");
            log.info("You entered: {}", input);
        } else {
            log.error("No console available.");
        }
    }

    /**
     * About: Reads a password securely from the console using System.console().readPassword().
     * Input: Console input from the user (masked).
     * Output: Logs the user's input (for demonstration; in real applications, passwords shouldn't be logged).
     */
    public static void readPasswordFromConsole() {
        Console console = System.console();
        if (console != null) {
            char[] password = console.readPassword("Enter your password: ");
            log.info("You entered a password (masked for security): {}", new String(password));
            // Note: In real applications, never log passwords like this.
        } else {
            log.error("No console available.");
        }
    }

    /**
     * About: Reads input using Console.reader() and writes output using Console.writer().
     * Input: Console input from the user.
     * Output: Echoes the user's input back to the console using Console.writer().
     */
    public static void readAndWriteUsingReaderWriter() {
        Console console = System.console();
        if (console != null) {
            Reader reader = console.reader();
            Writer writer = console.writer();

            try {
                writer.write("Enter some text: ");
                writer.flush();

                char[] buffer = new char[100];
                int readChars = reader.read(buffer);

                if (readChars != -1) {
                    String input = new String(buffer, 0, readChars).trim();
                    writer.write("You entered: " + input + "\n");
                    writer.flush();
                }
            } catch (IOException e) {
                log.error("I/O error occurred: ", e);
            }
        } else {
            log.error("No console available.");
        }
    }


    public static void main(String[] args) {
        // Read a line of text from the console
        readFromConsoleWithConsoleReadLine();

        // Read a password from the console
        readPasswordFromConsole();

        // Read and write using Reader and Writer
        readAndWriteUsingReaderWriter();
    }
}

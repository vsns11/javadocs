package ca.siva.ch11_localization;

import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Locale;

@Slf4j
public class MessageFormatExamples {

    /**
     * Basic example of using MessageFormat to insert dynamic text into a message.
     * Input: Dynamic variables "John", "Java Programming".
     * Output: Logs the formatted message with the inserted text.
     */
    public static void basicMessageFormat() {
        String template = "Hello, {0}! Welcome to {1}.";
        String message = MessageFormat.format(template, "John", "Java Programming");
        log.info("Formatted Message: {}", message);  // Expected: "Hello, John! Welcome to Java Programming."
    }

    /**
     * Example of using MessageFormat to format numbers within a message.
     * Input: Dynamic variable 1234567.89.
     * Output: Logs the formatted message with the inserted and formatted number.
     */
    public static void messageFormatWithNumber() {
        String template = "Your account balance is {0,number,currency}.";
        double balance = 1234567.89;
        String message = MessageFormat.format(template, balance);
        log.info("Formatted Message with Number: {}", message);  // Expected: "Your account balance is $1,234,567.89." (in US locale)
    }

    /**
     * Example of using MessageFormat to format dates within a message.
     * Input: Dynamic variable representing the current date.
     * Output: Logs the formatted message with the inserted and formatted date.
     */
    public static void messageFormatWithDate() {
        String template = "The event will take place on {0,date,long}.";
        Date eventDate = new Date();  // Current date
        String message = MessageFormat.format(template, eventDate);
        log.info("Formatted Message with Date: {}", message);  // Expected: "The event will take place on {formatted date}."
    }

    /**
     * Example of using MessageFormat with localization.
     * Input: Dynamic variables for French locale.
     * Output: Logs the formatted message with localized number and date formats.
     */
    public static void messageFormatWithLocalization() {
        Locale locale = Locale.FRANCE;
        String template = "Bonjour {0}, vous avez {1,number,currency} sur votre compte. La date d'aujourd'hui est {2,date,long}.";

        double balance = 1234567.89;
        Date currentDate = new Date();
        MessageFormat messageFormat = new MessageFormat(template, locale);
        String message = messageFormat.format(new Object[]{"Jean", balance, currentDate});

        log.info("Localized Message (French): {}", message);  // Expected: Localized message in French
    }

    /**
     * Example of using MessageFormat to format complex messages with multiple placeholders.
     * Input: Dynamic variables "Alice", "Paris", 3 tickets, "2024-12-25".
     * Output: Logs the formatted message with multiple dynamic parts inserted.
     */
    public static void complexMessageFormat() {
        String template = "{0} has booked {1} tickets to {2} on {3,date,long}. The total cost is {4,number,currency}.";
        Object[] params = {"Alice", 3, "Paris", new Date(), 599.99};

        String message = MessageFormat.format(template, params);
        log.info("Complex Formatted Message: {}", message);
        // Expected: "Alice has booked 3 tickets to Paris on {formatted date}. The total cost is $599.99."
    }

    public static void main(String[] args) {
        basicMessageFormat();
        messageFormatWithNumber();
        messageFormatWithDate();
        messageFormatWithLocalization();
        complexMessageFormat();
    }
}

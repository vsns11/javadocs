package ca.siva.chapter11;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

/*
NOTE:
1) DateTimeFormatter and localDate, both have format method. Where DateTimeFormatter's format takes localDate as input to retrieve string.
The other, localDate's format method takes DateTimeFormatter as input parameter.
 */
@Slf4j
public class DateTimeFormatterExamples {

    /**
     * Example of formatting the current date and time using the default format.
     * Input: No input, uses the current date and time.
     * Output: Logs the formatted date and time using the default pattern.
     */
    public static void formatCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  // Default format
        String formattedDateTime = now.format(formatter);
        log.info("Formatted Current DateTime: {}", formattedDateTime);
    }

    /**
     * Example of formatting a date using a custom pattern.
     * Input: A LocalDate object representing 2024-05-22.
     * Output: Logs the formatted date using the custom pattern "dd MMM yyyy".
     */
    public static void formatDateWithCustomPattern() {
        LocalDate date = LocalDate.of(2024, 5, 22);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        String formattedDate = date.format(formatter);
        log.info("Formatted Date with Custom Pattern: {}", formattedDate);  // Expected: "22 May 2024"
    }

    /**
     * Example of formatting a time using a custom pattern.
     * Input: A LocalTime object representing 14:30:00.
     * Output: Logs the formatted time using the custom pattern "hh:mm a".
     */
    public static void formatTimeWithCustomPattern() {
        LocalTime time = LocalTime.of(14, 30);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        String formattedTime = time.format(formatter);
        log.info("Formatted Time with Custom Pattern: {}", formattedTime);  // Expected: "02:30 PM"
    }

    /**
     * Example of parsing a date string into a LocalDate object using a custom pattern.
     * Input: A date string "22-May-2024".
     * Output: Logs the parsed LocalDate object.
     */
    public static void parseDateFromString() {
        String dateString = "22-May-2024";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        LocalDate date = LocalDate.parse(dateString, formatter);
        log.info("Parsed Date from String: {}", date);  // Expected: 2024-05-22
    }

    /**
     * Example of parsing a date and time string into a LocalDateTime object using a custom pattern.
     * Input: A date and time string "22-May-2024 14:30".
     * Output: Logs the parsed LocalDateTime object.
     */
    public static void parseDateTimeFromString() {
        String dateTimeString = "22-May-2024 14:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
        log.info("Parsed DateTime from String: {}", dateTime);  // Expected: 2024-05-22T14:30
    }

    /**
     * Example of formatting a date and time with a specific locale (e.g., French).
     * Input: A LocalDateTime object representing 2024-05-22 14:30:00.
     * Output: Logs the formatted date and time in French locale.
     */
    public static void formatDateTimeWithLocale() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 5, 22, 14, 30);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy HH:mm", Locale.FRENCH);
        String formattedDateTime = dateTime.format(formatter);
        log.info("Formatted DateTime with French Locale: {}", formattedDateTime);  // Expected: "mercredi, 22 mai 2024 14:30"
    }

    /**
     * Example of formatting a date with a custom pattern that includes escaping quotes.
     * Input: A LocalDate object representing 2024-05-22.
     * Output: Logs the formatted date with custom text around it (e.g., 'Date: 22-May-2024').
     */
    public static void formatDateWithEscapedQuotes() {
        LocalDate date = LocalDate.of(2024, 5, 22);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'Date:' dd-MMM-yyyy");
        String formattedDate = date.format(formatter);
        log.info("Formatted Date with Escaped Quotes: {}", formattedDate);  // Expected: "Date: 22-May-2024"
    }

    /**
     * Example of using DateTimeFormatter.ofLocalizedDate to format a date with different localized styles.
     * Input: A LocalDate object representing 2024-05-22.
     * Output: Logs the formatted date with SHORT, MEDIUM, LONG, and FULL styles.
     */
    public static void formatLocalizedDate() {
        LocalDate date = LocalDate.of(2024, 5, 22);

        // Using different FormatStyles
        DateTimeFormatter shortFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
        DateTimeFormatter mediumFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
        DateTimeFormatter longFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);
        DateTimeFormatter fullFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);

        log.info("Formatted Date (SHORT): {}", date.format(shortFormatter));    // Expected: 5/22/24 (US)
        log.info("Formatted Date (MEDIUM): {}", date.format(mediumFormatter));  // Expected: May 22, 2024 (US)
        log.info("Formatted Date (LONG): {}", date.format(longFormatter));      // Expected: May 22, 2024 (US)
        log.info("Formatted Date (FULL): {}", date.format(fullFormatter));      // Expected: Wednesday, May 22, 2024 (US)
    }

    /**
     * Example of using DateTimeFormatter.ofLocalizedTime to format a time with different localized styles.
     * Input: A LocalTime object representing 14:30:00.
     * Output: Logs the formatted time with SHORT and MEDIUM styles.
     */
    public static void formatLocalizedTime() {
        LocalTime time = LocalTime.of(14, 30);

        // Using different FormatStyles
        DateTimeFormatter shortFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
        DateTimeFormatter mediumFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM);

        log.info("Formatted Time (SHORT): {}", time.format(shortFormatter));    // Expected: 2:30 PM (US)
        log.info("Formatted Time (MEDIUM): {}", time.format(mediumFormatter));  // Expected: 2:30:00 PM (US)
    }

    /**
     * Example of using DateTimeFormatter.ofLocalizedDateTime to format a date and time with different localized styles.
     * Input: A LocalDateTime object representing 2024-05-22 14:30:00.
     * Output: Logs the formatted date and time with SHORT, MEDIUM, LONG, and FULL styles.
     */
    public static void formatLocalizedDateTime() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 5, 22, 14, 30);

        // Using different FormatStyles
        DateTimeFormatter shortFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
        DateTimeFormatter mediumFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        DateTimeFormatter longFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG);
        DateTimeFormatter fullFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL);

        log.info("Formatted DateTime (SHORT): {}", dateTime.format(shortFormatter));    // Expected: 5/22/24, 2:30 PM (US)
        log.info("Formatted DateTime (MEDIUM): {}", dateTime.format(mediumFormatter));  // Expected: May 22, 2024, 2:30:00 PM (US)
        log.info("Formatted DateTime (LONG): {}", dateTime.format(longFormatter));      // Expected: May 22, 2024 at 2:30:00 PM EDT (US)
        log.info("Formatted DateTime (FULL): {}", dateTime.format(fullFormatter));      // Expected: Wednesday, May 22, 2024 at 2:30:00 PM Eastern Daylight Time (US)
    }

    /**
     * Example of using localized date formatting with a specific locale (e.g., French).
     * Input: A LocalDate object representing 2024-05-22.
     * Output: Logs the formatted date in French locale with different styles.
     */
    public static void formatLocalizedDateWithLocale() {
        LocalDate date = LocalDate.of(2024, 5, 22);

        // Using different FormatStyles with French locale
        DateTimeFormatter shortFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(Locale.FRENCH);
        DateTimeFormatter mediumFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(Locale.FRENCH);
        DateTimeFormatter longFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(Locale.FRENCH);
        DateTimeFormatter fullFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(Locale.FRENCH);

        log.info("Formatted Date (SHORT, French): {}", date.format(shortFormatter));    // Expected: 22/05/24
        log.info("Formatted Date (MEDIUM, French): {}", date.format(mediumFormatter));  // Expected: 22 mai 2024
        log.info("Formatted Date (LONG, French): {}", date.format(longFormatter));      // Expected: 22 mai 2024
        log.info("Formatted Date (FULL, French): {}", date.format(fullFormatter));      // Expected: mercredi 22 mai 2024
    }

    public static void main(String[] args) {
        formatCurrentDateTime();
        formatDateWithCustomPattern();
        formatTimeWithCustomPattern();
        parseDateFromString();
        parseDateTimeFromString();
        formatDateTimeWithLocale();
        formatDateWithEscapedQuotes();
        formatLocalizedDate();
        formatLocalizedTime();
        formatLocalizedDateTime();
        formatLocalizedDateWithLocale();
    }
}

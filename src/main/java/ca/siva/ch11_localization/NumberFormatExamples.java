package ca.siva.ch11_localization;

import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

/*
NOTE:
1) CompactNumberInstance format() always rounds off to 3 digits.
2) In DecimalNumberFormat pattern, use ,00 (grouping and two fixed digits),
    # (optional digit), and .# (decimal and one optional digit).
3) Locale.Category.FORMAT -> Controls how data like numbers, dates, times, and currencies are formatted. This category affects methods like NumberFormat.getInstance() or DateTimeFormatter.ofLocalizedDate().
4) Locale.Category.DISPLAY -> Controls how text is displayed in the user interface, including language names, month names, day names, and other text-based elements that are usually localized.
5) DecimalFormat constructor takes only pattern not locale and it is a child class of NumberFormat(i.e., an abstract class).
This is valid:
NumberFormat formatter = DecimalFormat.getCurrencyInstance(jp);
6) getInstance(Locale ) is a valid factory method in NumberFormat class but it will not not format the given number as per the currency.

 */
@Slf4j
public class NumberFormatExamples {

    /**
     * Example of formatting a number using the default locale.
     * Input: A double value 1234567.89
     * Output: Logs the formatted number according to the default locale (e.g., "1,234,567.89" in US locale).
     */
    public static void formatNumberDefaultLocale() {
        double number = 1234567.89;
        NumberFormat numberFormat = NumberFormat.getNumberInstance();  // Uses the default locale
        String formattedNumber = numberFormat.format(number);
        log.info("Formatted Number (Default Locale): {}", formattedNumber);
    }

    /**
     * Example of formatting a number using NumberFormat.getInstance().
     * Input: A double value 1234567.89
     * Output: Logs the formatted number using a general-purpose formatter according to the default locale.
     */
    public static void formatWithGetInstance() {
        double number = 1234567.89;
        // getInstance(Locale ) is a valid factory method in NumberFormat class but it will not not format the given number as per the currency.
        NumberFormat numberFormat = NumberFormat.getInstance();  // General-purpose number formatter
        String formattedNumber = numberFormat.format(number);
        log.info("Formatted Number with getInstance(): {}", formattedNumber);
    }

    /**
     * Example of formatting a number using a specific locale (e.g., Germany).
     * Input: A double value 1234567.89
     * Output: Logs the formatted number according to the German locale (e.g., "1.234.567,89").
     */
    public static void formatNumberWithLocale() {
        double number = 1234567.89;
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.GERMANY);  // Uses the German locale
        String formattedNumber = numberFormat.format(number);
        log.info("Formatted Number (German Locale): {}", formattedNumber);
    }

    /**
     * Example of formatting a number as currency using the default locale.
     * Input: A double value 1234567.89
     * Output: Logs the formatted currency according to the default locale (e.g., "$1,234,567.89" in US locale).
     */
    public static void formatCurrencyDefaultLocale() {
        double amount = 1234567.89;
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();  // Uses the default locale
        String formattedCurrency = currencyFormat.format(amount);
        log.info("Formatted Currency (Default Locale): {}", formattedCurrency);
    }

    /**
     * Example of formatting a number as currency using a specific locale (e.g., Japan).
     * Input: A double value 1234567.89
     * Output: Logs the formatted currency according to the Japanese locale (e.g., "￥1,234,568").
     */
    public static void formatCurrencyWithLocale() {
        double amount = 1234567.89;
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.JAPAN);  // Uses the Japanese locale
        String formattedCurrency = currencyFormat.format(amount);
        log.info("Formatted Currency (Japanese Locale): {}", formattedCurrency);
    }

    /**
     * Example of formatting a number as a percentage using the default locale.
     * Input: A double value 0.75 (representing 75%)
     * Output: Logs the formatted percentage (e.g., "75%" in US locale).
     */
    public static void formatPercentage() {
        double percentage = 0.75;
        NumberFormat percentFormat = NumberFormat.getPercentInstance();  // Uses the default locale
        String formattedPercentage = percentFormat.format(percentage);
        log.info("Formatted Percentage: {}", formattedPercentage);
    }

    /**
     * Example of parsing a formatted number back to a Number.
     * Input: A formatted number string "1,234,567.89" in US locale.
     * Output: Logs the parsed number as a double.
     */
    public static void parseFormattedNumber() {
        String formattedNumber = "1,234,567.89";
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        try {
            Number parsedNumber = numberFormat.parse(formattedNumber);
            log.info("Parsed Number: {}", parsedNumber.doubleValue());
        } catch (ParseException e) {
            log.error("Error parsing formatted number", e);
        }
    }

    /**
     * Example of parsing a formatted currency string back to a Number.
     * Input: A formatted currency string "$1,234,567.89" in US locale.
     * Output: Logs the parsed number as a double.
     */
    public static void parseFormattedCurrency() {
        String formattedCurrency = "$1,234,567.89";
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        try {
            Number parsedAmount = currencyFormat.parse(formattedCurrency);
            log.info("Parsed Currency: {}", parsedAmount.doubleValue());
        } catch (ParseException e) {
            log.error("Error parsing formatted currency", e);
        }
    }

    /**
     * Example of formatting a number in a compact form using the default locale.
     * Input: A large number 1234567 (representing 1,234,567).
     * Output: Logs the number in a compact form (e.g., "1.2M" in US locale, "1.234.567" in German locale).
     */
    public static void formatCompactNumberDefaultLocale() {
        long number = 1234567;
        NumberFormat compactNumberFormat = NumberFormat.getCompactNumberInstance();  // Uses the default locale
        String formattedCompactNumber = compactNumberFormat.format(number);
        log.info("Formatted Compact Number (Default Locale): {}", formattedCompactNumber);
    }

    /**
     * Example of formatting a number in a compact form using a specific locale (e.g., Germany).
     * Input: A large number 1234567 (representing 1,234,567).
     * Output: Logs the number in a compact form according to the German locale (e.g., "1,2 Mio." in German locale).
     */
    public static void formatCompactNumberWithLocale() {
        long number = 1234567;
        NumberFormat compactNumberFormat = NumberFormat.getCompactNumberInstance(Locale.GERMANY, NumberFormat.Style.SHORT);  // Uses the German locale
        String formattedCompactNumber = compactNumberFormat.format(number);
        log.info("Formatted Compact Number (German Locale): {}", formattedCompactNumber);
    }

    /**
     * Example of using Locale.Category.FORMAT to set a different locale for formatting.
     * Input: A double value 1234567.89
     * Output: Logs the formatted currency using Locale.Category.FORMAT, which allows for a separate formatting locale.
     */
    public static void formatCurrencyWithCategoryFormat() {
        Locale.setDefault(Locale.Category.FORMAT, Locale.UK);  // Set the FORMAT category to UK locale
        double amount = 1234567.89;
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();  // Uses the FORMAT category locale
        String formattedCurrency = currencyFormat.format(amount);
        log.info("Formatted Currency (Locale.Category.FORMAT as UK): {}", formattedCurrency);

        // Reset the default locale to avoid affecting other parts of the application
        Locale.setDefault(Locale.Category.FORMAT, Locale.US);
    }

    /**
     * Example of using DecimalFormat to customize the format pattern.
     * Input: A double value 1234567.89
     * Output: Logs the number formatted using a custom DecimalFormat pattern.
     */
    public static void formatWithDecimalFormat() {
        double number = 1234567.89;
        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");  // Custom pattern
        String formattedNumber = decimalFormat.format(number);
        log.info("Formatted Number with DecimalFormat: {}", formattedNumber);  // Expected output: 1,234,567.89
    }

    public static void demonstrateFormatCategory() {
        // Set the default locale for FORMAT to Germany
        Locale.setDefault(Locale.Category.FORMAT, Locale.GERMANY);

        // Format a number using the FORMAT category locale (Germany)
        double number = 1234567.89;
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        String formattedNumber = numberFormat.format(number);
        log.info("Formatted Number with Locale.Category.FORMAT (Germany): {}", formattedNumber);
    }

    /**
     * Example of using separate Locale.Category.FORMAT and Locale.Category.DISPLAY
     * Input: A double value 1234567.89 and default display locale (France)
     * Output: Logs the formatted number using German locale and displays text in French locale.
     */
    public static void demonstrateMixedCategories() {
        // Set the default locale for FORMAT to Germany (for formatting numbers, dates, etc.)
        Locale.setDefault(Locale.Category.FORMAT, Locale.GERMANY);

        // Set the default locale for DISPLAY to France (for displaying UI text, month names, etc.)
        Locale.setDefault(Locale.Category.DISPLAY, Locale.FRANCE);

        // Format a number using the FORMAT category locale (Germany)
        double number = 1234567.89;
        NumberFormat numberFormat = NumberFormat.getNumberInstance();  // Uses the FORMAT category locale
        String formattedNumber = numberFormat.format(number);
        log.info("Formatted Number (Locale.Category.FORMAT = Germany): {}", formattedNumber);

        // Display language names using the DISPLAY category locale (France)
        Locale currentLocale = Locale.getDefault(Locale.Category.DISPLAY);
        String displayLanguage = currentLocale.getDisplayLanguage(Locale.ENGLISH);  // Display the name "English" in French
        String displayCountry = currentLocale.getDisplayCountry(Locale.ENGLISH);    // Display the name "United States" in French
        log.info("Display Language (Locale.Category.DISPLAY = France): {}", displayLanguage); // anglais
        log.info("Display Country (Locale.Category.DISPLAY = France): {}", displayCountry); //  États-Unis

        // Reset the default locale to avoid affecting other parts of the application
        Locale.setDefault(Locale.Category.FORMAT, Locale.US);
        Locale.setDefault(Locale.Category.DISPLAY, Locale.US);
    }

    public static void demonstrateDisplayCategory() {
        // Set the default locale for DISPLAY to France
        Locale.setDefault(Locale.Category.DISPLAY, Locale.FRANCE);

        // Get the display name of a month using the DISPLAY category locale (France)
        String monthDisplayName = Locale.getDefault(Locale.Category.DISPLAY).getDisplayLanguage(Locale.ENGLISH);
        log.info("Display Language with Locale.Category.DISPLAY (France): {}", monthDisplayName);
    }

    /**
     * Example of using DecimalFormat to format a stream of doubles with a specific pattern.
     * The expected output is "<02.1> <06.9> <10,00>".
     * The pattern must enforce leading zeros, rounding, and grouping separators.
     */
    public static void decimalFormatPatternExample() {
        String pattern = "00.#,00";  // This pattern satisfies the expected output
        var message = DoubleStream.of(2.1, 6.923, 1000)
                .mapToObj(v -> new DecimalFormat(pattern).format(v))
                .collect(Collectors.joining("> <", "<", ">"));

        log.info("Formatted Output: {}", message);  // Expected output: "<02.1> <06.9> <10,00>"
    }

    public static void main(String[] args) {
        formatNumberDefaultLocale();
        formatWithGetInstance();  // Added call to the new getInstance() method example
        formatNumberWithLocale();
        formatCurrencyDefaultLocale();
        formatCurrencyWithLocale();
        formatPercentage();
        parseFormattedNumber();
        parseFormattedCurrency();
        formatCompactNumberDefaultLocale();
        formatCompactNumberWithLocale();
        formatCurrencyWithCategoryFormat();
        formatWithDecimalFormat();
        demonstrateFormatCategory();
        demonstrateDisplayCategory();
        demonstrateMixedCategories();
        decimalFormatPatternExample();
    }
}

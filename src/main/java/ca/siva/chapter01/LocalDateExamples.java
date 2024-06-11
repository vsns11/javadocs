package ca.siva.chapter01;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import java.time.Period;

@Slf4j
public class LocalDateExamples {

    // Basic usage of LocalDate.now()
    public static void currentDate() {
        LocalDate today = LocalDate.now();
        log.info("Current Date: " + today);
    }

    // Creating a LocalDate using of() method
    public static void createDate() {
        LocalDate date = LocalDate.of(2024, 5, 22);
        log.info("Created Date: " + date);
    }

    // Parsing a LocalDate from a string
    public static void parseDate() {
        LocalDate date = LocalDate.parse("2024-05-22");
        log.info("Parsed Date: " + date);
    }

    // Formatting a LocalDate to a string
    public static void formatDate() {
        LocalDate date = LocalDate.of(2024, 5, 22);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        String formattedDate = date.format(formatter);
        log.info("Formatted Date: " + formattedDate);
    }

    // Adding days, weeks, months, and years
    public static void addToDate() {
        LocalDate date = LocalDate.of(2024, 5, 22);
        LocalDate newDate = date.plusDays(10).plusWeeks(2).plusMonths(1).plusYears(1);
        log.info("Date after adding: " + newDate);
    }

    // Subtracting days, weeks, months, and years
    public static void subtractFromDate() {
        LocalDate date = LocalDate.of(2024, 5, 22);
        LocalDate newDate = date.minusDays(10).minusWeeks(2).minusMonths(1).minusYears(1);
        log.info("Date after subtracting: " + newDate);
    }

    // Comparing two dates
    public static void compareDates() {
        LocalDate date1 = LocalDate.of(2024, 5, 22);
        LocalDate date2 = LocalDate.of(2023, 5, 22);
        log.info("Is date1 after date2? " + date1.isAfter(date2));
        log.info("Is date1 before date2? " + date1.isBefore(date2));
        log.info("Are date1 and date2 equal? " + date1.isEqual(date2));
    }

    // Getting day of the week, day of the month, and day of the year
    public static void dayDetails() {
        LocalDate date = LocalDate.of(2024, 5, 22);
        log.info("Day of the Week: " + date.getDayOfWeek());
        log.info("Day of the Month: " + date.getDayOfMonth());
        log.info("Day of the Year: " + date.getDayOfYear());
    }

    // Finding the difference between two dates
    public static void differenceBetweenDates() {
        LocalDate date1 = LocalDate.of(2024, 5, 22);
        LocalDate date2 = LocalDate.of(2023, 5, 22);
        long daysBetween = ChronoUnit.DAYS.between(date2, date1);
        log.info("Days between date1 and date2: " + daysBetween);
    }

    // Using Period to find the difference in years, months, and days
    public static void periodBetweenDates() {
        LocalDate date1 = LocalDate.of(2024, 5, 22);
        LocalDate date2 = LocalDate.of(2023, 5, 22);
        Period period = Period.between(date2, date1);
        log.info("Period between date1 and date2: " + period.getYears() + " years, " +
                period.getMonths() + " months, " + period.getDays() + " days");
    }

    // Creating a LocalDate using of() method with Month enum
    public static void createDateWithMonthEnum() {
        LocalDate date = LocalDate.of(2024, Month.MAY, 22);
        log.info("Created Date with Month Enum: " + date);
    }

    // Returns a LocalDate based on the current date plus a period of 1 year, 2 months, and 3 days
    public static void exampleCustomPeriod() {
        Period period = Period.of(1, 2, 3);
        log.info("exampleCustomPeriod() " + LocalDate.now().plus(period));
    }

    // Returns true if the current year is a leap year
    public static boolean exampleIsLeapYear() {
        return LocalDate.now().isLeapYear();
    }

    // Returns the length of the month of the current date
    public static int exampleLengthOfMonth() {
        return LocalDate.now().lengthOfMonth();
    }

    // Returns the length of the year of the current date
    public static int exampleLengthOfYear() {
        return LocalDate.now().lengthOfYear();
    }

    public static void main(String[] args) {
        currentDate();
        createDate();
        parseDate();
        formatDate();
        addToDate();
        subtractFromDate();
        compareDates();
        dayDetails();
        differenceBetweenDates();
        periodBetweenDates();
        createDateWithMonthEnum();
        exampleCustomPeriod();
        exampleIsLeapYear();
        exampleLengthOfMonth();
        exampleLengthOfYear();
    }
}

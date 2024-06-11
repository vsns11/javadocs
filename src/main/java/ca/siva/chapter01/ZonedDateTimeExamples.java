package ca.siva.chapter01;

import lombok.extern.slf4j.Slf4j;

import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import java.time.Period;
import java.time.Duration;

@Slf4j
public class ZonedDateTimeExamples {

    // Basic usage of ZonedDateTime.now()
    public static void currentDateTime() {
        ZonedDateTime now = ZonedDateTime.now();
        log.info("Current ZonedDateTime: " + now);
    }

    // Creating a ZonedDateTime using of() method
    public static void createDateTime() {
        ZonedDateTime dateTime = ZonedDateTime.of(2024, 5, 22, 14, 30, 0, 0, ZoneId.of("America/New_York"));
        log.info("Created ZonedDateTime: " + dateTime);
    }

    // Parsing a ZonedDateTime from a string
    public static void parseDateTime() {
        ZonedDateTime dateTime = ZonedDateTime.parse("2024-05-22T14:30:00-04:00[America/New_York]");
        log.info("Parsed ZonedDateTime: " + dateTime);
    }

    // Formatting a ZonedDateTime to a string
    public static void formatDateTime() {
        ZonedDateTime dateTime = ZonedDateTime.of(2024, 5, 22, 14, 30, 0, 0, ZoneId.of("America/New_York"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm z");
        String formattedDateTime = dateTime.format(formatter);
        log.info("Formatted ZonedDateTime: " + formattedDateTime);
    }

    // Adding days, weeks, months, years, hours, minutes, and seconds
    public static void addToDateTime() {
        ZonedDateTime dateTime = ZonedDateTime.of(2024, 5, 22, 14, 30, 0, 0, ZoneId.of("America/New_York"));
        ZonedDateTime newDateTime = dateTime.plusDays(10).plusWeeks(2).plusMonths(1).plusYears(1)
                .plusHours(5).plusMinutes(45).plusSeconds(30);
        log.info("ZonedDateTime after adding: " + newDateTime);
    }

    // Subtracting days, weeks, months, years, hours, minutes, and seconds
    public static void subtractFromDateTime() {
        ZonedDateTime dateTime = ZonedDateTime.of(2024, 5, 22, 14, 30, 0, 0, ZoneId.of("America/New_York"));
        ZonedDateTime newDateTime = dateTime.minusDays(10).minusWeeks(2).minusMonths(1).minusYears(1)
                .minusHours(5).minusMinutes(45).minusSeconds(30);
        log.info("ZonedDateTime after subtracting: " + newDateTime);
    }

    // Comparing two ZonedDateTimes
    public static void compareDateTimes() {
        ZonedDateTime dateTime1 = ZonedDateTime.of(2024, 5, 22, 14, 30, 0, 0, ZoneId.of("America/New_York"));
        ZonedDateTime dateTime2 = ZonedDateTime.of(2023, 5, 22, 14, 30, 0, 0, ZoneId.of("America/New_York"));
        log.info("Is dateTime1 after dateTime2? " + dateTime1.isAfter(dateTime2));
        log.info("Is dateTime1 before dateTime2? " + dateTime1.isBefore(dateTime2));
        log.info("Are dateTime1 and dateTime2 equal? " + dateTime1.isEqual(dateTime2));
    }

    // Getting day of the week, day of the month, and day of the year
    public static void dayDetails() {
        ZonedDateTime dateTime = ZonedDateTime.of(2024, 5, 22, 14, 30, 0, 0, ZoneId.of("America/New_York"));
        log.info("Day of the Week: " + dateTime.getDayOfWeek());
        log.info("Day of the Month: " + dateTime.getDayOfMonth());
        log.info("Day of the Year: " + dateTime.getDayOfYear());
        log.info("{}", dateTime.getMonth());
    }

    // Finding the difference between two ZonedDateTimes in various units
    public static void differenceBetweenDateTimes() {
        ZonedDateTime dateTime1 = ZonedDateTime.of(2024, 5, 22, 14, 30, 0, 0, ZoneId.of("America/New_York"));
        ZonedDateTime dateTime2 = ZonedDateTime.of(2023, 5, 22, 14, 30, 0, 0, ZoneId.of("America/New_York"));
        long daysBetween = ChronoUnit.DAYS.between(dateTime2, dateTime1);
        long hoursBetween = ChronoUnit.HOURS.between(dateTime2, dateTime1);
        long minutesBetween = ChronoUnit.MINUTES.between(dateTime2, dateTime1);
        log.info("Days between dateTime1 and dateTime2: " + daysBetween);
        log.info("Hours between dateTime1 and dateTime2: " + hoursBetween);
        log.info("Minutes between dateTime1 and dateTime2: " + minutesBetween);
    }

    // Using Period to find the difference in years, months, and days
    public static void periodBetweenDateTimes() {
        ZonedDateTime dateTime1 = ZonedDateTime.of(2024, 5, 22, 14, 30, 0, 0, ZoneId.of("America/New_York"));
        ZonedDateTime dateTime2 = ZonedDateTime.of(2023, 5, 22, 14, 30, 0, 0, ZoneId.of("America/New_York"));
        Period period = Period.between(dateTime2.toLocalDate(), dateTime1.toLocalDate());
        log.info("Period between dateTime1 and dateTime2: " + period.getYears() + " years, " +
                period.getMonths() + " months, " + period.getDays() + " days" +
                "--"+dateTime1.getMonthValue() + "--"+dateTime1.getDayOfMonth() +
                "--"+dateTime1.getDayOfWeek() + "--"+dateTime1.getDayOfWeek());


    }

    // Adding Duration to ZonedDateTime
    public static void addDuration() {
        ZonedDateTime dateTime = ZonedDateTime.of(2024, 5, 22, 10, 0, 0, 0, ZoneId.of("America/New_York"));
        Duration duration = Duration.ofHours(5);
        ZonedDateTime newDateTime = dateTime.plus(duration);
        log.info("ZonedDateTime after adding duration: " + newDateTime);
    }

    // Subtracting Duration from ZonedDateTime
    public static void subtractDuration() {
        ZonedDateTime dateTime = ZonedDateTime.of(2024, 5, 22, 10, 0, 0, 0, ZoneId.of("America/New_York"));
        Duration duration = Duration.ofHours(5);
        ZonedDateTime newDateTime = dateTime.minus(duration);
        log.info("ZonedDateTime after subtracting duration: " + newDateTime);
    }

    // Finding the difference in hours between two ZonedDateTimes
    public static void differenceBetweenDateTimesUsingDuration() {
        ZonedDateTime dateTime1 = ZonedDateTime.of(2024, 5, 22, 10, 0, 0, 0, ZoneId.of("America/New_York"));
        ZonedDateTime dateTime2 = ZonedDateTime.of(2024, 5, 21, 5, 0, 0, 0, ZoneId.of("America/New_York"));
        Duration duration = Duration.between(dateTime2, dateTime1);
        log.info("Hours between dateTime1 and dateTime2: " + duration.toHours());
    }

    public static void main(String[] args) {
        currentDateTime();
        createDateTime();
        parseDateTime();
        formatDateTime();
        addToDateTime();
        subtractFromDateTime();
        compareDateTimes();
        dayDetails();
        differenceBetweenDateTimes();
        periodBetweenDateTimes();
        addDuration();
        subtractDuration();
        differenceBetweenDateTimesUsingDuration();
    }
}

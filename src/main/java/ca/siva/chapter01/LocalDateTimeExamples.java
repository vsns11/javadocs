package ca.siva.chapter01;

import java.time.LocalDateTime;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import java.time.Period;
import java.time.Duration;

public class LocalDateTimeExamples {

    // Basic usage of LocalDateTime.now()
    public static void currentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("Current DateTime: " + now);
    }

    // Creating a LocalDateTime using of() method
    public static void createDateTime() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 5, 22, 14, 30);
        System.out.println("Created DateTime: " + dateTime);
    }

    // Parsing a LocalDateTime from a string
    public static void parseDateTime() {
        LocalDateTime dateTime = LocalDateTime.parse("2024-05-22T14:30:00");
        System.out.println("Parsed DateTime: " + dateTime);
    }

    // Formatting a LocalDateTime to a string
    public static void formatDateTime() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 5, 22, 14, 30);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");
        String formattedDateTime = dateTime.format(formatter);
        System.out.println("Formatted DateTime: " + formattedDateTime);
    }

    // Adding days, weeks, months, years, hours, minutes, and seconds
    public static void addToDateTime() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 5, 22, 14, 30);
        LocalDateTime newDateTime = dateTime.plusDays(10).plusWeeks(2).plusMonths(1).plusYears(1)
                .plusHours(5).plusMinutes(45).plusSeconds(30);
        System.out.println("DateTime after adding: " + newDateTime);
    }

    // Subtracting days, weeks, months, years, hours, minutes, and seconds
    public static void subtractFromDateTime() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 5, 22, 14, 30);
        LocalDateTime newDateTime = dateTime.minusDays(10).minusWeeks(2).minusMonths(1).minusYears(1)
                .minusHours(5).minusMinutes(45).minusSeconds(30);
        System.out.println("DateTime after subtracting: " + newDateTime);
    }

    // Comparing two LocalDateTimes
    public static void compareDateTimes() {
        LocalDateTime dateTime1 = LocalDateTime.of(2024, 5, 22, 14, 30);
        LocalDateTime dateTime2 = LocalDateTime.of(2023, 5, 22, 14, 30);
        System.out.println("Is dateTime1 after dateTime2? " + dateTime1.isAfter(dateTime2));
        System.out.println("Is dateTime1 before dateTime2? " + dateTime1.isBefore(dateTime2));
        System.out.println("Are dateTime1 and dateTime2 equal? " + dateTime1.isEqual(dateTime2));
    }

    // Getting day of the week, day of the month, and day of the year
    public static void dayDetails() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 5, 22, 14, 30);
        System.out.println("Day of the Week: " + dateTime.getDayOfWeek());
        System.out.println("Day of the Month: " + dateTime.getDayOfMonth());
        System.out.println("Day of the Year: " + dateTime.getDayOfYear());
    }

    // Finding the difference between two LocalDateTimes in various units
    public static void differenceBetweenDateTimes() {
        LocalDateTime dateTime1 = LocalDateTime.of(2024, 5, 22, 14, 30);
        LocalDateTime dateTime2 = LocalDateTime.of(2023, 5, 22, 14, 30);
        long daysBetween = ChronoUnit.DAYS.between(dateTime2, dateTime1);
        long hoursBetween = ChronoUnit.HOURS.between(dateTime2, dateTime1);
        long minutesBetween = ChronoUnit.MINUTES.between(dateTime2, dateTime1);
        System.out.println("Days between dateTime1 and dateTime2: " + daysBetween);
        System.out.println("Hours between dateTime1 and dateTime2: " + hoursBetween);
        System.out.println("Minutes between dateTime1 and dateTime2: " + minutesBetween);
    }

    // Using Period to find the difference in years, months, and days
    public static void periodBetweenDateTimes() {
        LocalDateTime dateTime1 = LocalDateTime.of(2024, 5, 22, 14, 30);
        LocalDateTime dateTime2 = LocalDateTime.of(2023, 5, 22, 14, 30);
        Period period = Period.between(dateTime2.toLocalDate(), dateTime1.toLocalDate());
        System.out.println("Period between dateTime1 and dateTime2: " + period.getYears() + " years, " +
                period.getMonths() + " months, " + period.getDays() + " days");
    }

    // Adding Duration to LocalDateTime
    public static void addDuration() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 5, 22, 10, 0);
        Duration duration = Duration.ofHours(5);
        LocalDateTime newDateTime = dateTime.plus(duration);
        System.out.println("DateTime after adding duration: " + newDateTime);
    }

    // Subtracting Duration from LocalDateTime
    public static void subtractDuration() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 5, 22, 10, 0);
        Duration duration = Duration.ofHours(5);
        LocalDateTime newDateTime = dateTime.minus(duration);
        System.out.println("DateTime after subtracting duration: " + newDateTime);
    }

    // Finding the difference in hours between two LocalDateTimes
    public static void differenceBetweenDateTimesUsingDuration() {
        LocalDateTime dateTime1 = LocalDateTime.of(2024, 5, 22, 10, 0);
        LocalDateTime dateTime2 = LocalDateTime.of(2024, 5, 21, 5, 0);
        Duration duration = Duration.between(dateTime2, dateTime1);
        System.out.println("Hours between dateTime1 and dateTime2: " + duration.toHours());
    }

    // Converting LocalDateTime to Instant using ZoneId
    public static void convertLocalDateTimeToInstant() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 5, 22, 14, 30);
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = dateTime.atZone(zoneId).toInstant();
        System.out.println("Converted Instant: " + instant);
    }

    // Converting Instant to LocalDateTime using ZoneId
    public static void convertInstantToLocalDateTime() {
        Instant instant = Instant.now();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, zoneId);
        System.out.println("Converted LocalDateTime: " + dateTime);
    }

    // Adding Duration to an Instant
    public static void addDurationToInstant() {
        Instant instant = Instant.now();
        Duration duration = Duration.ofHours(5);
        Instant newInstant = instant.plus(duration);
        System.out.println("Instant after adding duration: " + newInstant);
    }

    // Subtracting Duration from an Instant
    public static void subtractDurationFromInstant() {
        Instant instant = Instant.now();
        Duration duration = Duration.ofHours(5);
        Instant newInstant = instant.minus(duration);
        System.out.println("Instant after subtracting duration: " + newInstant);
    }

    // Finding the difference between two Instants
    public static void differenceBetweenInstants() {
        Instant instant1 = Instant.now();
        Instant instant2 = instant1.plus(Duration.ofHours(5));
        Duration duration = Duration.between(instant1, instant2);
        System.out.println("Hours between instants: " + duration.toHours());
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
        convertLocalDateTimeToInstant();
        convertInstantToLocalDateTime();
        addDurationToInstant();
        subtractDurationFromInstant();
        differenceBetweenInstants();
    }
}

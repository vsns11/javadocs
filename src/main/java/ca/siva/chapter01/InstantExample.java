package ca.siva.chapter01;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

/*
Period: Represents date-based amounts of time in years, months, and days.
Duration: Represents time-based amounts of time in hours, minutes, seconds, and nanoseconds.
Instant: Represents a specific point in time, down to the nanosecond,
useful for timestamping and measuring time intervals.

 */
public class InstantExample {

    public static void main(String[] args) {
        // Current timestamp
        Instant now = Instant.now();
        System.out.println("Current Instant: " + now);

        // Creating Instants from epoch time
        Instant epoch = Instant.ofEpochSecond(0);
        System.out.println("Epoch Instant: " + epoch);

        Instant instantFromMillis = Instant.ofEpochMilli(1627848123456L);
        System.out.println("Instant from Milliseconds: " + instantFromMillis);

        // Parsing an Instant from a string
        Instant parsedInstant = Instant.parse("2023-05-25T10:15:30.00Z");
        System.out.println("Parsed Instant: " + parsedInstant);

        // Adding and subtracting time
        Instant futureInstant = now.plus(Duration.ofDays(10));
        System.out.println("Future Instant (10 days later): " + futureInstant);

        Instant pastInstant = now.minus(5, ChronoUnit.HOURS);
        System.out.println("Past Instant (5 hours earlier): " + pastInstant);

        // Comparing Instants
        boolean isBefore = pastInstant.isBefore(now);
        boolean isAfter = futureInstant.isAfter(now);
        System.out.println("Is pastInstant before now? " + isBefore);
        System.out.println("Is futureInstant after now? " + isAfter);

        // Converting to epoch milliseconds and seconds
        long epochMilli = now.toEpochMilli();
        long epochSecond = now.getEpochSecond();
        System.out.println("Current Instant in Milliseconds: " + epochMilli);
        System.out.println("Current Instant in Seconds: " + epochSecond);

        // Measuring elapsed time
        Instant start = Instant.now();
        // Simulate some processing by sleeping for a moment
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println("Time Elapsed: " + timeElapsed.toMillis() + " milliseconds");
    }
}

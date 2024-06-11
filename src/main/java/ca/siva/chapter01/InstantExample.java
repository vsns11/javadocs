package ca.siva.chapter01;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

/*
Period: Represents date-based amounts of time in years, months, and days.
Duration: Represents time-based amounts of time in hours, minutes, seconds, and nanoseconds.
Instant: Represents a specific point in time, down to the nanosecond,
useful for timestamping and measuring time intervals.

 */
@Slf4j
public class InstantExample {

    public static void main(String[] args) {
        // Current timestamp
        Instant now = Instant.now();
        log.info("Current Instant: " + now);

        // Creating Instants from epoch time
        Instant epoch = Instant.ofEpochSecond(0);
        log.info("Epoch Instant: " + epoch);

        Instant instantFromMillis = Instant.ofEpochMilli(1627848123456L);
        log.info("Instant from Milliseconds: " + instantFromMillis);

        // Parsing an Instant from a string
        Instant parsedInstant = Instant.parse("2023-05-25T10:15:30.00Z");
        log.info("Parsed Instant: " + parsedInstant);

        // Adding and subtracting time
        Instant futureInstant = now.plus(Duration.ofDays(10));
        log.info("Future Instant (10 days later): " + futureInstant);

        Instant pastInstant = now.minus(5, ChronoUnit.HOURS);
        log.info("Past Instant (5 hours earlier): " + pastInstant);

        // Comparing Instants
        boolean isBefore = pastInstant.isBefore(now);
        boolean isAfter = futureInstant.isAfter(now);
        log.info("Is pastInstant before now? " + isBefore);
        log.info("Is futureInstant after now? " + isAfter);

        // Converting to epoch milliseconds and seconds
        long epochMilli = now.toEpochMilli();
        long epochSecond = now.getEpochSecond();
        log.info("Current Instant in Milliseconds: " + epochMilli);
        log.info("Current Instant in Seconds: " + epochSecond);

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
        log.info("Time Elapsed: " + timeElapsed.toMillis() + " milliseconds");
    }
}

package ca.siva.chapter01;

/*
  NOTE:
   1) Always define enum possible values first before declaring the constant field such as "static final".

 */

import lombok.extern.slf4j.Slf4j;

// Custom enum representing months
enum CustomMonth {
    JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER;

    // Custom method to get a display name
    public String getDisplayName() {
        return this.name().charAt(0) + this.name().substring(1).toLowerCase();
    }
}

// Enum with static final field
enum Color {
    BROWN, YELLOW;

    // Static final field
    public static final Color DEFAULT_COLOR = BROWN;

    // Method to get a description of the color
    public String getDescription() {
        return "Color is " + this.name().toLowerCase();
    }
}

@Slf4j
public class EnumExamples {

    // Example of using name() method
    public static void usingNameMethod() {
        CustomMonth month = CustomMonth.MAY;
        log.info("Name: " + month.name());
    }

    // Example of using ordinal() method
    public static void usingOrdinalMethod() {
        CustomMonth month = CustomMonth.MAY;
        log.info("Ordinal: " + month.ordinal());
    }

    // Example of using valueOf() method
    public static void usingValueOfMethod() {
        CustomMonth month = CustomMonth.valueOf("MAY");
        log.info("Month from valueOf: " + month);
    }

    // Example of custom method in enum
    public static void usingCustomMethod() {
        CustomMonth month = CustomMonth.MAY;
        log.info("Custom Month Display: " + month.getDisplayName());
    }

    // Example of using static final field in enum
    public static void usingStaticFinalField() {
        log.info("Default Color: " + Color.DEFAULT_COLOR);
        log.info("Default Color Description: " + Color.DEFAULT_COLOR.getDescription());
    }

    // Example of iterating over enum constants
    public static void iterateOverEnumConstants() {
        log.info("Iterating over CustomMonth:");
        for (CustomMonth month : CustomMonth.values()) {
            log.info(month + ": " + month.getDisplayName());
        }

        log.info("Iterating over Color:");
        for (Color color : Color.values()) {
            log.info(color + ": " + color.getDescription());
        }
    }

    public static void main(String[] args) {
        usingNameMethod();
        usingOrdinalMethod();
        usingValueOfMethod();
        usingCustomMethod();
        usingStaticFinalField();
        iterateOverEnumConstants();
    }
}

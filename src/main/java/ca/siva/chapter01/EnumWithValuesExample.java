package ca.siva.chapter01;

import lombok.extern.slf4j.Slf4j;

/*
 NOTE:
 1) valueOf method will throw IllegalArgumentException when an invalid value is passed.
 2) If enum contains an abstract method, then every enum has to override that method.
 */
enum Season {
    WINTER("Cold season", 0) {
        @Override
        public String activity() {
            return "Skiing";
        }
    },
    SPRING("Mild season", 15) {
        @Override
        public String activity() {
            return "Hiking";
        }
    },
    SUMMER("Hot season", 30) {
        @Override
        public String activity() {
            return "Swimming";
        }
    },
    FALL("Cool season", 10) {
        @Override
        public String activity() {
            return "Leaf Peeping";
        }
    };

    private final String description;
    private final int averageTemperature;

    // Enum constructor
    Season(String description, int averageTemperature) {
        this.description = description;
        this.averageTemperature = averageTemperature;
    }

    // Getter for description
    public String getDescription() {
        return description;
    }

    // Getter for averageTemperature
    public int getAverageTemperature() {
        return averageTemperature;
    }

    // Abstract method that must be overridden by each enum constant
    public abstract String activity();
}

@Slf4j
public class EnumWithValuesExample {

    // Example of accessing custom values in an enum
    public static void accessEnumValues() {
        for (Season season : Season.values()) {
            log.info("Season: " + season);
            log.info("Description: " + season.getDescription());
            log.info("Average Temperature: " + season.getAverageTemperature() + "°C");
            log.info("Activity: " + season.activity());
        }
    }

    // Example of using valueOf() method
    public static void usingValueOfMethod() {
        try {
            Season season = Season.valueOf("SUMMER");
            log.info("Season from valueOf: " + season);
            log.info("Description: " + season.getDescription());
            log.info("Average Temperature: " + season.getAverageTemperature() + "°C");
            log.info("Activity: " + season.activity());
        } catch (IllegalArgumentException e) {
            log.error("Invalid season name: " + e.getMessage());
        }

        // Handle invalid string
        try {
            String invalidSeasonString = "RAIN";
            Season invalidSeason = Season.valueOf(invalidSeasonString);
        } catch (IllegalArgumentException e) {
            log.error("Invalid season name: " + e.getMessage());
        }

        // Example with null
        try {
            String nullSeasonString = null;
            Season nullSeason = Season.valueOf(nullSeasonString);
        } catch (NullPointerException e) {
            log.error("Null season string: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        accessEnumValues();
        usingValueOfMethod();
    }
}

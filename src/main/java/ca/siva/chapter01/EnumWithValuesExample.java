package ca.siva.chapter01;

import lombok.extern.slf4j.Slf4j;

enum Season {
    WINTER("Cold season", 0),
    SPRING("Mild season", 15),
    SUMMER("Hot season", 30),
    FALL("Cool season", 10);

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
}

@Slf4j
public class EnumWithValuesExample {

    // Example of accessing custom values in an enum
    public static void accessEnumValues() {
        for (Season season : Season.values()) {
            log.info("Season: " + season);
            log.info("Description: " + season.getDescription());
            log.info("Average Temperature: " + season.getAverageTemperature() + "°C");
        }
    }

    // Example of using valueOf() method
    public static void usingValueOfMethod() {
        Season season = Season.valueOf("SUMMER");
        log.info("Season from valueOf: " + season);
        log.info("Description: " + season.getDescription());
        log.info("Average Temperature: " + season.getAverageTemperature() + "°C");
    }

    public static void main(String[] args) {
        accessEnumValues();
        usingValueOfMethod();
    }
}

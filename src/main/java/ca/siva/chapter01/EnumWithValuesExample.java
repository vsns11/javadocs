package ca.siva.chapter01;

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

public class EnumWithValuesExample {

    // Example of accessing custom values in an enum
    public static void accessEnumValues() {
        for (Season season : Season.values()) {
            System.out.println("Season: " + season);
            System.out.println("Description: " + season.getDescription());
            System.out.println("Average Temperature: " + season.getAverageTemperature() + "°C");
        }
    }

    // Example of using valueOf() method
    public static void usingValueOfMethod() {
        Season season = Season.valueOf("SUMMER");
        System.out.println("Season from valueOf: " + season);
        System.out.println("Description: " + season.getDescription());
        System.out.println("Average Temperature: " + season.getAverageTemperature() + "°C");
    }

    public static void main(String[] args) {
        accessEnumValues();
        usingValueOfMethod();
    }
}

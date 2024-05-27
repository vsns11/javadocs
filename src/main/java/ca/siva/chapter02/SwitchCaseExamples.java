package ca.siva.chapter02;

import java.time.DayOfWeek;

public class SwitchCaseExamples {

    public static void main(String[] args) {
        /*
        **NOTE: Java does not support boolean in switch statements; use if-else statements instead.
        * Byte in Switch: byte values can be used in switch statements, similar to other integral types.
Default Case: Can be omitted if it is guaranteed that all possible values are explicitly covered by case labels.
         */
        oldSwitchCaseExample(2);
        enhancedSwitchCaseExample(3);
        switchExpressionExample("MONDAY");
        switchWithMultipleLabelsExample(5);
        yieldInSwitchExpressionExample("TUESDAY");
        switchWithoutDefaultExample(3);
        exhaustiveSwitchExample(DayOfWeek.MONDAY);
        byteSwitchExample((byte)2);
        defaultVariableValueInSwitch(123);
        switchWithOnlyDefaultCase(1);
    }

    // Old switch-case style
    public static void oldSwitchCaseExample(int day) {
        String dayName;
        switch (day) {
            case 1:
                dayName = "Sunday";
                break;
            case 2:
                dayName = "Monday";
                break;
            case 3:
                dayName = "Tuesday";
                break;
            case 4:
                dayName = "Wednesday";
                break;
            case 5:
                dayName = "Thursday";
                break;
            case 6:
                dayName = "Friday";
                break;
            case 7:
                dayName = "Saturday";
                break;
            default:
                dayName = "Invalid day";
                break;
        }
        System.out.println("Old switch case day name: " + dayName);
    }

    // Enhanced switch-case style
    public static void enhancedSwitchCaseExample(int day) {
        String dayName = switch (day) {
            case 1 -> "Sunday";
            case 2 -> "Monday";
            case 3 -> "Tuesday";
            case 4 -> "Wednesday";
            case 5 -> "Thursday";
            case 6 -> "Friday";
            case 7 -> "Saturday";
            default -> "Invalid day";
        };
        System.out.println("Enhanced switch case day name: " + dayName);
    }

    // Switch expression example
    public static void switchExpressionExample(String day) {
        int dayNumber = switch (day) {
            case "SUNDAY" -> 1;
            case "MONDAY" -> 2;
            case "TUESDAY" -> 3;
            case "WEDNESDAY" -> 4;
            case "THURSDAY" -> 5;
            case "FRIDAY" -> 6;
            case "SATURDAY" -> 7;
            default -> throw new IllegalArgumentException("Invalid day: " + day);
        };
        System.out.println("Switch expression day number: " + dayNumber);
    }

    // Switch with multiple case labels
    public static void switchWithMultipleLabelsExample(int month) {
        String season = switch (month) {
            case 12, 1, 2 -> "Winter";
            case 3, 4, 5 -> "Spring";
            case 6, 7, 8 -> "Summer";
            case 9, 10, 11 -> "Fall";
            default -> throw new IllegalArgumentException("Invalid month: " + month);
        };
        System.out.println("Season: " + season);
    }

    // Using yield in switch expression
    public static void yieldInSwitchExpressionExample(String day) {
        int workHours = switch (day) {
            case "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY" -> 8;
            case "SATURDAY", "SUNDAY" -> 0;
            default -> {
                System.out.println("Invalid day: " + day);
                yield -1; // Use yield to return a value from a block
            }
        };
        System.out.println("Work hours on " + day + ": " + workHours);
    }

    // Switch expression example without default case (skipped default)
    public static void switchWithoutDefaultExample(int day) {
        String dayName = switch (day) {
            case 1 -> "Sunday";
            case 2 -> "Monday";
            case 3 -> "Tuesday";
            case 4 -> "Wednesday";
            case 5 -> "Thursday";
            case 6 -> "Friday";
            case 7 -> "Saturday";
            // No default case
            default -> "N/A";
        };
        System.out.println("Switch without default case day name: " + dayName);
    }

    // Exhaustive switch example with enum (skipped default)
    public static void exhaustiveSwitchExample(DayOfWeek day) {
        String typeOfDay = switch (day) {
            case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY -> "Weekday";
            case SATURDAY, SUNDAY -> "Weekend";
            // No default case needed because all enum values are covered
        };
        System.out.println("Exhaustive switch case type of day: " + typeOfDay);
    }


    public static void byteSwitchExample(byte value) {
        switch (value) {
            case 1 -> System.out.println("Value is 1");
            case 2 -> System.out.println("Value is 2");
            case 3 -> System.out.println("Value is 3");
            // No default case
        }
    }

    public static void defaultVariableValueInSwitch(int value) {
        String result;
        switch (123) { // This will always use the value 123
            case 1 -> result = "One";
            case 2 -> result = "Two";
            case 3 -> result = "Three";
            default -> result = "Unknown"; // Default value assignment
        }
        System.out.println("The result is: " + result); // Will always print "Unknown"
    }

    // Boolean example using if-else
    public static void booleanExample(boolean flag) {
        /*
         **NOTE: Java does not support boolean in switch statements; use if-else statements instead.
         */
        if (flag) {
            System.out.println("Flag is true");
        } else {
            System.out.println("Flag is false");
        }
    }

    // Switch statement with only a default case
    public static void switchWithOnlyDefaultCase(int value) {
        switch (value) {
            default -> System.out.println("Default case executed with value: " + value);
        }
    }

    // Enum representing days of the week
    enum DayOfWeek {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }
}
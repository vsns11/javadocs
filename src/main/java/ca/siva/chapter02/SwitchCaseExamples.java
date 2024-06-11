package ca.siva.chapter02;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SwitchCaseExamples {

    /*
     1) It doesn't support floating types like float, object and double.
     However, it does support byte, char, int, short, and their wrapper classes
     2) Traditional switch statements does support return
     3) Enum constant name should be used when defined switch case. ***Do not use <ClsName>.<Enum> in case expression
     */
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
        byteSwitchWithDefaultExample((byte)5);
//        varInSwitchCaseWithDoubleExample(10.5);  // New example method
//        varInSwitchCaseWithDoubleExample("Hello");  // New example method
        instanceofPatternMatchingExample("Hello");  // New example method
        instanceofPatternMatchingExample(123);  // New example method
        instanceofPatternMatchingWithOrExample("Hello, World!");  // New example method
        instanceofPatternMatchingWithOrExample(123);  // New example method
        instanceofPatternMatchingWithOrExample(3.14);  // New example method

        enumWithDefaultCaseExample(DayOfWeek.MONDAY);
        enumWithDefaultCaseExample(DayOfWeek.SUNDAY);
        enumWithDefaultCaseExample(null);  // Handling null value
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
        log.info("Old switch case day name: " + dayName);
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
        log.info("Enhanced switch case day name: " + dayName);
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
        log.info("Switch expression day number: " + dayNumber);
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
        log.info("Season: " + season);
    }

    // Using yield in switch expression
    public static void yieldInSwitchExpressionExample(String day) {
        int workHours = switch (day) {
            case "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY" -> 8;
            case "SATURDAY", "SUNDAY" -> 0;
            default -> {
                log.info("Invalid day: " + day);
                yield -1; // Use yield to return a value from a block
            }
        };
        log.info("Work hours on " + day + ": " + workHours);
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
        log.info("Switch without default case day name: " + dayName);
    }

    // Exhaustive switch example with enum (skipped default)
    public static void exhaustiveSwitchExample(DayOfWeek day) {
        String typeOfDay = switch (day) {
            case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY -> "Weekday";
            case SATURDAY, SUNDAY -> "Weekend";
            // No default case needed because all enum values are covered
        };
        log.info("Exhaustive switch case type of day: " + typeOfDay);
    }

    public static void byteSwitchExample(byte value) {
        switch (value) {
            case 1 -> log.info("Value is 1");
            case 2 -> log.info("Value is 2");
            case 3 -> log.info("Value is 3");
            // No default case because assignment is not present for the return value
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
        log.info("The result is: " + result); // Will always print "Unknown"
    }

    // Boolean example using if-else
    public static void booleanExample(boolean flag) {
        /*
         **NOTE: Java does not support boolean in switch statements; use if-else statements instead.
         */
        if (flag) {
            log.info("Flag is true");
        } else {
            log.info("Flag is false");
        }
    }

    // Switch statement with only a default case
    public static void switchWithOnlyDefaultCase(int value) {
        switch (value) {
            default -> log.info("Default case executed with value: " + value);
        }
    }

    // Byte switch case with default
    public static void byteSwitchWithDefaultExample(byte value) {
        switch (value) {
            case 1 -> log.info("Value is 1");
            case 2 -> log.info("Value is 2");
            case 3 -> log.info("Value is 3");
            default -> log.info("Default case: Value is " + value);
        }
    }

    // Example using var in switch case with double cast
//    public static void varInSwitchCaseWithDoubleExample(Object obj) {
//        var result = switch (obj) {
//            case Integer i -> (double) i;
//            case String s -> s.length() * 1.0;
//            case Double d -> d;
//            default -> -1.0;
//        };
//        log.info("Var in switch case with double result: " + result);
//    }

    // Example using instanceof pattern matching with flow scoping
    public static void instanceofPatternMatchingExample(Object obj) {
        if (obj instanceof String s) {
            log.info("String with length: " + s.length());
        } else if (obj instanceof Integer i) {
            log.info("Integer with value: " + i);
        } else if (obj instanceof Double d) {
            log.info("Double with value: " + d);
        } else {
            log.info("Unknown type");
        }
    }

    // Example using instanceof pattern matching with || check
    public static void instanceofPatternMatchingWithOrExample(Object obj) {
        if (obj instanceof String s && (s.length() > 5 || s.contains("Hello"))) {
            log.info("String with length > 5 or contains 'Hello': " + s);
        } else if (obj instanceof Integer i && (i > 100 || i % 2 == 0)) {
            log.info("Integer > 100 or even: " + i);
        } else if (obj instanceof Double d && (d > 0 || d.isNaN())) {
            log.info("Double > 0 or NaN: " + d);
        }
        /*
        *** The below does not work because instanceof pattern matching uses flowtyping
        * and d won't be available to access, hence compilation error.

        else if (obj instanceof Integer d || Math.abs(d) == 0) {
        }*/

        else {
            log.info("Unknown type or condition not met");
        }
    }

    // Enum representing days of the week
    enum DayOfWeek {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

    // New method demonstrating a switch case with both default and specific enum cases
    public static void enumWithDefaultCaseExample(DayOfWeek day) {
        String typeOfDay = switch (day) {
            case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY -> "Weekday";
            case SATURDAY, SUNDAY -> "Weekend";
            default -> {
                if (day == null) {
                    yield "Null day"; // Special handling for null value
                }
                yield "Unknown day"; // Default case
            }
        };
        log.info("Enum switch with default case type of day: " + typeOfDay);
    }

    public static void enumWithDefaultAndNormalCase(DayOfWeek day) {
        String ans = "";
         switch (day) {
             case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY :
                 ans = "Weekday";
                 break;
            case SATURDAY:
             default:
                 ans = "avc";
                 break;
        };
        log.info("Enum switch with default case type of day: " + ans);
    }

    public static void enumSwitchWithoutBreak(DayOfWeek day) {
        String ans = "";
        switch (day) {
            case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY :
                ans = "Weekday";
            case SATURDAY:
            default:
                ans = "avc";
                break;
        };
        log.info("Enum switch with default case type of day: " + ans);
    }
}

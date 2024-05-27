package ca.siva.chapter01;

enum CustomMonth {
    JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER;

    public String getDisplayName() {
        return this.name().charAt(0) + this.name().substring(1).toLowerCase();
    }
}

public class EnumExamples {

    // Example of using name() method
    public static void usingNameMethod() {
        CustomMonth month = CustomMonth.MAY;
        System.out.println("Name: " + month.name());
    }

    // Example of using ordinal() method
    public static void usingOrdinalMethod() {
        CustomMonth month = CustomMonth.MAY;
        System.out.println("Ordinal: " + month.ordinal());
    }

    // Example of using valueOf() method
    public static void usingValueOfMethod() {
        CustomMonth month = CustomMonth.valueOf("MAY");
        System.out.println("Month from valueOf: " + month);
    }

    // Example of custom method in enum
    public static void usingCustomMethod() {
        CustomMonth month = CustomMonth.MAY;
        System.out.println("Custom Month Display: " + month.getDisplayName());
    }

    public static void main(String[] args) {
        usingNameMethod();
        usingOrdinalMethod();
        usingValueOfMethod();
        usingCustomMethod();
    }
}

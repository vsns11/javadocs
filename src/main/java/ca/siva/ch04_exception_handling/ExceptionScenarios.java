package ca.siva.ch04_exception_handling;

import java.io.*;
import java.sql.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
/*
NOTE:
1) Exception thrown inside the catch(){} need to be sourrounded by another try-catch.
2) Closeable interface is mainly used for I/O ops, which extends autoClosable interface.
3) close() method is invoked after the end of try{} block execution
4) An exception thrown inside the close() will become a suppressed exception and the one thrown inside the try{} would be a primary exception.
5) If close() throws an exception, and catch() block also throws an exception, the close() ex would be the primary exception and the catch one would be the suppressed one.
6) If parent class method does not have "throws" and a child class's overriding method can have "throws <RuntimeException or its subclasses>".
7) The variables used in the try-with-resources should always be final or effectively final.
8) You cannot unnecessarily declare a checked exception in catch without having it defined with throws
9) You cannot use multi-catch block to catch exceptions if one of the exceptions is a subclass of another.
e.g., catch (FileNotFoundException | IOException e) -> this won't compile, as FileNotFoundException is a subclass of IOException
10) When a method do throws <checked or unchecked exception> it is not required for that method to actually throw that declared exception.
But the caller has to mandatory implement that method in case of a checked exception.
11) Any checked exception that's thrown inside close() must be implemented by the caller in try{} catch{}
12) The subclass covariant check applies only on checked exceptions, but not on RuntimeExceptions.
13) When both try and finally throws an exception, the one thrown in finally would become the primary exception.
14) The close() method in a try-with-resources block is called before the finally block.
If both a close() method and a finally block are present, the resource is guaranteed to be closed before the finally block executes.
15) A try-with-resources statement can have catch as well as finally blocks but does not require either.

 */
public class ExceptionScenarios {

    public static void main(String[] args) {
        arithmeticExceptionExample();
        nullPointerExceptionExample();
        arrayIndexOutOfBoundsExceptionExample();
        numberFormatExceptionExample();
        fileNotFoundExceptionExample();
        ioExceptionExample();
        classNotFoundExceptionExample();
        sqlExceptionExample();
        illegalArgumentExceptionExample();
        customExceptionExample();
    }

    static void arithmeticExceptionExample() {
        try {
            int result = 10 / 0; // Will throw ArithmeticException
        } catch (ArithmeticException e) {
            log.error("Caught ArithmeticException: ", e);
        }
    }

    static void nullPointerExceptionExample() {
        try {
            String str = null;
            str.length(); // Will throw NullPointerException
        } catch (NullPointerException e) {
            log.error("Caught NullPointerException: ", e);
        }
    }

    static void arrayIndexOutOfBoundsExceptionExample() {
        try {
            int[] array = new int[5];
            int element = array[10]; // Will throw ArrayIndexOutOfBoundsException
        } catch (ArrayIndexOutOfBoundsException e) {
            log.error("Caught ArrayIndexOutOfBoundsException: ", e);
        }
    }

    static void numberFormatExceptionExample() {
        try {
            int number = Integer.parseInt("ABC"); // Will throw NumberFormatException
        } catch (NumberFormatException e) {
            log.error("Caught NumberFormatException: ", e);
        }
    }

    static void fileNotFoundExceptionExample() {
        try {
            FileInputStream fis = new FileInputStream("nonexistentfile.txt"); // Will throw FileNotFoundException
        } catch (FileNotFoundException e) {
            log.error("Caught FileNotFoundException: ", e);
        }
    }

    static void ioExceptionExample() {
        try {
            FileReader fr = new FileReader("nonexistentfile.txt");
            fr.read(); // Will throw IOException
        } catch (IOException e) {
            log.error("Caught IOException: ", e);
        }
    }

    static void classNotFoundExceptionExample() {
        try {
            Class.forName("nonexistent.Class"); // Will throw ClassNotFoundException
        } catch (ClassNotFoundException e) {
            log.error("Caught ClassNotFoundException: ", e);
        }
    }

    static void sqlExceptionExample() {
        try {
            DriverManager.getConnection("jdbc:mysql://localhost:3306/nonexistentdb", "user", "password"); // Will throw SQLException
        } catch (SQLException e) {
            log.error("Caught SQLException: ", e);
        }
    }

    static void illegalArgumentExceptionExample() {
        try {
            int[] array = new int[-5]; // Will throw IllegalArgumentException
        } catch (IllegalArgumentException e) {
            log.error("Caught IllegalArgumentException: ", e);
        }
    }

    static void customExceptionExample() {
        try {
            throw new CustomException("This is a custom exception"); // Will throw CustomException
        } catch (CustomException e) {
            log.error("Caught CustomException: ", e);
        }
    }

    static void testMustUseCustomRunTimeException() {
        new MustUseCustomRunTimeException().testRunTimeException();
    }
}

class CustomException extends Exception {
    public CustomException(String message) {
        super(message);
    }
}

class CustomClosableRsc implements AutoCloseable {
    public void close() throws Exception {
    }
}

class MustUseCustomRunTimeException {
  public void testRunTimeException() throws RuntimeException {
      throw new ArrayIndexOutOfBoundsException();

   }

}
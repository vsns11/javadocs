package ca.siva.ch09_io_and_nio;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/*
NOTE:
    1) System.in uses inputStream and System.out and System.err uses printStream
    2) It is fine to declare BufferedInputStream Object outside try-with-resources block and refer inside the try block
    3) In printf, %5.2f means 2 decimal values after "." and overall min width should be of 5, if not, spaces are added to the left.
    4) During deserialization, the constructor of the class (or any static or instance blocks) is not executed. However, if the super class does not implement Serializable,
    its constructor is called. So here, BooBoo and Boo are not Serializable. So, their constructor is invoked.
    5) java.nio.file.NoSuchFileException will be thrown when the program tries to create a BufferedReader to read the file specified by the Path object that does not exist.
    6) write/readString are not a valid methods in Data[Output|Input]Stream class.
    If you need to write and read Strings, you should use writeUTF and readUTF.
 */
@Slf4j
public class InputStreamAndOutputStreamExample {

    /**
     * About: Reads the contents of a file using FileInputStream.
     * Input: A file path to read from, e.g., "/path/to/input.txt".
     * Output: Logs the contents of the file.
     */
    public static void readFileWithFileInputStream() {
        String filePath = "/path/to/input.txt";
        try (InputStream inputStream = new FileInputStream(filePath)) {
            int data;
            StringBuilder content = new StringBuilder();
            while ((data = inputStream.read()) != -1) {
                content.append((char) data);  // Convert byte to char and append to the string
            }
            log.info("File content: {}", content);
        } catch (IOException e) {
            log.error("An error occurred while reading the file", e);
        }
    }

    /**
     * About: Demonstrates mark, markSupported, and skip methods using ByteArrayInputStream.
     * Input: A byte array.
     * Output: Logs the behavior of mark, skip, and reset operations.
     */
    public static void demonstrateMarkAndSkip() {
        byte[] byteArray = "Hello, World!".getBytes();
        try (InputStream inputStream = new ByteArrayInputStream(byteArray)) {
            log.info("markSupported: {}", inputStream.markSupported());

            // Mark the current position in the stream
            if (inputStream.markSupported()) {
                inputStream.mark(10);  // Mark this position with a read limit of 10 bytes
                log.info("Marked at position 0");

                // Read a few bytes
                int data = inputStream.read();
                log.info("Read first byte: {}", (char) data);  // H

                // Skip a few bytes
                long skippedBytes = inputStream.skip(5);
                log.info("Skipped {} bytes", skippedBytes);  // Skips "ello,"

                // Read the next byte
                data = inputStream.read();
                log.info("Read next byte after skip: {}", (char) data);  // W

                // Reset to the marked position
                inputStream.reset();
                log.info("Stream reset to marked position");

                // Read again from the reset position
                data = inputStream.read();
                log.info("Read byte after reset: {}", (char) data);  // H
            } else {
                log.info("Mark not supported");
            }
        } catch (IOException e) {
            log.error("An error occurred while using mark and skip", e);
        }
    }

    /**
     * About: Writes data to a file using FileOutputStream.
     * Input: A file path to write to, e.g., "/path/to/output.txt", and a string to write.
     * Output: Writes the data to the file.
     */
    public static void writeFileWithFileOutputStream() {
        String filePath = "/path/to/output.txt";
        String data = "This is an example of writing to a file using FileOutputStream.";

        try (OutputStream outputStream = new FileOutputStream(filePath)) {
            outputStream.write(data.getBytes());  // Convert string to bytes and write to the file
            log.info("Data written to file: {}", filePath);
        } catch (IOException e) {
            log.error("An error occurred while writing to the file", e);
        }
    }

    /**
     * About: Reads data from a byte array using ByteArrayInputStream.
     * Input: A byte array containing data.
     * Output: Logs the data read from the byte array.
     */
    public static void readFromByteArray() {
        byte[] byteArray = "This is a string in a byte array.".getBytes();
        try (InputStream inputStream = new ByteArrayInputStream(byteArray)) {
            int data;
            StringBuilder content = new StringBuilder();
            while ((data = inputStream.read()) != -1) {
                content.append((char) data);  // Convert byte to char and append to the string
            }
            log.info("Byte array content: {}", content);
        } catch (IOException e) {
            log.error("An error occurred while reading from the byte array", e);
        }
    }

    /**
     * About: Serializes and deserializes an object using ObjectOutputStream and ObjectInputStream.
     * Input: An object to serialize and then deserialize.
     * Output: Logs the deserialized object.
     */
    public static void serializeAndDeserializeObject() {
        String filePath = "/path/to/serialized_object.ser";
        try {
            // Serialize the object
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
                ExampleObject originalObject = new ExampleObject("example", 123);
                objectOutputStream.writeObject(originalObject);
                log.info("Object serialized: {}", originalObject);
            }

            // Deserialize the object
            try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filePath))) {
                ExampleObject deserializedObject = (ExampleObject) objectInputStream.readObject();
                log.info("Object deserialized: {}", deserializedObject);
            }

        } catch (IOException | ClassNotFoundException e) {
            log.error("An error occurred during object serialization/deserialization", e);
        }
    }

    /**
     * About: Writes data to a file using PrintStream.
     * Input: A file path to write to, e.g., "/path/to/output.txt", and a string to write.
     * Output: Writes the data to the file using PrintStream.
     */
    public static void writeWithPrintStream() {
        String filePath = "/path/to/output.txt";
        String data = "This is an example of writing to a file using PrintStream.";

        try (PrintStream printStream = new PrintStream(new FileOutputStream(filePath))) {
            printStream.println(data);  // Write data to the file
            log.info("Data written to file with PrintStream: {}", filePath);
        } catch (IOException e) {
            log.error("An error occurred while writing to the file with PrintStream", e);
        }
    }

    /**
     * About: Reads the contents of a file using FilterInputStream.
     * Input: A file path to read from, e.g., "/path/to/input.txt".
     * Output: Logs the contents of the file.
     */
    public static void readWithFilterInputStream() {
        String filePath = "/path/to/input.txt";
        try (InputStream inputStream = new FilterInputStream(new FileInputStream(filePath)) {}) {
            int data;
            StringBuilder content = new StringBuilder();
            while ((data = inputStream.read()) != -1) {
                content.append((char) data);  // Convert byte to char and append to the string
            }
            log.info("File content (FilterInputStream): {}", content);
        } catch (IOException e) {
            log.error("An error occurred while reading the file with FilterInputStream", e);
        }
    }


    /**
     * About: Reads the contents of a file using FileReader.
     * Input: A file path to read from, e.g., "/path/to/input.txt".
     * Output: Logs the contents of the file.
     */
    public static void readFileWithFileReader() {
        String filePath = "/path/to/input.txt";
        try (Reader reader = new FileReader(filePath)) {
            int data;
            StringBuilder content = new StringBuilder();
            while ((data = reader.read()) != -1) {
                content.append((char) data);  // Convert int to char and append to the string
            }
            log.info("File content (FileReader): {}", content);
        } catch (IOException e) {
            log.error("An error occurred while reading the file with FileReader", e);
        }
    }

    /**
     * About: Reads the contents of a file using BufferedReader for efficient reading.
     * Input: A file path to read from, e.g., "/path/to/input.txt".
     * Output: Logs the contents of the file.
     */
    public static void readFileWithBufferedReader() {
        String filePath = "/path/to/input.txt";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());  // Append the line and a newline character
            }
            log.info("File content (BufferedReader): {}", content);
        } catch (IOException e) {
            log.error("An error occurred while reading the file with BufferedReader", e);
        }
    }

    /**
     * About: Reads the contents of an InputStream using InputStreamReader.
     * Input: An InputStream to read from, e.g., System.in or a file input stream.
     * Output: Logs the contents read from the InputStream.
     */
    public static void readInputStreamWithInputStreamReader() {
        String filePath = "/path/to/input.txt";
        try (InputStream inputStream = new FileInputStream(filePath);
             Reader reader = new InputStreamReader(inputStream)) {
            int data;
            StringBuilder content = new StringBuilder();
            while ((data = reader.read()) != -1) {
                content.append((char) data);  // Convert int to char and append to the string
            }
            log.info("InputStream content (InputStreamReader): {}", content);
        } catch (IOException e) {
            log.error("An error occurred while reading the InputStream with InputStreamReader", e);
        }
    }

    /**
     * About: Reads from a character array using CharArrayReader.
     * Input: A character array.
     * Output: Logs the content read from the character array.
     */
    public static void readFromCharArray() {
        char[] charArray = "This is a string in a char array.".toCharArray();
        try (Reader reader = new CharArrayReader(charArray)) {
            int data;
            StringBuilder content = new StringBuilder();
            while ((data = reader.read()) != -1) {
                content.append((char) data);  // Convert int to char and append to the string
            }
            log.info("CharArray content (CharArrayReader): {}", content);
        } catch (IOException e) {
            log.error("An error occurred while reading from the character array", e);
        }
    }

    /**
     * About: Demonstrates formatted output using System.out.printf().
     * Input: A set of variables (e.g., a string, integer, and floating-point number).
     * Output: Logs formatted output using System.out.printf().
     */
    public static void demonstrateFormattedOutput() {
        String name = "John Doe";
        int age = 30;
        double salary = 45000.75;

        // Using System.out.printf() to print formatted output
        System.out.printf("Name: %s, Age: %d, Salary: $%.2f%n", name, age, salary);
    }

    /**
     * About: Reads data from an input file and writes it to an output file using InputStream and OutputStream.
     * Input: A source file (e.g., "input.txt") to read from.
     * Output: An output file (e.g., "output.txt") to write the data to.
     */
    public static void readAndWriteBytes() {
        String inputFile = "input.txt";
        String outputFile = "output.txt";

        try (InputStream inputStream = new FileInputStream(inputFile);
             OutputStream outputStream = new FileOutputStream(outputFile)) {

            byte[] buffer = new byte[1024]; // Buffer to hold bytes during read and write
            int bytesRead;

            // Read and write in chunks using a while loop
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            log.info("Data successfully copied from {} to {}", inputFile, outputFile);

        } catch (IOException e) {
            log.error("An error occurred during the read/write process", e);
        }
    }

    /**
     * About: Reads data from an input file and writes it to an output file using InputStream and OutputStream, one byte at a time.
     * Input: A source file (e.g., "input.txt") to read from.
     * Output: An output file (e.g., "output.txt") to write the data to.
     */
    public static void readAndWriteOneByteAtATime() {
        String inputFile = "input.txt";
        String outputFile = "output.txt";

        try (InputStream inputStream = new FileInputStream(inputFile);
             OutputStream outputStream = new FileOutputStream(outputFile)) {

            int data;

            // Read and write one byte at a time using a while loop
            while ((data = inputStream.read()) != -1) {
                outputStream.write(data);  // Write the single byte to the output stream
            }

            log.info("Data successfully copied from {} to {}", inputFile, outputFile);

        } catch (IOException e) {
            log.error("An error occurred during the read/write process", e);
        }
    }

    /**
     * About: Demonstrates reading and writing using RandomAccessFile.
     * Input: A file path to read from and write to, e.g., "/path/to/file.txt".
     * Output: Logs the contents read from and written to the file.
     *
     *Although writeChars(String ) is a valid method in RandomAccessFile, it is not suitable here because you want to read the contents in UTF format later.
     * writeChars will write the String is default encoding and if you try to read it as UTF, it will throw an exception while reading.
     */
    public static void demonstrateRandomAccessFile() {
        String filePath = "/path/to/file.txt";

        //  Valid modes are "r", "rw", "rws", and "rwd"
        /*
        "r": Read-only, file must exist.
        "rw": Read and write, creates file if it doesn't exist.
        "rws": Read, write with synchronous updates to both data and metadata.
        "rwd": Read, write with synchronous updates to data only, metadata updates are not guaranteed to be immediate.
         */
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "rw")) {
            // Write to the file
            randomAccessFile.writeUTF("This is a line written by RandomAccessFile.");

            // Move the file pointer to the beginning
            randomAccessFile.seek(0);

            // Read from the file
            String content = randomAccessFile.readUTF();
            log.info("RandomAccessFile content: {}", content);

            // Move the file pointer to the end
            randomAccessFile.seek(randomAccessFile.length());

            // Write more data
            randomAccessFile.writeUTF(" Another line added to the end.");
            log.info("Additional content written to file.");

            // Read the entire file from the beginning
            randomAccessFile.seek(0);
            log.info("Complete content after additional write: {}", randomAccessFile.readUTF());

        } catch (IOException e) {
            log.error("An error occurred with RandomAccessFile", e);
        }
    }



    public static void main(String[] args) {
        readFileWithFileInputStream();
        writeFileWithFileOutputStream();
        readFromByteArray();
        serializeAndDeserializeObject();
        writeWithPrintStream();
        readWithFilterInputStream();
        demonstrateMarkAndSkip();
        // Reader examples
        readFileWithFileReader();
        readFileWithBufferedReader();
        readInputStreamWithInputStreamReader();
        readFromCharArray();
        readAndWriteBytes();
        demonstrateFormattedOutput();
        readAndWriteOneByteAtATime();
        demonstrateRandomAccessFile();
    }

    static class ExampleObject implements Serializable {
        private static final long serialVersionUID = 1L;

        private String name;
        private int value;

        public ExampleObject(String name, int value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public String toString() {
            return "ExampleObject{name='" + name + "', value=" + value + '}';
        }
    }
}

package ca.siva.chapter09;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.FileTime;

@Slf4j
public class FilesWithAttributesExample {

    /**
     * About: Reads the basic file attributes (size, creation time, last modified time, etc.) of a file.
     * Input: A Path object representing the file, e.g., Paths.get("/path/to/file.txt").
     * Output: Logs the basic file attributes.
     */
    public static void readBasicFileAttributes() throws IOException {
        Path path = Paths.get("/path/to/file.txt");

        // Read the basic file attributes
        BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);

        log.info("File size: {} bytes", attrs.size());
        log.info("Creation time: {}", attrs.creationTime());
        log.info("Last modified time: {}", attrs.lastModifiedTime());
        log.info("Is directory: {}", attrs.isDirectory());
        log.info("Is regular file: {}", attrs.isRegularFile());
    }

    /**
     * About: Modifies the file's last modified time using the BasicFileAttributeView.
     * Input: A Path object representing the file, e.g., Paths.get("/path/to/file.txt").
     * Output: Logs the updated last modified time.
     */
    public static void modifyLastModifiedTime() throws IOException {
        Path path = Paths.get("/path/to/file.txt");

        // Get the BasicFileAttributeView for modifying attributes
        BasicFileAttributeView view = Files.getFileAttributeView(path, BasicFileAttributeView.class);

        // Set the new last modified time
        FileTime newLastModifiedTime = FileTime.fromMillis(System.currentTimeMillis());
        view.setTimes(newLastModifiedTime, null, null);

        // Read back the updated attributes
        BasicFileAttributes attrs = view.readAttributes();
        log.info("Updated last modified time: {}", attrs.lastModifiedTime());
    }

    /**
     * About: Modifies the file's last access time and creation time using the BasicFileAttributeView.
     * Input: A Path object representing the file, e.g., Paths.get("/path/to/file.txt").
     * Output: Logs the updated last access time and creation time.
     */
    public static void modifyAccessAndCreationTime() throws IOException {
        Path path = Paths.get("/path/to/file.txt");

        // Get the BasicFileAttributeView for modifying attributes
        BasicFileAttributeView view = Files.getFileAttributeView(path, BasicFileAttributeView.class);

        // Set the new access and creation times
        FileTime newAccessTime = FileTime.fromMillis(System.currentTimeMillis());
        FileTime newCreationTime = FileTime.fromMillis(System.currentTimeMillis() - 100000); // Example: Set creation time to earlier

        view.setTimes(null, newAccessTime, newCreationTime);

        // Read back the updated attributes
        BasicFileAttributes attrs = view.readAttributes();
        log.info("Updated last access time: {}", attrs.lastAccessTime());
        log.info("Updated creation time: {}", attrs.creationTime());
    }

    /**
     * About: Demonstrates reading and modifying basic file attributes using the BasicFileAttributeView.
     * Input: None.
     * Output: Logs the attributes and their modifications.
     */
    public static void main(String[] args) {
        try {
            // Read and log basic file attributes
            readBasicFileAttributes();

            // Modify and log the last modified time
            modifyLastModifiedTime();

            // Modify and log the last access and creation time
            modifyAccessAndCreationTime();

        } catch (IOException e) {
            log.error("An I/O error occurred", e);
        }
    }
}

package ca.siva.chapter09;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
NOTES:
1) Checking isSameFile() or doing normalize() and compare with equals on a path object is same
2) isSameFile() throws NoSuchFileException when the path pointing file does not exist.
3) In p1.relativize(p2) method, both p1 and p2 should be either relative or absolute paths; should be same to work fine.
Else it throws runTimeException.
 */
@Slf4j
public class PathsExample {

    /**
     * About: Creates a Path instance from a String path.
     * Input: A string representing a file path, e.g., "/path/to/file.txt".
     * Output: A Path instance representing the same path.
     */
    public static void getPathFromString() {
        Path path = Paths.get("/path/to/file.txt");
        log.info("Path: {}", path);
    }

    /**
     * About: Creates a Path instance from a sequence of strings.
     * Input: Multiple strings representing different parts of a path, e.g., "/path", "to", "file.txt".
     * Output: A Path instance representing the combined path.
     */
    public static void getPathFromMultipleStrings() {
        Path path = Paths.get("/path", "to", "file.txt");
        log.info("Path: {}", path);
    }

    /**
     * About: Creates a Path instance from a URI.
     * Input: A URI string representing a file path, e.g., "file:///path/to/file.txt".
     * Output: A Path instance representing the same path.
     */
    public static void getPathFromURI() {
        URI uri = URI.create("file:///path/to/file.txt");
        Path path = Paths.get(uri);
        log.info("Path: {}", path);
    }

    /**
     * About: Converts a File instance to a Path instance.
     * Input: A File instance, e.g., new File("/path/to/file.txt").
     * Output: A Path instance representing the same file path.
     */
    public static void getPathFromFile() {
        File file = new File("/path/to/file.txt");
        Path path = file.toPath();
        log.info("Path: {}", path);
    }

    /**
     * About: Converts a relative Path instance to an absolute path.
     * Input: A relative Path, e.g., "relative/path/to/file.txt".
     * Output: The absolute Path instance representing the full file path.
     */
    public static void getAbsolutePath() {
        Path absolutePath = Paths.get("relative/path/to/file.txt").toAbsolutePath();
        log.info("Absolute Path: {}", absolutePath);
    }

    /**
     * About: Normalizes a Path, removing redundant elements (e.g., "." and "..").
     * Input: A Path with redundant elements, e.g., "/path/./to/../file.txt".
     * Output: The normalized Path, e.g., "/path/file.txt".
     */
    public static void getNormalizedPath() {
        Path normalizedPath = Paths.get("/path/./to/../file.txt").normalize();
        log.info("Normalized Path: {}", normalizedPath);
    }

    /**
     * About: Gets the parent of a Path.
     * Input: A Path, e.g., "/path/to/file.txt".
     * Output: The parent Path, e.g., "/path/to".
     */
    public static void getParentPath() {
        Path parentPath = Paths.get("/path/to/file.txt").getParent();
        log.info("Parent Path: {}", parentPath);
    }

    /**
     * About: Gets the file name of a Path.
     * Input: A Path, e.g., "/path/to/file.txt".
     * Output: The file name as a Path, e.g., "file.txt".
     */
    public static void getFileName() {
        Path fileName = Paths.get("/path/to/file.txt").getFileName();
        log.info("File Name: {}", fileName);
    }

    /**
     * About: Resolves a path against another base path.
     * If absolute path is given as input, the output would be the same as input argument.
     * Input: A base Path and a relative Path, e.g., "/path/to" and "file.txt".
     * Output: The resolved Path, e.g., "/path/to/file.txt".
     */
    public static void resolvePath() {
        Path resolvedPath = Paths.get("/path/to").resolve("file.txt");
        log.info("Resolved Path: {}", resolvedPath);
    }

    /**
     * About: Relativizes a Path with respect to another Path.
     * Input: Two Paths, e.g., "/path/to/file.txt" and "/path/to/anotherfile.txt".
     * Output: The relative Path between them, e.g., "../anotherfile.txt".
     */
    public static void relativizePath() {
        Path relativePath = Paths.get("/path/to/file.txt").relativize(Paths.get("/path/to/anotherfile.txt"));
        log.info("Relative Path: {}", relativePath);
    }

    /**
     * About: Checks if a Path is absolute.
     * Input: A Path, e.g., "/path/to/file.txt".
     * Output: A boolean indicating whether the Path is absolute.
     */
    public static void isAbsolutePath() {
        boolean isAbsolute = Paths.get("/path/to/file.txt").isAbsolute();
        log.info("Is Absolute: {}", isAbsolute);
    }

    /**
     * About: Retrieves the root component of a Path.
     * Input: A Path, e.g., "/path/to/file.txt".
     * Output: The root component of the Path, e.g., "/".
     */
    public static void getRootPath() {
        Path rootPath = Paths.get("/path/to/file.txt").getRoot();
        log.info("Root Path: {}", rootPath);
    }

    /**
     * About: Iterates over the elements of a Path.
     * Input: A Path, e.g., "/path/to/file.txt".
     * Output: Each element of the Path, e.g., "path", "to", "file.txt".
     */
    public static void iterateOverPathElements() {
        Path path = Paths.get("/path/to/file.txt");
        path.forEach(element -> log.info("Path Element: {}", element));
    }

    /**
     * About: Compares two Paths for equality.
     * Input: Two Paths, e.g., "/path/to/file.txt" and "/path/to/file.txt".
     * Output: A boolean indicating whether the Paths are equal.
     */
    public static void comparePaths() {
        boolean areEqual = Paths.get("/path/to/file.txt").equals(Paths.get("/path/to/file.txt"));
        log.info("Paths are equal: {}", areEqual);
    }

    /**
     * About: Resolves a sibling path relative to the current path.
     * Input: A Path and a sibling file name, e.g., "/path/to/file.txt" and "anotherfile.txt".
     * Output: The resolved sibling Path, e.g., "/path/to/anotherfile.txt".
     */
    public static void resolveSiblingPath() {
        Path siblingPath = Paths.get("/path/to/file.txt").resolveSibling("anotherfile.txt");
        log.info("Sibling Path: {}", siblingPath);
    }

    /**
     * About: Converts a Path to a URI.
     * Input: A Path, e.g., "/path/to/file.txt".
     * Output: The URI representing the Path, e.g., "file:///path/to/file.txt".
     */
    public static void convertPathToURI() {
        URI uri = Paths.get("/path/to/file.txt").toUri();
        log.info("URI: {}", uri);
    }

    /**
     * About: Checks if a Path starts with a specific prefix.
     * Input: A Path and a prefix, e.g., "/path/to/file.txt" and "/path".
     * Output: A boolean indicating whether the Path starts with the given prefix.
     */
    public static void pathStartsWith() {
        boolean startsWith = Paths.get("/path/to/file.txt").startsWith("/path");
        log.info("Starts with '/path': {}", startsWith);
    }

    /**
     * About: Checks if a Path ends with a specific suffix.
     * Input: A Path and a suffix, e.g., "/path/to/file.txt" and "file.txt".
     * Output: A boolean indicating whether the Path ends with the given suffix.
     */
    public static void pathEndsWith() {
        boolean endsWith = Paths.get("/path/to/file.txt").endsWith("file.txt");
        log.info("Ends with 'file.txt': {}", endsWith);
    }

    /**
     * About: Converts a Path to its string representation.
     * Input: A Path, e.g., "/path/to/file.txt".
     * Output: The string representation of the Path, e.g., "/path/to/file.txt".
     */
    public static void convertPathToString() {
        String pathString = Paths.get("/path/to/file.txt").toString();
        log.info("Path as String: {}", pathString);
    }

    /**
     * About: Retrieves the number of elements in a Path.
     * Input: A Path, e.g., "/path/to/file.txt".
     * Output: The number of elements in the Path, e.g., 3 ("path", "to", "file.txt").
     */
    public static void getPathElementCount() {
        int elementCount = Paths.get("/path/to/file.txt").getNameCount();
        log.info("Number of elements in Path: {}", elementCount);
    }

    /**
     * About: Retrieves a subpath from the Path.
     * Input: A Path, e.g., "/path/to/file.txt", and start/end indices, e.g., (0, 2).
     * Output: The subpath representing a portion of the Path, e.g., "path/to".
     */
    public static void getSubpath() {
        Path subPath = Paths.get("/path/to/file.txt").subpath(0, 2); // Gets "path/to"
        log.info("Subpath: {}", subPath);
    }

    /**
     * About: Resolves a path to its real, absolute path by resolving symbolic links and normalizing the path.
     * Input: A Path, e.g., "/path/to/file.txt".
     * Output: The real path, with symbolic links and redundant elements resolved.
     */
    public static void getRealPath() {
        try {
            Path realPath = Paths.get("/path/to/file.txt").toRealPath();
            log.info("Real Path: {}", realPath);
        } catch (IOException e) {
            log.error("An error occurred while resolving the real path", e);
        }
    }

    /**
     * About: Demonstrates usage of getName(int index) to retrieve specific elements of a Path.
     * Input: A Path, e.g., "/path/to/file.txt".
     * Output: Logs the elements of the Path at specified indices.
     */
    public static void getPathNameByIndex() {
        Path path = Paths.get("/path/to/file.txt");

        // Ensure the path has enough elements before accessing them by index
        int nameCount = path.getNameCount();

        log.info("Total elements in Path: {}", nameCount);

        for (int i = 0; i < nameCount; i++) {
            Path nameElement = path.getName(i);
            log.info("Element at index {}: {}", i, nameElement);
        }

        if (nameCount > 1) {
            Path firstElement = path.getName(0);
            Path secondElement = path.getName(1);
            log.info("First element: {}", firstElement);
            log.info("Second element: {}", secondElement);
        }
    }

    public static void main(String[] args) {
        getPathFromString();
        getPathFromMultipleStrings();
        getPathFromURI();
        getPathFromFile();
        getAbsolutePath();
        getNormalizedPath();
        getParentPath();
        getFileName();
        resolvePath();
        relativizePath();
        isAbsolutePath();
        getRootPath();
        iterateOverPathElements();
        comparePaths();
        resolveSiblingPath();
        convertPathToURI();
        pathStartsWith();
        pathEndsWith();
        convertPathToString();
        getPathElementCount();
        getSubpath();
        getRealPath();
        getPathNameByIndex();
    }
}

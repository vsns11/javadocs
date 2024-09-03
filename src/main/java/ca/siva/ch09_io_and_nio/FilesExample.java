package ca.siva.ch09_io_and_nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.stream.Stream;

/*
NOTE:
 1) toAbsolutePath(): Converts a relative path to an absolute path but does not resolve symbolic links or canonicalize the path. It does not interact with FileSystem.
toRealPath(): Unlike toAbsolutePath(), this interacts with filesystem. And converts the path to its canonical form, resolving symbolic links and removing any redundant elements. It accesses the filesystem and can throw an IOException.
2) Files.walk(xxx) method's default depth is Integer.MAX_VALUE if not provided.
3) Files.copy method will copy the file test1.txt into test2.txt. If test2.txt doesn't exist, it will be created. However,
Files.isSameFile method doesn't check the contents of the file. It is meant to check if the two path objects resolve to the same file or not. In this case, they are not, and so, it will return false.
4) Files.list(Path) returns Stream<Path> containing all the paths (files and subdirectories) of current directory. It is not recursive.
5) For recursive access, use overloaded Files.walk() methods.
 */
@Slf4j
public class FilesExample {

    /**
     * About: Checks if a file or directory exists at the given path.
     * Input: A Path object representing a file or directory, e.g., Paths.get("/path/to/file.txt").
     * Output: A boolean indicating whether the file or directory exists.
     */
    public static void checkIfExists() throws IOException {
        Path path = Paths.get("/path/to/file.txt");
        boolean exists = Files.exists(path);
        log.info("File exists: {}", exists);
    }

    /**
     * About: Creates a new directory at the specified path.
     * Input: A Path object representing the directory to be created, e.g., Paths.get("/path/to/newDir").
     * Output: The Path object of the created directory.
     */
    public static void createDirectory() throws IOException {
        Path path = Paths.get("/path/to/newDir");
        Path newDir = Files.createDirectory(path);
        log.info("Directory created: {}", newDir);
    }

    /**
     * About: Creates multiple directories at the specified path.
     * Input: A Path object representing the directory tree to be created, e.g., Paths.get("/path/to/newDir/subDir").
     * Output: The Path object of the created directories.
     */
    public static void createDirectories() throws IOException {
        Path path = Paths.get("/path/to/newDir/subDir");
        Path newDirs = Files.createDirectories(path);
        log.info("Directories created: {}", newDirs);
    }

    /**
     * About: Reads the contents of a file as a list of strings.
     * Input: A Path object representing the file, e.g., Paths.get("/path/to/file.txt").
     * Output: A List of strings representing the lines in the file.
     */
    public static void readFileAsList() throws IOException {
        Path path = Paths.get("/path/to/file.txt");
        List<String> lines = Files.readAllLines(path);
        log.info("File contents: {}", lines);
    }

    /**
     * About: Writes a list of strings to a file.
     * Input: A Path object representing the file, and a List of strings to write, e.g., Paths.get("/path/to/file.txt").
     * Output: The Path object representing the written file.
     */
    public static void writeFile() throws IOException {
        Path path = Paths.get("/path/to/file.txt");
        List<String> lines = List.of("First line", "Second line", "Third line");
        Path writtenFile = Files.write(path, lines);
        log.info("File written: {}", writtenFile);
    }

    /**
     * About: Copies a file from one location to another.
     * Input: Two Path objects representing the source and target locations, e.g., Paths.get("/path/to/source.txt") and Paths.get("/path/to/target.txt").
     * Output: The Path object representing the copied file.
     */
    public static void copyFile() throws IOException {
        Path sourcePath = Paths.get("/path/to/source.txt");
        Path targetPath = Paths.get("/path/to/target.txt");
        Path copiedFile = Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        log.info("File copied to: {}", copiedFile);
    }

    /**
     * About: Moves a file from one location to another.
     * Input: Two Path objects representing the source and target locations, e.g., Paths.get("/path/to/source.txt") and Paths.get("/path/to/target.txt").
     * Output: The Path object representing the moved file.
     */
    public static void moveFile() throws IOException {
        Path sourcePath = Paths.get("/path/to/source.txt");
        Path targetPath = Paths.get("/path/to/target.txt");
        Path movedFile = Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        log.info("File moved to: {}", movedFile);
    }

    /**
     * About: Deletes a file or directory at the given path.
     * Input: A Path object representing the file or directory to delete, e.g., Paths.get("/path/to/file.txt").
     * Output: None (void). Logs the deletion action.
     */
    public static void deleteFile() throws IOException {
        Path path = Paths.get("/path/to/file.txt");
        Files.delete(path);
        log.info("File deleted: {}", path);
    }

    /**
     * About: Walks through a file tree starting from a given root directory.
     * Input: A Path object representing the root directory, e.g., Paths.get("/path/to/rootDir").
     * Output: Logs each file and directory visited.
     */
    public static void walkFileTree() throws IOException {
        Path startPath = Paths.get("/path/to/rootDir");
        Files.walkFileTree(startPath, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                log.info("Visited file: {}", file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) {
                log.error("Failed to visit file: {}", file, exc);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                log.info("Visited directory: {}", dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * About: Lists the files in a directory.
     * Input: A Path object representing the directory, e.g., Paths.get("/path/to/directory").
     * Output: Logs each file and directory in the given directory.
     */
    public static void listDirectoryContents() throws IOException {
        Path dirPath = Paths.get("/path/to/directory");
        try (Stream<Path> paths = Files.list(dirPath)) {
            paths.forEach(path -> log.info("Found: {}", path));
        }
    }

    /**
     * About: Checks the size of a file.
     * Input: A Path object representing the file, e.g., Paths.get("/path/to/file.txt").
     * Output: The size of the file in bytes.
     */
    public static void checkFileSize() throws IOException {
        Path path = Paths.get("/path/to/file.txt");
        long fileSize = Files.size(path);
        log.info("File size: {} bytes", fileSize);
    }

    /**
     * About: Creates a temporary file.
     * Input: Two strings representing the prefix and suffix of the temporary file name, e.g., "temp" and ".txt".
     * Output: The Path object representing the created temporary file.
     */
    public static void createTempFile() throws IOException {
        Path tempFile = Files.createTempFile("temp", ".txt");
        log.info("Temporary file created: {}", tempFile);
    }

    /**
     * About: Walks through a file tree using Files.walk() and processes each file and directory as a stream.
     * Input: A Path object representing the root directory, e.g., Paths.get("/path/to/rootDir"), and an integer representing the depth of traversal.
     * Output: Logs each file and directory visited up to the specified depth.
     */
    public static void walkFileTreeWithStreamAndDepth() throws IOException {
        Path startPath = Paths.get("/path/to/rootDir");
        int maxDepth = 2;  // Specify the maximum depth of the traversal
        try (Stream<Path> paths = Files.walk(startPath, maxDepth)) {
            paths.forEach(path -> log.info("Visited (depth <= {}): {}", maxDepth, path));
        }
    }

    /**
     * About: Walks through a file tree using Files.walk() and converts paths to their absolute paths.
     * Input: A Path object representing the root directory, e.g., Paths.get("/path/to/rootDir").
     * Output: Logs each file and directory visited, converted to its absolute path.
     */
    public static void walkFileTreeAndConvertToAbsolutePath() throws IOException {
        Path startPath = Paths.get("/path/to/rootDir");
        try (Stream<Path> paths = Files.walk(startPath)) {
            paths.map(Path::toAbsolutePath)
                    .forEach(path -> log.info("Absolute Path: {}", path));
        }
    }

    /**
     * About: Walks through a file tree using Files.walk() and converts paths to their real paths (resolving symbolic links).
     * Input: A Path object representing the root directory, e.g., Paths.get("/path/to/rootDir").
     * Output: Logs each file and directory visited, converted to its real path.
     */
    public static void walkFileTreeAndConvertToRealPath() throws IOException {
        Path startPath = Paths.get("/path/to/rootDir");
        try (Stream<Path> paths = Files.walk(startPath)) {
            paths.map(path -> {
                try {
                    return path.toRealPath();
                } catch (IOException e) {
                    log.error("Failed to convert to real path: {}", path, e);
                    return path;
                }
            }).forEach(path -> log.info("Real Path: {}", path));
        }
    }

    /**
     * About: Finds files in a directory tree based on a filter using Files.find().
     * Input: A Path object representing the root directory, e.g., Paths.get("/path/to/rootDir"), and a filter condition.
     * Output: Logs each file that matches the filter.
     */
    public static void findFilesByFilter() throws IOException {
        Path startPath = Paths.get("/path/to/rootDir");
        int maxDepth = 3; // Define how deep to search

        try (Stream<Path> foundFiles = Files.find(startPath, maxDepth, (path, attrs) ->
                attrs.isRegularFile() && path.toString().endsWith(".txt"))) {
            foundFiles.forEach(path -> log.info("Found file: {}", path));
        }
    }

    public static void main(String[] args) {
        try {
            checkIfExists();
            createDirectory();
            createDirectories();
            readFileAsList();
            writeFile();
            copyFile();
            moveFile();
            deleteFile();
            walkFileTree();
            walkFileTreeWithStreamAndDepth();
            listDirectoryContents();
            checkFileSize();
            createTempFile();
            walkFileTreeAndConvertToAbsolutePath();
            walkFileTreeAndConvertToRealPath();
            findFilesByFilter();
        } catch (IOException e) {
            log.error("An I/O error occurred", e);
        }
    }
}

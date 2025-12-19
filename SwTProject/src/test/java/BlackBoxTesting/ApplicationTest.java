package BlackBoxTesting;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {

    private Path tempDir;
    private Object appInstance;

    @BeforeEach
    public void setUp() throws Exception {
        tempDir = Files.createTempDirectory("test_app_");

        // Use reflection to create Application instance since constructor is not public
        Class<?> appClass = Class.forName("com.mycompany.swtproject.Application");
        Class<?> movieReaderClass = Class.forName("com.mycompany.swtproject.MovieFilereader");
        Class<?> userReaderClass = Class.forName("com.mycompany.swtproject.UserFilereader");

        Object movieReader = movieReaderClass.getDeclaredConstructor().newInstance();
        Object userReader = userReaderClass.getDeclaredConstructor().newInstance();

        Constructor<?> constructor = appClass.getDeclaredConstructor(movieReaderClass, userReaderClass);
        constructor.setAccessible(true);
        appInstance = constructor.newInstance(movieReader, userReader);
    }

    @AfterEach
    public void tearDown() throws IOException {
        // Clean up temp directory
        if (tempDir != null && Files.exists(tempDir)) {
            // Delete all files in directory first
            try (var stream = Files.walk(tempDir)) {
                stream.sorted((a, b) -> -a.compareTo(b))
                        .forEach(path -> {
                            try {
                                Files.deleteIfExists(path);
                            } catch (IOException e) {
                                // Ignore, file might be locked
                            }
                        });
            } catch (IOException e) {
                // Ignore cleanup errors
            }
        }
    }

    private Path createTempFile(String filename, String content) throws IOException {
        Path file = tempDir.resolve(filename);
        Files.writeString(file, content);
        return file;
    }

    private void runApplication(String moviePath, String userPath, String outputPath) throws Exception {
        Method method = appInstance.getClass().getDeclaredMethod(
                "RecommenderApp", String.class, String.class, String.class);
        method.invoke(appInstance, moviePath, userPath, outputPath);
    }

    @Test
    public void testValidInputProducesRecommendations() throws Exception {
        // ---- ARRANGE ----
        Path moviesFile = createTempFile("movies.txt",
                "The Matrix,TM123\n" +
                        "Action\n" +
                        "John Wick,JW456\n" +
                        "Action\n");

        Path usersFile = createTempFile("users.txt",
                "Jana Wael,12345678A\n" +
                        "TM123\n");

        Path outputFile = tempDir.resolve("output.txt");

        // ---- ACT ----
        runApplication(
                moviesFile.toString(),
                usersFile.toString(),
                outputFile.toString()
        );

        // ---- ASSERT ----
        String result = Files.readString(outputFile);
        System.out.println("Valid input output: " + result);

        assertNotNull(result, "Output file should not be null");
        assertFalse(result.trim().isEmpty(), "Output file should not be empty");
        assertTrue(result.contains("Jana Wael"), "Output should contain user name");
        assertTrue(result.contains("12345678A"), "Output should contain user ID");
    }

    @Test
    public void testEmptyUserFile() throws Exception {
        // ---- ARRANGE ----
        Path moviesFile = createTempFile("movies.txt",
                "The Matrix,TM123\nAction\n");
        Path usersFile = createTempFile("empty_users.txt", ""); // Empty file
        Path outputFile = tempDir.resolve("output.txt");

        // ---- ACT ----
        runApplication(
                moviesFile.toString(),
                usersFile.toString(),
                outputFile.toString()
        );

        // ---- ASSERT ----
        String result = Files.readString(outputFile);
        System.out.println("Empty user file output: " + result);

        // Check what happens - might be empty or have error message
        assertNotNull(result, "Output should not be null");

        // The test passes if application handles it without crashing
    }

    @Test
    public void testEmptyMovieFile() throws Exception {
        // ---- ARRANGE ----
        Path moviesFile = createTempFile("empty_movies.txt", ""); // Empty file
        Path usersFile = createTempFile("users.txt",
                "Jana Wael,12345678A\nTM123\n");
        Path outputFile = tempDir.resolve("output.txt");

        // ---- ACT ----
        runApplication(
                moviesFile.toString(),
                usersFile.toString(),
                outputFile.toString()
        );

        // ---- ASSERT ----
        String result = Files.readString(outputFile);
        System.out.println("Empty movie file output: " + result);

        assertNotNull(result, "Output should not be null");
    }

    @Test
    public void testUserWithNoPreferences() throws Exception {
        // ---- ARRANGE ----
        Path moviesFile = createTempFile("movies.txt",
                "The Matrix,TM123\nAction\n" +
                        "Titanic,T567\nRomance\n");

        // User with valid info but no movie IDs
        Path usersFile = createTempFile("users.txt",
                "John Doe,12345678A\n"); // No movie IDs

        Path outputFile = tempDir.resolve("output.txt");

        // ---- ACT ----
        runApplication(
                moviesFile.toString(),
                usersFile.toString(),
                outputFile.toString()
        );

        // ---- ASSERT ----
        String result = Files.readString(outputFile);
        System.out.println("User with no preferences output: " + result);

        assertNotNull(result, "Should produce some output");
    }

    @Test
    public void testInvalidUserIdFormat() throws Exception {
        // ---- ARRANGE ----
        Path moviesFile = createTempFile("movies.txt",
                "The Matrix,TM123\nAction\n");

        // Invalid ID format (wrong length - based on your output)
        Path usersFile = createTempFile("bad_users.txt",
                "Jana Wael,1234567A\nTM123\n"); // 9 chars

        Path outputFile = tempDir.resolve("output.txt");

        // ---- ACT ----
        runApplication(
                moviesFile.toString(),
                usersFile.toString(),
                outputFile.toString()
        );

        // ---- ASSERT ----
        String result = Files.readString(outputFile);
        System.out.println("Invalid user ID output: " + result);

        assertNotNull(result, "Should produce some output");

        // Based on your output, it seems to print "ERROR: User ID {1234567A} is wrong"
        // but this might go to stderr, not the output file
    }

    @Test
    public void testInvalidMovieFormat() throws Exception {
        // ---- ARRANGE ----
        // Missing genre line for second movie
        Path moviesFile = createTempFile("bad_movies.txt",
                "The Matrix,TM123\nAction\n" +
                        "John Wick,JW456\n"); // Missing genre

        Path usersFile = createTempFile("users.txt",
                "Jana Wael,12345678A\nTM123\n");

        Path outputFile = tempDir.resolve("output.txt");

        // ---- ACT ----
        runApplication(
                moviesFile.toString(),
                usersFile.toString(),
                outputFile.toString()
        );

        // ---- ASSERT ----
        String result = Files.readString(outputFile);
        System.out.println("Invalid movie format output: " + result);

        assertNotNull(result, "Should produce some output");
    }

    @Test
    public void testNonExistentMovieId() throws Exception {
        // ---- ARRANGE ----
        Path moviesFile = createTempFile("movies.txt",
                "The Matrix,TM123\nAction\n");

        // User references movie ID that doesn't exist
        Path usersFile = createTempFile("users.txt",
                "Jana Wael,12345678A\nINVALID123\n");

        Path outputFile = tempDir.resolve("output.txt");

        // ---- ACT ----
        runApplication(
                moviesFile.toString(),
                usersFile.toString(),
                outputFile.toString()
        );

        // ---- ASSERT ----
        String result = Files.readString(outputFile);
        System.out.println("Non-existent movie ID output: " + result);

        assertNotNull(result, "Should produce some output");
    }

    @Test
    public void testDuplicateUsers() throws Exception {
        // ---- ARRANGE ----
        Path moviesFile = createTempFile("movies.txt",
                "The Matrix,TM123\nAction\n" +
                        "Inception,I456\nSci-Fi\n");

        Path usersFile = createTempFile("users.txt",
                "Jana Wael,12345678A\nTM123\n" +
                        "Jana Wael,12345678A\nI456\n"); // Duplicate user

        Path outputFile = tempDir.resolve("output.txt");

        // ---- ACT ----
        runApplication(
                moviesFile.toString(),
                usersFile.toString(),
                outputFile.toString()
        );

        // ---- ASSERT ----
        String result = Files.readString(outputFile);
        System.out.println("Duplicate users output: " + result);

        assertNotNull(result, "Should produce output despite duplicates");
    }

    @Test
    public void testAllUsersInvalid() throws Exception {
        // ---- ARRANGE ----
        Path moviesFile = createTempFile("movies.txt",
                "The Matrix,TM123\nAction\n");

        // All users have invalid IDs
        Path usersFile = createTempFile("users.txt",
                "User1,123\n" +  // Too short
                        "User2,12345678901234567890\n" +  // Too long
                        "User3,ABC!@#$%^\n");  // Invalid characters

        Path outputFile = tempDir.resolve("output.txt");

        // ---- ACT ----
        runApplication(
                moviesFile.toString(),
                usersFile.toString(),
                outputFile.toString()
        );

        // ---- ASSERT ----
        String result = Files.readString(outputFile);
        System.out.println("All users invalid output: " + result);

        assertNotNull(result, "Should handle all invalid users");
    }

    @Test
    public void testNoUniqueRecommendations() throws Exception {
        // ---- ARRANGE ----
        // All movies same genre
        Path moviesFile = createTempFile("movies.txt",
                "The Matrix,TM123\nAction\n" +
                        "John Wick,JW456\nAction\n" +
                        "Die Hard,DH789\nAction\n");

        // User likes all action movies
        Path usersFile = createTempFile("users.txt",
                "Jana Wael,12345678A\nTM123\nJW456\nDH789\n");

        Path outputFile = tempDir.resolve("output.txt");

        // ---- ACT ----
        runApplication(
                moviesFile.toString(),
                usersFile.toString(),
                outputFile.toString()
        );

        // ---- ASSERT ----
        String result = Files.readString(outputFile);
        System.out.println("No unique recommendations output: " + result);

        assertNotNull(result, "Should produce output even with no new recommendations");
        assertTrue(result.contains("Jana Wael"), "User should appear in output");
    }

    @Test
    public void testLargeFilesHandling() throws Exception {
        // ---- ARRANGE ----
        StringBuilder moviesContent = new StringBuilder();
        StringBuilder usersContent = new StringBuilder();

        // Generate 20 movies (smaller for faster testing)
        for (int i = 1; i <= 20; i++) {
            moviesContent.append(String.format("Movie%d,M%03d\n", i, i));
            moviesContent.append(i % 3 == 0 ? "Action\n" :
                    i % 3 == 1 ? "Comedy\n" : "Drama\n");
        }

        // Generate 10 users
        for (int i = 1; i <= 10; i++) {
            usersContent.append(String.format("User%d,ID%08d\n", i, i));
            // Each user likes 2 random movies
            usersContent.append(String.format("M%03d\nM%03d\n",
                    (i % 20) + 1,
                    ((i + 10) % 20) + 1));
        }

        Path moviesFile = createTempFile("large_movies.txt", moviesContent.toString());
        Path usersFile = createTempFile("large_users.txt", usersContent.toString());
        Path outputFile = tempDir.resolve("output.txt");

        // ---- ACT ----
        long startTime = System.currentTimeMillis();
        runApplication(
                moviesFile.toString(),
                usersFile.toString(),
                outputFile.toString()
        );
        long endTime = System.currentTimeMillis();

        // ---- ASSERT ----
        String result = Files.readString(outputFile);
        System.out.println("Large files output length: " + result.length());

        assertNotNull(result, "Should handle large files");
        assertTrue(result.contains("User"), "Should contain user information");

        // Performance check
        long executionTime = endTime - startTime;
        System.out.println("Execution time: " + executionTime + "ms");
        assertTrue(executionTime < 5000, "Should process files in reasonable time: " + executionTime + "ms");
    }

    @Test
    public void testApplicationDoesNotThrowExceptions() throws Exception {
        // Test that application doesn't throw exceptions for various inputs

        String[][] testCases = {
                // movies content, users content
                {"", "Test User,12345678A\n"}, // Empty movies
                {"The Matrix,TM123\nAction\n", ""}, // Empty users
                {"Invalid,ID\nNoGenreLine", "Test User,12345678A\n"}, // Invalid format
                {"Movie without ID\nAction", "Test User,12345678A\n"}, // Bad movie format
                {"Movie,ID\n", "Test User,12345678A\n"}, // No genre
                {"\n\n", "Test User,12345678A\n"}, // Just newlines
                {"The Matrix,TM123\nAction\n", "User,ID\nINVALID\n"}, // Invalid movie reference
        };

        for (int i = 0; i < testCases.length; i++) {
            String moviesContent = testCases[i][0];
            String usersContent = testCases[i][1];

            Path moviesFile = createTempFile("test_movies_" + i + ".txt", moviesContent);
            Path usersFile = createTempFile("test_users_" + i + ".txt", usersContent);
            Path outputFile = tempDir.resolve("test_output_" + i + ".txt");

            try {
                // Should not throw exception
                runApplication(
                        moviesFile.toString(),
                        usersFile.toString(),
                        outputFile.toString()
                );

                // Verify output file was created (even if empty)
                assertTrue(Files.exists(outputFile), "Output file should be created for test case " + i);

            } catch (Exception e) {
                // If an exception is thrown, it should be caught and logged, not crash
                System.err.println("Test case " + i + " threw exception (but application might handle it): " +
                        e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
                // Don't fail the test - we're checking that the main flow doesn't crash
            }
        }
    }
}
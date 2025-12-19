package DataFlowTesting;

import com.mycompany.swtproject.OutputFileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

public class OutputFileWriter_DFT {
    
    private String testFilePath = "TextFile_integrationTest/dfttest_output.txt";

    @BeforeEach
    public void setUp() {
        // Clean the file before each test
        try {
            Files.deleteIfExists(Paths.get(testFilePath));
        } catch (Exception e) {
            // File doesn't exist yet, that's fine
        }
    }

    @AfterEach
    public void tearDown() {
        // Clean up after each test
        try {
            Files.deleteIfExists(Paths.get(testFilePath));
        } catch (Exception e) {
            // Cleanup error
        }
    }

    @Test
    public void DF01_writeFirstErrorSingleError() {
        OutputFileWriter writer = new OutputFileWriter(testFilePath);
        ArrayList<String> errors = new ArrayList<>();
        errors.add("ERROR: Invalid Movie Title");

        writer.WriteFirstError(errors);

        assertTrue(Files.exists(Paths.get(testFilePath)));
        try (BufferedReader reader = new BufferedReader(new FileReader(testFilePath))) {
            String line = reader.readLine();
            assertEquals("ERROR: Invalid Movie Title", line);
        } catch (Exception e) {
            fail("Error reading file: " + e.getMessage());
        }
    }

    @Test
    public void DF02_writeRecommendationsSingleUser() {
        OutputFileWriter writer = new OutputFileWriter(testFilePath);
        ArrayList<String> results = new ArrayList<>();
        results.add("John Doe,123456789");
        results.add("Movie1,Movie2,Movie3");

        writer.WriteRecommendations(results);

        assertTrue(Files.exists(Paths.get(testFilePath)));
        try (BufferedReader reader = new BufferedReader(new FileReader(testFilePath))) {
            String line1 = reader.readLine();
            String line2 = reader.readLine();
            assertEquals("John Doe,123456789", line1);
            assertEquals("Movie1,Movie2,Movie3", line2);
        } catch (Exception e) {
            fail("Error reading file: " + e.getMessage());
        }
    }

    @Test
    public void DF03_writeRecommendationsMultipleUsers() {
        OutputFileWriter writer = new OutputFileWriter(testFilePath);
        ArrayList<String> results = new ArrayList<>();
        results.add("John Doe,123456789");
        results.add("Movie1,Movie2");
        results.add("Jane Smith,234567890");
        results.add("Movie3,Movie4");

        writer.WriteRecommendations(results);

        assertTrue(Files.exists(Paths.get(testFilePath)));
        try (BufferedReader reader = new BufferedReader(new FileReader(testFilePath))) {
            String line1 = reader.readLine();
            assertEquals("John Doe,123456789", line1);
            String line2 = reader.readLine();
            assertEquals("Movie1,Movie2", line2);
        } catch (Exception e) {
            fail("Error reading file: " + e.getMessage());
        }
    }

    @Test
    public void DF04_writeRecommendationsEmptyList() {
        OutputFileWriter writer = new OutputFileWriter(testFilePath);
        ArrayList<String> results = new ArrayList<>();

        writer.WriteRecommendations(results);

        assertTrue(Files.exists(Paths.get(testFilePath)));
        try (BufferedReader reader = new BufferedReader(new FileReader(testFilePath))) {
            String line = reader.readLine();
            assertNull(line);
        } catch (Exception e) {
            fail("Error reading file: " + e.getMessage());
        }
    }

    @Test
    public void DF05_cleanFileBeforeWrite() {
        OutputFileWriter writer = new OutputFileWriter(testFilePath);
        
        // First write
        ArrayList<String> results1 = new ArrayList<>();
        results1.add("First Error");
        writer.WriteFirstError(results1);

        // Second write should clean the file first
        ArrayList<String> results2 = new ArrayList<>();
        results2.add("Second Error");
        writer.WriteFirstError(results2);

        try (BufferedReader reader = new BufferedReader(new FileReader(testFilePath))) {
            String line = reader.readLine();
            assertEquals("Second Error", line);
            String nextLine = reader.readLine();
            assertNull(nextLine);
        } catch (Exception e) {
            fail("Error reading file: " + e.getMessage());
        }
    }

    @Test
    public void DF06_setOutputPath() {
        String newPath = "TextFile_integrationTest/dfttest_output2.txt";
        try {
            Files.deleteIfExists(Paths.get(newPath));
        } catch (Exception e) {
            // File doesn't exist yet
        }

        OutputFileWriter writer = new OutputFileWriter();
        writer.setOutputPath(newPath);

        ArrayList<String> results = new ArrayList<>();
        results.add("Test Error");
        writer.WriteFirstError(results);

        assertTrue(Files.exists(Paths.get(newPath)));
        
        try {
            Files.deleteIfExists(Paths.get(newPath));
        } catch (Exception e) {
            // Cleanup
        }
    }

    @Test
    public void DF07_writeRecommendationsWithBlankLines() {
        OutputFileWriter writer = new OutputFileWriter(testFilePath);
        ArrayList<String> results = new ArrayList<>();
        results.add("User1,ID1");
        results.add("Movie1,Movie2");
        results.add("User2,ID2");
        results.add("Movie3,Movie4");

        writer.WriteRecommendations(results);

        assertTrue(Files.exists(Paths.get(testFilePath)));
        try (BufferedReader reader = new BufferedReader(new FileReader(testFilePath))) {
            assertEquals("User1,ID1", reader.readLine());
            assertEquals("Movie1,Movie2", reader.readLine());
            // Should have blank line
            assertEquals("", reader.readLine());
            assertEquals("User2,ID2", reader.readLine());
        } catch (Exception e) {
            fail("Error reading file: " + e.getMessage());
        }
    }

    @Test
    public void DF08_writeErrorWithSpecialCharacters() {
        OutputFileWriter writer = new OutputFileWriter(testFilePath);
        ArrayList<String> errors = new ArrayList<>();
        errors.add("ERROR: Invalid {Character} @ #");

        writer.WriteFirstError(errors);

        assertTrue(Files.exists(Paths.get(testFilePath)));
        try (BufferedReader reader = new BufferedReader(new FileReader(testFilePath))) {
            String line = reader.readLine();
            assertEquals("ERROR: Invalid {Character} @ #", line);
        } catch (Exception e) {
            fail("Error reading file: " + e.getMessage());
        }
    }

    @Test
    public void DF09_writeRecommendationsWithCommas() {
        OutputFileWriter writer = new OutputFileWriter(testFilePath);
        ArrayList<String> results = new ArrayList<>();
        results.add("John Doe,123456789");
        results.add("Movie1,Movie2,Movie3,Movie4,Movie5");

        writer.WriteRecommendations(results);

        assertTrue(Files.exists(Paths.get(testFilePath)));
        try (BufferedReader reader = new BufferedReader(new FileReader(testFilePath))) {
            String line = reader.readLine();
            assertEquals("John Doe,123456789", line);
            String movies = reader.readLine();
            assertEquals("Movie1,Movie2,Movie3,Movie4,Movie5", movies);
        } catch (Exception e) {
            fail("Error reading file: " + e.getMessage());
        }
    }

    @Test
    public void DF10_writeRecommendationsSingleMovie() {
        OutputFileWriter writer = new OutputFileWriter(testFilePath);
        ArrayList<String> results = new ArrayList<>();
        results.add("John Doe,123456789");
        results.add("Movie1");

        writer.WriteRecommendations(results);

        assertTrue(Files.exists(Paths.get(testFilePath)));
        try (BufferedReader reader = new BufferedReader(new FileReader(testFilePath))) {
            String line1 = reader.readLine();
            String line2 = reader.readLine();
            assertEquals("John Doe,123456789", line1);
            assertEquals("Movie1", line2);
        } catch (Exception e) {
            fail("Error reading file: " + e.getMessage());
        }
    }

    @Test
    public void DF11_writeRecommendationsEmptyRecommendations() {
        OutputFileWriter writer = new OutputFileWriter(testFilePath);
        ArrayList<String> results = new ArrayList<>();
        results.add("John Doe,123456789");
        results.add("");

        writer.WriteRecommendations(results);

        assertTrue(Files.exists(Paths.get(testFilePath)));
        try (BufferedReader reader = new BufferedReader(new FileReader(testFilePath))) {
            String line1 = reader.readLine();
            String line2 = reader.readLine();
            assertEquals("John Doe,123456789", line1);
            assertEquals("", line2);
        } catch (Exception e) {
            fail("Error reading file: " + e.getMessage());
        }
    }

    @Test
    public void DF12_dataFlowErrorToFile() {
        OutputFileWriter writer = new OutputFileWriter(testFilePath);
        ArrayList<String> errors = new ArrayList<>();
        String errorMessage = "ERROR: User ID {invalid} is wrong";
        errors.add(errorMessage);

        writer.WriteFirstError(errors);

        try (BufferedReader reader = new BufferedReader(new FileReader(testFilePath))) {
            String line = reader.readLine();
            assertEquals(errorMessage, line);
        } catch (Exception e) {
            fail("Error reading file: " + e.getMessage());
        }
    }

    @Test
    public void DF13_dataFlowRecommendationsToFile() {
        OutputFileWriter writer = new OutputFileWriter(testFilePath);
        ArrayList<String> results = new ArrayList<>();
        String userName = "John Doe";
        String userId = "123456789";
        String recommendations = "Movie1,Movie2,Movie3";
        results.add(userName + "," + userId);
        results.add(recommendations);

        writer.WriteRecommendations(results);

        try (BufferedReader reader = new BufferedReader(new FileReader(testFilePath))) {
            String line1 = reader.readLine();
            String line2 = reader.readLine();
            assertTrue(line1.contains(userName));
            assertTrue(line1.contains(userId));
            assertTrue(line2.contains("Movie1"));
        } catch (Exception e) {
            fail("Error reading file: " + e.getMessage());
        }
    }

    @Test
    public void DF14_multipleWriteOperations() {
        OutputFileWriter writer = new OutputFileWriter(testFilePath);
        
        // First write
        ArrayList<String> results1 = new ArrayList<>();
        results1.add("User1,ID1");
        results1.add("Movie1,Movie2");
        writer.WriteRecommendations(results1);

        // Verify first write
        try (BufferedReader reader = new BufferedReader(new FileReader(testFilePath))) {
            String line = reader.readLine();
            assertEquals("User1,ID1", line);
        } catch (Exception e) {
            fail("Error reading file: " + e.getMessage());
        }

        // Second write should clean file
        ArrayList<String> results2 = new ArrayList<>();
        results2.add("User2,ID2");
        results2.add("Movie3,Movie4");
        writer.WriteRecommendations(results2);

        // Verify second write
        try (BufferedReader reader = new BufferedReader(new FileReader(testFilePath))) {
            String line = reader.readLine();
            assertEquals("User2,ID2", line);
        } catch (Exception e) {
            fail("Error reading file: " + e.getMessage());
        }
    }

    @Test
    public void DF15_filePathConfiguration() {
        String path = "TextFile_integrationTest/dfttest_config.txt";
        try {
            Files.deleteIfExists(Paths.get(path));
        } catch (Exception e) {
            // File doesn't exist yet
        }

        OutputFileWriter writer = new OutputFileWriter(path);
        ArrayList<String> results = new ArrayList<>();
        results.add("Test,123456789");
        results.add("Movie1");

        writer.WriteRecommendations(results);

        assertTrue(Files.exists(Paths.get(path)));
        
        try {
            Files.deleteIfExists(Paths.get(path));
        } catch (Exception e) {
            // Cleanup
        }
    }
}

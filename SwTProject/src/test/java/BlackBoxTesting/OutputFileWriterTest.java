/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package BlackBoxTesting;

import com.mycompany.swtproject.OutputFileWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Black Box Testing for OutputFileWriter
 */
public class OutputFileWriterTest {

    private String tempFilePath;
    private OutputFileWriter writer;

    public OutputFileWriterTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() throws IOException {
        // Create a temporary file for each test
        File tempFile = File.createTempFile("testOutput", ".txt");
        tempFilePath = tempFile.getAbsolutePath();

        // Ensure it's clean/empty
        tempFile.deleteOnExit();

        writer = new OutputFileWriter(tempFilePath);
    }

    @AfterEach
    public void tearDown() {
        // Cleanup if needed, though deleteOnExit handles best effort
        new File(tempFilePath).delete();
    }

    @Test
    public void testWriteRecommendations() throws IOException {
        // Arrange
        ArrayList<String> results = new ArrayList<>();
        results.add("John Doe,123456789");
        results.add("Movie A,Movie B");
        results.add("Jane Doe,987654321");
        results.add("Movie C");

        // Act
        writer.WriteRecommendations(results);

        // Assert
        // Read file back
        BufferedReader br = new BufferedReader(new FileReader(tempFilePath));
        String line1 = br.readLine();
        String line2 = br.readLine();
        String line3 = br.readLine(); // Blank line expected between users
        String line4 = br.readLine();
        String line5 = br.readLine();
        br.close();

        assertEquals("John Doe,123456789", line1);
        assertEquals("Movie A,Movie B", line2);
        assertTrue(line3 == null || line3.isEmpty(), "Should have blank line or be empty string depending on impl"); // Impl
                                                                                                                     // says
                                                                                                                     // newLine()

        // The implementation loop:
        // for i=0..size
        // write(result)
        // newLine
        // if i%2 != 0 newLine (every 2nd item, so after the movie list)

        // Index 0 (User): Writes "John...", newLine
        // Index 1 (Movies): Writes "Movie...", newLine, then (1%2!=0) -> newLine
        // (blank)
        // Index 2 (User): Writes "Jane...", newLine
        // Index 3 (Movies): Writes "Movie...", newLine, then (3%2!=0) -> newLine
        // (blank)

        // Re-verifying read logic:
        // line1: John
        // line2: Movie A
        // line3: (Blank)
        // line4: Jane
        // line5: Movie C

        // Check blank line
        if (line3 != null) {
            assertEquals("", line3.trim(), "Line should be empty");
        }
        assertEquals("Jane Doe,987654321", line4);
        assertTrue(line5.contains("Movie C"), "Should contain last movie");
    }

    @Test
    public void testCleanFile() throws IOException {
        // Write something first
        Files.writeString(new File(tempFilePath).toPath(), "Garbage Data");

        // Act
        writer.cleanFile(tempFilePath);

        // Assert
        String content = Files.readString(new File(tempFilePath).toPath());
        assertEquals("", content, "File should be empty after clear");
    }
}

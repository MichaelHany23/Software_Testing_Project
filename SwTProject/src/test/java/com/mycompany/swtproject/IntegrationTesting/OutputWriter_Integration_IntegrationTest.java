/*
 * Output File Writer Integration Test
 * Tests the OutputFileWriter integration with the complete system using TextFile_integrationTest data
 */
package com.mycompany.swtproject.IntegrationTesting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import com.mycompany.swtproject.Application;
import com.mycompany.swtproject.MovieFilereader;
import com.mycompany.swtproject.UserFilereader;

public class OutputWriter_Integration_IntegrationTest {
    
    private static final String TEST_DATA_PATH = "TextFile_integrationTest/";
    private static final String OUTPUT_PATH = "TextFile_integrationTest/output_writer_test.txt";
    
    @AfterEach
    public void cleanup() {
        try {
            Files.deleteIfExists(Paths.get(OUTPUT_PATH));
        } catch (IOException e) {
            // ignore
        }
    }
    
    /**
     * TC1: Recommendations should be written to output file
     */
    @Test
    public void TC1_RecommendationsWrittenCorrectly() {
        Application app = new Application(
            new MovieFilereader(),
            new UserFilereader()
        );
        
        app.RecommenderApp(
            TEST_DATA_PATH + "movies.txt",
            TEST_DATA_PATH + "users.txt",
            OUTPUT_PATH
        );
        
        try {
            assertTrue(Files.exists(Paths.get(OUTPUT_PATH)), "Output file should exist");
            String content = new String(Files.readAllBytes(Paths.get(OUTPUT_PATH)));
            
            assertNotNull(content);
            assertTrue(content.length() > 0, "Output should not be empty");
            
            // Verify output contains user information
            assertTrue(content.contains("michael") || content.contains("adham") || content.contains("andrew"),
                      "Output should contain user names");
            
            // Verify output contains movie recommendations
            String[] lines = content.split("\n");
            assertTrue(lines.length >= 4, "Should have recommendations for multiple users");
            
        } catch (IOException e) {
            fail("Output file should be readable: " + e.getMessage());
        }
    }
    
    /**
     * TC2: Error messages should be written correctly
     */
    @Test
    public void TC2_ErrorMessageWrittenCorrectly() {
        Application app = new Application(
            new MovieFilereader(),
            new UserFilereader()
        );
        
        app.RecommenderApp(
            TEST_DATA_PATH + "moviesError.txt",
            TEST_DATA_PATH + "users.txt",
            OUTPUT_PATH
        );
        
        try {
            assertTrue(Files.exists(Paths.get(OUTPUT_PATH)), "Output file should exist");
            String content = new String(Files.readAllBytes(Paths.get(OUTPUT_PATH)));
            
            assertNotNull(content);
            assertTrue(content.contains("ERROR"), "Output should contain ERROR keyword");
            
        } catch (IOException e) {
            fail("Output file should be readable: " + e.getMessage());
        }
    }
    
    /**
     * TC3: Multiple users should have formatted output
     */
    @Test
    public void TC3_MultipleUsersFormattedOutput() {
        Application app = new Application(
            new MovieFilereader(),
            new UserFilereader()
        );
        
        app.RecommenderApp(
            TEST_DATA_PATH + "moviesSmall.txt",
            TEST_DATA_PATH + "usersSmall.txt",
            OUTPUT_PATH
        );
        
        try {
            String content = new String(Files.readAllBytes(Paths.get(OUTPUT_PATH)));
            
            // Verify output has content
            assertNotNull(content);
            assertFalse(content.trim().isEmpty(), "Output should not be empty");
            
        } catch (IOException e) {
            fail("Output file should be readable: " + e.getMessage());
        }
    }
    
    /**
     * TC4: Output format should match expected structure (userId,movieList)
     */
    @Test
    public void TC4_OutputFormatValidation() {
        Application app = new Application(
            new MovieFilereader(),
            new UserFilereader()
        );
        
        app.RecommenderApp(
            TEST_DATA_PATH + "moviesGenreTest.txt",
            TEST_DATA_PATH + "usersGenreTest.txt",
            OUTPUT_PATH
        );
        
        try {
            String content = new String(Files.readAllBytes(Paths.get(OUTPUT_PATH)));
            String[] lines = content.split("\n");
            
            for (String line : lines) {
                if (!line.trim().isEmpty() && !line.contains("ERROR")) {
                    // Each line should have format: username,userId or movielist
                    assertTrue(line.contains(",") || line.trim().isEmpty(),
                              "Non-empty lines should contain comma-separated values or be empty");
                }
            }
            
        } catch (IOException e) {
            fail("Output file should be readable: " + e.getMessage());
        }
    }
}

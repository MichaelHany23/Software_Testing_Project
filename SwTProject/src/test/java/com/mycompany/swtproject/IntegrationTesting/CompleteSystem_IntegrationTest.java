/*
 * Complete Integration Test Suite for Movie Recommendation System
 * Tests using TextFile_integrationTest data files
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

public class CompleteSystem_IntegrationTest {
    
    private static final String TEST_DATA_PATH = "TextFile_integrationTest/";
    private static final String OUTPUT_PATH = "TextFile_integrationTest/test_integration_output.txt";
    
    @AfterEach
    public void cleanup() {
        try {
            Files.deleteIfExists(Paths.get(OUTPUT_PATH));
        } catch (IOException e) {
            // ignore
        }
    }
    
    /**
     * TC1: Full valid workflow - movies.txt and users.txt should produce recommendations
     */
    @Test
    public void TC1_FullValidWorkflow() {
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
            assertTrue(Files.exists(Paths.get(OUTPUT_PATH)), "Output file should be created");
            String content = new String(Files.readAllBytes(Paths.get(OUTPUT_PATH)));
            assertNotNull(content);
            assertTrue(content.length() > 0, "Output should contain recommendations");
            
            // Verify specific users are in output
            assertTrue(content.contains("michael sameh") || content.contains("michael hany"),
                      "Output should contain user recommendations");
        } catch (IOException e) {
            fail("Output file should exist and be readable: " + e.getMessage());
        }
    }
    
    /**
     * TC2: Error workflow - moviesError.txt contains validation errors
     */
    @Test
    public void TC2_ErrorWorkflow_InvalidMovies() {
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
            String content = new String(Files.readAllBytes(Paths.get(OUTPUT_PATH)));
            assertTrue(content.contains("ERROR"), "Output should contain error message");
            // Should not contain recommendations
            assertFalse(content.split("\n").length > 10, 
                       "Error output should be brief, not full recommendations");
        } catch (IOException e) {
            fail("Output file should exist: " + e.getMessage());
        }
    }
    
    /**
     * TC3: Error workflow - usersError.txt contains validation errors
     */
    @Test
    public void TC3_ErrorWorkflow_InvalidUsers() {
        Application app = new Application(
            new MovieFilereader(),
            new UserFilereader()
        );
        
        app.RecommenderApp(
            TEST_DATA_PATH + "movies.txt",
            TEST_DATA_PATH + "usersError.txt",
            OUTPUT_PATH
        );
        
        try {
            String content = new String(Files.readAllBytes(Paths.get(OUTPUT_PATH)));
            assertTrue(content.contains("ERROR"), "Output should contain error message");
            assertTrue(content.contains("1234567BA"), 
                      "Error should reference the invalid user ID");
        } catch (IOException e) {
            fail("Output file should exist: " + e.getMessage());
        }
    }
    
    /**
     * TC4: Small dataset test - minimal valid data
     */
    @Test
    public void TC4_SmallDataset() {
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
            assertNotNull(content);
            assertTrue(content.contains("michael sameh") || content.contains("john doe"),
                      "Should contain recommendations for users");
        } catch (IOException e) {
            fail("Output file should exist: " + e.getMessage());
        }
    }
    
    /**
     * TC5: Dummy data test - very minimal dataset
     */
    @Test
    public void TC5_DummyData() {
        Application app = new Application(
            new MovieFilereader(),
            new UserFilereader()
        );
        
        app.RecommenderApp(
            TEST_DATA_PATH + "dummymovies.txt",
            TEST_DATA_PATH + "dummyusers.txt",
            OUTPUT_PATH
        );
        
        try {
            String content = new String(Files.readAllBytes(Paths.get(OUTPUT_PATH)));
            assertNotNull(content);
            assertTrue(content.contains("michael"), 
                      "Should process dummy data successfully");
        } catch (IOException e) {
            fail("Output file should exist: " + e.getMessage());
        }
    }
    
    /**
     * TC6: Invalid movie ID format
     */
    @Test
    public void TC6_InvalidMovieIDFormat() {
        Application app = new Application(
            new MovieFilereader(),
            new UserFilereader()
        );
        
        app.RecommenderApp(
            TEST_DATA_PATH + "moviesInvalidID.txt",
            TEST_DATA_PATH + "usersSmall.txt",
            OUTPUT_PATH
        );
        
        try {
            String content = new String(Files.readAllBytes(Paths.get(OUTPUT_PATH)));
            assertTrue(content.contains("ERROR"), 
                      "Invalid movie ID should trigger error");
        } catch (IOException e) {
            fail("Output file should exist: " + e.getMessage());
        }
    }
    
    /**
     * TC7: Invalid movie title format
     */
    @Test
    public void TC7_InvalidMovieTitleFormat() {
        Application app = new Application(
            new MovieFilereader(),
            new UserFilereader()
        );
        
        app.RecommenderApp(
            TEST_DATA_PATH + "moviesInvalidTitle.txt",
            TEST_DATA_PATH + "usersSmall.txt",
            OUTPUT_PATH
        );
        
        try {
            String content = new String(Files.readAllBytes(Paths.get(OUTPUT_PATH)));
            assertTrue(content.contains("ERROR"), 
                      "Invalid movie title should trigger error");
            assertTrue(content.contains("inception") || content.contains("Title"),
                      "Error should reference the invalid title");
        } catch (IOException e) {
            fail("Output file should exist: " + e.getMessage());
        }
    }
    
    /**
     * TC8: Invalid user name format
     */
    @Test
    public void TC8_InvalidUserNameFormat() {
        Application app = new Application(
            new MovieFilereader(),
            new UserFilereader()
        );
        
        app.RecommenderApp(
            TEST_DATA_PATH + "moviesSmall.txt",
            TEST_DATA_PATH + "usersInvalidName.txt",
            OUTPUT_PATH
        );
        
        try {
            String content = new String(Files.readAllBytes(Paths.get(OUTPUT_PATH)));
            assertTrue(content.contains("ERROR"), 
                      "Invalid user name should trigger error");
        } catch (IOException e) {
            fail("Output file should exist: " + e.getMessage());
        }
    }
    
    /**
     * TC9: Invalid user ID format
     */
    @Test
    public void TC9_InvalidUserIDFormat() {
        Application app = new Application(
            new MovieFilereader(),
            new UserFilereader()
        );
        
        app.RecommenderApp(
            TEST_DATA_PATH + "moviesSmall.txt",
            TEST_DATA_PATH + "usersInvalidID.txt",
            OUTPUT_PATH
        );
        
        try {
            String content = new String(Files.readAllBytes(Paths.get(OUTPUT_PATH)));
            assertTrue(content.contains("ERROR"), 
                      "Invalid user ID should trigger error");
        } catch (IOException e) {
            fail("Output file should exist: " + e.getMessage());
        }
    }
}

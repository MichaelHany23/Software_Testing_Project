/*
 * File Reader Validation Integration Test
 * Tests the integration of file readers with validators using TextFile_integrationTest data
 */
package com.mycompany.swtproject.IntegrationTesting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import com.mycompany.swtproject.Movie;
import com.mycompany.swtproject.MovieFilereader;
import com.mycompany.swtproject.MovieValidator;
import com.mycompany.swtproject.Recommender;
import com.mycompany.swtproject.User;
import com.mycompany.swtproject.UserFilereader;
import com.mycompany.swtproject.UserValidator;
import com.mycompany.swtproject.Application;

public class FileReader_Validation_IntegrationTest {
    
    private static final String TEST_DATA_PATH = "TextFile_integrationTest/";
    private static final String OUTPUT_PATH = "TextFile_integrationTest/output_test.txt";
    
    @AfterEach
    public void cleanup() {
        try {
            Files.deleteIfExists(Paths.get(OUTPUT_PATH));
        } catch (IOException e) {
            // ignore
        }
    }
    
    /**
     * TC1: Valid movies.txt should read and validate successfully
     */
    @Test
    public void TC1_ValidMoviesReadAndValidate() {
        MovieFilereader reader = new MovieFilereader();
        ArrayList<Movie> movies = reader.ReadMovies(TEST_DATA_PATH + "movies.txt");
        
        assertNotNull(movies);
        assertFalse(movies.isEmpty(), "Should read movies from file");
        
        MovieValidator validator = new MovieValidator(movies);
        validator.Validate();
        
        assertTrue(validator.ErrorIsEmpty(), "Valid movies should pass validation");
        assertEquals(0, validator.getMovie_errors().size());
        assertEquals(15, movies.size(), "Should have 15 movies");
    }
    
    /**
     * TC2: Valid users.txt should read and validate successfully
     */
    @Test
    public void TC2_ValidUsersReadAndValidate() {
        UserFilereader reader = new UserFilereader();
        ArrayList<User> users = reader.ReadUsers(TEST_DATA_PATH + "users.txt");
        
        assertNotNull(users);
        assertFalse(users.isEmpty(), "Should read users from file");
        
        UserValidator validator = new UserValidator(users);
        validator.Validate();
        
        assertTrue(validator.ErrorIsEmpty(), "Valid users should pass validation");
        assertEquals(0, validator.getUser_errors().size());
        assertEquals(4, users.size(), "Should have 4 users");
    }
    
    /**
     * TC3: moviesError.txt contains invalid movies
     * Expected errors: S867 (missing 'I'), T321 (missing 'A'), HPSS101 (lowercase words in title)
     */
    @Test
    public void TC3_InvalidMoviesDetection() {
        MovieFilereader reader = new MovieFilereader();
        ArrayList<Movie> movies = reader.ReadMovies(TEST_DATA_PATH + "moviesError.txt");
        
        assertNotNull(movies);
        
        MovieValidator validator = new MovieValidator(movies);
        validator.Validate();
        
        assertFalse(validator.ErrorIsEmpty(), "Invalid movies should fail validation");
        assertTrue(validator.getMovie_errors().size() >= 3, "Should detect at least 3 errors");
        
        // Verify specific invalid IDs are detected
        String allErrors = String.join(" ", validator.getMovie_errors());
        assertTrue(allErrors.contains("S867") || allErrors.contains("T321") || allErrors.contains("HPSS101"),
                  "Should detect specific invalid movie IDs or titles");
    }
    
    /**
     * TC4: usersError.txt contains invalid users
     */
    @Test
    public void TC4_InvalidUsersDetection() {
        UserFilereader reader = new UserFilereader();
        ArrayList<User> users = reader.ReadUsers(TEST_DATA_PATH + "usersError.txt");
        
        assertNotNull(users);
        
        UserValidator validator = new UserValidator(users);
        validator.Validate();
        
        assertFalse(validator.ErrorIsEmpty(), "Invalid users should fail validation");
        assertFalse(validator.getUser_errors().isEmpty(), "Should detect errors");
        
        // Verify specific error about invalid user ID
        boolean foundInvalidIDError = false;
        for (String error : validator.getUser_errors()) {
            if (error.contains("1234567BA")) {
                foundInvalidIDError = true;
                break;
            }
        }
        assertTrue(foundInvalidIDError, "Should detect invalid user ID format");
    }
    
    /**
     * TC5: Duplicate movie ID should be detected
     * File has 2 movies with same ID BDK123
     */
    @Test
    public void TC5_DuplicateMovieIDDetection() {
        MovieFilereader reader = new MovieFilereader();
        ArrayList<Movie> movies = reader.ReadMovies(TEST_DATA_PATH + "moviesDuplicateID.txt");
        
        MovieValidator validator = new MovieValidator(movies);
        validator.Validate();
        
        assertFalse(validator.ErrorIsEmpty());
        assertEquals(1, validator.getMovie_errors().size(), "Should detect exactly 1 duplicate error");
        assertEquals("ERROR: Movie Id letters {BDK123} is not unique", validator.getMovie_errors().get(0));
    }
    
    /**
     * TC6: Duplicate user ID should be detected
     * File has 2 users with same ID 123456789
     */
    @Test
    public void TC6_DuplicateUserIDDetection() {
        UserFilereader reader = new UserFilereader();
        ArrayList<User> users = reader.ReadUsers(TEST_DATA_PATH + "usersDuplicateID.txt");
        
        UserValidator validator = new UserValidator(users);
        validator.Validate();
        
        assertFalse(validator.ErrorIsEmpty());
        assertEquals(1, validator.getUser_errors().size(), "Should detect exactly 1 duplicate error");
        assertEquals("ERROR: User ID {123456789} is wrong", validator.getUser_errors().get(0));
    }
    
    /**
     * TC7: Recommendations based on genre
     */
    @Test
    public void TC7_GenreBasedRecommendations() {
        MovieFilereader movieReader = new MovieFilereader();
        UserFilereader userReader = new UserFilereader();
        
        ArrayList<Movie> movies = movieReader.ReadMovies(TEST_DATA_PATH + "moviesRecommendationTest.txt");
        ArrayList<User> users = userReader.ReadUsers(TEST_DATA_PATH + "usersRecommendationTest.txt");
        
        Recommender recommender = new Recommender(movies, users);
        String recommendations = recommender.GetRecommendations_OnUser(users.get(0));
        
        assertTrue(recommendations.contains("Spiderman Home Coming"));
        assertFalse(recommendations.contains("The Notebook"));
        assertFalse(recommendations.contains("Batman Dark Knight"));
    }
    
    /**
     * TC8: Full pipeline - recommendations written to output file
     * users.txt has 4 users: michael sameh, michael hany, adham ahmed, andrew wagdy
     */
    @Test
    public void TC8_RecommendationsWrittenToFile() {
        Application app = new Application(new MovieFilereader(), new UserFilereader());
        
        app.RecommenderApp(
            TEST_DATA_PATH + "movies.txt",
            TEST_DATA_PATH + "users.txt",
            OUTPUT_PATH
        );
        
        try {
            assertTrue(Files.exists(Paths.get(OUTPUT_PATH)));
            String content = new String(Files.readAllBytes(Paths.get(OUTPUT_PATH)));
            
            // Verify specific users from users.txt are in output
            assertTrue(content.contains("michael sameh"), "Output should contain user michael sameh");
            assertTrue(content.contains("123456789"), "Output should contain user ID 123456789");
            assertTrue(content.contains("michael hany") || content.contains("adham ahmed"),
                      "Output should contain other users");
            
            // Verify output format: each user has 2 lines (name,id and recommendations)
            String[] lines = content.split("\n");
            assertTrue(lines.length >= 8, "Should have at least 8 lines for 4 users (2 lines each)");
            
        } catch (IOException e) {
            fail("Output file should be readable");
        }
    }
    
    /**
     * TC9: Error messages written to output file
     * moviesError.txt has invalid movies - only first error should be written
     */
    @Test
    public void TC9_ErrorMessageWrittenToFile() {
        Application app = new Application(new MovieFilereader(), new UserFilereader());
        
        app.RecommenderApp(
            TEST_DATA_PATH + "moviesError.txt",
            TEST_DATA_PATH + "users.txt",
            OUTPUT_PATH
        );
        
        try {
            assertTrue(Files.exists(Paths.get(OUTPUT_PATH)));
            String content = new String(Files.readAllBytes(Paths.get(OUTPUT_PATH)));
            
            // Verify error format
            assertTrue(content.startsWith("ERROR:"), "Output should start with ERROR:");
            assertTrue(content.contains("Movie"), "Error should reference Movie");
            
            // Verify only first error written (single line output)
            String[] lines = content.trim().split("\n");
            assertEquals(1, lines.length, "Should write only 1 error line (first error)");
            
        } catch (IOException e) {
            fail("Output file should be readable");
        }
    }
}

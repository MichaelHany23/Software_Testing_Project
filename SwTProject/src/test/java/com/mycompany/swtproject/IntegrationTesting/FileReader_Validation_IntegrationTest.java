/*
 * File Reader Validation Integration Test
 * Tests the integration of file readers with validators using TextFile_integrationTest data
 */
package com.mycompany.swtproject.IntegrationTesting;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.mycompany.swtproject.Movie;
import com.mycompany.swtproject.MovieFilereader;
import com.mycompany.swtproject.MovieValidator;
import com.mycompany.swtproject.User;
import com.mycompany.swtproject.UserFilereader;
import com.mycompany.swtproject.UserValidator;

public class FileReader_Validation_IntegrationTest {
    
    private static final String TEST_DATA_PATH = "TextFile_integrationTest/";
    
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
        assertEquals(16, movies.size(), "Should have 16 movies");
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
     */
    @Test
    public void TC3_InvalidMoviesDetection() {
        MovieFilereader reader = new MovieFilereader();
        ArrayList<Movie> movies = reader.ReadMovies(TEST_DATA_PATH + "moviesError.txt");
        
        assertNotNull(movies);
        
        MovieValidator validator = new MovieValidator(movies);
        validator.Validate();
        
        assertFalse(validator.ErrorIsEmpty(), "Invalid movies should fail validation");
        assertFalse(validator.getMovie_errors().isEmpty(), "Should detect errors");
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
     * TC5: Small valid dataset should work correctly
     */
    @Test
    public void TC5_SmallDatasetValidation() {
        MovieFilereader movieReader = new MovieFilereader();
        UserFilereader userReader = new UserFilereader();
        
        ArrayList<Movie> movies = movieReader.ReadMovies(TEST_DATA_PATH + "moviesSmall.txt");
        ArrayList<User> users = userReader.ReadUsers(TEST_DATA_PATH + "usersSmall.txt");
        
        MovieValidator movieValidator = new MovieValidator(movies);
        UserValidator userValidator = new UserValidator(users);
        
        movieValidator.Validate();
        userValidator.Validate();
        
        assertTrue(movieValidator.ErrorIsEmpty());
        assertTrue(userValidator.ErrorIsEmpty());
        assertEquals(3, movies.size());
        assertEquals(2, users.size());
    }
    
    /**
     * TC6: Dummy data validation
     */
    @Test
    public void TC6_DummyDataValidation() {
        MovieFilereader movieReader = new MovieFilereader();
        UserFilereader userReader = new UserFilereader();
        
        ArrayList<Movie> movies = movieReader.ReadMovies(TEST_DATA_PATH + "dummymovies.txt");
        ArrayList<User> users = userReader.ReadUsers(TEST_DATA_PATH + "dummyusers.txt");
        
        assertNotNull(movies);
        assertNotNull(users);
        
        MovieValidator movieValidator = new MovieValidator(movies);
        UserValidator userValidator = new UserValidator(users);
        
        movieValidator.Validate();
        userValidator.Validate();
        
        // Check if dummy data is valid or has errors
        if (!movieValidator.ErrorIsEmpty()) {
            assertFalse(movieValidator.getMovie_errors().isEmpty());
        }
        if (!userValidator.ErrorIsEmpty()) {
            assertFalse(userValidator.getUser_errors().isEmpty());
        }
    }
    
    /**
     * TC7: Invalid movie ID format detection
     */
    @Test
    public void TC7_InvalidMovieIDDetection() {
        MovieFilereader reader = new MovieFilereader();
        ArrayList<Movie> movies = reader.ReadMovies(TEST_DATA_PATH + "moviesInvalidID.txt");
        
        MovieValidator validator = new MovieValidator(movies);
        validator.Validate();
        
        assertFalse(validator.ErrorIsEmpty(), "Should detect invalid movie ID");
        assertFalse(validator.getMovie_errors().isEmpty());
    }
    
    /**
     * TC8: Invalid movie title format detection
     */
    @Test
    public void TC8_InvalidMovieTitleDetection() {
        MovieFilereader reader = new MovieFilereader();
        ArrayList<Movie> movies = reader.ReadMovies(TEST_DATA_PATH + "moviesInvalidTitle.txt");
        
        MovieValidator validator = new MovieValidator(movies);
        validator.Validate();
        
        assertFalse(validator.ErrorIsEmpty(), "Should detect invalid movie title");
        boolean foundTitleError = false;
        for (String error : validator.getMovie_errors()) {
            if (error.toLowerCase().contains("title") || error.contains("inception")) {
                foundTitleError = true;
                break;
            }
        }
        assertTrue(foundTitleError, "Should report title capitalization error");
    }
    
    /**
     * TC9: Invalid user name format detection
     */
    @Test
    public void TC9_InvalidUserNameDetection() {
        UserFilereader reader = new UserFilereader();
        ArrayList<User> users = reader.ReadUsers(TEST_DATA_PATH + "usersInvalidName.txt");
        
        UserValidator validator = new UserValidator(users);
        validator.Validate();
        
        assertFalse(validator.ErrorIsEmpty(), "Should detect invalid user name");
        boolean foundNameError = false;
        for (String error : validator.getUser_errors()) {
            if (error.toLowerCase().contains("name") || error.contains("michael123")) {
                foundNameError = true;
                break;
            }
        }
        assertTrue(foundNameError, "Should report user name error");
    }
    
    /**
     * TC10: Invalid user ID format detection
     */
    @Test
    public void TC10_InvalidUserIDDetection() {
        UserFilereader reader = new UserFilereader();
        ArrayList<User> users = reader.ReadUsers(TEST_DATA_PATH + "usersInvalidID.txt");
        
        UserValidator validator = new UserValidator(users);
        validator.Validate();
        
        assertFalse(validator.ErrorIsEmpty(), "Should detect invalid user ID");
        assertFalse(validator.getUser_errors().isEmpty());
    }
    
    /**
     * TC11: Genre test data should be valid
     */
    @Test
    public void TC11_GenreTestDataValidation() {
        MovieFilereader movieReader = new MovieFilereader();
        UserFilereader userReader = new UserFilereader();
        
        ArrayList<Movie> movies = movieReader.ReadMovies(TEST_DATA_PATH + "moviesGenreTest.txt");
        ArrayList<User> users = userReader.ReadUsers(TEST_DATA_PATH + "usersGenreTest.txt");
        
        MovieValidator movieValidator = new MovieValidator(movies);
        UserValidator userValidator = new UserValidator(users);
        
        movieValidator.Validate();
        userValidator.Validate();
        
        assertTrue(movieValidator.ErrorIsEmpty(), "Genre test movies should be valid");
        assertTrue(userValidator.ErrorIsEmpty(), "Genre test users should be valid");
    }
}

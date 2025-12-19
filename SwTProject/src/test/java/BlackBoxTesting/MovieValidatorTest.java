/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package BlackBoxTesting;

import com.mycompany.swtproject.Movie;
import com.mycompany.swtproject.MovieValidator;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Black Box Testing Examples for MovieValidator
 * 
 * Goals:
 * 1. Understand input requirements.
 * 2. Write tests for Valid cases.
 * 3. Write tests for Invalid cases (Equivalence Partitioning).
 * 4. Find bugs!
 */
public class MovieValidatorTest {

    // We will use this list to set up our validator for each test
    private ArrayList<Movie> moviesList;
    private MovieValidator validator;

    public MovieValidatorTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        // Reset list before each test
        moviesList = new ArrayList<>();
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testValidMovie() {
        // Arrange
        // Title: Capitalized words. ID: Initials + 3 numbers.
        moviesList.add(new Movie("Toy Story", "TS001"));
        validator = new MovieValidator(moviesList);

        // Act
        validator.Validate();

        // Assert
        assertTrue(validator.ErrorIsEmpty(), "Valid movie should have no errors");

    }

    @Test
    public void testInvalidTitleLowerCase() {
        // Arrange
        moviesList.add(new Movie("toy story", "TS001")); // Lowercase 't' and 's'
        validator = new MovieValidator(moviesList);

        // Act
        validator.Validate();

        // Assert
        // We expect errors because the title requirements are not met
        assertFalse(validator.ErrorIsEmpty(), "Should contain errors for lowercase title");

        // Optional: Check specific error message (White box ish, but good for
        // verification)
        System.out.println(validator.getMovie_errors());
    }

    @Test
    public void testInvalidIdStructure() {
        // Arrange
        moviesList.add(new Movie("Toy Story", "XX001")); // XX does not match TS
        validator = new MovieValidator(moviesList);

        // Act
        validator.Validate();

        // Assert
        assertFalse(validator.ErrorIsEmpty(), "Should contain errors for non-matching ID initials");
        System.out.println(validator.getMovie_errors());
    }

    @Test
    public void testInvalidIdNonNumericSuffix() {
        // Requirement says: "followed by 3 unique numbers"

        // Arrange
        moviesList.add(new Movie("Toy Story", "TSabc")); // 'abc' are not numbers!
        validator = new MovieValidator(moviesList);

        // Act
        validator.Validate();

        assertFalse(validator.ErrorIsEmpty(), "Should contain errors for non-numeric ID suffix");

    }

    @Test
    public void testDuplicateIds() {
        // 1. Add first movie (valid)
        // 2. Add second movie (valid title, but SAME ID as first)
        // 3. Validate
        // 4. Assert that ErrorIsEmpty() is false

        moviesList.add(new Movie("Toy Story", "TS001"));
        moviesList.add(new Movie("The Shinning", "TS001"));
        validator = new MovieValidator(moviesList);
        validator.Validate();
        assertFalse(validator.ErrorIsEmpty(), "there should be an error for 2 similar movie id's");
        System.out.println(validator.getMovie_errors());
    }

    @Test
    public void testTitleWithNumbers() {

        moviesList.add(new Movie("12 Angry Men", "AM001"));
        validator = new MovieValidator(moviesList);
        validator.Validate();
        assertTrue(validator.ErrorIsEmpty(), "Id should contain only letters from the movie title not numbers");
    }
}

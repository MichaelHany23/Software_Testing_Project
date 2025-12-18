package IntegrationTesting;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.mycompany.swtproject.Movie;
import com.mycompany.swtproject.MovieFilereader;
import com.mycompany.swtproject.MovieValidator;
import com.mycompany.swtproject.Recommender;
import com.mycompany.swtproject.User;
import com.mycompany.swtproject.UserFilereader;
import com.mycompany.swtproject.UserValidator;

public class FileReader_Validator_Recommender_IntegrationTest {

    private static final String TEST_DATA_PATH = "TextFile_integrationTest/";

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

    @Test
    public void TC3_InvalidMoviesDetection() {
        MovieFilereader reader = new MovieFilereader();
        ArrayList<Movie> movies = reader.ReadMovies(TEST_DATA_PATH + "moviesError.txt");

        assertNotNull(movies);

        MovieValidator validator = new MovieValidator(movies);
        validator.Validate();

        assertFalse(validator.ErrorIsEmpty(), "Invalid movies should fail validation");

        String allErrors = String.join(" ", validator.getMovie_errors());
        assertTrue(allErrors.contains("S867") && allErrors.contains("T321") && allErrors.contains("Harry Potter and the Sorcerer's Stone"),
                "Should detect specific invalid movie IDs or titles");
    }

    @Test
    public void TC4_InvalidUsersDetection() {
        UserFilereader reader = new UserFilereader();
        ArrayList<User> users = reader.ReadUsers(TEST_DATA_PATH + "usersError.txt");

        assertNotNull(users);

        UserValidator validator = new UserValidator(users);
        validator.Validate();

        assertFalse(validator.ErrorIsEmpty(), "Invalid users should fail validation");
        String allErrors = String.join(" ", validator.getUser_errors());
        assertTrue(allErrors.contains("1234567BA") && allErrors.contains("123456789"),
                "Should detect invalid user IDs");

    }

    @Test
    public void TC5_DuplicateMovieIDDetection() {
        MovieFilereader reader = new MovieFilereader();
        ArrayList<Movie> movies = reader.ReadMovies(TEST_DATA_PATH + "moviesDuplicateID.txt");

        MovieValidator validator = new MovieValidator(movies);
        validator.Validate();

        String allErrors = String.join(" ", validator.getMovie_errors());

        assertFalse(allErrors.isEmpty(), "Should detect duplicate movie IDs");
        assertTrue(allErrors.contains("BDK123"), "Should detect duplicate movie ID BDK123");

    }

    @Test
    public void TC6_DuplicateUserIDDetection() {
        UserFilereader reader = new UserFilereader();
        ArrayList<User> users = reader.ReadUsers(TEST_DATA_PATH + "usersDuplicateID.txt");

        UserValidator validator = new UserValidator(users);
        validator.Validate();
        String allErrors = String.join(" ", validator.getUser_errors());
        System.out.println("Detected Errors: " + allErrors);

        assertFalse(allErrors.isEmpty(), "Should detect duplicate user IDs");
        assertTrue(allErrors.contains("123456789"), "Should detect duplicate user ID 123456789");
    }

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
}

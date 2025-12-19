package IntegrationTesting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import com.mycompany.swtproject.Application;
import com.mycompany.swtproject.Movie;
import com.mycompany.swtproject.MovieFilereader;
import com.mycompany.swtproject.MovieValidator;
import com.mycompany.swtproject.Recommender;
import com.mycompany.swtproject.User;
import com.mycompany.swtproject.UserFilereader;
import com.mycompany.swtproject.UserValidator;

public class BottomUp_IntegrationTest {

    private static final String TEST_DATA_PATH = "TextFile_integrationTest/";
    private static final String OUTPUT_PATH = "TextFile_integrationTest/test_bottomup_output.txt";

    @AfterEach
    public void cleanup() {
        try {
            Files.deleteIfExists(Paths.get(OUTPUT_PATH));
        } catch (IOException e) {
        }
    }

    // LAYER 1: VALIDATORS (Unit Level)
    @Test
    public void L1_TC1_MovieValidator_Valid() {
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Batman Dark Knight", "BDK123", new String[]{"Action"}));

        MovieValidator validator = new MovieValidator(movies);
        validator.Validate();

        assertTrue(validator.ErrorIsEmpty());
    }

    @Test
    public void L1_TC2_UserValidator_Valid() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("john doe", "123456789", new String[]{"M1"}));

        UserValidator validator = new UserValidator(users);
        validator.Validate();

        assertTrue(validator.ErrorIsEmpty());
    }

    //LAYER 2: FILEREADER + VALIDATOR
    @Test
    public void L2_TC1_MovieReader_Validator_ValidData() {
        MovieFilereader reader = new MovieFilereader();
        ArrayList<Movie> movies = reader.ReadMovies(TEST_DATA_PATH + "moviesSmall.txt");

        MovieValidator validator = new MovieValidator(movies);
        validator.Validate();

        assertTrue(validator.ErrorIsEmpty());
    }

    @Test
    public void L2_TC2_MovieReader_Validator_InvalidData() {
        MovieFilereader reader = new MovieFilereader();
        ArrayList<Movie> movies = reader.ReadMovies(TEST_DATA_PATH + "moviesError.txt");

        MovieValidator validator = new MovieValidator(movies);
        validator.Validate();

        assertFalse(validator.ErrorIsEmpty());
        assertTrue(validator.getMovie_errors().get(0).contains("S867"));
    }

    @Test
    public void L2_TC3_UserReader_Validator_ValidData() {
        UserFilereader reader = new UserFilereader();
        ArrayList<User> users = reader.ReadUsers(TEST_DATA_PATH + "usersSmall.txt");

        UserValidator validator = new UserValidator(users);
        validator.Validate();

        assertTrue(validator.ErrorIsEmpty());
    }

    @Test
    public void L2_TC4_UserReader_Validator_InvalidData() {
        UserFilereader reader = new UserFilereader();
        ArrayList<User> users = reader.ReadUsers(TEST_DATA_PATH + "usersError.txt");

        UserValidator validator = new UserValidator(users);
        validator.Validate();

        assertFalse(validator.ErrorIsEmpty());
        assertTrue(validator.getUser_errors().get(0).contains("1234567BA"));
    }

    // LAYER 3: FILEREADER + VALIDATOR + RECOMMENDER
    @Test
    public void L3_TC1_Recommender_RecommendsCorrectMovies() {
        MovieFilereader movieReader = new MovieFilereader();
        UserFilereader userReader = new UserFilereader();
        ArrayList<Movie> movies = movieReader.ReadMovies(TEST_DATA_PATH + "moviesRecommendationTest.txt");
        ArrayList<User> users = userReader.ReadUsers(TEST_DATA_PATH + "usersRecommendationTest.txt");

        MovieValidator mv = new MovieValidator(movies);
        UserValidator uv = new UserValidator(users);
        mv.Validate();
        uv.Validate();
        assertTrue(mv.ErrorIsEmpty() && uv.ErrorIsEmpty());

        Recommender recommender = new Recommender(movies, users);
        String recommendations = recommender.GetRecommendations_OnUser(users.get(0));

        assertTrue(recommendations.contains("Spiderman Home Coming"));
        assertFalse(recommendations.contains("Batman Dark Knight"));
        assertFalse(recommendations.contains("The Notebook"));
    }

    @Test
    public void L3_TC2_Recommender_MultipleUsers() {
        MovieFilereader movieReader = new MovieFilereader();
        UserFilereader userReader = new UserFilereader();
        ArrayList<Movie> movies = movieReader.ReadMovies(TEST_DATA_PATH + "moviesSmall.txt");
        ArrayList<User> users = userReader.ReadUsers(TEST_DATA_PATH + "usersSmall.txt");

        MovieValidator mv = new MovieValidator(movies);
        UserValidator uv = new UserValidator(users);
        mv.Validate();
        uv.Validate();

        Recommender recommender = new Recommender(movies, users);
        recommender.FindAllRecommendations();
        ArrayList<String> results = recommender.getRecommendationsResults();

        assertEquals("michael sameh,123456789", results.get(0));
        assertEquals("michael hany,12345678A", results.get(2));
        assertEquals(4, results.size());
    }

    //LAYER 4: FULL APPLICATION
    @Test
    public void L4_TC1_OutputFileWriter_WritesRecommendations() {
        MovieFilereader movieReader = new MovieFilereader();
        UserFilereader userReader = new UserFilereader();

        Application app = new Application(movieReader, userReader);
        app.RecommenderApp(
                TEST_DATA_PATH + "moviesSmall.txt",
                TEST_DATA_PATH + "usersSmall.txt",
                OUTPUT_PATH
        );

        try {
            assertTrue(Files.exists(Paths.get(OUTPUT_PATH)));
            String content = new String(Files.readAllBytes(Paths.get(OUTPUT_PATH)));

            assertTrue(content.contains("michael hany"));
            assertTrue(content.contains("michael sameh"));
            assertFalse(content.contains("ERROR"));
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void L4_TC2_OutputFileWriter_WritesError() {
        MovieFilereader movieReader = new MovieFilereader();
        UserFilereader userReader = new UserFilereader();

        Application app = new Application(movieReader, userReader);
        app.RecommenderApp(
                TEST_DATA_PATH + "moviesError.txt",
                TEST_DATA_PATH + "users.txt",
                OUTPUT_PATH
        );

        try {
            String content = new String(Files.readAllBytes(Paths.get(OUTPUT_PATH)));

            assertEquals("ERROR: Movie Id letters {S867} wrong", content.trim());
        } catch (IOException e) {
            fail();
        }
    }
}

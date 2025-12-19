package IntegrationTesting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import IntegrationTesting.stubs.MovieFilereaderStub;
import IntegrationTesting.stubs.UserFilereaderStub;

public class TopDown_IntegrationTest {

    private static final String TEST_DATA_PATH = "TextFile_integrationTest/";
    private static final String OUTPUT_PATH = "TextFile_integrationTest/test_topdown_output.txt";

    @AfterEach
    public void cleanup() {
        try {
            Files.deleteIfExists(Paths.get(OUTPUT_PATH));
        } catch (IOException e) {
        }
    }

    // ========== LAYER 1: Both FileReaders as Stubs ==========
    @Test
    public void Layer1_TestApplicationLogicWithAllStubs() {
        MovieFilereaderStub movieReaderStub = new MovieFilereaderStub();
        UserFilereaderStub userReaderStub = new UserFilereaderStub();

        Application app = new Application(movieReaderStub, userReaderStub);

        app.RecommenderApp(
                TEST_DATA_PATH + "moviesSmall.txt",
                TEST_DATA_PATH + "usersSmall.txt",
                OUTPUT_PATH
        );

        try {
            assertTrue(Files.exists(Paths.get(OUTPUT_PATH)),
                    "Layer 1: Output file should be created with stubs");
            String content = new String(Files.readAllBytes(Paths.get(OUTPUT_PATH)));
            assertNotNull(content);
            assertFalse(content.isEmpty(),
                    "Layer 1: Output file should not be empty with valid stub data");
        } catch (IOException e) {
            fail("Layer 1: Output file should exist: " + e.getMessage());
        }
    }

    // ========== LAYER 2: Replace MovieFilereader Stub ==========
    @Test
    public void Layer2_TestApplicationUserStub() {
        MovieFilereader movieReader = new MovieFilereader();
        UserFilereaderStub userReaderStub = new UserFilereaderStub();

        Application app = new Application(movieReader, userReaderStub);

        app.RecommenderApp(
                TEST_DATA_PATH + "moviesSmall.txt",
                TEST_DATA_PATH + "usersSmall.txt",
                OUTPUT_PATH
        );

        try {
            assertTrue(Files.exists(Paths.get(OUTPUT_PATH)),
                    "Layer 2: Output file should be created");
            String content = new String(Files.readAllBytes(Paths.get(OUTPUT_PATH)));
            assertNotNull(content);
            assertFalse(content.isEmpty(),
                    "Layer 2: Application should work with real MovieFilereader");
        } catch (IOException e) {
            fail("Layer 2: Output file should exist: " + e.getMessage());
        }
    }

    // ========== LAYER 3: Replace UserFilereader Stub ==========
    @Test
    public void Layer3_TestApplicationWithMovieStub() {
        MovieFilereaderStub movieReaderStub = new MovieFilereaderStub();
        UserFilereader userReader = new UserFilereader();

        Application app = new Application(movieReaderStub, userReader);

        app.RecommenderApp(
                TEST_DATA_PATH + "moviesSmall.txt",
                TEST_DATA_PATH + "usersSmall.txt",
                OUTPUT_PATH
        );

        try {
            assertTrue(Files.exists(Paths.get(OUTPUT_PATH)),
                    "Layer 3: Output file should be created");
            String content = new String(Files.readAllBytes(Paths.get(OUTPUT_PATH)));
            assertNotNull(content);
            assertFalse(content.isEmpty(),
                    "Layer 3: Application should work with real UserFilereader");
        } catch (IOException e) {
            fail("Layer 3: Output file should exist: " + e.getMessage());
        }
    }

    // ========== LAYER 4: Full Integration - Both Readers Real ==========
    @Test
    public void Layer4_TestApplicationWithBothRealReaders() {
        MovieFilereader movieReader = new MovieFilereader();
        UserFilereader userReader = new UserFilereader();

        Application app = new Application(movieReader, userReader);

        app.RecommenderApp(
                TEST_DATA_PATH + "moviesSmall.txt",
                TEST_DATA_PATH + "usersSmall.txt",
                OUTPUT_PATH
        );

        try {
            assertTrue(Files.exists(Paths.get(OUTPUT_PATH)),
                    "Layer 4: Output file should be created");
            String content = new String(Files.readAllBytes(Paths.get(OUTPUT_PATH)));
            assertNotNull(content);
            assertFalse(content.isEmpty(),
                    "Layer 4: Complete integration should generate recommendations");
            assertTrue(content.contains("michael") || content.contains("john"),
                    "Layer 4: Output should contain user recommendations");
        } catch (IOException e) {
            fail("Layer 4: Output file should exist: " + e.getMessage());
        }
    }

    // ========== LAYER 5: Validators Integration with Error Detection ==========
    @Test
    public void Layer5_TestValidatorsIntegrationWithInvalidMovieData() {
        MovieFilereader movieReader = new MovieFilereader();
        UserFilereader userReader = new UserFilereader();

        ArrayList<Movie> invalidMovies = movieReader.ReadMovies(TEST_DATA_PATH + "moviesInvalidID.txt");

        MovieValidator movieValidator = new MovieValidator(invalidMovies);
        movieValidator.Validate();

        assertTrue(movieValidator.getMovie_errors().contains("ERROR: Movie Id letters {I2} wrong"),
                "Layer 5: MovieValidator should detect errors in integrated data");

        ArrayList<User> invalidUsers = userReader.ReadUsers(TEST_DATA_PATH + "usersInvalidID.txt");
        UserValidator userValidator = new UserValidator(invalidUsers);
        userValidator.Validate();

        assertTrue(userValidator.getUser_errors().contains("ERROR: User ID {M12} is wrong"),
                "UserValidator should detect M12 error");
        assertTrue(userValidator.getUser_errors().contains("ERROR: User ID {J654321} is wrong"),
                "UserValidator should detect J654321 error");
    }

    // ========== LAYER 6: Recommender Excludes Watched Movies ==========
    @Test
    public void Layer6_TestRecommenderExcludesWatchedMovies() {
        MovieFilereader movieReader = new MovieFilereader();
        UserFilereader userReader = new UserFilereader();

        ArrayList<Movie> movies = movieReader.ReadMovies(TEST_DATA_PATH + "movies.txt");
        ArrayList<User> users = userReader.ReadUsers(TEST_DATA_PATH + "users.txt");

        Recommender recommender = new Recommender(movies, users);

        for (User user : users) {
            String recommendations = recommender.GetRecommendations_OnUser(user);

            if (recommendations != null && !recommendations.isEmpty()) {
                String[] likedMovieIds = user.getLikedMovieIds();
                ArrayList<String> likedTitles = new ArrayList<>();

                for (String likedId : likedMovieIds) {
                    for (Movie movie : movies) {
                        if (movie.getMovieId().equals(likedId)) {
                            assertFalse(recommendations.contains(movie.getMovieTitle()),
                                    "Recommendations should exclude watched movie: " + movie.getMovieTitle());
                        }
                    }
                }
                for (String likedTitle : likedTitles) {
                    assertFalse(recommendations.contains(likedTitle),
                            "Recommendations should not include already watched movies by user: " + likedTitle);
                }
            }
        }
    }

    // ========== LAYER 7: OutputFileWriter Integration ==========
    @Test
    public void Layer7_TestOutputFileWriterIntegration() {
        MovieFilereader movieReader = new MovieFilereader();
        UserFilereader userReader = new UserFilereader();
        Application app = new Application(movieReader, userReader);

        app.RecommenderApp(
                TEST_DATA_PATH + "moviesSmall.txt",
                TEST_DATA_PATH + "usersSmall.txt",
                OUTPUT_PATH
        );

        try {
            assertTrue(Files.exists(Paths.get(OUTPUT_PATH)),
                    "Layer 7: OutputFileWriter should create output file");

            String content = new String(Files.readAllBytes(Paths.get(OUTPUT_PATH)));
            assertNotNull(content, "Layer 7: Output file content should exist");
            assertFalse(content.isEmpty(), "Layer 7: Output file should not be empty");

        } catch (IOException e) {
            fail("Layer 7: Output file should be readable: " + e.getMessage());
        }
    }

    // ========== LAYER 7B: OutputFileWriter with Error Messages ==========
    @Test
    public void Layer7B_TestOutputFileWriterIntegrationWithErrors() {
        MovieFilereader movieReader = new MovieFilereader();
        UserFilereader userReader = new UserFilereader();
        Application app = new Application(movieReader, userReader);

        app.RecommenderApp(
                TEST_DATA_PATH + "moviesError.txt",
                TEST_DATA_PATH + "users.txt",
                OUTPUT_PATH
        );

        try {
            assertTrue(Files.exists(Paths.get(OUTPUT_PATH)),
                    "Layer 7B: OutputFileWriter should create error output file");

            String content = new String(Files.readAllBytes(Paths.get(OUTPUT_PATH)));
            assertNotNull(content, "Layer 7B: Output file content should exist");
            assertTrue(content.contains("ERROR"),
                    "Layer 7B: OutputFileWriter should write error messages to file");
        } catch (IOException e) {
            fail("Layer 7B: Output file should exist: " + e.getMessage());
        }
    }
}

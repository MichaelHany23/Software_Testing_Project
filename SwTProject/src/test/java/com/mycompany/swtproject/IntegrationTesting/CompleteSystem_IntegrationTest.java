/*
 * Complete Integration Test Suite for Movie Recommendation System
 * Tests using TextFile_integrationTest data files
 */
package com.mycompany.swtproject.IntegrationTesting;

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

public class CompleteSystem_IntegrationTest {

    private static final String TEST_DATA_PATH = "TextFile_integrationTest/";
    private static final String OUTPUT_PATH = "TextFile_integrationTest/test_integration_output.txt";

    @AfterEach
    public void cleanup() {
        try {
            Files.deleteIfExists(Paths.get(OUTPUT_PATH));
        } catch (IOException e) {
        }
    }

    @Test
    public void TestingFullValidWorkflow() {
        MovieFilereader movieReader = new MovieFilereader();
        UserFilereader userReader = new UserFilereader();
        Application app = new Application(
                movieReader,
                userReader
        );

        app.RecommenderApp(
                TEST_DATA_PATH + "movies.txt",
                TEST_DATA_PATH + "usersSmall.txt",
                OUTPUT_PATH
        );

        try {
            assertTrue(Files.exists(Paths.get(OUTPUT_PATH)), "Output file should be created");
            String content = new String(Files.readAllBytes(Paths.get(OUTPUT_PATH)));
            assertNotNull(content);
            assertFalse(content.isEmpty(), "Output file should not be empty");
            assertTrue(content.contains("michael hany") && content.contains("michael sameh"),
                    "File should contain both user names");

            String[] lines = content.split("\n");
            for (int i = 0; i < lines.length - 1; i++) {
                if (lines[i].contains("michael hany")) {
                    assertTrue(lines[i + 1].contains("Batman Dark Knight,Inception,Interstellar,The Matrix,The Godfather,The Avengers,Joker,Tenet,Harry Potter And The Sorcerer's Stone,The Lion King,Finding Nemo,Avatar"),
                            "Recommendations for michael hany should match expected");
                }
                if (lines[i].contains("michael sameh")) {
                    assertTrue(lines[i + 1].contains("Interstellar,Shutter Island,The Avengers,Joker,Spider-Man No Way Home,Harry Potter And The Sorcerer's Stone,Finding Nemo,Toy Story,Avatar"),
                            "Recommendations for michael sameh should match expected");
                }
            }
        } catch (IOException e) {
            fail("Output file should exist and be readable: " + e.getMessage());
        }
    }

    @Test
    public void TestingInvalidMovies() {
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
            assertTrue(content.contains("ERROR: Movie Id letters {S867} wrong"), "Output should contain error message");
        } catch (IOException e) {
            fail("Output file should exist: " + e.getMessage());
        }
    }

    @Test
    public void TestingInvalidUsers() {
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
            assertTrue(content.contains("ERROR: User ID {1234567BA} is wrong"), "Output should contain error message");

        } catch (IOException e) {
            fail("Output file should exist: " + e.getMessage());
        }
    }

    @Test
    public void TestingInvalidMovieIDFormat() {
        Application app = new Application(
                new MovieFilereader(),
                new UserFilereader()
        );

        app.RecommenderApp(
                TEST_DATA_PATH + "moviesInvalidID.txt",
                TEST_DATA_PATH + "users.txt",
                OUTPUT_PATH
        );

        try {
            String content = new String(Files.readAllBytes(Paths.get(OUTPUT_PATH)));
            assertTrue(content.contains("ERROR: Movie Id letters {I2} wrong"),
                    "Invalid movie ID should trigger error");
        } catch (IOException e) {
            fail("Output file should exist: " + e.getMessage());
        }
    }

    @Test
    public void TestingInvalidMovieTitleFormat() {
        Application app = new Application(
                new MovieFilereader(),
                new UserFilereader()
        );

        app.RecommenderApp(
                TEST_DATA_PATH + "moviesInvalidTitle.txt",
                TEST_DATA_PATH + "users.txt",
                OUTPUT_PATH
        );

        try {
            String content = new String(Files.readAllBytes(Paths.get(OUTPUT_PATH)));
            assertTrue(content.contains("ERROR: Movie Title {inception} wrong"),
                    "Invalid movie title should trigger error");

        } catch (IOException e) {
            fail("Output file should exist: " + e.getMessage());
        }
    }

    @Test
    public void TestingInvalidUserNameFormat() {
        Application app = new Application(
                new MovieFilereader(),
                new UserFilereader()
        );

        app.RecommenderApp(
                TEST_DATA_PATH + "movies.txt",
                TEST_DATA_PATH + "usersInvalidName.txt",
                OUTPUT_PATH
        );

        try {
            String content = new String(Files.readAllBytes(Paths.get(OUTPUT_PATH)));
            assertTrue(content.contains("ERROR: User Name {michael123} is wrong"),
                    "Invalid user name should trigger error");
        } catch (IOException e) {
            fail("Output file should exist: " + e.getMessage());
        }
    }

    @Test
    public void TestingInvalidUserIDFormat() {
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
            assertTrue(content.contains("ERROR: User ID {M12} is wrong"),
                    "Invalid user ID should trigger error");
        } catch (IOException e) {
            fail("Output file should exist: " + e.getMessage());
        }
    }

    @Test
    public void TestingFullDatasetGenreMatching() {
        MovieFilereader movieReader = new MovieFilereader();
        UserFilereader userReader = new UserFilereader();

        ArrayList<Movie> movies = movieReader.ReadMovies(TEST_DATA_PATH + "movies.txt");
        ArrayList<User> users = userReader.ReadUsers(TEST_DATA_PATH + "users.txt");

        MovieValidator movieValidator = new MovieValidator(movies);
        UserValidator userValidator = new UserValidator(users);

        movieValidator.Validate();
        userValidator.Validate();

        assertTrue(movieValidator.ErrorIsEmpty(), "Movies should be valid");
        assertTrue(userValidator.ErrorIsEmpty(), "Users should be valid");

        Recommender recommender = new Recommender(movies, users);
        recommender.FindAllRecommendations();

        ArrayList<String> results = recommender.getRecommendationsResults();
        assertFalse(results.isEmpty(), "Should generate recommendations");

        for (User user : users) {
            String userRecs = recommender.GetRecommendations_OnUser(user);
            assertNotNull(userRecs, "Each user should have recommendation list");
        }
    }

    @Test
    public void TestingExcludingWatchedMovies() {
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
                            likedTitles.add(movie.getMovieTitle());
                        }
                    }
                }
                for (String likedTitle : likedTitles) {
                    assertFalse(recommendations.contains(likedTitle),
                            "Recommendations should not include already watched movies");
                }
            }
        }
    }
}

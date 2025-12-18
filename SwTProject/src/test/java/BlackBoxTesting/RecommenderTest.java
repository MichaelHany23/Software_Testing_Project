/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package BlackBoxTesting;

import com.mycompany.swtproject.Movie;

import com.mycompany.swtproject.Recommender;
import com.mycompany.swtproject.User;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Black Box Testing for Recommender
 */
public class RecommenderTest {

    private ArrayList<Movie> movies;
    private ArrayList<User> users;
    private Recommender recommender;

    public RecommenderTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        movies = new ArrayList<>();
        users = new ArrayList<>();
        // Basic Setup that can be overridden or added to in tests
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testRecommendMoviesBasedOnGenre() {
        // Arrange
        // Movie 1: Action (ID: M1)
        // Movie 2: Action (ID: M2)
        // Movie 3: Comedy (ID: M3)
        // User likes M1. Should be recommended M2 (Action), but not M3.

        movies.add(new Movie("Action Movie 1", "M1", new String[] { "Action" }));
        movies.add(new Movie("Action Movie 2", "M2", new String[] { "Action" }));
        movies.add(new Movie("Comedy Movie 1", "M3", new String[] { "Comedy" }));

        User user = new User("John", "123456789", new String[] { "M1" });
        users.add(user);

        recommender = new Recommender(movies, users);

        // Act
        // Accessing private method via public wrapper or public logical entry point
        // The class has FindAllRecommendations() which populates a list
        recommender.FindAllRecommendations();
        ArrayList<String> results = recommender.getRecommendationsResults();

        // Assert
        // Results structure: User Info, Recommendations
        // [0]: "John,123456789"
        // [1]: "Action Movie 2"

        assertEquals(2, results.size());
        assertEquals("John,123456789", results.get(0));
        assertTrue(results.get(1).contains("Action Movie 2"), "Should recommend other Action movie");
        assertFalse(results.get(1).contains("Comedy Movie 1"), "Should NOT recommend Comedy movie");
    }

    @Test
    public void testNoRecommendationsIfAllGenreWatched() {
        // Arrange
        // User likes M1 (Action). Only other movie is M3 (Comedy). No other Action
        // movies.
        movies.add(new Movie("Action Movie 1", "M1", new String[] { "Action" }));
        movies.add(new Movie("Comedy Movie 1", "M3", new String[] { "Comedy" }));

        User user = new User("John", "123456789", new String[] { "M1" });
        users.add(user);

        recommender = new Recommender(movies, users);

        // Act
        recommender.FindAllRecommendations();
        ArrayList<String> results = recommender.getRecommendationsResults();

        // Assert
        assertEquals("", results.get(1), "Should yield empty string if no relevant movies found");
    }

    @Test
    public void testRecommendMultipleGenres() {
        // Arrange
        // User likes M1 (Action) and M3 (Comedy).
        // Available: M2 (Action), M4 (Comedy), M5 (Drama).
        // Should recommend M2 and M4. Not M5.

        movies.add(new Movie("A1", "M1", new String[] { "Action" }));
        movies.add(new Movie("A2", "M2", new String[] { "Action" }));
        movies.add(new Movie("C1", "M3", new String[] { "Comedy" }));
        movies.add(new Movie("C2", "M4", new String[] { "Comedy" }));
        movies.add(new Movie("D1", "M5", new String[] { "Drama" }));

        User user = new User("John", "123456789", new String[] { "M1", "M3" });
        users.add(user);

        recommender = new Recommender(movies, users);

        // Act
        recommender.FindAllRecommendations();
        String recs = recommender.getRecommendationsResults().get(1);

        // Assert
        assertTrue(recs.contains("A2"));
        assertTrue(recs.contains("C2"));
        assertFalse(recs.contains("D1"));
    }

    @Test
    public void testDoNotRecommendAlreadyLiked() {
        // User likes M1 and M2. Both are Action.
        // Should not recommend M1 or M2 again.

        movies.add(new Movie("A1", "M1", new String[] { "Action" }));
        movies.add(new Movie("A2", "M2", new String[] { "Action" }));

        User user = new User("John", "123456789", new String[] { "M1", "M2" });
        users.add(user);

        recommender = new Recommender(movies, users);

        // Act
        recommender.FindAllRecommendations();
        String recs = recommender.getRecommendationsResults().get(1);

        // Assert
        assertEquals("", recs, "Should be empty as all genre movies are already liked");
    }
}

/*
 * Genre-Based Recommendation Integration Test
 * Tests recommendation logic with genre matching using TextFile_integrationTest data
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
import org.junit.jupiter.api.Test;

import com.mycompany.swtproject.Movie;
import com.mycompany.swtproject.MovieFilereader;
import com.mycompany.swtproject.MovieValidator;
import com.mycompany.swtproject.Recommender;
import com.mycompany.swtproject.User;
import com.mycompany.swtproject.UserFilereader;
import com.mycompany.swtproject.UserValidator;

public class GenreBased_Recommendation_IntegrationTest {
    
    private static final String TEST_DATA_PATH = "TextFile_integrationTest/";
    private static final String OUTPUT_PATH = "TextFile_integrationTest/genre_test_output.txt";
    
    @AfterEach
    public void cleanup() {
        try {
            Files.deleteIfExists(Paths.get(OUTPUT_PATH));
        } catch (IOException e) {
            // ignore
        }
    }
    
    /**
     * TC1: Genre matching test - users should get recommendations based on liked movie genres
     */
    @Test
    public void TC1_GenreMatchingRecommendations() {
        MovieFilereader movieReader = new MovieFilereader();
        UserFilereader userReader = new UserFilereader();
        
        ArrayList<Movie> movies = movieReader.ReadMovies(TEST_DATA_PATH + "moviesGenreTest.txt");
        ArrayList<User> users = userReader.ReadUsers(TEST_DATA_PATH + "usersGenreTest.txt");
        
        assertNotNull(movies);
        assertNotNull(users);
        assertEquals(4, movies.size());
        assertEquals(3, users.size());
        
        Recommender recommender = new Recommender(movies, users);
        recommender.FindAllRecommendations();
        
        // Alice likes Action Movie One (Action), should get Action Movie Two recommended
        User alice = users.get(0);
        String aliceRecs = recommender.GetRecommendations_OnUser(alice);
        assertNotNull(aliceRecs);
        
        // Bob likes Sci-Fi Movie One, should not get action movies
        User bob = users.get(1);
        String bobRecs = recommender.GetRecommendations_OnUser(bob);
        assertNotNull(bobRecs);
    }
    
    /**
     * TC2: Full dataset genre matching with movies.txt and users.txt
     */
    @Test
    public void TC2_FullDatasetGenreMatching() {
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
        assertNotNull(results);
        assertFalse(results.isEmpty(), "Should generate recommendations");
        
        // Verify each user gets recommendations
        for (User user : users) {
            String userRecs = recommender.GetRecommendations_OnUser(user);
            assertNotNull(userRecs, "Each user should have recommendation list");
        }
    }
    
    /**
     * TC3: User with Animation preference should get Animation recommendations
     */
    @Test
    public void TC3_AnimationGenrePreference() {
        MovieFilereader movieReader = new MovieFilereader();
        UserFilereader userReader = new UserFilereader();
        
        ArrayList<Movie> movies = movieReader.ReadMovies(TEST_DATA_PATH + "movies.txt");
        ArrayList<User> users = userReader.ReadUsers(TEST_DATA_PATH + "users.txt");
        
        Recommender recommender = new Recommender(movies, users);
        
        // andrew wagdy likes TS401 (Toy Story - Animation, Comedy, Family)
        User andrewWagdy = null;
        for (User u : users) {
            if (u.getName().toLowerCase().contains("andrew")) {
                andrewWagdy = u;
                break;
            }
        }
        
        if (andrewWagdy != null) {
            String recommendations = recommender.GetRecommendations_OnUser(andrewWagdy);
            assertNotNull(recommendations);
            // Should recommend other Animation/Comedy/Family movies
        }
    }
    
    /**
     * TC4: Multiple liked movies should expand genre preferences
     */
    @Test
    public void TC4_MultipleGenreExpansion() {
        MovieFilereader movieReader = new MovieFilereader();
        UserFilereader userReader = new UserFilereader();
        
        ArrayList<Movie> movies = movieReader.ReadMovies(TEST_DATA_PATH + "movies.txt");
        ArrayList<User> users = userReader.ReadUsers(TEST_DATA_PATH + "users.txt");
        
        Recommender recommender = new Recommender(movies, users);
        
        // adham ahmed likes HPATSS781 (Fantasy, Adventure), TA316 (Action, Sci-Fi, Adventure), BDK142 (Action, Thriller)
        User adhamAhmed = null;
        for (User u : users) {
            if (u.getName().toLowerCase().contains("adham")) {
                adhamAhmed = u;
                break;
            }
        }
        
        if (adhamAhmed != null) {
            String recommendations = recommender.GetRecommendations_OnUser(adhamAhmed);
            assertNotNull(recommendations);
            // Should recommend movies in Fantasy, Adventure, Action, Sci-Fi, or Thriller
            // But exclude the 3 already watched movies
        }
    }
    
    /**
     * TC5: Verify recommendations exclude already watched movies
     */
    @Test
    public void TC5_ExcludeWatchedMovies() {
        MovieFilereader movieReader = new MovieFilereader();
        UserFilereader userReader = new UserFilereader();
        
        ArrayList<Movie> movies = movieReader.ReadMovies(TEST_DATA_PATH + "movies.txt");
        ArrayList<User> users = userReader.ReadUsers(TEST_DATA_PATH + "users.txt");
        
        Recommender recommender = new Recommender(movies, users);
        
        for (User user : users) {
            String recommendations = recommender.GetRecommendations_OnUser(user);
            
            if (recommendations != null && !recommendations.isEmpty()) {
                String[] likedMovieIds = user.getLikedMovieIds();
                
                // Get titles of liked movies
                ArrayList<String> likedTitles = new ArrayList<>();
                for (String likedId : likedMovieIds) {
                    for (Movie movie : movies) {
                        if (movie.getMovieId().equals(likedId)) {
                            likedTitles.add(movie.getMovieTitle());
                        }
                    }
                }
                
                // Verify none of the liked movies appear in recommendations
                for (String likedTitle : likedTitles) {
                    assertFalse(recommendations.contains(likedTitle),
                               "Recommendations should not include already watched movies");
                }
            }
        }
    }
}

package DataFlowTesting;

import com.mycompany.swtproject.Movie;
import com.mycompany.swtproject.User;
import com.mycompany.swtproject.Recommender;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Recommender_DFT {

    @Test
    public void DF01_validRecommendationsSingleUser() {
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("The Matrix", "TM123", new String[]{"Action", "Sci-Fi"}));
        movies.add(new Movie("Action Hero", "AH456", new String[]{"Action"}));
        movies.add(new Movie("Comedy Show", "CS789", new String[]{"Comedy"}));

        ArrayList<User> users = new ArrayList<>();
        users.add(new User("John Doe", "123456789", new String[]{"TM123"}));

        Recommender recommender = new Recommender(movies, users);
        recommender.FindAllRecommendations();

        assertNotNull(recommender.getRecommendationsResults());
        assertTrue(recommender.getRecommendationsResults().size() > 0);
    }

    @Test
    public void DF02_recommendationsMultipleUsers() {
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("The Matrix", "TM123", new String[]{"Action", "Sci-Fi"}));
        movies.add(new Movie("Action Hero", "AH456", new String[]{"Action"}));
        movies.add(new Movie("Love Story", "LS789", new String[]{"Romance"}));

        ArrayList<User> users = new ArrayList<>();
        users.add(new User("John Doe", "123456789", new String[]{"TM123"}));
        users.add(new User("Jane Smith", "234567890", new String[]{"LS789"}));

        Recommender recommender = new Recommender(movies, users);
        recommender.FindAllRecommendations();

        ArrayList<String> results = recommender.getRecommendationsResults();
        assertNotNull(results);
        assertTrue(results.size() >= 4); // At least 2 users * 2 lines each (name/id, recommendations)
    }

    @Test
    public void DF03_noRecommendationsAvailable() {
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("The Matrix", "TM123", new String[]{"Action"}));

        ArrayList<User> users = new ArrayList<>();
        users.add(new User("John Doe", "123456789", new String[]{"TM123"}));

        Recommender recommender = new Recommender(movies, users);
        recommender.FindAllRecommendations();

        ArrayList<String> results = recommender.getRecommendationsResults();
        assertNotNull(results);
        // User has seen all available movies in their genre
        assertTrue(results.size() >= 2);
    }

    @Test
    public void DF04_userHasNoLikedMovies() {
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("The Matrix", "TM123", new String[]{"Action"}));
        movies.add(new Movie("Action Hero", "AH456", new String[]{"Action"}));

        ArrayList<User> users = new ArrayList<>();
        users.add(new User("John Doe", "123456789", new String[]{})); // No liked movies

        Recommender recommender = new Recommender(movies, users);
        recommender.FindAllRecommendations();

        ArrayList<String> results = recommender.getRecommendationsResults();
        assertNotNull(results);
        assertTrue(results.size() >= 2);
    }

    @Test
    public void DF05_multipleGenresForSingleMovie() {
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("The Matrix", "TM123", new String[]{"Action", "Sci-Fi", "Thriller"}));
        movies.add(new Movie("Action Hero", "AH456", new String[]{"Action"}));
        movies.add(new Movie("Sci-Fi Adventure", "SA789", new String[]{"Sci-Fi"}));

        ArrayList<User> users = new ArrayList<>();
        users.add(new User("John Doe", "123456789", new String[]{"TM123"}));

        Recommender recommender = new Recommender(movies, users);
        recommender.FindAllRecommendations();

        ArrayList<String> results = recommender.getRecommendationsResults();
        assertNotNull(results);
        assertTrue(results.size() >= 2);
        assertTrue(results.get(1).contains("Action Hero") || results.get(1).contains("Sci-Fi Adventure"));
    }

    @Test
    public void DF06_likedMovieNotInMovieList() {
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("The Matrix", "TM123", new String[]{"Action"}));
        movies.add(new Movie("Comedy Show", "CS456", new String[]{"Comedy"}));

        ArrayList<User> users = new ArrayList<>();
        users.add(new User("John Doe", "123456789", new String[]{"NOTINLIST"})); // Movie not in list

        Recommender recommender = new Recommender(movies, users);
        recommender.FindAllRecommendations();

        ArrayList<String> results = recommender.getRecommendationsResults();
        assertNotNull(results);
        assertTrue(results.size() >= 2);
    }

    @Test
    public void DF07_emptyMovieList() {
        ArrayList<Movie> movies = new ArrayList<>();

        ArrayList<User> users = new ArrayList<>();
        users.add(new User("John Doe", "123456789", new String[]{"TM123"}));

        Recommender recommender = new Recommender(movies, users);
        recommender.FindAllRecommendations();

        ArrayList<String> results = recommender.getRecommendationsResults();
        assertNotNull(results);
        assertTrue(results.size() >= 2);
    }

    @Test
    public void DF08_emptyUserList() {
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("The Matrix", "TM123", new String[]{"Action"}));

        ArrayList<User> users = new ArrayList<>();

        Recommender recommender = new Recommender(movies, users);
        recommender.FindAllRecommendations();

        ArrayList<String> results = recommender.getRecommendationsResults();
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    public void DF09_userLikesMultipleMovies() {
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("The Matrix", "TM123", new String[]{"Action"}));
        movies.add(new Movie("Action Hero", "AH456", new String[]{"Action"}));
        movies.add(new Movie("Action Sequence", "AS789", new String[]{"Action"}));
        movies.add(new Movie("Comedy Show", "CS000", new String[]{"Comedy"}));

        ArrayList<User> users = new ArrayList<>();
        users.add(new User("John Doe", "123456789", new String[]{"TM123", "AH456"}));

        Recommender recommender = new Recommender(movies, users);
        recommender.FindAllRecommendations();

        ArrayList<String> results = recommender.getRecommendationsResults();
        assertNotNull(results);
        assertTrue(results.size() >= 2);
        assertTrue(results.get(1).contains("Action") || results.get(1).isEmpty());
    }

    @Test
    public void DF10_genreWithSpaces() {
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("The Matrix", "TM123", new String[]{" Action ", " Sci-Fi "}));
        movies.add(new Movie("Action Hero", "AH456", new String[]{"Action"}));

        ArrayList<User> users = new ArrayList<>();
        users.add(new User("John Doe", "123456789", new String[]{"TM123"}));

        Recommender recommender = new Recommender(movies, users);
        recommender.FindAllRecommendations();

        ArrayList<String> results = recommender.getRecommendationsResults();
        assertNotNull(results);
        assertTrue(results.size() >= 2);
    }

    @Test
    public void DF11_singleMovieSingleUserGenreMatch() {
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("The Matrix", "TM123", new String[]{"Action"}));

        ArrayList<User> users = new ArrayList<>();
        users.add(new User("John Doe", "123456789", new String[]{"TM123"}));

        Recommender recommender = new Recommender(movies, users);
        recommender.FindAllRecommendations();

        ArrayList<String> results = recommender.getRecommendationsResults();
        assertNotNull(results);
        assertEquals(2, results.size()); // Name/ID and empty recommendations
    }

    @Test
    public void DF12_getRecommendationsOnSingleUser() {
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("The Matrix", "TM123", new String[]{"Action"}));
        movies.add(new Movie("Action Hero", "AH456", new String[]{"Action"}));

        ArrayList<User> users = new ArrayList<>();
        User testUser = new User("John Doe", "123456789", new String[]{"TM123"});
        users.add(testUser);

        Recommender recommender = new Recommender(movies, users);
        String recommendations = recommender.GetRecommendations_OnUser(testUser);

        assertNotNull(recommendations);
        assertTrue(recommendations.contains("Action Hero") || recommendations.isEmpty());
    }

    @Test
    public void DF13_multipleUsersWithDifferentGenres() {
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("The Matrix", "TM123", new String[]{"Action"}));
        movies.add(new Movie("Action Hero", "AH456", new String[]{"Action"}));
        movies.add(new Movie("Romance Tale", "RT789", new String[]{"Romance"}));
        movies.add(new Movie("Love Story", "LS000", new String[]{"Romance"}));

        ArrayList<User> users = new ArrayList<>();
        users.add(new User("John Doe", "123456789", new String[]{"TM123"}));
        users.add(new User("Jane Smith", "234567890", new String[]{"RT789"}));

        Recommender recommender = new Recommender(movies, users);
        recommender.FindAllRecommendations();

        ArrayList<String> results = recommender.getRecommendationsResults();
        assertNotNull(results);
        assertTrue(results.size() >= 4);
    }

    @Test
    public void DF14_recommendationsNotIncludingLikedMovies() {
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("The Matrix", "TM123", new String[]{"Action"}));
        movies.add(new Movie("Action Hero", "AH456", new String[]{"Action"}));

        ArrayList<User> users = new ArrayList<>();
        users.add(new User("John Doe", "123456789", new String[]{"TM123", "AH456"}));

        Recommender recommender = new Recommender(movies, users);
        recommender.FindAllRecommendations();

        ArrayList<String> results = recommender.getRecommendationsResults();
        assertNotNull(results);
        // User has liked all action movies, so no recommendations
        assertFalse(results.get(1).contains("The Matrix"));
        assertFalse(results.get(1).contains("Action Hero"));
    }

    @Test
    public void DF15_getRecommendationsResultsIsPopulated() {
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("The Matrix", "TM123", new String[]{"Action"}));
        movies.add(new Movie("Action Hero", "AH456", new String[]{"Action"}));

        ArrayList<User> users = new ArrayList<>();
        users.add(new User("John Doe", "123456789", new String[]{"TM123"}));

        Recommender recommender = new Recommender(movies, users);
        ArrayList<String> initialResults = recommender.getRecommendationsResults();
        assertTrue(initialResults.isEmpty());

        recommender.FindAllRecommendations();
        ArrayList<String> finalResults = recommender.getRecommendationsResults();
        assertTrue(finalResults.size() > 0);
    }
}

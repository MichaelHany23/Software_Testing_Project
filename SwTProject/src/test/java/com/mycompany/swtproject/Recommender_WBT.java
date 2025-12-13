/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.swtproject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

/**
 * White Box Testing for Recommender
 * 
 * This test class focuses on INTERNAL code structure and execution paths:
 * - Statement Coverage: Every line of code is executed at least once
 * - Branch Coverage: All TRUE and FALSE branches of conditional statements
 * - Condition Coverage: Different combinations of boolean conditions
 * - Loop Coverage: Boundary conditions and iterations in loops
 * 
 * The Recommender algorithm works by:
 * 1. For each user, collect genres of movies they liked
 * 2. Find movies in those genres that the user hasn't seen
 * 3. Return list of recommended movie titles
 * 
 * @author Michael
 */
public class Recommender_WBT {
    
    public Recommender_WBT() {
    }
    
    // ============== STATEMENT COVERAGE ==============
    // Statement Coverage ensures EVERY statement in the code executes at least once.
    
    @Test
    public void StatementCov_1()
    {
        // TEST PURPOSE: Basic recommendation scenario - user likes one movie, similar genre exists
        // COVERS: All statements execute in happy path (no null/empty conditions)
        // INPUT: 1 user liking 1 movie, 1 similar genre movie available
        // EXPECTED: User gets recommended the similar movie
        
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User("Alice", "123456789", new String[]{"M1"});
        users.add(u1);
        
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Movie1", "M1", new String[]{"Drama"}));
        movies.add(new Movie("Movie2", "M2", new String[]{"Drama"}));
        
        Recommender R = new Recommender(movies, users);
        R.FindAllRecommendations();
        ArrayList<String> results = R.getRecommendationsResults();
        
        assertEquals(2, results.size());
        assertEquals("Alice,123456789", results.get(0));
        assertEquals("Movie2", results.get(1));
    }
    
    @Test
    public void StatementCov_2()
    {
        // TEST PURPOSE: User has no liked movies
        // COVERS: Loop executes but no genres are collected (empty likedGenres)
        // INPUT: User with empty liked movies array
        // EXPECTED: No recommendations returned (empty string)
        
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User("Bob", "234567890", new String[]{});  // No liked movies
        users.add(u1);
        
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Movie1", "M1", new String[]{"Drama"}));
        movies.add(new Movie("Movie2", "M2", new String[]{"Drama"}));
        
        Recommender R = new Recommender(movies, users);
        R.FindAllRecommendations();
        ArrayList<String> results = R.getRecommendationsResults();
        
        assertEquals(2, results.size());
        assertEquals("Bob,234567890", results.get(0));
        assertEquals("", results.get(1));  // No recommendations
    }
    
    @Test
    public void StatementCov_3()
    {
        // TEST PURPOSE: User already watched all movies
        // COVERS: Loop checks all movies but all are in likedMovieIds
        // INPUT: User has liked all available movies
        // EXPECTED: No recommendations (all movies excluded)
        
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User("Charlie", "345678901", new String[]{"M1", "M2"});
        users.add(u1);
        
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Movie1", "M1", new String[]{"Drama"}));
        movies.add(new Movie("Movie2", "M2", new String[]{"Drama"}));
        
        Recommender R = new Recommender(movies, users);
        R.FindAllRecommendations();
        ArrayList<String> results = R.getRecommendationsResults();
        
        assertEquals(2, results.size());
        assertEquals("Charlie,345678901", results.get(0));
        assertEquals("", results.get(1));  // No new movies to recommend
    }
    
    @Test
    public void StatementCov_4()
    {
        // TEST PURPOSE: No movies available in liked genres
        // COVERS: likedGenres is populated but no movies match those genres
        // INPUT: User likes movies with genre "Drama", but only "Action" movies available
        // EXPECTED: Empty recommendation
        
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User("Diana", "456789012", new String[]{"M1"});
        users.add(u1);
        
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Movie1", "M1", new String[]{"Drama"}));
        movies.add(new Movie("Movie2", "M2", new String[]{"Action"}));
        movies.add(new Movie("Movie3", "M3", new String[]{"Comedy"}));
        
        Recommender R = new Recommender(movies, users);
        R.FindAllRecommendations();
        ArrayList<String> results = R.getRecommendationsResults();
        
        assertEquals(2, results.size());
        assertEquals("Diana,456789012", results.get(0));
        assertEquals("", results.get(1));  // No drama movies to recommend
    }
    
    @Test
    public void StatementCov_5()
    {
        // TEST PURPOSE: Multiple users with different preferences
        // COVERS: Outer loop executes multiple times with different data
        // INPUT: 2 users with different liked movies
        // EXPECTED: Each user gets personalized recommendations
        
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("Eve", "567890123", new String[]{"M1"}));
        users.add(new User("Frank", "678901234", new String[]{"M2"}));
        
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Movie1", "M1", new String[]{"Drama"}));
        movies.add(new Movie("Movie2", "M2", new String[]{"Action"}));
        movies.add(new Movie("Movie3", "M3", new String[]{"Drama"}));
        movies.add(new Movie("Movie4", "M4", new String[]{"Action"}));
        
        Recommender R = new Recommender(movies, users);
        R.FindAllRecommendations();
        ArrayList<String> results = R.getRecommendationsResults();
        
        assertEquals(4, results.size());
        assertEquals("Eve,567890123", results.get(0));
        assertEquals("Movie3", results.get(1));  // Drama recommendation
        assertEquals("Frank,678901234", results.get(2));
        assertEquals("Movie4", results.get(3));  // Action recommendation
    }
    
    @Test
    public void StatementCov_6()
    {
        // TEST PURPOSE: Movie with multiple genres, user matches one
        // COVERS: Inner loop for genres - break statement executes
        // INPUT: User likes "Action" movie, movie with "Action,Drama" genres available
        // EXPECTED: Movie is recommended (matches on first matching genre)
        
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("Grace", "789012345", new String[]{"M1"}));
        
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Action Movie", "M1", new String[]{"Action"}));
        movies.add(new Movie("Action Drama", "M2", new String[]{"Action", "Drama"}));
        
        Recommender R = new Recommender(movies, users);
        R.FindAllRecommendations();
        ArrayList<String> results = R.getRecommendationsResults();
        
        assertEquals(2, results.size());
        assertEquals("Grace,789012345", results.get(0));
        assertEquals("Action Drama", results.get(1));
    }
    
    @Test
    public void StatementCov_7()
    {
        // TEST PURPOSE: Trailing comma removal in recommendations
        // COVERS: StringBuilder trim logic - multiple recommendations then comma removal
        // INPUT: User with 1 liked movie, 3 movies in same genre available
        // EXPECTED: All 3 recommended without trailing comma
        
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("Henry", "890123456", new String[]{"M1"}));
        
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Liked", "M1", new String[]{"Drama"}));
        movies.add(new Movie("Rec1", "M2", new String[]{"Drama"}));
        movies.add(new Movie("Rec2", "M3", new String[]{"Drama"}));
        movies.add(new Movie("Rec3", "M4", new String[]{"Drama"}));
        
        Recommender R = new Recommender(movies, users);
        R.FindAllRecommendations();
        ArrayList<String> results = R.getRecommendationsResults();
        
        assertEquals(2, results.size());
        String recommendations = results.get(1);
        assertFalse(recommendations.endsWith(","), "Recommendations should not end with comma");
        assertTrue(recommendations.contains("Rec1,Rec2,Rec3"));
    }
    
    // ============== BRANCH COVERAGE ==============
    // Branch Coverage tests both TRUE and FALSE paths of every if/else decision
    
    @Test
    public void BranchCov_1()
    {
        // TEST PURPOSE: Empty users list - outer loop skipped
        // CODE PATH: for (int i = 0; i < users.size(); i++)
        // INPUT: No users
        // COVERS: Loop condition FALSE - loop body never executes
        // EXPECTED: Empty recommendations list
        
        ArrayList<User> users = new ArrayList<>();
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Movie1", "M1", new String[]{"Drama"}));
        
        Recommender R = new Recommender(movies, users);
        R.FindAllRecommendations();
        ArrayList<String> results = R.getRecommendationsResults();
        
        assertEquals(0, results.size());
    }
    
    @Test
    public void BranchCov_2()
    {
        // TEST PURPOSE: Empty movies list
        // CODE PATH: Inner loops that iterate through movies
        // INPUT: User exists but no movies available
        // COVERS: Loops execute but nothing matches
        // EXPECTED: Empty recommendations
        
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("Ivy", "901234567", new String[]{"M1"}));
        ArrayList<Movie> movies = new ArrayList<>();
        
        Recommender R = new Recommender(movies, users);
        R.FindAllRecommendations();
        ArrayList<String> results = R.getRecommendationsResults();
        
        assertEquals(2, results.size());
        assertEquals("Ivy,901234567", results.get(0));
        assertEquals("", results.get(1));
    }
    
    @Test
    public void BranchCov_3()
    {
        // TEST PURPOSE: TRUE branch - likedMovieIds.contains() returns true
        // CODE PATH: if (likedMovieIds.contains(movie.getMovieId()))
        // INPUT: Movie matches user's liked IDs
        // COVERS: Enters first if to collect genres
        // EXPECTED: Genres are collected for recommendation logic
        
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("Jack", "012345678", new String[]{"M1"}));
        
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Liked", "M1", new String[]{"Drama", "Thriller"}));
        movies.add(new Movie("Recommend", "M2", new String[]{"Drama"}));
        
        Recommender R = new Recommender(movies, users);
        R.FindAllRecommendations();
        ArrayList<String> results = R.getRecommendationsResults();
        
        assertEquals(2, results.size());
        assertEquals("Recommend", results.get(1));
    }
    
    @Test
    public void BranchCov_4()
    {
        // TEST PURPOSE: FALSE branch - likedMovieIds.contains() returns false
        // CODE PATH: if (!likedMovieIds.contains(movie.getMovieId()))
        // INPUT: Movie not in user's liked list
        // COVERS: Checks genres for recommendation
        // EXPECTED: Movie can be recommended if genre matches
        
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("Karen", "123456780", new String[]{"M1"}));
        
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Liked", "M1", new String[]{"Drama"}));
        movies.add(new Movie("NotLiked", "M2", new String[]{"Comedy"}));
        movies.add(new Movie("Recommend", "M3", new String[]{"Drama"}));
        
        Recommender R = new Recommender(movies, users);
        R.FindAllRecommendations();
        ArrayList<String> results = R.getRecommendationsResults();
        
        assertEquals(2, results.size());
        assertEquals("Recommend", results.get(1));
    }
    
    @Test
    public void BranchCov_5()
    {
        // TEST PURPOSE: TRUE branch - genre matches likedGenres
        // CODE PATH: if (likedGenres.contains(genre.trim()))
        // INPUT: Recommended movie has genre matching user's preference
        // COVERS: Adds movie to recommendations and breaks inner loop
        // EXPECTED: Movie is in recommendations
        
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("Leo", "234567801", new String[]{"M1"}));
        
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("SciFiMovie", "M1", new String[]{"Sci-Fi"}));
        movies.add(new Movie("AnotherSciFi", "M2", new String[]{"Sci-Fi", "Action"}));
        
        Recommender R = new Recommender(movies, users);
        R.FindAllRecommendations();
        ArrayList<String> results = R.getRecommendationsResults();
        
        assertEquals(2, results.size());
        assertTrue(results.get(1).contains("AnotherSciFi"));
    }
    
    @Test
    public void BranchCov_6()
    {
        // TEST PURPOSE: FALSE branch - genre doesn't match likedGenres
        // CODE PATH: Inner loop continues without matching
        // INPUT: Recommended movie has no matching genres
        // COVERS: Movie skipped in recommendations
        // EXPECTED: Movie not recommended
        
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("Mia", "345678012", new String[]{"M1"}));
        
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Drama", "M1", new String[]{"Drama"}));
        movies.add(new Movie("Comedy", "M2", new String[]{"Comedy"}));
        movies.add(new Movie("Horror", "M3", new String[]{"Horror"}));
        
        Recommender R = new Recommender(movies, users);
        R.FindAllRecommendations();
        ArrayList<String> results = R.getRecommendationsResults();
        
        assertEquals(2, results.size());
        assertEquals("", results.get(1));  // No matching recommendations
    }
    
    @Test
    public void BranchCov_7()
    {
        // TEST PURPOSE: TRUE branch - recommendedTitles.length() > 0
        // CODE PATH: if (recommendedTitles.length() > 0)
        // INPUT: At least one recommendation exists
        // COVERS: Executes trailing comma removal
        // EXPECTED: Recommendation string without trailing comma
        
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("Noah", "456789023", new String[]{"M1"}));
        
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Original", "M1", new String[]{"Fantasy"}));
        movies.add(new Movie("Rec1", "M2", new String[]{"Fantasy"}));
        
        Recommender R = new Recommender(movies, users);
        R.FindAllRecommendations();
        ArrayList<String> results = R.getRecommendationsResults();
        
        assertEquals(2, results.size());
        assertFalse(results.get(1).endsWith(","));
    }
    
    @Test
    public void BranchCov_8()
    {
        // TEST PURPOSE: FALSE branch - recommendedTitles is empty
        // CODE PATH: if (recommendedTitles.length() > 0) - FALSE
        // INPUT: No matching recommendations
        // COVERS: Skips comma removal, returns empty string
        // EXPECTED: Empty string returned
        
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("Oscar", "567890124", new String[]{"M1"}));
        
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Only", "M1", new String[]{"Documentary"}));
        movies.add(new Movie("Different", "M2", new String[]{"Thriller"}));
        
        Recommender R = new Recommender(movies, users);
        R.FindAllRecommendations();
        ArrayList<String> results = R.getRecommendationsResults();
        
        assertEquals(2, results.size());
        assertEquals("", results.get(1));
    }
    
    // ============== CONDITION COVERAGE ==============
    // Condition Coverage tests different combinations of boolean conditions
    
    @Test
    public void ConditionCov_1()
    {
        // TEST PURPOSE: Genres with whitespace - trim() method called
        // CONDITION: genre.trim() removes leading/trailing spaces
        // INPUT: Genre string has spaces "  Drama  "
        // COVERS: trim() executes properly
        // EXPECTED: Genre matching works despite whitespace
        
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("Pam", "678901235", new String[]{"M1"}));
        
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Liked", "M1", new String[]{"Drama"}));
        movies.add(new Movie("Recommend", "M2", new String[]{"  Drama  "}));
        
        Recommender R = new Recommender(movies, users);
        R.FindAllRecommendations();
        ArrayList<String> results = R.getRecommendationsResults();
        
        assertEquals(2, results.size());
        assertEquals("Recommend", results.get(1));
    }
    
    @Test
    public void ConditionCov_2()
    {
        // TEST PURPOSE: Case sensitivity in genre matching
        // INPUT: Genre stored as "drama" vs "Drama"
        // COVERS: HashSet contains() is case-sensitive
        // EXPECTED: Different cases are treated differently
        
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("Quinn", "789012346", new String[]{"M1"}));
        
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Liked", "M1", new String[]{"Drama"}));
        movies.add(new Movie("Different", "M2", new String[]{"drama"}));
        
        Recommender R = new Recommender(movies, users);
        R.FindAllRecommendations();
        ArrayList<String> results = R.getRecommendationsResults();
        
        assertEquals(2, results.size());
        assertEquals("", results.get(1));  // "drama" != "Drama"
    }
    
    @Test
    public void ConditionCov_3()
    {
        // TEST PURPOSE: Multiple genres on single movie
        // INPUT: Movie with 3 genres, user likes 1 of them
        // COVERS: Enters if condition and breaks loop
        // EXPECTED: Movie recommended on first matching genre
        
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("Rose", "890123457", new String[]{"M1"}));
        
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Action Sci-Fi", "M1", new String[]{"Action", "Sci-Fi"}));
        movies.add(new Movie("Multi", "M2", new String[]{"Comedy", "Action", "Drama"}));
        
        Recommender R = new Recommender(movies, users);
        R.FindAllRecommendations();
        ArrayList<String> results = R.getRecommendationsResults();
        
        assertEquals(2, results.size());
        assertEquals("Multi", results.get(1));
    }
    
    // ============== LOOP COVERAGE ==============
    // Loop Coverage tests boundary conditions and iterations
    
    @Test
    public void LoopCov_1()
    {
        // TEST PURPOSE: Outer loop with single user
        // LOOP: for (int i = 0; i < users.size(); i++)
        // INPUT: 1 user
        // COVERS: Loop executes exactly 1 time
        // EXPECTED: 2 results (user info + recommendations)
        
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("Sam", "901234568", new String[]{"M1"}));
        
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Movie", "M1", new String[]{"Drama"}));
        movies.add(new Movie("Rec", "M2", new String[]{"Drama"}));
        
        Recommender R = new Recommender(movies, users);
        R.FindAllRecommendations();
        ArrayList<String> results = R.getRecommendationsResults();
        
        assertEquals(2, results.size());
    }
    
    @Test
    public void LoopCov_2()
    {
        // TEST PURPOSE: User with single liked movie
        // LOOP: for (String id : user.getLikedMovieIds())
        // INPUT: User with 1 liked movie ID
        // COVERS: Loop executes 1 time
        // EXPECTED: Single movie's genres collected
        
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("Tara", "012345679", new String[]{"M1"}));
        
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("OneLiked", "M1", new String[]{"Thriller"}));
        movies.add(new Movie("Recommend", "M2", new String[]{"Thriller"}));
        
        Recommender R = new Recommender(movies, users);
        R.FindAllRecommendations();
        ArrayList<String> results = R.getRecommendationsResults();
        
        assertEquals(2, results.size());
        assertEquals("Recommend", results.get(1));
    }
    
    @Test
    public void LoopCov_3()
    {
        // TEST PURPOSE: User with multiple liked movies
        // INPUT: User with 3 liked movie IDs
        // COVERS: Loop iterates 3 times, collects multiple genres
        // EXPECTED: All genres from all liked movies collected
        
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("Uma", "123456780", new String[]{"M1", "M2", "M3"}));
        
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Action", "M1", new String[]{"Action"}));
        movies.add(new Movie("Drama", "M2", new String[]{"Drama"}));
        movies.add(new Movie("Sci-Fi", "M3", new String[]{"Sci-Fi"}));
        movies.add(new Movie("RecAction", "M4", new String[]{"Action"}));
        movies.add(new Movie("RecDrama", "M5", new String[]{"Drama"}));
        movies.add(new Movie("RecSF", "M6", new String[]{"Sci-Fi"}));
        
        Recommender R = new Recommender(movies, users);
        R.FindAllRecommendations();
        ArrayList<String> results = R.getRecommendationsResults();
        
        assertEquals(2, results.size());
        assertTrue(results.get(1).contains("RecAction"));
        assertTrue(results.get(1).contains("RecDrama"));
        assertTrue(results.get(1).contains("RecSF"));
    }
    
    @Test
    public void LoopCov_4()
    {
        // TEST PURPOSE: Single movie to check
        // LOOP: for (Movie movie : movies)
        // INPUT: Only 1 movie in system
        // COVERS: Loop executes 1 time
        // EXPECTED: Movie is either recommended or not based on conditions
        
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("Victor", "234567891", new String[]{"M1"}));
        
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("OnlyMovie", "M1", new String[]{"Drama"}));
        
        Recommender R = new Recommender(movies, users);
        R.FindAllRecommendations();
        ArrayList<String> results = R.getRecommendationsResults();
        
        assertEquals(2, results.size());
        assertEquals("", results.get(1));  // User already watched the only movie
    }
    
    @Test
    public void LoopCov_5()
    {
        // TEST PURPOSE: Inner genre loop with single genre
        // LOOP: for (String genre : movie.getGenres())
        // INPUT: Movie with 1 genre
        // COVERS: Loop executes 1 time
        // EXPECTED: Genre is checked for match
        
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("Wendy", "345678902", new String[]{"M1"}));
        
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Liked", "M1", new String[]{"Mystery"}));
        movies.add(new Movie("OneGenre", "M2", new String[]{"Mystery"}));
        
        Recommender R = new Recommender(movies, users);
        R.FindAllRecommendations();
        ArrayList<String> results = R.getRecommendationsResults();
        
        assertEquals(2, results.size());
        assertEquals("OneGenre", results.get(1));
    }
    
    @Test
    public void LoopCov_6()
    {
        // TEST PURPOSE: Inner genre loop with multiple genres
        // INPUT: Movie with 3 genres, need to check all
        // COVERS: Loop iterates multiple times
        // EXPECTED: All genres checked until match found
        
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("Xavier", "456789013", new String[]{"M1"}));
        
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Comedy", "M1", new String[]{"Comedy"}));
        movies.add(new Movie("MultiGenre", "M2", new String[]{"Sci-Fi", "Thriller", "Comedy"}));
        
        Recommender R = new Recommender(movies, users);
        R.FindAllRecommendations();
        ArrayList<String> results = R.getRecommendationsResults();
        
        assertEquals(2, results.size());
        assertEquals("MultiGenre", results.get(1));
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }
}

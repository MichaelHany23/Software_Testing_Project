/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package BlackBoxTesting;

import com.mycompany.swtproject.Movie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Black Box Testing for Movie
 */
public class MovieTest {

    public MovieTest() {
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

    @Test
    public void testConstructorAndGetters() {
        String[] genres = { "Action", "Comedy" };
        Movie movie = new Movie("Title", "ID001", genres);

        assertEquals("Title", movie.getMovieTitle());
        assertEquals("ID001", movie.getMovieId());
        assertArrayEquals(genres, movie.getGenres());
    }

    @Test
    public void testSetters() {
        Movie movie = new Movie();
        movie.setMovieTitle("New Title");
        movie.setMovieId("ID002");
        movie.setGenres(new String[] { "Horror" });

        assertEquals("New Title", movie.getMovieTitle());
        assertEquals("ID002", movie.getMovieId());
        assertEquals("Horror", movie.getGenres()[0]);
    }
}

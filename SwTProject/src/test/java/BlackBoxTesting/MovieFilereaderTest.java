/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package BlackBoxTesting;

import com.mycompany.swtproject.Movie;
import com.mycompany.swtproject.MovieFilereader;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Black Box Testing for MovieFilereader
 */
public class MovieFilereaderTest {

    private MovieFilereader reader;

    public MovieFilereaderTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        reader = new MovieFilereader();
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testReadValidMovies() {
        // Input format:
        // Title,ID
        // Genre1,Genre2

        String input = "Toy Story,TS001\nAnimation,Comedy\n\nThe Shining,TS002\nHorror";
        BufferedReader bufferedReader = new BufferedReader(new StringReader(input));

        ArrayList<Movie> movies = reader.ReadMovies(bufferedReader);

        assertEquals(2, movies.size());

        assertEquals("Toy Story", movies.get(0).getMovieTitle());
        assertEquals("TS001", movies.get(0).getMovieId());
        assertEquals("Animation", movies.get(0).getGenres()[0]);

        assertEquals("The Shining", movies.get(1).getMovieTitle());
        assertEquals("Horror", movies.get(1).getGenres()[0]);
    }

    @Test
    public void testReadMovieMissingGenres() {
        // If genres line is null or missing, it should throw exception (caught
        // internally)
        // and probably stop reading or skip.
        // Based on code: throws Exception which is caught and printed. Logic loop might
        // break or not add movie.

        String input = "Toy Story,TS001"; // No next line for genres
        BufferedReader bufferedReader = new BufferedReader(new StringReader(input));

        ArrayList<Movie> movies = reader.ReadMovies(bufferedReader);

        // It catches exception and returns whatever was added before.
        // In this case, it fails to add the current movie because exception happens
        // before movies.add()
        assertEquals(0, movies.size());
    }

    @Test
    public void testReadMalformedLine() {
        // Missing comma in ID line
        String input = "Toy Story TS001\nAnimation";
        BufferedReader bufferedReader = new BufferedReader(new StringReader(input));

        ArrayList<Movie> movies = reader.ReadMovies(bufferedReader);

        assertEquals(0, movies.size());
    }

    @Test
    public void testReadWithExtraBlankLines() {
        String input = "\n\nToy Story,TS001\n\n\nAnimation\n\n";
        BufferedReader bufferedReader = new BufferedReader(new StringReader(input));

        ArrayList<Movie> movies = reader.ReadMovies(bufferedReader);

        assertEquals(1, movies.size());
        assertEquals("Toy Story", movies.get(0).getMovieTitle());
    }
}

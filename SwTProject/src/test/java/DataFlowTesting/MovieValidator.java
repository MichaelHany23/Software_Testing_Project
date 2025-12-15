/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package DataFlowTesting;

import com.mycompany.swtproject.Movie;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MovieValidator {

    // DF-01: Definition → Use (VALID movie title and ID)
    @Test
    public void DF01_validMovieData() {
        ArrayList<Movie> movies = new ArrayList<>();
        // "The Matrix" → T M + 3 digits = TM123 ✅
        movies.add(new Movie("The Matrix", "TM123", new String[]{"Action"}));

        com.mycompany.swtproject.MovieValidator validator =
                new com.mycompany.swtproject.MovieValidator(movies);

        validator.Validate();

        // VALID case → NO errors
        assertTrue(validator.ErrorIsEmpty());
        assertNotNull(validator.getMovies());
    }


    // DF-02: Definition → Use (INVALID movie title)
    @Test
    public void DF02_invalidMovieTitle() {
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("the Matrix", "TMX123", new String[]{"Action"}));

        com.mycompany.swtproject.MovieValidator validator = new com.mycompany.swtproject.MovieValidator(movies);
        validator.Validate();

        assertFalse(validator.ErrorIsEmpty());
        assertTrue(validator.getMovie_errors().getFirst().contains("Movie Title"));
    }

    // DF-03: Definition → Use (INVALID movie ID letters)
    @Test
    public void DF03_invalidMovieIdLetters() {
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("The Matrix", "tmx123", new String[]{"Action"}));

        com.mycompany.swtproject.MovieValidator validator = new com.mycompany.swtproject.MovieValidator(movies);
        validator.Validate();

        assertFalse(validator.ErrorIsEmpty());
        assertTrue(validator.getMovie_errors().get(0).contains("Movie Id letters"));
    }

    // DF-04: Definition → Use (DUPLICATE movie ID)
    @Test
    public void DF04_duplicateMovieId() {
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("The Matrix", "TMX123", new String[]{"Action"}));
        movies.add(new Movie("The Godfather", "TG123", new String[]{"Drama"}));

        com.mycompany.swtproject.MovieValidator validator = new com.mycompany.swtproject.MovieValidator(movies);
        validator.Validate();

        assertFalse(validator.ErrorIsEmpty());
        assertFalse(validator.getMovie_errors().get(0).contains("not unique"));
    }
}

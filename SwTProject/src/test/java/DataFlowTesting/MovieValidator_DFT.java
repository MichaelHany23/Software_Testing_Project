package DataFlowTesting;

import com.mycompany.swtproject.Movie;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MovieValidator_DFT {

    @Test
    public void DF01_validMovieData() {
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("The Matrix", "TM123", new String[]{"Action"}));
        com.mycompany.swtproject.MovieValidator validator =
                new com.mycompany.swtproject.MovieValidator(movies);
        validator.Validate();


        assertTrue(validator.ErrorIsEmpty());
        assertNotNull(validator.getMovies());
    }

    @Test
    public void DF02_invalidMovieTitle() {
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("the Matrix", "TM123", new String[]{"Action"}));

        com.mycompany.swtproject.MovieValidator validator =
                new com.mycompany.swtproject.MovieValidator(movies);
        validator.Validate();


        assertFalse(validator.ErrorIsEmpty());
        assertTrue(validator.getMovie_errors().get(0).contains("Movie Title"));
    }

    @Test
    public void DF03_invalidMovieIdLetters() {
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("The Matrix", "tm123", new String[]{"Action"}));

        com.mycompany.swtproject.MovieValidator validator =
                new com.mycompany.swtproject.MovieValidator(movies);
        validator.Validate();

        assertFalse(validator.ErrorIsEmpty());
        assertTrue(validator.getMovie_errors().get(0).contains("Movie Id letters"));
    }

    @Test
    public void DF04_duplicateMovieId() {
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("The Matrix", "TM123", new String[]{"Action"}));

        movies.add(new Movie("The Matrix", "TM123", new String[]{"Action"}));

        com.mycompany.swtproject.MovieValidator validator =
                new com.mycompany.swtproject.MovieValidator(movies);

        validator.Validate();

        assertFalse(validator.ErrorIsEmpty());
        assertTrue(validator.getMovie_errors().get(0).contains("not unique"));
    }

}


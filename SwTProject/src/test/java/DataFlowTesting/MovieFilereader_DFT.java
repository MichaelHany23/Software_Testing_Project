package DataFlowTesting;

import com.mycompany.swtproject.Movie;
import com.mycompany.swtproject.MovieFilereader;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MovieFilereader_DFT {

    @Test
    public void DF01_validMovieDataSingleMovie() {
        String data = "The Matrix,TM123\nAction,Sci-Fi\n";
        BufferedReader reader = new BufferedReader(new StringReader(data));
        
        MovieFilereader filereader = new MovieFilereader();
        ArrayList<Movie> movies = filereader.ReadMovies(reader);
        
        assertNotNull(movies);
        assertEquals(1, movies.size());
        assertEquals("The Matrix", movies.get(0).getMovieTitle());
        assertEquals("TM123", movies.get(0).getMovieId());
    }

    @Test
    public void DF02_validMovieDataMultipleMovies() {
        String data = "The Matrix,TM123\nAction,Sci-Fi\nAction Hero,AH456\nAction\n";
        BufferedReader reader = new BufferedReader(new StringReader(data));
        
        MovieFilereader filereader = new MovieFilereader();
        ArrayList<Movie> movies = filereader.ReadMovies(reader);
        
        assertNotNull(movies);
        assertEquals(2, movies.size());
        assertEquals("The Matrix", movies.get(0).getMovieTitle());
        assertEquals("Action Hero", movies.get(1).getMovieTitle());
    }

    @Test
    public void DF03_movieWithMultipleGenres() {
        String data = "The Matrix,TM123\nAction,Sci-Fi,Thriller\n";
        BufferedReader reader = new BufferedReader(new StringReader(data));
        
        MovieFilereader filereader = new MovieFilereader();
        ArrayList<Movie> movies = filereader.ReadMovies(reader);
        
        assertNotNull(movies);
        assertEquals(1, movies.size());
        assertEquals(3, movies.get(0).getGenres().length);
        assertEquals("Action", movies.get(0).getGenres()[0]);
    }

    @Test
    public void DF04_movieTitleWithSpaces() {
        String data = "The Dark Knight,TDK789\nAction\n";
        BufferedReader reader = new BufferedReader(new StringReader(data));
        
        MovieFilereader filereader = new MovieFilereader();
        ArrayList<Movie> movies = filereader.ReadMovies(reader);
        
        assertNotNull(movies);
        assertEquals(1, movies.size());
        assertEquals("The Dark Knight", movies.get(0).getMovieTitle());
    }

    @Test
    public void DF05_blankLinesBetweenMovies() {
        String data = "The Matrix,TM123\nAction,Sci-Fi\n\n\nAction Hero,AH456\nAction\n";
        BufferedReader reader = new BufferedReader(new StringReader(data));
        
        MovieFilereader filereader = new MovieFilereader();
        ArrayList<Movie> movies = filereader.ReadMovies(reader);
        
        assertNotNull(movies);
        assertEquals(2, movies.size());
    }

    @Test
    public void DF06_leadingTrailingSpaces() {
        String data = "  The Matrix  ,  TM123  \n  Action  ,  Sci-Fi  \n";
        BufferedReader reader = new BufferedReader(new StringReader(data));
        
        MovieFilereader filereader = new MovieFilereader();
        ArrayList<Movie> movies = filereader.ReadMovies(reader);
        
        assertNotNull(movies);
        assertEquals(1, movies.size());
        assertEquals("The Matrix", movies.get(0).getMovieTitle());
        assertEquals("TM123", movies.get(0).getMovieId());
    }

    @Test
    public void DF07_missingGenres() {
        String data = "The Matrix,TM123\n";
        BufferedReader reader = new BufferedReader(new StringReader(data));
        
        MovieFilereader filereader = new MovieFilereader();
        ArrayList<Movie> movies = filereader.ReadMovies(reader);
        
        // Exception is caught internally, returns empty list
        assertNotNull(movies);
    }

    @Test
    public void DF08_missingCommaInMovieInfo() {
        String data = "The Matrix TM123\nAction\n";
        BufferedReader reader = new BufferedReader(new StringReader(data));
        
        MovieFilereader filereader = new MovieFilereader();
        ArrayList<Movie> movies = filereader.ReadMovies(reader);
        
        // Exception caught internally
        assertNotNull(movies);
    }

    @Test
    public void DF09_tooManyFieldsInMovieInfo() {
        String data = "The Matrix,TM123,Extra\nAction\n";
        BufferedReader reader = new BufferedReader(new StringReader(data));
        
        MovieFilereader filereader = new MovieFilereader();
        ArrayList<Movie> movies = filereader.ReadMovies(reader);
        
        // Exception caught internally
        assertNotNull(movies);
    }

    @Test
    public void DF10_emptyInputStream() {
        String data = "";
        BufferedReader reader = new BufferedReader(new StringReader(data));
        
        MovieFilereader filereader = new MovieFilereader();
        ArrayList<Movie> movies = filereader.ReadMovies(reader);
        
        assertNotNull(movies);
        assertEquals(0, movies.size());
    }

    @Test
    public void DF11_genreWithSpaces() {
        String data = "The Matrix,TM123\n Action , Sci-Fi , Thriller \n";
        BufferedReader reader = new BufferedReader(new StringReader(data));
        
        MovieFilereader filereader = new MovieFilereader();
        ArrayList<Movie> movies = filereader.ReadMovies(reader);
        
        assertNotNull(movies);
        assertEquals(1, movies.size());
        assertEquals(3, movies.get(0).getGenres().length);
    }

    @Test
    public void DF12_movieIdFormat() {
        String data = "The Matrix,TM123\nAction\n";
        BufferedReader reader = new BufferedReader(new StringReader(data));
        
        MovieFilereader filereader = new MovieFilereader();
        ArrayList<Movie> movies = filereader.ReadMovies(reader);
        
        assertNotNull(movies);
        assertEquals(1, movies.size());
        assertTrue(movies.get(0).getMovieId().matches("[A-Z0-9]+"));
    }

    @Test
    public void DF13_singleGenre() {
        String data = "Action Hero,AH456\nAction\n";
        BufferedReader reader = new BufferedReader(new StringReader(data));
        
        MovieFilereader filereader = new MovieFilereader();
        ArrayList<Movie> movies = filereader.ReadMovies(reader);
        
        assertNotNull(movies);
        assertEquals(1, movies.size());
        assertEquals(1, movies.get(0).getGenres().length);
    }

    @Test
    public void DF14_manyBlankLinesAtStart() {
        String data = "\n\n\nThe Matrix,TM123\nAction\n";
        BufferedReader reader = new BufferedReader(new StringReader(data));
        
        MovieFilereader filereader = new MovieFilereader();
        ArrayList<Movie> movies = filereader.ReadMovies(reader);
        
        assertNotNull(movies);
        assertEquals(1, movies.size());
    }

    @Test
    public void DF15_genreListParsing() {
        String data = "Movie1,M001\nGenre1,Genre2,Genre3,Genre4\n";
        BufferedReader reader = new BufferedReader(new StringReader(data));
        
        MovieFilereader filereader = new MovieFilereader();
        ArrayList<Movie> movies = filereader.ReadMovies(reader);
        
        assertNotNull(movies);
        assertEquals(1, movies.size());
        assertEquals(4, movies.get(0).getGenres().length);
    }

    @Test
    public void DF16_dataFlowMovieTitleToObject() {
        String data = "Inception,IN789\nSci-Fi\n";
        BufferedReader reader = new BufferedReader(new StringReader(data));
        
        MovieFilereader filereader = new MovieFilereader();
        ArrayList<Movie> movies = filereader.ReadMovies(reader);
        
        assertNotNull(movies);
        assertEquals(1, movies.size());
        Movie movie = movies.get(0);
        assertEquals("Inception", movie.getMovieTitle());
        assertEquals("IN789", movie.getMovieId());
        assertEquals("Sci-Fi", movie.getGenres()[0]);
    }
}

package IntegrationTesting.stubs;

import java.util.ArrayList;

import com.mycompany.swtproject.Movie;
import com.mycompany.swtproject.MovieFilereader;

public class MovieFilereaderStub extends MovieFilereader {

    @Override
    public ArrayList<Movie> ReadMovies(String path) {
        ArrayList<Movie> movies = new ArrayList<>();

        Movie m1 = new Movie("Inception", "I123", new String[]{"Action", "Sci-Fi"});
        movies.add(m1);

        Movie m2 = new Movie("The Matrix", "TM456", new String[]{"Action", "Sci-Fi"});
        movies.add(m2);

        Movie m3 = new Movie("Interstellar", "I789", new String[]{"Sci-Fi", "Drama"});
        movies.add(m3);

        return movies;
    }
}

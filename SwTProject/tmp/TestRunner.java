import com.mycompany.swtproject.Movie;
import com.mycompany.swtproject.MovieValidator;
import java.util.ArrayList;

public class TestRunner {
    public static void main(String[] args) {
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("The  Matrix", "TM123", new String[]{"Action"}));

        MovieValidator validator = new MovieValidator(movies);
        try {
            validator.Validate();
            System.out.println("Errors empty: " + validator.ErrorIsEmpty());
            System.out.println("Errors: " + validator.getMovie_errors());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

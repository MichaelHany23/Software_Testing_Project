package com.mycompany.swtproject;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RecommenderTest {

    public RecommenderTest() {}

    @Test
    public void TC1() {

        ArrayList<User> users = new ArrayList<>();

        User u1 = new User("Michael Sameh", "123456789", new String[]{"BDK142", "TJ125"});
        users.add(u1);

        User u2 = new User("Michael Hany", "987654321", new String[]{"SI890"});
        users.add(u2);

        User u3 = new User("Adham Ahmed", "12345678A", new String[]{"TJ125"});
        users.add(u3);

        UserValidator UV = new UserValidator(users);
        UV.Validate();
        assertTrue(UV.ErrorIsEmpty());

        ArrayList<Movie> movies = new ArrayList<>();

        movies.add(new Movie("Batman Dark Knight", "BDK142", new String[]{"Thriller", "Action"}));
        movies.add(new Movie("Shutter Island", "SI890", new String[]{"Thriller", "Mystery", "Drama"}));
        movies.add(new Movie("The Joker", "TJ125", new String[]{"Thriller", "Crime", "Drama"}));
        movies.add(new Movie("The Avengers", "TA314", new String[]{"Action", "Sci-Fi", "Adventure"}));

        MovieValidator MV = new MovieValidator(movies);
        MV.Validate();
        assertTrue(MV.ErrorIsEmpty());

        assertEquals(UV.getUsers().size(), 3);
        assertEquals(MV.getMovies().size(), 4);

        Recommender rcmnd = new Recommender(movies, users);
        rcmnd.FindAllRecommendations();
        ArrayList<String> Results = rcmnd.getRecommendationsResults();
        System.out.println("TC1 Results: " + Results);

        assertEquals(6, Results.size());

        assertEquals(Results.get(0), u1.getName() + "," + u1.getUserId());
        assertEquals(Results.get(1), "Shutter Island,The Avengers");

        assertEquals(Results.get(2), u2.getName() + "," + u2.getUserId());
        assertEquals(Results.get(3), "Batman Dark Knight,The Joker");

        assertEquals(Results.get(4), u3.getName() + "," + u3.getUserId());
        assertEquals(Results.get(5), "Batman Dark Knight,Shutter Island");
    }

    @Test
    public void TC2() {

        ArrayList<User> users = new ArrayList<>();

        User u1 = new User("Michael Sameh", "123456789", new String[]{"BDK142", "TJ125"});
        users.add(u1);

        User u2 = new User("Michael Hany", "987654321", new String[]{"BDK142", "TJ125"});
        users.add(u2);

        UserValidator UV = new UserValidator(users);
        UV.Validate();
        assertTrue(UV.ErrorIsEmpty());

        ArrayList<Movie> movies = new ArrayList<>();

        movies.add(new Movie("Batman Dark Knight", "BDK142", new String[]{"Thriller", "Action"}));
        movies.add(new Movie("Shutter Island", "SI890", new String[]{"Thriller", "Mystery", "Drama"}));
        movies.add(new Movie("The Joker", "TJ125", new String[]{"Thriller", "Crime", "Drama"}));
        movies.add(new Movie("The Avengers", "TA314", new String[]{"Action", "Sci-Fi", "Adventure"}));

        MovieValidator MV = new MovieValidator(movies);
        MV.Validate();
        assertTrue(MV.ErrorIsEmpty());

        Recommender rcmnd = new Recommender(movies, users);
        rcmnd.FindAllRecommendations();
        ArrayList<String> Results = rcmnd.getRecommendationsResults();
        System.out.println("TC2 Results: " + Results);

        assertEquals(4, Results.size());

        assertEquals(Results.get(0), u1.getName() + "," + u1.getUserId());
        assertEquals(Results.get(1), "Shutter Island,The Avengers");

        assertEquals(Results.get(2), u2.getName() + "," + u2.getUserId());
        assertEquals(Results.get(3), "Shutter Island,The Avengers");
    }

    @Test
    public void TC3() {

        ArrayList<User> users = new ArrayList<>();

        User u1 = new User("Michael Sameh", "123456789", new String[]{"BDK142"});
        users.add(u1);

        UserValidator UV = new UserValidator(users);
        UV.Validate();

        ArrayList<Movie> movies = new ArrayList<>();

        movies.add(new Movie("Batman Dark Knight", "BDK142", new String[]{"Thriller", "Action"}));
        movies.add(new Movie("The Godfather", "TG123", new String[]{"Crime", "Drama"}));

        MovieValidator MV = new MovieValidator(movies);
        MV.Validate();

        Recommender rcmnd = new Recommender(movies, users);
        rcmnd.FindAllRecommendations();
        ArrayList<String> Results = rcmnd.getRecommendationsResults();
        System.out.println("TC3 Results: " + Results);

        assertEquals(2, Results.size());
        assertEquals(Results.get(0), u1.getName() + "," + u1.getUserId());
        assertEquals(Results.get(1), "");
    }
    @Test
    public void TC4() {

        ArrayList<User> users = new ArrayList<>();

        User u1 = new User("Michael Sameh", "123456789", new String[]{"BDK142"});
        users.add(u1);

        UserValidator UV = new UserValidator(users);
        UV.Validate();

        ArrayList<Movie> movies = new ArrayList<>();

        movies.add(new Movie("Batman Dark Knight", "BDK142", new String[]{"Thriller", "Action"}));
        movies.add(new Movie("The Godfather", "TG123", new String[]{"Crime", "Drama"}));
        movies.add(new Movie("Spider-Man No Way Home", "SMNWH789", new String[]{"Adventure","Action","Fantasy"}));

        MovieValidator MV = new MovieValidator(movies);
        MV.Validate();

        Recommender rcmnd = new Recommender(movies, users);
        rcmnd.FindAllRecommendations();
        ArrayList<String> Results = rcmnd.getRecommendationsResults();
        System.out.println("TC4 Results: " + Results);

        assertEquals(2, Results.size());
        assertEquals(Results.get(0), u1.getName() + "," + u1.getUserId());
        assertEquals(Results.get(1), "Spider-Man No Way Home");
    }
    @Test
    public void TC5() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("Ghost User", "111222333", new String[]{"XYZ000"})); // invalid movie ID

        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Batman Dark Knight", "BDK142", new String[]{"Thriller", "Action"}));

        Recommender R = new Recommender(movies, users);
        R.FindAllRecommendations();
        ArrayList<String> results = R.getRecommendationsResults();
        System.out.println("TC5 Results: " + results);

        assertEquals(2, results.size());
        assertEquals(users.get(0).getName() + "," + users.get(0).getUserId(), results.get(0));
        assertEquals("", results.get(1)); // No valid IDs -> no recommendations
    }

    @Test
    public void TC6() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("Order Test", "123450000", new String[]{"M1"}));

        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Movie1", "M1", new String[]{"Drama"}));
        movies.add(new Movie("Movie2", "M2", new String[]{"Drama"}));
        movies.add(new Movie("Movie3", "M3", new String[]{"Drama"}));

        Recommender R = new Recommender(movies, users);
        R.FindAllRecommendations();
        ArrayList<String> results = R.getRecommendationsResults();
        System.out.println("TC6 Results: " + results);

        assertEquals(2, results.size());
        assertEquals(users.get(0).getName() + "," + users.get(0).getUserId(), results.get(0));
        // Order should follow movie insertion order
        assertEquals("Movie2,Movie3", results.get(1));
    }




}

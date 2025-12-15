/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.swtproject;

import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Michael
 */
public class MovieValidatorTest {

    public MovieValidatorTest() {
    }
    @Test
    public void TC1()
    {
        //Tc1 : CORRECT MOVIE TITLE & CORRECT MOVIE ID 

        ArrayList<Movie> movies = new ArrayList<>();
        Movie M1= new Movie();
        movies.add(M1);
        M1.setMovieTitle("Batman Dark Knight"); // every word start with capital test case1
        M1.setMovieId("BDK142"); // id is correct
        String[] M1G ={"Thriller" , "Action"};
        M1.setGenres(M1G);

        MovieValidator MV= new MovieValidator(movies);
        MV.Validate();

        assertTrue(MV.ErrorIsEmpty());
    }
    @Test
    public void TC2()
    {

        //Tc2 : CORRECT MOVIE TITLE & NON UNIQUE MOVIE ID

        ArrayList<Movie> movies = new ArrayList<>();
        Movie M1= new Movie();
        movies.add(M1);
        M1.setMovieTitle("Batman Dark Knight");
        M1.setMovieId("BDK112");   // 112 is wrong
        String[] M1G ={"Thriller" , "Action"};
        M1.setGenres(M1G);

        MovieValidator MV= new MovieValidator(movies);
        MV.Validate();

        assertTrue(MV.ErrorIsEmpty());
       // assertEquals(MV.getMovie_errors().get(0) ,"ERROR:  Movie Id numbers {"+ M1.getMovieId() + "} arent unique");
    }

    @Test
    public void TC3()
    {

        //Tc2 : CORRECT MOVIE TITLE & MOVIE ID doesnt contain capital letters in movie

        ArrayList<Movie> movies = new ArrayList<>();
        Movie M1= new Movie();
        movies.add(M1);
        M1.setMovieTitle("Batman Dark Knight");
        M1.setMovieId("BD123");   // K is missing
        String[] M1G ={"Thriller" , "Action"};
        M1.setGenres(M1G);

        MovieValidator MV= new MovieValidator(movies);
        MV.Validate();

        assertFalse(MV.ErrorIsEmpty());
        assertEquals(MV.getMovie_errors().get(0) ,"ERROR: Movie Id letters {"+ M1.getMovieId() + "} wrong");
    }

    @Test
    public void TC4()
    {

        //Tc2 : CORRECT MOVIE TITLE & MOVIE ID doesnt contain 3 numbers

        ArrayList<Movie> movies = new ArrayList<>();
        Movie M1= new Movie();
        movies.add(M1);

        M1.setMovieTitle("Batman Dark Knight");
        M1.setMovieId("BDK12");

        String[] M1G ={"Thriller" , "Action"};
        M1.setGenres(M1G);

        MovieValidator MV= new MovieValidator(movies);
        MV.Validate();

        assertFalse(MV.ErrorIsEmpty());
        assertEquals(MV.getMovie_errors().get(0) ,"ERROR: Movie Id letters {"+ M1.getMovieId() + "} wrong");

    }
    @Test
    public void TC5()
    {

        //Tc2 : CORRECT MOVIE TITLE & MOVIE ID doesnt end with numbers

        ArrayList<Movie> movies = new ArrayList<>();
        Movie M1= new Movie();
        movies.add(M1);

        M1.setMovieTitle("Batman Dark Knight");
        M1.setMovieId("BDK123K");

        String[] M1G ={"Thriller" , "Action"};
        M1.setGenres(M1G);

        MovieValidator MV= new MovieValidator(movies);
        MV.Validate();

        assertFalse(MV.ErrorIsEmpty());
        assertEquals(MV.getMovie_errors().get(0) ,"ERROR: Movie Id letters {"+ M1.getMovieId() + "} wrong");

    }
    @Test
    public void TC6()
    {

        //Tc2 : CORRECT MOVIE TITLE & MOVIE ID has more than 3 numbers

        ArrayList<Movie> movies = new ArrayList<>();
        Movie M1= new Movie();
        movies.add(M1);

        M1.setMovieTitle("Batman Dark Knight");
        M1.setMovieId("BDK1234");

        String[] M1G ={"Thriller" , "Action"};
        M1.setGenres(M1G);

        MovieValidator MV= new MovieValidator(movies);
        MV.Validate();

        assertFalse(MV.ErrorIsEmpty());
        assertEquals(MV.getMovie_errors().get(0) ,"ERROR: Movie Id letters {"+ M1.getMovieId() + "} wrong");

    }
    @Test
    public void TC7()
    {

        //Tc2 : CORRECT MOVIE TITLE & MOVIE ID has wrong capital letter

        ArrayList<Movie> movies = new ArrayList<>();
        Movie M1= new Movie();
        movies.add(M1);

        M1.setMovieTitle("Batman Dark Knight");

        //COMBINED TEST CASE

        //last letter wrong M should be K
        M1.setMovieId("BDM123");

        String[] M1G ={"Thriller" , "Action"};
        M1.setGenres(M1G);

        MovieValidator MV= new MovieValidator(movies);
        MV.Validate();

        assertFalse(MV.ErrorIsEmpty());
        assertEquals(MV.getMovie_errors().get(0) ,"ERROR: Movie Id letters {"+ M1.getMovieId() + "} wrong");

        // second letter wrong
        M1.setMovieId("BMK123");
        MV.Validate();

        assertEquals(MV.getMovie_errors().size(),2);
        assertEquals(MV.getMovie_errors().get(1) ,"ERROR: Movie Id letters {"+ M1.getMovieId() + "} wrong");

        // First letter wrong
        M1.setMovieId("MDK123");
        MV.Validate();

        assertEquals(MV.getMovie_errors().size(),3);
        assertEquals(MV.getMovie_errors().get(2) ,"ERROR: Movie Id letters {"+ M1.getMovieId() + "} wrong");

        // Correct Case
        M1.setMovieId("BDK123");
        MV.Validate();

        assertEquals(MV.getMovie_errors().size(),3); // asset no increase in error list size


    }

    @Test
    public void TC8()
    {

        //Tc1 : CORRECT MOVIE TITLE & CORRECT MOVIE ID 

        ArrayList<Movie> movies = new ArrayList<>();
        Movie M1= new Movie();
        movies.add(M1);
        M1.setMovieTitle("Fast & Furious"); // every word start with capital test case1
        M1.setMovieId("FF142"); // id is correct
        String[] M1G ={"Thriller" , "Action"};
        M1.setGenres(M1G);

        MovieValidator MV= new MovieValidator(movies);
        MV.Validate();

        assertTrue(MV.ErrorIsEmpty());


    }
    @Test
    public void TC9()
    {

        //Tc1 : CORRECT MOVIE TITLE & CORRECT MOVIE ID 

        ArrayList<Movie> movies = new ArrayList<>();
        Movie M1= new Movie();
        movies.add(M1);
        M1.setMovieTitle("Spider-Man"); // every word start with capital test case1
        M1.setMovieId("S142"); // id is correct
        String[] M1G ={"Thriller" , "Action"};
        M1.setGenres(M1G);

        MovieValidator MV= new MovieValidator(movies);
        MV.Validate();

        assertTrue(MV.ErrorIsEmpty());
    }
    @Test
    public void TC10()
    {

        //Tc1 : CORRECT MOVIE TITLE & CORRECT MOVIE ID 

        ArrayList<Movie> movies = new ArrayList<>();
        Movie M1= new Movie();
        movies.add(M1);
        M1.setMovieTitle("Amazing Spider-Man 2"); // every word start with capital test case1
        M1.setMovieId("AS142"); // id is correct
        String[] M1G ={"Thriller" , "Action"};
        M1.setGenres(M1G);

        MovieValidator MV= new MovieValidator(movies);
        MV.Validate();

        assertTrue(MV.ErrorIsEmpty());
    }
    @Test
    public void TC11()
    {

        //Tc1 : CORRECT MOVIE TITLE & Duplicate Movie id

        ArrayList<Movie> movies = new ArrayList<>();
        Movie M1= new Movie();
        ;
        M1.setMovieTitle("Amazing Spider-Man 2");
        M1.setMovieId("AS125"); // id is correct
        String[] M1G ={"Thriller" , "Action"};
        M1.setGenres(M1G);
        movies.add(M1);

        Movie M2= new Movie();
        M2.setMovieTitle("Amazing Spider-Man 3");
        M2.setMovieId("AS125"); // id is correct but duplicate
        String[] M2G ={"Thriller" , "Action"};
        M2.setGenres(M2G);
        movies.add(M2);

        MovieValidator MV= new MovieValidator(movies);
        MV.Validate();

        assertFalse(MV.ErrorIsEmpty());
        assertEquals( MV.getMovie_errors().get(0) ,"ERROR: Movie Id letters {"+ M2.getMovieId() + "} is not unique");

    }

    @Test
    public void TC12()
    {
        //Tc1 : INCorrect Movie title

        ArrayList<Movie> movies = new ArrayList<>();
        Movie M1= new Movie();
        movies.add(M1);
        M1.setMovieTitle("batman dark knight"); // every word start with capital test case1
        M1.setMovieId("BDK142"); // id is correct
        String[] M1G ={"Thriller" , "Action"};
        M1.setGenres(M1G);

        MovieValidator MV= new MovieValidator(movies);
        MV.Validate();

        assertFalse(MV.ErrorIsEmpty());
        assertEquals( MV.getMovie_errors().get(0) ,"ERROR: Movie Title {"+ M1.getMovieTitle() + "} wrong");

    }
    @Test
    public void TC13()
    {
        //Tc1 : INCorrect Movie title

        ArrayList<Movie> movies = new ArrayList<>();
        Movie M1= new Movie();
        movies.add(M1);
        M1.setMovieTitle("Batman Dark knight"); // every word start with capital test case1
        M1.setMovieId("BDK142"); // id is correct
        String[] M1G ={"Thriller" , "Action"};
        M1.setGenres(M1G);

        MovieValidator MV= new MovieValidator(movies);
        MV.Validate();

        assertFalse(MV.ErrorIsEmpty());
        assertEquals( MV.getMovie_errors().get(0) ,"ERROR: Movie Title {"+ M1.getMovieTitle() + "} wrong");

    }

    @Test
    public void TC15() {
        ArrayList<Movie> movies = new ArrayList<>();
        Movie M1 = new Movie();
        movies.add(M1);

        M1.setMovieTitle("21 Jump Street");

        M1.setMovieId("JS142");
        String[] M1G ={"Comedy"};
        M1.setGenres(M1G);

        MovieValidator MV= new MovieValidator(movies);
        MV.Validate();

        assertTrue(MV.ErrorIsEmpty());
    }

    @Test
    public void TC16() {
        // Scenario: ID numbers are not unique)
        ArrayList<Movie> movies = new ArrayList<>();
        Movie M1 = new Movie();
        movies.add(M1);

        M1.setMovieTitle("Batman Dark Knight");
        M1.setMovieId("BDK464"); 
        String[] M1G = {"Action"};
        M1.setGenres(M1G);
        
        Movie M2 = new Movie();
        M2.setMovieTitle("Tom And Jerry");
        M2.setMovieId("TAJ123"); 
        String[] M2G = {"Cartoon"};
        M2.setGenres(M2G);
        movies.add(M2);
        
        Movie M3 = new Movie();
        M3.setMovieTitle("Batman Dark Knight 2");
        M3.setMovieId("BDK464"); 
        M3.setGenres(M1G);
        movies.add(M3);
        
        MovieValidator MV = new MovieValidator(movies);
        MV.Validate();

        assertFalse(MV.ErrorIsEmpty());
        assertEquals(MV.getMovie_errors().get(0)  , "ERROR: Movie Id letters {"+ M3.getMovieId() + "} is not unique");
    }
    @Test
    public void TC17() {
        // Scenario: ID numbers are not unique)
        ArrayList<Movie> movies = new ArrayList<>();
        Movie M1 = new Movie();
        movies.add(M1);

        M1.setMovieTitle("Batman Dark Knight");
        M1.setMovieId("BDK464"); 
        String[] M1G = {"Action"};
        M1.setGenres(M1G);
        
        Movie M2 = new Movie();
        M2.setMovieTitle("Tom And Jerry");
        M2.setMovieId("TAJ123"); 
        String[] M2G = {"Cartoon"};
        M2.setGenres(M2G);
        movies.add(M2);
        
        Movie M3 = new Movie();
        M3.setMovieTitle("Batman Dark Knight Asylum");
        M3.setMovieId("BDKA464"); 
        M3.setGenres(M1G);
        movies.add(M3);
        
        MovieValidator MV = new MovieValidator(movies);
        MV.Validate();

        assertTrue(MV.ErrorIsEmpty());
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

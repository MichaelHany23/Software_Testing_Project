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
import java.io.BufferedReader;
import java.io.StringReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream; 
/**
 *
 * @author Michael
 */
public class MovieFilereaderTest {
    
    private MovieFilereader reader ;
    
    public MovieFilereaderTest() {
    }
    @BeforeEach
    public void setUp() {
        this.reader= new MovieFilereader();
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
    }
    @AfterEach
    public void tearDown() {
    }
    
    @Test
    public void TC1()
    {
        String filename="Movies_JunitTest1.txt";
      
        
        Movie m1 = reader.ReadMovies(filename).get(0);
        
        //System.out.println(m1.getMovieTitle() + m1.getMovieId() + m1.getGenres()[0]);

        
        assertEquals(m1.getMovieTitle() , "Batman Dark Knight");
        assertEquals(m1.getMovieId() , "BDK142");
        assertEquals(m1.getGenres().length , 2);
    }
    @Test
    public void TC2()
    {
        String filename="Movies_JunitTest2.txt";
        
        ArrayList<Movie> arr = reader.ReadMovies(filename);
        
        for(int i = 0 ; i < arr.size() ; i++)
        {
            Movie m = arr.get(i);
            System.out.println("Movie "+i);
            System.out.println(m.getMovieTitle());
            System.out.println(m.getMovieId());
            System.out.println(m.getGenres());
            System.out.println("\n");
        }
    }
    
    @Test
    public void TC3()
    {
        //Testcase for Try catch block & error handling
        
        String TestData = "The Matrix,TM123"; // Movie ID, title, no genres
        BufferedReader BR = new BufferedReader(new StringReader(TestData));
        
        MovieFilereader reader = new MovieFilereader();
             
        
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        
        
        reader.ReadMovies(BR);   //Throws error
        System.out.println(outContent.toString());
        
        
        assertTrue(outContent.toString().contains("Exception: missing genres"));
        //reset
        System.setOut(originalOut);
    }

    @Test
    public void TC4()
    {
        // Test missing movie ID (only title provided)
        String TestData = "The Matrix\nAction,Sci-Fi";
        BufferedReader BR = new BufferedReader(new StringReader(TestData));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        MovieFilereader reader = new MovieFilereader();
        reader.ReadMovies(BR); // should throw "missing info"

        assertTrue(outContent.toString().contains("Exception: missing info"));
        System.setOut(originalOut);
    }

    @Test
    public void TC5()
    {
        // Test missing genres line (title & ID provided but no genres)
        String TestData = "Inception,INC100";
        BufferedReader BR = new BufferedReader(new StringReader(TestData));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        MovieFilereader reader = new MovieFilereader();
        reader.ReadMovies(BR); // should throw "missing genres"

        assertTrue(outContent.toString().contains("Exception: missing genres"));
        System.setOut(originalOut);
    }

    @Test
    public void TC6()
    {
        // blank line between movies (should skip)
        String TestData = "Batman Dark Knight,BDK142\nAction,Thriller\n\nThe Matrix,TM123\nAction,Sci-Fi";
        BufferedReader BR = new BufferedReader(new StringReader(TestData));

        MovieFilereader reader = new MovieFilereader();
        ArrayList<Movie> movies = reader.ReadMovies(BR);

        assertEquals(2, movies.size());
        assertEquals("Batman Dark Knight", movies.get(0).getMovieTitle());
        assertEquals("BDK142", movies.get(0).getMovieId());
        assertEquals("The Matrix", movies.get(1).getMovieTitle());
    }

    @Test
    public void TC7()
    {
        // trailing spaces should be trimmed
        String TestData = "  Avatar  ,  AV100  \nAction,Adventure";
        BufferedReader BR = new BufferedReader(new StringReader(TestData));

        MovieFilereader reader = new MovieFilereader();
        ArrayList<Movie> movies = reader.ReadMovies(BR);

        Movie m = movies.get(0);
        assertEquals("Avatar", m.getMovieTitle());
        assertEquals("AV100", m.getMovieId());
        assertEquals(2, m.getGenres().length);
    }

    @Test
    public void TC8()
    {
        // multiple genre entries
        String TestData = "Titanic,TT999\nDrama,Romance,History";
        BufferedReader BR = new BufferedReader(new StringReader(TestData));

        MovieFilereader reader = new MovieFilereader();
        ArrayList<Movie> movies = reader.ReadMovies(BR);

        assertEquals(1, movies.size());
        assertEquals(3, movies.get(0).getGenres().length);
    }
}

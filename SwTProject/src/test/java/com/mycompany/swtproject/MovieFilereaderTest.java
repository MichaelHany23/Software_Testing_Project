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
            System.out.println(m.getGenres().length);
            System.out.println("\n");
        }
        
    
    }
    @Test
    public void TC3()
    {
        //Testcase for Try catch block & error handling
        
        String TestData = "The Matrix,TM123"; // Movie ID, title, no genres
    
        BufferedReader BR = new BufferedReader(new StringReader(TestData));
        
     
        //set an  output stream
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        
        MovieFilereader reader = new MovieFilereader();
        reader.ReadMovies(BR);   //Throws error // use the overloaded readmovies with bufferedReader arguments NOT THE STRING FILEPATH or FILENAME
        
        System.out.println(outContent.toString()); // see what is in the output stream , (catch block system.out.print function)
        
        
        assertTrue(outContent.toString().contains("Exception: missing genres"));
        //reset
        System.setOut(originalOut);
    }
}


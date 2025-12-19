/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.swtproject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;
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
public class MovieFilereader_WBT {
    
    MovieFilereader reader = new MovieFilereader();
    public MovieFilereader_WBT() {
    }
        /*
      White-box coverage achieved:
      - Statement coverage: 100%
      - Branch coverage: 100%
      - Loop coverage: 0, 1, many
      - Exception paths tested via stdout capture
     */

    @Test
    public void TC1_valid()
    {
        // Valid Path , Valid Movies and genres
        // Covers normal path
    String input =
        "Inception,I123\n" +
        "Action,Sci-Fi\n";

    BufferedReader br = new BufferedReader(new StringReader(input));
    ArrayList<Movie> movies = reader.ReadMovies(br);

    assertEquals(1, movies.size());
    assertEquals("Inception", movies.get(0).getMovieTitle());
    assertEquals("I123", movies.get(0).getMovieId());
    assertEquals(2, movies.get(0).getGenres().length);
    
    }
        
    @Test
    public void TC2_valid_SpacesCommas()
    {
        // added space before starting title
        // added spaces after commas
    String input =
        " Inception,I123\n" +
        "Action ,Sci-Fi\n" +
        "Spider Man , SM123\n"+
        "Action\n";

    BufferedReader br = new BufferedReader(new StringReader(input));
    ArrayList<Movie> movies = reader.ReadMovies(br);

    assertEquals(2, movies.size());
    assertEquals("Inception", movies.get(0).getMovieTitle());
    assertEquals("I123", movies.get(0).getMovieId());
    assertEquals("Spider Man", movies.get(1).getMovieTitle());
    assertEquals("SM123", movies.get(1).getMovieId());
    assertEquals(1, movies.get(1).getGenres().length);

    } 
    
     @Test
    public void TC3_SkipBlank_Coverage()
    {
        // branch and statement coverage for the while loop of leading blank lines
        /*
            while (myline != null && myline.trim().isEmpty()) {
                myline = reader.readLine();
            }
            if (myline == null) break;
        */
    String input =
        "\n"+
        "\n"+ 
        "Inception,I123\n" +
        "Action,Sci-Fi,Mystery\n"+
        "\n"+
        "Titanic ,T111\n"+
        "Drama,History\n";

    BufferedReader br = new BufferedReader(new StringReader(input));
    ArrayList<Movie> movies = reader.ReadMovies(br);

    assertEquals(2, movies.size());
    assertEquals("Inception", movies.get(0).getMovieTitle());
    assertEquals("I123", movies.get(0).getMovieId());
    assertEquals(3, movies.get(0).getGenres().length);
    assertEquals("Titanic", movies.get(1).getMovieTitle());
    assertEquals("T111", movies.get(1).getMovieId());
    assertEquals(2, movies.get(1).getGenres().length);
    
    }
    @Test
    public void TC4_empty()
    {
        //Skip the whole outer while loop
        String input ="";

        BufferedReader br = new BufferedReader(new StringReader(input));
        ArrayList<Movie> movies = reader.ReadMovies(br);

        assertEquals(0, movies.size());
    
    }
    @Test
    void TC5_missingGenres() {
        
        // Test and covers the IF branch of missing genres
        /*
                     
             if (genres == null) // there must be atleast 1 genre
             {
                 throw new Exception ("missing genres , at least 1"); 
             }
        */
        // Capture System.out
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        
        
        String input =
            "Titanic,T789\n";
        
            
        BufferedReader br = new BufferedReader(new StringReader(input));
        ArrayList<Movie> movies = reader.ReadMovies(br);
        
        System.setOut(originalOut);
        
        
        assertTrue(movies.isEmpty()); // exception caught internally
        String printedOutput = outContent.toString();
        assertTrue(printedOutput.contains("Exception: missing genres, at least 1"));

        
    }
     @Test
    void TC6_extracomma_withoutGenre() {
        
        // Extra comma creates empty genre entry (edge case)
  
        String input =
            "Titanic,T789\n"+
            "Drama,\n";
        
            
        BufferedReader br = new BufferedReader(new StringReader(input));
        ArrayList<Movie> movies = reader.ReadMovies(br);
            
        assertEquals(movies.size() , 1); 
        assertEquals(1, movies.get(0).getGenres().length);
   
    }
         @Test
    void TC6_1_extracomma_withoutGenre() {
        
        // Extra comma creates empty genre entry (edge case)

        // Bad case here !! due to the logic in the class code of splitting by ,
        // user should be careful while entering genres of movies
  
        String input =
            "Titanic,T789\n"+
            "Drama,,Action\n";
        
            
        BufferedReader br = new BufferedReader(new StringReader(input));
        ArrayList<Movie> movies = reader.ReadMovies(br);
            
        assertEquals(movies.size() , 1); 
        assertEquals(3, movies.get(0).getGenres().length);
   
    }
    @Test
    void TC7_BadMovieInfo() {
        
        // Test and covers the IF branch of missing info
        /*
        if(MovieInfo.length != 2) {throw new Exception("missing info") ; }
        */
        // Capture System.out
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        
        
        String input =
            "Titanic\n"
             +"Drama \n";
        
 
        BufferedReader br = new BufferedReader(new StringReader(input));
        ArrayList<Movie> movies = reader.ReadMovies(br);
        
        System.setOut(originalOut);
        
        
        assertTrue(movies.isEmpty()); // exception caught internally
        String printedOutput = outContent.toString();
        assertTrue(printedOutput.contains("missing info"));

    }
    @Test
    void testBlankLinesBetweenMovies() {
        
        //test loop repetition + blank lines between any 2 entries
        String input =
            "Movie1,1\nGenre1\n\n\n" +
            "Movie2,2\nGenre2\n"+ 
            "Movie3,3\nGenre3\n\n\n\n\n"+
            "Movie4,4\nGenre4\n"
            ; 

        BufferedReader br = new BufferedReader(new StringReader(input));
        ArrayList<Movie> movies = reader.ReadMovies(br);

        assertEquals(4, movies.size());
    }
    
    
   ////////// Condition coverga
        @Test
    void TC8_NoBlankLineConditionFalse() {

        // C1 = true, C2 = false
        // Inner while condition fails immediately

        String input =
            "Avatar,A1\n" +
            "Sci-Fi\n";

        BufferedReader br = new BufferedReader(new StringReader(input));
        ArrayList<Movie> movies = reader.ReadMovies(br);

        assertEquals(1, movies.size());
    }

    
      @Test
    void TC10_BlankLinesAfterMovie() {

        String input =
            "Movie1,1\nGenre1\n\n\n" +
            "Movie2,2\nGenre2\n";

        BufferedReader br = new BufferedReader(new StringReader(input));
        ArrayList<Movie> movies = reader.ReadMovies(br);

        assertEquals(2, movies.size());
    }
    /*
    Although statement and branch coverage reach 100%, full condition coverage cannot be achieved.
    The atomic condition myline != null inside the compound predicate
    (myline != null && myline.trim().isEmpty()) cannot evaluate to false due to the guarding outer while
    */
}

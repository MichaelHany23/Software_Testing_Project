/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.swtproject;

import com.mycompany.swtproject.Movie;
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
public class MovieValidator_WBT {
    
    public MovieValidator_WBT() {
    }
        
    //// FULL STATEMENT COVERAGE
    @Test
    public void StatementCov_1()
    {
        //covers the basic paths of correct input title and id
        // misses all if conditions for wrong input statements 
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie( "Batman Dark Knight", "BDK142", new String[]{"Thriller", "Action"}));
        
        MovieValidator MV = new MovieValidator(movies);
        MV.Validate();
        assertTrue(MV.ErrorIsEmpty());
        
        
    }
    @Test
    public void StatementCov_2()
    {
        //passes through wrong id length condition
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie( "Batman Dark Knight", "BD142", new String[]{"Thriller", "Action"}));
        
        MovieValidator MV = new MovieValidator(movies);
        MV.Validate();
        assertFalse(MV.ErrorIsEmpty());
        assertEquals(MV.getMovie_errors().get(0), "ERROR: Movie Id letters {BD142} wrong");
        
    }
    
        @Test
    public void StatementCov_3()
    {
        //passes through wrong id letter condition
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie( "Batman Dark Knight", "BDS142", new String[]{"Thriller", "Action"}));
        
        MovieValidator MV = new MovieValidator(movies);
        MV.Validate();
        assertFalse(MV.ErrorIsEmpty());
        assertEquals(MV.getMovie_errors().get(0), "ERROR: Movie Id letters {BDS142} wrong");
        
    }
            @Test
    public void StatementCov_4()
    {
        //passes through wrong id NOT capital letter 
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie( "Batman Dark Knight", "bdk142", new String[]{"Thriller", "Action"}));
        
        MovieValidator MV = new MovieValidator(movies);
        MV.Validate();
        assertFalse(MV.ErrorIsEmpty());
        assertEquals(MV.getMovie_errors().get(0), "ERROR: Movie Id letters {bdk142} wrong");
        
    }
               @Test
    public void StatementCov_5()
    {
        //passes through duplicate id
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie( "Batman Dark Knight", "BDK142", new String[]{"Thriller", "Action"}));
        movies.add(new Movie( "Batman Dark Knight 2", "BDK142", new String[]{"Thriller", "Action"}));
        MovieValidator MV = new MovieValidator(movies);
        MV.Validate();
        assertFalse(MV.ErrorIsEmpty());
        assertEquals(MV.getMovie_errors().get(0), "ERROR: Movie Id letters {BDK142} is not unique");
        
    }
        @Test
    public void StatementCov_6()
    {
        //passes through if condition of less than 3 id numbers condition
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie( "Batman Dark Knight", "BDK14", new String[]{"Thriller", "Action"}));
        MovieValidator MV = new MovieValidator(movies);
        MV.Validate();
        assertFalse(MV.ErrorIsEmpty());
        assertEquals(MV.getMovie_errors().get(0), "ERROR: Movie Id letters {BDK14} wrong");
    }
            @Test
    public void StatementCov_7()
    {
        //passes through not capital letter in TITLE condition
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie( "Batman dark Knight", "BDK142", new String[]{"Thriller", "Action"}));
        MovieValidator MV = new MovieValidator(movies);
        MV.Validate();
        assertFalse(MV.ErrorIsEmpty());
        assertEquals(MV.getMovie_errors().get(0), "ERROR: Movie Title {Batman dark Knight} wrong");
    }
    ///// FULL BRANCH COVERAGE
    @Test
    public void BranchCov_1()
    {
        // this case will execute
        //all false branches of if conditions in title validation 
        //all for loops branches
        //all false branches of if conditions in id validations
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie( "SpiderMan Home Coming", "SHC112", new String[] {"Action"}));
        MovieValidator MV = new MovieValidator(movies);
        MV.Validate();
        assertTrue(MV.ErrorIsEmpty());
    
    }
    @Test
    public void BranchCov_2()
    {
        //skip the outer for loop
        MovieValidator MV = new MovieValidator( new ArrayList<>());
        MV.Validate();
       assertTrue(MV.ErrorIsEmpty());
       assertEquals(MV.getMovies().size(),0);
    }   
    @Test
    public void BranchCov_3()
    {
        // String title =movies.get(i).getMovieTitle();
            /* TITLE VALIDATION PART
           String[] titleparts= title.trim().split(" ");
            boolean invalid = false;
            int NonAlphabet_count= 0; // count non alphabetic characters in title
          
           for(int j=0 ; j < titleparts.length ; j++)
           { 
            char first = titleparts[j].charAt(0);
           
        if(!Character.isAlphabetic(first)) {NonAlphabet_count++; continue;}
     
        if(!Character.isUpperCase(first))
            {
                Movie_errors.add("ERROR: Movie Title {"+ title + "} wrong");
                invalid= true;
                break;
            }
        }
           */
        
        // covered true branch of first if 
        // covered trie branch of second if
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie( "4 eVER", "E112", new String[] {"Action"}));
        MovieValidator MV = new MovieValidator( movies);
        MV.Validate();
        assertFalse(MV.ErrorIsEmpty());

    }   
    @Test
    public void BranchCov_4()
    {
     /*
          
            if( id.length() != (titleparts.length-NonAlphabet_count + 3)) // if more than the expected length break , error 
            {
             Movie_errors.add("ERROR: Movie Id letters {"+ id + "} wrong");
             continue;
            }

            //contain all the capital letters in the title
            int expectedLetterIndex = 0;
            for (int j = 0; j < titleparts.length; j++) {
                char current_firstchar = titleparts[j].charAt(0);
                if (!Character.isAlphabetic(current_firstchar)) continue;

                if (!Character.isUpperCase(id.charAt(expectedLetterIndex))
                        || current_firstchar != id.charAt(expectedLetterIndex)) {

                    Movie_errors.add("ERROR: Movie Id letters {" + id + "} wrong");
                    invalid = true;
                    break;
                }
                expectedLetterIndex++;
            }
            if(invalid) continue;
  

            for(int j = i-1 ; j >= 0 ; j--)
            { 
                if(id.equals(movies.get(j).getMovieId())) // duplicate found in previous users
                {
                 Movie_errors.add( "ERROR: Movie Id letters {" + id + "} is not unique");
                 invalid = true;
                 break;             
                }
            }
        */
        
        // covered true branch of first if condition
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie( "SpiderMan Home Coming", "SHC11", new String[] {"Action"}));
        MovieValidator MV = new MovieValidator( movies);
        MV.Validate();
        assertFalse(MV.ErrorIsEmpty());
        assertEquals(MV.getMovie_errors().get(0), "ERROR: Movie Id letters {SHC11} wrong");

    }   
               
    @Test
    public void BranchCov_5()
    {
     /*
          
            if( id.length() != (titleparts.length-NonAlphabet_count + 3)) // if more than the expected length break , error 
            {
             Movie_errors.add("ERROR: Movie Id letters {"+ id + "} wrong");
             continue;
            }

            //contain all the capital letters in the title
            int expectedLetterIndex = 0;
            for (int j = 0; j < titleparts.length; j++) {
                char current_firstchar = titleparts[j].charAt(0);
                if (!Character.isAlphabetic(current_firstchar)) continue;

                if (!Character.isUpperCase(id.charAt(expectedLetterIndex))
                        || current_firstchar != id.charAt(expectedLetterIndex)) {

                    Movie_errors.add("ERROR: Movie Id letters {" + id + "} wrong");
                    invalid = true;
                    break;
                }
                expectedLetterIndex++;
            }
            if(invalid) continue;
  

            for(int j = i-1 ; j >= 0 ; j--)
            { 
                if(id.equals(movies.get(j).getMovieId())) // duplicate found in previous users
                {
                 Movie_errors.add( "ERROR: Movie Id letters {" + id + "} is not unique");
                 invalid = true;
                 break;             
                }
            }
        */
        
        // covered false branch of second if condition
        //wrong capital letter in ID
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie( "SpiderMan Home Coming", "SDC11", new String[] {"Action"}));
        MovieValidator MV = new MovieValidator( movies);
        MV.Validate();
        assertFalse(MV.ErrorIsEmpty());
        assertEquals(MV.getMovie_errors().get(0), "ERROR: Movie Id letters {SDC11} wrong");

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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}

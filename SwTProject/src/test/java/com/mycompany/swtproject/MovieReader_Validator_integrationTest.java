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
public class MovieReader_Validator_integrationTest {
    
    public MovieReader_Validator_integrationTest() {
    }
    
    @Test
    public void TC1()
    {
        String filename="Movies_JunitTestErrors.txt";
            /*
            Batman Dark Knight,BDK142
            Action,Thriller

            Inception,I210
            Sci-Fi,Thriller,Mystery

            Interstellar,I451
            Sci-Fi,Drama,Adventure

            The Matrix,TM19   // NOT 3 NUMBERS (Error 1)
            Sci-Fi,Action

            The Godfather,TG972
            Crime,Drama

            Shutter island,S897  // NOT CAPITAL (Error 2)
            Thriller,Mystery,Drama

            The Avengers,TA300   // NUMBERS ARENT UNIQUE
            Action,Sci-Fi,Adventure

            Joker,JK501  // WRONG CAPITAL LETTER IN ID
            Drama,Crime,Thriller

            */
                
        MovieFilereader reader = new MovieFilereader();
        MovieValidator validator = new MovieValidator( reader.ReadMovies(filename));
        validator.Validate();
        // errors in movies : matrix , shutter , Joker
        ArrayList<String> errors = validator.getMovie_errors();
        assertEquals(errors.size() , 4);
        assertEquals(errors.get(0) , "ERROR: Movie Id letters {TM19} wrong");
        assertEquals(errors.get(1) , "ERROR: Movie Title {Shutter island} wrong");
        assertEquals(errors.get(2) , "ERROR:  Movie Id numbers {TA300} arent unqiue");
        assertEquals(errors.get(3) , "ERROR: Movie Id letters {JK501} wrong");
        
        
        
      
    }
    
    
}

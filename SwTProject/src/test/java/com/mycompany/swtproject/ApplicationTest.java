/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.swtproject;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
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
public class ApplicationTest {
    
    public ApplicationTest() {
    }
    // this represent a bottom up integration test
    // as we have tested all the units
    // then we are testing the whole application through application class
    // which consists of the correct flow of functions calling 
    
    // open the text files to see the outputs
    @Test
    public void TC1()
    {
        System.out.println("CWD = " + System.getProperty("user.dir"));
        String movies_data = "TextFile_integrationTest/movies.txt";
        String user_data = "TextFile_integrationTest/users.txt";
        String output = "TextFile_integrationTest/output.txt";
        MovieFilereader FrM = new MovieFilereader();
        UserFilereader FrU= new UserFilereader();
        Application app = new Application (FrM,FrU);
        
        app.RecommenderApp(movies_data, user_data, output);
       
        
    }
    @Test
    public void TC2() throws Exception
    {
        //error in movie
        
        String movies_data = "TextFile_integrationTest/moviesError.txt";
        String user_data = "TextFile_integrationTest/users.txt";
        String output = "TextFile_integrationTest/outputError.txt";
        MovieFilereader FrM = new MovieFilereader();
        UserFilereader FrU= new UserFilereader();
        Application app = new Application (FrM,FrU);
        
        app.RecommenderApp(movies_data, user_data, output);
        
                
        List<String> lines = Files.readAllLines(Paths.get(output));

        // Assert lines 
        assertEquals("ERROR:  Movie Id numbers {TM199} arent unique" ,lines.get(0));
        assertEquals(lines.size(),1);
    }

    @Test
    public void TC3 () throws Exception
    {
        //error in user
   
        String movies_data = "TextFile_integrationTest/movies.txt";
        String user_data = "TextFile_integrationTest/usersError.txt";
        String output = "TextFile_integrationTest/outputError.txt";
        MovieFilereader FrM = new MovieFilereader();
        UserFilereader FrU= new UserFilereader();
        Application app = new Application (FrM,FrU);
        
        app.RecommenderApp(movies_data, user_data, output);
        
        List<String> lines = Files.readAllLines(Paths.get(output));

        // Assert lines 
        assertEquals("ERROR: User ID {1234567BA} is wrong" ,lines.get(0));
        assertEquals(lines.size(),1);
    }
}

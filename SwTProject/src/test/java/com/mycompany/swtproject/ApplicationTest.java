/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.swtproject;

import java.io.BufferedWriter;
import java.io.FileWriter;
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
        // mutliple errors in file but caught only the first one as required
        //assert size = 1
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
        // ends in 2 character wrong
        assertEquals("ERROR: User ID {1234567BA} is wrong" ,lines.get(0));
        assertEquals(lines.size(),1);
    }
  
    @Test
    public void TC4 () throws Exception
    {

        MovieFilereader FrM = new MovieFilereader();
        UserFilereader FrU= new UserFilereader();
        Application app = new Application (FrM,FrU);
        BufferedWriter writer;
        
        // movie insertion
        String dummymovies = "TextFile_integrationTest/dummymovies.txt";
        writer = new BufferedWriter(new FileWriter(dummymovies, false));
        writer.write("Spiderman Home Coming , SHC124");
        writer.newLine();
        writer.write("Action");
        writer.close();
        
        
        
        // user insertion
        String dummyusers = "TextFile_integrationTest/dummyusers.txt";
        writer = new BufferedWriter(new FileWriter(dummyusers, false)); 
        
        writer.write("michael hany , 123456789"); 
        writer.newLine();
        writer.write("SHC124");
        writer.newLine();
        // same id wrong
        writer.write("michael sameh , 123456789");
        writer.newLine();
        writer.write("SHC124");
        writer.close();

        String dummyoutput = "TextFile_integrationTest/dummyoutput.txt";
        
        app.RecommenderApp(dummymovies, dummyusers, dummyoutput);
        
        List<String> lines = Files.readAllLines(Paths.get(dummyoutput));
        assertEquals("ERROR: User ID {123456789} is wrong" ,lines.get(0));
        assertEquals(lines.size(),1);
    }
    @Test
    public void TC5 () throws Exception
    {

        MovieFilereader FrM = new MovieFilereader();
        UserFilereader FrU= new UserFilereader();
        Application app = new Application (FrM,FrU);
        BufferedWriter writer;
        
        // movie insertion
        String dummymovies = "TextFile_integrationTest/dummymovies.txt";
        writer = new BufferedWriter(new FileWriter(dummymovies, false));
        writer.write("Spiderman Home Coming , SHC124");
        writer.newLine();
        writer.write("Action");
        writer.newLine();
        writer.write("Mickey Mouse , MM123");
        writer.newLine();
        writer.write("Animation,Family");
        writer.close();
        
        
        
        // user insertion
        String dummyusers = "TextFile_integrationTest/dummyusers.txt";
        writer = new BufferedWriter(new FileWriter(dummyusers, false)); 
        
        writer.write("michael sameh , 123456789"); 
        writer.newLine();
        writer.write("MM123");
        writer.newLine();
        // same id wrong
        writer.write("michael hany , 12345678A");
        writer.newLine();
        writer.write("SHC124");
        writer.close();

        String dummyoutput = "TextFile_integrationTest/dummyoutput.txt";
        
        app.RecommenderApp(dummymovies, dummyusers, dummyoutput);
        
        List<String> lines = Files.readAllLines(Paths.get(dummyoutput));
        
        // no any common genres 
        assertEquals("michael sameh,123456789" ,lines.get(0));
        assertEquals("" ,lines.get(1));
        assertEquals("" ,lines.get(2));
        assertEquals("michael hany,12345678A" ,lines.get(3));
        assertEquals("" ,lines.get(4));
   
    }
}


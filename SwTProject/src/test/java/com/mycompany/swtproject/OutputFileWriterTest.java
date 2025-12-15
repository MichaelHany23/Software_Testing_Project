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
import java.io.File;
import java.io.FileWriter;
import org.junit.jupiter.api.AfterEach;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
/**
 *
 * @author Michael
 */
public class OutputFileWriterTest {
    
    private OutputFileWriter O = new OutputFileWriter();
    private String filepath;
    public OutputFileWriterTest() {
    }


    @BeforeEach
    public void SetUp()
    {
        //before each test case set path and writer object
       this.filepath = "Output_JunitTest.txt";
       O.setOutputPath(filepath); 
       //clear up the file
       O.cleanFile(filepath);
    } 
   @AfterEach
    public void tearDown() {
        // clean and reset folder
          O.cleanFile(filepath);
    }

    
    
    @Test
    public void TC1() throws Exception {
        
        //write 2 lines : hi and user
        
        ArrayList<String> arr = new ArrayList<>();
        arr.add("Hi");
        arr.add("User");
        
        //write
        O.WriteRecommendations(arr);

        // Read file
        List<String> lines = Files.readAllLines(Paths.get(filepath));

        // Assert lines 
        assertEquals("Hi", lines.get(0));
        assertEquals("User", lines.get(1));
    }
        
    @Test
    public void TC2() throws Exception {
        
        //write a file and check that the cleanfile works
  
        ArrayList<String> arr = new ArrayList<>();
        arr.add("hi");
        O.WriteRecommendations(arr);
        O.cleanFile(filepath);
       
        // Read file
        List<String> lines = Files.readAllLines(Paths.get(filepath));
        //empty
        assertTrue(lines.isEmpty());
        
    }
    @Test
    public void TC3 ()throws Exception
    {
        //Test integration with all the classes 
        //pipelining data -> validate -> recommendation -> write
        //TEST DATA
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

        Recommender rcmnd = new Recommender (movies , users);
        rcmnd.FindAllRecommendations();
        rcmnd.GetRecommendationsInFile(O);
        
        List<String> lines = Files.readAllLines(Paths.get(filepath));

        assertEquals(lines.get(0), "Michael Sameh,123456789" );
        assertEquals(lines.get(1), "Shutter Island,The Avengers");
        assertEquals(lines.get(2), "" );
        
        assertEquals(lines.get(3), "Michael Hany,987654321" );
        assertEquals(lines.get(4), "Batman Dark Knight,The Joker");
        assertEquals(lines.get(5), "" );
        
        assertEquals(lines.get(6), "Adham Ahmed,12345678A" );
        assertEquals(lines.get(7), "Batman Dark Knight,Shutter Island");
    }
    @Test
    public void TC4() throws Exception {
        
        //write 2 errors
        ArrayList<User> users = new ArrayList<>();

        User u1 = new User("Michael Sameh", "1234567AB", new String[]{"BDK142", "TJ125"});
        users.add(u1);
        UserValidator UV = new UserValidator(users);
        UV.Validate();
        assertEquals(UV.getUser_errors().get(0),"ERROR: User ID {" + u1.getUserId() + "} is wrong");
        O.WriteFirstError(UV.getUser_errors());
        
        List<String> lines = Files.readAllLines(Paths.get(filepath));

        assertEquals(lines.size() , 1);
        assertEquals(lines.get(0), "ERROR: User ID {" + u1.getUserId() + "} is wrong" );
        
           
        
    }

    @Test
    public void TC5() throws Exception {
        
       ArrayList<Movie> movies = new ArrayList<>();
        Movie m1 = new Movie("Batman Dark Knight", "BD121", new String[]{"Thriller", "Action"});
        movies.add(m1);
        MovieValidator MV = new MovieValidator(movies);
        MV.Validate();
        
        assertFalse(MV.ErrorIsEmpty());
        O.WriteFirstError(MV.getMovie_errors());
        
        List<String> lines = Files.readAllLines(Paths.get(filepath));

        assertEquals(lines.size() , 1);
        assertEquals(lines.get(0), "ERROR: Movie Id letters {"+ m1.getMovieId() + "} wrong");
    }
}

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
public class RecommenderTest {
    
    public RecommenderTest() {
    }
    
    
    @Test
    public void TC1()
    {
        //Set users 
        ArrayList<User> users = new ArrayList<>();
        //user 1
        User u1 = new User(); 
        u1.setName("Michael Sameh");
        u1.setUserId("123456789");
        String[] likedMovieIds1 = {"BDK142" , "TJ125"};
        u1.setLikedMovieIds(likedMovieIds1);
        users.add(u1);
        //user 2
        User u2 = new User(); 
        u2.setName("Michael Hany");
        u2.setUserId("987654321");
        String[] likedMovieIds2 = {"SI890"};
        u2.setLikedMovieIds(likedMovieIds2);
        users.add(u2);
        //user 3
        User u3 = new User(); 
        u3.setName("Adham Ahmed");
        u3.setUserId("12345678A");
        String[] likedMovieIds3 = {"TJ125"};
        u3.setLikedMovieIds(likedMovieIds3);
        users.add(u3);        
        
        
        //Check that the users here are correctly inserted
        UserValidator UV = new UserValidator(users); 
        UV.Validate();
        
        assertTrue(UV.ErrorIsEmpty());
        
        
        // Set movies 
        ArrayList<Movie> movies = new ArrayList<>();
        //Movie 1
        Movie M1= new Movie();
        movies.add(M1);
        M1.setMovieTitle("Batman Dark Knight"); // every word start with capital test case1
        M1.setMovieId("BDK142"); // id is correct
        String[] M1G ={"Thriller" , "Action"};
        M1.setGenres(M1G);
        //Movie 2
        Movie M2 = new Movie();
        movies.add(M2);
        M2.setMovieTitle("Shutter Island");
        M2.setMovieId("SI890");
        String[] M2G ={"Thriller" , "Mystery" , "Drama"};
        M2.setGenres(M2G);
        //Movie 3
        Movie M3 = new Movie();
        movies.add(M3);
        M3.setMovieTitle("The Joker");
        M3.setMovieId("TJ125");
        String[] M3G ={"Thriller" , "Crime" , "Drama"};
        M3.setGenres(M3G);
        //Movie 4
        Movie M4 = new Movie();
        movies.add(M4);
        M4.setMovieTitle("The Avengers");
        M4.setMovieId("TA314");
        String[] M4G ={"Action" , "Sci-Fi" , "Adventure"};
        M4.setGenres(M4G);
        
        //Check that the users here are correctly inserted
        MovieValidator MV= new MovieValidator(movies);
        MV.Validate();
        
        assertTrue(MV.ErrorIsEmpty());
        
        //check outputs of Validators 
        assertEquals(UV.getUsers().size() , 3);        
        assertEquals(MV.getMovies().size() , 4);
        
        Recommender rcmnd = new Recommender(MV.getMovies() , UV.getUsers());
        rcmnd.FindAllRecommendations();
        ArrayList<String> Results = rcmnd.getRecommendationsResults();
        
        // 3 users x  2 lines for info and recommned per user       
        assertEquals(Results.size() , 3*2); 
        
        assertEquals(Results.get(0) , u1.getName()+","+u1.getUserId());
        assertEquals(Results.get(1) , "Shutter Island,The Avengers"); // user 1 liked thriller and action - should get M2 M4 
        
        assertEquals(Results.get(2) , u2.getName()+","+u2.getUserId());
        assertEquals(Results.get(3) , "Batman Dark Knight,The Joker"); 
        
        assertEquals(Results.get(4) , u3.getName()+","+u3.getUserId());
        assertEquals(Results.get(5) , "Batman Dark Knight,Shutter Island"); 
        
    }
 
   @Test
    public void TC2()
    {
        
        
    }
}

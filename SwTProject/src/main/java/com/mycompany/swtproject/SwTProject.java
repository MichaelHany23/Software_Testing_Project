/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.swtproject;
import java.util.ArrayList;
/**
 *
 * @author Michael
 */
public class SwTProject {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        MovieFilereader FrM = new MovieFilereader(); // movie file reader 
        UserFilereader FrU = new UserFilereader();
        String MovieFilePath =".txt";   //movie file directory
        String UserFilePath =".txt";
        String OutputFilePath=".txt";
        Application app = new Application (FrM,FrU);
        app.RecommenderApp(MovieFilePath, UserFilePath, OutputFilePath);
        
        
       /* MovieFilereader FrM = new MovieFilereader(); // movie file reader 
        UserFilereader FrU = new UserFilereader(); // user file reader
        // user file directory
        String MovieFilePath =".txt";   //movie file directory
        String UserFilePath =".txt";
        String OutputFilePath=".txt"; 
                
                
        ArrayList<Movie> Movies = FrM.ReadMovies(MovieFilePath); // get lists of movies and user by reading file
        ArrayList<User> Users = FrU.ReadUsers(UserFilePath);
        
        UserValidator UV= new UserValidator(Users);
        MovieValidator MV= new MovieValidator(Movies);
        
        Recommender Recmnd = new Recommender( Movies, Users);
        OutputFileWriter FwO = new OutputFileWriter();
        Recmnd.GetRecommendationsInFile(FwO);
        
        */
 
        
        
        
        
        
       
                
    }
}

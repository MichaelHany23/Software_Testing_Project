/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.swtproject;
import java.util.ArrayList;
/**
 *
 * @author Michael
 */
public class Application {
    
    private UserFilereader frUser ;
    private MovieFilereader frMovie ;
    private Recommender rcmnd ;
    private OutputFileWriter fw;
    Application(MovieFilereader frMovie,UserFilereader frUser )  
    {
        this.frUser=frUser;
        this.frMovie=frMovie;
    }
    public void RecommenderApp(String MoviePath , String UserPath , String OutputPath)
    {
        //read files
        ArrayList<User> users = frUser.ReadUsers(UserPath);
        ArrayList<Movie> movies= frMovie.ReadMovies(MoviePath);
        //validate 
        UserValidator uv = new UserValidator(users);
        MovieValidator mv = new MovieValidator(movies);
        
        
        if(uv.getUser_errors().size() != 0) fw.WriteFirstError(uv.getUser_errors());
        if(mv.getMovie_errors().size() != 0) fw.WriteFirstError(mv.getMovie_errors());
        
        //get recommendations and set output path
        this.rcmnd = new Recommender(movies, users);
        this.fw.setOutputPath(OutputPath);
        //recommendation results
        rcmnd.FindAllRecommendations();
        
        //call the filewriter to write recommendation results
        rcmnd.GetRecommendationsInFile(fw);
        
        
    }
    
    
}

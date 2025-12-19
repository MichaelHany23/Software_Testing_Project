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
    
    // this application class represents the integration of all
    // the required & used modulus
    
    // application consist of :
    //recommender 
    //Movie / User File reader 
    // output file writer 
    
    private UserFilereader frUser ;
    private MovieFilereader frMovie ;
    private Recommender rcmnd ;
    private OutputFileWriter fw;
    
    public Application(MovieFilereader frMovie,UserFilereader frUser )  
    {
        this.frUser=frUser;
        this.frMovie=frMovie;
        fw= new OutputFileWriter();
    }

    // the fucntion called 
    public void RecommenderApp(String MoviePath , String UserPath , String OutputPath)
    {
        //read files
        ArrayList<User> users = frUser.ReadUsers(UserPath);
        ArrayList<Movie> movies= frMovie.ReadMovies(MoviePath);
        //validate 
        UserValidator uv = new UserValidator(users);
        MovieValidator mv = new MovieValidator(movies);
        
        // set your path before any action written !!
        this.fw.setOutputPath(OutputPath);
        
        mv.Validate();
        uv.Validate();
        
        if(uv.getUser_errors().size() != 0) { fw.WriteFirstError(uv.getUser_errors()); return; };
        if(mv.getMovie_errors().size() != 0) {fw.WriteFirstError(mv.getMovie_errors()); return; };
        
        //get recommendations and set output path
        this.rcmnd = new Recommender(movies, users);
       
        //recommendation results
        rcmnd.FindAllRecommendations();
        
        //call the filewriter to write recommendation results
        rcmnd.GetRecommendationsInFile(fw);
        
        
    }
    
    
}

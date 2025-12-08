/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.swtproject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
/**
 *
 * @author Michael
 */
public class Recommender {
    
    private ArrayList<Movie> movies = new ArrayList<>(); // all movies from file
    private ArrayList<User> users = new ArrayList<>(); // all users from file
    private ArrayList<String> recommendationsResults = new ArrayList<>();
    
    Recommender( ArrayList<Movie> Filemovies , ArrayList<User> Fileusers)
    {
        this.movies = Filemovies ;
        this.users = Fileusers ;
    }
 
      
    public void GetRecommendationsInFile(OutputFileWriter O)
    {
        // append to the output file
        O.WriteRecommendations(recommendationsResults);
    }   

    public ArrayList<String> getRecommendationsResults() {
        return recommendationsResults;
    }
    
    //OVERLOADING THE 2 Above function to  LOOSE COUPLE FileWriter & Recommender class
    //Provide LOOSE COUPLIN
     public void FindAllRecommendations() 
    {
        for(int i = 0 ; i < users.size() ; i++)
        {
            User u = users.get(i);
            String s = GetRecommendations_OnUser(u);
            //insert user info and recommendationsResults in arraylist & save
            recommendationsResults.add(u.getName()+","+u.getUserId());
            recommendationsResults.add(s);
        }
     
    }
    private String GetRecommendations_OnUser(User U)
    {
        //used per each user 
        return recommendMovies(U);
    }   
 

    
    
    private String recommendMovies(User user) {

    Set<String> likedMovieIds = new HashSet<>();
    for (String id : user.getLikedMovieIds()) {
        likedMovieIds.add(id);
    }

    // Step 1: Collect genres of movies the user likes
    Set<String> likedGenres = new HashSet<>();
    for (Movie movie : movies) {
        if (likedMovieIds.contains(movie.getMovieId())) {
            for (String genre : movie.getGenres()) {
                likedGenres.add(genre.trim());
            }
        }
    }

    // Step 2: Recommend movies in those genres, excluding already liked
    StringBuilder recommendedTitles = new StringBuilder();

    for (Movie movie : movies) {
        if (!likedMovieIds.contains(movie.getMovieId())) {

            for (String genre : movie.getGenres()) {

                if (likedGenres.contains(genre.trim())) {
                    recommendedTitles.append(movie.getMovieTitle()).append(",");
                    break;
                }
            }
        }
    }

    // remove the trailing comma if exists
    if (recommendedTitles.length() > 0) {
        recommendedTitles.setLength(recommendedTitles.length() - 1);
    }

    return recommendedTitles.toString();
}

       //TO use in output file writing
   /* public void FindAllRecommendations(OutputFileWriter O) 
    {
        for(int i = 0 ; i < users.size() ; i++)
        {
            GetRecommendations_Results(O , users.get(i));
        }

    } */
    
}

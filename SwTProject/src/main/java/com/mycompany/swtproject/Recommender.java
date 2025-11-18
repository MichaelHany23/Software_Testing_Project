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
    
    
    Recommender( ArrayList<Movie> Filemovies , ArrayList<User> Fileusers)
    {
        this.movies = Filemovies ;
        this.users = Fileusers ;
    }
    public void recommendForAllUsers() {
        
        for (User user : users) {
            ArrayList<String> results = recommendMovies(user);
            System.out.println("Recommendations for " + user.getName() + ": " + results);
        }
    }
    public ArrayList<String> recommendMovies(User user) {
        
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
        ArrayList<String> recommendedTitles = new ArrayList<>();
        for (Movie movie : movies) {
            
            if (!likedMovieIds.contains(movie.getMovieId())) {
                
                for (String genre : movie.getGenres()) {
                   
                    if (likedGenres.contains(genre.trim())) {
                        recommendedTitles.add(movie.getMovieTitle());
                        break; // Avoid adding the same movie multiple times if it has multiple genres
                    }
                }
            }
        }

        return recommendedTitles;
    }

    
    
}

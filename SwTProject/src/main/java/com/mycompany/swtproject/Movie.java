/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.swtproject;

/**
 *
 * @author Michael
 */
public class Movie {
    
     private String MovieTitle;
     private String MovieId;
     private String[] genres;
    
     Movie()
     {
         
     };
     
     //sort genress

    public void setMovieTitle(String MovieTitle) {
        this.MovieTitle = MovieTitle;
    }

    public void setMovieId(String MovieId) {
        this.MovieId = MovieId;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public String getMovieTitle() {
        return MovieTitle;
    }

    public String getMovieId() {
        return MovieId;
    }

    public String[] getGenres() {
        return genres;
    }

}

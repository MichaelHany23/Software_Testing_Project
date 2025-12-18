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

<<<<<<< HEAD
    Movie() {

    }
=======
    public Movie() {

    };
>>>>>>> 7ca9398 (added BlackBoxTesting)

    public Movie(String MovieTitle, String MovieId, String[] genres) {
        this.MovieTitle = MovieTitle;
        this.MovieId = MovieId;
        this.genres = genres;
<<<<<<< HEAD
    }

    ;
     
     //sort genress
=======
    };

    public Movie(String MovieTitle, String MovieId) {
        this.MovieTitle = MovieTitle;
        this.MovieId = MovieId;
        this.genres = new String[] {};
    }

    // sort genress
>>>>>>> 7ca9398 (added BlackBoxTesting)

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

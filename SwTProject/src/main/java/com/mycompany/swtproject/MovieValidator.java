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
public class MovieValidator {

    private ArrayList<String> Movie_errors;
    private ArrayList<Movie> movies;

    public MovieValidator(ArrayList<Movie> movies)
    {
        this.movies = movies;
        Movie_errors = new ArrayList<>();
    }


    public void Validate()
    {
        MovieValidate();


    }
   // getter for examining errors
    public ArrayList<String> getMovie_errors() {
        return Movie_errors;
    }
    public boolean ErrorIsEmpty()
    {
        return Movie_errors.isEmpty();
    }

    public ArrayList<Movie> getMovies() {

        if (ErrorIsEmpty()) return movies;

        return null;
    }

    private void MovieValidate()
    {
        // every word in title starts with captial

        for(int i = 0 ; i < movies.size() ; i++)
        {

            String title =movies.get(i).getMovieTitle();
            String[] titleparts= title.trim().split(" ");
            boolean invalid = false;
            int NonAlphabet_count= 0; // count non alphabetic characters in title
            for(int j=0 ; j < titleparts.length ; j++)
           {
            char first = titleparts[j].charAt(0);
            if(!Character.isAlphabetic(first)) {NonAlphabet_count++; continue;}
            if(!Character.isUpperCase(first))
            {
                Movie_errors.add("ERROR: Movie Title {"+ title + "} wrong");
                invalid= true;
                break;
            }

           }
           if(invalid) continue;

            //id must contain all the capital letters in the title followed by 3 unique numbers
           // validate id of this movie
            String id = movies.get(i).getMovieId();


            if( id.length() != (titleparts.length-NonAlphabet_count + 3)) // if more than the expected length break , error
            {
             Movie_errors.add("ERROR: Movie Id letters {"+ id + "} wrong");
             continue;
            }

            //contain all the capital letters in the title
            int expectedLetterIndex = 0;
            for (int j = 0; j < titleparts.length; j++) {
                char current_firstchar = titleparts[j].charAt(0);
                if (!Character.isAlphabetic(current_firstchar)) continue;

                if (!Character.isUpperCase(id.charAt(expectedLetterIndex))
                        || current_firstchar != id.charAt(expectedLetterIndex)) {

                    Movie_errors.add("ERROR: Movie Id letters {" + id + "} wrong");
                    invalid = true;
                    break;
                }
                expectedLetterIndex++;
            }
            if(invalid) continue;


            for(int j = i-1 ; j >= 0 ; j--)
            {
                if(id.equals(movies.get(j).getMovieId())) // duplicate found in previous users
                {
                 Movie_errors.add( "ERROR: Movie Id letters {" + id + "} is not unique");
                 invalid = true;
                 break;
                }
            }
        }

    }

}


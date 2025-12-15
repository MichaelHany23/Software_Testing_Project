/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.swtproject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
/**
 *
 * @author Michael
 */
public class MovieFilereader {
    
    
    public ArrayList<Movie> ReadMovies(String filename){
      String myline;
      ArrayList<Movie> movies = new ArrayList<>();
      try
      {
         BufferedReader reader = new BufferedReader(new FileReader(filename));

         while((myline = reader.readLine()) != null)
         {
             //skip blank line
            while (myline != null && myline.trim().isEmpty()) {
                 myline = reader.readLine();
                 }
                if (myline == null) break;
             
             
             
             String movieName = myline; // take current line
             String genres = reader.readLine(); // read next line genres & shift to the next movie
             
             if (genres == null) // there must be atleast 1 genre
             {
                 throw new Exception ("missing genres , at least 1"); 
             }
             //movieName=movieName.trim(); // remove spaces
             //genres=genres.trim();
             
        
             
             // create movie object and assign the related infos
             Movie CurrentMovie = new Movie();
             
             String[] MovieInfo = movieName.split(",");
             if(MovieInfo.length != 2) {throw new Exception("missing info") ; }
             
             
             CurrentMovie.setMovieTitle(MovieInfo[0].trim());
             CurrentMovie.setMovieId(MovieInfo[1].trim());
             CurrentMovie.setGenres(genres.split(","));
             
             movies.add(CurrentMovie); //add the current movie to the arraylist that will be returned
             
         }
         
         reader.close();
      }
      catch(Exception e)
      {
         System.out.println("Exception: " + e.getMessage());
      }
      
      return movies ;
   }
    
    //// READMOVIES function WITH Buffered reader instead of giving the direct filename
    // BufferedReader 
    // Flexibility & used in MovieFilereader test
    public ArrayList<Movie> ReadMovies(BufferedReader reader) {
    String myline;
    ArrayList<Movie> movies = new ArrayList<>();
    try {
        while ((myline = reader.readLine()) != null) {
            // skip blank lines
            while (myline != null && myline.trim().isEmpty()) {
                myline = reader.readLine();
            }
            if (myline == null) break;

            String movieName = myline; // current line
            String genres = reader.readLine(); // next line for genres

            if (genres == null) {
                throw new Exception("missing genres, at least 1");
            }

            Movie CurrentMovie = new Movie();
            String[] MovieInfo = movieName.split(",");
            if (MovieInfo.length != 2) {
                throw new Exception("missing info");
            }

            CurrentMovie.setMovieTitle(MovieInfo[0].trim());
            CurrentMovie.setMovieId(MovieInfo[1].trim());
            CurrentMovie.setGenres(genres.split(","));

            movies.add(CurrentMovie);
        }
    } catch (Exception e) {
        System.out.println("Exception: " + e.getMessage());
    }
    return movies;
}

    
};

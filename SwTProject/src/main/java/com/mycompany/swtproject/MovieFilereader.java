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
    
    
    public ArrayList<Movie> getMovies(String filename){
      String myline;
      ArrayList<Movie> movies = new ArrayList<>();
      try
      {
         BufferedReader reader = new BufferedReader(new FileReader(filename));

         while((myline = reader.readLine()) != null)
         {
             String movieName = myline; // take current line
             String genres = reader.readLine(); // read next line genres & shift to the next movie
             
             if (genres == null) // there must be atleast 1 genre
             {
                 throw new Exception (""); 
             }
             movieName=movieName.trim(); // remove spaces
             genres=genres.trim();
             
        
             
             // create movie object and assign the related infos
             Movie CurrentMovie = new Movie();
             
             String[] MovieInfo = movieName.split(",");
             if(MovieInfo.length != 2) {throw new Exception("") ; }
             
             
             CurrentMovie.setMovieTitle(MovieInfo[0].trim());
             CurrentMovie.setMovieId(MovieInfo[1].trim());
             CurrentMovie.setGenres(genres.split(","));
             
             movies.add(CurrentMovie); //add the current movie to the arraylist that will be returned
             
         }
         
         reader.close();
      }
      catch(Exception e)
      {
         System.out.println("Exception: " +e);
      }
      
      return movies ;
   }
};

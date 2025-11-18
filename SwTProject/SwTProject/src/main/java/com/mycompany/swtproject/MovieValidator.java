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
    
    ArrayList<String> Movie_errors;
    ArrayList<Movie> movies;
   
    MovieValidator(ArrayList<Movie> movies)
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
  
    private void MovieValidate()
    {
        // every word in title starts with captial
       
        for(int i = 0 ; i < movies.size() ; i++)
        { 
            
            String title =movies.get(i).getMovieTitle();
            String[] titleparts= title.trim().split(" ");
            boolean invalid = false;
            for(int j=0 ; j < titleparts.length ; j++)
           { 
            char first = titleparts[j].charAt(0);
            if(!Character.isUpperCase(first))
            {
                Movie_errors.add("ERROR: Movie Title {"+ title + "}"  +"wrong");
                invalid= true;
                break;
            }
           }
           if(invalid) continue; 
           
            //id must contain all the capital letters in the title followed by 3 unique numbers 
           // validate id of this movie
            String id = movies.get(i).getMovieId();
            
            if( id.length() != (titleparts.length + 3)) // if more than the expected length break , error 
            {
             Movie_errors.add("ERROR: Movie Id letters {"+ id + "}"  +"wrong");
             continue;
            }
            
            for(int j = 0 ; j< titleparts.length ; j++)
            {   
                char current_firstchar = titleparts[j].charAt(0);
               //contain all the capital letters in the title
                if( !Character.isUpperCase(id.charAt(j)) || current_firstchar != id.charAt(j)) 
                {
                    
                    Movie_errors.add("ERROR: Movie Id letters {"+ id + "}"  +"wrong");
                    invalid = true;
                    break;
                }
            }
            if(invalid) continue;
            //check the last 3 digits
            char a = id.charAt(id.length()-1);
            char b = id.charAt(id.length()-2);
            char c = id.charAt(id.length()-3);
            if( ! Character.isDigit(a) || ! Character.isDigit(b) || ! Character.isDigit(c))
            {
                Movie_errors.add("ERROR: Movie Id letters {"+ id + "}"  +"wrong");
                continue;
            } 
            if( a== b || b==c || a==c)
            {
               Movie_errors.add("ERROR:  Movie Id numbers {"+ id + "}"  +"arent unqiue");
               continue;
               
            } 
        }
        
    }     

}

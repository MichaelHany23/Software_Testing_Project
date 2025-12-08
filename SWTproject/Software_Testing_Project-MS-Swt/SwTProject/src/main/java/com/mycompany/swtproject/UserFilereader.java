/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.swtproject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
/**
/**
 *
 * @author Michael
 */
public class UserFilereader {
    
    
      public ArrayList<User> ReadUsers(String filename){
      String myline;
      ArrayList<User> users = new ArrayList<>();
      
      try{
      BufferedReader reader = new BufferedReader(new FileReader(filename));
      
        
      while((myline = reader.readLine()) != null)
      {
          
              //skip blank line
            while (myline != null && myline.trim().isEmpty()) {
                 myline = reader.readLine();
                 }
                if (myline == null) break;  
          
        String username = myline;
        String likedmovies = reader.readLine();
        
        if (likedmovies == null) {
          throw new Exception("Missing liked movies , at least 1 ");
        }
        
        //username = username.trim();
        //likedmovies = likedmovies.trim();
          
        String[] userInfo = username.split(",");
         
        if (userInfo.length != 2) {throw new Exception("missing info");}
          
          User CurrentUser = new User();
          CurrentUser.setName(userInfo[0].trim());
          CurrentUser.setUserId(userInfo[1].trim());
          //CurrentUser.setLikedMovieIds(likedmovies.split(","));
          
          // better for trimming each like movie
          
            String[] likedIds = likedmovies.split(",");
            for (int i = 0; i < likedIds.length; i++) {
            likedIds[i] = likedIds[i].trim();
            }
            CurrentUser.setLikedMovieIds(likedIds);
           
          
          users.add(CurrentUser);
      }
        reader.close();
      }
      
      catch(Exception e)
      {
          System.out.println("Exception from user reader: " + e.getMessage());
      }
      
      return users;
      }
}

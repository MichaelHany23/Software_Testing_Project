/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.swtproject;

/**
 *
 * @author Michael
 */
public class SwTProject {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        
        
        MovieFilereader FrM = new MovieFilereader(); // movie file reader 
        UserFilereader FrU = new UserFilereader(); // user file reader
        String MovieFile =".txt";   //directory
        String UserFile =".txt"; // user directory
        
        Recommender Recmnd = new Recommender(FrM.getMovies(MovieFile) , FrU.getUsers(UserFile));
       
        
        //Recmnd.recommendForAllUsers();
        
       
        // OutputFileWriter FwO = new OutputFileWriter();        
    }
}

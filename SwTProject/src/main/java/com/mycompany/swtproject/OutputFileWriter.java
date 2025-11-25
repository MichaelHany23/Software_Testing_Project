/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.swtproject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;


/**
 *
 * @author Michael
 */
public class OutputFileWriter {
    
    
    private String outputPath;
    
    
    OutputFileWriter()
    {
        
    }
    OutputFileWriter(String outputpath)
    {
        this.outputPath = outputpath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }
    protected void WriteFirstError(ArrayList<String> results)
    {   
        //clean the file before any writing
        this.cleanFile(outputPath);
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath, true)); // append mode

            // results contains: 
               // error line !!!
            String line = results.get(0);
                
                writer.write(line);
                writer.newLine();
                
            writer.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    protected void WriteRecommendations(ArrayList<String> results)
    {
        //clean the file before any writing
        this.cleanFile(outputPath);
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath, true)); // append mode

            // results contains: 
            // line 1 → "username,userid"
            // line 2 → "movie1,movie2,movie3"

            for (int i = 0 ; i < results.size(); i++) {
                writer.write(results.get(i));
                writer.newLine();
                if(i%2 !=0) writer.newLine(); //add blank line between users
            }

            writer.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    } 

    public void cleanFile(String filePath) {
    
        try (FileWriter fw = new FileWriter(filePath, false)) {
        fw.write(""); 
        } catch (Exception e) {
            e.printStackTrace();
        }
}
    
   /* public void WriteRecommendations(User U , ArrayList<String> recommendedTitles)
    {
        
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath, true)); // true to append not delete
            
            writer.write(U.getName()+","+U.getUserId());
            writer.newLine();
            for(int i = 0 ; i < recommendedTitles.size()-1 ; i++)
            {
                 writer.write(recommendedTitles.get(i) + ",");
            }
            writer.write(recommendedTitles.getLast()); // without the comma for laaast element !
            
            
             writer.newLine();
             writer.close();
        }
        
        catch(Exception e)
        {
            System.out.println(" error ");
        }
        
        
    }*/
    
    
}

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
    
    
    private String outputPath = "recommendations.txt"; 
    
    
    OutputFileWriter()
    {
        
    }
    OutputFileWriter(String outputpath)
    {
        this.outputPath = outputpath;
    }
    public void WriteRecommendations(User U , ArrayList<String> recommendedTitles)
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
        
        
    }
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.swtproject;
import java.util.ArrayList;
/**
 *
 * @author Michael
 */
public class SwTProject {

    public static void main(String[] args) {
        System.out.println("Hello World!");

        // Check if arguments were provided (usually 3: moviePath, userPath, outputPath)
        if (args.length < 3) {
            System.out.println("Error: Missing file path arguments.");
            return;
        }

        MovieFilereader FrM = new MovieFilereader();
        UserFilereader FrU = new UserFilereader();

        // Use the arguments from the test/command line
        String MovieFilePath = args[0];
        String UserFilePath = args[1];
        String OutputFilePath = args[2];

        Application app = new Application(FrM, FrU);
        app.RecommenderApp(MovieFilePath, UserFilePath, OutputFilePath);
    }
}

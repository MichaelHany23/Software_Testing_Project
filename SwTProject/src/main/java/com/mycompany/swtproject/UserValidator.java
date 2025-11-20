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
public class UserValidator {
    
    ArrayList<String> User_errors ;
    ArrayList<User> users;
    
    UserValidator(ArrayList<User> users)
    {
        this.users = users;
        User_errors = new ArrayList<>();
    }
    public void Validate()
    {
        UserValidate();
   
    }
    // getter for examining errors
    public ArrayList<String> getUser_errors() {
        return User_errors;
    }
    public boolean ErrorIsEmpty()
    {
        return User_errors.isEmpty(); 
    }
    private void UserValidate()
    {
         //It must be Alphabetic characters and Spaces. the name should not start with space. 
        for(int i = 0 ; i < users.size() ; i++)
        {
            boolean invalid=false; // flag used within loop to skip the outer loop of the wrong users
            
            // USERNAME
           
            String currentUsername = users.get(i).getName();
            
            if(currentUsername.charAt(0) == ' ') //shouldnt start with space
            {
                User_errors.add( "ERROR: User Name {" + currentUsername + "} is wrong");
                continue;
            }

            for(int j=0 ; j < currentUsername.length() ; j++)
            {
                if(!Character.isAlphabetic(currentUsername.charAt(j)) && currentUsername.charAt(j) != ' ' )
                {
                 User_errors.add( "ERROR: User Name {" + currentUsername + "} is wrong");
                 invalid = true;
                 break; 
                }
            }
            if(invalid) continue;
            
            ////////////////
            
            //USERID
            
            String currentid = users.get(i).getUserId();
            
            //rule 1 , 9 character exact
            if(currentid.length() != 9)
            {
            User_errors.add( "ERROR: User ID {" + currentid + "} is wrong");
            continue;
            }
            //rule 2 , starts with digits only
            if(!Character.isDigit(currentid.charAt(0))) // start with digit must!!
            {
                User_errors.add( "ERROR: User ID {" + currentid + "} is wrong");
            continue;
            }
            //rule 3 , alphanumeric and might end with only 1 alphabet
            for(int j = 1 ; j < currentid.length() ; j++)
            { 
                if(!Character.isAlphabetic(currentid.charAt(j)) && !Character.isDigit(currentid.charAt(j))) // alphanumeric ID !!
                {
                 User_errors.add( "ERROR: User ID {" + currentid + "} is wrong");
                 invalid = true;
                 break;
                }
                if(j == currentid.length()-1 && Character.isAlphabetic(currentid.charAt(j)))//might end with only 1 alphabet!!!
                {
                   if(Character.isAlphabetic(currentid.charAt(j-1))) // look at the pre last character for ex : 1234...AB wrong ends with AB
                    {
                    User_errors.add( "ERROR: User ID {" + currentid + "} is wrong");
                    invalid = true;
                    break;        
                    }
                }
            }
            if(invalid) continue;
            
            //rule 4 , NO DUPLICATES 
            for(int j = i-1 ; j >= 0 ; j--)
            { 
                if(currentid.equals(users.get(j).getUserId())) // duplicate found in previous users
                {
                 User_errors.add( "ERROR: User ID {" + currentid + "} is wrong");
                 invalid = true;
                 break;             
                }
            }
            if(invalid) continue;
            
         }
    }
    
         
    }
    


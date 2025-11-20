/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.swtproject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
/**
 *
 * @author Michael
 */
public class UserValidatorTest {
    
    public UserValidatorTest() {
    }
    
    @Test
    public void TC1()
    {
           //correct name and id
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User(); 
        u1.setName("Michael Sameh");
        u1.setUserId("123456789");
        String[] likedMovieIds = {"BDK123"};
        u1.setLikedMovieIds(likedMovieIds);
        users.add(u1);
        
            
        UserValidator UV = new UserValidator(users); 
        UV.Validate();
        
        assertTrue(UV.ErrorIsEmpty());
    }
        @Test
    public void TC2()
    {
        // correct name and id ends with a letter
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User(); 
        u1.setName("Michael Sameh");
        u1.setUserId("12345678A");
        String[] likedMovieIds = {"BDK123"};
        u1.setLikedMovieIds(likedMovieIds);
        users.add(u1);
        
            
        UserValidator UV = new UserValidator(users); 
        UV.Validate();
        
        assertTrue(UV.ErrorIsEmpty());
    }
        @Test
    public void TC3()
    {
        // correct name and id 
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User(); 
        u1.setName("Michael Sameh Michel");
        u1.setUserId("123456789");
        String[] likedMovieIds = {"BDK123"};
        u1.setLikedMovieIds(likedMovieIds);
        users.add(u1);
        
            
        UserValidator UV = new UserValidator(users); 
        UV.Validate();
        
        assertTrue(UV.ErrorIsEmpty());
    }
    
    @Test
    public void TC4()
    {
        // correct name and wrong id ends with 2 characters
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User(); 
        u1.setName("Michael Sameh");
        u1.setUserId("1234567AB");
        String[] likedMovieIds = {"BDK123"};
        u1.setLikedMovieIds(likedMovieIds);
        users.add(u1);
        
            
        UserValidator UV = new UserValidator(users); 
        UV.Validate();
        
        assertFalse(UV.ErrorIsEmpty());
        assertEquals(UV.getUser_errors().get(0) ,"ERROR: User ID {" + u1.getUserId() + "} is wrong");
    }

    @Test
    public void TC5()
    {
        // correct name and with many spaces
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User(); 
        u1.setName("Michael     Sameh    ");
        u1.setUserId("123456789");
        String[] likedMovieIds = {"BDK123"};
        u1.setLikedMovieIds(likedMovieIds);
        users.add(u1);
        
            
        UserValidator UV = new UserValidator(users); 
        UV.Validate();
        
        assertTrue(UV.ErrorIsEmpty());

    }
    @Test
    public void TC6()
    {
        // correct name and in small letters
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User(); 
        u1.setName("michael sameh");
        u1.setUserId("123456789");
        String[] likedMovieIds = {"BDK123"};
        u1.setLikedMovieIds(likedMovieIds);
        users.add(u1);
        
            
        UserValidator UV = new UserValidator(users); 
        UV.Validate();
        
        assertTrue(UV.ErrorIsEmpty());

    }
    
    @Test
    public void TC7()
    {
        // correct name and id is not EXACTLY 9 letters
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User(); 
        u1.setName("michael sameh");
        u1.setUserId("1234567");
        String[] likedMovieIds = {"BDK123" ,"A124"};
        u1.setLikedMovieIds(likedMovieIds);
        users.add(u1);
        
            
        UserValidator UV = new UserValidator(users); 
        UV.Validate();
        
        assertFalse(UV.ErrorIsEmpty());
        assertEquals(UV.getUser_errors().get(0) ,"ERROR: User ID {" + u1.getUserId() + "} is wrong");
    }
    @Test
    public void TC8()
    {
        // correct name and id 
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User(); 
        u1.setName("michael sameh");
        u1.setUserId("1BCDEFGH9");
        String[] likedMovieIds = {"BDK123" ,"A124"};
        u1.setLikedMovieIds(likedMovieIds);
        users.add(u1);
        
            
        UserValidator UV = new UserValidator(users); 
        UV.Validate();
        
        assertTrue(UV.ErrorIsEmpty());
        
    }
    @Test
    public void TC9()
    {
        // correct name and wrong id , doesnt start with number
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User(); 
        u1.setName("michael sameh");
        u1.setUserId("ABCDEFGH9");
        String[] likedMovieIds = {"BDK123"};
        u1.setLikedMovieIds(likedMovieIds);
        users.add(u1);
        
            
        UserValidator UV = new UserValidator(users); 
        UV.Validate();
        
        assertFalse(UV.ErrorIsEmpty());
        assertEquals(UV.getUser_errors().get(0) ,"ERROR: User ID {" + u1.getUserId() + "} is wrong");
    }
    @Test
    public void TC10()
    {
        // correct name and wrong id , doesnt start with number
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User(); 
        u1.setName("michael sameh");
        u1.setUserId("abcdefghj");
        String[] likedMovieIds = {"BDK123"};
        u1.setLikedMovieIds(likedMovieIds);
        users.add(u1);
        
            
        UserValidator UV = new UserValidator(users); 
        UV.Validate();
        
        assertFalse(UV.ErrorIsEmpty());
        assertEquals(UV.getUser_errors().get(0) ,"ERROR: User ID {" + u1.getUserId() + "} is wrong");
    }
    @Test
    public void TC11()
    {
        // correct name and id in small letter
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User(); 
        u1.setName("michael sameh");
        u1.setUserId("1bcdefgh4");
        String[] likedMovieIds = {"BDK123"};
        u1.setLikedMovieIds(likedMovieIds);
        users.add(u1);
        
            
        UserValidator UV = new UserValidator(users); 
        UV.Validate();
        
        assertTrue(UV.ErrorIsEmpty());
       
    }

    @Test
    public void TC12()
    {
        // correct name and wrong id contains a non alphabet character
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User(); 
        u1.setName("michael sameh");
        u1.setUserId("1@2345678");
        String[] likedMovieIds = {"BDK123"};
        u1.setLikedMovieIds(likedMovieIds);
        users.add(u1);
        
            
        UserValidator UV = new UserValidator(users); 
        UV.Validate();
        
        assertFalse(UV.ErrorIsEmpty());
        assertEquals(UV.getUser_errors().get(0) ,"ERROR: User ID {" + u1.getUserId() + "} is wrong");

       
    }
    @Test
    public void TC13()
    {
        // wrong name contain a non alphabetic characters
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User(); 
        u1.setName("michael@sameh");
        u1.setUserId("192345678");
        String[] likedMovieIds = {"BDK123"};
        u1.setLikedMovieIds(likedMovieIds);
        users.add(u1);
        
            
        UserValidator UV = new UserValidator(users); 
        UV.Validate();
        
        assertFalse(UV.ErrorIsEmpty());
        assertEquals(UV.getUser_errors().get(0) ,"ERROR: User Name {" + u1.getName()+ "} is wrong");

       
    }
    @Test
    public void TC14()
    {
        // correct name and id 
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User(); 
        u1.setName("michael");
        u1.setUserId("111111111");
        String[] likedMovieIds = {"BDK123"};
        u1.setLikedMovieIds(likedMovieIds);
        users.add(u1);
        
            
        UserValidator UV = new UserValidator(users); 
        UV.Validate();
        
        assertTrue(UV.ErrorIsEmpty());
       
    }
    @Test
    public void TC15()
    {
        // correct name and id duplicate
        ArrayList<User> users = new ArrayList<>();
        
        User u1 = new User(); 
        u1.setName("michael sameh");
        u1.setUserId("123456789");
       
        User u2 = new User(); 
        u2.setName("michael hany");
        u2.setUserId("987654301");

        User u3 = new User(); 
        u3.setName("michael sameh");
        u3.setUserId("987654301");
        
        User u4 = new User(); 
        u4.setName("michael hany");
        u4.setUserId("9ABCDEFG0");
        
        User u5 = new User(); 
        u5.setName("andrew wagdy");
        u5.setUserId("9ABCDEFG0"); 
        
        users.add(u1);
        users.add(u2);
        users.add(u3);
        users.add(u4);
        users.add(u5);
            
        UserValidator UV = new UserValidator(users); 
        UV.Validate();
        
        assertFalse(UV.ErrorIsEmpty());
        assertEquals(UV.getUser_errors().size() ,2);
        assertEquals(UV.getUser_errors().get(0) ,"ERROR: User ID {" + u3.getUserId() + "} is wrong");
        assertEquals(UV.getUser_errors().get(1) ,"ERROR: User ID {" + u5.getUserId() + "} is wrong");
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}


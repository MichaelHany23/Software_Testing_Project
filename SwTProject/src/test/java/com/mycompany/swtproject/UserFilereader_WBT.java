package com.mycompany.swtproject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserFilereader_WBT {

    UserFilereader reader = new UserFilereader();

    /*
     White-box coverage achieved:
     - Statement coverage: 100%
     - Branch coverage: 100%
     - Loop coverage: 0, 1, many
     - Exception paths tested via stdout capture
    */

    @Test
    public void TC1_validUser() {
        String input =
            "Michael,U123\n" +
            "M1,M2,M3\n";

        BufferedReader br = new BufferedReader(new StringReader(input));
        ArrayList<User> users = reader.ReadUsers(br);
        
        assertEquals(1, users.size());
        assertEquals("Michael", users.get(0).getName());
        assertEquals("U123", users.get(0).getUserId());
        assertEquals(3, users.get(0).getLikedMovieIds().length);
    }

    @Test
    public void TC2_valid_SpacesAndTrim() {
        String input =
            " Michael , U111 \n" +
            " M1 , M2 , M3 \n" +
            "Sarah,S222\n" +
            "M9\n";

        BufferedReader br = new BufferedReader(new StringReader(input));
        ArrayList<User> users = reader.ReadUsers(br);

        assertEquals(2, users.size());
        assertEquals("Michael", users.get(0).getName());
        assertEquals("U111", users.get(0).getUserId());
        assertEquals("Sarah", users.get(1).getName());
        assertEquals(1, users.get(1).getLikedMovieIds().length);
    }

    @Test
    public void TC3_skipBlankLines() {
        String input =
            "\n\n" +
            "Adam,A001\n" +
            "M1,M2\n" +
            "\n\n" +
            "Eve,E002\n" +
            "M3\n";

        BufferedReader br = new BufferedReader(new StringReader(input));
        ArrayList<User> users = reader.ReadUsers(br);

        assertEquals(2, users.size());
    }

    @Test
    public void TC4_emptyFile() {
        BufferedReader br = new BufferedReader(new StringReader(""));
        ArrayList<User> users = reader.ReadUsers(br);

        assertEquals(0, users.size());
    }

    @Test
    public void TC5_missingLikedMovies() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        String input =
            "John,J777\n";

        BufferedReader br = new BufferedReader(new StringReader(input));
        ArrayList<User> users = reader.ReadUsers(br);

        System.setOut(originalOut);

        assertTrue(users.isEmpty());
        assertTrue(outContent.toString()
                .contains("Missing liked movies"));
    }

    @Test
    public void TC6_extraCommaInLikedMovies() {
        String input =
            "Liam,L555\n" +
            "M1,\n";

        BufferedReader br = new BufferedReader(new StringReader(input));
        ArrayList<User> users = reader.ReadUsers(br);

        assertEquals(1, users.size());
        assertEquals(1, users.get(0).getLikedMovieIds().length);
        assertEquals("M1", users.get(0).getLikedMovieIds()[0]);
    }

    @Test
    public void TC6_1_doubleCommaInLikedMovies() {
        String input =
            "Noah,N888\n" +
            "M1,,M2\n";

        BufferedReader br = new BufferedReader(new StringReader(input));
        ArrayList<User> users = reader.ReadUsers(br);

        assertEquals(1, users.size());
        assertEquals(3, users.get(0).getLikedMovieIds().length);
        assertEquals("", users.get(0).getLikedMovieIds()[1]);
    }

    @Test
    public void TC7_badUserInfo() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        String input =
            "OnlyName\n" +
            "M1,M2\n";

        BufferedReader br = new BufferedReader(new StringReader(input));
        ArrayList<User> users = reader.ReadUsers(br);

        System.setOut(originalOut);

        assertTrue(users.isEmpty());
        assertTrue(outContent.toString().contains("missing info"));
    }

    @Test
    public void TC8_blankLinesBetweenUsers() {
        String input =
            "U1,1\nM1\n\n\n" +
            "U2,2\nM2\n\n\n\n" +
            "U3,3\nM3\n";

        BufferedReader br = new BufferedReader(new StringReader(input));
        ArrayList<User> users = reader.ReadUsers(br);

        assertEquals(3, users.size());
    }
}

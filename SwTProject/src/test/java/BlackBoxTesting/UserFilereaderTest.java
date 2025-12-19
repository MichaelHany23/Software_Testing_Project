/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package BlackBoxTesting;

import com.mycompany.swtproject.User;
import com.mycompany.swtproject.UserFilereader;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Black Box Testing for UserFilereader
 */
public class UserFilereaderTest {

    private UserFilereader reader;

    public UserFilereaderTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        reader = new UserFilereader();
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testReadValidUsers() {
        // Format:
        // Name,ID
        // LikedMovie1, LikedMovie2

        String input = "John Doe,123456789\nM1, M2\n\nJane Doe,987654321\nM3";
        BufferedReader bufferedReader = new BufferedReader(new StringReader(input));

        ArrayList<User> users = reader.ReadUsers(bufferedReader);

        assertEquals(2, users.size());

        assertEquals("John Doe", users.get(0).getName());
        assertEquals("123456789", users.get(0).getUserId());
        assertEquals("M1", users.get(0).getLikedMovieIds()[0]);
        assertEquals("M2", users.get(0).getLikedMovieIds()[1]);

        assertEquals("Jane Doe", users.get(1).getName());
    }

    @Test
    public void testReadUserMissingLikedLines() {
        String input = "John Doe,123456789"; // No second line
        BufferedReader bufferedReader = new BufferedReader(new StringReader(input));

        ArrayList<User> users = reader.ReadUsers(bufferedReader);

        assertEquals(0, users.size(), "Should not load user with missing data");
    }

    @Test
    public void testReadMalformedUserLine() {
        // No comma
        String input = "John Doe 123456789\nM1";
        BufferedReader bufferedReader = new BufferedReader(new StringReader(input));

        ArrayList<User> users = reader.ReadUsers(bufferedReader);

        assertEquals(0, users.size());
    }

    @Test
    public void testTrimmingFeatures() {
        // Spaces around data
        String input = " John Doe , 123456789 \n M1 , M2 ";
        BufferedReader bufferedReader = new BufferedReader(new StringReader(input));

        ArrayList<User> users = reader.ReadUsers(bufferedReader);

        assertEquals("John Doe", users.get(0).getName());
        assertEquals("123456789", users.get(0).getUserId());
        assertEquals("M1", users.get(0).getLikedMovieIds()[0]);
        assertEquals("M2", users.get(0).getLikedMovieIds()[1]);
    }
}

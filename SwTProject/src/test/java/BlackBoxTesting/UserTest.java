/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package BlackBoxTesting;

import com.mycompany.swtproject.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Black Box Testing for User
 */
public class UserTest {

    public UserTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testConstructorAndGetters() {
        String[] liked = { "M1", "M2" };
        User user = new User("John", "123", liked);

        assertEquals("John", user.getName());
        assertEquals("123", user.getUserId());
        assertArrayEquals(liked, user.getLikedMovieIds());
    }

    @Test
    public void testSetters() {
        User user = new User();
        user.setName("Jane");
        user.setUserId("456");
        user.setLikedMovieIds(new String[] { "M3" });

        assertEquals("Jane", user.getName());
        assertEquals("456", user.getUserId());
        assertEquals("M3", user.getLikedMovieIds()[0]);
    }
}

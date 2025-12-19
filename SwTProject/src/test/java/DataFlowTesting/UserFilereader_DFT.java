package DataFlowTesting;

import com.mycompany.swtproject.User;
import com.mycompany.swtproject.UserFilereader;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserFilereader_DFT {

    @Test
    public void DF01_validUserDataSingleUser() {
        String data = "John Doe,123456789\nM001,M002\n";
        BufferedReader reader = new BufferedReader(new StringReader(data));
        
        UserFilereader filereader = new UserFilereader();
        ArrayList<User> users = filereader.ReadUsers(reader);
        
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("John Doe", users.get(0).getName());
        assertEquals("123456789", users.get(0).getUserId());
    }

    @Test
    public void DF02_validUserDataMultipleUsers() {
        String data = "John Doe,123456789\nM001,M002\nJane Smith,234567890\nM003,M004\n";
        BufferedReader reader = new BufferedReader(new StringReader(data));
        
        UserFilereader filereader = new UserFilereader();
        ArrayList<User> users = filereader.ReadUsers(reader);
        
        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals("John Doe", users.get(0).getName());
        assertEquals("Jane Smith", users.get(1).getName());
    }

    @Test
    public void DF03_userWithMultipleLikedMovies() {
        String data = "John Doe,123456789\nM001,M002,M003,M004\n";
        BufferedReader reader = new BufferedReader(new StringReader(data));
        
        UserFilereader filereader = new UserFilereader();
        ArrayList<User> users = filereader.ReadUsers(reader);
        
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(4, users.get(0).getLikedMovieIds().length);
        assertEquals("M001", users.get(0).getLikedMovieIds()[0]);
    }

    @Test
    public void DF04_userNameWithSpaces() {
        String data = "John Mary Doe,123456789\nM001\n";
        BufferedReader reader = new BufferedReader(new StringReader(data));
        
        UserFilereader filereader = new UserFilereader();
        ArrayList<User> users = filereader.ReadUsers(reader);
        
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("John Mary Doe", users.get(0).getName());
    }

    @Test
    public void DF05_blankLinesBetweenUsers() {
        String data = "John Doe,123456789\nM001\n\n\nJane Smith,234567890\nM002\n";
        BufferedReader reader = new BufferedReader(new StringReader(data));
        
        UserFilereader filereader = new UserFilereader();
        ArrayList<User> users = filereader.ReadUsers(reader);
        
        assertNotNull(users);
        assertEquals(2, users.size());
    }

    @Test
    public void DF06_leadingTrailingSpaces() {
        String data = "  John Doe  ,  123456789  \n  M001  ,  M002  \n";
        BufferedReader reader = new BufferedReader(new StringReader(data));
        
        UserFilereader filereader = new UserFilereader();
        ArrayList<User> users = filereader.ReadUsers(reader);
        
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("John Doe", users.get(0).getName());
        assertEquals("123456789", users.get(0).getUserId());
    }

    @Test
    public void DF07_missingLikedMovies() {
        String data = "John Doe,123456789\n";
        BufferedReader reader = new BufferedReader(new StringReader(data));
        
        UserFilereader filereader = new UserFilereader();
        ArrayList<User> users = filereader.ReadUsers(reader);
        
        // Exception is caught internally, returns empty list
        assertNotNull(users);
    }

    @Test
    public void DF08_missingCommaInUserInfo() {
        String data = "John Doe 123456789\nM001\n";
        BufferedReader reader = new BufferedReader(new StringReader(data));
        
        UserFilereader filereader = new UserFilereader();
        ArrayList<User> users = filereader.ReadUsers(reader);
        
        // Exception caught internally
        assertNotNull(users);
    }

    @Test
    public void DF09_tooManyFieldsInUserInfo() {
        String data = "John Doe,123456789,Extra\nM001\n";
        BufferedReader reader = new BufferedReader(new StringReader(data));
        
        UserFilereader filereader = new UserFilereader();
        ArrayList<User> users = filereader.ReadUsers(reader);
        
        // Exception caught internally
        assertNotNull(users);
    }

    @Test
    public void DF10_emptyInputStream() {
        String data = "";
        BufferedReader reader = new BufferedReader(new StringReader(data));
        
        UserFilereader filereader = new UserFilereader();
        ArrayList<User> users = filereader.ReadUsers(reader);
        
        assertNotNull(users);
        assertEquals(0, users.size());
    }

    @Test
    public void DF11_movieIdWithSpaces() {
        String data = "John Doe,123456789\n M001 , M002 , M003 \n";
        BufferedReader reader = new BufferedReader(new StringReader(data));
        
        UserFilereader filereader = new UserFilereader();
        ArrayList<User> users = filereader.ReadUsers(reader);
        
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(3, users.get(0).getLikedMovieIds().length);
        assertEquals("M001", users.get(0).getLikedMovieIds()[0]);
    }

    @Test
    public void DF12_userIdFormat() {
        String data = "John Doe,123456789\nM001\n";
        BufferedReader reader = new BufferedReader(new StringReader(data));
        
        UserFilereader filereader = new UserFilereader();
        ArrayList<User> users = filereader.ReadUsers(reader);
        
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(9, users.get(0).getUserId().length());
    }

    @Test
    public void DF13_singleLikedMovie() {
        String data = "John Doe,123456789\nM001\n";
        BufferedReader reader = new BufferedReader(new StringReader(data));
        
        UserFilereader filereader = new UserFilereader();
        ArrayList<User> users = filereader.ReadUsers(reader);
        
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(1, users.get(0).getLikedMovieIds().length);
    }

    @Test
    public void DF14_manyBlankLinesAtStart() {
        String data = "\n\n\nJohn Doe,123456789\nM001\n";
        BufferedReader reader = new BufferedReader(new StringReader(data));
        
        UserFilereader filereader = new UserFilereader();
        ArrayList<User> users = filereader.ReadUsers(reader);
        
        assertNotNull(users);
        assertEquals(1, users.size());
    }

    @Test
    public void DF15_movieIdListParsing() {
        String data = "User One,111111111\nMOV1,MOV2,MOV3,MOV4,MOV5\n";
        BufferedReader reader = new BufferedReader(new StringReader(data));
        
        UserFilereader filereader = new UserFilereader();
        ArrayList<User> users = filereader.ReadUsers(reader);
        
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(5, users.get(0).getLikedMovieIds().length);
    }

    @Test
    public void DF16_dataFlowUserNameToObject() {
        String data = "Alice Wonder,111111111\nM100,M200\n";
        BufferedReader reader = new BufferedReader(new StringReader(data));
        
        UserFilereader filereader = new UserFilereader();
        ArrayList<User> users = filereader.ReadUsers(reader);
        
        assertNotNull(users);
        assertEquals(1, users.size());
        User user = users.get(0);
        assertEquals("Alice Wonder", user.getName());
        assertEquals("111111111", user.getUserId());
        assertEquals("M100", user.getLikedMovieIds()[0]);
    }

    @Test
    public void DF17_userIdTrimming() {
        String data = "User Name,  987654321  \nM001\n";
        BufferedReader reader = new BufferedReader(new StringReader(data));
        
        UserFilereader filereader = new UserFilereader();
        ArrayList<User> users = filereader.ReadUsers(reader);
        
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("987654321", users.get(0).getUserId());
    }
}

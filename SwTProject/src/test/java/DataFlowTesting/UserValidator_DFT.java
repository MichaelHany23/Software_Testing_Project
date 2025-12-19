package DataFlowTesting;

import com.mycompany.swtproject.User;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserValidator_DFT {

    @Test
    public void DF01_validUserData() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("John Doe", "123456789", new String[]{"M001", "M002"}));

        com.mycompany.swtproject.UserValidator validator =
                new com.mycompany.swtproject.UserValidator(users);
        validator.Validate();

        assertTrue(validator.ErrorIsEmpty());
        assertNotNull(validator.getUsers());
    }

    @Test
    public void DF02_invalidUserNameNull() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User(null, "123456789", new String[]{"M001"}));

        com.mycompany.swtproject.UserValidator validator =
                new com.mycompany.swtproject.UserValidator(users);
        validator.Validate();

        assertFalse(validator.ErrorIsEmpty());
        assertTrue(validator.getUser_errors().get(0).contains("User Name"));
    }

    @Test
    public void DF03_invalidUserNameEmpty() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("", "123456789", new String[]{"M001"}));

        com.mycompany.swtproject.UserValidator validator =
                new com.mycompany.swtproject.UserValidator(users);
        validator.Validate();

        assertFalse(validator.ErrorIsEmpty());
        assertTrue(validator.getUser_errors().get(0).contains("User Name"));
    }

    @Test
    public void DF04_invalidUserNameStartsWithSpace() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User(" John Doe", "123456789", new String[]{"M001"}));

        com.mycompany.swtproject.UserValidator validator =
                new com.mycompany.swtproject.UserValidator(users);
        validator.Validate();

        assertFalse(validator.ErrorIsEmpty());
        assertTrue(validator.getUser_errors().get(0).contains("User Name"));
    }

    @Test
    public void DF05_invalidUserNameNonAlphabetic() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("John@Doe", "123456789", new String[]{"M001"}));

        com.mycompany.swtproject.UserValidator validator =
                new com.mycompany.swtproject.UserValidator(users);
        validator.Validate();

        assertFalse(validator.ErrorIsEmpty());
        assertTrue(validator.getUser_errors().get(0).contains("User Name"));
    }

    @Test
    public void DF06_validUserNameWithSpaces() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("John Mary Doe", "123456789", new String[]{"M001"}));

        com.mycompany.swtproject.UserValidator validator =
                new com.mycompany.swtproject.UserValidator(users);
        validator.Validate();

        assertTrue(validator.ErrorIsEmpty());
        assertNotNull(validator.getUsers());
    }

    @Test
    public void DF07_invalidUserIdNull() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("John Doe", null, new String[]{"M001"}));

        com.mycompany.swtproject.UserValidator validator =
                new com.mycompany.swtproject.UserValidator(users);
        validator.Validate();

        assertFalse(validator.ErrorIsEmpty());
        assertTrue(validator.getUser_errors().get(0).contains("User ID"));
    }

    @Test
    public void DF08_invalidUserIdEmpty() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("John Doe", "", new String[]{"M001"}));

        com.mycompany.swtproject.UserValidator validator =
                new com.mycompany.swtproject.UserValidator(users);
        validator.Validate();

        assertFalse(validator.ErrorIsEmpty());
        assertTrue(validator.getUser_errors().get(0).contains("User ID"));
    }

    @Test
    public void DF09_invalidUserIdWrongLength() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("John Doe", "12345678", new String[]{"M001"})); // 8 chars instead of 9

        com.mycompany.swtproject.UserValidator validator =
                new com.mycompany.swtproject.UserValidator(users);
        validator.Validate();

        assertFalse(validator.ErrorIsEmpty());
        assertTrue(validator.getUser_errors().get(0).contains("User ID"));
    }

    @Test
    public void DF10_invalidUserIdNotStartWithDigit() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("John Doe", "A23456789", new String[]{"M001"}));

        com.mycompany.swtproject.UserValidator validator =
                new com.mycompany.swtproject.UserValidator(users);
        validator.Validate();

        assertFalse(validator.ErrorIsEmpty());
        assertTrue(validator.getUser_errors().get(0).contains("User ID"));
    }

    @Test
    public void DF11_invalidUserIdNonAlphanumeric() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("John Doe", "123@56789", new String[]{"M001"}));

        com.mycompany.swtproject.UserValidator validator =
                new com.mycompany.swtproject.UserValidator(users);
        validator.Validate();

        assertFalse(validator.ErrorIsEmpty());
        assertTrue(validator.getUser_errors().get(0).contains("User ID"));
    }

    @Test
    public void DF12_validUserIdEndWithLetter() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("John Doe", "123456789A", new String[]{"M001"}));

        com.mycompany.swtproject.UserValidator validator =
                new com.mycompany.swtproject.UserValidator(users);
        validator.Validate();

        assertTrue(validator.ErrorIsEmpty());
        assertNotNull(validator.getUsers());
    }

    @Test
    public void DF13_invalidUserIdTwoLettersAtEnd() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("John Doe", "123456AB", new String[]{"M001"})); // Two letters at end

        com.mycompany.swtproject.UserValidator validator =
                new com.mycompany.swtproject.UserValidator(users);
        validator.Validate();

        assertFalse(validator.ErrorIsEmpty());
        assertTrue(validator.getUser_errors().get(0).contains("User ID"));
    }

    @Test
    public void DF14_duplicateUserId() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("John Doe", "123456789", new String[]{"M001"}));
        users.add(new User("Jane Smith", "123456789", new String[]{"M002"}));

        com.mycompany.swtproject.UserValidator validator =
                new com.mycompany.swtproject.UserValidator(users);
        validator.Validate();

        assertFalse(validator.ErrorIsEmpty());
        assertTrue(validator.getUser_errors().get(1).contains("User ID"));
    }

    @Test
    public void DF15_getUsersNullOnError() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("John Doe", "12345678", new String[]{"M001"})); // Invalid ID length

        com.mycompany.swtproject.UserValidator validator =
                new com.mycompany.swtproject.UserValidator(users);
        validator.Validate();

        assertFalse(validator.ErrorIsEmpty());
        assertNull(validator.getUsers());
    }

    @Test
    public void DF16_multipleInvalidUsers() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("John@Doe", "123456789", new String[]{"M001"})); // Invalid name
        users.add(new User("Jane Smith", "12345678", new String[]{"M002"})); // Invalid ID length

        com.mycompany.swtproject.UserValidator validator =
                new com.mycompany.swtproject.UserValidator(users);
        validator.Validate();

        assertFalse(validator.ErrorIsEmpty());
        assertTrue(validator.getUser_errors().size() >= 2);
    }

    @Test
    public void DF17_validUserIdMixedAlphanumeric() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("John Doe", "123AB5C78D", new String[]{"M001"}));

        com.mycompany.swtproject.UserValidator validator =
                new com.mycompany.swtproject.UserValidator(users);
        validator.Validate();

        assertTrue(validator.ErrorIsEmpty());
        assertNotNull(validator.getUsers());
    }

    @Test
    public void DF18_emptyUserList() {
        ArrayList<User> users = new ArrayList<>();

        com.mycompany.swtproject.UserValidator validator =
                new com.mycompany.swtproject.UserValidator(users);
        validator.Validate();

        assertTrue(validator.ErrorIsEmpty());
        assertNotNull(validator.getUsers());
    }
}

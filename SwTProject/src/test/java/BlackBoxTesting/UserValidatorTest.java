/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package BlackBoxTesting;

import com.mycompany.swtproject.User;
import com.mycompany.swtproject.UserValidator;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Black Box Testing for UserValidator
 */
public class UserValidatorTest {

    private ArrayList<User> usersList;
    private UserValidator validator;

    public UserValidatorTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        usersList = new ArrayList<>();
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testValidUser() {
        // Arrange
        // Name: Alphabetic and spaces, no leading space.
        // ID: 9 chars, starts with digit, alphanumeric, ends with letter only if
        // preceeded by digit.
        usersList.add(new User("Alice Smith", "12345678A", new String[] {}));
        validator = new UserValidator(usersList);

        // Act
        validator.Validate();

        // Assert
        assertTrue(validator.ErrorIsEmpty(), "Valid user should have no errors");
    }

    @Test
    public void testValidUserAllDigitsId() {
        // Arrange
        usersList.add(new User("Bob", "123456789", new String[] {}));
        validator = new UserValidator(usersList);

        // Act
        validator.Validate();

        // Assert
        assertTrue(validator.ErrorIsEmpty(), "Valid user with all digit ID should have no errors");
    }

    @Test
    public void testInvalidNameStartsWithSpace() {
        // Arrange
        usersList.add(new User(" Alice", "123456789", new String[] {}));
        validator = new UserValidator(usersList);

        // Act
        validator.Validate();

        // Assert
        assertFalse(validator.ErrorIsEmpty(), "Name starting with space should be invalid");
        assertTrue(validator.getUser_errors().get(0).contains("User Name"), "Error message should mention User Name");
    }

    @Test
    public void testInvalidNameNonAlphabetic() {
        // Arrange
        usersList.add(new User("Alice123", "123456789", new String[] {}));
        validator = new UserValidator(usersList);

        // Act
        validator.Validate();

        // Assert
        assertFalse(validator.ErrorIsEmpty(), "Name with digits should be invalid");
    }

    @Test
    public void testInvalidNameEmpty() {
        // Arrange
        usersList.add(new User("", "123456789", new String[] {}));
        validator = new UserValidator(usersList);

        // Act
        validator.Validate();

        // Assert
        assertFalse(validator.ErrorIsEmpty(), "Empty name should be invalid");
    }

    @Test
    public void testInvalidIdLength() {
        // Arrange
        usersList.add(new User("Alice", "12345678", new String[] {})); // 8 chars
        validator = new UserValidator(usersList);
        validator.Validate();
        assertFalse(validator.ErrorIsEmpty(), "ID with 8 chars should be invalid");

        usersList.clear();
        usersList.add(new User("Alice", "1234567890", new String[] {})); // 10 chars
        validator = new UserValidator(usersList);
        validator.Validate();
        assertFalse(validator.ErrorIsEmpty(), "ID with 10 chars should be invalid");
    }

    @Test
    public void testInvalidIdStartsWithLetter() {
        // Arrange
        usersList.add(new User("Alice", "A23456789", new String[] {}));
        validator = new UserValidator(usersList);

        // Act
        validator.Validate();

        // Assert
        assertFalse(validator.ErrorIsEmpty(), "ID starting with letter should be invalid");
    }

    @Test
    public void testInvalidIdSpecialChars() {
        // Arrange
        usersList.add(new User("Alice", "1234-6789", new String[] {}));
        validator = new UserValidator(usersList);

        // Act
        validator.Validate();

        // Assert
        assertFalse(validator.ErrorIsEmpty(), "ID with special chars should be invalid");
    }

    @Test
    public void testInvalidIdEndingLogic() {
        // Rule: ends with letter -> ensure previous is not a letter
        // So 1234567AB is invalid (B is letter, A is letter)
        usersList.add(new User("Alice", "1234567AB", new String[] {}));
        validator = new UserValidator(usersList);

        // Act
        validator.Validate();

        // Assert
        assertFalse(validator.ErrorIsEmpty(), "ID ending with two letters should be invalid");
    }

    @Test
    public void testDuplicateUserIds() {
        usersList.add(new User("Alice", "123456789", new String[] {}));
        usersList.add(new User("Bob", "123456789", new String[] {}));

        validator = new UserValidator(usersList);
        validator.Validate();

        assertFalse(validator.ErrorIsEmpty(), "Duplicate User IDs should result in error");
    }
}

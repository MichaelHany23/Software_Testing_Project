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
 * White Box Testing for UserValidator
 * 
 * This test class focuses on INTERNAL code structure and execution paths:
 * - Statement Coverage: Every line of code is executed at least once
 * - Branch Coverage: All TRUE and FALSE branches of conditional statements
 * - Condition Coverage: Different combinations of boolean conditions
 * - Loop Coverage: Boundary conditions and iterations in loops
 * 
 * Unlike Black Box Testing which tests functionality from outside,
 * White Box Testing verifies that EVERY code path has been tested.
 * 
 * @author Michael
 */
public class UserValidator_WBT {
    
    public UserValidator_WBT() {
    }
    
    // ============== STATEMENT COVERAGE ==============
    // Statement Coverage ensures EVERY statement in the code executes at least once.
    // This is the most basic level of code coverage.
    // We test different input scenarios to force execution through different statements.
    
    @Test
    public void StatementCov_1()
    {
        // TEST PURPOSE: Verify valid user passes ALL validations
        // This is the "happy path" - all validations succeed
        // INPUT: Valid username (has space, starts with capital) and valid userId (9 chars, starts with digit)
        // COVERS: All validation statements where conditions are FALSE (no errors added)
        // EXPECTED: No errors, validation passes
        
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setName("Michael Sameh");  // Valid: alphabetic + space, doesn't start with space
        u1.setUserId("123456789");     // Valid: exactly 9 chars, starts with digit, alphanumeric, ends with digit
        String[] likedMovieIds = {"BDK123"};
        u1.setLikedMovieIds(likedMovieIds);
        users.add(u1);
        
        UserValidator UV = new UserValidator(users);
        UV.Validate();
        
        assertTrue(UV.ErrorIsEmpty());
    }
    
    @Test
    public void StatementCov_2()
    {
        // TEST PURPOSE: Verify null username triggers error
        // CODE PATH: if (currentUsername == null || currentUsername.isEmpty())
        // INPUT: null username
        // COVERS: TRUE branch of null/empty check statement
        // EXPECTED: Error added, validation fails
    
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setName(null);
        u1.setUserId("123456789");
        users.add(u1);
        
        UserValidator UV = new UserValidator(users);
        UV.Validate();
        
        assertFalse(UV.ErrorIsEmpty());
        assertEquals(UV.getUser_errors().get(0), "ERROR: User Name {null} is wrong");
    }
    
    @Test
    public void StatementCov_3()
    {
        // Username is empty string - covers empty check statement
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setName("");
        u1.setUserId("123456789");
        users.add(u1);
        
        UserValidator UV = new UserValidator(users);
        UV.Validate();
        
        assertFalse(UV.ErrorIsEmpty());
        assertEquals(UV.getUser_errors().get(0), "ERROR: User Name {} is wrong");
    }
    
    @Test
    public void StatementCov_4()
    {
        // Username starts with space - covers space check statement
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setName(" Michael");
        u1.setUserId("123456789");
        users.add(u1);
        
        UserValidator UV = new UserValidator(users);
        UV.Validate();
        
        assertFalse(UV.ErrorIsEmpty());
        assertEquals(UV.getUser_errors().get(0), "ERROR: User Name { Michael} is wrong");
    }
    
    @Test
    public void StatementCov_5()
    {
        // TEST PURPOSE: Verify non-alphabetic characters cause validation error
        // CODE PATH: for (int j = 0; j < currentUsername.length(); j++) with if (!Character.isAlphabetic(c) && c != ' ')
        // INPUT: "Michael@Sameh" (contains @ which is neither alphabetic nor space)
        // COVERS: TRUE branch of character validation loop - executes error statement
        // EXPECTED: Error about invalid character
        
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setName("Michael@Sameh");
        u1.setUserId("123456789");
        users.add(u1);
        
        UserValidator UV = new UserValidator(users);
        UV.Validate();
        
        assertFalse(UV.ErrorIsEmpty());
        assertEquals(UV.getUser_errors().get(0), "ERROR: User Name {Michael@Sameh} is wrong");
    }
    
    @Test
    public void StatementCov_6()
    {
        // Valid username, but userId is null - covers userId null check
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setName("Michael Sameh");
        u1.setUserId(null);
        users.add(u1);
        
        UserValidator UV = new UserValidator(users);
        UV.Validate();
        
        assertFalse(UV.ErrorIsEmpty());
        assertEquals(UV.getUser_errors().get(0), "ERROR: User ID {null} is wrong");
    }
    
    @Test
    public void StatementCov_7()
    {
        // Valid username, but userId is empty - covers userId empty check
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setName("Michael Sameh");
        u1.setUserId("");
        users.add(u1);
        
        UserValidator UV = new UserValidator(users);
        UV.Validate();
        
        assertFalse(UV.ErrorIsEmpty());
        assertEquals(UV.getUser_errors().get(0), "ERROR: User ID {} is wrong");
    }
    
    @Test
    public void StatementCov_8()
    {
        // userId wrong length (less than 9) - covers length check
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setName("Michael Sameh");
        u1.setUserId("12345678");
        users.add(u1);
        
        UserValidator UV = new UserValidator(users);
        UV.Validate();
        
        assertFalse(UV.ErrorIsEmpty());
        assertEquals(UV.getUser_errors().get(0), "ERROR: User ID {12345678} is wrong");
    }
    
    @Test
    public void StatementCov_9()
    {
        // userId wrong length (more than 9) - covers length check
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setName("Michael Sameh");
        u1.setUserId("1234567890");
        users.add(u1);
        
        UserValidator UV = new UserValidator(users);
        UV.Validate();
        
        assertFalse(UV.ErrorIsEmpty());
        assertEquals(UV.getUser_errors().get(0), "ERROR: User ID {1234567890} is wrong");
    }
    
    @Test
    public void StatementCov_10()
    {
        // userId doesn't start with digit - covers first char digit check
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setName("Michael Sameh");
        u1.setUserId("A23456789");
        users.add(u1);
        
        UserValidator UV = new UserValidator(users);
        UV.Validate();
        
        assertFalse(UV.ErrorIsEmpty());
        assertEquals(UV.getUser_errors().get(0), "ERROR: User ID {A23456789} is wrong");
    }
    
    @Test
    public void StatementCov_11()
    {
        // userId contains non-alphanumeric character - covers alphanumeric check
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setName("Michael Sameh");
        u1.setUserId("1234@6789");
        users.add(u1);
        
        UserValidator UV = new UserValidator(users);
        UV.Validate();
        
        assertFalse(UV.ErrorIsEmpty());
        assertEquals(UV.getUser_errors().get(0), "ERROR: User ID {1234@6789} is wrong");
    }
    
    @Test
    public void StatementCov_12()
    {
        // TEST PURPOSE: Verify userId can only end with ONE letter (if any)
        // CODE PATH: if (j == currentid.length()-1 && Character.isAlphabetic(c)) with if (Character.isAlphabetic(currentid.charAt(j-1)))
        // INPUT: "1234567AB" (ends with 2 letters)
        // COVERS: Both letter-ending conditions - detects invalid double letter ending
        // EXPECTED: Error about invalid userId format
        // WHY: Business rule: "may end with only one alphabet" (not two consecutive letters)
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setName("Michael Sameh");
        u1.setUserId("1234567AB");
        users.add(u1);
        
        UserValidator UV = new UserValidator(users);
        UV.Validate();
        
        assertFalse(UV.ErrorIsEmpty());
        assertEquals(UV.getUser_errors().get(0), "ERROR: User ID {1234567AB} is wrong");
    }
    
    @Test
    public void StatementCov_13()
    {
        // Duplicate userId - covers duplicate check
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setName("Michael Sameh");
        u1.setUserId("123456789");
        users.add(u1);
        
        User u2 = new User();
        u2.setName("John Smith");
        u2.setUserId("123456789");
        users.add(u2);
        
        UserValidator UV = new UserValidator(users);
        UV.Validate();
        
        assertFalse(UV.ErrorIsEmpty());
        assertEquals(UV.getUser_errors().get(0), "ERROR: User ID {123456789} is wrong");
    }
    
    // ============== BRANCH COVERAGE ==============
    // Branch Coverage tests both TRUE and FALSE paths of every if/else decision
    // This ensures that BOTH branches of conditional statements are executed
    // Example: if (x > 5) has 2 branches - one when TRUE, one when FALSE
    
    @Test
    public void BranchCov_1()
    {
        // TEST PURPOSE: Verify outer loop is skipped when user list is empty
        // CODE PATH: for (int i = 0; i < users.size(); i++)
        // INPUT: Empty ArrayList (size = 0)
        // COVERS: Loop condition FALSE branch - loop body doesn't execute at all
        // EXPECTED: No errors, validation completes without processing anyone
        // WHY: Tests boundary condition - what happens when there are 0 users to validate?
        ArrayList<User> users = new ArrayList<>();
        
        UserValidator UV = new UserValidator(users);
        UV.Validate();
        
        assertTrue(UV.ErrorIsEmpty());
        assertEquals(UV.getUsers().size(), 0);
    }
    
    @Test
    public void BranchCov_2()
    {
        // TEST PURPOSE: Verify all validation conditions take FALSE branch for valid input
        // CODE PATH: Tests the "happy path" through validation
        // INPUT: Valid user with correct name and userId
        // COVERS: FALSE branch of EVERY if condition (no errors added)
        // EXPECTED: No errors, validation passes completely
        // WHY: Need to verify validation succeeds when all rules are met
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setName("Michael Sameh");    // Valid name
        u1.setUserId("123456789");      // Valid userId
        users.add(u1);
        
        UserValidator UV = new UserValidator(users);
        UV.Validate();
        
        assertTrue(UV.ErrorIsEmpty());
    }
    
    @Test
    public void BranchCov_3()
    {
        // TRUE branch: username is null (first if condition)
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setName(null);
        u1.setUserId("123456789");
        users.add(u1);
        
        UserValidator UV = new UserValidator(users);
        UV.Validate();
        
        assertFalse(UV.ErrorIsEmpty());
    }
    
    @Test
    public void BranchCov_4()
    {
        // TRUE branch: starts with space (second if condition)
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setName(" Michael");
        u1.setUserId("123456789");
        users.add(u1);
        
        UserValidator UV = new UserValidator(users);
        UV.Validate();
        
        assertFalse(UV.ErrorIsEmpty());
    }
    
    @Test
    public void BranchCov_5()
    {
        // TRUE branch: non-alphabetic character in username inner loop
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setName("Michael1Sameh");
        u1.setUserId("123456789");
        users.add(u1);
        
        UserValidator UV = new UserValidator(users);
        UV.Validate();
        
        assertFalse(UV.ErrorIsEmpty());
    }
    
    @Test
    public void BranchCov_6()
    {
        // TRUE branch: userId is null
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setName("Michael Sameh");
        u1.setUserId(null);
        users.add(u1);
        
        UserValidator UV = new UserValidator(users);
        UV.Validate();
        
        assertFalse(UV.ErrorIsEmpty());
    }
    
    @Test
    public void BranchCov_7()
    {
        // TRUE branch: userId length != 9
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setName("Michael Sameh");
        u1.setUserId("12345");
        users.add(u1);
        
        UserValidator UV = new UserValidator(users);
        UV.Validate();
        
        assertFalse(UV.ErrorIsEmpty());
    }
    
    @Test
    public void BranchCov_8()
    {
        // TRUE branch: first character is not digit
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setName("Michael Sameh");
        u1.setUserId("A23456789");
        users.add(u1);
        
        UserValidator UV = new UserValidator(users);
        UV.Validate();
        
        assertFalse(UV.ErrorIsEmpty());
    }
    
    @Test
    public void BranchCov_9()
    {
        // TRUE branch: non-alphanumeric character in userId validation loop
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setName("Michael Sameh");
        u1.setUserId("123456@89");
        users.add(u1);
        
        UserValidator UV = new UserValidator(users);
        UV.Validate();
        
        assertFalse(UV.ErrorIsEmpty());
    }
    
    @Test
    public void BranchCov_10()
    {
        // TRUE branch: ends with letter and previous is also letter
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setName("Michael Sameh");
        u1.setUserId("1234567AB");
        users.add(u1);
        
        UserValidator UV = new UserValidator(users);
        UV.Validate();
        
        assertFalse(UV.ErrorIsEmpty());
    }
    
    @Test
    public void BranchCov_11()
    {
        // FALSE branch: ends with letter but previous is digit (valid)
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setName("Michael Sameh");
        u1.setUserId("123456789");
        users.add(u1);
        
        UserValidator UV = new UserValidator(users);
        UV.Validate();
        
        assertTrue(UV.ErrorIsEmpty());
    }
    
    @Test
    public void BranchCov_12()
    {
        // Ends with single letter (valid case)
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setName("Michael Sameh");
        u1.setUserId("12345678A");
        users.add(u1);
        
        UserValidator UV = new UserValidator(users);
        UV.Validate();
        
        assertTrue(UV.ErrorIsEmpty());
    }
    
    @Test
    public void BranchCov_13()
    {
        // TEST PURPOSE: Verify duplicate userId detection works
        // CODE PATH: for (int j = i-1; j >= 0; j--) with if(previousId != null && currentid.equals(previousId))
        // INPUT: Two users with same userId "123456789"
        // COVERS: TRUE branch of duplicate detection - finds match and reports error
        // EXPECTED: Error on second user with duplicate ID
        // WHY: Business rule: "must be unique (no duplicates)"
        //      Tests inner backward loop that searches previous users
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setName("Michael Sameh");
        u1.setUserId("123456789");
        users.add(u1);
        
        User u2 = new User();
        u2.setName("John Smith");
        u2.setUserId("123456789");  // Same ID as u1 - duplicate!
        users.add(u2);
        
        UserValidator UV = new UserValidator(users);
        UV.Validate();
        
        assertFalse(UV.ErrorIsEmpty());
    }
    
    @Test
    public void BranchCov_14()
    {
        // FALSE branch: no duplicate userId found
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setName("Michael Sameh");
        u1.setUserId("123456789");
        users.add(u1);
        
        User u2 = new User();
        u2.setName("John Smith");
        u2.setUserId("987654321");
        users.add(u2);
        
        UserValidator UV = new UserValidator(users);
        UV.Validate();
        
        assertTrue(UV.ErrorIsEmpty());
    }
    
    // ============== CONDITION COVERAGE ==============
    // Condition Coverage tests different combinations of boolean conditions
    // When we have: if (!Character.isAlphabetic(c) && c != ' ')
    // We need to test: (F && F), (F && T), (T && F), (T && T)
    
    @Test
    public void ConditionCov_1()
    {
        // TEST PURPOSE: Verify spaces in middle of username are allowed
        // CONDITION: Character validation loop with (c != ' ') check
        // INPUT: "Michael    Sameh    Hany" (multiple spaces allowed between words)
        // COVERS: When c is space, condition (c != ' ') is FALSE - space is allowed
        // EXPECTED: Valid username accepted
        // WHY: Business rule allows spaces as separators - tests this explicitly
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setName("Michael    Sameh    Hany");
        u1.setUserId("123456789");
        users.add(u1);
        
        UserValidator UV = new UserValidator(users);
        UV.Validate();
        
        assertTrue(UV.ErrorIsEmpty());
    }
    
    @Test
    public void ConditionCov_2()
    {
        // All lowercase username (valid)
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setName("michael sameh");
        u1.setUserId("123456789");
        users.add(u1);
        
        UserValidator UV = new UserValidator(users);
        UV.Validate();
        
        assertTrue(UV.ErrorIsEmpty());
    }
    
    @Test
    public void ConditionCov_3()
    {
        // Mixed case username (valid)
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setName("MiChAeL SaMeH");
        u1.setUserId("123456789");
        users.add(u1);
        
        UserValidator UV = new UserValidator(users);
        UV.Validate();
        
        assertTrue(UV.ErrorIsEmpty());
    }
    
    @Test
    public void ConditionCov_4()
    {
        // userId: mixed alphanumeric with letter at end (valid)
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setName("Michael Sameh");
        u1.setUserId("1AbCdEfG9");
        users.add(u1);
        
        UserValidator UV = new UserValidator(users);
        UV.Validate();
        
        assertTrue(UV.ErrorIsEmpty());
    }
    
    @Test
    public void ConditionCov_5()
    {
        // userId: all digits (valid)
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setName("Michael Sameh");
        u1.setUserId("123456789");
        users.add(u1);
        
        UserValidator UV = new UserValidator(users);
        UV.Validate();
        
        assertTrue(UV.ErrorIsEmpty());
    }
    
    // ============== LOOP COVERAGE ==============
    // Loop Coverage tests boundary conditions:
    // 0 iterations (loop doesn't run), 1 iteration (minimum), n iterations (multiple)
    // Also tests loop exit conditions and break statements
    
    @Test
    public void LoopCov_1()
    {
        // TEST PURPOSE: Verify outer loop executes for single user
        // LOOP: for (int i = 0; i < users.size(); i++)
        // INPUT: 1 user in list
        // COVERS: Loop runs exactly 1 time (minimum iteration count)
        // EXPECTED: Single user is validated successfully
        // WHY: Tests loop doesn't skip when size = 1, boundary condition
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setName("Michael");
        u1.setUserId("123456789");
        users.add(u1);
        
        UserValidator UV = new UserValidator(users);
        UV.Validate();
        
        assertTrue(UV.ErrorIsEmpty());
    }
    
    @Test
    public void LoopCov_2()
    {
        // Multiple users - outer loop executes multiple times
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setName("Michael One");
        u1.setUserId("123456789");
        users.add(u1);
        
        User u2 = new User();
        u2.setName("Michael Two");
        u2.setUserId("234567890");
        users.add(u2);
        
        User u3 = new User();
        u3.setName("Michael Three");
        u3.setUserId("345678901");
        users.add(u3);
        
        UserValidator UV = new UserValidator(users);
        UV.Validate();
        
        assertTrue(UV.ErrorIsEmpty());
    }
    
    @Test
    public void LoopCov_3()
    {
        // Username with single character (loop executes once)
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setName("M");
        u1.setUserId("123456789");
        users.add(u1);
        
        UserValidator UV = new UserValidator(users);
        UV.Validate();
        
        assertTrue(UV.ErrorIsEmpty());
    }
    
    @Test
    public void LoopCov_4()
    {
        // Username with many characters (loop executes multiple times)
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setName("MichaelSamehHanyAhmedWagdyAbdallah");
        u1.setUserId("123456789");
        users.add(u1);
        
        UserValidator UV = new UserValidator(users);
        UV.Validate();
        
        assertTrue(UV.ErrorIsEmpty());
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
}

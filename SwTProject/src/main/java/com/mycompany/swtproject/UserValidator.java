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

    ArrayList<String> User_errors;
    ArrayList<User> users;

    public UserValidator(ArrayList<User> users) {
        this.users = users;
        User_errors = new ArrayList<>();
    }

    public void Validate() {
        UserValidate();
    }

    // getter for examining errors
    public ArrayList<String> getUser_errors() {
        return User_errors;
    }

    public boolean ErrorIsEmpty() {
        return User_errors.isEmpty();
    }

    public ArrayList<User> getUsers() {
        if (ErrorIsEmpty()) {
            return users;
        }
        return null;
    }

    private void UserValidate() {
        // It must be Alphabetic characters and Spaces. The name should not start with
        // space.
        for (int i = 0; i < users.size(); i++) {
            boolean invalid = false; // flag used within loop to skip outer loop for wrong users

            // USERNAME VALIDATION
            String currentUsername = users.get(i).getName();

            // check if username is null or empty
            if (currentUsername == null || currentUsername.isEmpty()) {
                User_errors.add("ERROR: User Name {" + currentUsername + "} is wrong");
                continue;
            }

            // shouldn't start with space
            if (currentUsername.charAt(0) == ' ') {
                User_errors.add("ERROR: User Name {" + currentUsername + "} is wrong");
                continue;
            }

            // all characters must be alphabetic or space
            for (int j = 0; j < currentUsername.length(); j++) {
                char c = currentUsername.charAt(j);
                if (!Character.isAlphabetic(c) && c != ' ') {
                    User_errors.add("ERROR: User Name {" + currentUsername + "} is wrong");
                    invalid = true;
                    break;
                }
            }
            if (invalid) {
                continue;
            }

            ///////////////////////////////
            // USER ID VALIDATION

            String currentid = users.get(i).getUserId();

            // check if userId is null or empty
            if (currentid == null || currentid.isEmpty()) {
                User_errors.add("ERROR: User ID {" + currentid + "} is wrong");
                continue;
            }

            // rule 1: 9 characters exactly
            if (currentid.length() != 9) {
                User_errors.add("ERROR: User ID {" + currentid + "} is wrong");
                continue;
            }

            // rule 2: must start with digit
            if (!Character.isDigit(currentid.charAt(0))) {
                User_errors.add("ERROR: User ID {" + currentid + "} is wrong");
                continue;
            }

            // rule 3: must be alphanumeric and may end with only one alphabet
            for (int j = 1; j < currentid.length(); j++) {
                char c = currentid.charAt(j);

                if (!Character.isAlphabetic(c) && !Character.isDigit(c)) {
                    User_errors.add("ERROR: User ID {" + currentid + "} is wrong");
                    invalid = true;
                    break;
                }

                if (j == currentid.length() - 1 && Character.isAlphabetic(c)) {
                    // ends with letter -> ensure previous is not a letter
                    if (Character.isAlphabetic(currentid.charAt(j - 1))) {
                        User_errors.add("ERROR: User ID {" + currentid + "} is wrong");
                        invalid = true;
                        break;
                    }
                }
            }
            if (invalid) {
                continue;
            }

            // rule 4: must be unique (no duplicates)
            for (int j = i - 1; j >= 0; j--) {
                String previousId = users.get(j).getUserId();
                if (previousId != null && currentid.equals(previousId)) {
                    User_errors.add("ERROR: User ID {" + currentid + "} is wrong");
                    invalid = true;
                    break;
                }
            }
            if (invalid) {
                continue;
            }
        }
    }
}

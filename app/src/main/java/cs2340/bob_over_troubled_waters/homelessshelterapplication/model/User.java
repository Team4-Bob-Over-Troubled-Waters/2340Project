package cs2340.bob_over_troubled_waters.homelessshelterapplication.model;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Admin on 2/9/2018.
 */

public class User extends Thread {
    private String email;
    private int passwordHash;
    private boolean isAdmin;
    private String name;

    // eventually this should be replaced by a database
    private static HashMap<String, User> existingUsers = new HashMap<>();

    static {
        new User("user@example.com", "pass", "User", false);
        new User("admin@example.com", "pass", null, true);
    }

    // the user who is logged in currently
    private static User currentUser = null;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public String getUsersName() {
        if (name == null) return email;
        else return name;
    }

    /**
     * constructs a user object
     * @param email the email the user should have
     * @param password the password for the user
     * @param isAdmin whether the user should be an admin
     * @throws IllegalArgumentException if a field is null or the email is taken
     */
    public User(String email, String password, String name, boolean isAdmin)
            throws IllegalArgumentException {
        if (email == null) throw new IllegalArgumentException(
                "Email is required");
        if (password == null) throw new IllegalArgumentException(
                "Password is required");
        this.email = email;
        this.passwordHash = password.hashCode();
        if (name != null && !name.isEmpty()) this.name = name;
        this.isAdmin = isAdmin;
        if (existingUsers.containsKey(email)) {
            throw new IllegalArgumentException("Username already exists");
        } else {
            existingUsers.put(email, this);
        }
    }

    /**
     * gets a user object with a specified username
     * @param username the username to find a matching user for
     * @return the user with that username, null if none exist
     */
    public static User getUser(String username) {
        return existingUsers.get(username);
    }

    /**
     * checks to see if the password entered for a user is correct
     * @param enteredPassword the password that was entered in login attempt
     * @return whether the password is correct
     */
    public boolean passwordCorrect(String enteredPassword) {
        return passwordHash == enteredPassword.hashCode();
    }

    public String getEmail() {
        return email;
    }

    /**
     * tells whether a user is an admin
     * @return isAdmin
     */
    public boolean isAdmin() {
        return isAdmin;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || !(other instanceof User)) return false;
        User that = (User) other;
        return (this.email.equals(that.email));
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }
}

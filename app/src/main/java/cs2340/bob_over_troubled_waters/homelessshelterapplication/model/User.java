package cs2340.bob_over_troubled_waters.homelessshelterapplication.model;

import com.google.firebase.database.DataSnapshot;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.interfacer.DataPoster;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.interfacer.SingleUserLoader;

/**
 * Created by Sarah on 2/9/2018.
 */

public abstract class User {

    protected String id;
    private String email;
    private String name;
    private boolean isBlocked = false;

    public boolean getIsBlocked() {
        return isBlocked;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    /**
     * toggles whether or not a user is blocked
     * @param blockedBy the admin user object for the admin that is blocking the user
     */
    public void toggleBlocked(AdminUser blockedBy) {
        if (blockedBy != null) {
            this.isBlocked = !this.isBlocked;
            DataPoster.post(this);
        } else {
            throw new IllegalArgumentException("Need Admin to block");
        }
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
        if (name == null || name.isEmpty()) return email;
        else return name;
    }

    /**
     * constructs a user object
     * @param email the email the user should have
     * @param password the password for the user
     * @throws IllegalArgumentException if a field is null or the email is taken
     */
    public User(String email, String password, String name) throws Exception {
        if (email == null) throw new IllegalArgumentException(
                "Email is required");
        if (password == null) throw new IllegalArgumentException(
                "Password is required");
        id = DataPoster.addFirebaseUser(email, password);
        this.email = email;
        if (name != null && !name.isEmpty()) this.name = name;
    }

    public User(DataSnapshot snapshot) {
        id = snapshot.getKey();
        email = snapshot.child("email").getValue(String.class);
        name = snapshot.child("name").getValue(String.class);
        isBlocked = snapshot.child("isBlocked").getValue(Boolean.class);
    }

    /**
     * attempts to login a user
     * do not call this method in the main thread
     * @param email email input by the user
     * @param password password input by the user
     * @return the user object of the user that was logged in
     * @throws Exception if there was a problem logging in - usually incorrect credentials
     */
    public static User attemptLogin(String email, String password) throws Exception {
        SingleUserLoader loginPerformer = new SingleUserLoader(email, password);
        currentUser = loginPerformer.execute();
        return currentUser;
    }

    public static void logout() {
        DataPoster.logout();
    }

    /**
     * get the string message to print on the user's home page
     * @return welcome message
     */
    public String getWelcomeMessage() {
        return String.format("Welcome %s!", getUsersName());
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        String val = email + "\n";
        if (this instanceof HomelessPerson) {
            val += "Homeless Person";
        } else if (this  instanceof AdminUser) {
            val += "Admin User";
        } else {
            val += "Shelter Employee";
        }
        return val;
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

    /**
     * gets a printable array of the user's info
     * @return each part of the user's info in an array
     */
    public abstract String[] getUserInfo();
}

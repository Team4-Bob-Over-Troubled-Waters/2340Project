package cs2340.bob_over_troubled_waters.homelessshelterapplication.model;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.interfacer.SingleUserLoader;

/**
 * Created by Sarah on 2/9/2018.
 */

public abstract class User {

    protected static DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    protected DatabaseReference path;

    private static FirebaseAuth auth = FirebaseAuth.getInstance();

    private FirebaseUser firebaseUser;

    protected String id;
    private String email;
    private String name;
    private boolean isBlocked = false;

    public boolean getIsBlocked() {
        return isBlocked;
    }

    /**
     * sets this user to be blocked
     * @param blockedBy the admin user object for the admin that is blocking the user
     */
    public void block(AdminUser blockedBy) {
        if (blockedBy != null) {
            this.isBlocked = true;
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
        if (name == null) return email;
        else return name;
    }

    /**
     * constructs a user object
     * @param email the email the user should have
     * @param password the password for the user
     * @throws IllegalArgumentException if a field is null or the email is taken
     */
    public User(String email, String password, String name)
            throws Exception {
        if (email == null) throw new IllegalArgumentException(
                "Email is required");
        if (password == null) throw new IllegalArgumentException(
                "Password is required");
        addFirebaseUser(email, password);
        if (firebaseException != null) {
            throw firebaseException;
        } else {
            if (name != null && !name.isEmpty()) {
                UserProfileChangeRequest updates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(name).build();
                firebaseUser.updateProfile(updates);
            }
        }
        this.email = email;
        if (name != null && !name.isEmpty()) this.name = name;
        path = database.child("users").child(id);
        path.child("email").setValue(email);
        path.child("name").setValue(name);
        path.child("isBlocked").setValue(getIsBlocked());
    }

    public User(DataSnapshot snapshot) {
        id = snapshot.getKey();
        email = snapshot.child("email").getValue(String.class);
        name = snapshot.child("name").getValue(String.class);
        isBlocked = snapshot.child("isBlocked").getValue(Boolean.class);
        path = database.child("users").child(id);
    }

    private static Exception firebaseException = null;

    private void addFirebaseUser(String email, String password) throws InterruptedException {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            firebaseUser = auth.getCurrentUser();
                            id = firebaseUser.getUid();
                            System.out.println("User: " + firebaseUser);
                        } else {
                            firebaseException = task.getException();
                        }
                    }
                });
        while (firebaseUser == null && firebaseException == null) {
            Thread.sleep(50);
        }
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
        auth.signOut();
        currentUser = null;
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
}

package cs2340.bob_over_troubled_waters.homelessshelterapplication.model;

import android.support.constraint.solver.widgets.Snapshot;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;

/**
 * Created by Admin on 3/5/2018.
 */

@IgnoreExtraProperties
public class AdminUser extends User {

    // these hash maps allow the admin to see add the users - map email to user object
    private static HashMap<String, User> adminUsers = new HashMap<>();
    private static HashMap<String, User> shelterEmployees = new HashMap<>();
    private static HashMap<String, User> homelessPeople = new HashMap<>();

    private boolean isApproved = false;

    public boolean isApproved() {
        return isApproved;
    }

    public AdminUser(String email, String password, String name) throws Exception {
        super(email, password, name);
        path.child("userType").setValue("admin");
        path.child("isApproved").setValue(isApproved);
    }

    public AdminUser(DataSnapshot snapshot) {
        super(snapshot);
        isApproved = snapshot.child("isApproved").getValue(Boolean.class);
    }

    /**
     * approve a newly registered admin user
     * @param newAdmin the newly registered admin user
     */
    public void approveAdmin(AdminUser newAdmin) {
        newAdmin.isApproved = true;
    }

    public void approveShelterEmployee(ShelterEmployee newEmployee) {
        if (newEmployee == null) {
            throw new IllegalArgumentException("Employee required");
        }
        newEmployee.approve(this);
    }

    /**
     * method to block a user
     * @param user the user to be blocked
     * @throws IllegalArgumentException if user is null or this
     */
    public void blockUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Cannot block null.");
        }
        if (this.equals(user)) {
            throw new IllegalArgumentException("Can't block yourself.");
        }
        user.block(this);
    }
}

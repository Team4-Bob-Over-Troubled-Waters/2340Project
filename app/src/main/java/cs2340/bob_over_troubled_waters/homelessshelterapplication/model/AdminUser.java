package cs2340.bob_over_troubled_waters.homelessshelterapplication.model;

/**
 * Created by Admin on 3/5/2018.
 */

public class AdminUser extends User {

    private boolean isApproved = false;

    public boolean isApproved() {
        return isApproved;
    }

    public AdminUser(String email, String password, String name) {
        super(email, password, name);
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

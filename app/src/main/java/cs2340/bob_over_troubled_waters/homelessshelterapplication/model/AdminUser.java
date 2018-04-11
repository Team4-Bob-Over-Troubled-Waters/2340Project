package cs2340.bob_over_troubled_waters.homelessshelterapplication.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.interfacer.DataPoster;

/**
 * Created by Sarah on 3/5/2018.
 *
 * Holds information for an admin user.
 */

@IgnoreExtraProperties
public class AdminUser extends User {

    // these hash maps allow the admin to see add the users - map email to user object
    private static final HashMap<String, AdminUser> adminUsers = new HashMap<>();
    private static final HashMap<String, ShelterEmployee> shelterEmployees = new HashMap<>();
    private static final HashMap<String, HomelessPerson> homelessPeople = new HashMap<>();

    /**
     * adds a user to the appropriate map
     * @param user to be added
     */
    public static void addUser(User user) {
        Class<? extends User> userClass = user.getClass();
        if (userClass.equals(AdminUser.class)) {
            adminUsers.put(user.getEmail(), (AdminUser) user);
        } else if (userClass.equals(ShelterEmployee.class)) {
            shelterEmployees.put(user.getEmail(), (ShelterEmployee) user);
        } else {
            homelessPeople.put(user.getEmail(), (HomelessPerson) user);
        }
    }

    /**
     * removes user from map
     * @param email of user to remove
     */
    public static void removeUser(String email) {
        if (adminUsers.containsKey(email)) {
            adminUsers.remove(email);
        } else if (shelterEmployees.containsKey(email)) {
            shelterEmployees.remove(email);
        } else if (homelessPeople.containsKey(email)) {
            homelessPeople.remove(email);
        }
    }

    /**
     * gets the users of the specified types in list form
     * @param types the user types requested
     * @return a list of all the users of the specified types
     */
    public static ArrayList<User> getUsers(Class ... types) {
        ArrayList<User> users = new ArrayList<>();
        if (types.length == 0) {
            types = new Class[]{AdminUser.class, ShelterEmployee.class, HomelessPerson.class};
        }
        for (Class type : types) {
            if (type.equals(AdminUser.class)) {
                users.addAll(adminUsers.values());
                users.remove(User.getCurrentUser());
            } else if (type.equals(ShelterEmployee.class)) {
                users.addAll(shelterEmployees.values());
            } else if (type.equals(HomelessPerson.class)) {
                users.addAll(homelessPeople.values());
            }
        }
        return users;
    }

    private Boolean isApproved = false;

    /**
     * whether user is approved
     * @return boolean value
     */
    public boolean isApproved() {
        return isApproved;
    }

    /**
     * constructor for an AdminUser
     * @param email user email
     * @param password user password
     * @param name of user
     * @throws Exception if problem with data
     */
    public AdminUser(String email, String password, String name) throws Exception {
        super(email, password, name);
    }

    /**
     * constructor for AdminUser from database snapshot
     * @param snapshot from database
     */
    public AdminUser(DataSnapshot snapshot) {
        super(snapshot);
        isApproved = snapshot.child("isApproved").getValue(Boolean.class);
    }

    @Override
    public String[] getUserInfo() {
        ArrayList<String> info = new ArrayList<>();
        info.add("Admin User");
        info.add("Email: " + getEmail());
        if (!getUsersName().equals(getEmail())) {
            info.add("Name: " + getUsersName());
        }
        if (!isApproved()) {
            info.add("Pending Approval");
        }
        if (getIsBlocked()) {
            info.add("This user is blocked.");
        }
        return info.toArray(new String[info.size()]);
    }

    /**
     * approve a newly registered admin user
     * @param newAdmin the newly registered admin user
     */
    public void approveAdmin(AdminUser newAdmin) {
        newAdmin.isApproved = true;
        DataPoster.post(newAdmin);
    }

    /**
     * approves a shelter employee
     * @param newEmployee to be approved
     */
    public void approveShelterEmployee(ShelterEmployee newEmployee) {
        if (newEmployee == null) {
            throw new IllegalArgumentException("Employee required");
        }
        newEmployee.approve(this);
    }

    /**
     * method to change blocked status of a user
     * @param user the user to be blocked/unblocked
     * @throws IllegalArgumentException if user is null or this
     */
    public void toggleBlockUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Cannot block null.");
        }
        if (this.equals(user)) {
            throw new IllegalArgumentException("Can't block yourself.");
        }
        user.toggleBlocked(this);
    }
}

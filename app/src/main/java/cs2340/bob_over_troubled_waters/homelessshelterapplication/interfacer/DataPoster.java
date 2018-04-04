package cs2340.bob_over_troubled_waters.homelessshelterapplication.interfacer;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.AdminUser;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.HomelessPerson;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.Shelter;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.ShelterEmployee;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.User;

/**
 * Created by Sarah on 3/13/2018.
 * posts data to the database (or updates it if its already there)
 */

public class DataPoster {

    private static DatabaseReference database;
    private static FirebaseAuth auth;

    static {
        try {
           database = FirebaseDatabase.getInstance().getReference();
           auth = FirebaseAuth.getInstance();
        } catch (Throwable e) {
            setEnabled(false);
        }
    }

    // set this to false when running tests so database isn't changed
    private static boolean enabled = true;

    public static void setEnabled(boolean enabled) {
        DataPoster.enabled = enabled;
    }

    /**
     * saves (or updates) a user record in the database
     * @param user the user object to be saved to the database
     */
    public static void post(User user) {
        if (enabled) {
            Timer timer = Timer.getTimer();
            DatabaseReference path = database.child("users").child(user.getId());
            path.child("email").setValue(user.getEmail());
            path.child("name").setValue(user.getName());
            path.child("isBlocked").setValue(user.getIsBlocked());
            if (user instanceof AdminUser) {
                saveAdminData((AdminUser) user, path);
            } else if (user instanceof ShelterEmployee) {
                saveEmployeeData((ShelterEmployee) user, path);
            } else {
                saveUserData((HomelessPerson) user, path);
            }
            timer.stopTimer();
        }
    }

    public static void post(Shelter shelter) {
        if (enabled) {
            Timer timer = Timer.getTimer();
            DatabaseReference ref = database.child("shelters").child(shelter.getID() + "");
            ref.setValue(shelter);
            timer.stopTimer();
        }
    }

    private static void saveAdminData(AdminUser admin, DatabaseReference path) {
        path.child("userType").setValue("admin");
        path.child("isApproved").setValue(admin.isApproved());
    }

    private static void saveEmployeeData(ShelterEmployee employee, DatabaseReference path) {
        path.child("userType").setValue("employee");
        path.child("isApproved").setValue(employee.isApproved());
        if (employee.getShelter() != null) {
            path.child("shelter").setValue(employee.getShelter().getID());
        } else if (employee.getShelterId() != null) {
            path.child("shelter").setValue(employee.getShelterId());
        }
    }

    private static void saveUserData(HomelessPerson user, DatabaseReference path) {
        path.child("userType").setValue("user");
        if (user.getCurrentReservedShelter() != null) {
            path.child("currentReservedShelter").setValue(user.getCurrentReservedShelter().getID());
            path.child("currentReservedNum").setValue(user.getCurrentReservedNum());
        } else if (user.getCurrentReservedShelterId() != null) {
            path.child("currentReservedShelter").setValue(user.getCurrentReservedShelterId());
            path.child("currentReservedNum").setValue(user.getCurrentReservedNum());
        } else {
            path.child("currentReservedShelter").removeValue();
            path.child("currentReservedNum").removeValue();
        }
    }

    private static String firebaseId;
    private static Exception firebaseException;

    public static String addFirebaseUser(String email, String password) throws Exception {
        firebaseId = null;
        firebaseException = null;

        if (enabled) {
            Timer timer = Timer.getTimer(20000);
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser firebaseUser = auth.getCurrentUser();
                                if (firebaseUser != null) {
                                    firebaseId = firebaseUser.getUid();
                                }
                            } else {
                                firebaseException = task.getException();
                            }
                        }
                    });
            while (firebaseId == null && firebaseException == null) {
                Thread.sleep(50);
            }
            timer.stopTimer();
        }

        if (firebaseException != null) {
            throw firebaseException;
        }
        return firebaseId;
    }

    public static void logout() {
        Timer timer = Timer.getTimer();
        auth.signOut();
        timer.stopTimer();
    }

}

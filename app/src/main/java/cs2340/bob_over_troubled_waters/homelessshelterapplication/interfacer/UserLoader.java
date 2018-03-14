package cs2340.bob_over_troubled_waters.homelessshelterapplication.interfacer;

import android.os.AsyncTask;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.AdminUser;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.HomelessPerson;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.ShelterEmployee;

/**
 * Created by Sarah on 3/12/2018.
 */

public class UserLoader extends AsyncTask<Void, Void, String> {

    private static UserLoader instance;

    public static void start() {
        instance = new UserLoader();
        instance.execute();
    }

    public static boolean usersLoaded() {
        if (instance == null) return false;
        return instance.done;
    }

    private static final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users");

    private boolean done;
    private String errorMessage;

    @Override
    public String doInBackground(Void ... params) {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    try {
                        String userType = child.child("userType").getValue(String.class);
                        if (userType.equals("user")) {
                            AdminUser.addUser(new HomelessPerson(child));
                        } else if (userType.equals("employee")) {
                            AdminUser.addUser(new ShelterEmployee(child));
                        } else {
                            AdminUser.addUser(new AdminUser(child));
                        }
                    } catch (Exception e) {
                        errorMessage = e.getMessage();
                    }
                }
                done = true;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                errorMessage = databaseError.getMessage();
            }
        });

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String userType = dataSnapshot.child("userType").getValue(String.class);
                if (userType.equals("user")) {
                    AdminUser.addUser(new HomelessPerson(dataSnapshot));
                } else if (userType.equals("employee")) {
                    AdminUser.addUser(new ShelterEmployee(dataSnapshot));
                } else {
                    AdminUser.addUser(new AdminUser(dataSnapshot));
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String userType = dataSnapshot.child("userType").getValue(String.class);
                if (userType.equals("user")) {
                    AdminUser.addUser(new HomelessPerson(dataSnapshot));
                } else if (userType.equals("employee")) {
                    AdminUser.addUser(new ShelterEmployee(dataSnapshot));
                } else {
                    AdminUser.addUser(new AdminUser(dataSnapshot));
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String email = dataSnapshot.child("email").getValue(String.class);
                AdminUser.removeUser(email);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                errorMessage = databaseError.getMessage();
            }
        });

        while (!done && errorMessage == null) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return errorMessage;
    }
}

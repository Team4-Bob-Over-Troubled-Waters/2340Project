package cs2340.bob_over_troubled_waters.homelessshelterapplication.interfacer;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.AdminUser;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.HomelessPerson;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.ShelterEmployee;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.User;

/**
 * Created by Sarah on 3/12/2018.
 * creates a user object for a single user and sets the current user to that user
 */

public class SingleUserLoader {

    private static final FirebaseAuth auth = FirebaseAuth.getInstance();

    private String email;
    private User loadedUser;
    private String password;
    private Exception error;
    private boolean done = false;

    public SingleUserLoader(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User execute() throws Exception {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    final FirebaseUser user = auth.getCurrentUser();
                    String id = null;
                    if (user != null) {
                        id = user.getUid();
                    } else {
                        done = true;
                        error = new Exception("Login unsuccessful");
                    }

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                            .child("users").child(id);

                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String userType = dataSnapshot.child("userType").getValue(String.class);
                            if (userType.equals("user")) {
                                loadedUser = new HomelessPerson(dataSnapshot);
                            } else if (userType.equals("employee")) {
                                loadedUser = new ShelterEmployee(dataSnapshot);
                            } else {
                                loadedUser = new AdminUser(dataSnapshot);
                            }
                            done = true;
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            error = databaseError.toException();
                            done = true;
                        }
                    });

                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String userType = dataSnapshot.child("userType").getValue(String.class);
                            if (userType.equals("user")) {
                                loadedUser = new HomelessPerson(dataSnapshot);
                            } else if (userType.equals("employee")) {
                                loadedUser = new ShelterEmployee(dataSnapshot);
                            } else {
                                loadedUser = new AdminUser(dataSnapshot);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            error = databaseError.toException();
                        }
                    });

                } else {
                    done = true;
                    error = task.getException();
                }
            }
        });

        Timer timer = Timer.getTimer(15000);
        while (!done) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        timer.stopTimer();

        if (error != null) {
            throw error;
        }
        return loadedUser;
    }
}

package cs2340.bob_over_troubled_waters.homelessshelterapplication.interfacer;

import android.os.AsyncTask;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.Shelter;

/**
 * Created by Sarah on 3/12/2018.
 * This class loads instances from the database of Shelter as Shelter class instances
 */

public abstract class ShelterLoader extends AsyncTask<Void, Void, String> {

    private static final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("shelters");
    private static int maxChildId = -1;

    private static ShelterLoader instance;

    private boolean done = false;
    private String errorMessage;

    public static void setInstance(ShelterLoader shelterLoader) {
        instance = shelterLoader;
        instance.execute();
    }

    /**
     * tells whether the shelters have already been loaded
     * @return
     */
    public static boolean sheltersLoaded() {
        if (instance == null) return false;
        return instance.done;
    }

    /**
     * gives the next valid shelter id
     * @return the maximum shelter id incremented by 1
     */
    public int getNextShelterId() {
        return maxChildId + 1;
    }

    @Override
    public String doInBackground(Void ... params) {
        if (!done) {
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        try {
                            Integer id = Integer.parseInt(child.getKey());
                            maxChildId = Math.max(maxChildId, id);
                            new Shelter(child);
                        } catch (Exception e) {
                            done = true;
                            errorMessage = e.getMessage();
                        }
                    }
                    done = true;
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    done = true;
                    errorMessage = databaseError.getMessage();
                }
            });
        }

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Integer id = Integer.parseInt(dataSnapshot.getKey());
                maxChildId = Math.max(maxChildId, id);
                new Shelter(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                new Shelter(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Integer id = Integer.parseInt(dataSnapshot.getKey());
                Shelter.removeShelter(id);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                errorMessage = databaseError.getMessage();
            }
        });
        while (!done) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {}
        }
        return errorMessage;
    }

    @Override
    public abstract void onPostExecute(final String errorMessage);
}

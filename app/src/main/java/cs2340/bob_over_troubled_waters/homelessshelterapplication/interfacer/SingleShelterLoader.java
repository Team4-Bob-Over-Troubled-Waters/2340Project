package cs2340.bob_over_troubled_waters.homelessshelterapplication.interfacer;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.Shelter;

/**
 * Created by Sarah on 3/12/2018.
 * loads a single shelter as a Shelter object
 */

public class SingleShelterLoader {

    private final int id;
    private Shelter loadedShelter;
    private boolean done = false;
    private Exception error;

    public SingleShelterLoader(int id) {
        this.id = id;
    }

    /**
     * loads and returns a single Shelter instance from the database
     * @return a Shelter object
     * @throws Exception if there is a problem connecting to the database
     */
    public Shelter execute() throws Exception {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child("shelters").child(id + "");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                loadedShelter = new Shelter(dataSnapshot);
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
                loadedShelter = new Shelter(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                error = databaseError.toException();
            }
        });

        Timer timer = Timer.getTimer(10000);
        while (!done) {
            Thread.sleep(50);
        }
        timer.stopTimer();

        if (error != null) throw error;
        return loadedShelter;
    }
}

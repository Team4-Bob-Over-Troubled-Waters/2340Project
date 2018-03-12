package cs2340.bob_over_troubled_waters.homelessshelterapplication.interfacer;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.AdminUser;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.HomelessPerson;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.Shelter;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.ShelterEmployee;

/**
 * Created by Admin on 3/12/2018.
 */

public class SingleShelterLoader {

    private int id;
    private Shelter loadedShelter;
    private boolean done = false;
    private Exception error;

    public SingleShelterLoader(int id) {
        this.id = id;
    }

    public Shelter execute() throws Exception {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child("shelters").child(id + "");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("here");
                System.out.println(dataSnapshot);
                loadedShelter = new Shelter(dataSnapshot);
                done = true;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                error = databaseError.toException();
                done = true;
            }
        });

        while (!done) {
            Thread.sleep(50);
        }
        if (error != null) throw error;
        return loadedShelter;
    }
}

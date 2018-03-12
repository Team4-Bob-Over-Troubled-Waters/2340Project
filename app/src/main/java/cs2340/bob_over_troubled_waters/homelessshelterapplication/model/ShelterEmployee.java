package cs2340.bob_over_troubled_waters.homelessshelterapplication.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.util.Collection;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.interfacer.SingleShelterLoader;

/**
 * Created by Admin on 3/5/2018.
 */

@IgnoreExtraProperties
public class ShelterEmployee extends User {

    private Shelter shelter;
    private boolean isApproved = false;

    public ShelterEmployee(String email, String password, String name) throws Exception {
        super(email, password, name);
        path.child("userType").setValue("employee");
        path.child("isApproved").setValue(isApproved);
        if (shelter != null) {
            path.child("shelter").setValue(shelter.getID());
        }
    }

    public ShelterEmployee(DataSnapshot snapshot) {
        super(snapshot);
        isApproved = snapshot.child("isApproved").getValue(Boolean.class);
        try {
            // TODO fix this
            Integer shelterId = snapshot.child("shelter").getValue(Integer.class);
            shelter = Shelter.getShelter(shelterId);
            if (shelter == null && shelterId != null) {
                shelter = new SingleShelterLoader(shelterId).execute();
            }
        } catch (Exception e) {
            shelter = null;
        }
    }

    public void setShelter(Shelter shelter) {
        path.child("shelter").setValue(shelter.getID());
        this.shelter = shelter;
    }

    public Shelter getShelter() {
        return shelter;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void approve(AdminUser approvedBy) {
        if (approvedBy != null) {
            isApproved = true;
        } else {
            throw new IllegalArgumentException("Need admin to approve");
        }
    }
}

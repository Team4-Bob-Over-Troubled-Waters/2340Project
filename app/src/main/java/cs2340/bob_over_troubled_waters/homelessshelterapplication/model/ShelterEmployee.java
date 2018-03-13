package cs2340.bob_over_troubled_waters.homelessshelterapplication.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Admin on 3/5/2018.
 */

@IgnoreExtraProperties
public class ShelterEmployee extends User {

    private Shelter shelter;
    private Integer shelterId = null;
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
            Integer shelterId = snapshot.child("shelter").getValue(Integer.class);
            this.shelterId = shelterId;
            shelter = Shelter.getShelter(shelterId);
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

    public Integer getShelterId() {
        return shelterId;
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

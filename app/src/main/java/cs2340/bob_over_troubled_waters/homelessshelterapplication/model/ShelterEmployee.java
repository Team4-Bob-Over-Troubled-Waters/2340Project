package cs2340.bob_over_troubled_waters.homelessshelterapplication.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.interfacer.DataPoster;

/**
 * Created by Admin on 3/5/2018.
 *
 * Holds information about a shelter employee user.
 */

@IgnoreExtraProperties
public class ShelterEmployee extends User {

    private Shelter shelter;
    private Integer shelterId = null;
    private boolean isApproved = false;

    public ShelterEmployee(String email, String password, String name) throws Exception {
        super(email, password, name);
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
//        path.child("shelter").setValue(shelter.getID());
        this.shelter = shelter;
        DataPoster.post(this);
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
            DataPoster.post(this);
        } else {
            throw new IllegalArgumentException("Need admin to approve");
        }
    }

    @Override
    public String[] getUserInfo() {
        ArrayList<String> info = new ArrayList<>();
        info.add("Shelter Employee");
        info.add("Email: " + getEmail());
        if (!getUsersName().equals(getEmail())) {
            info.add("Name: " + getUsersName());
        }
        if (shelter != null) {
            info.add("Works at " + shelter);
        } else {
            info.add("No shelter selected yet");
        }
        if (!isApproved()) {
            info.add("Pending Approval");
        }
        if (getIsBlocked()) {
            info.add("This user is blocked.");
        }
        return info.toArray(new String[info.size()]);
    }
}

package cs2340.bob_over_troubled_waters.homelessshelterapplication.model;

import android.content.Intent;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.interfacer.DataPoster;

/**
 * Created by Sarah on 3/5/2018.
 */

@IgnoreExtraProperties
public class HomelessPerson extends User {

//    private Shelter currentReservedShelter = null;
//    private Integer currentReservedShelterId = null;
//    private int currentReservedNum = 0;

    private Reservation currentReservation = null;

    public Reservation getCurrentReservation() {
        return currentReservation;
    }

    public void setCurrentReservation(Reservation currentReservation) {
        this.currentReservation = currentReservation;
        DataPoster.post(this);
    }

    public Shelter getCurrentReservedShelter() {
        if (currentReservation != null) {
            return currentReservation.getShelter();
        }
        return null;
    }

    public Integer getCurrentReservedShelterId() {
        if (currentReservation != null) {
            return currentReservation.getShelterId();
        }
        return null;
    }

    public int getCurrentReservedNum() {
        if (currentReservation != null) {
            return currentReservation.getNumberOfBeds();
        }
        return 0;
    }

    public HomelessPerson(String email, String password, String name) throws Exception {
        super(email, password, name);

    }

    @Override
    public String[] getUserInfo() {
        ArrayList<String> info = new ArrayList<>();
        info.add("Homeless Person");
        info.add("Email: " + getEmail());
        if (!getUsersName().equals(getEmail())) {
            info.add("Name: " + getUsersName());
        }
        if (getCurrentReservedShelter() != null) {
            String val = "Reservation for " + getCurrentReservedNum() + " at "
                    + getCurrentReservedShelter() + ".";
            info.add(val);
        }
        if (getIsBlocked()) {
            info.add("This user is blocked.");
        }
        return info.toArray(new String[info.size()]);
    }

    public HomelessPerson(DataSnapshot snapshot) {
        super(snapshot);
        Integer shelterId = snapshot.child("currentReservedShelter").getValue(Integer.class);
        Integer numReserved = snapshot.child("currentReservedNum").getValue(Integer.class);
        if (numReserved == null) {
            numReserved = 0;
        }
        if (shelterId != null) {
            currentReservation = new Reservation(numReserved, shelterId);
        }
    }
}

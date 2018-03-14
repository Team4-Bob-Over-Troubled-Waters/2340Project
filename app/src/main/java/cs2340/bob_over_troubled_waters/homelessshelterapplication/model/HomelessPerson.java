package cs2340.bob_over_troubled_waters.homelessshelterapplication.model;

import android.content.Intent;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

/**
 * Created by Sarah on 3/5/2018.
 */

@IgnoreExtraProperties
public class HomelessPerson extends User {

    private Shelter currentReservedShelter = null;
    private Integer currentReservedShelterId = null;
    private int currentReservedNum = 0;

    public Shelter getCurrentReservedShelter() {
        return currentReservedShelter;
    }

    public Integer getCurrentReservedShelterId() {
        return currentReservedShelterId;
    }

    public int getCurrentReservedNum() {
        return currentReservedNum;
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
        if (currentReservedShelter != null) {
            String val = "Reservation for " + currentReservedNum + " at "
                    + currentReservedShelter + ".";
            info.add(val);
        }
        if (getIsBlocked()) {
            info.add("This user is blocked.");
        }
        return info.toArray(new String[info.size()]);
    }

    public HomelessPerson(DataSnapshot snapshot) {
        super(snapshot);
    }
}

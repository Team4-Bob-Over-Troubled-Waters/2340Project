package cs2340.bob_over_troubled_waters.homelessshelterapplication.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Admin on 3/5/2018.
 */

@IgnoreExtraProperties
public class HomelessPerson extends User {

    Shelter currentReservedShelter = null;
    int currentReservedNum = 0;

    public HomelessPerson(String email, String password, String name) throws Exception {
        super(email, password, name);
        path.child("userType").setValue("user");

    }

    public HomelessPerson(DataSnapshot snapshot) {
        super(snapshot);
    }
}

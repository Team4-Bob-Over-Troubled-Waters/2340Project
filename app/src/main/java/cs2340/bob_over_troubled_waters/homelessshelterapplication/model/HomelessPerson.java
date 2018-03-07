package cs2340.bob_over_troubled_waters.homelessshelterapplication.model;

/**
 * Created by Admin on 3/5/2018.
 */

public class HomelessPerson extends User {

    Shelter currentReservedShelter = null;
    int currentReservedNum = 0;

    public HomelessPerson(String email, String password, String name) {
        super(email, password, name);
    }
}

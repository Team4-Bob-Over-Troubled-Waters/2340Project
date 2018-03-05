package cs2340.bob_over_troubled_waters.homelessshelterapplication.model.enums;

import java.util.ArrayList;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.Shelter;

/**
 * Created by Sarah on 2/28/2018.
 */

public enum Gender {
    MALE(new ArrayList<Shelter>()),
    FEMALE(new ArrayList<Shelter>()),
    BOTH(new ArrayList<Shelter>());

    private ArrayList<Shelter> shelters;

    Gender(ArrayList<Shelter> shelters) {
        this.shelters = shelters;
    }

    public ArrayList<Shelter> getShelters() {
        return this.shelters;
    }

    public void addShelter(Shelter shelter) {
        this.shelters.add(shelter);
    }

    public static Gender parseGender(String restrictions) {
        restrictions = restrictions.toLowerCase();
        if (restrictions.contains("women")) {
            return FEMALE;
        } else if (restrictions.contains("men")) {
            return MALE;
        } else {
            return BOTH;
        }
    }
}

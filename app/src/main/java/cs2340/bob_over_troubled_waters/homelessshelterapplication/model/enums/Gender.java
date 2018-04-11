package cs2340.bob_over_troubled_waters.homelessshelterapplication.model.enums;

import java.util.ArrayList;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.Shelter;

/**
 * Created by Sarah on 2/28/2018.
 * Enum for gender restrictions in shelters.
 */

@SuppressWarnings("Convert2Diamond")
public enum Gender {
    /* Cannot replace explicit type argument Shelter because the constructor requires
    ArrayList<Shelter>, not ArrayList<>
     */
    MALE(new ArrayList<Shelter>()),
    FEMALE(new ArrayList<Shelter>()),
    BOTH(new ArrayList<Shelter>());

    private final ArrayList<Shelter> shelters;

    Gender(ArrayList<Shelter> shelters) {
        this.shelters = shelters;
    }

    /**
     * get list of shelters with that gender
     * @return array list of shelters
     */
    public ArrayList<Shelter> getShelters() {
        return this.shelters;
    }

    /**
     * add shelter to list of shelters with that gender
     * @param shelter to be added
     */
    public void addShelter(Shelter shelter) {
        this.shelters.add(shelter);
    }

    /**
     * parse restriction string to determine which genders a shelter has
     * @param restrictions string to parse
     * @return gender for that shelter
     */
    public static Gender parseGender(String restrictions) {
        String str = restrictions.toLowerCase();
        if (str.contains("women")) {
            return FEMALE;
        } else if (str.contains("men")) {
            return MALE;
        } else {
            return BOTH;
        }
    }
}

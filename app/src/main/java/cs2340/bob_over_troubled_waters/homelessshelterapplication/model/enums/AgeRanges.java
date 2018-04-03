package cs2340.bob_over_troubled_waters.homelessshelterapplication.model.enums;

import java.util.ArrayList;
import java.util.HashSet;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.Shelter;

/**
 * Created by Sarah on 2/28/2018.
 * Enum for age restrictions in shelters.
 */

@SuppressWarnings("Convert2Diamond")
public enum AgeRanges {
    // Cannot replace explicit type argument Shelter because the constructor requires ArrayList<Shelter>, not ArrayList<>
    FAMILIES_WITH_NEWBORNS(new ArrayList<Shelter>()),
    CHILDREN(new ArrayList<Shelter>()),
    YOUNG_ADULTS(new ArrayList<Shelter>()),
    ANYONE(new ArrayList<Shelter>());

    private final ArrayList<Shelter> shelters;

    AgeRanges(ArrayList<Shelter> shelters) {
        this.shelters = shelters;
    }

    public ArrayList<Shelter> getShelters() {
        return this.shelters;
    }

    public void addShelter(Shelter shelter) {
        this.shelters.add(shelter);
    }

    public static HashSet<AgeRanges> parseAgeRanges(String restrictions) {
        restrictions = restrictions.toLowerCase();
        HashSet<AgeRanges> ageRanges = new HashSet<>();
        if (restrictions.contains("children")) {
            ageRanges.add(CHILDREN);
        }
        if (restrictions.contains("anyone")) {
            ageRanges.add(ANYONE);
        }
        if (restrictions.contains("newborn")) {
            ageRanges.add(FAMILIES_WITH_NEWBORNS);
        }
        if (restrictions.contains("young adult")) {
            ageRanges.add(YOUNG_ADULTS);
        }
        return ageRanges;
    }
}

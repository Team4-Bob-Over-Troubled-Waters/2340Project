package cs2340.bob_over_troubled_waters.homelessshelterapplication.model;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by Francine on 3/13/2018.
 */

public class Reservation {
    private int numberOfBeds;
    private Integer shelterId;

    public Reservation(int numberOfBeds, Integer shelterId) {
        this.numberOfBeds = numberOfBeds;
        this.shelterId = shelterId;
    }

    public Reservation(int numberOfBeds, Shelter shelter) {
        this.numberOfBeds = numberOfBeds;
        this.shelterId = shelter.getID();
    }

    @Override
    public String toString() {
        return (numberOfBeds + " beds at " + getShelter());
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public Shelter getShelter() {
        return Shelter.getShelter(shelterId);
    }

    public Integer getShelterId() {
        return shelterId;
    }
}

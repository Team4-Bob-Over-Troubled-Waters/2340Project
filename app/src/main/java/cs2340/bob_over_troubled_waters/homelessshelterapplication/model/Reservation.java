package cs2340.bob_over_troubled_waters.homelessshelterapplication.model;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by Francine on 3/13/2018.
 */

public class Reservation {
    private int numberOfBeds;
    private Shelter shelter;
    private Integer shelterId;

    public Reservation(int numberOfBeds, Integer shelterId) {
        this.numberOfBeds = numberOfBeds;
        this.shelterId = shelterId;
        shelter = Shelter.getShelter(shelterId);
    }

    public Reservation(int numberOfBeds, Shelter shelter) {
        this.numberOfBeds = numberOfBeds;
        this.shelter = shelter;
        this.shelterId = shelter.getID();
    }

    @Override
    public String toString() {
        return (numberOfBeds + " beds at " + shelter);
    }

    /**
     * Fetches and returns the number of beds.
     *
     * @return the number of beds.
     */
    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    /**
     * Sets the number of beds.
     *
     * @param numberOfBeds the number of beds.
     */
    public void setNumberOfBeds(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    /**
     * Fetches and returns the shelter.
     *
     * @return the shelter.
     */
    public Shelter getShelter() {
        return shelter;
    }

    /**
     * Sets the shelter.
     *
     * @param shelter the shelter to set.
     */
    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }

    /**
     * Fetches and returns the shelter id.
     *
     * @return the shelter id.
     */
    public Integer getShelterId() {
        return shelterId;
    }
}

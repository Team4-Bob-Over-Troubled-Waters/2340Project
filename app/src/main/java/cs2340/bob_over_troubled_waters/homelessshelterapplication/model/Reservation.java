package cs2340.bob_over_troubled_waters.homelessshelterapplication.model;

/**
 * Created by Francine on 3/13/2018.
 */

public class Reservation {
    private int numberOfBeds;
    private Shelter shelter;

    public Reservation(int numberOfBeds, Shelter shelter) {
        this.numberOfBeds = numberOfBeds;
        this.shelter = shelter;
    }

    @Override
    public String toString() {
        return (numberOfBeds + " beds at " + shelter);
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public Shelter getShelter() {
        return shelter;
    }

    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }
}

package cs2340.bob_over_troubled_waters.homelessshelterapplication.model;

/**
 * Created by Francine on 3/13/2018.
 *
 * Holds information about a reservation.
 */

public class Reservation {
    private final int numberOfBeds;
    private final Integer shelterId;

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

// --Commented out by Inspection START (3/31/2018 15:35):
//    public void setNumberOfBeds(int numberOfBeds) {
//        this.numberOfBeds = numberOfBeds;
//    }
// --Commented out by Inspection STOP (3/31/2018 15:35)

    public Shelter getShelter() {
        return Shelter.getShelter(shelterId);
    }

    public Integer getShelterId() {
        return shelterId;
    }
}

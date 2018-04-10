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

    /**
     * Fetches and returns the number of beds.
     *
     * @return the number of beds.
     */
    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    /**
     * Fetches and returns the shelter.
     *
     * @return the shelter.
     */
    public Shelter getShelter() {
        return Shelter.getShelter(shelterId);
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

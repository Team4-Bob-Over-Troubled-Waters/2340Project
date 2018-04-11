package cs2340.bob_over_troubled_waters.homelessshelterapplication.model;

import android.util.SparseArray;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.interfacer.DataPoster;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.interfacer.ShelterLoader;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.enums.AgeRanges;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.enums.Gender;

/**
 * Created by Francine on 2/16/2018.
 *
 * Holds information about a shelter.
 */

@IgnoreExtraProperties
public class Shelter {
    private Integer ID;
    private String name;
    private String capacity;
    private Integer reservations = 0;
    private Integer maxVacancies;
    private String restrictions;
    private Double longitude;
    private Double latitude;
    private String address;
    private String specialNotes;
    private String phoneNumber;
    private Gender gender;
    private HashSet<AgeRanges> ageRanges;

    private static final HashSet<String> shelterNames = new HashSet<>();
    private static final SparseArray<Shelter> shelters = new SparseArray<>();

    private static Shelter currentShelter = null;

    /**
     * Fetches and returns the currentShelter Shelter object.
     *
     * @return the Shelter object of the current instance.
     */
    public static Shelter getCurrentShelter() {
        return currentShelter;
    }

    /**
     * Sets the current shelter object.
     *
     * @param currentShelter Shelter object to set as currentShelter object.
     */
    public static void setCurrentShelter(Shelter currentShelter) {
        Shelter.currentShelter = currentShelter;
    }

    /**
     * Returns the collection of shelters.
     *
     * @return the collection of shelters.
     */
    public static Collection<Shelter> getShelters() {
        Collection<Shelter> collection = new ArrayList<>();
        for (int i = 0; i < shelters.size(); i++) {
            collection.add(shelters.get(i));
        }
        return collection;
    }

    /**
     * Fetches and returns the shelter associated with a particular shelter id.
     *
     * @param shelterId the id with which to identify the shelter to find.
     * @return the shelter associated with the given shelter id.
     */
    public static Shelter getShelter(Integer shelterId) {
        return shelters.get(shelterId);
    }


    /**
     * gets shelter with a given name
     * @param name to search for
     * @return shelter with that name
     */
    public static Shelter getShelterByName(String name) {
        for (int i = 0; i < shelters.size(); i++) {
            Shelter shelter = shelters.get(i);
            if (name.equals(shelter.getName())) {
                return shelter;
            }
        }
        return null;
    }

    /**
     * constructs a shelter object from the saved instance in the database
     * @param snapshot saved instance in the database
     */
    public Shelter(DataSnapshot snapshot) {
        this.ID = snapshot.child("id").getValue(Integer.class);
        this.name = snapshot.child("name").getValue(String.class);
        shelterNames.add(this.name);
        this.capacity = snapshot.child("capacity").getValue(String.class);
        this.restrictions = snapshot.child("restrictions").getValue(String.class);
        this.longitude = snapshot.child("longitude").getValue(Double.class);
        this.latitude = snapshot.child("latitude").getValue(Double.class);
        this.address = snapshot.child("address").getValue(String.class);
        this.specialNotes = snapshot.child("specialNotes").getValue(String.class);
        this.phoneNumber = snapshot.child("phoneNumber").getValue(String.class);
        this.gender = Gender.parseGender(restrictions);
        this.gender.addShelter(this);
        ageRanges = AgeRanges.parseAgeRanges(restrictions);
        for (AgeRanges range : ageRanges) {
            range.addShelter(this);
        }

        this.maxVacancies = snapshot.child("maxVacancies").getValue(Integer.class);
        if (snapshot.child("reservations").getValue(Integer.class) != null) {
            this.reservations = snapshot.child("reservations").getValue(Integer.class);
        }

        shelters.put(this.ID, this);
    }

    private Shelter(int ID, String name, String capacity, Integer maxVacancies, String restrictions,
                   Double longitude, Double latitude, String address, String specialNotes,
                   String phoneNumber) throws IllegalArgumentException {
        // validate all the data first
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (capacity == null || capacity.isEmpty()) {
            capacity = "";
            if (maxVacancies == null) {
                maxVacancies = 0;
            }
        }
        if (maxVacancies == null) {
            try {
                maxVacancies = Integer.parseInt(capacity);
            } catch (Exception e) {
                maxVacancies = 0;
            }
        }
        if (restrictions == null) {
            restrictions = "";
        }
        if (longitude == null) {
            throw new IllegalArgumentException("Longitude is required");
        }
        if (latitude == null) {
            throw new IllegalArgumentException("Latitude is required");
        }
        if (address == null || address.isEmpty()) {
            throw new IllegalArgumentException("Address is required");
        }
        if (specialNotes == null) {
            specialNotes = "";
        }
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new IllegalArgumentException("Phone number is required");
        }

        this.ID = ID;
        this.name = name;
        shelterNames.add(name);
        this.capacity = capacity;
        this.maxVacancies = maxVacancies;
        reservations = 0;
        this.restrictions = restrictions;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address.replaceFirst(",", System.getProperty("line.separator"));
        this.specialNotes = specialNotes;
        this.phoneNumber = phoneNumber;
        this.gender = Gender.parseGender(restrictions);
        this.gender.addShelter(this);
        ageRanges = AgeRanges.parseAgeRanges(restrictions);
        for (AgeRanges range : ageRanges) {
            range.addShelter(this);
        }
        DataPoster.post(this);
    }

    /**
     * Adds a new shelter with the given information to the database of shelters.
     *
     * @param name the name of the shelter.
     * @param capacity the maximum capacity of the shelter.
     * @param maxVacancies the maximum number of vacancies of the shelter.
     * @param restrictions the restrictions for the shelter.
     * @param longitude the longitude of the location of the shelter.
     * @param latitude the latitude of the location of the shelter.
     * @param address the address of the shelter.
     * @param specialNotes and special notes for the shelter.
     * @param phoneNumber the phone number of the shelter.
     * @return the new shelter object.
     * @throws IllegalArgumentException if the shelter name is already in use.
     */
    public static Shelter addShelter(String name, String capacity, Integer maxVacancies, String restrictions,
                           Double longitude, Double latitude, String address, String specialNotes,
                           String phoneNumber) throws IllegalArgumentException {
        if (shelterNames.contains(name)) {
            throw new IllegalArgumentException("Shelter name in use");
        }
        int ID = ShelterLoader.getNextShelterId();
        return new Shelter(ID, name, capacity, maxVacancies, restrictions, longitude, latitude,
                address, specialNotes, phoneNumber);
    }

    /**
     * Updates the information for a given shelter.
     *
     * @param shelter the shelter being updated.
     * @param name the updated name of the shelter.
     * @param capacity the updated capacity of the shelter.
     * @param maxVacancies the updated maximum number of vacancies for the shelter.
     * @param restrictions the updated restrictions for the shelter.
     * @param longitude the updated longitude for the location of the shelter.
     * @param latitude the updated latitutde for the location of the shelter.
     * @param address the updated address of the shelter.
     * @param specialNotes the updated special notes for the shelter.
     * @param phoneNumber the updated phone number for the shelter.
     * @return the updated shelter object.
     * @throws IllegalArgumentException if the shelter name is already in use.
     */
    public static Shelter updateShelter(Shelter shelter, String name, String capacity, Integer maxVacancies,
                              String restrictions, Double longitude, Double latitude, String address,
                              String specialNotes, String phoneNumber) throws IllegalArgumentException {
        if (!name.equals(shelter.name) && shelterNames.contains(name)) {
            throw new IllegalArgumentException("Shelter name in use");
        }
        return new Shelter(shelter.ID, name, capacity, maxVacancies, restrictions, longitude,
                latitude, address, specialNotes, phoneNumber);
    }

    /**
     * removes a shelter from memory
     * @param id the id of the shelter to be removed
     */
    public static void removeShelter(Integer id) {
        shelters.remove(id);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || !(other instanceof Shelter)) {
            return false;
        }
        if (this == other) {
            return true;
        }
        Shelter that = (Shelter) other;
        return Objects.equals(this.ID, that.ID);
    }

    @Override
    public int hashCode() {
        return ID;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Returns the ID of the shelter.
     *
     * @return the id of the shelter.
     */
    public int getID() {
        return ID;
    }

    /**
     * Fetches and returns the name of the shelter.
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Fetches and returns the capacity of the shelter.
     *
     * @return the capacity of the shelter.
     */
    public String getCapacity() {
        return capacity;
    }

    /**
     * get the current number of vacancies
     * this can never be less than 0 or more than maxVacancies
     * @return usually maxVacancies - reservations
     */
    public int getVacancies() {
        int vacancies = Math.max(0, maxVacancies - reservations);
        return Math.min(maxVacancies, vacancies);
    }

    /**
     * getter for reservations
     * DO NOT REMOVE!
     * appears un used but actually required to save shelter to database
     * @return number of reservations
     */
    public int getReservations() {
        return reservations;
    }

    /**
     * Adds a reservation.
     *
     * @param reservation the reservation to add.
     * @throws Exception if there are not enough vacancies.
     */
    public void addReservation(Reservation reservation) throws Exception {
        if (reservation.getNumberOfBeds() > getVacancies()) {
            throw new Exception("Can't make reservation. Not enough vacancies");
        }
        reservations += reservation.getNumberOfBeds();
        DataPoster.post(this);
    }

    /**
     * Cancels a reservation.
     *
     * @param reservation the reservation to cancel.
     */
    public void cancelReservation(Reservation reservation) {
        reservations -= reservation.getNumberOfBeds();
        reservations = Math.max(reservations, 0);
        DataPoster.post(this);
    }

    /**
     * Fetches and returns the maximum number of vacancies.
     *
     * @return the max number of vacancies.
     */
    public int getMaxVacancies() {
        return maxVacancies;
    }

    /**
     * Fetches and returns the restrictions for the shelter.
     *
     * @return the restrictions for the shelter.
     */
    public String getRestrictions() {
        return restrictions;
    }

    /**
     * Fetches and returns the longitude value for the location
     * of a shelter.
     *
     * @return the longitude value for the location of a shelter.
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Fetches and returns the latitude value for the location of the shelter.
     *
     * @return the latitude value for the location of the shelter.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Fetches and returns the address of the shelter.
     *
     * @return the address of the shelter.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Fetches and returns any special notes for the shelter.
     *
     * @return any special notes for the shelter.
     */
    public String getSpecialNotes() {
        return specialNotes;
    }

    /**
     * Fetches and returns the phone number for the shelter.
     *
     * @return the phone number for the shelter.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
}

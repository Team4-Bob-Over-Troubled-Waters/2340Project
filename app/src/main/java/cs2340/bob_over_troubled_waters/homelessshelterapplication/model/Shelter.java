package cs2340.bob_over_troubled_waters.homelessshelterapplication.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.IgnoreExtraProperties;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.interfacer.DataPoster;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.interfacer.ShelterLoader;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.enums.AgeRanges;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.enums.Gender;

/**
 * Created by Francine on 2/16/2018.
 */

@IgnoreExtraProperties
public class Shelter {
    private int ID;
    private String name;
    private String capacity;
    private int reservations;
    private int maxVacancies;
    private String restrictions;
    private double longitude;
    private double latitude;
    private String address;
    private String specialNotes;
    private String phoneNumber;
    private Gender gender;
    private HashSet<AgeRanges> ageRanges;

    private static HashSet<String> shelterNames = new HashSet<>();
    private static HashMap<Integer, Shelter> shelters = new HashMap<>();

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
        return shelters.values();
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
     * Returns a boolean indicating whether a shelter is already being used.
     *
     * @param name the name you want to check if it is being used.
     * @return true if the shelter name is currently being used; false otherwise.
     */
    public static boolean shelterNameInUse(String name) {
        return shelterNames.contains(name);
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
        this.reservations = snapshot.child("reservations").getValue(Integer.class);

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
        } else if (maxVacancies == null) {
            try {
                maxVacancies = Integer.parseInt(capacity);
            } catch (Exception e) {
                maxVacancies = 0;
            }
        }
        if (restrictions == null) restrictions = "";
        if (longitude == null) throw new IllegalArgumentException("Longitude is required");
        if (latitude == null) throw new IllegalArgumentException("Latitude is required");
        if (address == null || address.isEmpty()) throw new IllegalArgumentException("Address is required");
        if (specialNotes == null) specialNotes = "";
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new IllegalArgumentException("Phone number is required");
        }

        this.ID = ID;
        this.name = name;
        shelterNames.add(name);
        this.capacity = capacity;
        this.maxVacancies = maxVacancies;
        System.out.println("name: " + name + "\nmax vacancies: " + maxVacancies);
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
        return new Shelter(ID, name, capacity, maxVacancies, restrictions, longitude, latitude, address,
                specialNotes, phoneNumber);
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
        return new Shelter(shelter.ID, name, capacity, maxVacancies, restrictions, longitude, latitude, address,
                specialNotes, phoneNumber);
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
        if (this == other) return true;
        Shelter that = (Shelter) other;
        return this.ID == that.ID;
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
     * Sets the ID of the shelter.
     *
     * @param ID the id of the shelter.
     */
    public void setID(int ID) {
        this.ID = ID;
        DataPoster.post(this);
    }

    /**
     * Fetches and returns the name of the shelter.
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the shelter.
     *
     * @param name the name of the shelter.
     */
    public void setName(String name) {
        this.name = name;
        DataPoster.post(this);
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
     * Sets the capacity of the shelter.
     *
     * @param capacity the capacity of the shelter.
     */
    public void setCapacity(String capacity) {
        this.capacity = capacity;
        DataPoster.post(this);
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
     * Fetches and returns the reservations.
     *
     * @return the reservations.
     */
    public int getReservations() {
        return reservations;
    }

//    public void setVacancies(int vacancies) {
//        this.vacancies = vacancies;
//        DataPoster.post(this);
//    }

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
     * Sets the max number of vacancies.
     *
     * @param maxVacancies the max number of vacancies.
     */
    public void setMaxVacancies(int maxVacancies) {
        this.maxVacancies = maxVacancies;
        DataPoster.post(this);
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
     * Sets the restrictions for a shelter.
     *
     * @param restrictions the restrictions for a shelter.
     */
    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
        DataPoster.post(this);
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
     * Sets the longitude value for the location of a shelter.
     *
     * @param longitude the longitude value for the location of the shelter.
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
        DataPoster.post(this);
    }

    /**
     * Fetches and returns the latitude value for the location of the shelter.
     *
     * @return the latitude value for the location of the shelter.
     */
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
        DataPoster.post(this);
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
     * Sets the address of the shelter.
     *
     * @param address the address of the shelter.
     */
    public void setAddress(String address) {
        this.address = address;
        DataPoster.post(this);
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
     * Sets any special notes for the shelter.
     *
     * @param specialNotes any special notes for the shelter.
     */
    public void setSpecialNotes(String specialNotes) {
        this.specialNotes = specialNotes;
        DataPoster.post(this);
    }

    /**
     * Fetches and returns the phone number for the shelter.
     *
     * @return the phone number for the shelter.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number for the shelter.
     *
     * @param phoneNumber the phone number for the shelter.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        DataPoster.post(this);
    }
}

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

    public static Shelter getCurrentShelter() {
        return currentShelter;
    }

    public static void setCurrentShelter(Shelter currentShelter) {
        Shelter.currentShelter = currentShelter;
    }

    public static Collection<Shelter> getShelters() {
        Collection<Shelter> collection = new ArrayList<>();
        for (int i = 0; i < shelters.size(); i++) {
            collection.add(shelters.get(i));
        }
        return collection;
    }

    public static Shelter getShelter(Integer shelterId) {
        return shelters.get(shelterId);
    }

    public static Shelter getShelterByName(String name) {
        for (int i = 0; i < shelters.size(); i++) {
            Shelter shelter = shelters.get(i);
            if (name.equals(shelter.getName())) {
                return shelter;
            }
        }
        return null;
    }

// --Commented out by Inspection START (3/31/2018 15:35):
//    public static boolean shelterNameInUse(String name) {
//        return shelterNames.contains(name);
//    }
// --Commented out by Inspection STOP (3/31/2018 15:35)

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

    public int getID() {
        return ID;
    }

// --Commented out by Inspection START (3/31/2018 15:35):
//    public void setID(int ID) {
//        this.ID = ID;
//        DataPoster.post(this);
//    }
// --Commented out by Inspection STOP (3/31/2018 15:35)

    public String getName() {
        return name;
    }

// --Commented out by Inspection START (3/31/2018 15:35):
//    public void setName(String name) {
//        this.name = name;
//        DataPoster.post(this);
//    }
// --Commented out by Inspection STOP (3/31/2018 15:35)

    public String getCapacity() {
        return capacity;
    }

// --Commented out by Inspection START (3/31/2018 15:35):
//    public void setCapacity(String capacity) {
//        this.capacity = capacity;
//        DataPoster.post(this);
//    }
// --Commented out by Inspection STOP (3/31/2018 15:35)

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

    public void addReservation(Reservation reservation) throws Exception {
        if (reservation.getNumberOfBeds() > getVacancies()) {
            throw new Exception("Can't make reservation. Not enough vacancies");
        }
        reservations += reservation.getNumberOfBeds();
        DataPoster.post(this);
    }

    public void cancelReservation(Reservation reservation) {
        reservations -= reservation.getNumberOfBeds();
        reservations = Math.max(reservations, 0);
        DataPoster.post(this);
    }

    public int getMaxVacancies() {
        return maxVacancies;
    }

// --Commented out by Inspection START (3/31/2018 15:35):
//    public void setMaxVacancies(int maxVacancies) {
//        this.maxVacancies = maxVacancies;
//        DataPoster.post(this);
//    }
// --Commented out by Inspection STOP (3/31/2018 15:35)

    public String getRestrictions() {
        return restrictions;
    }

// --Commented out by Inspection START (3/31/2018 15:35):
//    public void setRestrictions(String restrictions) {
//        this.restrictions = restrictions;
//        DataPoster.post(this);
//    }
// --Commented out by Inspection STOP (3/31/2018 15:35)

    public double getLongitude() {
        return longitude;
    }

// --Commented out by Inspection START (3/31/2018 15:35):
//    public void setLongitude(double longitude) {
//        this.longitude = longitude;
//        DataPoster.post(this);
//    }
// --Commented out by Inspection STOP (3/31/2018 15:35)

    public double getLatitude() {
        return latitude;
    }

// --Commented out by Inspection START (3/31/2018 15:35):
//    public void setLatitude(double latitude) {
//        this.latitude = latitude;
//        DataPoster.post(this);
//    }
// --Commented out by Inspection STOP (3/31/2018 15:35)

    public String getAddress() {
        return address;
    }

// --Commented out by Inspection START (3/31/2018 15:35):
//    public void setAddress(String address) {
//        this.address = address;
//        DataPoster.post(this);
//    }
// --Commented out by Inspection STOP (3/31/2018 15:35)

    public String getSpecialNotes() {
        return specialNotes;
    }

// --Commented out by Inspection START (3/31/2018 15:35):
//    public void setSpecialNotes(String specialNotes) {
//        this.specialNotes = specialNotes;
//        DataPoster.post(this);
//    }
// --Commented out by Inspection STOP (3/31/2018 15:35)

    public String getPhoneNumber() {
        return phoneNumber;
    }

// --Commented out by Inspection START (3/31/2018 15:35):
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//        DataPoster.post(this);
//    }
// --Commented out by Inspection STOP (3/31/2018 15:35)
}

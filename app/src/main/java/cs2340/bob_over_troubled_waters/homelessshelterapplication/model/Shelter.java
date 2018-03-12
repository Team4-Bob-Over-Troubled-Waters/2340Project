package cs2340.bob_over_troubled_waters.homelessshelterapplication.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.enums.AgeRanges;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.enums.Gender;

/**
 * Created by Francine on 2/16/2018.
 */

public class Shelter {
    private int ID;
    private String name;
    private String capacity;
    private String restrictions;
    private double longitude;
    private double latitude;
    private String address;
    private String specialNotes;
    private String phoneNumber;
    private Gender gender;
    private HashSet<AgeRanges> ageRanges;

    private static DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("shelters");

    private static HashMap<Integer, Shelter> shelters = new HashMap<>();

    public static Collection<Shelter> getShelters() {
        return shelters.values();
    }

    public static Shelter getShelter(Integer shelterId) {
        return shelters.get(shelterId);
    }

    /**
     * constructs a shelter object from the saved instance in the database
     * @param snapshot saved instance in the database
     */
    public Shelter(DataSnapshot snapshot) {
        this.ID = snapshot.child("id").getValue(Integer.class);
        this.name = snapshot.child("name").getValue(String.class);
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
        shelters.put(this.ID, this);
    }

    public Shelter(String ID, String name, String capacity, String restrictions, String longitude,
                   String latitude, String address, String specialNotes, String phoneNumber) {
        this.ID = Integer.parseInt(ID);
        this.name = name;
        this.capacity = capacity;
        this.restrictions = restrictions;
        this.longitude = Double.parseDouble(longitude);
        this.latitude = Double.parseDouble(latitude);
        this.address = address.replaceFirst(",", System.getProperty("line.separator"));
        this.specialNotes = specialNotes;
        this.phoneNumber = phoneNumber;
        this.gender = Gender.parseGender(restrictions);
        this.gender.addShelter(this);
        ageRanges = AgeRanges.parseAgeRanges(restrictions);
        for (AgeRanges range : ageRanges) {
            range.addShelter(this);
        }
        shelters.put(this.ID, this);
        ref.child(ID).setValue(this);
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

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSpecialNotes() {
        return specialNotes;
    }

    public void setSpecialNotes(String specialNotes) {
        this.specialNotes = specialNotes;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

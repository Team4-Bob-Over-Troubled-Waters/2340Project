package cs2340.bob_over_troubled_waters.homelessshelterapplication.model;

import android.content.Context;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.R;
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

    private static HashSet<Shelter> shelters = new HashSet<>();

    public static HashSet<Shelter> getShelters() {
        if (shelters.isEmpty()) {
            try {
                InputStream inputStream = Shelter.class
                        .getResourceAsStream("/res/raw/homeless_shelter_database.csv");
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
                String[] nextShelter;
                while ((nextShelter = csvReader.readNext()) != null) {
                    new Shelter(nextShelter[0], nextShelter[1], nextShelter[2],
                            nextShelter[3], nextShelter[4], nextShelter[5], nextShelter[6],
                            nextShelter[7], nextShelter[8]);
                }
                shelters.addAll(Shelter.getShelters());
            } catch (IOException e) {
                System.out.println("There was an error loading shelter information.");
                System.out.println(e);
            }
        }
        return shelters;
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
        shelters.add(this);
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

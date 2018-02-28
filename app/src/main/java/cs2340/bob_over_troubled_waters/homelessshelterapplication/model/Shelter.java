package cs2340.bob_over_troubled_waters.homelessshelterapplication.model;

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

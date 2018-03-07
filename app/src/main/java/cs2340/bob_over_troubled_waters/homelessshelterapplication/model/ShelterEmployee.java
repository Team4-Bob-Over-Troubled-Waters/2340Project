package cs2340.bob_over_troubled_waters.homelessshelterapplication.model;

/**
 * Created by Admin on 3/5/2018.
 */

public class ShelterEmployee extends User {

    private Shelter shelter;
    private boolean isApproved = false;

    public ShelterEmployee(String email, String password, String name) {
        super(email, password, name);
    }

    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }

    public Shelter getShelter() {
        return shelter;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void approve(AdminUser approvedBy) {
        if (approvedBy != null) {
            isApproved = true;
        } else {
            throw new IllegalArgumentException("Need admin to approve");
        }
    }
}

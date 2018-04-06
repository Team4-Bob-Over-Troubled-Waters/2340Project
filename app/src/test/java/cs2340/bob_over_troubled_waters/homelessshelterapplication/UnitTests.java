package cs2340.bob_over_troubled_waters.homelessshelterapplication;

import org.junit.Before;
import org.junit.Test;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.interfacer.DataPoster;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.AdminUser;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.HomelessPerson;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.Reservation;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.ShelterEmployee;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.User;

import static org.junit.Assert.*;

/**
 * Unit testing
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTests {

    @Before
    public void setUp() {
        DataPoster.setEnabled(false);
    }

    @Test (timeout = 200)
    public void testUserToString() {
        try {
            User employee = new ShelterEmployee("employee@test.com", "testing", null);
            User homelessPerson = new HomelessPerson("user@test.com", "testing", null);
            User admin = new AdminUser("admin@test.com", "testing", null);
            String employeeString = "employee@test.com\nShelter Employee";
            String userString = "user@test.com\nHomeless Person";
            String adminString = "admin@test.com\nAdmin User";
            assertEquals(employeeString, employee.toString());
            assertEquals(userString, homelessPerson.toString());
            assertEquals(adminString, admin.toString());
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test (timeout = 200)
    public void testHomelessPersonGetCurrentReservedNum() {
        try {
            HomelessPerson homelessPerson = new HomelessPerson("hp@test.com", "testing", null);
            assertEquals(homelessPerson.getCurrentReservedNum(), 0);
            Reservation reserve = new Reservation(4, 1);
            homelessPerson.setCurrentReservation(reserve);
            assertEquals(homelessPerson.getCurrentReservedNum(), 4);
        } catch (Exception e) {
            assertTrue(false);
        }
    }
}
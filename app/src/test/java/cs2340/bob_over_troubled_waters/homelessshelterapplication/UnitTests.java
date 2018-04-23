package cs2340.bob_over_troubled_waters.homelessshelterapplication;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

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

    @Test (timeout = 200)
    public void testAdminGetUsers() {
        try {
            User employee = new ShelterEmployee("employee@test.com", "testing", null);
            User homelessPerson = new HomelessPerson("user@test.com", "testing", null);
            User admin = new AdminUser("admin@test.com", "testing", null);
            AdminUser.addUser(employee);
            AdminUser.addUser(homelessPerson);
            AdminUser.addUser(admin);

            ArrayList<User> users = new ArrayList<>();

            users.add(admin);
            assertEquals(AdminUser.getUsers(AdminUser.class), users);

            users.add(employee);
            assertEquals(AdminUser.getUsers(AdminUser.class, ShelterEmployee.class), users);

            users.add(homelessPerson);
            assertEquals(AdminUser.getUsers(AdminUser.class, ShelterEmployee.class, HomelessPerson.class), users);
            assertEquals(AdminUser.getUsers(), users);

            users.remove(employee);
            assertEquals(AdminUser.getUsers(AdminUser.class, HomelessPerson.class), users);

            users.remove(admin);
            assertEquals(AdminUser.getUsers(HomelessPerson.class), users);

            users.clear();
            User homelessPerson2 = new HomelessPerson("user2@test.com", "testing", null);
            AdminUser.addUser(homelessPerson2);
            users.add(homelessPerson2);
            users.add(homelessPerson);
            assertEquals(AdminUser.getUsers(HomelessPerson.class), users);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test (timeout = 200)
    public void testGetUsersName() {
        try {
            String email = "email@emailCompany.com";
            String password = "reallyBadPassword";

            User homelessPerson1 = new HomelessPerson(email, password, null);
            User homelessPerson2 = new HomelessPerson(email, password, "");
            User homelessPerson3 = new HomelessPerson(email, password, "Donald Trump");
            assertEquals(email, homelessPerson1.getUsersName());
            assertEquals(email, homelessPerson2.getUsersName());
            assertEquals("Donald Trump", homelessPerson3.getUsersName());

            User shelterEmployee1 = new ShelterEmployee(email, password, null);
            User shelterEmployee2 = new ShelterEmployee(email, password, "");
            User shelterEmployee3 =
                    new ShelterEmployee(email, password, "Hannah Montana");
            assertEquals(email, shelterEmployee1.getUsersName());
            assertEquals(email, shelterEmployee2.getUsersName());
            assertEquals("Hannah Montana", shelterEmployee3.getUsersName());

            User adminUser1 = new AdminUser(email, password, null);
            User adminUser2 = new AdminUser(email, password, "");
            User adminUser3 = new AdminUser(email, password, "Frank Underwood");
            assertEquals(email, adminUser1.getUsersName());
            assertEquals(email, adminUser2.getUsersName());
            assertEquals("Frank Underwood", adminUser3.getUsersName());
        } catch (Exception e) {
            assertTrue(false);
        }
    }
}
package cs2340.bob_over_troubled_waters.homelessshelterapplication.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.R;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.interfacer.DataPoster;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.Shelter;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.ShelterEmployee;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.User;

/**
 * home screen for a shelter employee
 */
public class EmployeeHome extends AppCompatActivity {

    private ShelterEmployee currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_home);

        currentUser = (ShelterEmployee) User.getCurrentUser();

        TextView welcomeMessage = findViewById(R.id.welcome_message);
        welcomeMessage.setText(currentUser.getWelcomeMessage());

        TextView worksAt = findViewById(R.id.works_at_view);
        if (currentUser.getShelter() != null) {
            String text = "Works at " + currentUser.getShelter();
            worksAt.setText(text);
        } else {
            worksAt.setText("");
        }
    }

    /**
     * logout current user
     * @param view current view
     */
    public void logoutButtonAction(View view) {
        DataPoster.logout();
        finish();
    }

    /**
     * open the view for that employee's shelter
     * @param view current view
     */
    public void viewShelterInfoButtonAction(View view) {
        Intent intent = new Intent(this, ShelterPage.class);
        Shelter.setCurrentShelter(currentUser.getShelter());
        startActivity(intent);
    }

    /**
     * edit your shelter's info
     * @param view current view
     */
    public void manageShelterButtonAction(View view) {
        Intent intent = new Intent(this, AddEditShelter.class);
        Shelter.setCurrentShelter(currentUser.getShelter());
        startActivity(intent);
    }
}

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

public class EmployeeHome extends AppCompatActivity {

    ShelterEmployee currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_home);

        currentUser = (ShelterEmployee) User.getCurrentUser();

        TextView welcomeMessage = findViewById(R.id.welcome_message);
        welcomeMessage.setText(currentUser.getWelcomeMessage());

        TextView worksAt = findViewById(R.id.works_at_view);
        if (currentUser.getShelter() != null) {
            worksAt.setText("Works at " + currentUser.getShelter());
        } else {
            worksAt.setText("");
        }
    }

    public void logoutButtonAction(View view) {
        DataPoster.logout();
        finish();
    }

    public void viewShelterInfoButtonAction(View view) {
        Intent intent = new Intent(this, ShelterPage.class);
        Shelter.setCurrentShelter(currentUser.getShelter());
        startActivity(intent);
    }

    public void manageShelterButtonAction(View view) {
        Intent intent = new Intent(this, AddEditShelter.class);
        Shelter.setCurrentShelter(currentUser.getShelter());
        startActivity(intent);
    }
}

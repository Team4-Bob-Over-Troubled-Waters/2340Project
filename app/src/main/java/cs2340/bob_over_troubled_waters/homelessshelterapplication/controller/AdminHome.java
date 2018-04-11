package cs2340.bob_over_troubled_waters.homelessshelterapplication.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.R;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.Shelter;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.User;

/**
 * home screen for an admin user
 */
public class AdminHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        TextView welcomeMessage = findViewById(R.id.welcome_message);
        welcomeMessage.setText(User.getCurrentUser().getWelcomeMessage());
    }

    /**
     * loads the shelter listing page
     * @param view current view
     */
    public void loadShelterAction(View view) {
        ShelterSearch.clearCriteria();
        Intent intent = new Intent(this, ShelterListingActivity.class);
        startActivity(intent);
    }

    /**
     * logs out the current user
     * @param view current view
     */
    public void logoutButtonAction(View view) {
        User.logout();
        finish();
    }

    /**
     * opens a shelter search view
     * @param view current view
     */
    public void searchSheltersButtonAction(View view) {
        Intent intent = new Intent(this, ShelterSearch.class);
        startActivity(intent);
    }

    /**
     * opens user listing view
     * @param view current view
     */
    public void viewUsersButtonAction(View view) {
        Intent intent = new Intent(this, UserListing.class);
        startActivity(intent);
    }

    /**
     * opens form to add a new shelter
     * @param view current view
     */
    public void addShelterButtonAction(View view) {
        Shelter.setCurrentShelter(null);
        Intent intent = new Intent(this, AddEditShelter.class);
        startActivity(intent);
    }
}

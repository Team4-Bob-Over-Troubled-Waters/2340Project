package cs2340.bob_over_troubled_waters.homelessshelterapplication.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.R;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.AdminUser;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.ShelterEmployee;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.User;

public class UserHome extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = User.getCurrentUser();
        setContentView(R.layout.activity_user_home);
        TextView welcomeMessage = findViewById(R.id.welcome_message);
        welcomeMessage.setText(user.getWelcomeMessage());
        if (user instanceof AdminUser || user instanceof ShelterEmployee) {
            Button viewReservationButton = findViewById(R.id.view_reservation_button);
            viewReservationButton.setVisibility(View.GONE);
            viewReservationButton.setEnabled(false);
        }
    }

    /**
     * Logs the user out after they hit the logout button.
     *
     * @param view the View object of the home screen user interface for the user.
     */
    public void logoutButtonAction(View view) {
        User.logout();
        finish();
    }

    /**
     * Loads and starts the shelter listing page.
     *
     * @param view the View object of the home screen user interface for the user.
     */
    public void loadShelterAction(View view) {
        ShelterSearch.clearCriteria();
        Intent intent = new Intent(this, ShelterListingActivity.class);
        startActivity(intent);
    }

    /**
     * Loads and starts the shelter search page.
     *
     * @param view the View object of the home screen user interface for the user.
     */
    public void searchSheltersButtonAction(View view) {
        Intent intent = new Intent(this, ShelterSearch.class);
        startActivity(intent);
    }

    /**
     * Loads and starts the page for viewing reservations.
     * @param view
     */
    public void viewReservationButtonAction(View view) {
        Intent intent = new Intent(this, ReservationPage.class);
        startActivity(intent);
    }
}

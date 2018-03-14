package cs2340.bob_over_troubled_waters.homelessshelterapplication.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    public void logoutButtonAction(View view) {
        User.logout();
        finish();
    }

    public void loadShelterAction(View view) {
        ShelterSearch.clearCriteria();
        Intent intent = new Intent(this, ShelterListingActivity.class);
        startActivity(intent);
    }

    public void searchSheltersButtonAction(View view) {
        Intent intent = new Intent(this, ShelterSearch.class);
        startActivity(intent);
    }

    public void viewReservationButtonAction(View view) {
        Intent intent = new Intent(this, ReservationPage.class);
        startActivity(intent);
    }
}

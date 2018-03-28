package cs2340.bob_over_troubled_waters.homelessshelterapplication.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.R;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.HomelessPerson;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.Reservation;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.Shelter;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.User;

public class ReservationPage extends AppCompatActivity {
    private Reservation currentReservation = null;
    private TextView reservationDetails;
    private HomelessPerson currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_page);

        currentUser = (HomelessPerson) User.getCurrentUser();

        currentReservation = currentUser.getCurrentReservation();
        reservationDetails = findViewById(R.id.text_reservation);
        if (currentReservation == null || currentReservation.getShelter() == null) {
            noReservation();
        } else {
            reservationDetails.setText(currentReservation.toString());
        }
    }

    public void backButtonAction(View view) {
        finish();
    }

    public void cancelReservationButtonAction(View view) {
        Shelter reservationShelter = currentReservation.getShelter();
        reservationShelter.cancelReservation(currentReservation);
//        int reservedBeds = currentReservation.getNumberOfBeds();
//        int oldVacancies = reservationShelter.getVacancies();
//        int newVacancies = oldVacancies + reservedBeds;
//        int maxVacancies = reservationShelter.getMaxVacancies();
//        if (newVacancies < maxVacancies) {
//            reservationShelter.setVacancies(newVacancies);
//        } else {
//            reservationShelter.setVacancies(maxVacancies);
//        }

        currentUser.setCurrentReservation(null);

        CancelReservationDialog newFragment = new CancelReservationDialog();
        newFragment.show(getFragmentManager(), "cancel reservation dialog");

        noReservation();
    }

    private void noReservation() {
        reservationDetails.setText(R.string.no_current_reservation);
        Button cancelReservationButton = findViewById(R.id.cancel_reservation_button);
        cancelReservationButton.setVisibility(View.GONE);
        cancelReservationButton.setEnabled(false);
    }
}

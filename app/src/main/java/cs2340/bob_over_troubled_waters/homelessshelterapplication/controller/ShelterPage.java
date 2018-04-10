package cs2340.bob_over_troubled_waters.homelessshelterapplication.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.R;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.AdminUser;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.HomelessPerson;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.Reservation;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.Shelter;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.ShelterEmployee;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.User;

/**
 * Created by Francine on 2/21/2018.
 * The page displaying information about a single shelter.
 */

public class ShelterPage extends AppCompatActivity implements NumberPicker.OnValueChangeListener{
    private Shelter selectedShelter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_page);

        TextView name = findViewById(R.id.text_name);
        TextView capacity = findViewById(R.id.text_capacity);
        TextView vacancies = findViewById(R.id.text_vacancies);
        TextView restrictions = findViewById(R.id.text_restrictions);
        TextView address = findViewById(R.id.text_address);
        TextView coordinates = findViewById(R.id.text_coordinates);
        TextView phone = findViewById(R.id.text_phone);
        TextView notes = findViewById(R.id.text_special_notes);

        User currentUser = User.getCurrentUser();
        if (currentUser instanceof AdminUser || currentUser instanceof ShelterEmployee) {
            Button reserveButton = findViewById(R.id.reserve_button);
            reserveButton.setVisibility(View.GONE);
            reserveButton.setEnabled(false);

            Button editButton = findViewById(R.id.edit_shelter_button);
            editButton.setVisibility(View.VISIBLE);
        }

        selectedShelter = Shelter.getCurrentShelter();
        Shelter.setCurrentShelter(selectedShelter);
        name.setText(selectedShelter.getName());
        String text = getString(R.string.capacity) + selectedShelter.getCapacity();
        capacity.setText(text);
        text = getString(R.string.vacancies) + selectedShelter.getVacancies();
        vacancies.setText(text);
        text = getString(R.string.restrictions) + selectedShelter.getRestrictions();
        restrictions.setText(text);
        address.setText(selectedShelter.getAddress());
        String coordinatesStr = "";
        if (selectedShelter.getLatitude() > 0) {
            coordinatesStr += selectedShelter.getLatitude() + "°N";
        } else {
            coordinatesStr += -selectedShelter.getLatitude() + "°S";
        }
        if (selectedShelter.getLongitude() < 0) {
            coordinatesStr += ", " + (-selectedShelter.getLongitude()) + "°W";
        } else {
            coordinatesStr += ", " + selectedShelter.getLongitude() + "°E";
        }
        coordinates.setText(coordinatesStr);
        phone.setText(selectedShelter.getPhoneNumber());
        if (selectedShelter.getSpecialNotes() != null
                && !selectedShelter.getSpecialNotes().isEmpty()) {
            text = getString(R.string.notes) + selectedShelter.getSpecialNotes();
            notes.setText(text);
        } else {
            notes.setText("");
        }
    }

    /**
     * Terminates necessary processes to leave the shelter page.
     *
     * @param view the View object of the shelter page.
     */
    public void backButtonAction(View view) {
        finish();
    }

    /**
     * Opens page to make a reservation.
     *
     * @param view the View object of the shelter page.
     */
    public void reservePageButtonAction(View view) {
        HomelessPerson currentUser = (HomelessPerson) User.getCurrentUser();
        Reservation currentReservation = currentUser.getCurrentReservation();
        if (currentReservation == null || currentReservation.getShelter() == null) {
            ReserveDialog newFragment = new ReserveDialog();
            newFragment.show(getFragmentManager(), "reserve dialog");
        } else {
            DoubleReservationDialog newFragment = new DoubleReservationDialog();
            newFragment.show(getFragmentManager(), "double reservation dialog");
        }
    }

    public void mapButtonAction(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView vacancies = findViewById(R.id.text_vacancies);
        String text = getString(R.string.vacancies) + selectedShelter.getVacancies();
        vacancies.setText(text);
    }

    /**
     * Opens page to edit a shelter.
     *
     * @param view the View object of the shelter page.
     */
    public void editShelterButtonAction(View view) {
        Intent intent = new Intent(this, AddEditShelter.class);
        startActivity(intent);
        finish();
    }
}

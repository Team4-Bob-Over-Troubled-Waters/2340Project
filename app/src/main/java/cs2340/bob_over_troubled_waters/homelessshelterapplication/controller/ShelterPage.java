package cs2340.bob_over_troubled_waters.homelessshelterapplication.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.R;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.AdminUser;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.HomelessPerson;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.MapFragment;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.Reservation;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.Shelter;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.ShelterEmployee;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.User;

/**
 * Created by Francine on 2/21/2018.
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

        //MapFragment mapFragment = new MapFragment();
        //android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        //manager.beginTransaction().replace(R.id.mapView, mapFragment).commit();

        selectedShelter = Shelter.getCurrentShelter();
        Shelter.setCurrentShelter(selectedShelter);
        name.setText(selectedShelter.getName());
        capacity.setText(getString(R.string.capacity) + selectedShelter.getCapacity());
        vacancies.setText(getString(R.string.vacancies) + selectedShelter.getVacancies());
        restrictions.setText(getString(R.string.restrictions) + selectedShelter.getRestrictions());
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
            notes.setText(getString(R.string.notes) + selectedShelter.getSpecialNotes());
        } else {
            notes.setText("");
        }
    }

    public void backButtonAction(View view) {
        finish();
    }

    public void reservePageButtonAction(View view) {
        HomelessPerson currentUser = (HomelessPerson) User.getCurrentUser();
        Reservation currentReservation = currentUser.getCurrentReservation();
        if (currentReservation == null) {
            ReserveDialog newFragment = new ReserveDialog();
            newFragment.setValueChangeListener(this);
            newFragment.show(getFragmentManager(), "reserve dialog");
        } else {
            DoubleReservationDialog newFragment = new DoubleReservationDialog();
            newFragment.show(getFragmentManager(), "double reservation dialog");
        }
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView vacancies = findViewById(R.id.text_vacancies);
        vacancies.setText(getString(R.string.vacancies) + selectedShelter.getVacancies());
    }

    public void editShelterButtonAction(View view) {
        Intent intent = new Intent(this, AddEditShelter.class);
        startActivity(intent);
        finish();
    }
}

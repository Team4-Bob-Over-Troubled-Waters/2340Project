package cs2340.bob_over_troubled_waters.homelessshelterapplication.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.R;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.MapFragment;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.Shelter;

/**
 * Created by Francine on 2/21/2018.
 */

public class ShelterPage extends AppCompatActivity {
    private Shelter selectedShelter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_page);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);

        TextView name = findViewById(R.id.text_name);
        TextView capacity = findViewById(R.id.text_capacity);
        TextView restrictions = findViewById(R.id.text_restrictions);
        //TextView latitude = findViewById(R.id.text_latitude);
        //TextView longitude = findViewById(R.id.text_longitude);
        TextView address = findViewById(R.id.text_address);
        TextView coordinates = findViewById(R.id.text_coordinates);
        TextView phone = findViewById(R.id.text_phone);
        TextView notes = findViewById(R.id.text_special_notes);

        //MapFragment mapFragment = new MapFragment();
        //android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        //manager.beginTransaction().replace(R.id.mapView, mapFragment).commit();

        ArrayList<Shelter> shelters = ShelterListingActivity.getShelters();
        selectedShelter = shelters.get(position);
        name.setText(selectedShelter.getName());
        capacity.setText(getString(R.string.capacity) + selectedShelter.getCapacity());
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
        System.out.println("Set all text fields");
    }

    public void backButtonAction(View view) {
        finish();
    }
}

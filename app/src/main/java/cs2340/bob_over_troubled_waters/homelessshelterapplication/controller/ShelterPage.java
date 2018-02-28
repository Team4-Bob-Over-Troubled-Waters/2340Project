package cs2340.bob_over_troubled_waters.homelessshelterapplication.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.R;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.Shelter;

/**
 * Created by Francine on 2/21/2018.
 */

public class ShelterPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Created ShelterPage");
        setContentView(R.layout.activity_shelter_page);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);

        TextView name = findViewById(R.id.text_name);
        TextView capacity = findViewById(R.id.text_capacity);
        TextView restrictions = findViewById(R.id.text_restrictions);
        TextView address = findViewById(R.id.text_address);
        TextView coordinates = findViewById(R.id.text_coordinates);
        TextView phone = findViewById(R.id.text_phone);
        TextView notes = findViewById(R.id.text_special_notes);

        ArrayList<Shelter> shelters = ShelterListingActivity.getShelters();
        Shelter selectedShelter = shelters.get(position);
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

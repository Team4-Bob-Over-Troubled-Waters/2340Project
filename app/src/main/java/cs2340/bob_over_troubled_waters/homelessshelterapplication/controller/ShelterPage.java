package cs2340.bob_over_troubled_waters.homelessshelterapplication.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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
        TextView latitude = findViewById(R.id.text_latitude);
        TextView longitude = findViewById(R.id.text_longitude);
        TextView address = findViewById(R.id.text_address);
        TextView phone = findViewById(R.id.text_phone);

        //MapFragment mapFragment = new MapFragment();
        //android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        //manager.beginTransaction().replace(R.id.mapView, mapFragment).commit();

        ArrayList<Shelter> shelters = ShelterListingActivity.getShelters();
        selectedShelter = shelters.get(position);
        name.setText(selectedShelter.getName());
        capacity.setText("Capacity: " + selectedShelter.getCapacity());
        restrictions.setText("Restrictions: " + selectedShelter.getRestrictions());
        latitude.setText("Latitude: " + selectedShelter.getLatitude());
        longitude.setText("Longitude: " + selectedShelter.getLongitude());
        address.setText(selectedShelter.getAddress());
        phone.setText(selectedShelter.getPhoneNumber());
    }
}

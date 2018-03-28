package cs2340.bob_over_troubled_waters.homelessshelterapplication.controller;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.R;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.Shelter;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private LatLng centerCoordinates = new LatLng(33.753594, -84.390429);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        ArrayList<Shelter> shelters = ShelterListingActivity.getShelters();
        for (Shelter shelter : shelters) {
            LatLng coordinates = new LatLng(shelter.getLatitude(), shelter.getLongitude());
            mMap.addMarker(new MarkerOptions()
                    .position(coordinates)
                    .title(shelter.getName())
                    .snippet("tap for details"));
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerCoordinates, 12));
        mMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Shelter selected = Shelter.getShelterByName(marker.getTitle());
        if (selected != null) {
            Shelter.setCurrentShelter(selected);
            Intent myIntent = new Intent(this, ShelterPage.class);
            startActivity(myIntent);
        }
    }

    public void searchButtonAction(View view) {
        Intent intent = new Intent(this, ShelterSearch.class);
        startActivity(intent);
    }
}

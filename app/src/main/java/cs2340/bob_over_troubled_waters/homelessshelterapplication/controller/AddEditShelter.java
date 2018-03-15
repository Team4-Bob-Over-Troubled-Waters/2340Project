package cs2340.bob_over_troubled_waters.homelessshelterapplication.controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.R;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.Shelter;

public class AddEditShelter extends AppCompatActivity {

    Shelter shelter;
    TextView shelterNameView;
    EditText shelterNameBox;
    EditText addressBox;
    EditText latitudeBox;
    EditText longitudeBox;
    EditText phoneBox;
    EditText restrictionsBox;
    EditText capacityBox;
    EditText notesBox;
    EditText maxVacanciesBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_shelter);

        shelter = Shelter.getCurrentShelter();
        shelterNameView = findViewById(R.id.shelter_name_view);
        shelterNameBox = findViewById(R.id.shelter_name_box);
        addressBox = findViewById(R.id.address_box);
        latitudeBox = findViewById(R.id.latitude_box);
        longitudeBox = findViewById(R.id.longitude_box);
        phoneBox = findViewById(R.id.phone_box);
        restrictionsBox = findViewById(R.id.restrictions_box);
        capacityBox = findViewById(R.id.capacity_box);
        notesBox = findViewById(R.id.notes_box);
        maxVacanciesBox = findViewById(R.id.max_vacancies_box);

        if (shelter != null) setFields();
    }

    public void setFields() {
        shelterNameView.setText(shelter.getName());
        shelterNameBox.setText(shelter.getName());
        addressBox.setText(shelter.getAddress());
        latitudeBox.setText("" + shelter.getLatitude());
        longitudeBox.setText("" + shelter.getLongitude());
        phoneBox.setText(shelter.getPhoneNumber());
        restrictionsBox.setText(shelter.getRestrictions());
        capacityBox.setText(shelter.getCapacity());
        notesBox.setText(shelter.getSpecialNotes());
        maxVacanciesBox.setText("" + shelter.getMaxVacancies());
    }

    public void confirmButtonAction(View view) {
        String name = shelterNameBox.getText().toString();
        String capacity = capacityBox.getText().toString();
        Integer maxVacancies;
        try {
            maxVacancies = Integer.parseInt(maxVacanciesBox.getText().toString());
        } catch (NumberFormatException e) {
            maxVacancies = null;
        }
        String restrictions = restrictionsBox.getText().toString();
        Double longitude;
        try {
            longitude = Double.parseDouble(longitudeBox.getText().toString());
        } catch (NumberFormatException e) {
            longitude = null;
        }
        Double latitude;
        try {
            latitude = Double.parseDouble(latitudeBox.getText().toString());
        } catch (NumberFormatException e) {
            latitude = null;
        }
        String address = addressBox.getText().toString();
        String specialNotes = notesBox.getText().toString();
        String phoneNumber = phoneBox.getText().toString();

        ShelterAdder adder = new ShelterAdder(name, capacity, maxVacancies, restrictions, longitude,
                latitude, address, specialNotes, phoneNumber);
        adder.execute();
    }

    public void backButtonAction(View view) {
        finish();
    }

    private class ShelterAdder extends AsyncTask<Void, Void, String> {

        String name;
        String capacity;
        Integer maxVacancies;
        String restrictions;
        Double longitude;
        Double latitude;
        String address;
        String specialNotes;
        String phoneNumber;

        public ShelterAdder(String name, String capacity, Integer maxVacancies, String restrictions,
                            Double longitude, Double latitude, String address, String specialNotes,
                            String phoneNumber) {
            this.name = name;
            this.capacity = capacity;
            this.maxVacancies = maxVacancies;
            this.restrictions = restrictions;
            this.longitude = longitude;
            this.latitude = latitude;
            this.address = address;
            this.specialNotes = specialNotes;
            this.phoneNumber = phoneNumber;
        }

        @Override
        public String doInBackground(Void ... params) {
            try {
                if (shelter == null) {
                    shelter = Shelter.addShelter(name, capacity, maxVacancies, restrictions, longitude, latitude,
                            address, specialNotes, phoneNumber);
                    Shelter.setCurrentShelter(shelter);
                } else {
                    shelter = Shelter.updateShelter(shelter, name, capacity, maxVacancies, restrictions, longitude,
                            latitude, address, specialNotes, phoneNumber);
                    Shelter.setCurrentShelter(shelter);
                }
                return null;
            } catch (IllegalArgumentException e) {
                return e.getMessage();
            }
        }

        @Override
        public void onPostExecute(final String errorMessage) {
            if (errorMessage == null) {
                Intent intent = new Intent(getApplicationContext(), ShelterPage.class);
                getApplicationContext().startActivity(intent);
                finish();
            } else {
                Button confirmButton = findViewById(R.id.confirm_button);
                confirmButton.setError(errorMessage);
                shelterNameBox.setError(errorMessage);
                System.out.println(errorMessage);
            }
        }
    }
}

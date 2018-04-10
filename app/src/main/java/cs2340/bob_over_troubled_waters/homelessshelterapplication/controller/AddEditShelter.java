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

    private Shelter shelter;
    private TextView shelterNameView;
    private EditText shelterNameBox;
    private EditText addressBox;
    private EditText latitudeBox;
    private EditText longitudeBox;
    private EditText phoneBox;
    private EditText restrictionsBox;
    private EditText capacityBox;
    private EditText notesBox;
    private EditText maxVacanciesBox;

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

    /**
     * Sets the values for the fields for a shelter.
     */
    public void setFields() {
        shelterNameView.setText(shelter.getName());
        shelterNameBox.setText(shelter.getName());
        addressBox.setText(shelter.getAddress());
        String text = "" + shelter.getLatitude();
        latitudeBox.setText(text);
        text = "" + shelter.getLongitude();
        longitudeBox.setText(text);
        phoneBox.setText(shelter.getPhoneNumber());
        restrictionsBox.setText(shelter.getRestrictions());
        capacityBox.setText(shelter.getCapacity());
        notesBox.setText(shelter.getSpecialNotes());
        text = "" + shelter.getMaxVacancies();
        maxVacanciesBox.setText(text);
    }

    /**
     * Updates internal information to correspond with user input after the user
     * presses the confirmation button.
     *
     * @param view the View object of the user interface screen for adding
     *             and editing shelters.
     */
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

    /**
     * Finalizes action to leave the add/edit shelter interface once the
     * user hits the back button.
     *
     * @param view the View object for the User Interface of the add/edit shelter
     *             functions
     */
    public void backButtonAction(View view) {
        finish();
    }

    private class ShelterAdder extends AsyncTask<Void, Void, String> {

        final String name;
        final String capacity;
        final Integer maxVacancies;
        final String restrictions;
        final Double longitude;
        final Double latitude;
        final String address;
        final String specialNotes;
        final String phoneNumber;

        ShelterAdder(String name, String capacity, Integer maxVacancies, String restrictions,
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

package cs2340.bob_over_troubled_waters.homelessshelterapplication.controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.R;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.interfacer.ShelterLoader;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.Shelter;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.ShelterEmployee;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.User;

public class ChooseShelter extends AppCompatActivity {

    Spinner shelterSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_shelter);

        if (!ShelterLoader.sheltersLoaded()) {
            ShelterLoader.setInstance(new ShelterSpinnerPopulator());
        } else {
            populateSpinner();
        }
    }

    public void continueButtonAction(View view) {
        Shelter selection = (Shelter) shelterSpinner.getSelectedItem();
        Button button = findViewById(R.id.continue_button);
        button.setError(null);
        if (selection == null) {
            button.setError("Must select a shelter.");
        } else {
            ShelterEmployee currentUser = (ShelterEmployee) User.getCurrentUser();
            currentUser.setShelter(selection);
            Intent intent = new Intent(this, UserPendingApproval.class);
            startActivity(intent);
            finish();
        }
    }

    public void logoutButtonAction(View view) {
        User.logout();
        finish();
    }

    private void populateSpinner() {
        shelterSpinner = findViewById(R.id.shelter_spinner);
        ArrayList<Shelter> shelters;
        shelters = new ArrayList<>(Shelter.getShelters());
        ArrayAdapter<Shelter> shelterAdapter = new ArrayAdapter<Shelter>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, shelters);
        shelterSpinner.setAdapter(shelterAdapter);
        shelterSpinner.requestFocus();
    }

    private class ShelterSpinnerPopulator extends ShelterLoader {

        @Override
        public void onPostExecute(final String errorMessage) {
            if (errorMessage == null) {
                populateSpinner();
            } else {
                Intent intent = new Intent(getApplicationContext(), ErrorPage.class);
                intent.putExtra("message", errorMessage);
                getApplicationContext().startActivity(intent);
                finish();
            }
        }
    }
}
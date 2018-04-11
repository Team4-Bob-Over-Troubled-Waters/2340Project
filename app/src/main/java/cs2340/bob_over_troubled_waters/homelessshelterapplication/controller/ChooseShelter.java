package cs2340.bob_over_troubled_waters.homelessshelterapplication.controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import org.apache.commons.lang3.time.StopWatch;

import java.util.ArrayList;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.R;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.interfacer.ShelterLoader;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.Shelter;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.ShelterEmployee;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.User;

/**
 * view controller where a new Shelter employee chooses their shelter
 */
public class ChooseShelter extends AppCompatActivity {

    private Spinner shelterSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_shelter);

        new ShelterSpinnerPopulator().execute();
    }

    /**
     * confirms shelter choice and continues to next view
     * @param view current view
     */
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

    /**
     * logs out current user
     * @param view current view
     */
    public void logoutButtonAction(View view) {
        User.logout();
        finish();
    }

    private void populateSpinner() {
        shelterSpinner = findViewById(R.id.shelter_spinner);
        ArrayList<Shelter> shelters;
        shelters = new ArrayList<>(Shelter.getShelters());
        ArrayAdapter<Shelter> shelterAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, shelters);
        shelterSpinner.setAdapter(shelterAdapter);
        shelterSpinner.requestFocus();
    }

    /**
     * this is necessary because the choose shelter page is right after login and the shelters
     * may not be done loading yet
     *
     * this class waits for the
     */
    private class ShelterSpinnerPopulator extends AsyncTask<Void, Void, Void> {

        @Override
        public Void doInBackground(Void ... params) {
            // to avoid potential memory leaks - fail-safe to stop task after 10 seconds
            StopWatch timer = new StopWatch();
            timer.start();
            while (ShelterLoader.sheltersLoaded() && timer.getTime() < 10000) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        public void onPostExecute(final Void none) {
            populateSpinner();
        }

    }
}

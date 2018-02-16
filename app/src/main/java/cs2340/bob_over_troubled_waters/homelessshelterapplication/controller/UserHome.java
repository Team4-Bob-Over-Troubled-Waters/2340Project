package cs2340.bob_over_troubled_waters.homelessshelterapplication.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.R;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.Shelter;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.User;

public class UserHome extends AppCompatActivity {

    private User user;
    private ArrayList<Shelter> shelters = new ArrayList<>();
    private ListView shelterListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = User.getCurrentUser();
        setContentView(R.layout.activity_user_home);
        TextView welcomeMessage = findViewById(R.id.welcome_message);
        welcomeMessage.setText(String.format("Welcome %s!", user.getUsersName()));
        if (user.isAdmin()) {
            TextView isAdminView = findViewById(R.id.is_admin_view);
            isAdminView.setText("Admin user");
        }

    }

    public void logoutButtonAction(View view) {
        Intent intent = new Intent(this, WelcomeScreen.class);
        startActivity(intent);
        finish();
    }

    public void loadShelterAction(View view) {
        try (
            InputStream inputStream = getResources().openRawResource(R.raw.homeless_shelter_database);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()
        ) {
            String[] nextShelter;
            System.out.println("File was found.");
            while ((nextShelter = csvReader.readNext()) != null) {
                shelters.add(new Shelter(nextShelter[0], nextShelter[1], nextShelter[2],
                        nextShelter[3], nextShelter[4], nextShelter[5], nextShelter[6],
                        nextShelter[7], nextShelter[8]));
            }
            System.out.println("File has been parsed.");
            setContentView(R.layout.activity_shelter_listing);
            shelterListView = (ListView) findViewById(R.id.shelter_listing);
            ArrayAdapter<Shelter> arrayAdapter = new ArrayAdapter<Shelter>(this,
                    android.R.layout.simple_list_item_1, shelters);
            shelterListView.setAdapter(arrayAdapter);
            System.out.println("List has been updated.");
        } catch (IOException e) {
            System.out.println("There was an error loading shelter information.");
        }
    }
}

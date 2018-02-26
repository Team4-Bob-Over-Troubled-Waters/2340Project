package cs2340.bob_over_troubled_waters.homelessshelterapplication.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.R;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.Shelter;

/**
 * Created by Francine on 2/21/2018.
 */

public class ShelterListingActivity extends AppCompatActivity {
    private static ArrayList<Shelter> shelters = new ArrayList<>();
    private ListView shelterListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try (
                InputStream inputStream = getResources().openRawResource(R.raw.homeless_shelter_database);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()
        ) {
            String[] nextShelter;
            while ((nextShelter = csvReader.readNext()) != null) {
                shelters.add(new Shelter(nextShelter[0], nextShelter[1], nextShelter[2],
                        nextShelter[3], nextShelter[4], nextShelter[5], nextShelter[6],
                        nextShelter[7], nextShelter[8]));
            }
            setContentView(R.layout.activity_shelter_listing);
            shelterListView = (ListView) findViewById(R.id.shelter_listing);
            ArrayAdapter<Shelter> arrayAdapter = new ArrayAdapter<Shelter>(this,
                    android.R.layout.simple_list_item_1, shelters);
            shelterListView.setAdapter(arrayAdapter);
            shelterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getBaseContext(), ShelterPage.class);
                    intent.putExtra("position", i);
                    System.out.println("About to open ShelterPage");
                    startActivity(intent);
                    System.out.println("Started new intent");
                }
            });
        } catch (IOException e) {
            System.out.println("There was an error loading shelter information.");
        }
    }

    public static ArrayList<Shelter> getShelters() {
        return shelters;
    }
}

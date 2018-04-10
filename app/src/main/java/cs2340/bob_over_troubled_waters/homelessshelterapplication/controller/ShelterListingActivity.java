package cs2340.bob_over_troubled_waters.homelessshelterapplication.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.R;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.Shelter;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.enums.AgeRanges;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.enums.Gender;

/**
 * Created by Francine on 2/21/2018.
 */

public class ShelterListingActivity extends AppCompatActivity {
    private static ArrayList<Shelter> shelters = new ArrayList<Shelter>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_listing);

        shelters.clear();

        updateView();
    }

    /**
     * updates the list view to have all the appropriate shelters in it
     */
    private void updateView() {
        shelters.addAll(Shelter.getShelters());
        narrowResults();
        ListView shelterListView = findViewById(R.id.shelter_listing);
        ArrayAdapter<Shelter> arrayAdapter = new ArrayAdapter<Shelter>(this,
                android.R.layout.simple_list_item_1, shelters);
        shelterListView.setAdapter(arrayAdapter);
        shelterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getBaseContext(), ShelterPage.class);
                Shelter.setCurrentShelter(shelters.get(i));
                startActivity(intent);
            }
        });
    }

    /**
     *
     * @param view
     */
    public void backButtonAction(View view) {
        finish();
    }

    /**
     * if we're searching for shelters in a particular category
     */
    private void narrowResults() {
        String searchString = ShelterSearch.getSearchString();

        /*
        Returns an empty set if all shelters meet age and gender criteria;
        null if no shelters meet the age, gender, and searchString criteria;
        and, otherwise, the set of shelters meeting the age, gender, and
        searchString criteria.
         */
        HashSet<Shelter> narrowedShelters = getShelters(searchString,
                ShelterSearch.getAgeCriteria(), ShelterSearch.getGenderCriteria());

        if (narrowedShelters == null) {
            shelters.clear();
        } else if (!narrowedShelters.isEmpty()) {
            shelters.clear();
            shelters.addAll(narrowedShelters);
        } else if (searchString != null) {
            /*
            Note that if the empty set is returned, we only know that all
            shelters meet the age and gender criteria; we still need to
            check to make sure that they the searchString criteria.
            */
            for (Shelter shelter : shelters) {
                if (shelter.toString().toLowerCase().contains(searchString)) {
                    narrowedShelters.add(shelter);
                }
            }
            shelters.clear();
            shelters.addAll(narrowedShelters);
        }
    }

    private HashSet<Shelter> getShelters(
            String searchString, ArrayList<AgeRanges> ranges, ArrayList<Gender> genders) {
        HashSet<Shelter> shelters = new HashSet<>();
        if (genders == null) return getShelters(searchString, ranges, shelters);
        for (Gender gender : genders) {
            addValidSheltersToHashSet(shelters, gender.getShelters(), searchString);
        }
        return getShelters(searchString, ranges, shelters);
    }

    private HashSet<Shelter> getShelters(
            String searchString, ArrayList<AgeRanges> ranges, HashSet<Shelter> shelters) {
        if (ranges == null) return shelters;
        if (!shelters.isEmpty()) { // if shelters isn't empty then there is some gender criteria
            HashSet<Shelter> sheltersForAgeRange =
                    getShelters(searchString, ranges, new HashSet<Shelter>());
            return intersection(shelters, sheltersForAgeRange);
        }
        for (AgeRanges range : ranges) {
            addValidSheltersToHashSet(shelters, range.getShelters(), searchString);
        }
        return shelters;
    }
    
    private HashSet<Shelter> addValidSheltersToHashSet(
            HashSet<Shelter> shelterSet, ArrayList<Shelter> shelterList, String searchString) {
        for (Shelter shelter : shelterList) {
            if (searchString == null
                        || shelter.toString().toLowerCase().contains(searchString)) {
                    shelterSet.add(shelter);
            }
        }
        return shelterSet;
    }

    /* Returns HashSet of elements in both set1 and set2 */
    private HashSet<Shelter> intersection(HashSet<Shelter> set1, HashSet<Shelter> set2) {
        Iterator<Shelter> iterator = set1.iterator();
        while (iterator.hasNext()) {
            Shelter shelter = iterator.next();
            if (!set2.contains(shelter)) iterator.remove();
        }
        return !set1.isEmpty() ? set1 : null;
    }
}
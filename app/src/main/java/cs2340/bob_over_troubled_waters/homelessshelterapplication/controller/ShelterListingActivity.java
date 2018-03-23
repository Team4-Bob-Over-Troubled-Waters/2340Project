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
//                intent.putExtra("position", i);
                Shelter.setCurrentShelter(shelters.get(i));
                startActivity(intent);
            }
        });
    }

    public void backButtonAction(View view) {
        finish();
    }

    /**
     * if we're searching for shelters in a particular category
     * TODO make this better
     */
    private void narrowResults() {
        String searchString = ShelterSearch.getSearchString();
        HashSet<Shelter> narrowed = getShelters(searchString,
                ShelterSearch.getAgeCriteria(), ShelterSearch.getGenderCriteria());
        System.out.println("after: " + narrowed.toString());
        if (!narrowed.isEmpty()) {
            shelters.clear();
            shelters.addAll(narrowed);
        } else if (searchString != null) {
            for (Shelter shelter : shelters) {
                if (shelter.toString().toLowerCase().contains(searchString)) {
                    narrowed.add(shelter);
                }
            }
            shelters.clear();
            shelters.addAll(narrowed);
        }


        /*
        HashSet<Shelter> narrowed = new HashSet<>();
        boolean genderNarrowed = false;
        boolean ageNarrowed = false;
        String searchString = ShelterSearch.getSearchString();
        if (ShelterSearch.getGenderCriteria() != null) {
            genderNarrowed = true;
            for (Gender gender : ShelterSearch.getGenderCriteria()) {
                if (searchString != null) {
                    for (Shelter shelter : gender.getShelters()) {
                        if (shelter.toString().toLowerCase().contains(searchString)) {
                            narrowed.add(shelter);
                        }
                    }
                } else {
                    narrowed.addAll(gender.getShelters());
                }
            }
        }
        if (ShelterSearch.getAgeCriteria() != null) {
            ageNarrowed = true;
            if (!genderNarrowed) {
                for (AgeRanges range : ShelterSearch.getAgeCriteria()) {
                    if (searchString != null) { //redundant if you add condition to below if statement
                        for (Shelter shelter : range.getShelters()) {
                            if (shelter.toString().toLowerCase().contains(searchString)) {
                                narrowed.add(shelter);
                            }
                        }
                    } else {
                        narrowed.addAll(range.getShelters());
                    }
                }
            } else {
                ArrayList<Shelter> temp = new ArrayList<>();
                for (AgeRanges range : ShelterSearch.getAgeCriteria()) {
                    for (Shelter shelter : range.getShelters()) {
                        if (narrowed.contains(shelter)) {
                            temp.add(shelter);
                        }
                    }
                }
                narrowed.clear();
                narrowed.addAll(temp);
            }
        }
        if (genderNarrowed || ageNarrowed) {
            shelters.clear();
            shelters.addAll(narrowed);
        } else if (searchString != null) {
            for (Shelter shelter : shelters) {
                if (shelter.toString().toLowerCase().contains(searchString)) {
                    narrowed.add(shelter);
                }
            }
            shelters.clear();
            shelters.addAll(narrowed);
        }
        */

    }

    private HashSet<Shelter> getShelters(
            String searchString, ArrayList<AgeRanges> ranges, ArrayList<Gender> genders) {
        HashSet<Shelter> shelters = new HashSet<>();
        System.out.println("first -- searchString: " + searchString + "; ranges: " + ranges
                + "; genders: " + genders);
        if (genders == null) return getShelters(searchString, ranges, shelters);
        for (Gender gender : genders) {
            shelters.addAll(addValidSheltersToHashSet(shelters, gender.getShelters(), searchString, ));
        }
        System.out.println("second -- shelters: " + shelters);
        return getShelters(searchString, ranges, shelters);
    }

    private HashSet<Shelter> getShelters(
            String searchString, ArrayList<AgeRanges> ranges, HashSet<Shelter> shelters) {
        System.out.println("third -- searchString: " + searchString + "; ranges: " + ranges
                + "; shelters: " + shelters);
        if (ranges == null) return shelters; // adjust for searchString?
        boolean sameIteration = false;
        for (AgeRanges range : ranges) {
            addValidSheltersToHashSet(shelters, range.getShelters(), searchString, );
        }
        System.out.println("forth -- shelters: " + shelters);
        return shelters;
    }

    //TODO: rename to reflect the searchString thing
    private HashSet<Shelter> addValidSheltersToHashSet(
            HashSet<Shelter> shelterSet, ArrayList<Shelter> shelterList, String searchString,
            boolean sameIteration) {
        System.out.println("fifth -- shelterSet: " + shelterSet + "; shelterList: " + shelterList);
        if (!shelterSet.isEmpty() && !sameIteration) {
            Iterator<Shelter> iterator = shelterSet.iterator();
            while (iterator.hasNext()) {
                Shelter shelter = iterator.next();
                if (!shelterList.contains(shelter)) iterator.remove();
            }
        } else {
            for (Shelter shelter : shelterList) {
                if (searchString == null //the shelterList cannot be null
                        || shelter.toString().toLowerCase().contains(searchString)) {
                    System.out.println("being added: " + shelter);
                    shelterSet.add(shelter);
                }
            }
        }
        System.out.println("shelter set after addValid method: " + shelterSet);
        return shelterSet;
    }

}
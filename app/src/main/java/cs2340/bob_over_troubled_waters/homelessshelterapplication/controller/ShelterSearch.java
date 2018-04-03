package cs2340.bob_over_troubled_waters.homelessshelterapplication.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;

import java.util.ArrayList;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.R;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.Shelter;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.enums.AgeRanges;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.enums.Gender;

public class ShelterSearch extends AppCompatActivity {

    private AutoCompleteTextView searchBar;

    private static final ArrayList<Gender> genderCriteria = new ArrayList<>();
    private static final ArrayList<AgeRanges> ageCriteria = new ArrayList<>();
    private static String searchString = null;

    public static void clearCriteria() {
        genderCriteria.clear();
        ageCriteria.clear();
        searchString = null;
    }

    public static ArrayList<Gender> getGenderCriteria() {
        if (genderCriteria.isEmpty() || genderCriteria.size() == 3) return null;
        return genderCriteria;
    }

    public static ArrayList<AgeRanges> getAgeCriteria() {
        if (ageCriteria.isEmpty()) return null;
        return ageCriteria;
    }

    public static String getSearchString() {
        return searchString;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_search);

        populateAutoComplete();
    }

    public void searchAction(View view) {
        clearCriteria();
        CheckBox maleBox = findViewById(R.id.male_check_box);
        if (maleBox.isChecked()) genderCriteria.add(Gender.MALE);
        CheckBox femaleBox = findViewById(R.id.female_check_box);
        if (femaleBox.isChecked()) genderCriteria.add(Gender.FEMALE);
        CheckBox bothBox = findViewById(R.id.both_check_box);
        if (bothBox.isChecked()) genderCriteria.add(Gender.BOTH);
        CheckBox newbornBox = findViewById(R.id.newborns_check_box);
        if (newbornBox.isChecked()) ageCriteria.add(AgeRanges.FAMILIES_WITH_NEWBORNS);
        CheckBox youngAdultsBox = findViewById(R.id.young_adults_check_box);
        if (youngAdultsBox.isChecked()) ageCriteria.add(AgeRanges.YOUNG_ADULTS);
        CheckBox childrenBox = findViewById(R.id.children_check_box);
        if (childrenBox.isChecked()) ageCriteria.add(AgeRanges.CHILDREN);
        CheckBox anyoneBox = findViewById(R.id.anyone_check_box);
        if (anyoneBox.isChecked()) ageCriteria.add(AgeRanges.ANYONE);
        searchString = searchBar.getText().toString().toLowerCase();
        if (searchString.isEmpty()) searchString = null;
        Intent intent = new Intent(this, ShelterListingActivity.class);
        startActivity(intent);
    }

    public void backButtonAction(View view) {
        finish();
    }

    /**
     * populates the suggestions for the autocomplete
     */
    private void populateAutoComplete() {
        searchBar = findViewById(R.id.shelter_search_field);
        ArrayList<String> shelters = new ArrayList<>();
        for (Shelter shelter : Shelter.getShelters()) shelters.add(shelter.toString());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_dropdown_item_1line, shelters);
        searchBar.setAdapter(adapter);
    }
}

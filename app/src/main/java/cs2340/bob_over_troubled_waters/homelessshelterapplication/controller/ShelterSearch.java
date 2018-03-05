package cs2340.bob_over_troubled_waters.homelessshelterapplication.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.regex.Matcher;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.R;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.enums.AgeRanges;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.enums.Gender;

public class ShelterSearch extends AppCompatActivity {

    private static ArrayList<Gender> genderCriteria = new ArrayList<>();
    private static ArrayList<AgeRanges> ageCriteria = new ArrayList<>();

    public static ArrayList<Gender> getGenderCriteria() {
        if (genderCriteria.isEmpty() || genderCriteria.size() == 3) return null;
        return genderCriteria;
    }

    public static ArrayList<AgeRanges> getAgeCriteria() {
        if (ageCriteria.isEmpty()) return null;
        return ageCriteria;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_search);
    }

    public void searchAction(View view) {
        genderCriteria.clear();
        ageCriteria.clear();
        CheckBox maleBox = (CheckBox) findViewById(R.id.male_check_box);
        if (maleBox.isChecked()) genderCriteria.add(Gender.MALE);
        CheckBox femaleBox = (CheckBox) findViewById(R.id.female_check_box);
        if (femaleBox.isChecked()) genderCriteria.add(Gender.FEMALE);
        CheckBox bothBox = (CheckBox) findViewById(R.id.both_check_box);
        if (bothBox.isChecked()) genderCriteria.add(Gender.BOTH);
        CheckBox newbornBox = (CheckBox) findViewById(R.id.newborns_check_box);
        if (newbornBox.isChecked()) ageCriteria.add(AgeRanges.FAMILIES_WITH_NEWBORNS);
        CheckBox youngAdultsBox = (CheckBox) findViewById(R.id.young_adults_check_box);
        if (youngAdultsBox.isChecked()) ageCriteria.add(AgeRanges.YOUNG_ADULTS);
        CheckBox childrenBox = (CheckBox) findViewById(R.id.children_check_box);
        if (childrenBox.isChecked()) ageCriteria.add(AgeRanges.CHILDREN);
        CheckBox anyoneBox = (CheckBox) findViewById(R.id.anyone_check_box);
        if (anyoneBox.isChecked()) ageCriteria.add(AgeRanges.ANYONE);
        Intent intent = new Intent(this, ShelterListingActivity.class);
        startActivity(intent);
    }

    public void backButtonAction(View view) {
        finish();
    }
}

package cs2340.bob_over_troubled_waters.homelessshelterapplication.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.R;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.User;

public class AdminHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
    }

    public void loadShelterAction(View view) {
    }

    public void logoutButtonAction(View view) {
        User.logout();
        finish();
    }

    public void searchSheltersButtonAction(View view) {
    }
}

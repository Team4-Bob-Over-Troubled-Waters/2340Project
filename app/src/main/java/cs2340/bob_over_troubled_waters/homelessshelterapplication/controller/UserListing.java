package cs2340.bob_over_troubled_waters.homelessshelterapplication.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.R;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.AdminUser;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.User;

public class UserListing extends AppCompatActivity {

    private static ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_listing);

        populateListView();
    }

    /**
     * Exits out of the user listing page after the user hits the back button.
     *
     * @param view the View object fo the User Listing page.
     */
    public void backButtonAction(View view) {
        finish();
    }

    private void populateListView() {

        ListView usersView = findViewById(R.id.user_view);
        users = AdminUser.getUsers();
        ArrayAdapter<User> adapter = new ArrayAdapter<User>(
                this, android.R.layout.simple_list_item_1, users);
        usersView.setAdapter(adapter);
        usersView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getBaseContext(), UserProfile.class);
                intent.putExtra("position", i);
                startActivity(intent);
            }
        });
    }

    /**
     * Fetches and returns the array list of users.
     *
     * @return the users of the application.
     */
    public static ArrayList<User> getUsers() {
        return users;
    }
}

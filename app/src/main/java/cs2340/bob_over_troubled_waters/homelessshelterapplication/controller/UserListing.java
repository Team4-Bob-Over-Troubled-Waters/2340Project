package cs2340.bob_over_troubled_waters.homelessshelterapplication.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.R;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.interfacer.UserLoader;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.AdminUser;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.User;

public class UserListing extends AppCompatActivity {

    private static ArrayList<User> users;
    private static UserListing currentInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_listing);

        if (!UserLoader.usersLoaded()) {
            currentInstance = this;
            UserLoader.setInstance(new UserListPopulater());
        } else {
            populateListView();
        }
    }

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
                // TODO make this go to a user profile page
            }
        });
    }

    private static class UserListPopulater extends UserLoader {

        @Override
        public void onPostExecute(final String errorMessage) {
            if (errorMessage == null) {
                currentInstance.populateListView();
            } else {
                Intent intent = new Intent(currentInstance, ErrorPage.class);
                intent.putExtra("message", errorMessage);
                currentInstance.startActivity(intent);
                currentInstance.finish();
            }
        }
    }
}

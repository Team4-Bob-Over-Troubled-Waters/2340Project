package cs2340.bob_over_troubled_waters.homelessshelterapplication.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.R;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.User;

/**
 * view controller for a user who has been blocked
 */
public class BlockedUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocked_user);
    }

    /**
     * logs out current user
     * @param view current view
     */
    public void logoutButtonAction(View view) {
        User.logout();
        finish();
    }
}

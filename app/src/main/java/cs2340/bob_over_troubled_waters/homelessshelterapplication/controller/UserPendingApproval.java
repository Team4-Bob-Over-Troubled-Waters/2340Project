package cs2340.bob_over_troubled_waters.homelessshelterapplication.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.R;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.User;

public class UserPendingApproval extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pending_approval);
    }

    /**
     * Initiates action to log the user out of the application.
     *
     * @param view the View object of the current page on the user interface.
     */
    public void logoutButtonAction(View view) {
        User.logout();
        finish();
    }
}

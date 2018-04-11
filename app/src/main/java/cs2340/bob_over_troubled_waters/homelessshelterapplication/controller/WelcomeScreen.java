package cs2340.bob_over_troubled_waters.homelessshelterapplication.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.R;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.User;

public class WelcomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        User.logout();
    }

    /**
     * Opens the login page.
     *
     * @param view the View object of the welcome screen.
     */
    public void loginButtonAction(View view) {
        Context context = view.getContext();
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    /**
     * Opens the resister new account page.
     *
     * @param view the View object of the welcome screen.
     */
    public void registerButtonAction(View view) {
        Context context = view.getContext();
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }
}

package cs2340.bob_over_troubled_waters.homelessshelterapplication.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.R;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.User;

public class UserHome extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = User.getCurrentUser();
        setContentView(R.layout.activity_user_home);
        TextView welcomeMessage = findViewById(R.id.welcome_message);
        welcomeMessage.setText(String.format("Welcome %s!", user.getUsersName()));
        if (user.isAdmin()) {
            TextView isAdminView = findViewById(R.id.is_admin_view);
            isAdminView.setText("Admin user");
        }

    }

    public void logoutButtonAction(View view) {
        Intent intent = new Intent(this, WelcomeScreen.class);
        startActivity(intent);
        finish();
    }
}

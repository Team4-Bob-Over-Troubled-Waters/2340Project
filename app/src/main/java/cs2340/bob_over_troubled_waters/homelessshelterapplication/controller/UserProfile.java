package cs2340.bob_over_troubled_waters.homelessshelterapplication.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.R;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.AdminUser;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.ShelterEmployee;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.User;

public class UserProfile extends AppCompatActivity {

    private User user;
    private AdminUser currentUser;

    private Button approveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);

        currentUser = (AdminUser) User.getCurrentUser();

        user = UserListing.getUsers().get(position);

        updateUserInfoView();

        // set the name header
        TextView nameView = findViewById(R.id.name_text_view);
        nameView.setText(user.getUsersName());

        // set the block button status
        ToggleButton toggleBlockedButton = findViewById(R.id.toggle_blocked_button);
        toggleBlockedButton.setChecked(user.getIsBlocked());

        // determine whether to show the approve button
        approveButton = findViewById(R.id.approve_button);
        Class<? extends User> userClass = user.getClass();
        if (userClass.equals(AdminUser.class)) {
            if (!((AdminUser) user).isApproved()) {
                approveButton.setVisibility(View.VISIBLE);
            }
        } else if (userClass.equals(ShelterEmployee.class)) {
            if (!((ShelterEmployee) user).isApproved()) {
                approveButton.setVisibility(View.VISIBLE);
            }
        }
    }

    private void updateUserInfoView() {
        // set the user's info on the view
        ListView userInfo = findViewById(R.id.user_info_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, user.getUserInfo());
        userInfo.setAdapter(adapter);
    }

    public void backButtonAction(View view) {
        finish();
    }

    public void approveButtonAction(View view) {
        if (user instanceof AdminUser) {
            currentUser.approveAdmin((AdminUser) user);
        } else {
            currentUser.approveShelterEmployee((ShelterEmployee) user);
        }
        approveButton.setVisibility(View.GONE);
        updateUserInfoView();
    }

    public void toggleBlockedButtonAction(View view) {
        currentUser.toggleBlockUser(user);
        updateUserInfoView();
    }
}

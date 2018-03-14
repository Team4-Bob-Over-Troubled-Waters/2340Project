package cs2340.bob_over_troubled_waters.homelessshelterapplication.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.R;

public class ErrorPage extends AppCompatActivity {

    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_page);
        message = getIntent().getStringExtra("message");
        if (message != null) {
            TextView messageView = findViewById(R.id.message_view);
            messageView.setText(message);
            messageView.requestFocus();
        }
    }

    public void backButtonAction(View view) {
        finish();
    }
}

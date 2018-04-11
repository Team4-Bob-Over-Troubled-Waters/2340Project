package cs2340.bob_over_troubled_waters.homelessshelterapplication.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.R;

/**
 * page that is shown when an error occurs
 */
public class ErrorPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_page);
        String message = getIntent().getStringExtra("message");
        if (message != null) {
            TextView messageView = findViewById(R.id.message_view);
            messageView.setText(message);
            messageView.requestFocus();
        }
    }

    /**
     * go back to previous view
     * @param view current view
     */
    public void backButtonAction(View view) {
        finish();
    }
}

package cs2340.bob_over_troubled_waters.homelessshelterapplication.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.R;

/**
 * Created by Francine on 3/14/2018.
 * Dialog that appears when user tries to make a reservation but already has one.
 */

public class DoubleReservationDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.double_reservation);
        builder.setPositiveButton(R.string.view_reservation, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getActivity(), ReservationPage.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        return builder.create();
    }
}

package cs2340.bob_over_troubled_waters.homelessshelterapplication.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.R;

/**
 * Created by Francine on 4/2/2018.
 * Dialog that appears when there is a runtime error while making a reservation.
 */

public class ReserveErrorDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.reserve_error);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        return builder.create();
    }
}

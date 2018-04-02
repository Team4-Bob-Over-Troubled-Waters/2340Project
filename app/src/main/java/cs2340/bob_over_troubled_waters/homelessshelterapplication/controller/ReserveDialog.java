package cs2340.bob_over_troubled_waters.homelessshelterapplication.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.TextView;

import cs2340.bob_over_troubled_waters.homelessshelterapplication.R;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.HomelessPerson;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.Reservation;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.Shelter;
import cs2340.bob_over_troubled_waters.homelessshelterapplication.model.User;

public class ReserveDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.reserve_bed);
        builder.setMessage(R.string.reserve_ask_beds);

        final NumberPicker numberPicker = new NumberPicker(getActivity());
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);
        builder.setView(numberPicker);

        builder.setPositiveButton(R.string.reserve, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.out.println("clicked positive button");
                HomelessPerson currentUser = (HomelessPerson) User.getCurrentUser();
                Shelter currentShelter = Shelter.getCurrentShelter();

                int vacancies = currentShelter.getVacancies();
                int numberOfBeds = numberPicker.getValue();
                System.out.println("current shelter: " + currentShelter.getName());
                System.out.println("vacancies: " + vacancies);
                System.out.println("beds: " + numberOfBeds);
                if (numberOfBeds > vacancies) {
                    getFragmentManager().popBackStackImmediate();
                    ReserveFailDialog newFragment = new ReserveFailDialog();
                    newFragment.show(getFragmentManager(), "reserve fail dialog");
                } else {
                    Reservation reservation = new Reservation(numberOfBeds, currentShelter);
                    try {
                        currentShelter.addReservation(reservation);
                        currentUser.setCurrentReservation(reservation);
//                    currentShelter.setVacancies(vacancies - numberOfBeds);
                        TextView vacanciesText = getActivity().findViewById(R.id.text_vacancies);
                        String text = getString(R.string.vacancies) + currentShelter.getVacancies();
                        vacanciesText.setText(text);

                        getFragmentManager().popBackStackImmediate();
                        ReserveSuccessDialog newFragment = new ReserveSuccessDialog();
                        newFragment.show(getFragmentManager(), "reserve success dialog");
                    } catch (Exception e) {
                        System.out.println(e);
                        currentShelter.cancelReservation(reservation);
                        currentUser.setCurrentReservation(null);
                        getFragmentManager().popBackStackImmediate();
                        ReserveErrorDialog newFragment = new ReserveErrorDialog();
                        newFragment.show(getFragmentManager(), "reserve error dialog");
                    }
                }
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        return builder.create();
    }

// --Commented out by Inspection START (3/31/2018 15:35):
//    public NumberPicker.OnValueChangeListener getValueChangeListener() {
//        return valueChangeListener;
//    }
// --Commented out by Inspection STOP (3/31/2018 15:35)

    public void setValueChangeListener(NumberPicker.OnValueChangeListener valueChangeListener) {
    }
}
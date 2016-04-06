package com.example.alex.helppeopletogether;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import java.util.Calendar;

/**
 * Created by Alex on 06.04.2016.
 */
public class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private int year, month, day;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        Dialog picture = new DatePickerDialog(getActivity(),this,year,month, day);
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {

    }
}

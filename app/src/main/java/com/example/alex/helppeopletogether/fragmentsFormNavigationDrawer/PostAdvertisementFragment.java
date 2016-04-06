package com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.app.Dialog;

import com.example.alex.helppeopletogether.R;

import java.util.Calendar;

/**
 * Created by User on 29.03.2016.
 */
public class PostAdvertisementFragment extends DialogFragment implements TextWatcher, DatePickerDialog.OnDateSetListener{
    private EditText secondName;
    private EditText fistName;
    private EditText therdName;
    private AutoCompleteTextView region;
    private AutoCompleteTextView city;
    private Button next;
    private EditText dayOfBirth;
    private int year, month, day;
    static final int DIALOG_ID = 0;
    private ArrayAdapter<String> adapter;
    private Button ready;
    private DatePickerDialog.OnDateSetListener dpickerListener;
    private String[] arrayRegion =
            {"Винницкая область", "Волынская область", "Днепропетровская область",
                    "Донецкая область", "Житомирская область", "Закарпатская область", "Запорожская область",
                    "Ивано-Франковская область", "Киевская область", "Кировоградская область",
                    "Луганская область", "Львовская область", "Николаевская область", "Одесская область",
                    "Полтавская область", "Ровненская область", "Сумская область", "Тернопольская область",
                    "Харьковская область", "Херсонская область", "Хмельницкая область", "Черкасская область",
                    "Черниговская область", "Черновицкая область", "Киев", "Севастополь", "Автономная Республика Крым"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.post_advertisement, container, false);
        secondName = (EditText) root.findViewById(R.id.post_advertisement_second_name);
        fistName = (EditText) root.findViewById(R.id.post_advertisement_fist_name);
        therdName = (EditText) root.findViewById(R.id.post_advertisement_therd_name);
        region = (AutoCompleteTextView) root.findViewById(R.id.post_advertisement_region);
        city = (AutoCompleteTextView) root.findViewById(R.id.post_advertisement_city);
        dayOfBirth = (EditText) root.findViewById(R.id.post_advertisement_date_of_birth);
        next = (Button)root.findViewById(R.id.post_advertisement_next);


        region.addTextChangedListener(this);
        city.addTextChangedListener(this);





        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line,arrayRegion);
        region.setAdapter(adapter);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dateDialog = new PostAdvertisementFragment();
                dateDialog.show(getFragmentManager(), "datePicker");
            }
        });


        return root;
    }




    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        Dialog picture = new DatePickerDialog(getActivity(), this, year, month, day);
        picture.setTitle(getResources().getString(R.string.birth_day));

        return picture;

    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
    dayOfBirth.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        ready = ((AlertDialog) getDialog())
//                .getButton(DialogInterface.BUTTON_POSITIVE);
//        ready.setText(getResources().getString(R.string.ready));
//
//    }

//    public void onClick(View v) {
//        DialogFragment dateDialog = new PostAdvertisementFragment();
//        dateDialog.show(getFragmentManager(), "datePicker");
//    }
}

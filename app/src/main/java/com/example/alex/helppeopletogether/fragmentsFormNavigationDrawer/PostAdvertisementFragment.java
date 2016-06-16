package com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer;


import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;


import android.support.v4.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.helppeopletogether.R;
import com.example.alex.helppeopletogether.registration.Login;
import com.example.alex.helppeopletogether.registration.Registration;
import com.example.alex.helppeopletogether.retrofit.RegistrationResponseFromServer;
import com.example.alex.helppeopletogether.retrofit.Retrofit;

import java.util.Calendar;

import java.util.LinkedHashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by User on 29.03.2016.
 */
public class PostAdvertisementFragment extends Fragment implements TextWatcher, View.OnClickListener, DatePickerDialog.OnDateSetListener {


    private EditText  phoneNumber;
    private AutoCompleteTextView region;
    private AutoCompleteTextView city;
    private TextView dayOfBirth;
    private ArrayAdapter<String> adapter;
    private Button dayOfBirthday, next;
    private Calendar calendar;
    private LinkedHashMap<String, String> advertisementData;
    private int installedYear, installedMonth, installedDay;
    private Integer id;


    private String[] arrayRegion =
            {"Винницкая область", "Волынская область", "Днепропетровская область",
                    "Донецкая область", "Житомирская область", "Закарпатская область", "Запорожская область",
                    "Ивано-Франковская область", "Киевская область", "Кировоградская область",
                    "Луганская область", "Львовская область", "Николаевская область", "Одесская область",
                    "Полтавская область", "Ровненская область", "Сумская область", "Тернопольская область",
                    "Харьковская область", "Херсонская область", "Хмельницкая область", "Черкасская область",
                    "Черниговская область", "Черновицкая область", "Киев", "Севастополь", "Автономная Республика Крым"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.post_advertisement, container, false);
        Login login = new Login();
        Registration registration = new Registration();
        if (login.userId != 0){
            id = login.userId;

        }else{
            id =  registration.responseFromServiseRegistrationId;
        }

        region = (AutoCompleteTextView) root.findViewById(R.id.post_advertisement_region);
        city = (AutoCompleteTextView) root.findViewById(R.id.post_advertisement_city);
        dayOfBirth = (TextView) root.findViewById(R.id.post_advertisement_date_of_birth);
        next = (Button) root.findViewById(R.id.post_advertisement_next);

        phoneNumber = (EditText) root.findViewById(R.id.post_advertisement_phone_number);
        region.addTextChangedListener(this);
        city.addTextChangedListener(this);

        next.setOnClickListener(this);
        dataPicker();


        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, arrayRegion);
        region.setAdapter(adapter);


        return root;
    }

    @Override
    public void onPause() {
        super.onPause();
        region.setText("");
        city.setText("");
        phoneNumber.setText("");
        dayOfBirth.setText("");
    }

    @Override
    public void onResume() {
        super.onResume();
        DatePickerDialog dpd = (DatePickerDialog) getActivity().getFragmentManager().findFragmentByTag("Datepickerdialog");
        if (dpd != null) dpd.setOnDateSetListener(this);
    }

    private void dataPicker() {
        dayOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        PostAdvertisementFragment.this,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setAccentColor(Color.parseColor("#03a9f4"));
                dpd.setThemeDark(true);
                dpd.dismissOnPause(true);
                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
            }

        });
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.post_advertisement_next:
                dateRetrofit();
                break;
        }
    }

    private void dateRetrofit() {
        advertisementData = new LinkedHashMap<>();

        if (region.getText().length() > 0 && city.getText().length() > 0 && phoneNumber.getText().length() > 0 &&
                dayOfBirth.getText().length() > 0) {
            advertisementData.put("region", String.valueOf(region.getText()));
            advertisementData.put("city", String.valueOf(city.getText()));
            advertisementData.put("date", String.valueOf(installedDay));
            advertisementData.put("month", String.valueOf(installedMonth));
            advertisementData.put("year", String.valueOf(installedYear));
            advertisementData.put("phoneNumber", String.valueOf(phoneNumber.getText()));
            advertisementData.put("user_id", String.valueOf(id));

            Retrofit.sendAdvertisementData(advertisementData, new Callback<RegistrationResponseFromServer>() {
                @Override
                public void success(RegistrationResponseFromServer registrationResponseFromServer, Response response) {
                    int answer = registrationResponseFromServer.response_add_user_information;
                    if (answer == 1) {
                        Toast.makeText(getActivity(), "Данные не отпраленны", Toast.LENGTH_LONG).show();
                    } else if (answer == 2) {
                        Toast.makeText(getActivity(), "Это успех детка", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getActivity(), "Data don't sent. Check internet connection", Toast.LENGTH_LONG).show();
                }
            });
            nextActivity();
        } else {

            Toast.makeText(getActivity(), "Не все поля заполнены, заполнети все поля", Toast.LENGTH_LONG).show();
        }
    }

    private void nextActivity() {
        Intent intent = new Intent(getActivity(), DescriptionProblem.class);
        startActivity(intent);
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String birthDay = dayOfMonth + "/" + (++monthOfYear) + "/" + year;
        installedDay = dayOfMonth;
        installedYear = year;
        installedMonth = monthOfYear;
        dayOfBirth.setText(birthDay);
    }
}


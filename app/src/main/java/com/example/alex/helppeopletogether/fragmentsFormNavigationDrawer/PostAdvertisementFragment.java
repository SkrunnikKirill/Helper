package com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.helppeopletogether.R;
import com.example.alex.helppeopletogether.SupportClasses.Constant;
import com.example.alex.helppeopletogether.SupportClasses.FiledTest;
import com.example.alex.helppeopletogether.SupportClasses.GetCurensyYear;
import com.example.alex.helppeopletogether.SupportClasses.IFonts;
import com.example.alex.helppeopletogether.SupportClasses.InternetCheck;
import com.example.alex.helppeopletogether.SupportClasses.Preferences;
import com.example.alex.helppeopletogether.retrofit.RegistrationResponseFromServer;
import com.example.alex.helppeopletogether.retrofit.Retrofit;
import com.vicmikhailau.maskededittext.MaskedEditText;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.LinkedHashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by User on 29.03.2016.
 */
public class PostAdvertisementFragment extends Fragment implements TextWatcher, View.OnClickListener, DatePickerDialog.OnDateSetListener, Constant, IFonts {


    Context context;
    AutoCompleteTextView region, city;
    TextView dayOfBirth, regionText, cityText, dayOfBirthText, phoneNumberText;
    Button next;
   private Preferences preferences;
   private InternetCheck internet;
   private LinearLayout linearLayout;
   private GetCurensyYear year;
   private FiledTest test;
   private MaskedEditText phoneNumber;
   private ArrayAdapter<String> regionAdapter, cityAdapter;
   private Calendar calendar;
   private LinkedHashMap<String, String> advertisementData;
   private int installedYear, installedMonth, installedDay;
   private String id;
   private String[] arrayRegion, arrayCity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.post_advertisement, container, false);
        region = (AutoCompleteTextView) root.findViewById(R.id.post_advertisement_region);
        city = (AutoCompleteTextView) root.findViewById(R.id.post_advertisement_city);
        dayOfBirth = (TextView) root.findViewById(R.id.post_advertisement_date_of_birth);
        regionText = (TextView) root.findViewById(R.id.name_region);
        cityText = (TextView) root.findViewById(R.id.name_city);
        dayOfBirthText = (TextView) root.findViewById(R.id.description_problem_problem_date_text);
        phoneNumberText = (TextView) root.findViewById(R.id.name_number);
        next = (Button) root.findViewById(R.id.post_advertisement_next);
        phoneNumber = (MaskedEditText) root.findViewById(R.id.post_advertisement_phone_number);
        phoneNumber.setSelection(phoneNumber.getText().length());
        region.addTextChangedListener(this);
        city.addTextChangedListener(this);
        linearLayout = (LinearLayout) root.findViewById(R.id.post_advertisemet_relative_layout);
        next.setOnClickListener(this);
        dataPicker();
        setCityRegionAdapter();


        region.setDropDownBackgroundResource(R.color.purple);
        city.setDropDownBackgroundResource(R.color.purple);
        region.setAdapter(regionAdapter);
        city.setAdapter(cityAdapter);

        fonts();
        return root;
    }

    private void setCityRegionAdapter() {
        arrayRegion = getResources().getStringArray(R.array.region);
        arrayCity = getResources().getStringArray(R.array.city);
        regionAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, arrayRegion);
        cityAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, arrayCity);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getActivity();
        preferences = new Preferences(context);
        year = new GetCurensyYear();
        id = preferences.loadText(PREFERENCES_ID);
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
                int nextYear = year.getCurrentYear() - 80;
                int startYear = year.getCurrentYear() - 18;

                DatePickerDialog birthDay = DatePickerDialog.newInstance(
                        PostAdvertisementFragment.this,
                        startYear,
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                birthDay.setAccentColor(Color.parseColor("#fbc02d "));
                birthDay.setThemeDark(true);
                birthDay.dismissOnPause(true);
                birthDay.setYearRange(nextYear, startYear);
                birthDay.show(getActivity().getFragmentManager(), "Datepickerdialog");
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
                dayOfBirth.getText().length() > 0 && phoneNumber.getText().toString().length() > 12) {
            advertisementData.put("region", region.getText().toString());
            advertisementData.put("city", city.getText().toString());
            advertisementData.put("date", String.valueOf(installedDay));
            advertisementData.put("month", String.valueOf(installedMonth));
            advertisementData.put("year", String.valueOf(installedYear));
            advertisementData.put("phoneNumber", phoneNumber.getText().toString());
            advertisementData.put("user_id", id);

            Retrofit.sendAdvertisementData(advertisementData, new Callback<RegistrationResponseFromServer>() {
                @Override
                public void success(RegistrationResponseFromServer registrationResponseFromServer, Response response) {
                    int answer = registrationResponseFromServer.response_add_user_information;
                    if (answer == 1) {
                        Toast toast = Toast.makeText(getActivity(), "Данные не отпраленны", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.NO_GRAVITY, 0, 0);
                        toast.show();
                    } else if (answer == 2) {
                        nextActivity();
                        region.setText("");
                        city.setText("");
                        dayOfBirth.setText("");
                        phoneNumber.setText("");
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    if (error.getCause() instanceof UnknownHostException) {
                        internet = new InternetCheck(context, linearLayout);
                        internet.execute();
                    }
                }
            });

        } else {

            Toast.makeText(getActivity(), R.string.all_filed, Toast.LENGTH_LONG).show();
        }
    }

    private void nextActivity() {
        Intent intent = new Intent(getActivity(), DescriptionProblem.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String birthDay = dayOfMonth + "/" + (++monthOfYear) + "/" + year;
        installedDay = dayOfMonth;
        installedYear = year;
        installedMonth = monthOfYear;
        dayOfBirth.setText(birthDay);
    }


    @Override
    public void fonts() {
        Typeface mtypeface = Typeface.createFromAsset(getActivity().getAssets(), "GothamProMedium.ttf");
        region.setTypeface(mtypeface);
        city.setTypeface(mtypeface);
        phoneNumber.setTypeface(mtypeface);
        dayOfBirth.setTypeface(mtypeface);
        next.setTypeface(mtypeface);
        regionText.setTypeface(mtypeface);
        cityText.setTypeface(mtypeface);
        dayOfBirthText.setTypeface(mtypeface);
        phoneNumberText.setTypeface(mtypeface);


    }
}


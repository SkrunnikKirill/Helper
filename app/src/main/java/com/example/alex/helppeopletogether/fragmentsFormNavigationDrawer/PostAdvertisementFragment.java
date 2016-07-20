package com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.helppeopletogether.R;
import com.example.alex.helppeopletogether.SupportClasses.Constant;
import com.example.alex.helppeopletogether.SupportClasses.FiledTest;
import com.example.alex.helppeopletogether.SupportClasses.GetCurensyYear;
import com.example.alex.helppeopletogether.SupportClasses.InternetCheck;
import com.example.alex.helppeopletogether.SupportClasses.Preferences;
import com.example.alex.helppeopletogether.retrofit.RegistrationResponseFromServer;
import com.example.alex.helppeopletogether.retrofit.Retrofit;
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
public class PostAdvertisementFragment extends Fragment implements TextWatcher, View.OnClickListener, DatePickerDialog.OnDateSetListener, Constant {


    private Preferences preferences;
    private Context context;
    private InternetCheck internet;
    private RelativeLayout relativeLayout;
    private GetCurensyYear year;
    private FiledTest test;
    private EditText phoneNumber;
    private AutoCompleteTextView region;
    private AutoCompleteTextView city;
    private TextView dayOfBirth;
    private ArrayAdapter<String> regionAdapter, cityAdapter;
    private Button next;
    private Calendar calendar;
    private LinkedHashMap<String, String> advertisementData;
    private int installedYear, installedMonth, installedDay;
    private String id;
    private String[] arrayRegion =
            {"Винницкая область", "Волынская область", "Днепропетровская область",
                    "Донецкая область", "Житомирская область", "Закарпатская область", "Запорожская область",
                    "Ивано-Франковская область", "Киевская область", "Кировоградская область",
                    "Луганская область", "Львовская область", "Николаевская область", "Одесская область",
                    "Полтавская область", "Ровненская область", "Сумская область", "Тернопольская область",
                    "Харьковская область", "Херсонская область", "Хмельницкая область", "Черкасская область",
                    "Черниговская область", "Черновицкая область", "Киев", "Севастополь", "Автономная Республика Крым"};

    private String[] arrayCity = {
            "Симферополь", "Евпатория", "Керчь", "Партенит", "Плодовое", "Феодосия",
            "Щёлкино", "Ялта", "Винница", "Балановка", " Ладыжин", "Луцк", "Владимир-Волынский", "Ковель",
            "Нововолынск", "Днепропетровск", "Булаховка", "Днепродзержинск", "Жёлтые Воды", "Зеленодольск",
            "Кривой Рог", "Кринички", "Марганец", "Никополь", "Новомосковск", "Павлоград", "Донецк",
            "Андреевка", "Артёмовск", "Безимянное", "Белосарайская Коса", "Бересток", "Волноваха",
            "Горловка", "Енакиево", "Зугрес", "Константиновка", "Краматорск", "Красноармейск",
            "Курахово", "Макеевка", "Мариуполь", "Николаевка", "Райгородок", "Светлодарск", "Севастополь",
            "Святогорск", "Славянск", "Снежное", "Торез", "Шахтёрск", "Житомир", "Андреевка",
            "Бердичев", "Коростень", "Новоград-Волынский", "Ужгород", "Берегово", "Виноградов", "Иршава",
            "Мукачево", "Рахов", "Свалява", "Тячев", "Хуст", "Запорожье", "Бердянск", "Днепрорудное",
            "Камыш-Заря", "Мелитополь", "Токмак", "Энергодар", "Ивано-Франковск", "Бурштын", "Калуш",
            "КоломыяБелая Церковь", "Борисполь", "Бровары", "Вышгород", "Припять", "Кировоград", "Александрия",
            "Луганск", "Алчевск", "Антрацит", "Белолуцк", "Ирмно", "Краснодон", "Красный Луч",
            "Лисичанск", "Ровеньки", "Рубежное", "Свердловск", "Северодонецк", "Старобельск", "Стаханов", "Счастье",
            "Чернухино", "Львов", "Дрогобыч", "Красное", "Стрый", "Червоноград", "Николаев", "Вознесенск", "Луч",
            "Первомайск", "Южноукраинск", "Одесса", "Белгород-Днестровский", "Жовтень", "Измаил", "Ильичевск",
            "Каменское", "Орловка", "Петровка", "Полтава", "Красногоровка", "Кременчуг", "Лубны", "Ровно", "Антополь",
            "Кузнецовск", "Сумы", "Ахтырка", "Белополье", "Конотоп", "Ромны", "Шостка", "Тернополь", "Харьков",
            "Барвенково", "Змиёв", "Изюм", "Кегичёвка", "Комсомольское", "Мерефа", "Лозовая", "Подворки", "Тарановка", "Херсон",
            "Васильевка", "Геническ", "Большая Александровка", "Новая Каховка", "Новотроицкое", "Рыбальче", "Чаплинка",
            "Хмельницкий", "Волочиск", "Каменец-Подольский", "Нетешин", "Черкассы", "Буки", "Смела", "Умань", "Чернигов", "Козелець", "Крути",
            "Нежин", "Новгород-Северский", "Прилуки", "Черновцы", "Киев"
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.post_advertisement, container, false);
        region = (AutoCompleteTextView) root.findViewById(R.id.post_advertisement_region);
        city = (AutoCompleteTextView) root.findViewById(R.id.post_advertisement_city);
        dayOfBirth = (TextView) root.findViewById(R.id.post_advertisement_date_of_birth);
        next = (Button) root.findViewById(R.id.post_advertisement_next);
        phoneNumber = (EditText) root.findViewById(R.id.post_advertisement_phone_number);
        test = new FiledTest(city, region, phoneNumber);
        test.checkPostAdvertisementData();
        region.addTextChangedListener(this);
        city.addTextChangedListener(this);
        relativeLayout = (RelativeLayout) root.findViewById(R.id.post_advertisemet_relative_layout);
        next.setOnClickListener(this);
        dataPicker();


        regionAdapter = new ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, arrayRegion);
        cityAdapter = new ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, arrayCity);


        region.setDropDownBackgroundResource(R.color.blue);
        city.setDropDownBackgroundResource(R.color.blue);
        region.setAdapter(regionAdapter);
        city.setAdapter(cityAdapter);


        return root;
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
                birthDay.setAccentColor(Color.parseColor("#03a9f4"));
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
                        Toast.makeText(getActivity(), "Данные не отпраленны", Toast.LENGTH_LONG).show();
                    } else if (answer == 2) {
                        nextActivity();
                        region.setText("");
                        city.setText("");
                        phoneNumber.setText("+380");
                        dayOfBirth.setText("");
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    if (error.getCause() instanceof UnknownHostException) {
                        internet = new InternetCheck(context, relativeLayout);
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


}


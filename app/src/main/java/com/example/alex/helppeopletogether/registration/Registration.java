package com.example.alex.helppeopletogether.registration;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alex.helppeopletogether.R;
import com.example.alex.helppeopletogether.retrofit.RegistrationResponseFromServer;
import com.example.alex.helppeopletogether.retrofit.Retrofit;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class Registration extends Activity implements View.OnClickListener {
    private EditText login;
    private EditText email;
    private EditText password;
    private Button buttonRegistration;
    private HashMap<String, String> data;
    private String regularExprensionsEmail;
    private boolean result;
    private String response;
    Intent intent;
    Integer responseFromServiseRegistration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        login = (EditText) findViewById(R.id.registration_login);
        email = (EditText) findViewById(R.id.registration_email);
        password = (EditText) findViewById(R.id.registration_password);
        buttonRegistration = (Button) findViewById(R.id.registration_button_registration);
        buttonRegistration.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registration_button_registration:
                sendRegistrationInformationToServer();
                break;
        }
    }

    public void sendRegistrationInformationToServer() {


        data = new HashMap<>();

        if (login.getText().length() < 6) {
            Toast.makeText(getApplication(), "Логин, должен быть больше 6 символов", Toast.LENGTH_LONG).show();
        } else if (resultRegularExprensionsEmail() == false) {
            Toast.makeText(getApplication(), "Email, некоректный", Toast.LENGTH_LONG).show();
        } else if (password.getText().length() < 6) {
            Toast.makeText(getApplication(), "Пароль, должен быть больше 6 символов", Toast.LENGTH_LONG).show();
        } else {
            data.put("login", String.valueOf(login.getText()));
            data.put("email", String.valueOf(email.getText()));
            data.put("password", String.valueOf(password.getText()));



            Retrofit.sendRegistrationData(data, new Callback<RegistrationResponseFromServer>() {
                @Override
                public void success(RegistrationResponseFromServer registrationResponseFromServer, Response response) {
                    Toast.makeText(getApplication(), "Data sent", Toast.LENGTH_SHORT).show();
                    responseFromServiseRegistration = registrationResponseFromServer.response;

                    if (responseFromServiseRegistration == 3){
                        Toast.makeText(Registration.this,"Логин занят",Toast.LENGTH_LONG).show();
                    }else if (responseFromServiseRegistration ==1){
                        Toast.makeText(Registration.this,"Email занят", Toast.LENGTH_LONG).show();
                    }else if (responseFromServiseRegistration ==2){
                        intent = new Intent(Registration.this, Agreement.class);
                          startActivity(intent);
                        //Toast.makeText(Registration.this,"ВСЕ ОК!!!!", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getApplication(), "Data don't sent. Check internet connection", Toast.LENGTH_LONG).show();
                }
            });

//            Retrofit.sendRegistrationData(data, new Callback<RegistrationResponseFromServer>() {
//                @Override
//                public void success(RegistrationResponseFromServer responseRegistration, Response response)  {
//                    Toast.makeText(getApplication(), "Data sent", Toast.LENGTH_SHORT).show();
//                    responseFromServiseRegistration = responseRegistration.response;
//
//                    if (responseFromServiseRegistration == 3){
//                        Toast.makeText(Registration.this,"Логин занят",Toast.LENGTH_LONG).show();
//                    }else if (responseFromServiseRegistration ==1){
//                        Toast.makeText(Registration.this,"Email занят", Toast.LENGTH_LONG).show();
//                    }else if (responseFromServiseRegistration ==2){
//                        Toast.makeText(Registration.this,"ВСЕ ОК!!!!", Toast.LENGTH_LONG).show();
//                    }
//
//                }
//
//                @Override
//                public void failure(RetrofitError error) {
//                    Toast.makeText(getApplication(), "Data don't sent. Check internet connection", Toast.LENGTH_LONG).show();
//                }
//            });


        }



    }

    private boolean resultRegularExprensionsEmail() {
        regularExprensionsEmail = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"" +
                "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\"" +
                ")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\" +
                "[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?" +
                "|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\" +
                "x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        Pattern pCheckEmail = Pattern.compile(regularExprensionsEmail);
        Matcher mCheckEmail = pCheckEmail.matcher(email.getText().toString());
        result = mCheckEmail.matches();
        return result;
    }


}


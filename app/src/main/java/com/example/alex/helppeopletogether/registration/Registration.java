package com.example.alex.helppeopletogether.registration;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alex.helppeopletogether.R;
import com.example.alex.helppeopletogether.retrofit.Retrofit;

import java.util.LinkedHashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class Registration extends Activity implements View.OnClickListener {
    private EditText login;
    private EditText email;
    private EditText password;
    private Button buttonRegistration;
    private String slogin;
    private String semail;
    private String spassword;
    private LinkedHashMap<String, String> data;
    boolean containsDogSymbol;
    boolean containsPointSymbol;

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
    switch (v.getId()){
        case R.id.registration_button_registration:
            sendRegistrationInformationToServer();
            break;
    }
    }

    public void sendRegistrationInformationToServer() {
        containsDogSymbol = email.getText().toString().contains("@");
        containsPointSymbol = email.getText().toString().contains(".");
        data = new LinkedHashMap<>();
        if (login.getText().length() < 6) {
            Toast.makeText(getApplication(), "Логин, должен быть больше 6 символов", Toast.LENGTH_LONG).show();
        } else if (containsDogSymbol==false){
            Toast.makeText(getApplication(),"Email, должен содержать символ: @", Toast.LENGTH_LONG).show();
        } else  if (containsPointSymbol == false){
            Toast.makeText(getApplication(), "Email, должен сожержать символ: . ",Toast.LENGTH_LONG).show();
        } else if (password.getText().length() < 6){
            Toast.makeText(getApplication(), "Пароль, должен быть больше 6 символов",Toast.LENGTH_LONG).show();
        }else {
            data.put("login", String.valueOf(login.getText()));
            data.put("email", String.valueOf(email.getText()));
            data.put("password", String.valueOf(password.getText()));
            Retrofit.sendRegistrationData(data, new Callback<Void>() {
                @Override
                public void success(Void aVoid, Response response) {
                    Toast.makeText(getApplication(), "Data sent", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getApplication(), "Data don't sent. Check internet connection", Toast.LENGTH_LONG).show();
                }
            });
        }


        }

    

    }


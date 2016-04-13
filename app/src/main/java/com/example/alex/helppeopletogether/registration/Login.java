package com.example.alex.helppeopletogether.registration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alex.helppeopletogether.R;
import com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer.NewsNavigationDrawer;
import com.example.alex.helppeopletogether.retrofit.RegistrationResponseFromServer;
import com.example.alex.helppeopletogether.retrofit.Retrofit;

import java.util.LinkedHashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class Login extends Activity implements View.OnClickListener {
    private EditText login;
    private EditText password;
    private Button buttonNext;
    private Button buttonNextStepRegistration;
    private Intent intentNextStep;
    private LinkedHashMap<String, String> loginData;
    private Integer responseFromServiseLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        login = (EditText) findViewById(R.id.login_login);
        password = (EditText) findViewById(R.id.login_password);
        buttonNext = (Button) findViewById(R.id.login_button_login);
        buttonNextStepRegistration = (Button) findViewById(R.id.login_button_registration);
        buttonNext.setOnClickListener(this);
        buttonNextStepRegistration.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button_login:
                checkData();
                break;
            case R.id.login_button_registration:
                intentNextStep = new Intent(Login.this, Registration.class);
                startActivity(intentNextStep);
                break;
        }
    }

    public void checkData(){
        if(login.getText().length()<6 || password.getText().length()<6){
            Toast.makeText(Login.this,"Не правильно введен логин или пароль",Toast.LENGTH_LONG).show();
        }else{
            loginData = new LinkedHashMap<>();
            loginData.put("login",String.valueOf(login.getText()));
            loginData.put("password",String.valueOf(password.getText()));
            Retrofit.sendLoginData(loginData, new Callback<RegistrationResponseFromServer>() {
                @Override
                public void success(RegistrationResponseFromServer responseLogin, Response response) {
                    Toast.makeText(getApplication(), "Data sent", Toast.LENGTH_SHORT).show();
                    responseFromServiseLogin = responseLogin.response_login;
                    if (responseFromServiseLogin == 1){
                        Toast.makeText(Login.this,"Не правильно введен логин или пароль",Toast.LENGTH_LONG).show();
                    } else if(responseFromServiseLogin == 2){
                        intentNextStep = new Intent(Login.this, NewsNavigationDrawer.class);
                        startActivity(intentNextStep);
                        //Toast.makeText(Login.this,"Это успех!!!",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getApplication(), "Data don't sent. Check internet connection", Toast.LENGTH_LONG).show();
                }
            });

        }
    }
}

package com.example.alex.helppeopletogether.registration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.alex.helppeopletogether.R;


public class Login extends Activity implements View.OnClickListener {
    private EditText login;
    private EditText password;
    private Button buttonNext;
    private Button buttonNextStepRegistration;
    private Intent intentNextStep;

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
                //intentNextStep = new Intent(Login.this, //переход на ленту новостей если все нормально с логином и паролем);
                // startActivity(intentNextStep);
                break;
            case R.id.login_button_registration:
                intentNextStep = new Intent(Login.this, Registration.class);
                startActivity(intentNextStep);
                break;
        }
    }
}

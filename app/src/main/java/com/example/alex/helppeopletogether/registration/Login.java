package com.example.alex.helppeopletogether.registration;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.alex.helppeopletogether.R;
import com.example.alex.helppeopletogether.SupportClasses.IFonts;
import com.example.alex.helppeopletogether.SupportClasses.InternetCheck;
import com.example.alex.helppeopletogether.SupportClasses.MyToast;
import com.example.alex.helppeopletogether.SupportClasses.Preferences;
import com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer.NewsNavigationDrawer;
import com.example.alex.helppeopletogether.retrofit.RegistrationResponseFromServer;
import com.example.alex.helppeopletogether.retrofit.Retrofit;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.vk.sdk.VKScope;

import java.net.UnknownHostException;
import java.util.LinkedHashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.example.alex.helppeopletogether.SupportClasses.Constant.PREFERENCES_FOTO;
import static com.example.alex.helppeopletogether.SupportClasses.Constant.PREFERENCES_ID;
import static com.example.alex.helppeopletogether.SupportClasses.Constant.PREFERENCES_LOGIN;
import static com.example.alex.helppeopletogether.SupportClasses.Constant.PREFERENCES_NAME;
import static com.example.alex.helppeopletogether.SupportClasses.Constant.PREFERENCES_PASSWORD;


public class Login extends AppCompatActivity implements View.OnClickListener, IFonts {
    LoginManager loginManager;
    Context context;
    EditText password, email;
    Button buttonNext, facebook, vk, googlePlus;
    TextView buttonNextStepRegistration;
    RelativeLayout relativeLayoutSnackBar;
    CallbackManager callbackManager;
    private LinkedHashMap<String, String> loginData;
    private Preferences preferences;
    private InternetCheck internetCheck;
    private Integer responseFromServiseLogin, responseFromServiseSocialNetwork, userId;
    private String fullName, UserPhoto;
    private String[] scope = new String[]{VKScope.EMAIL, VKScope.PHOTOS, VKScope.MESSAGES, VKScope.FRIENDS};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //relativeLayoutSnackBar = (RelativeLayout) findViewById(R.id.relativeLayoutLogin);
        email = (EditText) findViewById(R.id.login_email);
        password = (EditText) findViewById(R.id.login_password);
        buttonNext = (Button) findViewById(R.id.login_button_login);
        buttonNext.setOnClickListener(this);
        callbackManager = CallbackManager.Factory.create();
        loginManager = LoginManager.getInstance();
        context = Login.this;
        preferences = new Preferences(Login.this);
        fonts();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setPreferences();
    }


    public void checkInternet() {
        internetCheck = new InternetCheck(Login.this, relativeLayoutSnackBar);
        internetCheck.execute();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }


    @Override
    protected void onStop() {
        super.onStop();
        email.setText("");
        password.setText("");

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button_login:
                checkData();
                break;


        }
    }

    public void setPreferences() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (preferences.loadText(PREFERENCES_PASSWORD) != null || preferences.loadText(PREFERENCES_LOGIN) != null || preferences.loadText(PREFERENCES_ID) != null) {
                        newsFragment();
                        Thread.sleep(2000);
                    }
                } catch (NullPointerException e) {
                    return;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


    public void checkData() {
        Registration loginEmail = new Registration();
        if (loginEmail.resultRegularExprensionsEmail(email) == false || password.getText().length() < 6) {
            MyToast.error(context, context.getString(R.string.not_correct_login_or_password));
        } else if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
            MyToast.error(context, context.getString(R.string.not_correct_login_or_password));
        } else {
            loginData = new LinkedHashMap<>();
            loginData.put("email", email.getText().toString());
            loginData.put("password", password.getText().toString());

            Retrofit.sendLoginData(loginData, new Callback<RegistrationResponseFromServer>() {
                @Override
                public void success(RegistrationResponseFromServer responseLogin, Response response) {

                    if (responseLogin == null) {
                        MyToast.info(context, context.getString(R.string.error_data_from_server));
                    } else {
                        responseFromServiseLogin = responseLogin.response_login;
                        userId = responseLogin.user_id;
                        fullName = responseLogin.full_name;
                        UserPhoto = responseLogin.avatar;
                        if (responseFromServiseLogin == 1) {
                            MyToast.error(context, context.getString(R.string.not_correct_login_or_password));
                        } else if (responseFromServiseLogin == 2) {
                            preferences.saveText(email.getText().toString(), PREFERENCES_LOGIN);
                            preferences.saveText(password.getText().toString(), PREFERENCES_PASSWORD);
                            preferences.saveText(userId.toString(), PREFERENCES_ID);
                            preferences.saveText(fullName, PREFERENCES_NAME);
                            preferences.saveText(UserPhoto, PREFERENCES_FOTO);
                            newsFragment();
                        }
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    if (error.getCause() instanceof UnknownHostException) {
                        checkInternet();
                    }

                }
            });

        }
    }

    private void newsFragment() {
        Intent intent = new Intent(Login.this, NewsNavigationDrawer.class);
        startActivity(intent);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        finish();
    }


    @Override
    public void fonts() {
        Typeface mtypeface = Typeface.createFromAsset(getAssets(), "GothamProMedium.ttf");
        email.setTypeface(mtypeface);
        buttonNext.setTypeface(mtypeface);
        password.setTypeface(mtypeface);
    }
}



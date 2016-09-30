package com.example.alex.helppeopletogether.registration;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.alex.helppeopletogether.R;
import com.example.alex.helppeopletogether.SupportClasses.Preferences;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.vk.sdk.VKScope;


public class Login extends AppCompatActivity {
    private LoginManager loginManager;
    private Context context;
    private Preferences preferences;
    private EditText password, email;
    private Button buttonNext, facebook, vk, googlePlus;
    private TextView buttonNextStepRegistration;


    private CallbackManager callbackManager;
    private String[] scope = new String[]{VKScope.EMAIL, VKScope.PHOTOS, VKScope.MESSAGES, VKScope.FRIENDS};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //  relativeLayoutSnackBar = (RelativeLayout) findViewById(R.id.relativeLayoutLogin);
        email = (EditText) findViewById(R.id.login_email);
        //   facebook = (Button) findViewById(R.id.login_button_facebook);
        //  vk = (Button) findViewById(R.id.login_button_vk);
        //  googlePlus = (Button) findViewById(R.id.login_button_google_plus);
        password = (EditText) findViewById(R.id.login_password);
        buttonNext = (Button) findViewById(R.id.login_button_login);
        //  buttonNextStepRegistration = (TextView) findViewById(R.id.login_view_registration);
//        buttonNext.setOnClickListener(this);
//        facebook.setOnClickListener(this);
//        vk.setOnClickListener(this);
//        googlePlus.setOnClickListener(this);
//        FacebookSdk.sdkInitialize(getApplicationContext());
//        callbackManager = CallbackManager.Factory.create();
//        buttonNextStepRegistration.setOnClickListener(this);
//        loginManager = LoginManager.getInstance();
//        context = getApplicationContext();
//        preferences = new Preferences(Login.this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        //    setPreferences();
    }


    public void checkInternet() {
        //  internetCheck = new InternetCheck(Login.this, relativeLayoutSnackBar);
        //  internetCheck.execute();
    }







    @Override
    protected void onStop() {
        super.onStop();
        email.setText("");
        password.setText("");


    }


//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.login_button_login:
//             //   checkData();
//                break;
//            case R.id.login_view_registration:
//            //    intentNextStep = new Intent(Login.this, Registration.class);
//             //   startActivity(intentNextStep);
//                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
//                break;
//            case R.id.login_button_facebook:
//            //    loginFacebook();
//                break;
//
//            case R.id.login_button_vk:
//                VKSdk.login(this, scope);
//                break;
//
//            case R.id.login_button_google_plus:
//             //   Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
//              //  startActivityForResult(signInIntent, RC_SIGN_IN);
//                break;
//
//        }
//    }


//    public void checkData() {
//        Registration loginEmail = new Registration();
//        if (loginEmail.resultRegularExprensionsEmail(email) == false || password.getText().length() < 6) {
//
//            Toast.makeText(Login.this, R.string.not_correct_login_or_password, Toast.LENGTH_LONG).show();
//        } else if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
//
//            Toast.makeText(Login.this, R.string.not_correct_login_or_password, Toast.LENGTH_SHORT).show();
//        } else {
//            loginData = new LinkedHashMap<>();
//            loginData.put("email", email.getText().toString());
//            loginData.put("password", password.getText().toString());
//
//            Retrofit.sendLoginData(loginData, new Callback<RegistrationResponseFromServer>() {
//                @Override
//                public void success(RegistrationResponseFromServer responseLogin, Response response) {
//
//                    if (responseLogin == null) {
//                        Toast.makeText(Login.this, R.string.error_data_from_server, Toast.LENGTH_LONG).show();
//                    } else {
//                        responseFromServiseLogin = responseLogin.response_login;
//                        userId = responseLogin.user_id;
//                        fullName = responseLogin.full_name;
//                        UserPhoto = responseLogin.avatar;
//                        if (responseFromServiseLogin == 1) {
//                            Toast.makeText(Login.this, R.string.not_correct_login_or_password, Toast.LENGTH_LONG).show();
//                        } else if (responseFromServiseLogin == 2) {
//                            preferences.saveText(email.getText().toString(), PREFERENCES_LOGIN);
//                            preferences.saveText(password.getText().toString(), PREFERENCES_PASSWORD);
//                            preferences.saveText(userId.toString(), PREFERENCES_ID);
//                            preferences.saveText(fullName, PREFERENCES_NAME);
//                            preferences.saveText(UserPhoto, PREFERENCES_FOTO);
//                            newsFragment();
//                        }
//                    }
//                }
//
//                @Override
//                public void failure(RetrofitError error) {
//                    if (error.getCause() instanceof UnknownHostException) {
//                        checkInternet();
//                    }
//
//                }
//            });
//
//        }
//    }




}



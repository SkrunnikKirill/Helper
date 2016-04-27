package com.example.alex.helppeopletogether.registration;


import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.helppeopletogether.R;
import com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer.NewsNavigationDrawer;
import com.example.alex.helppeopletogether.retrofit.RegistrationResponseFromServer;
import com.example.alex.helppeopletogether.retrofit.Retrofit;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.gson.Gson;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.util.VKUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class Login extends AppCompatActivity implements View.OnClickListener {
    String fistName, secondName, id, name, linkUri;
    private EditText login;
    private EditText password;
    private Button buttonNext, facebook, vk;
    private TextView buttonNextStepRegistration;
    private Intent intentNextStep;
    private LinkedHashMap<String, String> loginData;
    private Integer responseFromServiseLogin;
    private CallbackManager callbackManager;
    private FacebookCallback<LoginResult> mCallback;
    private String[] scope = new String[]{VKScope.EMAIL, VKScope.PHOTOS, VKScope.MESSAGES, VKScope.FRIENDS};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.login);
        login = (EditText) findViewById(R.id.login_login);
        facebook = (Button) findViewById(R.id.login_button_facebook);
        vk = (Button) findViewById(R.id.login_button_vk);
        password = (EditText) findViewById(R.id.login_password);
        buttonNext = (Button) findViewById(R.id.login_button_login);
        buttonNextStepRegistration = (TextView) findViewById(R.id.login_view_registration);
        buttonNext.setOnClickListener(this);
        facebook.setOnClickListener(this);
        vk.setOnClickListener(this);
        buttonNextStepRegistration.setOnClickListener(this);
        String[] fingerprints = VKUtil.getCertificateFingerprint(this, this.getPackageName());
        System.out.println(Arrays.toString(fingerprints));



    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button_login:
                checkData();
                break;
            case R.id.login_view_registration:
                intentNextStep = new Intent(Login.this, Registration.class);
                startActivity(intentNextStep);
                break;
            case R.id.login_button_facebook:
                FacebookSdk.sdkInitialize(getApplicationContext());
                callbackManager = CallbackManager.Factory.create();
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends", "login"));
                LoginManager.getInstance().registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                Profile profile = Profile.getCurrentProfile();
                                String user = profile.getFirstName();
                                String user1 = profile.getLastName();
                                String id = profile.getId();
                                String name = profile.getName();


                            }

                            @Override
                            public void onCancel() {

                            }

                            @Override
                            public void onError(FacebookException exception) {

                            }
                        });
                try {
                    PackageInfo info = getPackageManager().getPackageInfo(
                            "com.facebook.samples.loginhowto",
                            PackageManager.GET_SIGNATURES);
                    for (Signature signature : info.signatures) {
                        MessageDigest md = MessageDigest.getInstance("SHA");
                        md.update(signature.toByteArray());
                        Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                    }
                } catch (PackageManager.NameNotFoundException e) {

                } catch (NoSuchAlgorithmException e) {

                }

                break;
            case R.id.login_button_vk:
                VKSdk.login(this, scope);
                break;

        }
    }


    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {

        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                String email = res.email;
                String id = res.userId;


                VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, "first_name,last_name"));
                request.executeSyncWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onError(VKError error) {
                        super.onError(error);
                        Toast.makeText(Login.this, "Все плохо", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);
                        String json = response.responseString;
                        vkjson(json);
                    }

                });
            }

            @Override
            public void onError(VKError error) {
                Toast.makeText(getApplicationContext(), "very very bad", Toast.LENGTH_LONG).show();
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }


    }

    private void vkjson(String json) {
        Gson gson = new Gson();
        VkArrayDates vkArrayDates = gson.fromJson(json, VkArrayDates.class);
        List<VkPersonDates> data = vkArrayDates.getResponse();
        VkPersonDates name = data.get(0);
        String fistName = name.getFirst_name();
        String secondName = name.getLast_name();
        Integer id = name.getId();
    }


    public void checkData() {
        if (login.getText().length() < 6 || password.getText().length() < 6) {
            Toast.makeText(Login.this, "Не правильно введен логин или пароль", Toast.LENGTH_LONG).show();
        } else {
            loginData = new LinkedHashMap<>();
            loginData.put("login", String.valueOf(login.getText()));
            loginData.put("password", String.valueOf(password.getText()));
            Retrofit.sendLoginData(loginData, new Callback<RegistrationResponseFromServer>() {
                @Override
                public void success(RegistrationResponseFromServer responseLogin, Response response) {
                    Toast.makeText(getApplication(), "Data sent", Toast.LENGTH_SHORT).show();
                    responseFromServiseLogin = responseLogin.response_login;
                    if (responseFromServiseLogin == 1) {
                        Toast.makeText(Login.this, "Не правильно введен логин или пароль", Toast.LENGTH_LONG).show();
                    } else if (responseFromServiseLogin == 2) {
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

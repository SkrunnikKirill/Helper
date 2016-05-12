package com.example.alex.helppeopletogether.registration;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.helppeopletogether.R;
import com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer.ExitFragment;
import com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer.NewsFragment;
import com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer.NewsItem;
import com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer.NewsNavigationDrawer;
import com.example.alex.helppeopletogether.retrofit.RegistrationResponseFromServer;
import com.example.alex.helppeopletogether.retrofit.Retrofit;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
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

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class Login extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private static final int RC_SIGN_IN = 0;
    private static final String TAG = "Login";
    private static final int PROFILE_PIC_SIZE = 400;
    LoginManager loginManager;
    Context context;
    private EditText email;
    private EditText password;
    private Button buttonNext, facebook, vk, googlePlus;
    private TextView buttonNextStepRegistration;
    private Intent intentNextStep;
    private LinkedHashMap<String, String> loginData, socialUserData;
    private Integer responseFromServiseLogin, responseFromServiseSocialNetwork;
    private CallbackManager callbackManager;
    private FacebookCallback<LoginResult> mCallback;
    private String[] scope = new String[]{VKScope.EMAIL, VKScope.PHOTOS, VKScope.MESSAGES, VKScope.FRIENDS};
    private String vkEmail, vkId, vkFirstName, vkSecondName, facebookFirstName, facebookSecondName, facebookId, token;
    private GoogleApiClient mGoogleApiClient;
    private boolean mIntentInProgress;
    private boolean mSignInClicked;
    private ConnectionResult mConnectionResult;
    private SignInButton btnSignIn;
    private String facebookSocialName, vkSocialName, googleSocialName, googleFirstName, googleSecondName, googleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.login);
        email = (EditText) findViewById(R.id.login_email);
        facebook = (Button) findViewById(R.id.login_button_facebook);
        vk = (Button) findViewById(R.id.login_button_vk);
        googlePlus = (Button) findViewById(R.id.login_button_google_plus);
        password = (EditText) findViewById(R.id.login_password);
        buttonNext = (Button) findViewById(R.id.login_button_login);
        buttonNextStepRegistration = (TextView) findViewById(R.id.login_view_registration);
        buttonNext.setOnClickListener(this);
        facebook.setOnClickListener(this);
        vk.setOnClickListener(this);
        googlePlus.setOnClickListener(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        buttonNextStepRegistration.setOnClickListener(this);
        String[] fingerprints = VKUtil.getCertificateFingerprint(this, this.getPackageName());
        System.out.println(Arrays.toString(fingerprints));
        loginGoogle();
        loginManager = LoginManager.getInstance();
        context = getApplicationContext();

    }

    private void loginGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestId()
                .requestProfile()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }


    @Override
    protected void onStop() {
        super.onStop();

        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
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
                loginFacebook();
                break;

            case R.id.login_button_vk:
                VKSdk.login(this, scope);
                break;

            case R.id.login_button_google_plus:
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);

                break;

        }
    }

    private void loginFacebook() {
        loginManager.logInWithReadPermissions(this, Arrays.asList(new String[]{"public_profile", "user_friends"}));
        loginManager.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override

                    public void onSuccess(LoginResult loginResult) {
                        //  String ddddd = loginResult.getAccessToken().getUserId();
                        Profile profile = Profile.getCurrentProfile();
                        facebookFirstName = profile.getFirstName();
                        facebookSecondName = profile.getLastName();
                        facebookId = profile.getId();


                        facebookSocialName = "Facebook";
                        socialNetworksRegistration(facebookFirstName, facebookSecondName, facebookSocialName, facebookId);

                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(Login.this, "у вас, не получилось авторизироваться через Facebook, попробуйте еще раз"
                                , Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        if (exception instanceof FacebookAuthorizationException) {
                            if (AccessToken.getCurrentAccessToken() != null) {
                                LoginManager.getInstance().logOut();
                            }
                        }
                    }

                });
    }

    public void socialNetworksRegistration(String firstName, String secondName, String nameSocial, String idSocial) {
        socialUserData = new LinkedHashMap<>();
        socialUserData.put("first_name", firstName);
        socialUserData.put("second_name", secondName);
        socialUserData.put("social_network", nameSocial);
        socialUserData.put("social_id", String.valueOf(idSocial));
        Retrofit.sendSocialNetworks(socialUserData, new Callback<RegistrationResponseFromServer>() {
            @Override
            public void success(RegistrationResponseFromServer registrationResponseFromServer, Response response) {
                responseFromServiseSocialNetwork = registrationResponseFromServer.response;
                if (responseFromServiseSocialNetwork == 1) {
                    newsFragment();

                } else if (responseFromServiseSocialNetwork == 2) {
                    intentNextStep = new Intent(Login.this, Agreement.class);
                    startActivity(intentNextStep);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(Login.this, "Данные пользователя соц сети не отправились", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void newsFragment() {
        Intent intent = new Intent(Login.this, NewsNavigationDrawer.class);
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                vkEmail = res.email;
                vkId = res.userId;


                VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, "first_name,last_name", "photo_id,"));
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
                        vkSocialName = "Vk";
                        socialNetworksRegistration(vkFirstName, vkSecondName, vkSocialName, vkId);
                    }

                });
            }

            @Override
            public void onError(VKError error) {
                Toast.makeText(getApplicationContext(), "у вас, не получилось авторизироваться через VK, попробуйте еще раз", Toast.LENGTH_LONG).show();
            }
        }))

        {
            super.onActivityResult(requestCode, resultCode, data);
        }


    }

    private void vkjson(String json) {
        Gson gson = new Gson();
        VkArrayDates vkArrayDates = gson.fromJson(json, VkArrayDates.class);
        List<VkPersonDates> data = vkArrayDates.getResponse();
        VkPersonDates name = data.get(0);
        vkFirstName = name.getFirst_name();
        vkSecondName = name.getLast_name();
    }


    public void checkData() {
        Registration loginEmail = new Registration();
        if (loginEmail.resultRegularExprensionsEmail(email) == false || password.getText().length() < 6) {
            Toast.makeText(Login.this, "Не правильно введен логин или пароль", Toast.LENGTH_LONG).show();
        } else {
            loginData = new LinkedHashMap<>();
            loginData.put("email", String.valueOf(email.getText()));
            loginData.put("password", String.valueOf(password.getText()));
            Retrofit.sendLoginData(loginData, new Callback<RegistrationResponseFromServer>() {
                @Override
                public void success(RegistrationResponseFromServer responseLogin, Response response) {
                    Toast.makeText(getApplication(), "Data sent", Toast.LENGTH_SHORT).show();
                    responseFromServiseLogin = responseLogin.response_login;
                    if (responseFromServiseLogin == 1) {
                        Toast.makeText(Login.this, "Не правильно введен email или пароль", Toast.LENGTH_LONG).show();
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


    private void handleSignInResult(GoogleSignInResult result) {
//        result.getSignInAccount().getEmail();
        //      result.getSignInAccount().getId();
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            String email = acct.getEmail();
            googleId = acct.getId();
            String name = acct.getDisplayName();
            if (name != null) {
                String[] arrayName = name.split(" ");
                googleFirstName = arrayName[0];
                googleSecondName = arrayName[1];
            }
            googleSocialName = "Google";
            socialNetworksRegistration(googleFirstName, googleSecondName, googleSocialName, googleId);
        } else {
            Toast.makeText(Login.this, "Интернет соеденение отсутствует", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // !!!
                connectionResult.startResolutionForResult(this, ConnectionResult.DEVELOPER_ERROR);
            } catch (IntentSender.SendIntentException e) {
                mGoogleApiClient.connect();
            }
        }

        mConnectionResult = connectionResult;

    }
}


package com.example.alex.helppeopletogether.registration;


import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.helppeopletogether.R;
import com.example.alex.helppeopletogether.SupportClasses.ConstantPreferences;
import com.example.alex.helppeopletogether.SupportClasses.InternetCheck;
import com.example.alex.helppeopletogether.SupportClasses.Preferences;
import com.example.alex.helppeopletogether.Vk.VkArrayDates;
import com.example.alex.helppeopletogether.Vk.VkPersonDates;
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
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
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
import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKList;
import com.vk.sdk.util.VKUtil;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class Login extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener, ConstantPreferences {

    private static final int RC_SIGN_IN = 0;
    private static final String TAG = "Login";
    public static Integer userId;
    public static String UserPhoto;
    public static String fullName;
    LoginManager loginManager;
    Context context;
    Profile profile;
    InternetCheck internetCheck;
    Preferences preferences;
    private EditText email;
    private EditText password;
    private Button buttonNext, facebook, vk, googlePlus;
    private TextView buttonNextStepRegistration;
    private Intent intentNextStep;
    private LinkedHashMap<String, String> loginData, socialUserData;
    private Integer responseFromServiseLogin, responseFromServiseSocialNetwork;
    private CallbackManager callbackManager;
    private String[] scope = new String[]{VKScope.EMAIL, VKScope.PHOTOS, VKScope.MESSAGES, VKScope.FRIENDS};
    private String vkEmail, vkId, vkFirstName, vkSecondName, facebookFirstName, facebookSecondName, facebookId;
    private GoogleApiClient mGoogleApiClient;
    private ConnectionResult mConnectionResult;
    private String facebookSocialName, vkSocialName, googleSocialName, googleFirstName, googleSecondName, googleId;
    private ProfileTracker profileTracker;
    private RelativeLayout relativeLayoutSnackBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        relativeLayoutSnackBar = (RelativeLayout) findViewById(R.id.relativeLayoutLogin);
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
        preferences = new Preferences(Login.this);



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
    protected void onPause() {
        super.onPause();
        email.setText("");
        password.setText("");
    }

    @Override
    protected void onRestart() {
        super.onRestart();


    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        email.setText("");
        password.setText("");
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
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
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
                        if (Profile.getCurrentProfile() == null) {
                            profileTracker = new ProfileTracker() {
                                @Override
                                protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                                    facebookFirstName = currentProfile.getFirstName();
                                    facebookSecondName = currentProfile.getLastName();
                                    facebookId = currentProfile.getId();
                                    UserPhoto = String.valueOf(currentProfile.getProfilePictureUri(150, 150));
                                    profileTracker.stopTracking();
                                    facebookSocialName = getString(R.string.facebook);
                                    socialNetworksRegistration(facebookFirstName, facebookSecondName, facebookSocialName, facebookId, UserPhoto);
                                }
                            };
                        } else {
                            profile = Profile.getCurrentProfile();
                            facebookFirstName = profile.getFirstName();
                            UserPhoto = String.valueOf(profile.getProfilePictureUri(150, 150));
                            facebookSecondName = profile.getLastName();
                            facebookId = profile.getId();
                            facebookSocialName = getString(R.string.facebook);
                            socialNetworksRegistration(facebookFirstName, facebookSecondName, facebookSocialName, facebookId, UserPhoto);
                        }


                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(Login.this, R.string.do_not_registration_from_facebook
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

    public void socialNetworksRegistration(String firstName, String secondName, String nameSocial, String idSocial, String userFoto) {
        socialUserData = new LinkedHashMap<>();
        socialUserData.put("first_name", firstName);
        socialUserData.put("second_name", secondName);
        socialUserData.put("social_network", nameSocial);
        socialUserData.put("social_id", String.valueOf(idSocial));
        socialUserData.put("avatar", userFoto);
        Retrofit.sendSocialNetworks(socialUserData, new Callback<RegistrationResponseFromServer>() {
            @Override
            public void success(RegistrationResponseFromServer registrationResponseFromServer, Response response) {
                if (registrationResponseFromServer == null) {
                    Toast.makeText(Login.this, R.string.error_data_from_server, Toast.LENGTH_LONG).show();
                } else {
                    responseFromServiseSocialNetwork = registrationResponseFromServer.response;
                    userId = registrationResponseFromServer.user_id;
                    fullName = registrationResponseFromServer.full_name;
                    UserPhoto = registrationResponseFromServer.avatar;


                    if (responseFromServiseSocialNetwork == 1) {
                        preferences.saveText(userId.toString(), PREFERENCES_ID);
                        preferences.saveText(fullName, PREFERENCES_NAME);
                        preferences.saveText(UserPhoto, PREFERENCES_FOTO);
                        newsFragment();

                    } else if (responseFromServiseSocialNetwork == 2) {
                        intentNextStep = new Intent(Login.this, Agreement.class);
                        startActivity(intentNextStep);
                        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
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

    private void newsFragment() {
        Intent intent = new Intent(Login.this, NewsNavigationDrawer.class);
        startActivity(intent);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                vkEmail = res.email;
                vkId = res.userId;


                VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, VKApiConst.PHOTO, "first_name,last_name", "photo_id"));
                request.executeSyncWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onError(VKError error) {
                        super.onError(error);
                    }

                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);
                        VKList<VKApiUserFull> list = (VKList<VKApiUserFull>) response.parsedModel;
                        String json = response.responseString;
                        vkjson(json);
                        vkSocialName = "Vk";
                        socialNetworksRegistration(vkFirstName, vkSecondName, vkSocialName, vkId, UserPhoto);
                    }

                });
            }

            @Override
            public void onError(VKError error) {

                Toast.makeText(getApplicationContext(), R.string.do_not_registration_from_vk, Toast.LENGTH_LONG).show();
            }
        }))

        {
            callbackManager.onActivityResult(requestCode, resultCode, data);
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
        UserPhoto = name.getPhoto();


    }


    public void checkData() {
        Registration loginEmail = new Registration();
        if (loginEmail.resultRegularExprensionsEmail(email) == false || password.getText().length() < 6) {

            Toast.makeText(Login.this, R.string.not_correct_login_or_password, Toast.LENGTH_LONG).show();
        } else if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {

            Toast.makeText(Login.this, R.string.not_correct_login_or_password, Toast.LENGTH_SHORT).show();
        } else {
            loginData = new LinkedHashMap<>();
            loginData.put("email", email.getText().toString());
            loginData.put("password", password.getText().toString());

            Retrofit.sendLoginData(loginData, new Callback<RegistrationResponseFromServer>() {
                @Override
                public void success(RegistrationResponseFromServer responseLogin, Response response) {

                    if (responseLogin == null) {
                        Toast.makeText(Login.this, R.string.error_data_from_server, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplication(), "Data sent", Toast.LENGTH_SHORT).show();
                        responseFromServiseLogin = responseLogin.response_login;
                        userId = responseLogin.user_id;
                        fullName = responseLogin.full_name;
                        UserPhoto = responseLogin.avatar;
                        if (responseFromServiseLogin == 1) {
                            Toast.makeText(Login.this, R.string.not_correct_login_or_password, Toast.LENGTH_LONG).show();
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


    private void handleSignInResult(GoogleSignInResult result) {

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

            UserPhoto = "foto";
            googleSocialName = "Google";
            socialNetworksRegistration(googleFirstName, googleSecondName, googleSocialName, googleId, UserPhoto);
        } else {
            Toast.makeText(Login.this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            return true;

        }
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        return false;
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


}



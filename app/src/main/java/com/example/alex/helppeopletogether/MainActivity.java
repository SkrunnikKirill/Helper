package com.example.alex.helppeopletogether;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.helppeopletogether.SupportClasses.Constant;
import com.example.alex.helppeopletogether.SupportClasses.IFonts;
import com.example.alex.helppeopletogether.SupportClasses.InternetCheck;
import com.example.alex.helppeopletogether.SupportClasses.MyToast;
import com.example.alex.helppeopletogether.SupportClasses.Preferences;
import com.example.alex.helppeopletogether.Vk.VkArrayDates;
import com.example.alex.helppeopletogether.Vk.VkPersonDates;
import com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer.NewsNavigationDrawer;
import com.example.alex.helppeopletogether.registration.Login;
import com.example.alex.helppeopletogether.registration.Registration;
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
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
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

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener, Constant, IFonts {
    Person currentUser;
    Button signIn, signUp;
    ImageButton vk, facebook, googlePlus;
    CallbackManager callbackManager;
    Preferences preferences;
    Context context;
    TextView socialText;
    LoginManager loginManager;
    RelativeLayout relativeLayoutSnackBar;
    GoogleApiClient mGoogleApiClient;
    ProfileTracker profileTracker;
    Profile profile;
    ConnectionResult mConnectionResult;
    private String facebookSocialName, vkSocialName, googleSocialName, googleFirstName, googleSecondName, googleId, fullName,
            UserPhoto, vkEmail, vkId, vkFirstName, vkSecondName, facebookFirstName, facebookSecondName, facebookId;
    private InternetCheck internetCheck;
    private LinkedHashMap<String, String> socialUserData;
    private Integer responseFromServiseLogin, responseFromServiseSocialNetwork, userId;
    private Intent intentNextStep;
    private String[] scope = new String[]{VKScope.EMAIL, VKScope.PHOTOS, VKScope.MESSAGES, VKScope.FRIENDS};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signIn = (Button) findViewById(R.id.sign_in_button);
        signUp = (Button) findViewById(R.id.sign_up_button);
        socialText = (TextView) findViewById(R.id.text_sign_in);
        vk = (ImageButton) findViewById(R.id.button_vk);
        facebook = (ImageButton) findViewById(R.id.button_facebook);
        googlePlus = (ImageButton) findViewById(R.id.button_google_plus);
        context = getApplicationContext();
        loginGoogle();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        loginManager = LoginManager.getInstance();
        preferences = new Preferences(MainActivity.this);
        callbackManager = CallbackManager.Factory.create();
        signIn.setOnClickListener(this);
        signUp.setOnClickListener(this);
        vk.setOnClickListener(this);
        facebook.setOnClickListener(this);
        googlePlus.setOnClickListener(this);
        fonts();


    }

    public void fonts() {
        Typeface mtypeface = Typeface.createFromAsset(getAssets(), "GothamProMedium.ttf");
        socialText.setTypeface(mtypeface);
        signIn.setTypeface(mtypeface);
        signUp.setTypeface(mtypeface);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                        Toast.makeText(MainActivity.this, R.string.do_not_registration_from_vk, Toast.LENGTH_LONG).show();
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


            }
        })) {
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

    @Override
    protected void onResume() {
        super.onResume();
        setPreferences();
    }


    public void checkInternet() {
        internetCheck = new InternetCheck(MainActivity.this, relativeLayoutSnackBar);
        internetCheck.execute();
    }


    private void loginGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.PLUS_LOGIN))
                .requestProfile()
                .requestEmail()
                .requestId()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(MainActivity.this)
                .enableAutoManage(MainActivity.this /* FragmentActivity */, MainActivity.this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(Plus.API)
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
                                    facebookSocialName = getResources().getString(R.string.facebook);
                                    socialNetworksRegistration(facebookFirstName, facebookSecondName, facebookSocialName, facebookId, UserPhoto);
                                }
                            };
                        } else {
                            profile = Profile.getCurrentProfile();
                            facebookFirstName = profile.getFirstName();
                            UserPhoto = String.valueOf(profile.getProfilePictureUri(150, 150));
                            facebookSecondName = profile.getLastName();
                            facebookId = profile.getId();
                            facebookSocialName = getResources().getString(R.string.facebook);
                            socialNetworksRegistration(facebookFirstName, facebookSecondName, facebookSocialName, facebookId, UserPhoto);
                        }


                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(MainActivity.this, R.string.do_not_registration_from_facebook
                                , Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        if (exception instanceof FacebookAuthorizationException) {
                            if (AccessToken.getCurrentAccessToken() != null) {
                                LoginManager.getInstance().logOut();

                            }
                            Log.d("Exception", exception.getLocalizedMessage());
                            MyToast.info(context, context.getString(R.string.check_internet_connection));
                        }
                    }

                });
    }

    private void newsFragment() {
        Intent intent = new Intent(MainActivity.this, NewsNavigationDrawer.class);
        startActivity(intent);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        finish();
    }

    public void socialNetworksRegistration(String firstName, String secondName, String nameSocial, String idSocial, String userFoto) {
        socialUserData = new LinkedHashMap<>();
        socialUserData.put("first_name", firstName);
        socialUserData.put("second_name", secondName);
        socialUserData.put("social_network", nameSocial);
        socialUserData.put("social_id", idSocial.toString());
        socialUserData.put("avatar", userFoto);
        Retrofit.sendSocialNetworks(socialUserData, new Callback<RegistrationResponseFromServer>() {
            @Override
            public void success(RegistrationResponseFromServer registrationResponseFromServer, Response response) {
                if (registrationResponseFromServer == null) {
                    Toast.makeText(MainActivity.this, R.string.error_data_from_server, Toast.LENGTH_LONG).show();
                } else {
                    responseFromServiseSocialNetwork = registrationResponseFromServer.response;
                    userId = registrationResponseFromServer.user_id;
                    fullName = registrationResponseFromServer.full_name;
                    UserPhoto = registrationResponseFromServer.avatar;
                }

                if (responseFromServiseSocialNetwork == 1) {
                    preferences.saveText(userId.toString(), PREFERENCES_ID);
                    preferences.saveText(fullName, PREFERENCES_NAME);
                    preferences.saveText(UserPhoto, PREFERENCES_FOTO);
                    newsFragment();

                } else if (responseFromServiseSocialNetwork == 2) {
                    preferences.saveText(userId.toString(), PREFERENCES_ID);
                    preferences.saveText(fullName, PREFERENCES_NAME);
                    preferences.saveText(UserPhoto, PREFERENCES_FOTO);
                    intentNextStep = new Intent(MainActivity.this, NewsNavigationDrawer.class);
                    startActivity(intentNextStep);
                    overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                }

            }

            @Override
            public void failure(RetrofitError error) {
                String p = error.getCause().toString();
                if (error.getCause() instanceof UnknownHostException) {
                    checkInternet();
                }

            }
        });
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            Person currentUser = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
            String personPhotoUrl = currentUser.getImage().getUrl();
            String email = acct.getEmail();
            googleId = acct.getId();
            String name = acct.getDisplayName();
            if (name != null) {
                String[] arrayName = name.split(" ");
                googleFirstName = arrayName[0];
                googleSecondName = arrayName[1];
            }
            UserPhoto = personPhotoUrl;
            googleSocialName = "Google";
            socialNetworksRegistration(googleFirstName, googleSecondName, googleSocialName, googleId, UserPhoto);
        } else {
            MyToast.info(context, context.getString(R.string.check_internet_connection));
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                Intent loginIntent = new Intent(MainActivity.this, Login.class);
                startActivity(loginIntent);
                break;
            case R.id.sign_up_button:
                Intent registrationIntent = new Intent(MainActivity.this, Registration.class);
                startActivity(registrationIntent);
                break;

            case R.id.button_vk:
                VKSdk.login(this, scope);
                break;

            case R.id.button_facebook:


                loginFacebook();
                break;

            case R.id.button_google_plus:
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
                break;
        }
    }
}
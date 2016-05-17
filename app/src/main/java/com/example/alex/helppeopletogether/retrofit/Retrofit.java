package com.example.alex.helppeopletogether.retrofit;

import android.widget.ImageView;

import com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer.DescriptionProblem;

import java.util.ArrayList;
import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.Field;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.GET;
import retrofit.http.Part;
import retrofit.http.PartMap;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import retrofit.mime.TypedFile;

/**
 * Created by Alex on 30.03.2016.
 */
public class Retrofit {
    private static final String ENDPOINT = "http://testpb.alscon-clients.com";
    private static PostInterfaceRegistration postInterfaceRegistration;
    private static PostInterfaceSocialNetworks postInterfaceSocialNetworks;
    private static PostInterfaceLogin postInterfaceLogin;
    private static PostInterfaseAdvertisement postInterfaseAdvertisement;
    private static PostInterfaceNextPostAdvertisementFragment postInterfaceNextPostAdvertisementFragment;
    private static PostInterfaceDescriptionProblem postInterfaceDescriptionProblem;

    static {
        init();
    }

    private static void init() {
        RestAdapter postAdapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        postInterfaceRegistration =postAdapter.create(PostInterfaceRegistration.class);
        postInterfaceLogin = postAdapter.create(PostInterfaceLogin.class);
        postInterfaseAdvertisement = postAdapter.create(PostInterfaseAdvertisement.class);
        postInterfaceNextPostAdvertisementFragment = postAdapter.create(PostInterfaceNextPostAdvertisementFragment.class);
        postInterfaceSocialNetworks = postAdapter.create(PostInterfaceSocialNetworks.class);
        postInterfaceDescriptionProblem = postAdapter.create(PostInterfaceDescriptionProblem.class);
    }

    public static void sendSocialNetworks(Map<String, String> social, Callback<RegistrationResponseFromServer> callback) {
        postInterfaceSocialNetworks.sendSocialNetworks(social, callback);
    }

    public static void sendRegistrationData(Map<String, String> datas, TypedFile file, Callback<RegistrationResponseFromServer> callback) {
        postInterfaceRegistration.sendRegistrationData(datas, file, callback);
      }

    public static void sendLoginData(Map<String, String> loginDatas, Callback<RegistrationResponseFromServer> callback){
        postInterfaceLogin.sendLoginData(loginDatas, callback);
    }

    public static void sendAdvertisementData(Map<String, String> advertisementDatas, Callback<RegistrationResponseFromServer> callback) {
        postInterfaseAdvertisement.sendAdvertisementData(advertisementDatas, callback);
    }

    public static void sendNextPostImage(Map<String, TypedFile> imageData, Callback<RegistrationResponseFromServer> callback) {
        postInterfaceNextPostAdvertisementFragment.sendNextPostImage(imageData, callback);
    }
    public static void sendAdvertisement(Map<String, String> dataAdvertisement, TypedFile file, Callback<RegistrationResponseFromServer> callback){
        postInterfaceDescriptionProblem.sendAdvertisement(dataAdvertisement, file, callback);
    }

    interface PostInterfaceNextPostAdvertisementFragment {
        @Multipart
        @POST("/upload_files.php")
        void sendNextPostImage(@PartMap Map<String, TypedFile> imageData, Callback<RegistrationResponseFromServer> callback);
    }

    interface PostInterfaceSocialNetworks {
        @POST("/auth_social.php")
        void sendSocialNetworks(@QueryMap Map<String, String> social, Callback<RegistrationResponseFromServer> callback);
    }

    interface PostInterfaceDescriptionProblem{
        @Multipart
        @POST("/add_adver.php")
        void sendAdvertisement(@QueryMap Map<String, String> dataAdvertisement, @Part("imageAdvertisement") TypedFile file, Callback<RegistrationResponseFromServer> callback);

    }

    interface PostInterfaceRegistration {
        // @FormUrlEncoded
        @Multipart
        @POST("/registration.php")
        void sendRegistrationData(@QueryMap Map<String, String> datas, @Part("imageFile") TypedFile file, Callback<RegistrationResponseFromServer> callback);
    }

    interface PostInterfaceLogin{

        @POST("/login.php")
        void sendLoginData(@QueryMap Map<String, String> loginDatas, Callback<RegistrationResponseFromServer> callback);
    }

    interface PostInterfaseAdvertisement {
        @POST("/add_user_information.php")
        void sendAdvertisementData(@QueryMap Map<String, String> advertisementDatas, Callback<RegistrationResponseFromServer> callback);
    }
}

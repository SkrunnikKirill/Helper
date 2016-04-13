package com.example.alex.helppeopletogether.retrofit;

import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.GET;
import retrofit.http.PartMap;
import retrofit.http.Query;
import retrofit.http.QueryMap;

/**
 * Created by Alex on 30.03.2016.
 */
public class Retrofit {
    private static final String ENDPOINT = "http://testpb.alscon-clients.com";
    private static PostInterfaceRegistration postInterfaceRegistration;
    private static PostInterfaceLogin postInterfaceLogin;
    private static PostInterfaseAdvertisement postInterfaseAdvertisement;

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
    }

      public static void sendRegistrationData(Map<String, String> datas, Callback<RegistrationResponseFromServer> callback){
          postInterfaceRegistration.sendRegistrationData(datas, callback);
      }

    public static void sendLoginData(Map<String, String> loginDatas, Callback<RegistrationResponseFromServer> callback){
        postInterfaceLogin.sendLoginData(loginDatas, callback);
    }

    public static void sendAdvertisementData(Map<String, String> advertisementDatas, Callback<RegistrationResponseFromServer> callback) {
        postInterfaseAdvertisement.sendAdvertisementData(advertisementDatas, callback);
    }



    interface PostInterfaceRegistration {
       // @FormUrlEncoded
        @POST("/registration.php")
         void sendRegistrationData(@QueryMap Map<String, String> datas, Callback<RegistrationResponseFromServer> callback);
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

package com.example.alex.helppeopletogether.retrofit;

import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by Alex on 30.03.2016.
 */
public class Retrofit {
    private static final String ENDPOINT = "http://testpb.alscon-clients.com";
    private static PostInterface postInterface;
    static {
        init();
    }

    private static void init() {
        RestAdapter postAdapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .build();
        postInterface =postAdapter.create(PostInterface.class);
    }

      public static void sendRegistrationData(Map<String, String> datas, Callback<Void> callback){
          postInterface.sendRegistrationData(datas, callback);
      }

    interface PostInterface {
        @FormUrlEncoded
        @POST("/login.php")
         void sendRegistrationData(@FieldMap Map<String, String> datas, Callback<Void> callback);
    }
}

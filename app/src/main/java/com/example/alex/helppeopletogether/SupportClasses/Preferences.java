package com.example.alex.helppeopletogether.SupportClasses;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by PM on 04.07.2016.
 */
public class Preferences {
    SharedPreferences sharedPreferences;
    Context context;


    public Preferences(Context context) {
        this.context = context;

    }

    public void saveText(String userId, String constant) {
        sharedPreferences = context.getSharedPreferences(constant, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString(constant, userId);
        ed.commit();

    }

    public String loadText(String constant) {
        String savedText = null;
        sharedPreferences = context.getSharedPreferences(constant, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(constant)) {
            savedText = sharedPreferences.getString(constant, "");

        }


        return savedText;
    }

    public void remove(String constant) {
        sharedPreferences = context.getSharedPreferences(constant, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
    }
}

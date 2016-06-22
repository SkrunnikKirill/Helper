package com.example.alex.helppeopletogether.SupportClasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Map;
import java.util.Set;

/**
 * Class for Shared Preference
 */
public class PrefManager {
    SharedPreferences prefManager = new SharedPreferences() {
        @Override
        public Map<String, ?> getAll() {
            return null;
        }

        @Nullable
        @Override
        public String getString(String s, String s1) {
            return null;
        }

        @Nullable
        @Override
        public Set<String> getStringSet(String s, Set<String> set) {
            return null;
        }

        @Override
        public int getInt(String s, int i) {
            return 0;
        }

        @Override
        public long getLong(String s, long l) {
            return 0;
        }

        @Override
        public float getFloat(String s, float v) {
            return 0;
        }

        @Override
        public boolean getBoolean(String s, boolean b) {
            return false;
        }

        @Override
        public boolean contains(String s) {
            return false;
        }

        @Override
        public Editor edit() {
            return null;
        }

        @Override
        public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {

        }

        @Override
        public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {

        }
    };
    Context context;

    public PrefManager(Context context) {
        this.context = context;


    }
//    static SharedPreferences getSharedPreferences(Context ctx) {
//        return PrefManager.getSharedPreferences(ctx);}


    public void saveLoginDetails(String AuthUserValue, String email, String password, String SsocialName, String id) {
        SharedPreferences SaveActiveStatus = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = SaveActiveStatus.edit();
        editor.putString("AuthUserValue", AuthUserValue);
        editor.putString("Email", email);
        editor.putString("Password", password);
        editor.putString("SsocialName", SsocialName);
        editor.putString("id", id);
        editor.commit();
    }

    public void saveLoginDetailsGoogle(String email, String password, String SsocialName, String id) {
        SharedPreferences SaveActiveStatus = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = SaveActiveStatus.edit();
        editor.putString("Email", email);
        editor.putString("Password", password);
        editor.putString("SsocialName", SsocialName);
        editor.putString("id", id);
        editor.commit();
    }

    public void saveFoto(String FotoProfile) {
        SharedPreferences SaveActiveStatus = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = SaveActiveStatus.edit();

        editor.putString("Foto", FotoProfile);

        editor.commit();
    }

    public String getAuthUserValue() {
        SharedPreferences SaveActiveStatus = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String AuthValue = SaveActiveStatus.getString("AuthUserValue", "");
        return AuthValue;

    }

    public String FotoProfile() {
        SharedPreferences SaveActiveStatus = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String FotoProfile = SaveActiveStatus.getString("Foto", "");
        return FotoProfile;

    }

    public String getid() {
        SharedPreferences SaveActiveStatus = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String id = SaveActiveStatus.getString("id", "");
        return id;

    }


    public String getSsocialName() {
        SharedPreferences SaveActiveStatus = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String SsocialName = SaveActiveStatus.getString("SsocialName", "");
        return SsocialName;

    }

    public String getEmail() {
        SharedPreferences SaveActiveStatus = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return SaveActiveStatus.getString("Email", "");

    }

    public String getPass() {
        SharedPreferences SaveActiveStatus = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return SaveActiveStatus.getString("Password", "");


    }

    public boolean isUserLogedOut() {
        SharedPreferences SaveActiveStatus = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        boolean isAuthUserValueEmpty = SaveActiveStatus.getString("AuthUserValue", "").isEmpty();
        boolean isEmailEmpty = SaveActiveStatus.getString("Email", "").isEmpty();
        boolean isPasswordEmpty = SaveActiveStatus.getString("Password", "").isEmpty();
        return isEmailEmpty || isPasswordEmpty || isAuthUserValueEmpty;
    }
}

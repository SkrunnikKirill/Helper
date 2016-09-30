package com.example.alex.helppeopletogether.SupportClasses;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.alex.helppeopletogether.R;

/**
 * Created by Alscon on 19.09.2016.
 */
public class MyToast {

    private static LayoutInflater mInflater;
    private static android.widget.Toast mToast;
    private static View mView;

    public static void ok(Context context, String msg) {
        ok(context, msg, android.widget.Toast.LENGTH_SHORT);
    }

    public static void ok(Context context, String msg, int duration) {
        mInflater = LayoutInflater.from(context);
        mView = mInflater.inflate(R.layout.toast_ok, null);
        initSetButtonMsg(msg);
        mToast = new android.widget.Toast(context);
        mToast.setView(mView);
        mToast.setDuration(duration);
        mToast.show();
    }


    public static void error(Context context, String msg) {
        error(context, msg, android.widget.Toast.LENGTH_SHORT);
    }


    public static void error(Context context, String msg, int duration) {
        mInflater = LayoutInflater.from(context);
        mView = mInflater.inflate(R.layout.toast_error, null);
        initSetButtonMsg(msg);
        mToast = new android.widget.Toast(context);
        mToast.setView(mView);
        mToast.setDuration(duration);
        mToast.show();
    }


    public static void info(Context context, String msg) {
        info(context, msg, android.widget.Toast.LENGTH_SHORT);
    }

    public static void info(Context context, String msg, int duration) {
        mInflater = LayoutInflater.from(context);
        mView = mInflater.inflate(R.layout.toast_info, null);
        initSetButtonMsg(msg);
        mToast = new android.widget.Toast(context);
        mToast.setView(mView);
        mToast.setDuration(duration);
        mToast.show();
    }


    private static Button initSetButtonMsg(String msg) {
        Button mButton = (Button) mView.findViewById(R.id.button);
        mButton.setText(msg);
        return mButton;
    }

    public static void CancelCurrentToast() {
        if (mToast != null)
            mToast.cancel();
    }
}

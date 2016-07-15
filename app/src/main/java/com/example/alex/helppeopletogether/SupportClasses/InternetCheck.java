package com.example.alex.helppeopletogether.SupportClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.example.alex.helppeopletogether.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by PM on 22.06.2016.
 */
public class InternetCheck extends AsyncTask<String, String, Boolean> {
    Context context;
    View view;
    ProgressDialog nDialog;


    public InternetCheck(Context context, View view) {
        this.context = context;
        this.view = view;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        nDialog = new ProgressDialog(context);
        nDialog.setTitle(context.getString(R.string.check_internet_connection));
        nDialog.setMessage(context.getString(R.string.please_wait));
        nDialog.setIndeterminate(true);
        nDialog.setCancelable(false);
        nDialog.show();
    }

    @Override
    protected Boolean doInBackground(String... args) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            try {
                URL url = new URL("https://www.google.ru/");
                HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                urlc.setConnectTimeout(3000);
                urlc.connect();
                if (urlc.getResponseCode() == 200) {
                    return true;
                }
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean th) {
        if (th == true) {
            nDialog.dismiss();
            //вызов активити или метода
        } else {
            nDialog.dismiss();
            snackBar();
            //loginErrorMsg.setText("Связь с интернетом отсутствует");
        }
    }

    public void snackBar() {
        try {
            Snackbar snackbar = Snackbar.make(view, R.string.no_internet, Snackbar.LENGTH_SHORT);
            // Changing message text color
            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(context.getResources().getColor(R.color.blue));
            textView.setTextSize(27);
            snackbar.show();
        } catch (NullPointerException e) {
            return;
        }
    }
}



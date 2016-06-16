package com.example.alex.helppeopletogether.SupportClasses;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.alex.helppeopletogether.R;

/**
 * Created by Alex on 14.06.2016.
 */
public class ProDialog {
    ProgressDialog progressDialog;

    public void defenitionProgressBar(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Загрузка...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }

    public void connectionProgressBar() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }


}

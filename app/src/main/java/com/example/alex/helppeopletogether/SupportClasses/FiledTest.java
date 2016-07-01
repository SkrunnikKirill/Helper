package com.example.alex.helppeopletogether.SupportClasses;


import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by PM on 30.06.2016.
 */
public class FiledTest {
    EditText theme, shortDescription, fullDescription, money, account;
    TextView day;

    public FiledTest(EditText theme, EditText shortDescription, EditText fullDescription, EditText money, EditText account, TextView day) {
        this.theme = theme;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.money = money;
        this.account = account;
        this.day = day;
    }

    public void inspection1() {
        theme.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (theme.getText().toString().isEmpty()) {
                    theme.setError("обязательные");
                } else if (theme.getText().toString().length() > 50) {
                    theme.setError("количество символов");
                }
            }
        });

        shortDescription.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (shortDescription.getText().toString().isEmpty()) {
                    shortDescription.setError("обязательные");
                } else if (shortDescription.getText().toString().length() > 100) {
                    shortDescription.setError("количество символов");
                }
            }
        });

        fullDescription.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (fullDescription.getText().toString().isEmpty()) {
                    fullDescription.setError("обязательные");
                } else if (fullDescription.getText().toString().length() > 1000) {
                    fullDescription.setError("количество символов");
                }
            }
        });

        money.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (money.getText().toString().isEmpty()) {
                    money.setError("обязательные");
                }
            }
        });

        account.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (account.getText().toString().isEmpty()) {
                    account.setError("обязательные");
                    b = false;
                } else if (account.getText().toString().length() > 20) {
                    account.setError("количество символов");
                    b = false;
                }
            }
        });
    }

    public boolean inspection() {

        if (theme.getText().toString().isEmpty()) {
            theme.setError("обязательные");
            return false;
        }
        if (shortDescription.getText().toString().isEmpty()) {
            shortDescription.setError("обязательные");
            return false;
        }
        if (fullDescription.getText().toString().isEmpty()) {
            fullDescription.setError("обязательные");
            return false;

        }
        if (money.getText().toString().isEmpty()) {
            money.setError("обязательные");
            return false;

        }
        if (account.getText().toString().isEmpty()) {
            account.setError("обязательные");
            return false;

        }
        if (theme.getText().toString().length() > 50) {
            theme.setError("количество символов");
            return false;
        }
        if (shortDescription.getText().length() > 100) {
            shortDescription.setError("количество символов");
            return false;
        }
        if (fullDescription.getText().length() > 1000) {
            fullDescription.setError("количество символов");
            return false;
        }
        if (account.getText().length() > 20) {
            account.setError("количество символов");
            return false;
        }
        return true;


    }

    public void registeView(EditText text) {
        text.setError("обязательные");

    }


}

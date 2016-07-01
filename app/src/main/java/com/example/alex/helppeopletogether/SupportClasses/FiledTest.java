package com.example.alex.helppeopletogether.SupportClasses;


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

    public boolean inspection() {

        if (theme.getText().toString().isEmpty()) {
            Validation.isAll(theme, false);
            registeView(theme);
            return false;
        }
        if (shortDescription.getText().toString().isEmpty()) {
            registeView(shortDescription);
            return false;
        }
        if (fullDescription.getText().toString().isEmpty()) {
            registeView(fullDescription);
            return false;

        }
        if (money.getText().toString().isEmpty()) {
            registeView(money);
            return false;

        }
        if (account.getText().toString().isEmpty()) {
            registeView(account);
            return false;

        }
        if (theme.getText().toString().length() > 50) {

            return false;
        }
        if (shortDescription.getText().length() > 100) {

            return false;
        }
        if (fullDescription.getText().length() > 1000) {

            return false;
        }
        if (account.getText().length() > 20) {

            return false;
        }
        return true;


    }

    public void registeView(EditText text) {
        Validation.isAll(text, true);

    }


}

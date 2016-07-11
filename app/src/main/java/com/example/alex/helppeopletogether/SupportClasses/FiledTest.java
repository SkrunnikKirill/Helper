package com.example.alex.helppeopletogether.SupportClasses;


import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;


public class FiledTest {
    EditText theme, shortDescription, fullDescription, money, account;
    Button edit;


    public FiledTest(EditText theme, EditText shortDescription, EditText fullDescription, EditText money, EditText account, Button edit) {
        this.theme = theme;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.money = money;
        this.account = account;
        this.edit = edit;

    }

    public void inspection1() {
        theme.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                check(theme, 50);

            }
        });
        shortDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                check(shortDescription, 100);

            }
        });

        fullDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                check(fullDescription, 1000);

            }
        });

        money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (money.getText().toString().isEmpty()) {
                    money.setError("обязательные");
                }
            }
        });

        account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                check(account, 20);
            }
        });


    }

    public void check(EditText editText, int number) {
        final String text = editText.getText().toString().trim();
        if (text.length() == 0) {
            editText.setError("обязательные");
            edit.setEnabled(false);
        } else if (text.length() > number) {
            editText.setError("количество символов");
            edit.setEnabled(false);
        } else {
            editText.setError(null);
            edit.setEnabled(true);
        }
    }


}

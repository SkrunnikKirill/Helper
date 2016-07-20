package com.example.alex.helppeopletogether.SupportClasses;


import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;


public class FiledTest {
    EditText theme, shortDescription, fullDescription, money, account, firstName, secondName, email, password, city, region, phoneNumber;


    public FiledTest(EditText theme, EditText shortDescription, EditText fullDescription, EditText money, EditText account) {
        this.theme = theme;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.money = money;
        this.account = account;


    }

    public FiledTest(EditText firstName, EditText secondName, EditText email, EditText password) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.password = password;
    }

    public FiledTest(EditText city, EditText region, EditText phoneNumber) {
        this.city = city;
        this.region = region;
        this.phoneNumber = phoneNumber;
    }

    public void checkPostAdvertisementData() {
        city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                check(city);
            }
        });

        region.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                check(region);
            }
        });

        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                final String text = phoneNumber.getText().toString().trim();
                if (text.length() == 0) {
                    phoneNumber.setError("обязательные");
                } else if (text.length() < 13) {
                    phoneNumber.setError("не коректный номер");
                } else {
                    phoneNumber.setError(null);
                }
            }
        });
    }

    public void checkRegistrationData() {
        email.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Validation.isEmailAddress(email, true);
            }

        });

        firstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                check(firstName);
            }
        });

        secondName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                check(secondName);
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                final String text = password.getText().toString().trim();
                if (text.length() == 0) {
                    password.setError("обязательное");
                } else if (text.length() < 6) {
                    password.setError("больше 6 символов");
                } else {
                    password.setError(null);
                }
            }
        });
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
                check(fullDescription, 1500);

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
                check(money);
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

                final String text = account.getText().toString().trim();
                if (text.length() == 0) {
                    account.setError("обязательные");
                } else if (text.length() < 20) {
                    account.setError("введите 20 символов");
                } else {
                    account.setError(null);
                }
            }
        });


    }

    public void check(EditText editText, int number) {
        final String text = editText.getText().toString().trim();
        if (text.length() == 0) {
            editText.setError("обязательные");
        } else if (text.length() == number) {
            editText.setError("максимальное количество символов");
        } else {
            editText.setError(null);
        }
    }

    public void check(EditText editText) {
        final String text = editText.getText().toString().trim();
        if (text.length() == 0) {
            editText.setError("обязательные");
        } else {
            editText.setError(null);
        }
    }




}

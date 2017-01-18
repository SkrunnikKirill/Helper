package com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.helppeopletogether.R;
import com.example.alex.helppeopletogether.SupportClasses.Constant;
import com.example.alex.helppeopletogether.SupportClasses.CustomImageView;
import com.example.alex.helppeopletogether.SupportClasses.FiledTest;
import com.example.alex.helppeopletogether.SupportClasses.GetCurensyYear;
import com.example.alex.helppeopletogether.SupportClasses.IFonts;
import com.example.alex.helppeopletogether.SupportClasses.InternetCheck;
import com.example.alex.helppeopletogether.SupportClasses.Preferences;
import com.example.alex.helppeopletogether.retrofit.RegistrationResponseFromServer;
import com.example.alex.helppeopletogether.retrofit.Retrofit;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.LinkedHashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

/**
 * Created by Alex on 13.04.2016.
 */
public class DescriptionProblem extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, Constant, IFonts {


    LinearLayout linearLayout, back;
    TextView day, themeText, shortDescriptionText, fullDescriptionText, moneyText, accountText, dayText, fotoText, textToolbar;
    EditText theme, shortDescription, fullDescription, money, account;
    Button locate;
    CustomImageView imageAdvertisement;
    TypedFile image;
    private Uri selectedImageUri;
    private String selectedImagePath, userid, currency;
    private String[] nameCurrency = {"USD", "EUR", "UAH"};
    private int total_images[] = {R.drawable.dollar, R.drawable.evro, R.drawable.hrivna};
    private GetCurensyYear year;
    private NewsFragment news;
    private DatePickerDialog datePickerDialog;
    private Preferences preferences;
    private FiledTest filedTest;
    private LinkedHashMap<String, String> dataAdvertisement;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description_problem);
        linearLayout = (LinearLayout) findViewById(R.id.description_problem_linear_layout);
        news = new NewsFragment();
        year = new GetCurensyYear();
        preferences = new Preferences(DescriptionProblem.this);
        userid = preferences.loadText(PREFERENCES_ID);
        imageAdvertisement = (CustomImageView) findViewById(R.id.description_problem_image);
        theme = (EditText) findViewById(R.id.description_problem_theme);
        back = (LinearLayout) findViewById(R.id.description_problem_layoutBack);
        textToolbar = (TextView) findViewById(R.id.description_problem_toolbar_text);
        shortDescription = (EditText) findViewById(R.id.description_problem_short_description);
        themeText = (TextView) findViewById(R.id.description_problem_thema_text);
        shortDescriptionText = (TextView) findViewById(R.id.description_problem_short_description_text);
        fullDescriptionText = (TextView) findViewById(R.id.description_problem_full_description_text);
        moneyText = (TextView) findViewById(R.id.description_problem_money_text);
        accountText = (TextView) findViewById(R.id.description_problem_account_text);
        dayText = (TextView) findViewById(R.id.description_problem_problem_date_text);
        fotoText = (TextView) findViewById(R.id.description_problem_name_photo);
        fullDescription = (EditText) findViewById(R.id.description_problem_full_description);
        money = (EditText) findViewById(R.id.description_problem_money);
        day = (TextView) findViewById(R.id.description_problem_day);
        account = (EditText) findViewById(R.id.description_problem_account);
        locate = (Button) findViewById(R.id.description_problem_locate);
        imageAdvertisement.setOnClickListener(this);
        locate.setOnClickListener(this);
        back.setOnClickListener(this);
        dataPicker();
        spiner();
        fonts();
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    private void spiner() {
        Spinner spinner = (Spinner) findViewById(R.id.description_problem_spinerstate);
        spinner.setAdapter(new MyAdapter(this, R.layout.custom_spinner, nameCurrency));
        spinner.setPrompt("Title");
        // выделяем элемент
        spinner.setSelection(2);
        // устанавливаем обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // показываем позиция нажатого элемента
                currency = nameCurrency[position];
                if (currency == "USD") {
                    currency = "$";
                } else if (currency == "EUR") {
                    currency = "€";
                } else if (currency == "UAH") {
                    currency = "₴";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    private void dataPicker() {
        day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                datePickerDialog = DatePickerDialog.newInstance(
                        DescriptionProblem.this,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.setMinDate(calendar);
                datePickerDialog.setAccentColor(Color.parseColor("#483a49"));
                datePickerDialog.setThemeDark(true);
                datePickerDialog.dismissOnPause(true);
                int startYear = year.getCurrentYear();
                int nextYear = year.getCurrentYear() + 5;
                datePickerDialog.setYearRange(startYear, nextYear);
                datePickerDialog.show(getFragmentManager(), "Datepickerdialog");
            }
        });
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("theme", theme.getText().toString());
        outState.putString("shortDescription", shortDescription.getText().toString());
        outState.putString("fullDescription", fullDescription.getText().toString());
        outState.putString("money", money.getText().toString());
        outState.putString("day", day.getText().toString());
        outState.putString("account", account.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        theme.setText(savedInstanceState.getString("theme"));
        shortDescription.setText(savedInstanceState.getString("shortDescription"));
        fullDescription.setText(savedInstanceState.getString("fullDescription"));
        money.setText(savedInstanceState.getString("money"));
        day.setText(savedInstanceState.getString("day"));
        account.setText(savedInstanceState.getString("account"));
    }

    @Override
    protected void onStop() {
        super.onStop();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.description_problem_locate:
                sendDescriptionInformationFromServise();
                break;
            case R.id.description_problem_image:
                CropImage.startPickImageActivity(this);
                break;
            case R.id.description_problem_layoutBack:
                finish();
                break;
        }
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // handle result of pick image chooser
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                selectedImageUri = data.getData();
                file = new File(getPath(selectedImageUri));
                image = new TypedFile("image/*", file);
            } else {
                selectedImageUri = getPickImageResultUri(data);
                file = new File(selectedImageUri.getPath());
                image = new TypedFile("image/*", file);
            }
            Uri imageUri = CropImage.getPickImageResultUri(this, data);
            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                imageUri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            } else {
                // no permissions required or already grunted, can start crop image activity

                startCropImageActivity(imageUri);
            }
        }

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageAdvertisement.setImageURI(result.getUri());

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (selectedImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // required permissions granted, start crop image activity
            startCropImageActivity(selectedImageUri);
        } else {
            Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Start crop image activity for the given image.
     */
    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }


    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s = cursor.getString(column_index);
        cursor.close();
        return s;
    }

    private void sendDescriptionInformationFromServise() {
        if (theme.getText().toString().length() > 0 && shortDescription.getText().toString().length() > 0 && fullDescription.getText().toString().length() > 0 &&
                money.getText().toString().length() > 0 && day.getText().toString().length() > 0 && account.getText().toString().length() > 0
                && theme.getText().toString().length() <= 50 && shortDescription.getText().toString().length() <= 100 && fullDescription.getText().toString().length() <= 1500
                && account.getText().toString().length() <= 20) {
            dataAdvertisement = new LinkedHashMap<>();
            dataAdvertisement.put("title", String.valueOf(theme.getText()));
            dataAdvertisement.put("short_description", String.valueOf(shortDescription.getText()));
            dataAdvertisement.put("description", String.valueOf(fullDescription.getText()));
            dataAdvertisement.put("expected_amount", String.valueOf(money.getText() + currency));
            dataAdvertisement.put("final_date", String.valueOf(day.getText()));
            dataAdvertisement.put("payment_account", String.valueOf(account.getText()));
            dataAdvertisement.put("user_id", userid);


            Retrofit.sendAdvertisement(dataAdvertisement, image, new Callback<RegistrationResponseFromServer>() {
                @Override
                public void success(RegistrationResponseFromServer registrationResponseFromServer, Response response) {
                    Intent intent = new Intent(DescriptionProblem.this, NewsNavigationDrawer.class);
                    startActivityForResult(intent, 1);
                    overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                }

                @Override
                public void failure(RetrofitError error) {
                    if (error.getCause() instanceof UnknownHostException) {
                        checkInternet();
                    }
                }
            });
        } else {
            Toast.makeText(DescriptionProblem.this, R.string.all_field_and_photo, Toast.LENGTH_LONG).show();
        }

    }

    public void checkInternet() {
        InternetCheck internetCheck = new InternetCheck(DescriptionProblem.this, linearLayout);
        internetCheck.execute();
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String finalDay = dayOfMonth + "/" + (++monthOfYear) + "/" + year;

        day.setText(finalDay);
    }

    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null && data.getData() != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        return isCamera ? getCaptureImageOutputUri() : data.getData();
    }

    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "pickImageResult.jpeg"));
        }
        return outputFileUri;
    }

    @Override
    public void fonts() {
        Typeface mtypeface = Typeface.createFromAsset(getAssets(), "GothamProMedium.ttf");
        day.setTypeface(mtypeface);
        theme.setTypeface(mtypeface);
        account.setTypeface(mtypeface);
        fullDescription.setTypeface(mtypeface);
        shortDescription.setTypeface(mtypeface);
        money.setTypeface(mtypeface);
        account.setTypeface(mtypeface);
        locate.setTypeface(mtypeface);
        themeText.setTypeface(mtypeface);
        fotoText.setTypeface(mtypeface);
        shortDescriptionText.setTypeface(mtypeface);
        fullDescriptionText.setTypeface(mtypeface);
        accountText.setTypeface(mtypeface);
        moneyText.setTypeface(mtypeface);
        dayText.setTypeface(mtypeface);
        textToolbar.setTypeface(mtypeface);

    }

    public class MyAdapter extends ArrayAdapter<String> {
        public MyAdapter(Context ctx, int txtViewResourceId, String[] objects) {
            super(ctx, txtViewResourceId, objects);
        }

        @Override
        public View getView(int pos, View cnvtView, ViewGroup prnt) {
            return getCustomView(pos, cnvtView, prnt);
        }

        @Override
        public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
            return getCustomView(position, cnvtView, prnt);
        }


        public View getCustomView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View mySpinner = inflater.inflate(R.layout.custom_spinner, parent, false);
            ImageView left_icon = (ImageView) mySpinner.findViewById(R.id.left_pic);
            left_icon.setImageResource(total_images[position]);
            return mySpinner;
        }
    }
}

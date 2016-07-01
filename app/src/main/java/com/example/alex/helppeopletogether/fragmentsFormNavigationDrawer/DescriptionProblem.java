package com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.helppeopletogether.R;
import com.example.alex.helppeopletogether.registration.Login;
import com.example.alex.helppeopletogether.registration.Registration;
import com.example.alex.helppeopletogether.retrofit.RegistrationResponseFromServer;
import com.example.alex.helppeopletogether.retrofit.Retrofit;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedHashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

/**
 * Created by Alex on 13.04.2016.
 */
public class DescriptionProblem extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    static final int GALLERY_REQUEST_IMAGE = 1;
    int DIALOG_DATE = 1;
    ImageView imageAdvertisement;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    Uri selectedImageUri;
    String selectedImagePath;
    Registration registration;
    Login login;
    String userid;
    NewsFragment news;
    Spinner mySpinner;
    String[] spinnerValues = {"UAH", "EUR", "USD"};
    int total_images[] = {R.drawable.google, R.drawable.facebook, R.drawable.google, R.drawable.facebook, R.drawable.google};
    String spin;
    private TextView day;
    private EditText theme, shortDescription, fullDescription, money, account;
    private Button locate;
    private LinkedHashMap<String, String> dataAdvertisement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description_problem);
        news = new NewsFragment();
        imageAdvertisement = (ImageView) findViewById(R.id.description_problem_image);
        theme = (EditText) findViewById(R.id.description_problem_theme);
        shortDescription = (EditText) findViewById(R.id.description_problem_short_description);
        fullDescription = (EditText) findViewById(R.id.description_problem_full_description);
        money = (EditText) findViewById(R.id.description_problem_money);
        day = (TextView) findViewById(R.id.description_problem_day);
        account = (EditText) findViewById(R.id.description_problem_account);
        locate = (Button) findViewById(R.id.description_problem_locate);
        registration = new Registration();
        imageAdvertisement.setOnClickListener(this);
        locate.setOnClickListener(this);
        mySpinner = (Spinner) findViewById(R.id.spinnerstate);
        mySpinner.setAdapter(new MyAdapter(this, R.layout.custom_spinner, spinnerValues));
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spin = spinnerValues[position];
                if (spin == "USD") {
                    spin = "$";
                } else if (spin == "EUR") {
                    spin = "€";
                } else if (spin == "UAH") {
                    spin = "₴";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        login = new Login();
        if (login.userId != null) {
            userid = String.valueOf(login.userId);
        } else if (registration.responseFromServiseRegistrationId != null) {
            userid = String.valueOf(registration.responseFromServiseRegistrationId);
        }
        dataPicker();
        Toolbar toolbar = (Toolbar) findViewById(R.id.description_problem_toolbar);
        toolbar.setTitle("Информация Обьявления");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_36dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    private void dataPicker() {
        day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.setMinimalDaysInFirstWeek(2016);
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                        DescriptionProblem.this,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.setAccentColor(Color.parseColor("#03a9f4"));
                datePickerDialog.setThemeDark(true);
                datePickerDialog.dismissOnPause(true);
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
                selectImage();
                break;
        }
    }

    public void selectImage() {
        final CharSequence[] items = {"Сделать фотографию", "Выбрать фотографию",
                "Отмена"};
        AlertDialog.Builder builder = new AlertDialog.Builder(DescriptionProblem.this);
        builder.setTitle("Добавить фотографию");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Сделать фотографию")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Выбрать фотографию")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Отмена")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data, imageAdvertisement);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data, imageAdvertisement);
        }
    }

    public void onCaptureImageResult(Intent data, ImageView image) {
        selectedImageUri = data.getData();
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        image.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    public void onSelectFromGalleryResult(Intent data, ImageView image) {
        selectedImageUri = data.getData();
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        selectedImagePath = cursor.getString(column_index);
        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);

        image.setImageBitmap(bm);
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
        if (account.getText().toString().length() < 20 || account.getText().toString().length() > 20) {
            Toast.makeText(DescriptionProblem.this, "Расчетный счет должен состоять из 20 символов", Toast.LENGTH_SHORT).show();
        } else if (theme.getText().toString().length() > 50 || shortDescription.getText().toString().length() > 100 || fullDescription.getText().toString().length() > 1000) {
            Toast.makeText(DescriptionProblem.this, "Превышено максимальное количество символов", Toast.LENGTH_SHORT).show();
        } else if (theme.getText().toString().length() > 0 && shortDescription.getText().toString().length() > 0 && fullDescription.getText().toString().length() > 0 &&
                money.getText().toString().length() > 0 && day.getText().toString().length() > 0 && account.getText().toString().length() > 0 && selectedImageUri != null) {
            dataAdvertisement = new LinkedHashMap<>();
            dataAdvertisement.put("title", String.valueOf(theme.getText()));
            dataAdvertisement.put("short_description", String.valueOf(shortDescription.getText()));
            dataAdvertisement.put("description", String.valueOf(fullDescription.getText()));
            dataAdvertisement.put("expected_amount", String.valueOf(money.getText() + spin));
            dataAdvertisement.put("final_date", String.valueOf(day.getText()));
            dataAdvertisement.put("payment_account", String.valueOf(account.getText()));
            dataAdvertisement.put("user_id", userid);
            File file = new File(getPath(selectedImageUri));
            TypedFile image = new TypedFile("image/*", file);

            Retrofit.sendAdvertisement(dataAdvertisement, image, new Callback<RegistrationResponseFromServer>() {
                @Override
                public void success(RegistrationResponseFromServer registrationResponseFromServer, Response response) {

                    Intent intent = new Intent(DescriptionProblem.this, NewsNavigationDrawer.class);
                    startActivityForResult(intent, 1);
                    overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(DescriptionProblem.this, "Все плохо", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(DescriptionProblem.this, "Заполните все поля и установите изображение", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String finalDay = dayOfMonth + "/" + (++monthOfYear) + "/" + year;

        day.setText(finalDay);
    }

    public class MyAdapter extends ArrayAdapter<String> {
        public MyAdapter(Context ctx, int txtViewResourceId, String[] objects) {
            super(ctx, txtViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
            return getCustomView(position, cnvtView, prnt);
        }

        @Override
        public View getView(int pos, View cnvtView, ViewGroup prnt) {
            return getCustomView(pos, cnvtView, prnt);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View mySpinner = inflater.inflate(R.layout.custom_spinner, parent, false);
            TextView main_text = (TextView) mySpinner.findViewById(R.id.text_main_seen);
            main_text.setText(spinnerValues[position]);
            ImageView left_icon = (ImageView) mySpinner.findViewById(R.id.left_pic);
            left_icon.setImageResource(total_images[position]);
            return mySpinner;
        }
    }
}

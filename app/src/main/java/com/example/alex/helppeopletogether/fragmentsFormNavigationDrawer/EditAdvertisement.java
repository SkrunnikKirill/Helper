package com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
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

import com.bumptech.glide.Glide;
import com.example.alex.helppeopletogether.R;
import com.example.alex.helppeopletogether.SupportClasses.Constant;
import com.example.alex.helppeopletogether.SupportClasses.FiledTest;
import com.example.alex.helppeopletogether.SupportClasses.GetCurensyYear;
import com.example.alex.helppeopletogether.SupportClasses.IFonts;
import com.example.alex.helppeopletogether.SupportClasses.InternetCheck;
import com.example.alex.helppeopletogether.SupportClasses.Preferences;
import com.example.alex.helppeopletogether.retrofit.RegistrationResponseFromServer;
import com.example.alex.helppeopletogether.retrofit.ResponseFromServerEditAdvertisement;
import com.example.alex.helppeopletogether.retrofit.Retrofit;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.LinkedHashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

/**
 * Created by PM on 29.06.2016.
 */
public class EditAdvertisement extends Activity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, Constant, IFonts {
    ImageView pictureAdvertisement;
    EditText theme, shortDescription, fullDescription, money, account;
    TextView day, themeText, shortDescriptionText, fullDescriptionText, moneyText, accountText, dayText, fotoText, toolbarText;
    Button editButton;
    Uri selectedImageUri;
    LinearLayout linearLayout;
    Context context;
    LinearLayout layoutBack;
    private Spinner currency;
    private FiledTest filedTest;
    private LinkedHashMap data;
    private InternetCheck internet;
    private Preferences preferences;
    private GetCurensyYear year;
    private NewsNavigationDrawer navigationDrawer;
    private File file;
    private TypedFile editImage;
    private FragmentManager fragmentManager;
    private Integer answerFromServer;
    private String[] nameCurrency = {"USD", "EUR", "UAH", ""};
    private int total_images[] = {R.drawable.dollar, R.drawable.evro, R.drawable.hrivna, 0};
    private String eAId, eAThema, eACreatedat, eAShortDescription, eADescription, eAImage, eAExpectedAmount, eAFinalDate, eAPaymentAccount, selectedImagePath, currencySimvol,
            imageFile, userId, newsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_advertisement);
        fragmentManager = getFragmentManager();
        data = new LinkedHashMap<>();
        context = EditAdvertisement.this;
        navigationDrawer = new NewsNavigationDrawer();
        preferences = new Preferences(context);
        userId = preferences.loadText(PREFERENCES_ID);
        newsId = preferences.loadText(PREFERENCES_ID_NEWS);
        year = new GetCurensyYear();
        themeText = (TextView) findViewById(R.id.edit_advertisement_thema_text);
        toolbarText = (TextView) findViewById(R.id.activity_edit_advertisement_toolbar_text);
        layoutBack = (LinearLayout) findViewById(R.id.layoutBack);
        shortDescriptionText = (TextView) findViewById(R.id.edit_advertisement_short_description_text);
        dayText = (TextView) findViewById(R.id.edit_advertisement_day_text);
        fullDescriptionText = (TextView) findViewById(R.id.edit_advertisement_full_description_text);
        moneyText = (TextView) findViewById(R.id.edit_advertisement_money_text);
        accountText = (TextView) findViewById(R.id.edit_advertisement_account_text);
        fotoText = (TextView) findViewById(R.id.edit_advertisement_name_photo);
        pictureAdvertisement = (ImageView) findViewById(R.id.edit_advertisement_image);
        linearLayout = (LinearLayout) findViewById(R.id.edit_advertisement_linear_layout);
        theme = (EditText) findViewById(R.id.edit_advertisement_theme);
        shortDescription = (EditText) findViewById(R.id.edit_advertisement_short_description);
        fullDescription = (EditText) findViewById(R.id.edit_advertisement_full_description);
        money = (EditText) findViewById(R.id.edit_advertisement_money);
        day = (TextView) findViewById(R.id.edit_advertisement_day);
        account = (EditText) findViewById(R.id.edit_advertisement_account);
        currency = (Spinner) findViewById(R.id.edit_advertisement_spinnerstate);
        editButton = (Button) findViewById(R.id.edit_advertisement_edit_button);
        pictureAdvertisement.setOnClickListener(this);
        layoutBack.setOnClickListener(this);
        spiner();
        fonts();
        dataPicker();



        Retrofit.sendAdvertisementEdit(userId, newsId, new Callback<ResponseFromServerEditAdvertisement>() {
            @Override
            public void success(ResponseFromServerEditAdvertisement responseFromServerEditAdvertisement, Response response) {
                if (responseFromServerEditAdvertisement == null) {
                    Toast.makeText(EditAdvertisement.this, R.string.error_data_from_server, Toast.LENGTH_SHORT).show();
                } else {
                    eAId = responseFromServerEditAdvertisement.id;
                    eACreatedat = responseFromServerEditAdvertisement.created_at;
                    eAShortDescription = responseFromServerEditAdvertisement.short_description;
                    eADescription = responseFromServerEditAdvertisement.description;
                    eAImage = responseFromServerEditAdvertisement.image;
                    eAExpectedAmount = responseFromServerEditAdvertisement.expected_amount;
                    eAFinalDate = responseFromServerEditAdvertisement.final_date;
                    eAThema = responseFromServerEditAdvertisement.title;
                    eAPaymentAccount = responseFromServerEditAdvertisement.payment_account;
                    dataRecordind();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (error.getCause() instanceof UnknownHostException) {
                    internet = new InternetCheck(context, linearLayout);
                    internet.execute();

                }
            }
        });
        filedTest = new FiledTest(theme, shortDescription, fullDescription, money, account);
        filedTest.inspection1();
        editButton.setOnClickListener(this);
    }


    private void dataRecordind() {
        theme.setText(eAThema);
        shortDescription.setText(eAShortDescription);
        fullDescription.setText(eADescription);
        day.setText(eAFinalDate);
        account.setText(eAPaymentAccount);
        money.setText(eAExpectedAmount);
        Glide.with(EditAdvertisement.this).load(eAImage).placeholder(R.drawable.no_donload_image).error(R.drawable.nointernet).into(pictureAdvertisement);


    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
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

    private void spiner() {
        currency.setAdapter(new SpinerAdapter(this, R.layout.custom_spinner, nameCurrency));
        currency.setPrompt("Title");
        // выделяем элемент
        currency.setSelection(3);
        // устанавливаем обработчик нажатия
        currency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // показываем позиция нажатого элемента
                currencySimvol = nameCurrency[position];
                if (currencySimvol == "USD") {
                    currencySimvol = "$";
                } else if (currencySimvol == "EUR") {
                    currencySimvol = "€";
                } else if (currencySimvol == "UAH") {
                    currencySimvol = "₴";
                } else if (currencySimvol == "") {
                    currencySimvol = "";
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
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                        EditAdvertisement.this,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.setMinDate(calendar);
                datePickerDialog.setAccentColor(Color.parseColor("#03a9f4"));
                datePickerDialog.setThemeDark(true);
                datePickerDialog.dismissOnPause(true);
                int startYear = year.getCurrentYear();
                int nextYear = year.getCurrentYear() + 5;
                datePickerDialog.setYearRange(startYear, nextYear);
                datePickerDialog.show(getFragmentManager(), "Datepickerdialog");
            }
        });
    }

    private void setEditInformationFromServer() {
        if (theme.getText().toString().length() > 0 && shortDescription.getText().toString().length() > 0 && fullDescription.getText().toString().length() > 0 &&
                money.getText().toString().length() > 0 && day.getText().toString().length() > 0 && account.getText().toString().length() > 0) {
            data.put("title", theme.getText().toString());
            data.put("short_description", shortDescription.getText().toString());
            data.put("description", fullDescription.getText().toString());
            data.put("final_date", day.getText().toString());
            data.put("payment_account", account.getText().toString());
            data.put("expected_amount", money.getText().toString() + currencySimvol);
            data.put("user_id", userId);
            data.put("adver_id", newsId);
            if (imageFile != null) {
                file = new File(imageFile);
                editImage = new TypedFile("image/*", file);
                data.put("imageAdvertisement", editImage);
            } else {
                data.put("imageAdvertisement", eAImage);
            }
            Retrofit.sendEditInformationFromServer(data, new Callback<RegistrationResponseFromServer>() {

                @Override
                public void success(RegistrationResponseFromServer registrationResponseFromServer, Response response) {
                    if (registrationResponseFromServer == null) {
                        Toast.makeText(EditAdvertisement.this, R.string.error_data_from_server, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    answerFromServer = registrationResponseFromServer.response;


                    if (answerFromServer == 1) {
                        Intent intent = new Intent(EditAdvertisement.this, NewsNavigationDrawer.class);
                        startActivityForResult(intent, 1);

                    } else {
                        Toast.makeText(EditAdvertisement.this, "У вас не получилось редактировать объявление", Toast.LENGTH_SHORT).show();
                    }
                    preferences.remove(PREFERENCES_ID_NEWS);
                }

                @Override
                public void failure(RetrofitError error) {
                    if (error.getCause() instanceof UnknownHostException) {
                        internet = new InternetCheck(context, linearLayout);
                        internet.execute();

                    }
                }
            });
        } else {
            Toast.makeText(EditAdvertisement.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
        }
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("user_id", userId);
        outState.putString("news_id", newsId);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edit_advertisement_edit_button:
                setEditInformationFromServer();
                break;
            case R.id.edit_advertisement_image:
                selectImage();
                break;
            case R.id.layoutBack:
                finish();
                break;


        }
    }

    public void selectImage() {
        final CharSequence[] items = {getString(R.string.take_a_photo), getString(R.string.select_a_photo),
                getString(R.string.cancel)};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.add_photo);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(getString(R.string.take_a_photo))) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals(getString(R.string.select_a_photo))) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, getString(R.string.select_a_photo)),
                            SELECT_FILE);
                } else if (items[item].equals(getString(R.string.cancel))) {
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
                onSelectFromGalleryResult(data, pictureAdvertisement);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data, pictureAdvertisement);
        }
    }

    public void onCaptureImageResult(Intent data, ImageView image) {
        selectedImageUri = data.getData();
        imageFile = getPath(selectedImageUri);
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
        imageFile = getPath(selectedImageUri);
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

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String finalDay = dayOfMonth + "/" + (++monthOfYear) + "/" + year;
        day.setText(finalDay);
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
        editButton.setTypeface(mtypeface);
        themeText.setTypeface(mtypeface);
        fotoText.setTypeface(mtypeface);
        shortDescriptionText.setTypeface(mtypeface);
        fullDescriptionText.setTypeface(mtypeface);
        accountText.setTypeface(mtypeface);
        moneyText.setTypeface(mtypeface);
        toolbarText.setTypeface(mtypeface);
        toolbarText.setTypeface(mtypeface);
        dayText.setTypeface(mtypeface);

    }

    public class SpinerAdapter extends ArrayAdapter<String> {
        public SpinerAdapter(Context ctx, int txtViewResourceId, String[] objects) {
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


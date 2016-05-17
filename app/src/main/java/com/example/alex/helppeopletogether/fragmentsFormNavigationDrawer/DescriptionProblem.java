package com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.alex.helppeopletogether.R;
import com.example.alex.helppeopletogether.registration.Login;
import com.example.alex.helppeopletogether.registration.Registration;
import com.example.alex.helppeopletogether.retrofit.RegistrationResponseFromServer;
import com.example.alex.helppeopletogether.retrofit.Retrofit;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

/**
 * Created by Alex on 13.04.2016.
 */
public class DescriptionProblem extends AppCompatActivity implements View.OnClickListener {
    static final int GALLERY_REQUEST_IMAGE = 1;
    ImageView imageAdvertisement;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    Uri selectedImageUri;
    String selectedImagePath;
    Registration registration;
    Login login;
    Integer userid;
    private Toolbar toolbar;

    private EditText theme, shortDescription, fullDescription, money, day, account;
    private Button locate;
    private LinkedHashMap<String, String> dataAdvertisement;
    android.support.v4.app.FragmentTransaction ft;
    android.support.v4.app.FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description_problem);
        imageAdvertisement = (ImageView) findViewById(R.id.description_problem_image);
        theme = (EditText) findViewById(R.id.description_problem_theme);
        shortDescription = (EditText) findViewById(R.id.description_problem_short_description);
        fullDescription = (EditText) findViewById(R.id.description_problem_full_description);
        money = (EditText) findViewById(R.id.description_problem_money);
        day = (EditText) findViewById(R.id.description_problem_day);
        account = (EditText)findViewById(R.id.description_problem_account);
        locate = (Button) findViewById(R.id.description_problem_locate);
        registration = new Registration();
        imageAdvertisement.setOnClickListener(this);
        locate.setOnClickListener(this);
        toolbar.setOnClickListener(this);
        login = new Login();
        userid = login.userId;
        initToolbar();


    }

    private void initToolbar() {
        toolbar = (Toolbar)findViewById(R.id.toolbar_description);
        toolbar.setTitle(R.string.description);
      toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_errow));
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            }
        });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
        dataAdvertisement = new LinkedHashMap<>();
        dataAdvertisement.put("title",String.valueOf(theme.getText()));
        dataAdvertisement.put("short_description",String.valueOf(shortDescription.getText()));
        dataAdvertisement.put("description",String.valueOf(fullDescription.getText()));
        dataAdvertisement.put("expected_amount",String.valueOf(money.getText()));
        dataAdvertisement.put("final_date", String.valueOf(day.getText()));
        dataAdvertisement.put("payment_account", String.valueOf(account.getText()));
        dataAdvertisement.put("user_id",String.valueOf(userid));
        File file = new File(getPath(selectedImageUri));
        TypedFile image = new TypedFile("image/*", file);
        Retrofit.sendAdvertisement(dataAdvertisement, image, new Callback<RegistrationResponseFromServer>() {
            @Override
            public void success(RegistrationResponseFromServer registrationResponseFromServer, Response response) {
                Toast.makeText(DescriptionProblem.this, "Все ok", Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(DescriptionProblem.this, "Все плохо", Toast.LENGTH_LONG).show();
            }
        });
    }
}

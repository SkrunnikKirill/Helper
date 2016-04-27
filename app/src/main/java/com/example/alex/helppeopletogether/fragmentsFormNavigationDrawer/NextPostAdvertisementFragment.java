package com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Telephony;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.alex.helppeopletogether.R;
import com.example.alex.helppeopletogether.retrofit.RegistrationResponseFromServer;
import com.example.alex.helppeopletogether.retrofit.Retrofit;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

/**
 * Created by Alex on 06.04.2016.
 */
public class NextPostAdvertisementFragment extends Activity implements View.OnClickListener {
    static final int GALLERY_REQUEST_PASSPORT = 1;
    private static final int GALLERY_REQUEST_COPYING = 2;
    Uri selectedCopying;
    Uri selectedPassport;
    Bitmap bitmapPassport;
    Bitmap bitmapCopying;
    private EditText serialPassport;
    private EditText numberPassport;
    private EditText account;
    private TextView licenseText;
    private CheckBox licenseCheckBox;
    private ImageView imagePassport;
    private ImageView imageCopying;
    private Button next;
    private LinkedHashMap<String, TypedFile> imageData;
    private Integer responseFromServiseNextPostAdvertisementFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.next_post_advertisement_fragment);
        serialPassport = (EditText) findViewById(R.id.next_post_advertisement_fragment_serial);
        numberPassport = (EditText) findViewById(R.id.next_post_advertisement_fragment_number);
        account = (EditText) findViewById(R.id.next_post_advertisement_fragment_account);
        licenseText = (TextView) findViewById(R.id.next_post_advertisement_fragment_license_text);
        licenseCheckBox = (CheckBox) findViewById(R.id.next_post_advertisement_fragment_license_checkbox);
        imageCopying = (ImageView) findViewById(R.id.next_post_advertisement_fragment_image_copying);
        imagePassport = (ImageView) findViewById(R.id.next_post_advertisement_fragment_image_passport);
        next = (Button) findViewById(R.id.next_post_advertisement_fragment_next);
        next.setOnClickListener(this);

        imagePassport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.next_post_advertisement_fragment_image_passport:
                        Intent photoPassportPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        photoPassportPickerIntent.setType("image/*");
                        photoPassportPickerIntent.addCategory(Intent.CATEGORY_OPENABLE);
                        // startActivityForResult(photoPassportPickerIntent, GALLERY_REQUEST_PASSPORT);
                        Intent chooserIntent = Intent.createChooser(photoPassportPickerIntent, "Выбереите изображение");
                        startActivityForResult(photoPassportPickerIntent, GALLERY_REQUEST_COPYING);
                        break;
                }

            }
        });

        imageCopying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.next_post_advertisement_fragment_image_copying:
                        Intent photoCopyingPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        photoCopyingPickerIntent.setType("image/*");
                        photoCopyingPickerIntent.addCategory(Intent.CATEGORY_OPENABLE);

                        Intent chooserIntent = Intent.createChooser(photoCopyingPickerIntent, "Выбереите изображение");
                        startActivityForResult(chooserIntent, GALLERY_REQUEST_COPYING);
                        //startActivityForResult(photoCopyingPickerIntent, GALLERY_REQUEST_COPYING);
                        break;
                }
            }
        });

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {

            case GALLERY_REQUEST_PASSPORT:
                if (resultCode == RESULT_OK) {
                    selectedPassport = data.getData();
                    // bitmapPassport.recycle();
                    try {
                        imagePassport.setImageBitmap(null);
                        if (bitmapPassport == null) {
                            bitmapPassport = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedPassport);
                        } else {
                            bitmapPassport.recycle();
                            bitmapPassport = null;
                            bitmapPassport = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedPassport);
                        }
                        imagePassport.setImageBitmap(bitmapPassport);
                        bitmapPassport.recycle();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
                break;

            case GALLERY_REQUEST_COPYING:
                if (resultCode == RESULT_OK) {
                    selectedCopying = data.getData();
                    try {
                        imageCopying.setImageBitmap(null);
                        if (bitmapCopying == null) {
                            bitmapCopying = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedCopying);
                        } else {
                            bitmapCopying.recycle();
                            bitmapCopying = null;
                            bitmapCopying = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedCopying);
                        }

                        imageCopying.setImageBitmap(bitmapCopying);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_post_advertisement_fragment_next:
                imageData = new LinkedHashMap<>();
                ArrayList<ImageView> list = new ArrayList<>();
                list.add(imagePassport);
                list.add(imageCopying);
                ImageView array[] = new ImageView[2];
                array[0] = imagePassport;
                array[1] = imageCopying;

                // String uri = String.valueOf(selectedCopying);
                File file = new File(getPath(selectedCopying));
                TypedFile image = new TypedFile("image/*", file);
                imageData.put("copying" + file.lastModified(), image);
                Uri[] uri = new Uri[6];
                uri[0] = selectedPassport;
                uri[1] = selectedCopying;
                File file1 = new File(getPath(selectedPassport));
                TypedFile image1 = new TypedFile("image/*", file1);
                imageData.put("passport" + file1.lastModified(), image1);

        }


        //  imageData.put("passport", );
        // imageData.put("copying", imageCopying);
        // File file = new File(imagePassport);
        Retrofit.sendNextPostImage(imageData, new Callback<RegistrationResponseFromServer>() {
            @Override
            public void success(RegistrationResponseFromServer registrationResponseFromServer, Response response) {
                responseFromServiseNextPostAdvertisementFragment = registrationResponseFromServer.status;
                if (responseFromServiseNextPostAdvertisementFragment == 2) {
                    Toast.makeText(NextPostAdvertisementFragment.this, "Данные пришли на сервер!!!", Toast.LENGTH_LONG).show();
                } else if (responseFromServiseNextPostAdvertisementFragment == 1) {
                    Toast.makeText(NextPostAdvertisementFragment.this, "Данные не пришли на сервер!!!", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(NextPostAdvertisementFragment.this, "Все добрееее!!!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplication(), "Data don't sent. Check internet connection", Toast.LENGTH_LONG).show();
            }
        });
        Intent intent = new Intent(this, DescriptionProblem.class);
        startActivity(intent);
        }
    }




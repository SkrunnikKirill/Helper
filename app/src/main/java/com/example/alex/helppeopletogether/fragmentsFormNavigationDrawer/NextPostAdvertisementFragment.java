package com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.alex.helppeopletogether.R;

import java.io.IOException;

/**
 * Created by Alex on 06.04.2016.
 */
public class NextPostAdvertisementFragment extends Activity {
    static final int GALLERY_REQUEST = 1;
    private EditText serialPassport;
    private EditText numberPassport;
    private EditText account;
    private TextView licenseText;
    private CheckBox licenseCheckBox;
    private ImageView imagePassport;
    private ImageView imageCopying;

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
        imagePassport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.next_post_advertisement_fragment_image_passport:
                        Intent photoPassportPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPassportPickerIntent.setType("image/*");
                        startActivityForResult(photoPassportPickerIntent, GALLERY_REQUEST);
                        break;
                    case R.id.next_post_advertisement_fragment_image_copying:
                        Intent photoCopyingPickerIntent1 = new Intent(Intent.ACTION_PICK);
                        photoCopyingPickerIntent1.setType("image/*");
                        startActivityForResult(photoCopyingPickerIntent1, GALLERY_REQUEST);
                        break;
                }

            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmapPassport = null;
        Bitmap bitmapCopying = null;

        switch (requestCode) {
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    Uri selectedPassport = data.getData();
                    Uri selectedCopying = data.getData();
                    try {
                        bitmapPassport = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedPassport);
                        bitmapCopying = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedCopying);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imagePassport.setImageBitmap(bitmapPassport);
                    imageCopying.setImageBitmap(bitmapCopying);
                }
        }
    }
}


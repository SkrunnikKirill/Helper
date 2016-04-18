package com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.alex.helppeopletogether.R;

import java.io.IOException;

/**
 * Created by Alex on 13.04.2016.
 */
public class DescriptionProblem extends Activity {
    static final int GALLERY_REQUEST_IMAGE = 1;
    ImageView image;
    private EditText target, shortDescription, fullDescription, money, day;
    private Button locate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description_problem);
        image = (ImageView) findViewById(R.id.description_problem_image);
        target = (EditText) findViewById(R.id.description_problem_target);
        shortDescription = (EditText) findViewById(R.id.description_problem_short_description);
        fullDescription = (EditText) findViewById(R.id.description_problem_full_description);
        money = (EditText) findViewById(R.id.description_problem_money);
        day = (EditText) findViewById(R.id.description_problem_day);
        locate = (Button) findViewById(R.id.description_problem_locate);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPassportPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPassportPickerIntent.setType("image/*");
                startActivityForResult(photoPassportPickerIntent, GALLERY_REQUEST_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;


        switch (requestCode) {
            case GALLERY_REQUEST_IMAGE:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    image.setImageBitmap(bitmap);
                }
        }
    }
}

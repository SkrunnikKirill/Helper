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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.alex.helppeopletogether.R;
import com.example.alex.helppeopletogether.registration.Registration;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Alex on 13.04.2016.
 */
public class DescriptionProblem extends Activity {
    static final int GALLERY_REQUEST_IMAGE = 1;
    ImageView image;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    Uri selectedImageUri;
    String selectedImagePath;
    Registration registration;
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
        registration = new Registration();
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
//                Intent photoPassportPickerIntent = new Intent(Intent.ACTION_PICK);
//                photoPassportPickerIntent.setType("image/*");
//                startActivityForResult(photoPassportPickerIntent, GALLERY_REQUEST_IMAGE);
            }
        });

    }

    public void selectImage() {
        final CharSequence[] items = {"Сделать фотографию", "Выбрать фотографию",
                "Отмена"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
                onSelectFromGalleryResult(data, image);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data, image);
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
}

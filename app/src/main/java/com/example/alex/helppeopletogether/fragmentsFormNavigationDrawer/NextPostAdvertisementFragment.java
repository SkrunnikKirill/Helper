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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.helppeopletogether.R;
import com.example.alex.helppeopletogether.retrofit.RegistrationResponseFromServer;
import com.example.alex.helppeopletogether.retrofit.Retrofit;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    Uri selectedCopying, selectedPassport;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private EditText serialPassport, numberPassport;
    private TextView licenseText;
    private CheckBox licenseCheckBox;
    private ImageView imagePassport, imageCopying;
    private Button next;
    private LinkedHashMap<String, TypedFile> imageData;
    private Integer responseFromServiseNextPostAdvertisementFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.next_post_advertisement_fragment);
        serialPassport = (EditText) findViewById(R.id.next_post_advertisement_fragment_serial);
        numberPassport = (EditText) findViewById(R.id.next_post_advertisement_fragment_number);
        licenseText = (TextView) findViewById(R.id.next_post_advertisement_fragment_license_text);
        licenseCheckBox = (CheckBox) findViewById(R.id.next_post_advertisement_fragment_license_checkbox);
        imageCopying = (ImageView) findViewById(R.id.next_post_advertisement_fragment_image_copying);
        imagePassport = (ImageView) findViewById(R.id.next_post_advertisement_fragment_image_passport);
        next = (Button) findViewById(R.id.next_post_advertisement_fragment_next);
        next.setOnClickListener(this);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.next_post_advertisement_fragment_image_passport:
                        selectImage();
                        break;
                    case R.id.next_post_advertisement_fragment_image_copying:
                        selectImage();
                        break;
                }
            }
        };
        imagePassport.setOnClickListener(listener);
        imageCopying.setOnClickListener(listener);


    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(NextPostAdvertisementFragment.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("imageAdvertisement/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

                setImage(requestCode, resultCode, data, imagePassport);


        }





    private void setImage(int requestCode, int resultCode, Intent data, ImageView imageView) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data, imageView);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data, imageView);
        }
    }


    private void onCaptureImageResult(Intent data, ImageView imageView) {
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

        imageView.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data, ImageView imageView) {
        Uri selectedImageUri = data.getData();
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);

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

        imageView.setImageBitmap(bm);
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
                TypedFile image = new TypedFile("imageAdvertisement/*", file);
                imageData.put("copying" + file.lastModified(), image);
                Uri[] uri = new Uri[6];
                uri[0] = selectedPassport;
                uri[1] = selectedCopying;
                File file1 = new File(getPath(selectedPassport));
                TypedFile image1 = new TypedFile("imageAdvertisement/*", file1);
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




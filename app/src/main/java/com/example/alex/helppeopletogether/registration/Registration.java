package com.example.alex.helppeopletogether.registration;


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
import android.widget.Toast;

import com.example.alex.helppeopletogether.R;
import com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer.NextPostAdvertisementFragment;
import com.example.alex.helppeopletogether.retrofit.RegistrationResponseFromServer;
import com.example.alex.helppeopletogether.retrofit.Retrofit;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;


public class Registration extends Activity implements View.OnClickListener {
    public static Integer responseFromServiseRegistrationId;
    Intent intent;
    Integer responseFromServiseRegistration;
    String responseFromServiseFullName;
    Uri selectedImageUri;
    String selectedImagePath;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private EditText firstName, secondName, email, password;
    private ImageView face;
    private Button buttonRegistration;
    private HashMap<String, String> data;
    private String regularExprensionsEmail;
    private String regularExprensionsLogin;
    private boolean result;
    private String response;
    private File file;
    private TypedFile imageFace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        face = (ImageView) findViewById(R.id.registration_face_image);
        firstName = (EditText) findViewById(R.id.registration_first_name);
        secondName = (EditText) findViewById(R.id.registration_second_name);
        email = (EditText) findViewById(R.id.registration_email);
        password = (EditText) findViewById(R.id.registration_password);
        buttonRegistration = (Button) findViewById(R.id.registration_button_registration);
        buttonRegistration.setOnClickListener(this);
        face.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registration_button_registration:
                sendRegistrationInformationToServer();
                break;
            case R.id.registration_face_image:
                selectImage();
                break;
        }
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
                onSelectFromGalleryResult(data, face);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data, face);
        }
    }


    public void onCaptureImageResult(Intent data, ImageView image) {
        Uri selectedImageUri = data.getData();
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


    public void sendRegistrationInformationToServer() {
        data = new HashMap<>();
        if (firstName.getText().length() < 0 || secondName.getText().length() < 0) {
            Toast.makeText(getApplication(), "Все поля должны быть заполнены", Toast.LENGTH_LONG).show();
        } else if (resultRegularExprensionsEmail(email) == false) {
            Toast.makeText(getApplication(), "Email, некоректный", Toast.LENGTH_LONG).show();
        } else if (password.getText().length() < 6) {
            Toast.makeText(getApplication(), "Пароль, должен быть больше 6 символов", Toast.LENGTH_LONG).show();
        } else {
            if (selectedImageUri == null){
                Toast.makeText(Registration.this,"Установите фотографию",Toast.LENGTH_LONG).show();
            }else {
                 file = new File(getPath(selectedImageUri));
                 imageFace = new TypedFile("image/*", file);
            }
            data.put("first_name", String.valueOf(firstName.getText()));
            data.put("second_name", String.valueOf(secondName.getText()));
            data.put("email", String.valueOf(email.getText()));
            data.put("password", String.valueOf(password.getText()));


            Retrofit.sendRegistrationData(data, imageFace, new Callback<RegistrationResponseFromServer>() {
                @Override
                public void success(RegistrationResponseFromServer registrationResponseFromServer, Response response) {
                    Toast.makeText(getApplication(), "Data sent", Toast.LENGTH_SHORT).show();
                    responseFromServiseRegistration = registrationResponseFromServer.response;
                    responseFromServiseRegistrationId = registrationResponseFromServer.user_id;
                    responseFromServiseFullName = registrationResponseFromServer.full_name;
                    if (responseFromServiseRegistration == 3){
                        Toast.makeText(Registration.this,"Логин занят",Toast.LENGTH_LONG).show();
                    }else if (responseFromServiseRegistration ==1){
                        Toast.makeText(Registration.this,"Email занят", Toast.LENGTH_LONG).show();
                    }else if (responseFromServiseRegistration ==2){
                        intent = new Intent(Registration.this, Agreement.class);
                          startActivity(intent);
                        //Toast.makeText(Registration.this,"ВСЕ ОК!!!!", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getApplication(), "Data don't sent. Check internet connection", Toast.LENGTH_LONG).show();
                }
            });

//            Retrofit.sendRegistrationData(data, new Callback<RegistrationResponseFromServer>() {
//                @Override
//                public void success(RegistrationResponseFromServer responseRegistration, Response response)  {
//                    Toast.makeText(getApplication(), "Data sent", Toast.LENGTH_SHORT).show();
//                    responseFromServiseRegistration = responseRegistration.response;
//
//                    if (responseFromServiseRegistration == 3){
//                        Toast.makeText(Registration.this,"Логин занят",Toast.LENGTH_LONG).show();
//                    }else if (responseFromServiseRegistration ==1){
//                        Toast.makeText(Registration.this,"Email занят", Toast.LENGTH_LONG).show();
//                    }else if (responseFromServiseRegistration ==2){
//                        Toast.makeText(Registration.this,"ВСЕ ОК!!!!", Toast.LENGTH_LONG).show();
//                    }
//
//                }
//
//                @Override
//                public void failure(RetrofitError error) {
//                    Toast.makeText(getApplication(), "Data don't sent. Check internet connection", Toast.LENGTH_LONG).show();
//                }
//            });


        }




    }

    public boolean resultRegularExprensionsEmail(EditText regularEmail) {
        regularExprensionsEmail = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"" +
                "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\"" +
                ")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\" +
                "[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?" +
                "|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\" +
                "x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        Pattern pCheckEmail = Pattern.compile(regularExprensionsEmail);
        Matcher mCheckEmail = pCheckEmail.matcher(regularEmail.getText().toString());
        result = mCheckEmail.matches();
        return result;
    }

//    private boolean resultRegularExprensionsLogin(){
//        regularExprensionsLogin = " /^[a-z0-9_-]{3,16}$/";
//        Pattern pCheckLogin = Pattern.compile(regularExprensionsLogin);
//        Matcher mCheckLogin = pCheckLogin.matcher(login.getText().toString());
//        result = mCheckLogin.matches();
//        return result;
//
//    }


}


package com.example.alex.helppeopletogether.registration;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.alex.helppeopletogether.R;
import com.example.alex.helppeopletogether.SupportClasses.Constant;
import com.example.alex.helppeopletogether.SupportClasses.FiledTest;
import com.example.alex.helppeopletogether.SupportClasses.InternetCheck;
import com.example.alex.helppeopletogether.SupportClasses.MyToast;
import com.example.alex.helppeopletogether.SupportClasses.Preferences;
import com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer.NewsNavigationDrawer;
import com.example.alex.helppeopletogether.retrofit.RegistrationResponseFromServer;
import com.example.alex.helppeopletogether.retrofit.Retrofit;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;


public class Registration extends Activity implements View.OnClickListener, Constant {
    private Integer responseFromServiseRegistrationId, responseFromServiseRegistration;
    private String responseFromServiseFullName, responseFromServiseImage, selectedImagePath, regularExprensionsEmail;
    private Intent intent;
    private Uri selectedImageUri, mCropImageUri, selectedImageUri1;
    private RelativeLayout relativeLayoutRegistrationSnackBar;
    private Preferences preferences;
    private FiledTest test;
    private EditText firstName, secondName, email, password;
    private CircleImageView face;
    private Button buttonRegistration;
    private HashMap<String, String> data;
    private Context context;
    private boolean result;
    private File file;
    private TypedFile imageFace;
    private ToggleButton showPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        preferences = new Preferences(Registration.this);
        context = Registration.this;
        relativeLayoutRegistrationSnackBar = (RelativeLayout) findViewById(R.id.registration_first_relative_layout);
        face = (CircleImageView) findViewById(R.id.registration_avatar_image);
        firstName = (EditText) findViewById(R.id.registration_first_name);
        secondName = (EditText) findViewById(R.id.registration_second_name);
        email = (EditText) findViewById(R.id.registration_email);
        password = (EditText) findViewById(R.id.registration_password);
        buttonRegistration = (Button) findViewById(R.id.registration_button_registration);
        buttonRegistration.setOnClickListener(this);
        face.setOnClickListener(this);

    }

    public void checkInternet() {
        InternetCheck internetCheck = new InternetCheck(Registration.this, relativeLayoutRegistrationSnackBar);
        internetCheck.execute();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("firstName", firstName.getText().toString());
        outState.putString("secondName", secondName.getText().toString());
        outState.putString("email", email.getText().toString());
        outState.putString("password", password.getText().toString());


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        firstName.setText(savedInstanceState.getString("firstName"));
        secondName.setText(savedInstanceState.getString("secondName"));
        email.setText(savedInstanceState.getString("email"));
        password.setText(savedInstanceState.getString("password"));
    }


    @Override
    protected void onStop() {
        super.onStop();
        firstName.setText("");
        secondName.setText("");
        email.setText("");
        password.setText("");
        face.setImageResource(R.drawable.foto);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registration_button_registration:
                sendRegistrationInformationToServer();
                break;
            case R.id.registration_avatar_image:
                CropImage.startPickImageActivity(this);
                break;
        }
    }


    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Bitmap bitmap = ((BitmapDrawable)face.getDrawable()).getBitmap();



        // handle result of pick image chooser
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                selectedImageUri = data.getData();
                file = new File(getPath(selectedImageUri));
                imageFace = new TypedFile("image/*", file);
            } else {
                selectedImageUri = getPickImageResultUri(data);
                file = new File(selectedImageUri.getPath());
                imageFace = new TypedFile("image/*", file);
            }
            Uri imageUri = CropImage.getPickImageResultUri(this, data);

            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                mCropImageUri = imageUri;
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
                face.setImageURI(result.getUri());
                //selectedImageUri = result.getUri();
                Toast.makeText(this, "Cropping successful, Sample: " + result.getSampleSize(), Toast.LENGTH_LONG).show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // required permissions granted, start crop image activity
            startCropImageActivity(mCropImageUri);
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



    public void sendRegistrationInformationToServer() {
        data = new HashMap<>();
        if (firstName.getText().toString().length() > 2 && secondName.getText().toString().length() > 2 && password.getText().toString().length() > 6 && resultRegularExprensionsEmail(email) != false) {

            data.put("first_name", firstName.getText().toString());
            data.put("second_name", secondName.getText().toString());
            data.put("email", email.getText().toString());
            data.put("password", password.getText().toString());


            Retrofit.sendRegistrationData(data, imageFace, new Callback<RegistrationResponseFromServer>() {
                @Override
                public void success(RegistrationResponseFromServer registrationResponseFromServer, Response response) {
                    if (registrationResponseFromServer == null) {
                        MyToast.error(context, context.getString(R.string.error_data_from_server));
                    } else {
                        responseFromServiseRegistration = registrationResponseFromServer.response;
                        responseFromServiseRegistrationId = registrationResponseFromServer.user_id;
                        responseFromServiseFullName = registrationResponseFromServer.full_name;
                        responseFromServiseImage = registrationResponseFromServer.avatar;
                        if (responseFromServiseRegistration == 4) {
                            MyToast.info(context, context.getString(R.string.image_is_not_sent_to_server));
                        } else if (responseFromServiseRegistration == 3) {
                            MyToast.info(context, context.getString(R.string.login_busy));
                        } else if (responseFromServiseRegistration == 1) {
                            MyToast.info(context, context.getString(R.string.email_busy));
                        } else if (responseFromServiseRegistration == 2) {
                            preferences.saveText(email.getText().toString(), PREFERENCES_LOGIN);
                            preferences.saveText(password.getText().toString(), PREFERENCES_PASSWORD);
                            preferences.saveText(responseFromServiseRegistrationId.toString(), PREFERENCES_ID);
                            preferences.saveText(responseFromServiseFullName, PREFERENCES_NAME);
                            preferences.saveText(responseFromServiseImage, PREFERENCES_FOTO);
                            intent = new Intent(Registration.this, NewsNavigationDrawer.class);
                            startActivity(intent);
                        }
                    }

                }


                @Override
                public void failure(RetrofitError error) {
                    if (error.getCause() instanceof UnknownHostException) {
                        checkInternet();
                    }
                }
            });
        } else {
            MyToast.info(context, context.getString(R.string.instal_data));
        }


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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }


}


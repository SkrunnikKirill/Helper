package com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.alex.helppeopletogether.Adapter.ComentAdapter;
import com.example.alex.helppeopletogether.R;
import com.example.alex.helppeopletogether.SupportClasses.ComentInformation;
import com.example.alex.helppeopletogether.SupportClasses.Constant;
import com.example.alex.helppeopletogether.SupportClasses.Dimensions;
import com.example.alex.helppeopletogether.SupportClasses.IFonts;
import com.example.alex.helppeopletogether.SupportClasses.Preferences;
import com.example.alex.helppeopletogether.retrofit.RegistrationResponseFromServer;
import com.example.alex.helppeopletogether.retrofit.Retrofit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class DetailNews extends Activity implements Constant, View.OnClickListener, IFonts {
    ComentAdapter comentAdapter;
    ImageView image, enter;
    RecyclerView recyclerView;
    EditText comment;
    TextView shortDescription, theme, expectedAmount, finalDate, description, paymentAccount, toolbarText;
    Toolbar toolbar;
    Context context;
    LinearLayout back;
    private String nImage, nshortDescription, nexpectedAmount, nfinalDate, nDescription, nPaymentAccount, userId, nIdNews, nTitle,
            commentId, createdAt, fullName, foto, userComment;
    private ArrayList<ComentInformation> commentList;
    private Dimensions dimensions;
    private HashMap<String, String> comentData;
    private Preferences preferences;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_news);
        Intent intent = getIntent();
        nIdNews = intent.getStringExtra("idNews");

        context = DetailNews.this;
        nImage = intent.getStringExtra("image");
        nTitle = intent.getStringExtra("title");
        nshortDescription = intent.getStringExtra("shortDescription");
        nexpectedAmount = intent.getStringExtra("expectedAmount");
        nfinalDate = intent.getStringExtra("finalDate");
        nDescription = intent.getStringExtra("description");
        nPaymentAccount = intent.getStringExtra("paymentAccount");
        commentList = new ArrayList<>();
        comentAdapter = new ComentAdapter(context, commentList);
        recyclerView = (RecyclerView) findViewById(R.id.detail_news_list);
        getComment();
        comentData = new HashMap<>();
        preferences = new Preferences(context);
        userId = preferences.loadText(PREFERENCES_ID);
        image = (ImageView) findViewById(R.id.detail_news_image);
        enter = (ImageView) findViewById(R.id.detail_news_enter);
        back = (LinearLayout) findViewById(R.id.detail_news_layoutBack);
        toolbarText = (TextView) findViewById(R.id.detail_news_toolbar_text);
        enter.setOnClickListener(this);
        shortDescription = (TextView) findViewById(R.id.detail_news_theme);
        //TextView title = (TextView) findViewById(R.id.toolbar_description_tit);
        theme = (TextView) findViewById(R.id.detail_news_like_short_description);
        comment = (EditText) findViewById(R.id.detail_news_coment);
        expectedAmount = (TextView) findViewById(R.id.detail_news_summa);
        finalDate = (TextView) findViewById(R.id.detail_news_days_left);
        description = (TextView) findViewById(R.id.detail_news_full_description);
        paymentAccount = (TextView) findViewById(R.id.detail_news_expected_amount);
        dimensions = new Dimensions();
        Glide.with(getApplicationContext()).load(nImage).placeholder(R.drawable.no_donload_image).error(R.drawable.nointernet).override(dimensions.getWidth(DetailNews.this), 550).centerCrop().into(image);
        expectedAmount.setText("необходимо:  " + nexpectedAmount);
        description.setText(nDescription);
        finalDate.setText("до:  " + nfinalDate);
        theme.setText(nTitle);
        back.setOnClickListener(this);
        paymentAccount.setText("Расчетный счет:  " + nPaymentAccount);
        fonts();


    }


    private void setComent() {
        final String checkComentText = comment.getText().toString().trim();
        if (checkComentText.length() == 0) {
            comment.setError("Введите текст");
        } else {
            comentData.put("comment", comment.getText().toString());
            comentData.put("user_id", userId);
            comentData.put("adver_id", nIdNews);
            Retrofit.sendCommentInformation(comentData, new Callback<RegistrationResponseFromServer>() {
                @Override
                public void success(RegistrationResponseFromServer registrationResponseFromServer, Response response) {
                    if (registrationResponseFromServer == null) {
                        Toast.makeText(DetailNews.this, R.string.error_data_from_server, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (registrationResponseFromServer.response == 1) {
                        comment.setText("");
                        Toast.makeText(DetailNews.this, "Все ок", Toast.LENGTH_SHORT).show();
                        commentList.clear();
                        getComment();
                    } else if (registrationResponseFromServer.response == 2) {
                        Toast.makeText(DetailNews.this, "Данные не отправлены", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }
    }


    private void getComment() {
        Retrofit.getCommentInformation(nIdNews, new Callback<List<ComentInformation>>() {
            @Override
            public void success(final List<ComentInformation> comentInformations, Response response) {
                if (comentInformations == null) {
                    Toast.makeText(DetailNews.this, R.string.error_data_from_server, Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = comentInformations.size() - 1; i >= 0; i--) {
                        commentList.add(new ComentInformation(comentInformations.get(i).full_name,
                                comentInformations.get(i).avatar, comentInformations.get(i).comment,
                                comentInformations.get(i).created_at));
                        recyclerView.setNestedScrollingEnabled(false);
                        recyclerView.setAdapter(comentAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    }
                }


            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_news_enter:
                setComent();
                break;
            case R.id.detail_news_layoutBack:
                finish();
                break;
        }
    }

    @Override
    public void fonts() {
        Typeface mtypeface = Typeface.createFromAsset(context.getAssets(), "GothamProMedium.ttf");
        comment.setTypeface(mtypeface);
        theme.setTypeface(mtypeface);
        finalDate.setTypeface(mtypeface);
        expectedAmount.setTypeface(mtypeface);
        description.setTypeface(mtypeface);
        paymentAccount.setTypeface(mtypeface);
        toolbarText.setTypeface(mtypeface);
    }
}



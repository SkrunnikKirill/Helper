package com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
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
import com.example.alex.helppeopletogether.retrofit.Retrofit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class DetailNewsLike extends Activity implements Constant, IFonts, View.OnClickListener {
    ComentAdapter comentAdapter;
    ImageView image, enter;
    RecyclerView recyclerView;
    EditText comment;
    LinearLayout back;
    TextView shortDescription, theme, expectedAmount, finalDate, description, paymentAccount, toolbarText;
    Toolbar toolbar;
    private String nImage, nshortDescription, nexpectedAmount, nfinalDate, nDescription, nPaymentAccount, userId, nIdNews,
            commentId, createdAt, fullName, foto, userComment;
    private ArrayList<ComentInformation> commentList;
    private Dimensions dimensions;
    private CollapsingToolbarLayout collapsingToolbar;
    private HashMap<String, String> comentData;
    private Preferences preferences;
    private Context context;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deteil_news_like);
        Intent intent = getIntent();
        nIdNews = intent.getStringExtra("idNews");
        context = DetailNewsLike.this;
        nImage = intent.getStringExtra("image");
        nshortDescription = intent.getStringExtra("shortDescription");
        nexpectedAmount = intent.getStringExtra("expectedAmount");
        nfinalDate = intent.getStringExtra("finalDate");
        nDescription = intent.getStringExtra("description");
        nPaymentAccount = intent.getStringExtra("paymentAccount");
        commentList = new ArrayList<>();
        comentAdapter = new ComentAdapter(context, commentList);
        //recyclerView = (RecyclerView) findViewById(R.id.detail_news_like_list);
        getComment();
        comentData = new HashMap<>();
        preferences = new Preferences(context);
        userId = preferences.loadText(PREFERENCES_ID);
        image = (ImageView) findViewById(R.id.detail_news_like_image);
        //  enter.setOnClickListener(this);
        shortDescription = (TextView) findViewById(R.id.detail_news_like_short_description);
        theme = (TextView) findViewById(R.id.detail_news_theme);
        toolbarText = (TextView) findViewById(R.id.detail_news_like_toolbar_text);
        back = (LinearLayout) findViewById(R.id.detail_news_like_layoutBack);
        comment = (EditText) findViewById(R.id.detail_news_coment);
        finalDate = (TextView) findViewById(R.id.detail_news_like_days_left);
        description = (TextView) findViewById(R.id.detail_news_like_full_description);
        paymentAccount = (TextView) findViewById(R.id.detail_news_like_expected_amount);
        expectedAmount = (TextView) findViewById(R.id.detail_news_like_summa);
        dimensions = new Dimensions();
        Glide.with(getApplicationContext()).load(nImage).placeholder(R.drawable.no_donload_image).error(R.drawable.nointernet).override(dimensions.getWidth(DetailNewsLike.this), 550).centerCrop().into(image);
        expectedAmount.setText("Необходимо: " + nexpectedAmount);
        description.setText(nDescription);
        finalDate.setText("до:  " + nfinalDate);
        shortDescription.setText(nshortDescription);
        paymentAccount.setText("Расчетный счет:  " + nPaymentAccount);
        back.setOnClickListener(this);
        fonts();

    }


    private void getComment() {
        Retrofit.getCommentInformation(nIdNews, new Callback<List<ComentInformation>>() {
            @Override
            public void success(final List<ComentInformation> comentInformations, Response response) {
                if (comentInformations == null) {
                    Toast.makeText(DetailNewsLike.this, R.string.error_data_from_server, Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = comentInformations.size() - 1; i >= 0; i--) {
                        commentList.add(new ComentInformation(comentInformations.get(i).full_name,
                                comentInformations.get(i).avatar, comentInformations.get(i).comment,
                                comentInformations.get(i).created_at));
//                        recyclerView.setAdapter(comentAdapter);
//                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    }
                }


            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }


    @Override
    public void fonts() {
        Typeface mtypeface = Typeface.createFromAsset(context.getAssets(), "GothamProMedium.ttf");
        shortDescription.setTypeface(mtypeface);
        finalDate.setTypeface(mtypeface);
        description.setTypeface(mtypeface);
        paymentAccount.setTypeface(mtypeface);
        expectedAmount.setTypeface(mtypeface);
        toolbarText.setTypeface(mtypeface);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_news_like_layoutBack:
                finish();
                break;
        }
    }
}


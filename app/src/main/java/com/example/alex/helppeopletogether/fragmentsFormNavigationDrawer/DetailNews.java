package com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.alex.helppeopletogether.R;
import com.example.alex.helppeopletogether.SupportClasses.Dimensions;


public class DetailNews extends Activity {
    private String NImage, NshortDescription, NexpectedAmount, NfinalDate, NDescription, NPaymentAccount;
    private ImageView image;
    private TextView shortDescription, theme, expectedAmount, finalDate, description, paymentAccount;
    private Dimensions dimensions;
    private CollapsingToolbarLayout collapsingToolbar;
    private Toolbar toolbar;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_news);
        Intent intent = getIntent();
        NImage = intent.getStringExtra("image");
        NshortDescription = intent.getStringExtra("shortDescription");
        NexpectedAmount = intent.getStringExtra("expectedAmount");
        NfinalDate = intent.getStringExtra("finalDate");
        NDescription = intent.getStringExtra("description");
        NPaymentAccount = intent.getStringExtra("paymentAccount");
        image = (ImageView) findViewById(R.id.detail_news_image);
        shortDescription = (TextView) findViewById(R.id.detail_news_theme);
        //TextView title = (TextView) findViewById(R.id.toolbar_description_tit);
        theme = (TextView) findViewById(R.id.detail_news_theme);
        expectedAmount = (TextView) findViewById(R.id.detail_news_summa);
        finalDate = (TextView) findViewById(R.id.detail_news_days_left);
        description = (TextView) findViewById(R.id.detail_news_full_description);
        paymentAccount = (TextView) findViewById(R.id.detail_news_expected_amount);
        dimensions = new Dimensions();
        Glide.with(getApplicationContext()).load(NImage).placeholder(R.drawable.no_donload_image).error(R.drawable.nointernet).override(dimensions.getWidth(DetailNews.this), 550).centerCrop().into(image);
        expectedAmount.setText("необходимо:  " + NexpectedAmount);
        description.setText(NDescription);
        finalDate.setText("до:  " + NfinalDate);
        paymentAccount.setText("Расчетный счет:  " + NPaymentAccount);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.main_collapsing);
        collapsingToolbar.setTitle("Запись");


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_36dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }


}


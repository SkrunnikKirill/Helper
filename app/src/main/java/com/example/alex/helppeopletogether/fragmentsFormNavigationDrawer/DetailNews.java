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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_news);
        Intent intent = getIntent();
        String NImage = intent.getStringExtra("image");
        String NshortDescription = intent.getStringExtra("shortDescription");
        String NexpectedAmount = intent.getStringExtra("expectedAmount");
        String NfinalDate = intent.getStringExtra("finalDate");
        String NDescription = intent.getStringExtra("description");
        String NPaymentAccount = intent.getStringExtra("paymentAccount");
        ImageView image = (ImageView) findViewById(R.id.detail_news_image);
        TextView shortDescription = (TextView) findViewById(R.id.detail_news_theme);
        //TextView title = (TextView) findViewById(R.id.toolbar_description_tit);
        TextView theme = (TextView) findViewById(R.id.detail_news_theme);
        TextView expectedAmount = (TextView) findViewById(R.id.detail_news_summa);
        TextView finalDate = (TextView) findViewById(R.id.detail_news_days_left);
        TextView description = (TextView) findViewById(R.id.detail_news_full_description);
        TextView paymentAccount = (TextView) findViewById(R.id.detail_news_expected_amount);
        Dimensions dimensions = new Dimensions();
        Glide.with(getApplicationContext()).load(NImage).placeholder(R.drawable.no_donload_image).error(R.drawable.nointernet).override(dimensions.getWidth(DetailNews.this), 500).centerCrop().into(image);
        expectedAmount.setText(NexpectedAmount);
        description.setText(NDescription);
        finalDate.setText(NfinalDate);
        paymentAccount.setText(NPaymentAccount);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.main_collapsing);
        collapsingToolbar.setTitle("Запись");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_36dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }


}


package com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.alex.helppeopletogether.R;
import com.example.alex.helppeopletogether.SupportClasses.Dimensions;


public class DetailNews extends Activity implements View.OnClickListener {


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
        ImageView image = (ImageView) findViewById(R.id.detail_news_image);
        TextView shortDescription = (TextView) findViewById(R.id.detail_news_theme);
        TextView title = (TextView) findViewById(R.id.toolbar_description_tit);
        ImageView backArrow = (ImageView) findViewById(R.id.toolbar_description_button_down);
        TextView theme = (TextView) findViewById(R.id.detail_news_theme);
        TextView expectedAmount = (TextView) findViewById(R.id.detail_news_summa);
        TextView finalDate = (TextView) findViewById(R.id.detail_news_days_left);
        TextView description = (TextView) findViewById(R.id.detail_news_full_description);
        Dimensions dimensions = new Dimensions();
        Glide.with(getApplicationContext()).load(NImage).placeholder(R.drawable.no_donload_image).error(R.drawable.nointernet).override(dimensions.getWidth(DetailNews.this), 500).centerCrop().into(image);
        expectedAmount.setText(NexpectedAmount);
        description.setText(NDescription);
        finalDate.setText(NfinalDate);
        title.setText("Запись");
        backArrow.setOnClickListener(this);
        shortDescription.setText(NshortDescription);

    }

    public int getWidth() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        return width;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_description_button_down:
                onBackPressed();
                break;
        }
    }
}


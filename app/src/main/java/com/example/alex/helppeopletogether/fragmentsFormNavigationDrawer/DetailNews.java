package com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer;


import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.alex.helppeopletogether.R;


public class DetailNews extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private ImageView backArrow;
    private TextView title;

    @Override
    protected void onStart() {
        super.onStart();
        toolbar = null;
        backArrow = null;
        title = null;
    }

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
        TextView shortDescription = (TextView) findViewById(R.id.description_problem_theme);
        title = (TextView) findViewById(R.id.toolbar_description_tit);
        backArrow = (ImageView) findViewById(R.id.toolbar_description_button_down);
        TextView theme = (TextView) findViewById(R.id.detail_news_theme);
        TextView expectedAmount = (TextView) findViewById(R.id.detail_news_summa);
        TextView finalDate = (TextView) findViewById(R.id.detail_news_days_left);
        TextView description = (TextView) findViewById(R.id.detail_news_full_description);
        Dimensions dimensions = new Dimensions();
        Glide.with(getApplicationContext()).load(NImage).override(dimensions.getWidth(DetailNews.this), 500).centerCrop().into(image);
        expectedAmount.setText(NexpectedAmount);
        description.setText(NDescription);
        finalDate.setText(NfinalDate);
        toolbar = (Toolbar) findViewById(R.id.toolbar_description);
        title.setText("Запись");
        setSupportActionBar(toolbar);
        backArrow.setOnClickListener(this);


//       shortDescription.setText(NshortDescription);

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


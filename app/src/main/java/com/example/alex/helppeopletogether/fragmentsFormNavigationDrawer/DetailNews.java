package com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.alex.helppeopletogether.R;


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
        ImageView image = (ImageView)findViewById(R.id.detail_news_image);
        TextView shortDescription = (TextView)findViewById(R.id.description_problem_theme);
        TextView expectedAmount = (TextView)findViewById(R.id.detail_news_summa);
        TextView finalDate = (TextView)findViewById(R.id.detail_news_days_left);
        TextView description = (TextView)findViewById(R.id.detail_news_full_description);
        Glide.with(getApplicationContext()).load(NImage).into(image);
//       shortDescription.setText(NshortDescription);
        expectedAmount.setText(NexpectedAmount);
        description.setText(NDescription);
        finalDate.setText(NfinalDate);
    }
}

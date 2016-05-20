package com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.alex.helppeopletogether.R;

import java.util.ArrayList;

public class CustomList extends ArrayAdapter<String>{

    private final Activity context;
    private final   ArrayList<String> shortDescription;
    private final  ArrayList<String> image;
    private final  ArrayList<String> datePublication;
    private final  ArrayList<String> expected_amount;
    private final  ArrayList<String> finalDate;
    public CustomList(Activity context,
                       ArrayList<String> shortDescription,   ArrayList<String> image,  ArrayList<String> datePublication,
                      ArrayList<String> expected_amount, ArrayList<String> finalDate) {
        super(context, R.layout.datail_news_item, shortDescription);
        this.context = context;
        this.shortDescription = shortDescription;
        this.image = image;
        this.datePublication = datePublication;
        this.expected_amount = expected_amount;
        this.finalDate = finalDate;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.datail_news_item, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.detail_news_theme);
        TextView timeDate = (TextView)rowView.findViewById(R.id.date_text);
        TextView date = (TextView)rowView.findViewById(R.id.detail_news_days_left);
        TextView summa = (TextView)rowView.findViewById(R.id.detail_news_summa);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.detail_news_image);
        timeDate.setText(datePublication.get(position));
        txtTitle.setText(shortDescription.get(position));
        summa.setText(expected_amount.get(position));
        date.setText(finalDate.get(position));
        Glide.with(context).load(image.get(position)).into(imageView);

        return rowView;
    }
}


package com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.bumptech.glide.Glide;
import com.example.alex.helppeopletogether.R;
import com.google.android.gms.wallet.fragment.Dimension;

import java.util.ArrayList;

public class CustomList extends ArrayAdapter<String> {

    private final Activity context;
    private final   ArrayList<String> shortDescription;
    private final  ArrayList<String> image;
    private final  ArrayList<String> datePublication;
    private final  ArrayList<String> expected_amount;
    private final  ArrayList<String> finalDate;
    private  ArrayList<Integer> idNews;








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
      //  this.idNews = idNews;

    }



    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.datail_news_item, parent, false);
        final ToggleButton like  = (ToggleButton)rowView.findViewById(R.id.datail_news_like);
        idNews = new ArrayList<Integer>();
        //idNews.add(0,0);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.detail_news_theme);
        TextView timeDate = (TextView)rowView.findViewById(R.id.date_text);
        final TextView test = (TextView)rowView.findViewById(R.id.detail_news_persent);
        final TextView date = (TextView)rowView.findViewById(R.id.detail_news_days_left);
        TextView summa = (TextView)rowView.findViewById(R.id.detail_news_summa);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.detail_news_image);
        timeDate.setText(datePublication.get(position));
        txtTitle.setText(shortDescription.get(position));
        summa.setText(expected_amount.get(position));
        date.setText(finalDate.get(position));
        Glide.with(context).load(image.get(position)).override(756,400).centerCrop().into(imageView);


        like.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    like.setTag(idNews);
                    like.setBackgroundResource(R.drawable.like);
                    idNews.add(position);
                   // test.setText(idNews.size());

                } else {
                    like.setBackgroundResource(R.drawable.nolike);
                    idNews.size();
                    for (int i = 0; i < idNews.size(); i++) {
                        if (idNews.get(i) == position) {
                            idNews.remove(i);
                            //test.setText(idNews.size());
                        }
                    }
                    //  idNews.remove(idNews.indexOf(position));
                }
            }


        });

        for (int i = 0; i < idNews.size(); i++) {
            if (idNews.get(i) == position){
                like.getText();
                like.setBackgroundResource(R.drawable.like);
            }
        }


        return rowView;
    }


}


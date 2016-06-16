package com.example.alex.helppeopletogether.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;


import com.bumptech.glide.Glide;
import com.example.alex.helppeopletogether.R;
import com.example.alex.helppeopletogether.SupportClasses.Dimensions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class CustomList extends ArrayAdapter<String> {
    private final Activity context;
    private final ArrayList<String> shortDescription;
    private final ArrayList<String> image;
    private final ArrayList<String> datePublication;
    private final ArrayList<String> expected_amount;
    private final ArrayList<String> finalDate;
    private ArrayList<Integer> likeNews;
    private ArrayList<Integer> idServerNews;
    private  ArrayList<Integer> idNews;




    public CustomList(Activity context,
                      ArrayList<String> shortDescription, ArrayList<String> image, ArrayList<String> datePublication,
                      ArrayList<String> expected_amount, ArrayList<String> finalDate, ArrayList<Integer> likeNews, ArrayList<Integer> idServerNews, ArrayList<Integer> idNews) {
        super(context, R.layout.datail_news_item, shortDescription);
        this.context = context;
        this.shortDescription = shortDescription;
        this.image = image;
        this.datePublication = datePublication;
        this.expected_amount = expected_amount;
        this.finalDate = finalDate;
        this.likeNews = likeNews;
        this.idServerNews = idServerNews;
        this.idNews = idNews;


    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // View rowView = convertView;
        // if (rowView == null) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.datail_news_item, parent, false);
        // }
        if (idNews == null) {
            // likeNews = new ArrayList<Integer>();
            idNews = new ArrayList<Integer>();
        }
        final ToggleButton like = (ToggleButton) rowView.findViewById(R.id.datail_news_like);


        TextView txtTitle = (TextView) rowView.findViewById(R.id.detail_news_theme);
        TextView timeDate = (TextView) rowView.findViewById(R.id.date_text);
        final TextView test = (TextView) rowView.findViewById(R.id.detail_news_persent);
        final TextView date = (TextView) rowView.findViewById(R.id.detail_news_days_left);
        TextView summa = (TextView) rowView.findViewById(R.id.detail_news_summa);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.detail_news_image);
        timeDate.setText(datePublication.get(position));
        txtTitle.setText(shortDescription.get(position));
        summa.setText(expected_amount.get(position));
        date.setText(finalDate.get(position));
        Dimensions dimensions = new Dimensions();
        Glide.with(context).load(image.get(position)).placeholder(R.drawable.no_donload_image).error(R.drawable.nointernet).override(dimensions.getWidth(getContext()), 400).centerCrop().into(imageView);

        if (likeNews.indexOf(idServerNews.get(position)) >= 0) {
            like.getText();
            like.setBackgroundResource(R.drawable.like);
        }


        like.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if ((isChecked) || true) {
                    if (likeNews.indexOf(idServerNews.get(position)) == -1) {
                        like.setBackgroundResource(R.drawable.like);
                        likeNews.add(idServerNews.get(position));
                    } else {
                        like.setBackgroundResource(R.drawable.nolike);
                        likeNews.remove(likeNews.indexOf(idServerNews.get(position)));

                    }
//                    for (int i = 0; i < likeNews.size() ; i++) {
//                        if (likeNews.get(i) == position) {
//                            likeNews.remove(i);
//                            idNews.remove(idServerNews.get(position));
//                        }
//                    }
                }
            }


        });

        for (int i = 0; i < likeNews.size(); i++) {
            if (likeNews.get(i) == position) {
                like.getText();
                like.setBackgroundResource(R.drawable.like);
            }
        }
//        for (int i = 0; i < likeNews.size(); i++) {
//            if (likeNews.get(i).equals(idServerNews.get(position))){
//
//            }
//        }



        return rowView;
    }

    public String getIdNewsJson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        String gsonIdNews = gson.toJson(likeNews);
        return gsonIdNews;

    }

    public Integer getLikeNewsItem(int i) {


        return likeNews.get(i);
    }

    public ArrayList<Integer> getLikeNews() {
        return likeNews;
    }



}

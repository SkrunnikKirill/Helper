package com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.alex.helppeopletogether.R;
import com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer.Dimensions;
import com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer.SelectedNews;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Alex on 02.06.2016.
 */
public class SelectedAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<SelectedNews> likeNews;

    public SelectedAdapter(Context context, ArrayList<SelectedNews> likeNews) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.likeNews = likeNews;
    }

    @Override
    public int getCount() {
        return likeNews.size();
    }

    @Override
    public Object getItem(int position) {
        return likeNews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            view = layoutInflater.inflate(R.layout.datail_news_item,parent,false);
        }
        SelectedNews selectedNews =getSelectedNews(position);
        TextView txtTitle = (TextView) view.findViewById(R.id.detail_news_theme);
        TextView timeDate = (TextView) view.findViewById(R.id.date_text);
        TextView test = (TextView) view.findViewById(R.id.detail_news_persent);
        TextView date = (TextView) view.findViewById(R.id.detail_news_days_left);
        TextView summa = (TextView) view.findViewById(R.id.detail_news_summa);
        ImageView imageView = (ImageView) view.findViewById(R.id.detail_news_image);
        timeDate.setText(selectedNews.created_at);
        txtTitle.setText(selectedNews.short_description);
        summa.setText(selectedNews.expected_amount);
        date.setText(selectedNews.final_date);
        Dimensions dimensions = new Dimensions();
        Glide.with(context).load(selectedNews.image).placeholder(R.drawable.no_donload_image).override(dimensions.getWidth(context), 400).centerCrop().into(imageView);
        return view;
    }

    SelectedNews getSelectedNews(int position){
        return ((SelectedNews)getItem(position));
    }


}

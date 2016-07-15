package com.example.alex.helppeopletogether.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.alex.helppeopletogether.R;
import com.example.alex.helppeopletogether.SupportClasses.Dimensions;
import com.example.alex.helppeopletogether.SupportClasses.SelectedNews;

import java.util.ArrayList;

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
        ViewHolder viewHolder;
        SelectedNews selectedNews = getSelectedNews(position);
        if (view == null){
            view = layoutInflater.inflate(R.layout.datail_news_item,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.txtTitle = (TextView) view.findViewById(R.id.detail_news_theme);
            // viewHolder.timeDate = (TextView) view.findViewById(R.id.date_text);
            viewHolder.date = (TextView) view.findViewById(R.id.detail_news_days_left);
            viewHolder.summa = (TextView) view.findViewById(R.id.detail_news_summa);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.detail_news_image);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

//        viewHolder.timeDate.setText(selectedNews.created_at);
        viewHolder.txtTitle.setText(selectedNews.short_description);
        viewHolder.summa.setText("необходимо:  " + selectedNews.expected_amount);
        viewHolder.date.setText("до:  " + selectedNews.final_date);

        Dimensions dimensions = new Dimensions();
        Glide.with(context).load(selectedNews.image).placeholder(R.drawable.no_donload_image).error(R.drawable.nointernet).override(dimensions.getWidth(context), 550).centerCrop().into(viewHolder.imageView);


        return view;
    }

    SelectedNews getSelectedNews(int position){
        return ((SelectedNews)getItem(position));
    }

    static class ViewHolder {
        TextView txtTitle;
        TextView timeDate;
        TextView date;
        TextView summa;
        ImageView imageView;

    }



}

package com.example.alex.helppeopletogether.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.example.alex.helppeopletogether.R;
import com.example.alex.helppeopletogether.SupportClasses.CustomImageView;
import com.example.alex.helppeopletogether.SupportClasses.Dimensions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class CustomList extends ArrayAdapter<String> {
    Dimensions dimensions;
    String idUser;
    LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<String> shortDescription;
    private ArrayList<String> image;
    private ArrayList<String> datePublication;
    private ArrayList<String> expected_amount;
    private ArrayList<String> finalDate;
    private ArrayList<Integer> likeNews;
    private ArrayList<Integer> idServerNews;
    private ArrayList<Integer> idNews;
    private ArrayList<Integer> newsLike;

    public CustomList(Context context,
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
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        newsLike = new ArrayList<>();

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder viewHolder;
        dimensions = new Dimensions();
        if (view==null){
            view = layoutInflater.inflate(R.layout.datail_news_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.like = (ToggleButton) view.findViewById(R.id.datail_news_like);
            viewHolder.txtTitle = (TextView) view.findViewById(R.id.detail_news_theme);
            // viewHolder.timeDate = (TextView) rowView.findViewById(R.id.date_text);
            viewHolder.date = (TextView) view.findViewById(R.id.detail_news_days_left);
            viewHolder.summa = (TextView) view.findViewById(R.id.detail_news_summa);
            viewHolder.imageView = (CustomImageView) view.findViewById(R.id.login_cover_image);
            Typeface mtypeface = Typeface.createFromAsset(context.getAssets(), "GothamProMedium.ttf");
            viewHolder.txtTitle.setTypeface(mtypeface);
            viewHolder.date.setTypeface(mtypeface);
            viewHolder.summa.setTypeface(mtypeface);
            view.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        if (idNews == null) {
            likeNews = new ArrayList<Integer>();
            idNews = new ArrayList<Integer>();
        }

//        viewHolder.timeDate.setText(datePublication.get(position));
        viewHolder.txtTitle.setText(shortDescription.get(position));
        viewHolder.summa.setText(expected_amount.get(position));
        viewHolder.date.setText("до:  " + finalDate.get(position));

        Glide.with(context).load(image.get(position)).placeholder(R.drawable.no_donload_image).error(R.drawable.nointernet).override(dimensions.getWidth(getContext()), 550).centerCrop().crossFade().into(viewHolder.imageView);


            if (likeNews.indexOf(idServerNews.get(position))>=0) {
                viewHolder.like.getText();
                viewHolder.like.setBackgroundResource(R.drawable.like);
                Log.d("LikeNEWS", "getView1: "+idServerNews.get(position));
            }else{
                viewHolder.like.getText();
                viewHolder.like.setBackgroundResource(R.drawable.dislike);
                Log.d("DisLikeNEWS", "getView2: "+idServerNews.get(position));
            }



        viewHolder.like.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if ((isChecked) || true) {
                    if (likeNews.indexOf(idServerNews.get(position)) == -1) {
                        viewHolder.like.setBackgroundResource(R.drawable.like);
                        likeNews.add(idServerNews.get(position));
                        Log.d("AddLikeNEWS", "getView3: "+idServerNews.get(position));
                    } else {
                        viewHolder.like.setBackgroundResource(R.drawable.dislike);
                        likeNews.remove(likeNews.indexOf(idServerNews.get(position)));
                        Log.d("RemoveLikeNEWS", "getView4: "+idServerNews.get(position));
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

//        for (int i = 0; i < likeNews.size(); i++) {
//            if (likeNews.get(i)==position) {
//                viewHolder.like.getText();
//                viewHolder.like.setBackgroundResource(R.drawable.like);
//                Log.d("InstallLikeNEWS", "getView5: "+idServerNews.get(position));
//            }
//        }


//        for (int i = 0; i < likeNews.size(); i++) {
//            if (likeNews.get(i).equals(idServerNews.get(position))){
//
//            }
//        }


        return view;
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

    static class ViewHolder {
        TextView txtTitle;
        TextView date;
        TextView summa;
        CustomImageView imageView;
        ToggleButton like;

    }


}
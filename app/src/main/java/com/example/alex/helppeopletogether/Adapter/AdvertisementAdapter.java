package com.example.alex.helppeopletogether.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.alex.helppeopletogether.R;
import com.example.alex.helppeopletogether.SupportClasses.Dimensions;

import java.util.ArrayList;

/**
 * Created by Alex on 27.05.2016.
 */
public class AdvertisementAdapter  extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> shortDescription;
    private final  ArrayList<String> image;
    private final  ArrayList<String> datePublication;
    private final  ArrayList<String> expected_amount;
    private final  ArrayList<String> finalDate;



    public AdvertisementAdapter(Activity context,
                      ArrayList<String> shortDescription,   ArrayList<String> image,  ArrayList<String> datePublication,
                      ArrayList<String> expected_amount, ArrayList<String> finalDate) {
        super(context, R.layout.advertisement_adapter, shortDescription);
        this.context = context;
        this.shortDescription = shortDescription;
        this.image = image;
        this.datePublication = datePublication;
        this.expected_amount = expected_amount;
        this.finalDate = finalDate;
    }



    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.advertisement_adapter, parent, false);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.advertisement_adapter_theme);
        TextView timeDate = (TextView)rowView.findViewById(R.id.advertisement_adapter_date_text);

        TextView date = (TextView)rowView.findViewById(R.id.advertisement_adapter_days_left);
        TextView summa = (TextView)rowView.findViewById(R.id.advertisement_adapter_summa);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.advertisement_adapter_image);
        timeDate.setText(datePublication.get(position));
        txtTitle.setText(shortDescription.get(position));
        summa.setText(expected_amount.get(position));
        date.setText(finalDate.get(position));
        Dimensions dimensions = new Dimensions();
        Glide.with(context).load(image.get(position)).placeholder(R.drawable.no_donload_image).error(R.drawable.nointernet).override(dimensions.getWidth(getContext()), 400).centerCrop().into(imageView);
        return rowView;
    }




}


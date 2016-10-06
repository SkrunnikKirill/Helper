package com.example.alex.helppeopletogether.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.alex.helppeopletogether.R;
import com.example.alex.helppeopletogether.SupportClasses.Constant;
import com.example.alex.helppeopletogether.SupportClasses.Dimensions;
import com.example.alex.helppeopletogether.SupportClasses.Preferences;
import com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer.EditAdvertisement;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

/**
 * Created by Alex on 27.05.2016.
 */
public class AdvertisementAdapter extends ArrayAdapter<String> implements Constant {

    Preferences preferences;
    String idUser;
    Intent intent;
    LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<String> shortDescription;
    private ArrayList<String> image;
    private ArrayList<String> datePublication;
    private ArrayList<String> expected_amount;
    private ArrayList<String> finalDate;
    private ArrayList<Integer> id;


    public AdvertisementAdapter(Context context,
                                ArrayList<String> shortDescription, ArrayList<String> image, ArrayList<String> datePublication,
                                ArrayList<String> expected_amount, ArrayList<String> finalDate, ArrayList<Integer> id) {
        super(context, R.layout.advertisement_adapter, shortDescription);
        this.context = context;
        this.shortDescription = shortDescription;
        this.image = image;
        this.datePublication = datePublication;
        this.expected_amount = expected_amount;
        this.finalDate = finalDate;
        this.id = id;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        preferences = new Preferences(context);
        idUser = preferences.loadText(PREFERENCES_ID);
        View rowView = view;
        ViewHolder viewHolder;
        if (rowView == null) {
            rowView = layoutInflater.inflate(R.layout.advertisement_adapter, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.txtTitle = (TextView) rowView.findViewById(R.id.advertisement_adapter_theme);
            viewHolder.edit = (Button) rowView.findViewById(R.id.advertisement_adapter_edit);
            //viewHolder.timeDate = (TextView) rowView.findViewById(R.id.advertisement_adapter_date_text);
            viewHolder.date = (TextView) rowView.findViewById(R.id.advertisement_adapter_days_left);
            viewHolder.summa = (TextView) rowView.findViewById(R.id.advertisement_adapter_summa);
            viewHolder.imageView = (RoundedImageView) rowView.findViewById(R.id.advertisement_adapter_image);
            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }

        //viewHolder.timeDate.setText(datePublication.get(position));
        viewHolder.txtTitle.setText(shortDescription.get(position));
        viewHolder.summa.setText("необходимо:  " + expected_amount.get(position));
        viewHolder.date.setText("до:  " + finalDate.get(position));
        Dimensions dimensions = new Dimensions();
        Glide.with(context).load(image.get(position)).placeholder(R.drawable.no_donload_image).error(R.drawable.nointernet).override(dimensions.getWidth(getContext()), 550).centerCrop().into(viewHolder.imageView);
        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferences.saveText(id.get(position).toString(), PREFERENCES_ID_NEWS);
                intent = new Intent(view.getContext(), EditAdvertisement.class);
                view.getContext().startActivity(intent);


            }
        });
        return rowView;
    }

    static class ViewHolder {
        TextView txtTitle;
        TextView timeDate;
        TextView date;
        TextView summa;
        RoundedImageView imageView;
        Button edit;

    }


}


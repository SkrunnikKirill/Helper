package com.example.alex.helppeopletogether.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;

import com.example.alex.helppeopletogether.R;

/**
 * Created by Кирилл on 01.07.2016.
 */
public class CustomAdapter extends BaseAdapter {
    Context context;
    int flags[];
    String[] countryNames;
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, int[] flags, String[] countryNames) {
        this.context = applicationContext;
        this.flags = flags;
        this.countryNames = countryNames;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return flags.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        ViewHolder viewHolder;
        if (view == null){
            viewHolder = new ViewHolder();
            view = inflter.inflate(R.layout.custom_spinner_irems, null);
            viewHolder.icon = (ImageView) view.findViewById(R.id.imageView12);
             viewHolder.names = (TextView) view.findViewById(R.id.textView12);
            Typeface mtypeface = Typeface.createFromAsset(context.getAssets(), "GothamProMedium.ttf");
            viewHolder.names.setTypeface(mtypeface);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)view.getTag();
        }



        viewHolder.icon.setImageResource(flags[position]);
        viewHolder.names.setText(countryNames[position]);
        return view;
    }

    static class ViewHolder{
        ImageView icon;
        TextView names;

    }
}

package com.example.alex.helppeopletogether.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.alex.helppeopletogether.R;
import com.example.alex.helppeopletogether.SupportClasses.ComentInformation;
import com.example.alex.helppeopletogether.SupportClasses.Dimensions;

import java.util.ArrayList;

/**
 * Created by Alex on 26.07.2016.
 */
public class ComentAdapter extends RecyclerView.Adapter<ComentAdapter.MyViewHolder> {
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<ComentInformation> coment;
    public ComentAdapter(Context context, ArrayList<ComentInformation> coment) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        //layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.coment = coment;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.coment_adapter, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

//    @Override
//    public int getCount() {
//        return coment.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return coment.get(position);
//    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ComentInformation comentInformation = coment.get(position);
        holder.fullName.setText(comentInformation.full_name);
        holder.userComent.setText(comentInformation.comment);
        Glide.with(context).load(comentInformation.avatar).placeholder(R.drawable.no_donload_image).error(R.drawable.nointernet).override(60, 60).centerCrop().into(holder.foto);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return coment.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView fullName;
        TextView userComent;
        ImageView foto;

        public MyViewHolder(View itemView) {
            super(itemView);
            fullName = (TextView) itemView.findViewById(R.id.coment_adapter_full_name);
            userComent = (TextView) itemView.findViewById(R.id.coment_adapter_coment);
            foto = (ImageView) itemView.findViewById(R.id.coment_adapter_foto);
        }
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View view = convertView;
//        ViewHolder viewHolder;
//        ComentInformation comentInformation = getComent(position);
//        if (view == null) {
//            view = layoutInflater.inflate(R.layout.coment_adapter, parent, false);
//            viewHolder = new ViewHolder();
//            viewHolder.fullName = (TextView) view.findViewById(R.id.coment_adapter_full_name);
//            viewHolder.userComent = (TextView) view.findViewById(R.id.coment_adapter_coment);
//            viewHolder.foto = (ImageView) view.findViewById(R.id.coment_adapter_foto);
//            view.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) view.getTag();
//        }
//        viewHolder.fullName.setText(comentInformation.full_name);
//        viewHolder.userComent.setText(comentInformation.comment);
//        Glide.with(context).load(comentInformation.avatar).placeholder(R.drawable.no_donload_image).error(R.drawable.nointernet).override(60, 60).centerCrop().into(viewHolder.foto);
//
//        return view;
//    }

//    ComentInformation getComent(int position) {
//        return ((ComentInformation) getItem(position));
//    }
//
//    static class ViewHolder {
//        TextView fullName;
//        TextView userComent;
//        ImageView foto;
//    }
}

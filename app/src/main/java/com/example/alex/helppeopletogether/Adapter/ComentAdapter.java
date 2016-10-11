package com.example.alex.helppeopletogether.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.alex.helppeopletogether.R;
import com.example.alex.helppeopletogether.SupportClasses.ComentInformation;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

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
        this.coment = coment;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.coment_adapter, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }



    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ComentInformation comentInformation = coment.get(position);
        holder.fullName.setText(comentInformation.full_name);
        holder.userComent.setText(comentInformation.comment);
        holder.commentTime.setText(comentInformation.created_at);
        Glide.with(context).load(comentInformation.avatar).into(holder.foto);
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
        TextView userComent, fullName, commentTime;
        CircleImageView foto;

        public MyViewHolder(View itemView) {
            super(itemView);
            fullName = (TextView) itemView.findViewById(R.id.coment_adapter_full_name);
            userComent = (TextView) itemView.findViewById(R.id.coment_adapter_coment);
            commentTime = (TextView) itemView.findViewById(R.id.coment_adapter_time);
            foto = (CircleImageView) itemView.findViewById(R.id.coment_adapter_foto);
        }
    }

}

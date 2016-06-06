package com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alex.helppeopletogether.R;
import com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer.Adapter.SelectedAdapter;
import com.example.alex.helppeopletogether.registration.Login;
import com.example.alex.helppeopletogether.retrofit.RegistrationResponseFromServer;
import com.example.alex.helppeopletogether.retrofit.Retrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Alex on 04.04.2016.
 */
public class MyAdvertisement extends Fragment {
    String userId;
    ArrayList<SelectedNews> likeNews;
    ListView list;
    SelectedAdapter selectedAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.my_advertisement, container, false);
        Login login = new Login();
        likeNews = new ArrayList<SelectedNews>();
        selectedAdapter = new SelectedAdapter(getActivity(),likeNews);
        userId = String.valueOf(login.userId);
        list = (ListView) root.findViewById(R.id.like_list);
        Retrofit.getSelectedNews(userId, new Callback<List<SelectedNews>>() {
            @Override
            public void success(final List<SelectedNews> selectedNews, Response response) {
                for (int i = 0; i < selectedNews.size(); i++) {
                    likeNews.add(new SelectedNews(selectedNews.get(i).created_at,selectedNews.get(i).title,
                            selectedNews.get(i).short_description,selectedNews.get(i).description,
                            selectedNews.get(i).image,selectedNews.get(i).expected_amount,selectedNews.get(i).final_date,
                            selectedNews.get(i).id));
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String detailNewsImage = selectedNews.get(position).image;
                            String detailNewsShortDescription = selectedNews.get(position).short_description;
                            String detailNewsExpectedAmount = selectedNews.get(position).expected_amount;
                            String detailNewsFinalDate = selectedNews.get(position).final_date;
                            String detailNewsDescription = selectedNews.get(position).description;
                            Intent news = new Intent(getActivity(), DetailNews.class);
                            news.putExtra("image", detailNewsImage);
                            news.putExtra("shortDescription", detailNewsShortDescription);
                            news.putExtra("expectedAmount", detailNewsExpectedAmount);
                            news.putExtra("finalDate", detailNewsFinalDate);
                            news.putExtra("description", detailNewsDescription);
                            startActivity(news);
                        }
                    });
                    list.setAdapter(selectedAdapter);
                }


            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
        return root;
    }



}

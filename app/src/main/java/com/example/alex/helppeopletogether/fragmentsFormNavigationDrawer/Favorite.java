package com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.alex.helppeopletogether.Adapter.SelectedAdapter;
import com.example.alex.helppeopletogether.R;
import com.example.alex.helppeopletogether.SupportClasses.ConstantPreferences;
import com.example.alex.helppeopletogether.SupportClasses.InternetCheck;
import com.example.alex.helppeopletogether.SupportClasses.Preferences;
import com.example.alex.helppeopletogether.SupportClasses.ProDialog;
import com.example.alex.helppeopletogether.SupportClasses.SelectedNews;
import com.example.alex.helppeopletogether.retrofit.Retrofit;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Alex on 04.04.2016.
 */
public class Favorite extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ConstantPreferences {
    String userId;
    ArrayList<SelectedNews> likeNews;
    ListView list;
    SelectedAdapter selectedAdapter;
    FragmentManager fm;
    NoLikeData noLikeData;
    DonloadInformationFromServer donloadInformationFromServer;
    ProDialog prodialog;
    SwipeRefreshLayout swipeRefreshLayout;
    Context context;
    Preferences preferences;
    InternetCheck internet;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.my_advertisement, container, false);
        list = (ListView) root.findViewById(R.id.like_list);
        swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.my_advertisement_swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(this);

        return root;
    }

    public void getLikeInformations() {
        donloadInformationFromServer = new DonloadInformationFromServer();
        donloadInformationFromServer.execute();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        likeNews = new ArrayList<>();
        prodialog = new ProDialog();
        preferences = new Preferences(context);
        userId = preferences.loadText(PREFERENCES_ID);
        getLikeInformations();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
//        selectedAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                getLikeInformations();

            }
        }, 4000);
    }

    public void getLikeNewsFromServer() {
        Retrofit.getSelectedNews(userId, new Callback<List<SelectedNews>>() {
            @Override
            public void success(final List<SelectedNews> selectedNews, Response response) {
                if (selectedNews.size() == 0) {
                    fm = getActivity().getSupportFragmentManager();
                    fm.beginTransaction().replace(R.id.container, noLikeData).commit();
                    Toast.makeText(context, "У вас нет избранных новостей", Toast.LENGTH_LONG).show();
                    prodialog.connectionProgressBar();

                } else {
                    for (int i = 0; i < selectedNews.size(); i++) {
                        likeNews.add(new SelectedNews(selectedNews.get(i).created_at, selectedNews.get(i).title,
                                selectedNews.get(i).short_description, selectedNews.get(i).description,
                                selectedNews.get(i).image, selectedNews.get(i).expected_amount, selectedNews.get(i).final_date,
                                selectedNews.get(i).id, selectedNews.get(i).payment_account));
                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String detailNewsImage = selectedNews.get(position).image;
                                String detailNewsShortDescription = selectedNews.get(position).short_description;
                                String detailNewsExpectedAmount = selectedNews.get(position).expected_amount;
                                String detailNewsFinalDate = selectedNews.get(position).final_date;
                                String detailNewsDescription = selectedNews.get(position).description;
                                String detailNewsPaymentAccount = selectedNews.get(position).payment_account;
                                Intent news = new Intent(getActivity(), DetailNews.class);
                                news.putExtra("image", detailNewsImage);
                                news.putExtra("shortDescription", detailNewsShortDescription);
                                news.putExtra("expectedAmount", detailNewsExpectedAmount);
                                news.putExtra("finalDate", detailNewsFinalDate);
                                news.putExtra("description", detailNewsDescription);
                                news.putExtra("paymentAccount", detailNewsPaymentAccount);
                                startActivity(news);
                            }
                        });
                        list.setAdapter(selectedAdapter);
                        prodialog.connectionProgressBar();
                    }
                }


            }

            @Override
            public void failure(RetrofitError error) {
                if (error.getCause() instanceof UnknownHostException) {
                    internet = new InternetCheck(context, swipeRefreshLayout);
                    internet.execute();
                    prodialog.connectionProgressBar();
                }
            }
        });
    }


    class DonloadInformationFromServer extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            prodialog.defenitionProgressBar(context);

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            likeNews = new ArrayList<SelectedNews>();
            selectedAdapter = new SelectedAdapter(context, likeNews);
            noLikeData = new NoLikeData();
            getLikeNewsFromServer();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }

}



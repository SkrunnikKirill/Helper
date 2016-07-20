package com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.alex.helppeopletogether.Adapter.AdvertisementAdapter;
import com.example.alex.helppeopletogether.R;
import com.example.alex.helppeopletogether.SupportClasses.ConstantPreferences;
import com.example.alex.helppeopletogether.SupportClasses.InternetCheck;
import com.example.alex.helppeopletogether.SupportClasses.Preferences;
import com.example.alex.helppeopletogether.SupportClasses.ProDialog;
import com.example.alex.helppeopletogether.retrofit.RegistrationResponseFromServer;
import com.example.alex.helppeopletogether.retrofit.Retrofit;

import java.net.UnknownHostException;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by User on 29.03.2016.
 */
public class Advertisement extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ConstantPreferences {
    public ArrayList<String> datePublication;
    public ArrayList<String> title;
    public ArrayList<String> shortDescription;
    public ArrayList<String> description;
    public ArrayList<String> image;
    public ArrayList<String> expectedAmount;
    public ArrayList<String> finalDate;
    public ArrayList<Integer> idNews;
    public ArrayList<String> paymentAccount;
    ListView list;
    String idUser;
    FragmentManager fm;
    FragmentTransaction ft;
    NoLikeData noLikeData;
    ProDialog proDialog;
    Preferences preferences;
    Context context;
    InternetCheck internet;
    private AdvertisementAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        proDialog = new ProDialog();
        proDialog.defenitionProgressBar(context);
        fm = getActivity().getSupportFragmentManager();
        noLikeData = new NoLikeData();
        preferences = new Preferences(context);
        idUser = preferences.loadText(PREFERENCES_ID);
        datePublication = new ArrayList<>();
        title = new ArrayList<>();
        shortDescription = new ArrayList<>();
        description = new ArrayList<>();
        image = new ArrayList<>();
        expectedAmount = new ArrayList<>();
        finalDate = new ArrayList<>();
        idNews = new ArrayList<>();
        paymentAccount = new ArrayList<>();

        getAdvertisementFromTheServer();

    }

    private void getAdvertisementFromTheServer() {
        Retrofit.getMyAdvertisementArrays(idUser, new Callback<RegistrationResponseFromServer>() {
            @Override
            public void success(RegistrationResponseFromServer registrationResponseFromServer, Response response) {
                if (registrationResponseFromServer.final_date.size() == 1 &&
                        registrationResponseFromServer.expected_amount.size() == 1 &&
                        registrationResponseFromServer.description.size() == 1 &&
                        registrationResponseFromServer.short_description.size() == 1 &&
                        registrationResponseFromServer.description.size() == 1 &&
                        registrationResponseFromServer.created_at.size() == 1 &&
                        registrationResponseFromServer.final_date.get(0).equals("0") &&
                        registrationResponseFromServer.image.get(0).equals("0") &&
                        registrationResponseFromServer.expected_amount.get(0).equals("0") &&
                        registrationResponseFromServer.short_description.get(0).equals("0") &&
                        registrationResponseFromServer.description.get(0).equals("0") &&
                        registrationResponseFromServer.created_at.get(0).equals("0")) {
                    fm.beginTransaction().replace(R.id.container, noLikeData).commit();
                    proDialog.connectionProgressBar();
                } else {
                    shortDescription = registrationResponseFromServer.short_description;
                    description = registrationResponseFromServer.description;
                    datePublication = registrationResponseFromServer.created_at;
                    image = registrationResponseFromServer.image;
                    expectedAmount = registrationResponseFromServer.expected_amount;
                    finalDate = registrationResponseFromServer.final_date;
                    idNews = registrationResponseFromServer.id;
                    paymentAccount = registrationResponseFromServer.payment_account;
                    adapter();
                    proDialog.connectionProgressBar();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (error.getCause() instanceof UnknownHostException) {
                    internet = new InternetCheck(context, swipeRefreshLayout);
                    internet.execute();
                    proDialog.connectionProgressBar();
                }
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.advertisement, container, false);
        list = (ListView) root.findViewById(R.id.my_advertisement_list);
        swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.advertisement_swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(this);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void adapter() {
        adapter = new
                AdvertisementAdapter(context, shortDescription, image, datePublication, expectedAmount, finalDate, idNews);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String detailNewsImage = image.get(position);
                String detailNewsShortDescription = shortDescription.get(position);
                String detailNewsExpectedAmount = expectedAmount.get(position);
                String detailNewsFinalDate = finalDate.get(position);
                String detailNewsDescription = description.get(position);
                String detailNewsPaymentAccount = paymentAccount.get(position).toString();
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
        list.setAdapter(adapter);
    }


    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);

                getAdvertisementFromTheServer();

            }
        }, 4000);
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}

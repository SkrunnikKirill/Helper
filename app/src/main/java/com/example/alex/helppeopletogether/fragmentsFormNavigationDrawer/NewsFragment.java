package com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.alex.helppeopletogether.Adapter.CustomList;
import com.example.alex.helppeopletogether.R;
import com.example.alex.helppeopletogether.SupportClasses.ProDialog;
import com.example.alex.helppeopletogether.registration.Login;
import com.example.alex.helppeopletogether.registration.Registration;
import com.example.alex.helppeopletogether.retrofit.RegistrationResponseFromServer;
import com.example.alex.helppeopletogether.retrofit.Retrofit;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;




public class NewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    public ArrayList<Integer> idServerNews;
    public ArrayList<String> datePublication;
    public ArrayList<String> title;
    public ArrayList<String> shortDescription;
    public ArrayList<String> description;
    public ArrayList<String> image;
    public ArrayList<String> expectedAmount;
    public ArrayList<String> finalDate;
    public ArrayList<Integer> likeNews;
    public ArrayList<String> paymentAccount;
    public ArrayList<Integer> idNews;
    public ArrayList<Integer> likeNewsFromServer;
    CustomList adapter;
    ProDialog proDialog;
    NewsFragment newsFragment;
    private ListView list;
    private String userId;
    private Login login;
    private Registration registration;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newsitem_list, container, false);

        list = (ListView) view.findViewById(R.id.list);
        login = new Login();
        newsFragment = new NewsFragment();
        registration = new Registration();
        proDialog = new ProDialog();

        proDialog.defenitionProgressBar(getActivity());
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        idNews = new ArrayList<>();
        swipeRefreshLayout.setOnRefreshListener(this);


        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (login.userId != null) {
            userId = String.valueOf(login.userId);
        } else if (registration.responseFromServiseRegistrationId != null) {
            userId = String.valueOf(registration.responseFromServiseRegistrationId);
        }
//        } if (savedInstanceState!=null){
//            userId = savedInstanceState.getString("userId");
//            idServerNews.clear();
//            datePublication.clear();
//            title.clear();
//            shortDescription.clear();
//            description.clear();
//            image.clear();
//            expectedAmount.clear();
//            finalDate.clear();
//            paymentAccount.clear();
//            likeNews.clear();
//            idServerNews.clear();
//            idNews.clear();
//        }

        idServerNews = new ArrayList<>();
        datePublication = new ArrayList<>();
        title = new ArrayList<>();
        shortDescription = new ArrayList<>();
        description = new ArrayList<>();
        image = new ArrayList<>();
        expectedAmount = new ArrayList<>();
        finalDate = new ArrayList<>();
        paymentAccount = new ArrayList<>();
        likeNews = new ArrayList<>();
        idNews = new ArrayList<>();
        idServerNews = new ArrayList<>();
        likeNewsFromServer = new ArrayList<>();
        newsInformationFromServer();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("userId", userId);
        outState.putStringArrayList("datePublication", datePublication);
        outState.putStringArrayList("title", title);
        outState.putStringArrayList("shortDescription", shortDescription);
        outState.putStringArrayList("description", description);
        outState.putStringArrayList("image", image);
        outState.putStringArrayList("expectedAmount", expectedAmount);
        outState.putStringArrayList("finalDate", finalDate);
        outState.putStringArrayList("paymentAccount", paymentAccount);
        outState.putIntegerArrayList("idServerNews", idServerNews);
        outState.putIntegerArrayList("likeNewsFromServer", likeNewsFromServer);
        outState.putIntegerArrayList("likeNews", likeNews);
        outState.putIntegerArrayList("idNews", idNews);


    }


    private void newsInformationFromServer() {
        Retrofit.getArrays(userId, new Callback<RegistrationResponseFromServer>() {
            @Override
            public void success(RegistrationResponseFromServer registrationResponseFromServer, Response response) {
                likeNews = registrationResponseFromServer.liked_advers;
                likeNewsFromServer.addAll(likeNews);
                paymentAccount = registrationResponseFromServer.payment_account;
                idServerNews = registrationResponseFromServer.id;
                shortDescription = registrationResponseFromServer.short_description;
                description = registrationResponseFromServer.description;
                datePublication = registrationResponseFromServer.created_at;
                image = registrationResponseFromServer.image;
                expectedAmount = registrationResponseFromServer.expected_amount;
                finalDate = registrationResponseFromServer.final_date;

                if (likeNews.size() == 1 && likeNews.get(0) == 0) {
                    likeNews = new ArrayList<Integer>();
                    adapter();
                    proDialog.connectionProgressBar();
                } else {
                    adapter();
                    proDialog.connectionProgressBar();
                }

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }


    public void adapter() {
        adapter = new CustomList(getActivity(), shortDescription, image, datePublication, expectedAmount, finalDate, likeNews, idServerNews, idNews);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String detailNewsImage = image.get(position);
                String detailNewsShortDescription = shortDescription.get(position);
                String detailNewsExpectedAmount = expectedAmount.get(position);
                String detailNewsFinalDate = finalDate.get(position);
                String detailNewsDescription = description.get(position);
                String detailPaymentAccount = paymentAccount.get(position).toString();
                Intent news = new Intent(getActivity(), DetailNews.class);
                news.putExtra("image", detailNewsImage);
                news.putExtra("shortDescription", detailNewsShortDescription);
                news.putExtra("expectedAmount", detailNewsExpectedAmount);
                news.putExtra("finalDate", detailNewsFinalDate);
                news.putExtra("description", detailNewsDescription);
                news.putExtra("paymentAccount", detailPaymentAccount);

                startActivity(news);

            }
        });


        list.setAdapter(adapter);
    }

    private void sendServer() {
        Retrofit.getLike(userId, adapter.getIdNewsJson(), new Callback<RegistrationResponseFromServer>() {
            @Override
            public void success(RegistrationResponseFromServer registrationResponseFromServer, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }



    @Override
    public void onPause() {
        super.onPause();
        addLikeNewsToTheServer();


    }

    private void addLikeNewsToTheServer() {
        if (likeNewsFromServer.size() != 0 && adapter.getLikeNews().size() != 0 && likeNewsFromServer.size() == adapter.getLikeNews().size()) {
            for (int i = 0; i < likeNewsFromServer.size(); i++) {
                if (adapter.getLikeNews().contains(likeNewsFromServer.get(i)) != true) {
                    sendServer();
                }
            }
        } else {
            sendServer();
        }
    }


    @Override
    public void onRefresh() {
        addLikeNewsToTheServer();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                newsInformationFromServer();
            }
        }, 4000);
    }
}

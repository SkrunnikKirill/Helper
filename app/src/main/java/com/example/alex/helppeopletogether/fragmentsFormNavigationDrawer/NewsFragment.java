package com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer;


import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

//import com.example.alex.helppeopletogether.R;
//import com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer.NewsItem.DummyItem;
//import com.example.alex.helppeopletogether.navigationDrawer.NewsRecyclerViewAdapter;

import com.example.alex.helppeopletogether.R;
import com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer.Adapter.CustomList;
import com.example.alex.helppeopletogether.registration.Login;
import com.example.alex.helppeopletogether.registration.Registration;
import com.example.alex.helppeopletogether.retrofit.RegistrationResponseFromServer;
import com.example.alex.helppeopletogether.retrofit.Retrofit;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class NewsFragment extends Fragment {
    public ArrayList<Integer> idServerNews;
    //public ArrayList<Integer> likeNews;
    public ArrayList<String> datePublication;
    public ArrayList<String> title;
    public ArrayList<String> shortDescription;
    public ArrayList<String> description;
    public ArrayList<String> image;
    public ArrayList<String> expectedAmount;
    public ArrayList<String> finalDate;
    public ArrayList<Integer> likeNews;
    public ArrayList<Integer> idNews;
    ListView list;
    CustomList adapter;
    private String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newsitem_list, container, false);
        list = (ListView) view.findViewById(R.id.list);
        Login login = new Login();
        Registration registration = new Registration();



        if (login.userId!=null) {
            userId = String.valueOf(login.userId);
        }else if (registration.responseFromServiseRegistrationId!=null){
            userId = String.valueOf(registration.responseFromServiseRegistrationId);
        }
        idNews = new ArrayList<>();
        Retrofit.getArrays(userId, new Callback<RegistrationResponseFromServer>() {
            @Override
            public void success(RegistrationResponseFromServer registrationResponseFromServer, Response response) {
                likeNews = registrationResponseFromServer.liked_advers;
                idServerNews = registrationResponseFromServer.id;
                shortDescription = registrationResponseFromServer.short_description;
                description = registrationResponseFromServer.description;
                datePublication = registrationResponseFromServer.created_at;
                image = registrationResponseFromServer.image;
                expectedAmount = registrationResponseFromServer.expected_amount;
                finalDate = registrationResponseFromServer.final_date;
                adapter();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

        return view;
    }


    public void adapter() {
        adapter = new
                CustomList(getActivity(), shortDescription, image, datePublication, expectedAmount, finalDate, likeNews,idServerNews, idNews);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String detailNewsImage = image.get(position);
                String detailNewsShortDescription = shortDescription.get(position);
                String detailNewsExpectedAmount = expectedAmount.get(position);
                String detailNewsFinalDate = finalDate.get(position);
                String detailNewsDescription = description.get(position);
                Intent news = new Intent(getActivity(), DetailNews.class);
                news.putExtra("image", detailNewsImage);
                news.putExtra("shortDescription", detailNewsShortDescription);
                news.putExtra("expectedAmount", detailNewsExpectedAmount);
                news.putExtra("finalDate", detailNewsFinalDate);
                news.putExtra("description", detailNewsDescription);
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
        sendServer();

    }


}

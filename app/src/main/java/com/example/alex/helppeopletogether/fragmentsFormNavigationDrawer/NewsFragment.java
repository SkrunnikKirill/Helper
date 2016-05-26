package com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer;


import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

//import com.example.alex.helppeopletogether.R;
//import com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer.NewsItem.DummyItem;
//import com.example.alex.helppeopletogether.navigationDrawer.NewsRecyclerViewAdapter;

import com.example.alex.helppeopletogether.R;
import com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer.Adapter.CustomList;
import com.example.alex.helppeopletogether.retrofit.RegistrationResponseFromServer;
import com.example.alex.helppeopletogether.retrofit.Retrofit;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class NewsFragment extends Fragment  {
    public ArrayList<String> idUser;
    public ArrayList<String> datePublication;
    public ArrayList<String> title;
    public ArrayList<String> shortDescription;
    public ArrayList<String> description;
    public ArrayList<String> image;
    public ArrayList<String> expectedAmount;
    public ArrayList<String> finalDate;
    ListView list;






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newsitem_list, container, false);
        list = (ListView) view.findViewById(R.id.list);
       // idNews = new ArrayList<>();
        Retrofit.getArrays(new Callback<RegistrationResponseFromServer>() {
            @Override
            public void success(RegistrationResponseFromServer registrationResponseFromServer, Response response) {
                idUser = registrationResponseFromServer.id;
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
        CustomList adapter = new
                CustomList(getActivity(), shortDescription, image, datePublication, expectedAmount, finalDate);
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


}

package com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alex.helppeopletogether.R;
import com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer.Adapter.CustomList;
import com.example.alex.helppeopletogether.registration.Login;
import com.example.alex.helppeopletogether.retrofit.RegistrationResponseFromServer;
import com.example.alex.helppeopletogether.retrofit.Retrofit;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by User on 29.03.2016.
 */
public class Advertisement extends Fragment {
    public ArrayList<String> datePublication;
    public ArrayList<String> title;
    public ArrayList<String> shortDescription;
    public ArrayList<String> description;
    public ArrayList<String> image;
    public ArrayList<String> expectedAmount;
    public ArrayList<String> finalDate;
    ListView list;
    private TextView advertisementText;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.advertisement, container, false);


        Login login = new Login();
        String idUser = String.valueOf(login.userId);
        list = (ListView) root.findViewById(R.id.my_advertisement_list);
        Retrofit.getMyAdvertisementArrays(idUser, new Callback<RegistrationResponseFromServer>() {
            @Override
            public void success(RegistrationResponseFromServer registrationResponseFromServer, Response response) {
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

        return root;
    }
    private void adapter() {
        CustomList adapter = new
                CustomList(getActivity(), shortDescription, image, datePublication, expectedAmount, finalDate);
        list.setAdapter(adapter);
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
                news.putExtra("description",detailNewsDescription);
                startActivity(news);

            }
        });
    }
}

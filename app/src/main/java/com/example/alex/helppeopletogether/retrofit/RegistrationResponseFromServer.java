package com.example.alex.helppeopletogether.retrofit;

import java.util.ArrayList;

/**
 * Created by Alex on 01.04.2016.
 */
public class RegistrationResponseFromServer {
    public Integer response;
    public Integer status;
    public Integer user_id;
    public String full_name;
    public String avatar;
    //public String login;
   public Integer response_login;
    public Integer response_add_user_information;
    public ArrayList<Integer> id;
    public ArrayList<Integer> liked_advers;
    public ArrayList<String> created_at;
    public ArrayList<String> title;
    public ArrayList<String> short_description;
    public ArrayList<String> description;
    public ArrayList<String> image;
    public ArrayList<String> expected_amount;
    public ArrayList<String> final_date;

}

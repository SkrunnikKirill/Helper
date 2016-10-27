package com.example.alex.helppeopletogether.SupportClasses;

import java.util.ArrayList;

/**
 * Created by Alscon on 24-Oct-16.
 */

public class NewsListInformation {
    public ArrayList<String> shortDescription;
    public ArrayList<String> image;
    public ArrayList<String> datePublication;
    public ArrayList<String> expected_amount;
    public ArrayList<String> finalDate;
    public ArrayList<Integer> likeNews;
    public ArrayList<Integer> idServerNews;
    public ArrayList<Integer> idNews;


    public NewsListInformation(ArrayList<String> shortDescription, ArrayList<String> image, ArrayList<String> datePublication, ArrayList<String> expected_amount, ArrayList<String> finalDate, ArrayList<Integer> likeNews, ArrayList<Integer> idServerNews, ArrayList<Integer> idNews) {
        this.shortDescription = shortDescription;
        this.image = image;
        this.datePublication = datePublication;
        this.expected_amount = expected_amount;
        this.finalDate = finalDate;
        this.likeNews = likeNews;
        this.idServerNews = idServerNews;
        this.idNews = idNews;
    }


}

package com.example.alex.helppeopletogether.SupportClasses;

/**
 * Created by Alex on 02.06.2016.
 */
public class SelectedNews {
    public  String id;
    public String payment_account;
    public String created_at;
    public String title;
    public String short_description;
    public String description;
    public String image;
    public String expected_amount;
    public String final_date;


    public SelectedNews(String created_at, String title, String short_description, String description, String image, String expected_amount, String final_date, String id, String payment_account) {
        this.created_at = created_at;
        this.title = title;
        this.short_description = short_description;
        this.description = description;
        this.image = image;
        this.expected_amount = expected_amount;
        this.final_date = final_date;
        this.id = id;
        this.payment_account = payment_account;
    }


}

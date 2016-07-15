package com.example.alex.helppeopletogether.Vk;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alex on 26.04.2016.
 */
public class VkPersonDates {
    @SerializedName("last_name")
    String last_name;
    @SerializedName("id")
    Integer id;
    @SerializedName("first_name")
    String first_name;
    @SerializedName("photo")
    String photo;

    public String getPhoto() {
        return photo;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
}

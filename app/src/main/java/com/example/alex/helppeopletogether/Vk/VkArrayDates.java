package com.example.alex.helppeopletogether.Vk;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VkArrayDates {

    @SerializedName("response")
    private List<VkPersonDates> response;

    public List<VkPersonDates> getResponse() {
        return response;
    }

    public void setResponse(List<VkPersonDates> response) {
        this.response = response;
    }
}

package com.example.sagar.myapplication.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SAGAR on 1/14/2017.
 */
public class Brand {

    public String getBrand() {
        return brand;
    }
    public void setDetails(String details) {
        this.details = details;
    }

    @SerializedName("brand")
    @Expose
    String  brand;

    @SerializedName("details")
    String  details;

}

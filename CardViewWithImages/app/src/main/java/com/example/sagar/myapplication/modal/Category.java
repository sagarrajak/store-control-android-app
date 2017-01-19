package com.example.sagar.myapplication.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SAGAR on 1/15/2017.
 */
public class Category {
    @SerializedName("product_type")
    @Expose
    private String category_name;
    @SerializedName("details")
    @Expose
    private String category_details;
    public String getCategory_details(){return category_details;}
    public String getCategory_name(){return category_name;}
    public void setCategory_name(String category_name) {this.category_name = category_name;}
    public void setCategory_details(String category_details) {this.category_details = category_details; }
}

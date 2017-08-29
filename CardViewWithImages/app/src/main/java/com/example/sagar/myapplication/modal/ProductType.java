package com.example.sagar.myapplication.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class ProductType implements Serializable{
    @SerializedName("product_type")
    @Expose
    private String productType;
    @SerializedName("details")
    @Expose
    private String detials;
    @SerializedName("_id")
    @Expose
    String id;

    public String getProductType() {
        return productType;
    }
    public void setProductType(String productType) {
        this.productType = productType;
    }
    public String getDetials() {
        return detials;
    }
    public void setDetials(String detials) {
        this.detials = detials;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}

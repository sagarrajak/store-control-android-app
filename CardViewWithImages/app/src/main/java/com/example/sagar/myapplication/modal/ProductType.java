package com.example.sagar.myapplication.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SAGAR on 1/15/2017.
 */
public class ProductType {
    @SerializedName("product_type")
    @Expose
    private String productType;
    @SerializedName("details")
    @Expose
    private String detials;
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
}

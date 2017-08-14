package com.example.sagar.myapplication.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Stock {
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("seller")
    @Expose
    private String seller;
    @SerializedName("product")
    @Expose
    private String product;
    @SerializedName("buyed_price")
    @Expose
    private String buyedPrice;
    @SerializedName("exp_date")
    @Expose
    private String expDate;
    @SerializedName("details")
    @Expose
    private String details;
    @SerializedName("buyed_date")
    @Expose
    private String buyedDate;

    public String getQuantity() {
        return quantity;
    }
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
    public String getSeller() {
        return seller;
    }
    public void setSeller(String seller) {
        this.seller = seller;
    }
    public String getProduct() {
        return product;
    }
    public void setProduct(String product) {
        this.product = product;
    }
    public String getBuyedPrice() {
        return buyedPrice;
    }
    public void setBuyedPrice(String buyedPrice) {
        this.buyedPrice = buyedPrice;
    }
    public String getExpDate() {
        return expDate;
    }
    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }
    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }
    public String getBuyedDate() {
        return buyedDate;
    }
    public void setBuyedDate(String buyedDate) {
        this.buyedDate = buyedDate;
    }

}

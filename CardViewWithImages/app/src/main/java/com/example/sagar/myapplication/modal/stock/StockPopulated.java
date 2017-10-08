package com.example.sagar.myapplication.modal.stock;

import com.example.sagar.myapplication.modal.Product;
import com.example.sagar.myapplication.modal.Retailer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StockPopulated {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("seller")
    @Expose
    private Retailer seller;
    @SerializedName("product")
    @Expose
    private Product product;
    @SerializedName("selling_price")
    @Expose
    private Integer sellingPrice;
    @SerializedName("buyed_date")
    @Expose
    private String buyedDate;
    @SerializedName("notification")
    @Expose
    private Notification notification;
    @SerializedName("expire")
    @Expose
    private Expire expire;
    @SerializedName("details")
    @Expose
    private  String details;
    @SerializedName("buyed_price")
    @Expose
    private Integer  buyedPrice;

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Integer getBuyedPrice() {
        return buyedPrice;
    }

    public void setBuyedPrice(Integer buyedPrice) {
        this.buyedPrice = buyedPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Retailer getSeller() {
        return seller;
    }

    public void setSeller(Retailer seller) {
        this.seller = seller;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Integer sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getBuyedDate() {
        return buyedDate;
    }

    public void setBuyedDate(String buyedDate) {
        this.buyedDate = buyedDate;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public Expire getExpire() {
        return expire;
    }

    public void setExpire(Expire expire) {
        this.expire = expire;
    }
}

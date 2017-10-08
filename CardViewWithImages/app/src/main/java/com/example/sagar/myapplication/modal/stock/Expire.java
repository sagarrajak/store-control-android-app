package com.example.sagar.myapplication.modal.stock;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Expire {
    public  Expire(){}
    public  Expire(boolean isExpireable,String expireDate){
        this.isExpireable =  isExpireable;
        this.expireDate   =  expireDate;
    }
    @SerializedName("isExpireable")
    @Expose
    private Boolean isExpireable;
    @SerializedName("expireDate")
    @Expose
    private String expireDate;

    public Boolean getIsExpireable() {
        return isExpireable;
    }

    public void setIsExpireable(Boolean isExpireable) {
        this.isExpireable = isExpireable;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }


}

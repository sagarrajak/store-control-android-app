package com.example.sagar.myapplication.modal.stock;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Expire {
    public Expire() {
    }

    public Expire(boolean isExpireable, Date expireDate) {
        this.isExpireable = isExpireable;
        this.expireDate = expireDate;
    }

    @SerializedName("isExpireable")
    @Expose
    private Boolean isExpireable;
    @SerializedName("expireDate")
    @Expose
    private Date expireDate;

    public Boolean getIsExpireable() {
        return isExpireable;
    }

    public void setIsExpireable(Boolean isExpireable) {
        this.isExpireable = isExpireable;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }


}

package com.example.sagar.myapplication.modal.stock;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Notification {

    public Notification(){}
    public Notification(boolean isAllowed , Integer minimumQuantity){
       this.isAllowed =  isAllowed;
       this.minimumQuantity = minimumQuantity;
    }
    @SerializedName("isAllowed")
    @Expose
    private Boolean isAllowed;
    @SerializedName("minimumQuantity")
    @Expose
    private Integer minimumQuantity;

    public Boolean getIsAllowed() {
        return isAllowed;
    }

    public void setIsAllowed(Boolean isAllowed) {
        this.isAllowed = isAllowed;
    }

    public Integer getMinimumQuantity() {
        return minimumQuantity;
    }

    public void setMinimumQuantity(Integer minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
    }

}

package com.example.sagar.myapplication.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Brand implements Serializable{
     public String getBrand() {
        return brand;
     }
     public void setBrand(String brand){
         this.brand = brand;
     }
     public void setDetails(String details) {
        this.details = details;
    }
     public  String getDetails(){
         return  details;
     }
     public String getLogo() {
            return logo;
        }
     public void setLogo(String logo) {
            this.logo = logo;
        }
     public String getId() {
        return id;
    }
     public void setId(String id) {
        this.id = id;
    }

     @SerializedName("brand")
     @Expose
     String  brand;

     @SerializedName("details")
     @Expose
     String  details;

     @SerializedName("image")
     @Expose
     String logo;

    @SerializedName("_id")
    @Expose
    String id;
}

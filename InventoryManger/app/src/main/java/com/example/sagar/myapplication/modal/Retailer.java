
package com.example.sagar.myapplication.modal;

import android.graphics.drawable.AnimationDrawable;
import android.provider.ContactsContract;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Retailer implements Serializable {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private Name name;
    @SerializedName("phone_num")
    @Expose
    private PhoneNum phoneNum;
    @SerializedName("mail")
    @Expose
    private Mail mail;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("image")
    @Expose
    private String image;

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public PhoneNum getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(PhoneNum phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Mail getMail() {
        return mail;
    }

    public void setMail(Mail mail) {
        this.mail = mail;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

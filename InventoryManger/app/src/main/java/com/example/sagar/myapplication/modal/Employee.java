package com.example.sagar.myapplication.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Employee implements Serializable{

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private Name name;
    @SerializedName("date_of_birth")
    @Expose
    private Date dateOfBirth;
    @SerializedName("mail")
    @Expose
    private Mail mail;
    @SerializedName("pan_num")
    @Expose
    private String panNum;
    @SerializedName("phone_num")
    @Expose
    private PhoneNum  phoneNumber ;
    @SerializedName("date_of_join")
    @Expose
    private Date dateOfJoin;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("address")
    @Expose
    private Address mAddress;

    public Address getAddress() {
        return mAddress;
    }

    public void setAddress(Address mAddress) {
        this.mAddress = mAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date  dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Mail getMail() {
        return mail;
    }

    public void setMail(Mail mail) {
        this.mail = mail;
    }

    public String getPanNum() {
        return panNum;
    }

    public void setPanNum(String panNum) {
        this.panNum = panNum;
    }

    public PhoneNum getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNum phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date  getDateOfJoin() {
        return dateOfJoin;
    }

    public void setDateOfJoin(Date   dateOfJoin) {
        this.dateOfJoin = dateOfJoin;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}


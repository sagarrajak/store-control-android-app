package com.example.sagar.myapplication.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Employee implements Serializable{
    public  Employee(){}
    public Employee(String name, String mail, String  image) {
        this.name = name;
        this.mail = mail;
        this.image = image;
    }

    public  Employee( String name , String mail , String image , String   dateOfBirth , String  dateOfJoin ){
        this.name = name ;
        this.mail = mail;
        this.image = image;
        this.dateOfJoin   =  dateOfJoin;
        this.dateOfBirth  =  dateOfBirth;
        this.phoneNumber="182892892389";
    }


    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("date_of_birth")
    @Expose
    private String dateOfBirth;
    @SerializedName("mail")
    @Expose
    private String mail;
    @SerializedName("pan_num")
    @Expose
    private String panNum;

    @SerializedName("phone_number")
    @Expose
    private String  phoneNumber ;
    @SerializedName("date_of_join")
    @Expose
    private String  dateOfJoin;
    @SerializedName("image")
    @Expose
    private String image;

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

    public String  getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String  dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPanNum() {
        return panNum;
    }

    public void setPanNum(String panNum) {
        this.panNum = panNum;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String  getDateOfJoin() {
        return dateOfJoin;
    }

    public void setDateOfJoin(String   dateOfJoin) {
        this.dateOfJoin = dateOfJoin;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}


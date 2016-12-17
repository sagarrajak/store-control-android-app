package com.example.sagar.myapplication.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Employee{
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
    }
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("age")
    @Expose
    private String  age;
    @SerializedName("date_of_birth")
    @Expose
    private String dateOfBirth;
    @SerializedName("mail")
    @Expose
    private String mail;
    @SerializedName("pan_num")
    @Expose
    private String panNum;
    @SerializedName("__v")
    @Expose
    private String  v;
    @SerializedName("phone_number")
    @Expose
    private List<String > phoneNumber = null;
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

    public String  getAge() {
        return age;
    }

    public void setAge(String  age) {
        this.age = age;
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

    public String  getV() {
        return v;
    }

    public void setV(String  v) {
        this.v = v;
    }

    public List<String> getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(List<String > phoneNumber) {
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

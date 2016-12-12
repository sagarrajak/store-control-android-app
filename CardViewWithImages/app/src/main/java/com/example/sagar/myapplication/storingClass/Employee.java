package com.example.sagar.myapplication.storingClass;

public class Employee{
    public Employee(String name, String mail, int image) {
        this.name = name;
        this.mail = mail;
        this.image = image;
    }
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getMail() {return mail;}
    public void setMail(String mail){this.mail = mail;}
    public int getImage() {return image;}
    public void setImage(int image) {this.image = image;}
    private String name , mail;
    private int image;
}

package com.example.sagar.myapplication.modal;
public class Product{
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public Product(String name, String brand, String details, String price) {
        this.name = name;
        this.brand = brand;
        this.details = details;
        this.price = price;
    }
    public  Product(){}
    private String name;
    private String brand;
    private String details;
    private String price ;
}

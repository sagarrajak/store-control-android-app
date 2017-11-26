
package com.example.sagar.myapplication.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Address implements Serializable{


    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPoBox() {
        return poBox;
    }

    public void setPoBox(String poBox) {
        this.poBox = poBox;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @SerializedName("po_box")
    @Expose
    private  String poBox;
    @SerializedName("neighborhood")
    @Expose
    private  String neighborhood;
    @SerializedName("zipcode")
    @Expose
    private  String zipcode;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private  String state;
    @SerializedName("nation")
    @Expose
    private  String nation;
    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("address")
    @Expose
    private String address;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Address() {
    }

    /**
     *
     * @param address
     * @param zipcode
     * @param poBox
     * @param street
     * @param state
     * @param neighborhood
     * @param nation
     * @param city
     */
    public Address(String address,String street, String poBox, String neighborhood, String zipcode, String city, String state, String nation) {
        this.address = address;
        this.street = street;
        this.poBox = poBox;
        this.neighborhood = neighborhood;
        this.zipcode = zipcode;
        this.city = city;
        this.state = state;
        this.nation = nation;
    }

}

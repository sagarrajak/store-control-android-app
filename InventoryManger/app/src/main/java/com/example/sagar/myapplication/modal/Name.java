
package com.example.sagar.myapplication.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Name implements Serializable{

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddle() {
        return middle;
    }

    public void setMiddle(String middle) {
        this.middle = middle;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @SerializedName("name")
    @Expose
    private  String name;
    @SerializedName("middle")
    @Expose
    private String middle;
    @SerializedName("last")
    @Expose
    private  String last;
    @SerializedName("family_name")
    @Expose
    private  String familyName;
    @SerializedName("suffix")
    @Expose
    private  String suffix;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Name() {
    }

    /**
     * 
     * @param last
     * @param name
     * @param familyName
     * @param suffix
     * @param middle
     */
    public Name(String name, String middle, String last, String familyName, String suffix) {
        super();
        this.name = name;
        this.middle = middle;
        this.last = last;
        this.familyName = familyName;
        this.suffix = suffix;
    }

}

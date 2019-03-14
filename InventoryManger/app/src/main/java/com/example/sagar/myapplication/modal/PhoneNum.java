
package com.example.sagar.myapplication.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PhoneNum implements Serializable {

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("sub")
    @Expose
    private String sub;

    /**
     * No args constructor for use in serialization
     */
    public PhoneNum() {
    }

    /**
     * @param sub
     * @param value
     */
    public PhoneNum(String value, String sub) {
        this.value = value;
        this.sub = sub;
    }

}

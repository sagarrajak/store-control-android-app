
package com.example.sagar.myapplication.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Mail implements Serializable {

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

    public Mail() {
    }

    public Mail(String value, String sub) {
        this.value = value;
        this.sub = sub;
    }

}

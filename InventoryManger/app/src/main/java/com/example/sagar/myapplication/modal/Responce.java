
package com.example.sagar.myapplication.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Responce {

    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("statusText")
    @Expose
    private String statusText;
    @SerializedName("errors")
    @Expose
    private Errors errors;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        String out = "";
        if (status != null) {
            out += status.toString();
            out += "\n";
        }
        if (statusText != null) {
            out += statusText.toString();
            out += "\n";
        }
        if (data != null) {
            out += data.toString();
            out += "\n";
        }

        if (errors != null) {
            out += errors.toString();
            out += "\n";
        }
        return out;
    }
}

package com.example.sagar.myapplication;

import android.content.Context;

/**
 * Created by SAGAR on 1/21/2017.
 */
public class CustumProgressDialog {
    public static android.app.ProgressDialog  getProgressDialog(Context mContext){
        android.app.ProgressDialog progressDialog = new android.app.ProgressDialog(mContext);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return  progressDialog;
    };
}

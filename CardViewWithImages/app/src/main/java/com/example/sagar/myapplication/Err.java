package com.example.sagar.myapplication;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by SAGAR on 12/16/2016.
 */
public class Err  {
    public  static void  s(Context mContext , String message){
        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
    }
}

package com.example.sagar.myapplication;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by SAGAR on 12/16/2016.
 */
public class Err  {
    public  static void  s(Context mContext , String message){
        Toast.makeText(mContext.getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }
    public  static  void e(String m){
        Log.e("FUCK",m);
    }

}

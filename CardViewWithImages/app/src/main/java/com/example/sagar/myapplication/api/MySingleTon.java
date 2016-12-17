package com.example.sagar.myapplication.api;


import android.app.Application;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
public class MySingleTon  extends Application{


  private  static  MySingleTon mySingleTon;
  private  RequestQueue mRequestQueue;
  private  ImageLoader imageLoader ;

    @Override
    public void onCreate() {
        super.onCreate();
        mySingleTon = this;
    }

    public MySingleTon(){}

    public  RequestQueue getmRequestQueue(){
        if(mRequestQueue==null)
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        return  mRequestQueue;
    }

    public  static  synchronized  MySingleTon getMySingleTon(){
        return  mySingleTon;
    }

    public ImageLoader getImageLoader(){
            if(imageLoader == null) {
                imageLoader = new ImageLoader(getmRequestQueue(), new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap> cache = new LruCache<>(20);
                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }
                    @Override
                    public void putBitmap(String url, Bitmap bitmap){
                        cache.put(url,bitmap);
                    }
                });
            }
        return imageLoader;
    }

}

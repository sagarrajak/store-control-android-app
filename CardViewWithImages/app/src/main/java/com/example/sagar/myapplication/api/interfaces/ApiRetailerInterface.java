package com.example.sagar.myapplication.api.interfaces;

import com.example.sagar.myapplication.modal.Data;
import com.example.sagar.myapplication.modal.Retailer;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiRetailerInterface {

    @GET("api/retailer")
    Call<List<Retailer>> getRetailer(
            @Header("token") String token
    );

    @POST("api/retailer")
    Call<Data> createRetailer(
        @Body Retailer mRetailer,
        @Header("token") String token
    );

    @DELETE("api/retailer/{id}")
    Call<Data>  deleteRetailer(
         @Path("id") String id ,
         @Header("token") String token
    );

    @Multipart
    @POST("api/retailer/image")
    Call<Data> uploadRetailerImage(
            @Part  MultipartBody.Part image ,
            @Header("token") String token
    );

    @DELETE("api/retailer/image/{id}")
    Call<Data>  deleteRetailerImage(
           @Path("image") String image ,
           @Header("token") String token
    );

}

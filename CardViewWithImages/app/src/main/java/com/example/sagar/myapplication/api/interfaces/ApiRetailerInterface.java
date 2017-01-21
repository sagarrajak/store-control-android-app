package com.example.sagar.myapplication.api.interfaces;

import com.example.sagar.myapplication.modal.Data;
import com.example.sagar.myapplication.modal.Retailer;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by SAGAR on 1/21/2017.
 */
public interface ApiRetailerInterface {

    @GET("api/retailer")
    Call<List<Retailer>> getRetailer();

    @FormUrlEncoded
    @POST("api/retailer")
    Call<Data> createRetailer(
        @Field("name") String name  ,
        @Field("phone_num") String phone_num ,
        @Field("mail") String mail ,
        @Field("address") String address ,
        @Field("image") String image
    );

    @DELETE("api/retailer/{id}")
    Call<Data>  deleteRetailer(
         @Path("id") String id
    );

    @Multipart
    @POST("api/retailer/image")
    Call<Data> uploadRetailerImage(
            @Part("image") MultipartBody.Part image
    );

    @DELETE("api/retailer/image/{id}")
    Call<Data>  deleteRetailerImage(
           @Path("image") String image
    );

}
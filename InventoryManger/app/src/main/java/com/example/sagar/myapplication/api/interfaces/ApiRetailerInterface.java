package com.example.sagar.myapplication.api.interfaces;

import com.example.sagar.myapplication.modal.Data;
import com.example.sagar.myapplication.modal.Retailer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    @PUT("api/retailer")
    Call<Data> modifyRetailer(
            @Body Retailer retailer,
            @Header("token") String mToken
    );

    @DELETE("api/retailer/{id}")
    Call<Data> deleteRetailer(
            @Path("id") String id,
            @Header("token") String token
    );

}

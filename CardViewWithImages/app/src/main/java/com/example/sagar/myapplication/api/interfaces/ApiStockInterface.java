package com.example.sagar.myapplication.api.interfaces;

import com.example.sagar.myapplication.modal.Data;
import com.example.sagar.myapplication.modal.Stock;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by SAGAR on 1/20/2017.
 */
public interface ApiStockInterface{

    @GET("api/stock")
    Call<List<Stock>>   getStockList();

    @POST("api/stock")
    Call<Data>  createStock(

            @Field("quantity")    Integer name,
            @Field("seller")      String seller,
            @Field("product")     String  product,
            @Field("buyed_price") Integer buyed_price,
            @Field("exp_date")    Date exp_date,
            @Field("details")     String details,
            @Field("buyed_date")  Date buyed_date

    );

    @DELETE("api/stock/{id}")
    Call<Data>  deleteEmployee(
         @Path("id") String id
    );

    @PUT("api/stock/{id}")
    Call<Data> updateStock();

}

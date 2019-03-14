package com.example.sagar.myapplication.api.interfaces;

import com.example.sagar.myapplication.modal.Data;
import com.example.sagar.myapplication.modal.stock.Expire;
import com.example.sagar.myapplication.modal.stock.Notification;
import com.example.sagar.myapplication.modal.stock.Stock;
import com.example.sagar.myapplication.modal.stock.StockPopulated;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiStockInterface {

    @GET("api/stock")
    Call<List<Stock>> getStockList(
            @Header("token") String token
    );


    @POST("api/stock")
    Call<Data> createStock(
            @Body Stock stock,
            @Header("token") String token
    );

    @DELETE("api/stock/{id}")
    Call<Data> deleteEmployee(
            @Path("id") String id,
            @Header("token") String token
    );

    @FormUrlEncoded
    @PUT("api/stock/name/{id}")
    Call<Data> updateStockName(
            @Header("token") String token,
            @Path("id") String id,
            @Field("name") String name
    );

    @FormUrlEncoded
    @PUT("api/stock/quantity/{id}")
    Call<Data> updateStockQuantity(
            @Header("token") String token,
            @Path("id") String id,
            @Field("quantity") String quantity
    );

    @FormUrlEncoded
    @PUT("api/stock/seller/{id}")
    Call<Data> updateStockSeller(
            @Header("token") String token,
            @Path("id") String id,
            @Field("seller") String seller
    );

    @FormUrlEncoded
    @PUT("api/stock/product/{id}")
    Call<Data> updateStockProduct(
            @Header("token") String token,
            @Path("id") String id,
            @Field("product") String product
    );

    @FormUrlEncoded
    @PUT("api/stock/buyed_price/{id}")
    Call<Data> updateStockBuyedPrice(
            @Header("token") String token,
            @Path("id") String id,
            @Field("buyed_price") String buyed_price
    );

    @FormUrlEncoded
    @PUT("api/stock/seller/{id}")
    Call<Data> updateSellingPrice(
            @Header("token") String token,
            @Path("id") String id,
            @Field("selling_price") String selling_price
    );

    @FormUrlEncoded
    @PUT("api/stock/expire/{id}")
    Call<Data> updateStockExpire(
            @Header("token") String token,
            @Path("id") String id,
            @Field("expire") Expire expire
    );

    @FormUrlEncoded
    @PUT("api/stock/buyed_date/{id}")
    Call<Data> updateBuyedDate(
            @Header("token") String token,
            @Path("id") String id,
            @Field("buyed_date") String buyed_date
    );

    @FormUrlEncoded
    @PUT("api/stock/notification/{id}")
    Call<Data> updateStockNotification(
            @Header("token") String token,
            @Path("id") String id,
            @Field("notification") Notification notification
    );

    @FormUrlEncoded
    @PUT("api/stock/details/{id}")
    Call<Data> updateStockDetails(
            @Header("token") String token,
            @Path("id") String id,
            @Field("details") String details
    );


    @GET("api/routes/stock")
    Call<List<StockPopulated>> getPopulatedStock(
            @Header("token") String token
    );

}

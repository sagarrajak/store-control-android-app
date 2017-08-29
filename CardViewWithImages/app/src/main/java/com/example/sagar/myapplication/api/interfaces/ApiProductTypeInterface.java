package com.example.sagar.myapplication.api.interfaces;

import com.example.sagar.myapplication.modal.Data;
import com.example.sagar.myapplication.modal.ProductType;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
public interface ApiProductTypeInterface{

    @GET("api/product-type")
    public Call<List<ProductType>> getProductTypeList(
            @Header("Token") String token
    );

    @FormUrlEncoded
    @POST("api/product-type")
    public Call<Data> addProductType(
        @Field("product_type") String type ,
        @Field("brand")  String details ,
        @Header("token") String token
    );

    @DELETE("api/product-type/{id}")
    public Call<Data>  deleteProduct(
        @Path("id") String id ,
        @Header("token") String token
    );

}

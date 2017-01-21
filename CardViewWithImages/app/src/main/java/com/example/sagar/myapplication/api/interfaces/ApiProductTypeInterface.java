package com.example.sagar.myapplication.api.interfaces;

import com.example.sagar.myapplication.modal.Data;
import com.example.sagar.myapplication.modal.ProductType;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by SAGAR on 1/14/2017.
 */
public interface ApiProductTypeInterface{

    @GET("api/product-type")
    public Call<List<ProductType>> getProductTypeList();

    @POST("api/product-type")
    public Call<Data> addProductType(
        @Field("product_type") String type ,
        @Field("details")  String details
    );

    @DELETE("api/product-type/{id}")
    public Call<Data>  deleteProduct(
        @Path("id") String id
    );

}

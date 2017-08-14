package com.example.sagar.myapplication.api.interfaces;

import com.example.sagar.myapplication.modal.Data;
import com.example.sagar.myapplication.modal.Product;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiProductInterface{

    @GET("api/product/")
    public Call<List<Product>> getProduct(
            @Header("token") String token
    );
    @FormUrlEncoded
    @POST("api/product/")
    Call<Data>  createProduct(
            @Field("name")  String name,
            @Field("brand")  String brand,
            @Field("type")   List<String>  type,
            @Field("detail") String details,
            @Field("price")  String price ,
            @Header("token") String token
    );

    @Multipart
    @POST("api/product/image")
    Call<Data> addProductImage(
            @Part MultipartBody.Part image ,
            @Header("token") String token
    );

    @DELETE("api/product/image/{id}")
    Call<Data> deleteProductImage(
        @Path("id")  String id ,
        @Header("token") String token
    );

    @DELETE("api/product/{id}")
    Call<Data> deleteProduct(
            @Path("id") String id ,
            @Header("token") String token
    );

}

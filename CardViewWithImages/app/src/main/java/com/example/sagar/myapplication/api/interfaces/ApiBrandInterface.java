package com.example.sagar.myapplication.api.interfaces;

import com.example.sagar.myapplication.modal.Brand;
import com.example.sagar.myapplication.modal.Data;
import com.example.sagar.myapplication.utill.Token;

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

public interface ApiBrandInterface {

    @GET("api/product-brand")
    public Call<List<Brand>> listBrand(
            @Header("token") String token
    );

    @FormUrlEncoded
    @POST("api/product-brand")
    public  Call<Data>  addNewBrand(
          @Field("brand") String brand ,
          @Field("details") String details,
          @Field("image") String image,
          @Header("token") String token
    );

    @DELETE ("api/product-brand/{id}")
    public Call<Data> deleteBrand(
            @Path("id") String id,
            @Header("token") String token
    );

    @Multipart
    @POST("api/product-brand/image/")
    public Call<Data> addBrandImage(
            @Part MultipartBody.Part  image ,
            @Header("token") String Token
    );

}

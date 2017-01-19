package com.example.sagar.myapplication.api;

import com.example.sagar.myapplication.modal.Brand;
import com.example.sagar.myapplication.modal.Data;

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
public interface ApiBrandInterface {

    @GET("api/product-brand")
    public Call<List<Brand>> listBrand();

    @POST("api/product-brand")
    public  Call<Data>  addNewBrand(
          @Field("brand") String brand ,
          @Field("details") String details,
          @Field("image") String image
    );

    @DELETE ("api/product-brand/{id}")
    public Call<Data> deleteBrand(
            @Path("id") String id
    );

}

package com.example.sagar.myapplication.api.interfaces;

import com.example.sagar.myapplication.modal.Data;
import com.example.sagar.myapplication.modal.Product;
import com.example.sagar.myapplication.modal.ProductPopulated;

import java.util.ArrayList;
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
import retrofit2.http.Query;

public interface ApiProductInterface{

    @GET("api/product/")
    public Call<List<Product>> getProductList(
            @Header("token") String token
    );

    @POST("api/product/")
    Call<Data>  createProduct(
            @Body Product product,
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

    @POST("api/routes/product")
    Call<Data> addProductTypeToProducts(
            @Query("isList") boolean isList ,
            @Field("product_id") String product_id ,
            @Field("product_list") ArrayList<Product> productList
     );

    @GET("api/routes/product/{id}")
    Call<ProductPopulated> getProductPopulated(
            @Header("token") String token,
            @Path("id") String id
    );

    @GET("api/routes/product")
    Call<List<ProductPopulated>> getProductPopulatedList(
            @Header("token") String token
    );

    @GET("api/routes/product/search-by-brand/{id}")
    Call<List<ProductPopulated>> getProductPopulatedBrandAssociatedList(
            @Header("token") String token,
            @Path("id") String brandId
    );

    @GET("api/routes/product/search-by-category/{id}")
    Call<List<ProductPopulated>> getProductPopulatedCategoryAssociatedList(
            @Header("token") String token,
            @Path("id") String CategoryId
    );

    @DELETE("api/routes/remove-category-from-product/{product_id}/{category_id}")
    Call<Data> deleteCategoryFromPrticularProduct(
        @Header("token") String token ,
        @Path("product_id") String product_id ,
        @Path("category_id") String category_id
    );


}

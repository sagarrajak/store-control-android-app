package com.example.sagar.myapplication.api.interfaces;

import com.example.sagar.myapplication.modal.Data;


import okhttp3.MultipartBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiImageInterface {

    @Multipart
    @POST("api/image")
    Call<Data> addImage(
            @Header("token") String token,
            @Part MultipartBody.Part mImage
    );


    @DELETE("api/image")
    Call<Data> deleteImage(
            @Header("token") String token,
            @Field("image") String image
    );

}

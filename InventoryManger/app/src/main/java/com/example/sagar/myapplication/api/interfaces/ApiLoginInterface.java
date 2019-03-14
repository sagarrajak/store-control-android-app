package com.example.sagar.myapplication.api.interfaces;

import com.example.sagar.myapplication.modal.Data;
import com.example.sagar.myapplication.modal.LoginData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiLoginInterface {

    @FormUrlEncoded
    @POST("api/login")
    public Call<LoginData> login(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("api/signup")
    public Call<LoginData> signup(
            @Field("username") String username,
            @Field("password") String password
    );
}

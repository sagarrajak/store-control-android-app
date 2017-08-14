package com.example.sagar.myapplication.api.interfaces;

import com.example.sagar.myapplication.modal.Data;
import com.example.sagar.myapplication.modal.WorkProfile;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
public interface ApiWorkProfileInterface {

    @GET("api/work-profile")
    public Call<List<WorkProfile>> getWorkProfileList(
           @Header("token") String token
    );

    @POST("api/work-profile")
    public  Call<Data> addWorkProfile(
            @Field("name") String name,
            @Field("hr_of_work") Integer hr_of_work,
            @Field("salary") Integer salary,
            @Field("right") String right ,
            @Header("token") String token
    );


    @DELETE("api/work-profile")
    public Call<Data>   deleteWorkProfile(
         @Field("id")  String id ,
         @Header("token") String token
    );

}

package com.example.sagar.myapplication.api.interfaces;

import com.example.sagar.myapplication.modal.Data;
import com.example.sagar.myapplication.modal.Employee;
import com.example.sagar.myapplication.modal.Responce;

import java.util.Date;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiEmployeeInterface {

     @GET("api/employee/")
     public  Call<List<Employee>> getEmployee(
        @Header("token") String token
     );


     @POST("api/employee/")
     Call<Responce>  createEmployee(
         @Body   Employee mEmployee,
         @Header("token") String token
     );

    @DELETE("api/employee/{id}")
    Call<Data> deleteEmployee(
             @Path("id") String mEmployeeId,
             @Header("token") String token
     );

    @GET("api/routes/employee/name/{id}")
    Call<List<Employee>> sortByName(
            @Path("id") Integer id ,
            @Header("token") String token
    );

    @GET("api/routes/employee/age/{id}")
    Call<List<Employee>> sortByAge(
            @Path("id") Integer id ,
            @Header("token") String token
    );

    @GET("api/routes/employee/date-of-join/{id}")
    Call<List<Employee>>  sortByDateOFJoin(
            @Path("id") Integer id ,
            @Header("token") String token
    );

    @PUT("api/employee/{id}")
    Call<Data> editEmployee(
          @Body Employee mEmployee ,
          @Header("token") String  token
    );

}

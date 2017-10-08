package com.example.sagar.myapplication.api.interfaces;

import com.example.sagar.myapplication.modal.Data;
import com.example.sagar.myapplication.modal.Employee;
import com.example.sagar.myapplication.modal.Responce;

import java.util.Date;
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

public interface ApiEmployeeInterface {

     @GET("api/employee/list-employee")
     public  Call<List<Employee>> getEmployee(
        @Header("token") String token
     );

    @FormUrlEncoded
    @POST("api/employee/create-employee")
     Call<Responce>  createEmployee(
                    @Field("name")   String name,
                    @Field("date_of_birth")  Date date_of_birth,
                    @Field("date_of_join")  Date date_of_join,
                    @Field("mail") String mail,
                    @Field("pan_num") String pan_num,
                    @Field("phone_num") String phone_number,
                    @Field("work_profile") String work_profile,
                    @Field("image") String image,
                    @Header("token") String token
     );
     @Multipart
     @POST("api/employee/upload-employee-image")
     Call<Data> addEmployeeImage(
                   @Part MultipartBody.Part image,
                   @Header("token") String token
             );
    @DELETE("api/employee/{id}")
    Call<Data> deleteEmployee(
             @Path("id") String id,
             @Header("token") String token
     );
    @FormUrlEncoded
    @POST("api/employee/delete-employee-image")
    Call<Data> deleteEmployeImage(
            @Field("id") String image,
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

}

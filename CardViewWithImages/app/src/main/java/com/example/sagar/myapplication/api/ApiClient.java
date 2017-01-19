package com.example.sagar.myapplication.api;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by SAGAR on 1/2/2017.
 */
public class ApiClient {

    public static final String baseUrl = "http://192.168.43.180:3000";
    private static Retrofit retrofit = null;

    private static Gson gson = new com.google.gson.GsonBuilder()
                                    .setLenient()
                                        .create();

    public static Retrofit getClient(){

             HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
             interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
             OkHttpClient client  = new OkHttpClient.Builder()
                                             .addInterceptor(interceptor)
                                                .retryOnConnectionFailure(true)
                                                     .connectTimeout(15,TimeUnit.DAYS)
                                                          .readTimeout(15,TimeUnit.DAYS)
                                                              .build();

            if (retrofit == null){
                    retrofit = new Retrofit.Builder()
                                    .baseUrl(baseUrl)
                                        .addConverterFactory(GsonConverterFactory.create(gson))
                                            .client(client)
                                                .build();
            }


        return retrofit;
    }
}

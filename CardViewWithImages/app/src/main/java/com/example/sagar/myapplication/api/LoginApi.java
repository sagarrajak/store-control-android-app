package com.example.sagar.myapplication.api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sagar.myapplication.utill.Token;
import com.example.sagar.myapplication.api.interfaces.ApiLoginInterface;
import com.example.sagar.myapplication.intent.employee.MainActivity;
import com.example.sagar.myapplication.modal.LoginData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginApi{
      private ApiLoginInterface mApiLoginInterface;
      private Context mApplicationContext;


      public  LoginApi(Context mContext){

                      this.mApiLoginInterface       =  ApiClient.getClient().create(ApiLoginInterface.class);
                      this.mApplicationContext      =  mContext;

      }

      public void   login(String username , String password , final ProgressBar bar , final Activity activity ){

                    mApiLoginInterface.login(username , password).enqueue(new Callback<LoginData>() {
                        @Override
                        public void onResponse(Call<LoginData> call, Response<LoginData> response){
                                    if(!response.body().isSuccess()){
                                        Toast.makeText( mApplicationContext,response.body().getMessage() , Toast.LENGTH_LONG ).show();
                                        bar.setVisibility( View.GONE );
                                    }
                                    else{
                                        Toast.makeText(mApplicationContext,response.body().getToken(),Toast.LENGTH_LONG).show();
                                        bar.setVisibility(View.GONE);
                                        Token.setToken(response.body().getToken());
                                        setSharePreference( response.body().getToken() , activity );
                                    }
                        }
                        @Override
                        public void onFailure(Call<LoginData> call, Throwable t) {
                                    t.printStackTrace();
                                    Toast.makeText( mApplicationContext , "Login Failed" , Toast.LENGTH_LONG ).show();
                        }
                    });


      }


      private void setSharePreference(String token , final Activity activity){

              mApplicationContext.getSharedPreferences("SETTING",Context.MODE_PRIVATE).edit().putString("token",token).apply();
              Intent intent =  new Intent( mApplicationContext , MainActivity.class );
              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              mApplicationContext.startActivity(intent);
              activity.finish();

      }

}





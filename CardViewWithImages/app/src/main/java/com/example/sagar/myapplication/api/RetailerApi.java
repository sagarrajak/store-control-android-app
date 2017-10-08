package com.example.sagar.myapplication.api;

import android.app.Dialog;

import com.example.sagar.myapplication.utill.Err;
import com.example.sagar.myapplication.utill.Token;
import com.example.sagar.myapplication.adapter.interfaces.RetailerAdapterInterface;
import com.example.sagar.myapplication.api.interfaces.ApiRetailerInterface;
import com.example.sagar.myapplication.modal.Data;
import com.example.sagar.myapplication.modal.Retailer;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class RetailerApi{
    private static  RetailerApi retailerApi;
    private ApiRetailerInterface mRetailerInterface;
    private RetailerAdapterInterface mRetailerAdapterInterface;

    public RetailerApi(RetailerAdapterInterface mRetailerAdapterInterface){
        mRetailerInterface = ApiClient.getClient().create( ApiRetailerInterface.class );
        this.mRetailerAdapterInterface = mRetailerAdapterInterface;
    }


    public void listRetailer(final Dialog dialog){
            mRetailerInterface.getRetailer(
                    Token.token
            ).enqueue(new Callback<List<Retailer>>() {
                @Override
                public void onResponse(Call<List<Retailer>> call, Response<List<Retailer>> response) {
                    if(response.code()==200){
                            mRetailerAdapterInterface.addNewReatilerList(response.body());
                            dialog.dismiss();
                            Err.e("listed retailer");
                    }
                    else{
                            Err.e("Error in getting employee");
                            dialog.dismiss();
                    }
                }
                @Override
                public void onFailure(Call<List<Retailer>> call, Throwable t) {
                    t.printStackTrace();
                    dialog.dismiss();
                }
            });
    }

    public void uploadRetailerImage( MultipartBody.Part bodypart , final  Retailer  retailer , final Dialog dialog ){
          mRetailerInterface.uploadRetailerImage( bodypart  , Token.token  ).enqueue(new Callback<Data>() {
              @Override
              public void onResponse(Call<Data> call,Response<Data> response) {
                    if(response.code()==200){
                        Err.e("Image uploaded");
                        createNewRetailer( dialog , retailer , response.body().getMessage()  );
                    }
                    else{
                        Err.e("failed to upload image");
                        dialog.dismiss();
                    }
              }
              @Override
              public void onFailure(Call<Data> call, Throwable t) {
                    t.printStackTrace();
                    dialog.dismiss();
              }
          });
    }

    public void deleteRetailerImage(final Dialog dialog , String image , final String retailer_id ){
        mRetailerInterface.deleteRetailerImage( image  , Token.token ).enqueue(new Callback<Data>() {
                            @Override
                            public void onResponse(Call<Data> call, Response<Data> response){
                                    if(response.code()==200){
                                        deleteRetailer(retailer_id,dialog);

                                    }
                                    else{
                                        dialog.dismiss();
                                        Err.e("failed to delete image");
                                    }
                            }
                            @Override
                            public void onFailure(Call<Data> call, Throwable t){
                                t.printStackTrace();
                                dialog.dismiss();
                            }
        });
    }

    public void createNewRetailer(final Dialog dialog , final Retailer retailer , String imageid ){
                  mRetailerInterface.createRetailer(
                        retailer.getName() , retailer.getPhoneNum() , retailer.getPhoneNum() ,retailer.getAddress() , imageid , Token.token
                  ).enqueue(new Callback<Data>(){
                      @Override
                      public void onResponse(Call<Data> call, Response<Data> response) {
                          if(response.code()==200){
                            listRetailer(dialog);
                              Err.e("Retailer created!");
                          }
                          else {
                              dialog.dismiss();
                              Err.e("Failed to create retailer!");
                          }
                      }
                      @Override
                      public void onFailure(Call<Data> call, Throwable t) {
                            t.printStackTrace();
                            dialog.dismiss();
                      }
                  });
    }

    public void deleteRetailer(final String id,final Dialog  dialog){
        mRetailerInterface.deleteRetailer(  id , Token.token  ).enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if(response.code()==200){
                    listRetailer(dialog);
                }
                else{
                    Err.e("error in deleting employee");
                    dialog.dismiss();
                }
            }
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                t.printStackTrace();
                dialog.dismiss();
            }
        });
    }

    public void setRetailerAdapterInterface(RetailerAdapterInterface mRetailerAdapterInterface){
        this.mRetailerAdapterInterface = mRetailerAdapterInterface;
    }

    public static RetailerApi getmReteilerApi(RetailerAdapterInterface mReaRetailerAdapterInterface){
        if(retailerApi==null)
            retailerApi = new RetailerApi(mReaRetailerAdapterInterface);
        else
            retailerApi.setRetailerAdapterInterface(mReaRetailerAdapterInterface);
        return retailerApi;
    }

}

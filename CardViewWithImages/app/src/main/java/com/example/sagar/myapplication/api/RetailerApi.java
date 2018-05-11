package com.example.sagar.myapplication.api;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;

import com.example.sagar.myapplication.api.interfaces.ApiImageInterface;
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
    private ApiImageInterface mApiImageInterface;
    private Context mContext;

    private  RetailerApi(RetailerAdapterInterface mRetailerAdapterInterface, Context mContext) {
        mRetailerInterface = ApiClient.getClient().create( ApiRetailerInterface.class );
        mApiImageInterface = ApiClient.getClient().create(ApiImageInterface.class);
        this.mContext = mContext;
        this.mRetailerAdapterInterface = mRetailerAdapterInterface;
    }

    public void listRetailer(final Dialog dialog){
            mRetailerInterface.getRetailer(
                    Token.token
            ).enqueue(new Callback<List<Retailer>>() {
                @Override
                public void onResponse(Call<List<Retailer>> call, Response<List<Retailer>> response) {
                    if(response.code() == 200) {
                        mRetailerAdapterInterface.addNewReatilerList(response.body());
                        dialog.dismiss();
                    }
                    else{
                        Err.s(mContext, "Error in getting employee");
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

    public void listRetailer(final SwipeRefreshLayout mSwipeToRefreshLayout) {
        mRetailerInterface.getRetailer(
                Token.token
        ).enqueue(new Callback<List<Retailer>>() {
            @Override
            public void onResponse(Call<List<Retailer>> call, Response<List<Retailer>> response) {
                if(response.code() == 200) {
                    mRetailerAdapterInterface.addNewReatilerList(response.body());
                } else{
                    Err.s(mContext, "Error in getting retailer");
                }
                mSwipeToRefreshLayout.setRefreshing(false);
            }
            @Override
            public void onFailure(Call<List<Retailer>> call, Throwable t) {
                t.printStackTrace();
                mSwipeToRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void createNewRetailer(MultipartBody.Part bodypart, final Retailer retailer, final Dialog dialog) {
        if(bodypart != null) {
            mApiImageInterface.addImage(Token.token, bodypart)
                    .enqueue(new Callback<Data>() {
                        @Override
                        public void onResponse(Call<Data> call, Response<Data> response) {
                            if(response.code() == 200 && response.body().getSuccess()) {
                                retailer.setImage(response.body().getId());
                                createNewRetailerHelper(dialog, retailer);
                            }
                            else{
                                Err.s(mContext, "failed to upload image");
                                dialog.dismiss();
                            }
                        }
                        @Override
                        public void onFailure(Call<Data> call, Throwable t) {
                            t.printStackTrace();
                            Err.s(mContext, "failed to upload image");
                            dialog.dismiss();
                        }
                    });
        }
        else {
            createNewRetailerHelper(dialog, retailer);
        }
    }

    public void deleteRetailer(final Dialog dialog,final  Retailer mRetailer) {
        if(mRetailer.getId() == null) {
            deleteRetailerHelper(mRetailer, dialog);
        } else {
            mApiImageInterface.deleteImage(mRetailer.getId() ,Token.token)
                    .enqueue(new Callback<Data>() {
                        @Override
                        public void onResponse(Call<Data> call, Response<Data> response) {
                            if(response.code() == 200 && response.body().getSuccess()){
                                deleteRetailerHelper(mRetailer, dialog);
                            }
                            else{
                                dialog.dismiss();
                                Err.s(mContext, "failed to delete image");
                            }
                        }
                        @Override
                        public void onFailure(Call<Data> call, Throwable t) {
                            t.printStackTrace();
                            dialog.dismiss();
                        }
                    });
        }
    }

    private void createNewRetailerHelper(final Dialog dialog , final Retailer retailer) {
                  mRetailerInterface.createRetailer(
                      retailer , Token.token
                  ).enqueue(new Callback<Data>() {
                      @Override
                      public void onResponse(Call<Data> call, Response<Data> response) {
                          if(response.code()==200) {
                              listRetailer(dialog);
                              Err.s(mContext, "Retailer created!");
                          }
                          else{
                              dialog.dismiss();
                              Err.s(mContext,"Failed to create retailer!");
                          }
                      }
                      @Override
                      public void onFailure(Call<Data> call, Throwable t) {
                            t.printStackTrace();
                            dialog.dismiss();
                      }
                  });
    }

    private  void deleteRetailerHelper(Retailer mRetailer, final Dialog  dialog){
        mRetailerInterface.deleteRetailer(mRetailer.getId(), Token.token).
                enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if(response.code()==200) {
                    listRetailer(dialog);
                }
                else {
                    Err.s(mContext, "error in deleting employee");
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

    public static RetailerApi getmReteilerApi(RetailerAdapterInterface mReaRetailerAdapterInterface, Context mContext){
        if(retailerApi == null)
            retailerApi = new RetailerApi(mReaRetailerAdapterInterface, mContext);
        else
            retailerApi.setRetailerAdapterInterface(mReaRetailerAdapterInterface);
        return retailerApi;
    }

}

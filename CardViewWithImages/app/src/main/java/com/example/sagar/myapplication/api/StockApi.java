package com.example.sagar.myapplication.api;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;

import com.example.sagar.myapplication.adapter.StockAdapter;
import com.example.sagar.myapplication.api.interfaces.ApiStockInterface;
import com.example.sagar.myapplication.modal.Data;
import com.example.sagar.myapplication.modal.Errors;
import com.example.sagar.myapplication.modal.stock.Stock;
import com.example.sagar.myapplication.modal.stock.StockPopulated;
import com.example.sagar.myapplication.utill.Err;
import com.example.sagar.myapplication.utill.Token;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockApi{

    private  static  StockApi mStockApi;
    private Context mContext;
    private ApiStockInterface mApiStockInterface;
    private StockAdapter mStockAdapter;

    public  StockApi( Context mContext ){
        this.mContext      =  mContext;
        mApiStockInterface =  ApiClient.getClient().create(ApiStockInterface.class);
        mStockAdapter      =  StockAdapter.getStockAdapter(mContext);
    }

    public void setContext(Context mContext){
        this.mContext = mContext;
    }

    public void listStock(final Dialog dialog ){
        mApiStockInterface.getPopulatedStock(Token.token)
                    .enqueue(new Callback<List<StockPopulated>>() {
                        @Override
                        public void onResponse(Call<List<StockPopulated>> call, Response<List<StockPopulated>> response) {
                            if(response.code()==200){
                                 mStockAdapter.setStockList(response.body());
                                 dialog.dismiss();
                            }
                            else{
                                Err.s(mContext,"Error in fetching date");
                                dialog.dismiss();
                            }
                        }
                        @Override
                        public void onFailure(Call<List<StockPopulated>> call, Throwable t) {
                             t.printStackTrace();
                             dialog.dismiss();
                        }
                    });
    }

    public void createStock(final Dialog dialog , Stock  stock ){
        mApiStockInterface.createStock(
                                        stock , Token.token
                                     ).enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                     if(response.code() == 200){
                         listStock(dialog);
                         Err.s(mContext , "Stock created successfully!!");
                     }
                     else{
                        dialog.dismiss();
                        Err.s(mContext , "Error in creating stock!");
                     }
            }
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                  t.printStackTrace();
                  Err.s(mContext , t.getMessage()+"" );
                  dialog.dismiss();
            }
        });
    }


    public void deleteStock( Dialog dialog , String id  ){


    }
}






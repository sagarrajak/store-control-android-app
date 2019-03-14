package com.example.sagar.myapplication.api;

import android.app.Dialog;
import android.content.Context;

import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.adapter.StockAdapter;
import com.example.sagar.myapplication.api.interfaces.ApiStockInterface;
import com.example.sagar.myapplication.modal.Data;
import com.example.sagar.myapplication.modal.stock.Stock;
import com.example.sagar.myapplication.modal.stock.StockPopulated;
import com.example.sagar.myapplication.utill.Err;
import com.example.sagar.myapplication.utill.Token;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockApi {

    private static StockApi mStockApi;
    private Context mContext;
    private ApiStockInterface mApiStockInterface;
    private StockAdapter mStockAdapter;

    public StockApi(Context mContext) {
        this.mContext = mContext;
        mApiStockInterface = ApiClient.getClient().create(ApiStockInterface.class);
        mStockAdapter = StockAdapter.getStockAdapter(mContext);
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public void listStock(final Dialog dialog) {
        mApiStockInterface.getPopulatedStock(Token.token)
                .enqueue(new Callback<List<StockPopulated>>() {
                    @Override
                    public void onResponse(Call<List<StockPopulated>> call, Response<List<StockPopulated>> response) {
                        if (response.code() == 200) {
                            mStockAdapter.setStockList(response.body());
                            dialog.dismiss();
                        } else {
                            Err.s(mContext, "Error in fetching date");
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

    public void createStock(final Dialog dialog, Stock stock) {
        mApiStockInterface.createStock(
                stock, Token.token
        ).enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if (response.code() == 200) {
                    listStock(dialog);
                    Err.s(mContext, "Stock created successfully!!");
                } else {
                    dialog.dismiss();
                    Err.s(mContext, "Error in creating stock!");
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                t.printStackTrace();
                Err.s(mContext, t.getMessage() + "");
                dialog.dismiss();
            }
        });
    }

    public void sortStock(int key, Dialog dialog) {
        dialog.dismiss();
        switch (key) {
            case R.id.bottom_sheet_stock_name:
                Err.s(mContext, "name");
                break;
            case R.id.bottom_sheet_stock_expire_date:
                Err.s(mContext, "expire date");
                break;
            case R.id.bottom_sheet_stock_created_date:
                Err.s(mContext, "created date");
                break;
            case R.id.bottom_sheet_stock_selling_price:
                Err.s(mContext, "selling price");
                break;
            case R.id.bottom_sheet_stock_buyed_price:
                Err.s(mContext, "buyed price");
                break;
            case R.id.bottom_sheet_stock_profit_per_item:
                Err.s(mContext, "profit per item");
                break;
            case R.id.bottom_sheet_stock_quantity:
                Err.s(mContext, "stock quantity");
                break;
        }
    }

}






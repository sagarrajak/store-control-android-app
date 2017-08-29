package com.example.sagar.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.sagar.myapplication.api.interfaces.ApiProductInterface;
import com.example.sagar.myapplication.modal.Data;
import com.example.sagar.myapplication.modal.ProductPopulated;
import com.example.sagar.myapplication.utill.Err;
import com.example.sagar.myapplication.utill.Token;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductCategoryDetailsAdapter extends RecyclerView.Adapter<ProductCategoryDetailsAdapter.MyViewHolder> {

    private List<ProductPopulated> mProduct;
    private ApiProductInterface mApiProductInterface;
    private Context context;



    public ProductCategoryDetailsAdapter(){


    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }
    @Override
    public int getItemCount() {
        return 0;
    }
    public void listProduct() {}
    public  void deleteProduct(int position ){}
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}

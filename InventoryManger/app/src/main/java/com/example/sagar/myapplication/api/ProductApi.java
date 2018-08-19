package com.example.sagar.myapplication.api;

import android.app.Dialog;
import android.support.v4.widget.SwipeRefreshLayout;

import com.example.sagar.myapplication.adapter.interfaces.ProductAdapterInterface;
import com.example.sagar.myapplication.modal.ProductPopulated;
import com.example.sagar.myapplication.utill.Err;
import com.example.sagar.myapplication.utill.Token;
import com.example.sagar.myapplication.api.interfaces.ApiProductInterface;
import com.example.sagar.myapplication.modal.Data;
import com.example.sagar.myapplication.modal.Product;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductApi{

    private static ProductApi mProductApi;
    private ProductAdapterInterface mProductAdapterInterface;
    private ApiProductInterface mApiProductInterface;

    public  ProductApi( ProductAdapterInterface mProductGridAdapter){
        this.mProductAdapterInterface = mProductGridAdapter;
        this.mApiProductInterface = ApiClient.getClient().create(ApiProductInterface.class);
    }

    public void addNewAdapter(ProductAdapterInterface mProductAdapterInterface){
        this.mProductAdapterInterface = mProductAdapterInterface;
    }

    public  void  addProduct(Product mProduct , final Dialog dialog , String  message ){
        mApiProductInterface.createProduct(
              mProduct , Token.token
        ).enqueue(new Callback<Data>() {
            @Override
            public void onResponse( Call<Data> call  , Response<Data> response  ){
                if( response.code() == 200 ){
                    listProduct(dialog);
                }
                else{
                    Err.e("Not able to add product");
                    dialog.dismiss();
                }
            }
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                    t.printStackTrace();
                    Err.e("Error in stack adding product");
            }
        });
    }

    public  void  deleteProduct(String id , final  Dialog dialog){
        mApiProductInterface.deleteProduct( id , Token.token )
                    .enqueue(new Callback<Data>() {
                        @Override
                        public void onResponse(Call<Data> call, Response<Data> response){
                                if(response.code() == 200){
                                    listProduct(dialog);
                                }else{
                                   Err.e("Failed to delete");
                                    dialog.dismiss();
                                }
                        }
                        @Override
                        public void onFailure(Call<Data> call,Throwable t){
                                t.printStackTrace();
                                Err.e("failed to load to product");
                        }
                    });

    }

    public  void  addImage(MultipartBody.Part part , final Product mProduct , final Dialog dialog ){
        mApiProductInterface.addProductImage(part , Token.token ).enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response){
                        if( response.code() == 200 ){
                              mProduct.setImage(response.body().getMessage());
                              addProduct( mProduct , dialog , response.body().getMessage() );
                        }
                        else{
                            Err.e("failed to create employee");
                            dialog.dismiss();
                        }
            }
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                t.printStackTrace();
                Err.e("failed to add product");
                dialog.dismiss();
            }
        });
    }

    public  void  deleteImage(final  String id  , final Dialog  dialog ){
        mApiProductInterface.deleteProduct( id , Token.token ).enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call,Response<Data> response){
                if(response.code() == 200){
                    deleteProduct(id,dialog);
                }
                else{
                    Err.e("failed to delete product");
                    dialog.dismiss();
                }
            }
            @Override
            public void onFailure(Call<Data> call,Throwable t){
                t.printStackTrace();
                Err.e("Error in deleting product");
            }
        });
    }

    public  void  listProduct(final Dialog dialog){
        mApiProductInterface.getProductPopulatedList(Token.token)
        .enqueue(new Callback<List<ProductPopulated>>(){
            @Override
            public void onResponse(Call<List<ProductPopulated>> call, Response<List<ProductPopulated>> response){
                if ( response.code() == 200 ){
                    mProductAdapterInterface.addNewproductList(response.body());
                }
                else Err.e("failed to List product");
                dialog.dismiss();
            }
            @Override
            public void onFailure(Call<List<ProductPopulated>> call, Throwable t){
                t.printStackTrace();
                Err.e("Failed to load product");
            }
        });
    }

    public void listProduct(final SwipeRefreshLayout mSwipeRefreshLayout){
        mApiProductInterface.getProductPopulatedList(Token.token)
                .enqueue(new Callback<List<ProductPopulated>>(){
            @Override
            public void onResponse(Call<List<ProductPopulated>> call, Response<List<ProductPopulated>> response){
                if ( response.code() == 200 ){
                    mProductAdapterInterface.addNewproductList(response.body());
                }
                else Err.e("failed to List product");
                mSwipeRefreshLayout.setRefreshing(false);
            }
            @Override
            public void onFailure(Call<List<ProductPopulated>> call, Throwable t){
                t.printStackTrace();
                Err.e("Failed to load product");
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public static ProductApi  getmProductApi( ProductAdapterInterface mProductAdapterInterface ){
        if( mProductApi == null )
            mProductApi = new ProductApi(mProductAdapterInterface);
        else
            mProductApi.addNewAdapter(mProductAdapterInterface);
      return mProductApi;
    }

}

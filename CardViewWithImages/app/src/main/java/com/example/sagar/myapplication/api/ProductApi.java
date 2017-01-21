package com.example.sagar.myapplication.api;

import android.app.Dialog;

import com.example.sagar.myapplication.Err;
import com.example.sagar.myapplication.adapter.ProductAdapter;
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
    private ProductAdapter mProductAdapter ;
    private ApiProductInterface mApiProductInterface;

    public  ProductApi( ProductAdapter mProductAdapter ){
        this.mProductAdapter = mProductAdapter;
        this.mApiProductInterface = ApiClient.getClient().create(ApiProductInterface.class);
    }

    public  void  addProduct(Product mProduct , final Dialog dialog , String  message ){

        mApiProductInterface.createProduct(
              mProduct.getName() , mProduct.getBrand() , mProduct.getType() , mProduct.getDetail() , mProduct.getPrice()
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

    public  void  deleteProduct(String id , final Dialog  dialog){

            mApiProductInterface.deleteProduct(id)
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
        mApiProductInterface.addProductImage(part).enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response){
                        if( response.code() == 200 ){
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

        mApiProductInterface.deleteProduct(id).enqueue(new Callback<Data>() {
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
        Call<List<Product>> employee_obj  =  mApiProductInterface.getProduct();
        employee_obj.enqueue(new Callback<List<Product>>(){
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response){
                if ( response.code() == 200 ){
                    mProductAdapter.addProductList(response.body());
                }
                else Err.e("failed to List product");
                dialog.dismiss();
            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t){
                t.printStackTrace();
                Err.e("Failed to load product");
            }
        });
    }

    public static ProductApi  getmProductApi(ProductAdapter mProductAdapter){
        if(mProductApi == null)
            mProductApi = new ProductApi(mProductAdapter);

      return mProductApi;
    }

}

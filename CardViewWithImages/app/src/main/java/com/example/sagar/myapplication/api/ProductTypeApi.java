package com.example.sagar.myapplication.api;

import android.app.Dialog;

import com.example.sagar.myapplication.Err;
import com.example.sagar.myapplication.Token;
import com.example.sagar.myapplication.adapter.CategoryAdapter;
import com.example.sagar.myapplication.api.interfaces.ApiProductTypeInterface;
import com.example.sagar.myapplication.modal.Data;
import com.example.sagar.myapplication.modal.ProductType;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SAGAR on 1/15/2017.
 */
public class ProductTypeApi{


    private static  ProductTypeApi  mProductTypeApi;


    private ApiProductTypeInterface mApiProductInterface;
    private CategoryAdapter mCategoryAdapter ;

    private List<ProductType> mList;

    public ProductTypeApi(CategoryAdapter mCategoryAdapter){
         mList = new ArrayList<>();
         mApiProductInterface = ApiClient.getClient().create(ApiProductTypeInterface.class);
         this.mCategoryAdapter = mCategoryAdapter;
    }


    public void  addProductType(String product_type , String details , final Dialog dialog ){
        mApiProductInterface
                .addProductType( product_type , details , Token.token )
                    .enqueue(new Callback<Data>(){
                        @Override
                        public void onResponse(Call<Data> call,Response<Data> response){
                            if(response.code()==200)
                                  listProductType(dialog);
                            else{
                                Err.e("Not able to add product");
                                dialog.dismiss();
                            }
                        }
                        @Override
                        public void onFailure(Call<Data> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
    }


    public void  deleteProductType(String id , final  Dialog  dialog ){
        mApiProductInterface
                .deleteProduct( id , Token.token )
                    .enqueue(new Callback<Data>() {
                        @Override
                        public void onResponse(Call<Data> call, Response<Data> response) {
                             if( response.code() == 200 ){
                                listProductType(dialog);
                             }
                            else{
                                Err.e("Error in deleting data");
                                dialog.dismiss();
                             }
                        }
                        @Override
                        public void onFailure(Call<Data> call,Throwable t){
                            t.printStackTrace();
                            Err.e("Error in deleting data");
                        }
                    });
    }

    public void listProductType(final  Dialog dialog) {
        mApiProductInterface
                .getProductTypeList( Token.token )
                .enqueue(new Callback<List<ProductType>>(){
                    @Override
                    public void onResponse(Call<List<ProductType>> call, Response<List<ProductType>> response) {
                        if( response.code() == 200 ){
                            mList = response.body();
                        }
                    }
                    @Override
                    public void onFailure(Call<List<ProductType>> call, Throwable t) {

                    }
                });
    }

    public static  ProductTypeApi getmProductTypeApi(CategoryAdapter mCategoryAdapter){
          if(mProductTypeApi == null)
              mProductTypeApi = new ProductTypeApi(mCategoryAdapter);
         return  mProductTypeApi;
    }



}

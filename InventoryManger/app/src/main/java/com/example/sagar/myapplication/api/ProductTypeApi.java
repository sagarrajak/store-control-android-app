package com.example.sagar.myapplication.api;

import android.app.Dialog;
import android.support.v4.widget.SwipeRefreshLayout;

import com.example.sagar.myapplication.modal.Product;
import com.example.sagar.myapplication.utill.Err;
import com.example.sagar.myapplication.utill.Token;
import com.example.sagar.myapplication.adapter.product.ProductCategoryAdapter;
import com.example.sagar.myapplication.api.interfaces.ApiProductTypeInterface;
import com.example.sagar.myapplication.modal.Data;
import com.example.sagar.myapplication.modal.ProductType;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductTypeApi {

    private ApiProductTypeInterface mApiProductInterface;
    private ProductCategoryAdapter mProductCategoryAdapter;
    private static ProductTypeApi mProductTypeApi;


    public ProductTypeApi(ProductCategoryAdapter mProductCategoryAdapter) {

        mApiProductInterface = ApiClient.getClient().create(ApiProductTypeInterface.class);
        this.mProductCategoryAdapter = mProductCategoryAdapter;

    }


    public void addProductType(String product_type, String details, final Dialog dialog) {


        mApiProductInterface
                .addProductType(product_type, details, Token.token)
                .enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data> call, Response<Data> response) {
                        if (response.code() == 200)
                            listProductType(dialog);
                        else {
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


    public void deleteProductType(String id, final Dialog dialog) {


        mApiProductInterface
                .deleteProduct(id, Token.token)
                .enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data> call, Response<Data> response) {
                        if (response.code() == 200) {
                            listProductType(dialog);
                        } else {
                            Err.e("Error in deleting data");
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {
                        t.printStackTrace();
                        Err.e("Error in deleting data");
                    }
                });


    }

    public void listProductType(final Dialog dialog) {


        mApiProductInterface
                .getProductTypeList(Token.token)
                .enqueue(new Callback<List<ProductType>>() {
                    @Override
                    public void onResponse(Call<List<ProductType>> call, Response<List<ProductType>> response) {
                        if (response.code() == 200) {
                            mProductCategoryAdapter.setmList(response.body());
                        }
                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<List<ProductType>> call, Throwable t) {
                        t.printStackTrace();
                        Err.e("Error in refreshing data");
                    }
                });

    }

    public void listProductType(final SwipeRefreshLayout mSwipeToRefresh) {

        mApiProductInterface
                .getProductTypeList(Token.token)
                .enqueue(new Callback<List<ProductType>>() {
                    @Override
                    public void onResponse(Call<List<ProductType>> call, Response<List<ProductType>> response) {
                        if (response.code() == 200) {
                            mProductCategoryAdapter.setmList(response.body());
                        }
                        mSwipeToRefresh.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(Call<List<ProductType>> call, Throwable t) {
                        t.printStackTrace();
                        mSwipeToRefresh.setRefreshing(false);
                    }

                });
    }


    public void addProductTypeToMultipleProduct(String product_type, String details, final Dialog dialog, List<Product> mpProducts) {

        addProductType(product_type, details, dialog);

    }

    public void setProductCategoryAdapter(ProductCategoryAdapter mProductCategoryAdapter) {

        this.mProductCategoryAdapter = mProductCategoryAdapter;
    }

    public static ProductTypeApi getProductTypeApi(ProductCategoryAdapter mProductCategoryAdapter) {

        if (mProductTypeApi == null)
            mProductTypeApi = new ProductTypeApi(mProductCategoryAdapter);
        else
            mProductTypeApi.setProductCategoryAdapter(mProductCategoryAdapter);

        return mProductTypeApi;

    }

}

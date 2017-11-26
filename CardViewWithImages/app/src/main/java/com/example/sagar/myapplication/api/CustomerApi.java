package com.example.sagar.myapplication.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;

import com.example.sagar.myapplication.adapter.interfaces.CustomerAdapterInterface;
import com.example.sagar.myapplication.api.interfaces.ApiCustomerInterface;
import com.example.sagar.myapplication.modal.Data;
import com.example.sagar.myapplication.modal.Customer;
import com.example.sagar.myapplication.utill.Err;
import com.example.sagar.myapplication.utill.Token;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerApi{

    private ApiCustomerInterface mApiCustomerInterface;
    private Context mContext;
    private static CustomerApi mCustomerApi;
    private CustomerAdapterInterface mCustomerAdapterInterface;


    public  CustomerApi(Context mContext , CustomerAdapterInterface mCustomerAdapterInterface){
        this.mContext = mContext;
        mApiCustomerInterface   = ApiClient.getClient().create(ApiCustomerInterface.class);
        this.mCustomerAdapterInterface = mCustomerAdapterInterface;
    }

    // A  helper method to add customer to database
    public   void addCustomerMain(final Customer mCustomer , final ProgressDialog mProgressDialog ){
         mApiCustomerInterface.createCustomer(
                 mCustomer , Token.token
         ).enqueue(new Callback<Data>() {
             @Override
             public void onResponse(Call<Data> call, Response<Data> response) {
                  if(response.code()==200){
                      Err.s( mContext , "Customer added successfully!"  );
                      listCustomer(mProgressDialog);
                  }
                  else{
                     Err.s(mContext ,"Error in adding customer");
                     mProgressDialog.dismiss();
                  }
             }
             @Override
             public void onFailure(Call<Data> call, Throwable t) {
                  t.printStackTrace();
                  Err.s(mContext, "Error in adding new customer");
                  mProgressDialog.dismiss();
             }
         });
    }

    // A  method to add customer to database
    public void addCustomer(MultipartBody.Part image , final Customer customer , final  ProgressDialog mProgressDialog){
        mApiCustomerInterface.addImage(
                Token.token ,
                image
        ).enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if(response.code()==200){
                    Err.s( mContext , "Added image successfully" );
                    customer.setImage(response.body().getMessage());
                    addCustomerMain(customer , mProgressDialog);
                }
                else{
                    mProgressDialog.dismiss();
                    Err.s( mContext , "Error in adding image");
                }
            }
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                t.printStackTrace();
                Err.s(mContext , t.getMessage());
                mProgressDialog.dismiss();
            }
        });
    }


    // a method to delete the previous image and add new One
    public void deletePreviousImageAndAddNewOne(final Customer mCustomer , final ProgressDialog mProgressDialog , final MultipartBody.Part image  ){
              // if current image is not default image
              mApiCustomerInterface.replaceCurrentImage(
                      image , mCustomer.getId() , Token.token
              ).enqueue(new Callback<Data>() {
                  @Override
                  public void onResponse(Call<Data> call, Response<Data> response) {
                        if( response.code() == 200 ){
                            Err.s(mContext , "Image changed successfully");
                            listCustomer(mProgressDialog);
                        }
                        else{
                            Err.s(mContext , "Error in changing current image");
                            mProgressDialog.dismiss();
                        }
                  }
                  @Override
                  public void onFailure(Call<Data> call, Throwable t) {
                       t.printStackTrace();
                       Err.s(mContext,t.getMessage());
                       mProgressDialog.dismiss();
                  }
              });
    }

    // a method to delete image and add default one
    public  void deleteImageAndSetDefault(final Customer mCustomer  , final  ProgressDialog mProgressDialog  ){
        // a Api which will delete image from cloudinary(cloud)
        mApiCustomerInterface.deleteImageAndSetDefaultOne(mCustomer.getId(),Token.token)
                    .enqueue(new Callback<Data>() {
                        @Override
                        public void onResponse(Call<Data> call, Response<Data> response) {
                            if(response.code()==200){
                                Err.s(mContext,"Image Deleted");
                                listCustomer(mProgressDialog);
                            }
                            else{
                                Err.s(mContext,"Error in deleting customer");
                                mProgressDialog.dismiss();
                            }
                        }
                        @Override
                        public void onFailure(Call<Data> call, Throwable t) {
                             t.printStackTrace();
                             mProgressDialog.dismiss();
                        }
                    });
    }


    // a function to list customer
    public  void listCustomer(final ProgressDialog mProgressDialog){
        mApiCustomerInterface.listCustomer(Token.token)
                .enqueue(new Callback<List<Customer>>() {
                    @Override
                    public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                            if(response.code()==200){
                                mCustomerAdapterInterface.addCustomerList(response.body());
                            }
                            else{
                               Err.s(mContext,"Error in getting all customer");
                            }
                            mProgressDialog.dismiss();
                    }
                    @Override
                    public void onFailure(Call<List<Customer>> call, Throwable t) {
                        t.printStackTrace();
                        Err.s(mContext,t.getMessage());
                        mProgressDialog.dismiss();
                    }
                });
    }

    // a overloaded function to swipt to refresh layout
    public  void listCustomer(final SwipeRefreshLayout mSwipeToRefresh){
        mApiCustomerInterface.listCustomer(Token.token)
                .enqueue(new Callback<List<Customer>>() {
                    @Override
                    public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                        if(response.code()==200){
                            mCustomerAdapterInterface.addCustomerList(response.body());
                        }
                        else{
                            Err.s(mContext,"Error in getting all customer");
                        }
                        mSwipeToRefresh.setRefreshing(false);
                    }
                    @Override
                    public void onFailure(Call<List<Customer>> call, Throwable t) {
                        t.printStackTrace();
                        Err.s(mContext,t.getMessage());
                        mSwipeToRefresh.setRefreshing(false);
                    }
                });
    }


    public void deleteCustomer(final ProgressDialog mProgressDialog  , String id  ){
        mApiCustomerInterface.deleteCustomer( id , Token.token )
                    .enqueue(new Callback<Data>() {
                        @Override
                        public void onResponse(Call<Data> call, Response<Data> response) {
                            if(response.code()==200){
                                Err.s(mContext , "Customer deleted successfully!");
                                listCustomer(mProgressDialog);
                            }
                            else{
                                Err.s(mContext , "Error in deleting customer");
                                mProgressDialog.dismiss();
                            }
                        }
                        @Override
                        public void onFailure(Call<Data> call, Throwable t) {
                            t.printStackTrace();
                            Err.s(mContext,t.getMessage());
                            mProgressDialog.dismiss();
                            Err.s(mContext , t.getMessage());
                        }
                    });
    }


    public void updateCustomer(@Nullable MultipartBody.Part image , Customer withUpdate, final ProgressDialog mProgressDialog ){
        mApiCustomerInterface.updateCustomer( image , withUpdate , withUpdate.getId() , Token.token )
                        .enqueue(new Callback<Data>() {
                            @Override
                            public void onResponse(Call<Data> call, Response<Data> response) {
                                if( response.code() == 200 ){
                                    Err.s( mContext , "Customer Update successfully");
                                    listCustomer(mProgressDialog);
                                }
                                else{
                                    Err.s( mContext , "Data failed to update");
                                    mProgressDialog.dismiss();
                                }
                            }
                            @Override
                            public void onFailure(Call<Data> call, Throwable t) {
                                 t.printStackTrace();
                                 mProgressDialog.dismiss();
                                 Err.s(mContext , t.getMessage());
                            }
                        });
    }

    public void setCustomerAdapter(CustomerAdapterInterface mCustomerAdapterInterface) {
        this.mCustomerAdapterInterface = mCustomerAdapterInterface;
    }

    public void setContext(Context mContext){
        this.mContext = mContext;
    }

    public  static  CustomerApi  getCustomerApi( Context mContext , CustomerAdapterInterface mCustomerAdapterinterface  ){
        if(mCustomerApi ==null)
            mCustomerApi = new CustomerApi( mContext , mCustomerAdapterinterface );
        else
            mCustomerApi.setContext(mContext);
        return mCustomerApi;
    }
}

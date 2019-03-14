package com.example.sagar.myapplication.api;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;

import com.example.sagar.myapplication.adapter.interfaces.CustomerAdapterInterface;
import com.example.sagar.myapplication.api.interfaces.ApiCustomerInterface;
import com.example.sagar.myapplication.api.interfaces.ApiImageInterface;
import com.example.sagar.myapplication.modal.Data;
import com.example.sagar.myapplication.modal.Customer;
import com.example.sagar.myapplication.utill.Err;
import com.example.sagar.myapplication.utill.Token;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerApi {

    private ApiCustomerInterface mApiCustomerInterface;
    private ApiImageInterface mApiImageInterface;
    private Context mContext;
    private static CustomerApi mCustomerApi;
    private CustomerAdapterInterface mCustomerAdapterInterface;

    private CustomerApi(Context mContext, CustomerAdapterInterface mCustomerAdapterInterface) {
        this.mContext = mContext;
        mApiCustomerInterface = ApiClient.getClient().create(ApiCustomerInterface.class);
        mApiImageInterface = ApiClient.getClient().create(ApiImageInterface.class);
        this.mCustomerAdapterInterface = mCustomerAdapterInterface;
    }

    // a function to list customer
    public void listCustomer(final Dialog mProgressDialog) {
        mApiCustomerInterface.listCustomer(Token.token)
                .enqueue(new Callback<List<Customer>>() {
                    @Override
                    public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                        if (response.code() == 200) {
                            mCustomerAdapterInterface.addCustomerList(response.body());
                        } else {
                            Err.s(mContext, "Error in getting all customer");
                        }
                        mProgressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<List<Customer>> call, Throwable t) {
                        t.printStackTrace();
                        Err.s(mContext, t.getMessage());
                        mProgressDialog.dismiss();
                    }
                });
    }

    // A  helper method to add customer to database
    private void addCustomerHelper(final Customer mCustomer, final Dialog mProgressDialog) {
        mApiCustomerInterface.createCustomer(
                mCustomer, Token.token
        ).enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if (response.code() == 200) {
                    Err.s(mContext, "Customer added successfully!");
                    listCustomer(mProgressDialog);
                } else {
                    Err.s(mContext, "Error in adding customer");
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
    public void addCustomer(MultipartBody.Part image, final Customer customer, final Dialog mProgressDialog) {
        if (image == null) {
            addCustomerHelper(customer, mProgressDialog);
        } else {
            mApiImageInterface.addImage(Token.token, image).enqueue(new Callback<Data>() {
                @Override
                public void onResponse(Call<Data> call, Response<Data> response) {
                    if (response.code() == 200 && response.body().getSuccess()) {
                        Err.s(mContext, "Image added to database");
                        customer.setImage(response.body().getId());
                        addCustomerHelper(customer, mProgressDialog);
                    } else {
                        Err.s(mContext, "Error in adding image in database");
                        mProgressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<Data> call, Throwable t) {
                    t.printStackTrace();
                    Err.s(mContext, t.getMessage());
                    mProgressDialog.dismiss();
                }
            });
        }
    }

    // a overloaded function to swipt to refresh layout
    public void listCustomer(final SwipeRefreshLayout mSwipeToRefresh) {
        mApiCustomerInterface.listCustomer(Token.token)
                .enqueue(new Callback<List<Customer>>() {
                    @Override
                    public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                        if (response.code() == 200) {
                            mCustomerAdapterInterface.addCustomerList(response.body());
                        } else {
                            Err.s(mContext, "Error in getting all customer");
                        }
                        mSwipeToRefresh.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(Call<List<Customer>> call, Throwable t) {
                        t.printStackTrace();
                        Err.s(mContext, t.getMessage());
                        mSwipeToRefresh.setRefreshing(false);
                    }
                });
    }

    // a method to delete customer
    public void deleteCustomer(final Dialog mProgressDialog, final Customer mCustomer) {
        //check first if image is there or not
        if (mCustomer.getImage() == null) {
            // if image is there then first delete the image
            mApiImageInterface.deleteImage(Token.token, mCustomer.getImage()).enqueue(new Callback<Data>() {
                @Override
                public void onResponse(Call<Data> call, Response<Data> response) {
                    if (response.code() == 200 && response.body().getSuccess()) {
                        Err.s(mContext, "Image deleted successfully");
                        deleteCustomerHelperMethod(mCustomer, mProgressDialog);
                    } else {
                        Err.s(mContext, "Error in deleting image");
                        mProgressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<Data> call, Throwable t) {
                    t.printStackTrace();
                    mProgressDialog.dismiss();
                }
            });
        } else {
            // just delete customer from database
            deleteCustomerHelperMethod(mCustomer, mProgressDialog);
        }
    }

    private void deleteCustomerHelperMethod(final Customer mCustomer, final Dialog mDialog) {
        mApiCustomerInterface.deleteCustomer(mCustomer.getId(), Token.token)
                .enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data> call, Response<Data> response) {
                        if (response.code() == 200 && response.body().getSuccess()) {
                            listCustomer(mDialog);
                        } else {
                            Err.s(mContext, "Error in deleting customer");
                            mDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {
                        t.printStackTrace();
                        mDialog.dismiss();
                        Err.s(mContext, t.getMessage());
                    }
                });
    }

    /*There are three possible cases of updating
     * 1) either user deleted the previous image and add new one
     * 2) either user deleted the previous image and do not added new one
     * 3) user do not do anything to image
     * for first two cased we write isDeletedImage is true
     * the filed which we want to change is only going to send in Customer object rest of the filed will null
     * */
    public void updateCustomer(@Nullable final MultipartBody.Part image, final Customer mCustomer, final Dialog mDialog, Boolean isDeleteImage) {
        if (image != null) {
            // we  have to delete the previous image
            if (mCustomer.getImage() != null) {
                mApiImageInterface.deleteImage(Token.token, mCustomer.getImage())
                        .enqueue(new Callback<Data>() {
                            @Override
                            public void onResponse(Call<Data> call, Response<Data> response) {
                                if (response.code() == 200 && response.body().getSuccess()) {
                                    // and then have to add new one
                                    mApiImageInterface.addImage(Token.token, image)
                                            .enqueue(new Callback<Data>() {
                                                @Override
                                                public void onResponse(Call<Data> call, Response<Data> response) {
                                                    if (response.code() == 200 && response.body().getSuccess()) {
                                                        mCustomer.setImage(response.body().getId());
                                                        updateCustomerHelper(mDialog, mCustomer);
                                                    } else {
                                                        Err.s(mContext, "Error in adding new image");
                                                        mDialog.dismiss();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<Data> call, Throwable t) {
                                                    t.printStackTrace();
                                                    Err.s(mContext, "Error in adding image");
                                                    Err.s(mContext, t.getMessage());
                                                    mDialog.dismiss();
                                                }
                                            });
                                } else {
                                    Err.s(mContext, "Error in deleting previous image");
                                    mDialog.dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<Data> call, Throwable t) {
                                t.printStackTrace();
                                Err.s(mContext, "Error in deleting previous image");
                                Err.s(mContext, t.getMessage());
                                mDialog.dismiss();
                            }
                        });
            } else {
                //just add new image and update the customer
                mApiImageInterface.addImage(Token.token, image)
                        .enqueue(new Callback<Data>() {
                            @Override
                            public void onResponse(Call<Data> call, Response<Data> response) {
                                if (response.code() == 200 && response.body().getSuccess()) {
                                    mCustomer.setImage(response.body().getId());
                                    updateCustomerHelper(mDialog, mCustomer);
                                } else {
                                    Err.s(mContext, "Error in adding new image");
                                    mDialog.dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<Data> call, Throwable t) {
                                t.printStackTrace();
                                Err.s(mContext, "Error in adding image");
                                Err.s(mContext, t.getMessage());
                                mDialog.dismiss();
                            }
                        });
            }

        } else if (isDeleteImage) {
            //  we have to delete the previous image and don't have to add new one
            if (mCustomer.getImage() != null) {
                mApiImageInterface.deleteImage(Token.token, mCustomer.getImage())
                        .enqueue(new Callback<Data>() {
                            @Override
                            public void onResponse(Call<Data> call, Response<Data> response) {
                                if (response.code() == 200 && response.body().getSuccess()) {
                                    mCustomer.setImage(null);
                                    updateCustomerHelper(mDialog, mCustomer);
                                } else {
                                    mDialog.dismiss();
                                    Err.s(mContext, "Error in deleting previous image");
                                }
                            }

                            @Override
                            public void onFailure(Call<Data> call, Throwable t) {
                                t.printStackTrace();
                                Err.s(mContext, "Error in deleting previous image");
                                Err.s(mContext, t.getMessage());
                                mDialog.dismiss();
                            }
                        });
            } else {
                mCustomer.setImage(null);
                updateCustomerHelper(mDialog, mCustomer);
            }

        } else {
            // no changes in image
            updateCustomerHelper(mDialog, mCustomer);
        }
    }

    private void updateCustomerHelper(final Dialog mDialog, final Customer mCustomer) {
        mApiCustomerInterface.updateCustomer(mCustomer, mCustomer.getId(), Token.token)
                .enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data> call, Response<Data> response) {
                        if (response.code() == 200 && response.body().getSuccess()) {
                            listCustomer(mDialog);
                        } else {
                            Err.s(mContext, "failed to update customer");
                            mDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {
                        t.printStackTrace();
                        mDialog.dismiss();
                        Err.s(mContext, "Failed to update customer");
                        Err.s(mContext, t.getMessage());
                    }
                });
    }

    public void setCustomerAdapter(CustomerAdapterInterface mCustomerAdapterInterface) {
        this.mCustomerAdapterInterface = mCustomerAdapterInterface;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public static CustomerApi getCustomerApi(Context mContext, CustomerAdapterInterface mCustomerAdapterinterface) {
        if (mCustomerApi == null)
            mCustomerApi = new CustomerApi(mContext, mCustomerAdapterinterface);
        else
            mCustomerApi.setContext(mContext);
        return mCustomerApi;
    }
}

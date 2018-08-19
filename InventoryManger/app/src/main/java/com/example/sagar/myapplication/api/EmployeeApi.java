package com.example.sagar.myapplication.api;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;

import com.example.sagar.myapplication.api.interfaces.ApiImageInterface;
import com.example.sagar.myapplication.utill.Err;
import com.example.sagar.myapplication.utill.Token;
import com.example.sagar.myapplication.adapter.interfaces.EmployeeAdapterInterface;
import com.example.sagar.myapplication.api.interfaces.ApiEmployeeInterface;
import com.example.sagar.myapplication.modal.Data;
import com.example.sagar.myapplication.modal.Employee;
import com.example.sagar.myapplication.modal.Responce;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class EmployeeApi {
    private Context mContext;
    private EmployeeAdapterInterface mEployeeAdapterInterface;
    private ApiEmployeeInterface apiEmployeeInterface;
    private static EmployeeApi mEmployeeApi;
    private ApiImageInterface mApiImageInterface;

    private EmployeeApi(EmployeeAdapterInterface employeeAdapterInterface, Context mContext) {
        this.mEployeeAdapterInterface = employeeAdapterInterface;
        this.mContext = mContext;
        apiEmployeeInterface = ApiClient.getClient().create(ApiEmployeeInterface.class);
        mApiImageInterface = ApiClient.getClient().create(ApiImageInterface.class);
    }

    public  void listEmployee(final Dialog dialog){
         Call<List<Employee>> employee_obj  =  apiEmployeeInterface.getEmployee(Token.token);
         employee_obj.enqueue(new Callback<List<Employee>>(){
             @Override
             public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                    mEployeeAdapterInterface.addNewEmployeeList(response.body());
                    dialog.dismiss();
             }
             @Override
             public void onFailure(Call<List<Employee>> call,Throwable t){
                 t.printStackTrace();
                 Err.s(mContext,t.getMessage());
                 dialog.dismiss();
             }
         });
    }

    public  void getSortByName(final Dialog dialog){
        Integer id=1;
        Call<List<Employee>> employee_obj  =  apiEmployeeInterface.sortByName( id , Token.token );
        employee_obj.enqueue(new Callback<List<Employee>>(){
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                mEployeeAdapterInterface.addNewEmployeeList(response.body());
                dialog.dismiss();
            }
            @Override
            public void onFailure(Call<List<Employee>> call,Throwable t){
                t.printStackTrace();
                Err.s(mContext, t.getMessage());
                dialog.dismiss();
            }
        });
    }


    public  void getSortByAge(final Dialog dialog){
        Integer id = 1;
        Call<List<Employee>> employee_obj  =  apiEmployeeInterface.sortByAge( id , Token.token );
        employee_obj.enqueue(new Callback<List<Employee>>(){
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                mEployeeAdapterInterface.addNewEmployeeList(response.body());
                dialog.dismiss();
            }
            @Override
            public void onFailure(Call<List<Employee>> call,Throwable t){
                t.printStackTrace();
                Err.s(mContext, t.getMessage());
                dialog.dismiss();
            }
        });
    }

    public  void getSortByDateOfJoin(final Dialog dialog){
        Integer id = 1;
        Call<List<Employee>> employee_obj = apiEmployeeInterface.sortByDateOFJoin( id , Token.token );
        employee_obj.enqueue(new Callback<List<Employee>>(){
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                mEployeeAdapterInterface.addNewEmployeeList(response.body());
                dialog.dismiss();
            }
            @Override
            public void onFailure(Call<List<Employee>> call,Throwable t){
                t.printStackTrace();
                Err.s(mContext, t.getMessage());
                dialog.dismiss();
            }
        });
    }


    public  void createEmployee(MultipartBody.Part mImage,final Employee employee, final Dialog dialog){
            if(mImage == null ) {
                addEmployee(employee, dialog);
            } else {
                mApiImageInterface.addImage(Token.token, mImage).enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data> call, Response<Data> response) {
                            if(response.code()==200) {
                                employee.setImage(response.body().getId());
                                addEmployee(employee, dialog);
                            } else {
                                dialog.dismiss();
                                Err.s(mContext, "Not able to upload image");
                            }
                    }
                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {
                            dialog.dismiss();
                            t.printStackTrace();
                            Err.s(mContext, t.getMessage());
                    }
                });
            }
    }

    private void addEmployee(Employee employee, final Dialog dialog) {
        apiEmployeeInterface.createEmployee(employee, Token.token)
                .enqueue(new Callback<Responce>() {
                    @Override
                    public void onResponse(Call<Responce> call, Response<Responce> response) {
                        if(response.code()==200){
                            listEmployee(dialog);
                        }
                        else{
                            dialog.dismiss();
                            Err.s(mContext,"Something went wrong");
                        }
                    }
                    @Override
                    public void onFailure(Call<Responce> call, Throwable t){
                        dialog.dismiss();
                        t.printStackTrace();
                        Err.s(mContext,t.getMessage());
                    }
                });
    }

    public  void deleteEmployeeApi(final Employee mEmployee, final Dialog dialog) {
         if(mEmployee.getImage() != null) {
             mApiImageInterface.deleteImage(Token.token, mEmployee.getImage()).enqueue(new Callback<Data>() {
                 @Override
                 public void onResponse(Call<Data> call, Response<Data> response) {
                      if( response.code() == 200 && response.body().getSuccess()) {
                          apiEmployeeInterface
                                  .deleteEmployee(mEmployee.getId(), Token.token)
                                  .enqueue(new Callback<Data>() {
                                      @Override
                                      public void onResponse(Call<Data> call, Response<Data> response){
                                          if(response.code() == 200){
                                              listEmployee(dialog);
                                          }
                                          else{
                                              Err.e( Integer.toString(response.code()));
                                              dialog.dismiss();
                                          }
                                      }
                                      @Override
                                      public void onFailure( Call<Data> call , Throwable t ){
                                          dialog.dismiss();
                                          t.printStackTrace();
                                          Err.s(mContext, t.getMessage());
                                      }
                                  });
                      } else {
                          dialog.dismiss();
                          Err.s(mContext, "Something went wrong during deleting image");
                      }
                 }
                 @Override
                 public void onFailure(Call<Data> call, Throwable t) {
                    dialog.dismiss();
                    t.printStackTrace();
                    Err.s(mContext, t.getMessage());
                 }
             });
         } else {
             apiEmployeeInterface
                     .deleteEmployee(mEmployee.getId(), Token.token)
                     .enqueue(new Callback<Data>() {
                         @Override
                         public void onResponse(Call<Data> call, Response<Data> response){
                             if(response.code() == 200){
                                 listEmployee(dialog);
                             }
                             else{
                                 Err.e( Integer.toString(response.code()));
                                 dialog.dismiss();
                             }
                         }
                         @Override
                         public void onFailure( Call<Data> call , Throwable t ){
                             dialog.dismiss();
                             t.printStackTrace();
                             Err.s(mContext, t.getMessage());
                         }
                     });
         }
    }

    public void deleteEmployeeImage(final Employee mEmployee, final Dialog  dialog){
        mApiImageInterface.deleteImage(Token.token, mEmployee.getImage())
                    .enqueue(new Callback<Data>() {
                        @Override
                        public void onResponse(Call<Data> call, Response<Data> response) {
                             if(response.code() == 200) {
                                 listEmployee(dialog);
                             } else {
                                 Err.s(mContext, "Not able to delete image");
                                 dialog.dismiss();
                             }
                        }
                        @Override
                        public void onFailure(Call<Data> call, Throwable t) {
                            t.printStackTrace();
                            Err.s(mContext, "Not able to delete image");
                            dialog.dismiss();
                        }
                    });
    }

    public void listEmployee(final  SwipeRefreshLayout swipeRefreshLayout){
        Call<List<Employee>> employee_obj  =  apiEmployeeInterface.getEmployee(Token.token);
        employee_obj.enqueue(new Callback<List<Employee>>(){
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response){
                if( response.code() == 200 ) {
                    mEployeeAdapterInterface.addNewEmployeeList(response.body());
                }
                swipeRefreshLayout.setRefreshing(false);
            }
            @Override
            public void onFailure(Call<List<Employee>> call,Throwable t){
                t.printStackTrace();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }
    /*Three possible cases
        1) remove previous image and add new one
        2) remove previous image and don't add new one
        3) don't do anything
        for first two cases isImageDeleted will be true and for third case it will be false
        */
    public void updateEmployee(final Employee employee, final MultipartBody.Part mPart, final Dialog mDialog, boolean isImageDeleted) {
        // we have to upload new image
         if(mPart != null) {
            /*first delete previous image because we have to upload new image  image  */
            if(employee.getImage() != null) {
                mApiImageInterface.deleteImage(Token.token, employee.getImage())
                        .enqueue(new Callback<Data>() {
                            @Override
                            public void onResponse(Call<Data> call, Response<Data> response) {
                                if(response.code() == 200 && response.body().getSuccess()) {
                                             /*then upload  new image*/
                                    mApiImageInterface.addImage(Token.token, mPart).enqueue(new Callback<Data>() {
                                        @Override
                                        public void onResponse(Call<Data> call, Response<Data> response) {
                                                if(response.code() == 200 && response.body().getSuccess()) {
                                                    employee.setImage(response.body().getId());
                                                    updateEmployeeHelper(employee, mDialog);
                                                }
                                        }
                                        @Override
                                        public void onFailure(Call<Data> call, Throwable t) {
                                            Err.s(mContext, "Error in uploading new image");
                                            mDialog.dismiss();
                                            t.printStackTrace();
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
                                Err.s(mContext, "Error in deleting image");
                                mDialog.dismiss();
                            }
                        });
            }
            else {
                /*upload new image because there is no previous image*/
                mApiImageInterface.addImage(Token.token, mPart).enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data> call, Response<Data> response) {
                        if(response.code() == 200 && response.body().getSuccess()) {
                            updateEmployeeHelper(employee, mDialog);
                        } else {
                            mDialog.dismiss();
                            Err.s(mContext, "Error in uploading new image");
                        }
                    }
                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {
                        t.printStackTrace();
                        Err.s(mContext, "Error in uploading new image");
                    }
                });
            }
         }
         else if(isImageDeleted) {
             //user deleted the previous image and not added new one
             if(employee.getImage() != null && !employee.getImage().isEmpty()) {
                 mApiImageInterface.deleteImage(Token.token, employee.getImage())
                         .enqueue(new Callback<Data>() {
                             @Override
                             public void onResponse(Call<Data> call, Response<Data> response) {
                                 if(response.code() == 200 && response.body().getSuccess()) {
                                     employee.setImage(null);
                                     updateEmployeeHelper(employee, mDialog);
                                 } else {
                                     Err.s(mContext, "Error in deleting previous image");
                                     mDialog.dismiss();
                                 }
                             }
                             @Override
                             public void onFailure(Call<Data> call, Throwable t) {
                                 t.printStackTrace();
                                 mDialog.dismiss();
                             }
                         });
             }
             else{
                 employee.setImage(null);
                 updateEmployeeHelper(employee, mDialog);
             }
         } else {
            // user didn't change the image
             updateEmployeeHelper(employee, mDialog);
         }
    }

    private void updateEmployeeHelper(Employee mEmployee,final Dialog mDialog) {
        apiEmployeeInterface.editEmployee(mEmployee, Token.token)
                .enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data> call, Response<Data> response) {
                        if(response.code() == 200 && response.body().getSuccess()){
                            listEmployee(mDialog);
                        } else{
                            mDialog.dismiss();
                            Err.s(mContext, "Failed to update employee");
                        }
                    }
                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {
                        t.printStackTrace();
                        mDialog.dismiss();
                        Err.s(mContext,t.getMessage());
                    }
                });
    }

    public void setAdapter( EmployeeAdapterInterface mEmployeeAdapterInterface) {
        this.mEployeeAdapterInterface = mEmployeeAdapterInterface;
    }

    public  void setContext(Context mContext){
        this.mContext = mContext;
    }

    public static EmployeeApi getEmployeeApi(EmployeeAdapterInterface mEmployeeInterface, Context mContext) {
        if(mEmployeeApi == null)
            mEmployeeApi = new EmployeeApi(mEmployeeInterface , mContext);
        else
            mEmployeeApi.setContext(mContext);
        return mEmployeeApi;
    }

    public void addNewAdapter(EmployeeAdapterInterface employeeAdapterInterface){
        this.mEployeeAdapterInterface = employeeAdapterInterface;
    }

}

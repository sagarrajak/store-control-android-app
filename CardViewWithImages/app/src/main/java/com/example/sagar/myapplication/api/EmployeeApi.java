package com.example.sagar.myapplication.api;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;

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
public class EmployeeApi{
    private Context mContext;
    private EmployeeAdapterInterface mEployeeAdapterInterface;
    ApiEmployeeInterface apiEmployeeInterface;
    private static EmployeeApi mEmployeeApi ;

    public  EmployeeApi( EmployeeAdapterInterface employeeAdapterInterface , Context mContext  ){
        this.mEployeeAdapterInterface = employeeAdapterInterface;
        this.mContext = mContext;
        apiEmployeeInterface = ApiClient.getClient().create(ApiEmployeeInterface.class);
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


    public  void createEmployee(MultipartBody.Part mImage , Employee employee , final Dialog dialog){
            apiEmployeeInterface.createEmployee(mImage,employee,Token.token)
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


    public  void deleteEmployeeApi(String  mEmployeeId , final Dialog dialog){
          apiEmployeeInterface
                    .deleteEmployee( mEmployeeId , Token.token )
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


    public void deleteEmployeeImage(final String  mEmployeeId , final Dialog  dialog){
        apiEmployeeInterface
                        .deleteEmployeeImage(mEmployeeId , Token.token)
                                .enqueue(new Callback<Data>(){
                                        @Override
                                        public void onResponse(Call<Data> call , Response<Data> response ){
                                                if( response.code() == 200 ){
                                                    Err.e("image deleted");
                                                    listEmployee(dialog);
                                                }
                                                else{
                                                    dialog.dismiss();
                                                    Err.e("failed");
                                                }
                                        }

                                        @Override
                                        public void onFailure(Call<Data> call, Throwable t){
                                            Err.e("Employee deleted");
                                            t.printStackTrace();
                                            Err.s(mContext, t.getMessage());
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

    public void updateEmployee(Employee employee , MultipartBody.Part mPart , final Dialog mDialog){
            apiEmployeeInterface.editEmployee(mPart,employee,Token.token)
                    .enqueue(new Callback<Data>() {
                        @Override
                        public void onResponse(Call<Data> call, Response<Data> response) {
                                    if(response.code()==200){
                                        listEmployee(mDialog);
                                    }
                                    else{
                                        mDialog.dismiss();
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

    public void setAdapter( EmployeeAdapterInterface mEmployeeAdapterInterface){
        this.mEployeeAdapterInterface = mEmployeeAdapterInterface;
    }

    public  void setContext(Context mContext){
        this.mContext = mContext;
    }

    public static EmployeeApi getEmployeeApi(EmployeeAdapterInterface mEmployeeInterface , Context mContext ){
        if( mEmployeeApi == null )
            mEmployeeApi = new EmployeeApi(mEmployeeInterface , mContext);
        else
            mEmployeeApi.setContext(mContext);
        return mEmployeeApi;
    }

    public void addNewAdapter(EmployeeAdapterInterface employeeAdapterInterface){
        this.mEployeeAdapterInterface = employeeAdapterInterface;
    }

}

package com.example.sagar.myapplication.api;

import android.app.Dialog;
import android.content.Context;

import com.example.sagar.myapplication.Err;
import com.example.sagar.myapplication.adapter.interfaces.EmployeeAdapterInterface;
import com.example.sagar.myapplication.adapter.EmployeeGridAdapter;
import com.example.sagar.myapplication.api.interfaces.ApiEmployeeInterface;
import com.example.sagar.myapplication.modal.Data;
import com.example.sagar.myapplication.modal.Employee;
import com.example.sagar.myapplication.modal.Responce;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class EmployeeApi{
    private Context mContext;
    private EmployeeAdapterInterface employeeAdapterInterface;
    ApiEmployeeInterface apiEmployeeInterface;
    private static EmployeeApi mEmployeeApi ;

    public  EmployeeApi( Context mContext, EmployeeAdapterInterface employeeAdapterInterface){
        this.employeeAdapterInterface = employeeAdapterInterface;
        apiEmployeeInterface = ApiClient.getClient().create(ApiEmployeeInterface.class);
    }

    public EmployeeApi(EmployeeGridAdapter employeeAdapterInterface){
        this.employeeAdapterInterface = employeeAdapterInterface;
        apiEmployeeInterface = ApiClient.getClient().create(ApiEmployeeInterface.class);
    }

    public  void getEmployee(final Dialog dialog){
         Call<List<Employee>> employee_obj  =  apiEmployeeInterface.getEmployee();
         employee_obj.enqueue(new Callback<List<Employee>>(){
             @Override
             public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                    employeeAdapterInterface.addNewEmployeeList(response.body());
                    dialog.dismiss();
             }
             @Override
             public void onFailure(Call<List<Employee>> call,Throwable t){
                 t.printStackTrace();
             }
         });
    }


    public  void getSortByName(final Dialog dialog ){
        Integer id=1;
        Call<List<Employee>> employee_obj  =  apiEmployeeInterface.sortByName(id);
        employee_obj.enqueue(new Callback<List<Employee>>(){
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                employeeAdapterInterface.addNewEmployeeList(response.body());
                dialog.dismiss();
            }
            @Override
            public void onFailure(Call<List<Employee>> call,Throwable t){
                t.printStackTrace();
            }
        });
    }
    
    public  void getSortByAge(final Dialog dialog){
        Integer id = 1;
        Call<List<Employee>> employee_obj  =  apiEmployeeInterface.sortByAge(id);
        employee_obj.enqueue(new Callback<List<Employee>>(){
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                employeeAdapterInterface.addNewEmployeeList(response.body());
                dialog.dismiss();
            }
            @Override
            public void onFailure(Call<List<Employee>> call,Throwable t){
                t.printStackTrace();
            }
        });
    }

    public  void getSortByDateOfJoin(final Dialog dialog){
        Integer id = 1;
        Call<List<Employee>> employee_obj  =  apiEmployeeInterface.sortByDateOFJoin(id);
        employee_obj.enqueue(new Callback<List<Employee>>(){
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                employeeAdapterInterface.addNewEmployeeList(response.body());
                dialog.dismiss();
            }
            @Override
            public void onFailure(Call<List<Employee>> call,Throwable t){
                t.printStackTrace();
            }
        });
    }


    public boolean  addEmployeeImage(MultipartBody.Part body , final Employee employee , final Dialog dialog ){

          Call<Data>  obj = apiEmployeeInterface.addEmployeeImage(body);
          obj.enqueue(new Callback<Data>() {
              @Override
              public void onResponse(Call<Data> call, Response<Data> response){
                  if(response.body()!=null){
                      Err.e(response.body().getMessage());
                      try {
                          createEmployee(employee,response.body().getMessage() , dialog);
                      } catch (ParseException e){
                          Err.e("Parse exception");
                          e.printStackTrace();
                      }
                  }
              }
              @Override
              public void onFailure(Call<Data> call, Throwable t) {
                    Err.e("failed");
                    dialog.dismiss();
                    t.printStackTrace();
              }
          });

         return true;
    }


    /**  Method for creating employee  **/
    public  void createEmployee(Employee employee , String image  , final Dialog dialog) throws ParseException {
        Date date_of_birth = new SimpleDateFormat("dd/MM/yyyy").parse(employee.getDateOfBirth());
        Date date_of_join = new SimpleDateFormat("dd/MM/yyyy").parse(employee.getDateOfJoin());
        Call<Responce> responceCall =  apiEmployeeInterface.createEmployee(
                                                                    employee.getName(),
                                                                    date_of_birth,
                                                                    date_of_join,
                                                                    employee.getMail(),
                                                                    employee.getPanNum(),
                                                                    employee.getPhoneNumber(),
                                                                    "",
                                                                    image
                                                                );

        responceCall.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response){
                if( response.code() == 200 ){
                    Err.e("Employee created");
                    getEmployee(dialog);
                }
            }
            @Override
            public void onFailure(Call<Responce> call, Throwable t) {
//                Err.s( mContext , "Failed to create employee");
                    t.printStackTrace();
                    Err.e("In employee");
                    dialog.dismiss();
            }
        });
    }


    public  void deleteEmployeeApi(String  id , final Dialog dialog){
          Err.e(id);
          apiEmployeeInterface
                    .deleteEmployee(id)
                        .enqueue(new Callback<Data>() {
                            @Override
                            public void onResponse(Call<Data> call, Response<Data> response){
                                    if(response.code() == 200){
                                        getEmployee(dialog);
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
                            }
                        });
    }

    public void deleteEmployeeImage(final String  id , final Dialog  dialog){
        apiEmployeeInterface
                        .deleteEmployeImage(id)
                                .enqueue(new Callback<Data>(){
                                        @Override
                                        public void onResponse(Call<Data> call , Response<Data> response ){
                                                if( response.code() == 200 ){
                                                    Err.e("image deleted");
                                                    deleteEmployeeApi(id,dialog);
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
                                            dialog.dismiss();
                                        }
                                });

    }

    public static EmployeeApi getEmloyeeApi(EmployeeAdapterInterface mEmployeeInterface, Context mContext){
        if( mEmployeeApi == null ){
            mEmployeeApi = new EmployeeApi(mContext, mEmployeeInterface);
        }
        return mEmployeeApi;
    }

    public void addNewAdapter(EmployeeAdapterInterface employeeAdapterInterface){
        this.employeeAdapterInterface = employeeAdapterInterface;
    }

}

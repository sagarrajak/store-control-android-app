package com.example.sagar.myapplication.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.sagar.myapplication.Err;
import com.example.sagar.myapplication.URLconstant;
import com.example.sagar.myapplication.adapter.EmployeeAdapter;
import com.example.sagar.myapplication.modal.Employee;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;

public  class Employee_api{

    private RequestQueue mRequestQueue;
    private Context mContext;
    private EmployeeAdapter employeeAdapter;

    public Employee_api(Context mContext , EmployeeAdapter employeeAdapter){
        this.mContext = mContext;
        this.mRequestQueue = MySingleTon.getMySingleTon().getmRequestQueue();
        this.employeeAdapter = employeeAdapter;
    }
    public void   getEmployee(){
                JsonArrayRequest jsonObjectRequest = new JsonArrayRequest( Request.Method.GET , URLconstant.Employee_get, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response){
                        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
                        for (int i=0;i<response.length();i++) {
                            try {
                                 addEmployee( gson.fromJson( response.getJSONObject(i).toString(), Employee.class));
                            } catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                         Err.s(mContext,error.toString());
                    }
                });
                mRequestQueue.add(jsonObjectRequest);
    }
    public  void addEmployee( Employee obj ){
        employeeAdapter.addData(obj);
    }
    public void employeeAdd(){

    }
    public void employeeDelete(){

    }
    public  void editEmployee(){

    }
}

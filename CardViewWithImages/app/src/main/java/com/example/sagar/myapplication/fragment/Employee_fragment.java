package com.example.sagar.myapplication.fragment;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.SpaceItemDecoration;
import com.example.sagar.myapplication.adapter.EmployeeAdapter;
import com.example.sagar.myapplication.api.EmployeeApi;
import com.example.sagar.myapplication.modal.Employee;

import java.util.ArrayList;

public class Employee_fragment extends Fragment implements SearchView.OnQueryTextListener{

    Activity mActivity;
    private EmployeeAdapter mEmployeeAdapter;
    private EmployeeApi mEmployeeApi;

    private FloatingActionButton fab;
    public Employee_fragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_employee,container,false);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mEmployeeAdapter = EmployeeAdapter.getEmployeeAdapter(getContext());
        mEmployeeApi = EmployeeApi.getEmloyeeApi(mEmployeeAdapter,getActivity());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        setRecycleView();
        super.onActivityCreated(savedInstanceState);
    }

    private ProgressDialog createProgressDialog(){
            ProgressDialog dialog = new ProgressDialog(getActivity());
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            return  dialog;
    }

    private void setRecycleView(){

             RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycle_vew);
             recyclerView.addItemDecoration(new SpaceItemDecoration(1));
             GridLayoutManager mGridLayoutManager = new GridLayoutManager( getActivity(),2,RecyclerView.VERTICAL , true );
             recyclerView.setItemAnimator(new DefaultItemAnimator());
             recyclerView.setAdapter(mEmployeeAdapter);
             recyclerView.setLayoutManager(mGridLayoutManager);
             Dialog dialog = createProgressDialog();
             dialog.show();
             mEmployeeApi.getEmployee(dialog);

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.toolbar_menu ,menu);
        final MenuItem item = menu.findItem(R.id.employee_search);
        final  SearchView searchView  =  (SearchView)MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        MenuItemCompat.setOnActionExpandListener(item , new MenuItemCompat.OnActionExpandListener(){
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem){
//                mEmployeeAdapter.setFilter(mEmployeeAdapter.getMlist());
                return true;
            }
            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                return true;
            }
        });
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        mEmployeeAdapter.setFilter(filter(s));
        return true;
    }

    private  ArrayList<Employee> filter(String query ){
         ArrayList<Employee> rlist = new ArrayList<>();
         for( Employee obj : mEmployeeAdapter.getMlist() ){
             if(obj.getName().contains(query)||obj.getMail().contains(query))
                 rlist.add(obj);
         }
        return  rlist;
    }

}

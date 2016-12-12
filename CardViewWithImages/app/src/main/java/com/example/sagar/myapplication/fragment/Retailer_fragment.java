package com.example.sagar.myapplication.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.SpaceItemDecoration;
import com.example.sagar.myapplication.adapter.EmployeeAdapter;
import com.example.sagar.myapplication.storingClass.Employee;

import java.util.ArrayList;

public class Retailer_fragment extends Fragment{
    public Retailer_fragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_retailer, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        createRecycleView();
        super.onActivityCreated(savedInstanceState);
    }
    private void createRecycleView(){
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycle_vew);
        recyclerView.addItemDecoration(new SpaceItemDecoration(1));
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(  getActivity() , 2 , RecyclerView.VERTICAL , true );
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new EmployeeAdapter( getActivity(), create() ) );
        recyclerView.setLayoutManager(mGridLayoutManager);
    }

    private ArrayList<Employee> create(){
        ArrayList<Employee> mLIst = new ArrayList<>();
        mLIst.add(new Employee("ramesh chnadra hajiram malik" , "sagarrajak858@gmail.com",10));
        mLIst.add(new Employee("ramesh chnadra hajiram malik" , "sagarrajak858@gmail.com",10));
        mLIst.add(new Employee("ramesh chnadra hajiram malik" , "sagarrajak858@gmail.com",10));
        mLIst.add(new Employee("ramesh chnadra hajiram malik" , "sagarrajak858@gmail.com",10));
        mLIst.add(new Employee("ramesh chnadra hajiram malik" , "sagarrajak858@gmail.com",10));
        mLIst.add(new Employee("ramesh chnadra hajiram malik" , "sagarrajak858@gmail.com",10));
        mLIst.add(new Employee("ramesh chnadra hajiram malik" , "sagarrajak858@gmail.com",10));
        mLIst.add(new Employee("ramesh chnadra hajiram malik" , "sagarrajak858@gmail.com",10));
        mLIst.add(new Employee("ramesh chnadra hajiram malik" , "sagarrajak858@gmail.com",10));
        mLIst.add(new Employee("ramesh chnadra hajiram malik" , "sagarrajak858@gmail.com",10));
        mLIst.add(new Employee("ramesh chnadra hajiram malik" , "sagarrajak858@gmail.com",10));
        mLIst.add(new Employee("ramesh chnadra hajiram malik" , "sagarrajak858@gmail.com",10));
        mLIst.add(new Employee("ramesh chnadra hajiram malik" , "sagarrajak858@gmail.com",10));
        mLIst.add(new Employee("ramesh chnadra hajiram malik" , "sagarrajak858@gmail.com",10));
        return mLIst;
    }
}
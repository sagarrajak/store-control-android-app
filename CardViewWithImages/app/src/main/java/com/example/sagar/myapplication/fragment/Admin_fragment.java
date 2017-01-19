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


public class Admin_fragment extends Fragment{
    public Admin_fragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_admin, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        setRecycleView();

        super.onActivityCreated(savedInstanceState);
    }

    private void setRecycleView(){
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycle_view);
        recyclerView.addItemDecoration(new SpaceItemDecoration(1));
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(  getActivity() , 2 , RecyclerView.VERTICAL , true );
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(mGridLayoutManager);
    }

}

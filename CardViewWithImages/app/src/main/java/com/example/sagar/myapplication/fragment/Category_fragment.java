package com.example.sagar.myapplication.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.sagar.myapplication.Err;
import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.adapter.CategoryAdapter;
import com.example.sagar.myapplication.api.ProductTypeApi;

public class Category_fragment extends Fragment{


    private CategoryAdapter mCategoryAdapter;
    private ProductTypeApi mProductTypeApi;

    public Category_fragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_category, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        setRecycleView();
        mCategoryAdapter = CategoryAdapter.getmCategoryAdapter(getContext());
        mProductTypeApi = ProductTypeApi.getmProductTypeApi(mCategoryAdapter);
        super.onActivityCreated(savedInstanceState);
    }
    private void setRecycleView(){
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycle_view);
        if(recyclerView != null ){
            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager( getContext() , LinearLayoutManager.VERTICAL , false);
            recyclerView.setLayoutManager(mLinearLayoutManager);
            recyclerView.setAdapter(mCategoryAdapter);
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
            inflater.inflate(R.menu.menu_category_fragment , menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case   R.id.category_product :
                Err.s(getContext() ," working for category fragment");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

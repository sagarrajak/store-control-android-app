package com.example.sagar.myapplication.element.product_category.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.sagar.myapplication.CustumProgressDialog;
import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.adapter.product.ProductCategoryAdapter;
import com.example.sagar.myapplication.api.ProductTypeApi;
import com.example.sagar.myapplication.element.product_category.Create_new_product_category;

public class Category_fragment extends Fragment{


    private ProductCategoryAdapter mProductCategoryAdapter;
    private ProductTypeApi mProductTypeApi;
    private SwipeRefreshLayout mSwipetoRefresh;
    public Category_fragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductCategoryAdapter =   new ProductCategoryAdapter(getActivity());
        mProductTypeApi         =   ProductTypeApi.getProductTypeApi(mProductCategoryAdapter);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate( R.layout.fragment_category ,  container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        setRecycleView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu_category_fragment  , menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void setRecycleView(){
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycle_view);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager( getContext() , LinearLayoutManager.VERTICAL , false );
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setAdapter(mProductCategoryAdapter);
        Dialog dialog       =   CustumProgressDialog.getProgressDialog(getActivity());
        dialog.show();
        mProductTypeApi.listProductType(dialog);
        mSwipetoRefresh = (SwipeRefreshLayout) getView().findViewById(R.id.swipe_to_refresh_category);
        mSwipetoRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mProductTypeApi.listProductType(mSwipetoRefresh);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_category_add :
                Intent intent  = new Intent( getActivity().getApplicationContext() , Create_new_product_category.class );
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}

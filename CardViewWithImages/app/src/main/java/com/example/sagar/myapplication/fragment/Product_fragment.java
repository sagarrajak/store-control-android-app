package com.example.sagar.myapplication.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.sagar.myapplication.Err;
import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.SpaceItemDecoration;
import com.example.sagar.myapplication.adapter.ProductAdapter;
import com.example.sagar.myapplication.api.ProductApi;
import com.example.sagar.myapplication.intent.product.Activity_create_product;

public class Product_fragment extends Fragment{

    private ProductAdapter mProductAdapter;
    private ProductApi mProductApi;
    private RecyclerView recyclerView;


    public Product_fragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate( R.layout.fragment_product_frag , container , false );
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        createRecycleView();
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void createRecycleView(){

        recyclerView = (RecyclerView)getView().findViewById(R.id.recycle_product_view);
        mProductAdapter  = ProductAdapter.getProductAdapter( getContext() , "fragment" );
        mProductApi  = ProductApi.getmProductApi(mProductAdapter);

        if( recyclerView != null ){
            recyclerView.addItemDecoration(new SpaceItemDecoration(1));
            GridLayoutManager mGridLayoutManager = new GridLayoutManager(  getActivity() , 2 , RecyclerView.VERTICAL , true );
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mProductAdapter);
            recyclerView.setLayoutManager(mGridLayoutManager);
            ProgressDialog mProgressDialog = createProgressDialog();
            mProgressDialog.show();
            mProductApi.listProduct(mProgressDialog);
        }
        else{
            Err.e("fuck recycleview is null");
        }
    }
    private ProgressDialog createProgressDialog(){
        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        return  dialog;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu_product_fragment  , menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case   R.id.product_product :
                Intent intent = new Intent( getActivity().getApplicationContext() , Activity_create_product.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

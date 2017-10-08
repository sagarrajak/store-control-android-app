package com.example.sagar.myapplication.element.product.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.sagar.myapplication.CustumProgressDialog;
import com.example.sagar.myapplication.adapter.ProductListAdapter;
import com.example.sagar.myapplication.api.ProductApi;
import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.utill.SpaceItemDecoration;
import com.example.sagar.myapplication.adapter.ProductGridAdapter;
import com.example.sagar.myapplication.element.product.Activity_create_product;

public class Product_fragment extends Fragment{

    private ProductApi mProductApi;
    private RecyclerView recyclerView;
    private Boolean isListLayout;
    private MenuItem listToGrid;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    public Product_fragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate( R.layout.fragment_product_frag , container , false );
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        isListLayout = false;
        mSwipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipe_to_refresh_product);
        createRecycleView();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mProductApi.listProduct(mSwipeRefreshLayout);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    private void createRecycleView(){

            recyclerView = (RecyclerView)getView().findViewById( R.id.recycle_product_view );
            recyclerView.addItemDecoration(new SpaceItemDecoration(1));
            GridLayoutManager mGridLayoutManager = new GridLayoutManager(  getActivity() , 2 , RecyclerView.VERTICAL , true );
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(ProductGridAdapter.getProductAdapter(getActivity()));
            recyclerView.setLayoutManager(mGridLayoutManager);
            ProgressDialog mProgressDialog = CustumProgressDialog.getProgressDialog(getActivity());
            mProgressDialog.show();
            mProductApi = ProductApi.getmProductApi(ProductGridAdapter.getProductAdapter(getActivity()));
            mProductApi.listProduct(mProgressDialog);

    }



    private void changeGridToList(){

            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager( getActivity() , LinearLayoutManager.VERTICAL , false);
            recyclerView.setLayoutManager(mLinearLayoutManager);
            recyclerView.setAdapter(ProductListAdapter.getProductListAdapter(getActivity()));
            ProgressDialog mProgressDialog  = CustumProgressDialog.getProgressDialog(getActivity());
            mProgressDialog.show();
            mProductApi.addNewAdapter(ProductListAdapter.getProductListAdapter(getActivity()));
            mProductApi.listProduct(mProgressDialog);

    }

    private void changeListToGrid(){

            recyclerView.addItemDecoration(new SpaceItemDecoration(1));
            GridLayoutManager mGridLayoutManager = new GridLayoutManager(  getActivity() , 2 , RecyclerView.VERTICAL , true );
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(ProductGridAdapter.getProductAdapter(getActivity()));
            recyclerView.setLayoutManager(mGridLayoutManager);
            ProgressDialog mProgressDialog = CustumProgressDialog.getProgressDialog(getActivity());
            mProgressDialog.show();
            mProductApi.addNewAdapter(ProductGridAdapter.getProductAdapter(getActivity()));
            mProductApi.listProduct(mProgressDialog);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){

            inflater.inflate(R.menu.menu_product_fragment  , menu);
            listToGrid = menu.findItem(R.id.list_to_grid);
            if(isListLayout) listToGrid.setIcon(R.drawable.ic_grid_24dp);
            else  listToGrid.setIcon(R.drawable.ic_list_black_24dp);
            super.onCreateOptionsMenu( menu , inflater );

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case   R.id.product_product :
                Intent intent = new Intent( getActivity().getApplicationContext() , Activity_create_product.class);
                startActivity(intent);
                break;
            case R.id.list_to_grid :
                if(isListLayout){
                    listToGrid.setIcon(R.drawable.ic_list_black_24dp);
                    changeListToGrid();
                }
                else{
                    listToGrid.setIcon(R.drawable.ic_grid_24dp);
                    changeGridToList();
                }
                isListLayout=!isListLayout;
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

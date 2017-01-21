package com.example.sagar.myapplication.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.example.sagar.myapplication.Err;
import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.SpaceItemDecoration;
import com.example.sagar.myapplication.adapter.RetailerGridAdapter;
import com.example.sagar.myapplication.adapter.RetailerListAdapter;
import com.example.sagar.myapplication.api.RetailerApi;

public class Retailer_fragment extends Fragment{

    private RetailerApi mRetailerApi;
    private RetailerGridAdapter mRetailerGridAdapter;
    private RecyclerView recyclerView;
    private boolean isListView;
    private MenuItem menuItem;

    public Retailer_fragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        setHasOptionsMenu(true);
        mRetailerGridAdapter = RetailerGridAdapter.getRetailerGridAdapter(getContext());
        mRetailerApi = RetailerApi.getmReteilerApi(mRetailerGridAdapter);
        isListView = false;
        super.onCreate(savedInstanceState);
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
        recyclerView = (RecyclerView)getView().findViewById(R.id.recycle_view);
        recyclerView.addItemDecoration(new SpaceItemDecoration(1));
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(  getActivity() , 2 , RecyclerView.VERTICAL , true );
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mRetailerGridAdapter);
        recyclerView.setLayoutManager(mGridLayoutManager);
        Dialog dialog = CustumProgressDialog.getProgressDialog(getContext());
        dialog.show();
        mRetailerApi.listEmployee(dialog);
    }

    private void gridToList(){
        RetailerListAdapter mRetailerListAdapter = RetailerListAdapter.getRetailerListAdapter(getContext());
        mRetailerApi.setRetailerAdapterInterface(mRetailerListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mRetailerListAdapter);
        Dialog dialog = CustumProgressDialog.getProgressDialog(getContext());
        dialog.show();
        mRetailerApi.listEmployee(dialog);
    }

    private void  lisToGrid(){
        recyclerView.setAdapter(mRetailerGridAdapter);
        recyclerView.addItemDecoration(new SpaceItemDecoration(1));
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(  getActivity() , 2 , RecyclerView.VERTICAL , true );
        recyclerView.setLayoutManager(mGridLayoutManager);
        recyclerView.setAdapter(mRetailerGridAdapter);
        mRetailerApi.setRetailerAdapterInterface(mRetailerGridAdapter);
        Dialog dialog = CustumProgressDialog.getProgressDialog(getContext());
        dialog.show();
        mRetailerApi.listEmployee(dialog);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu_retailer_fragment ,menu);
        menuItem = menu.findItem(R.id.list_to_grid);
        if(isListView)
            menuItem.setIcon(R.drawable.ic_grid_24dp);
        else
            menuItem.setIcon(R.drawable.ic_list_black_24dp);
        
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case   R.id.category_product :
                Err.s(getContext()  , "working for category fragment");
                break;
            case   R.id.list_to_grid :
                    Err.s(getContext()  , "working with my ass");
                    if(!isListView){
                        isListView=true;
                        lisToGrid();
                        menuItem.setIcon(R.drawable.ic_list_black_24dp);
                    }
                    else{
                        isListView=false;
                        gridToList();
                        menuItem.setIcon(R.drawable.ic_grid_24dp);
                    }
                break;
            case   R.id.fragment_menu_sort :
                Err.s(getContext()  , "working with my ass");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

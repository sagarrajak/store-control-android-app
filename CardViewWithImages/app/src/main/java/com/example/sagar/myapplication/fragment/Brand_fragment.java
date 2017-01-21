package com.example.sagar.myapplication.fragment;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import com.example.sagar.myapplication.adapter.BrandAdapter;
import com.example.sagar.myapplication.api.BrandApi;

public class Brand_fragment extends Fragment{

    private BrandAdapter brandAdapter;
    private BrandApi bradApi;

    public Brand_fragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_brand_fragment, container, false);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        creatRecycleView();
        super.onViewCreated(view, savedInstanceState);
    }
    private void creatRecycleView(){
        brandAdapter  = BrandAdapter.getmBrandAdapter(getContext());
        bradApi = BrandApi.getBrandApi(brandAdapter);
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(brandAdapter);
        Dialog dialog = createProgressDialog();
        dialog.show();
        bradApi.listBrand(dialog);
    }
    private Dialog createProgressDialog(){
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(true);
        return  progressDialog;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu_brand_fragment,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case   R.id.brand_product :
                Err.s(getContext(),"working for brand fragment");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

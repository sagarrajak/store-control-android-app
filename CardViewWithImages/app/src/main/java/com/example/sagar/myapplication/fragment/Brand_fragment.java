package com.example.sagar.myapplication.fragment;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import com.example.sagar.myapplication.api.BrandApi;
import com.example.sagar.myapplication.intent.brand.Create_brand_activity;
import com.example.sagar.myapplication.utill.Err;
import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.adapter.BrandAdapter;

public class Brand_fragment extends Fragment{

    private BrandAdapter brandAdapter;
    private BrandApi bradApi;
    private SwipeRefreshLayout swipeRefreshLayout ;

    public Brand_fragment(){}

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
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        creatRecycleView();
        setSwipeToRefresh();
        super.onActivityCreated(savedInstanceState);
    }

    private void setSwipeToRefresh() {
        swipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipe_to_refresh_brand);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(){
//                swipeRefreshLayout.setVisibility(SwipeRefreshLayout.VISIBLE);
                bradApi.listBrand(swipeRefreshLayout);
            }
        });
    }


    private void creatRecycleView(){

        brandAdapter  = BrandAdapter.getmBrandAdapter(getContext());
        bradApi = BrandApi.getBrandApi(brandAdapter);
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(brandAdapter);
        Dialog dialog = CustumProgressDialog.getProgressDialog(getActivity());
        dialog.show();
        bradApi.listBrand(dialog);

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
                Intent intent = new Intent(getActivity().getApplicationContext()  , Create_brand_activity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}

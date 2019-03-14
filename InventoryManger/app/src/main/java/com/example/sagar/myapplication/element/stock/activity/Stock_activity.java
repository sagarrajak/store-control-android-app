package com.example.sagar.myapplication.element.stock.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;

import com.example.sagar.myapplication.CustumProgressDialog;
import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.adapter.StockAdapter;
import com.example.sagar.myapplication.element.stock.BottomSheetHelper;
import com.example.sagar.myapplication.utill.ui.NavigationDrawer;
import com.example.sagar.myapplication.api.*;

public class Stock_activity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private RecyclerView mRecycleView;
    private StockAdapter mStockAdapter;
    private StockApi mStockApi;


    //bottomSheetElement
    private RadioButton mBottomSheetRadioButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_stock_activity);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setToolbar();
        setNavigationView();
        setAdapter();
        setRecycleView();
    }

    private void setAdapter() {
        mStockAdapter = StockAdapter.getStockAdapter(getBaseContext());
        mStockApi = new StockApi(getBaseContext());
    }

    private void setToolbar() {
        setTitle("Stock");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setNavigationView() {
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        NavigationDrawer navigationDrawer = new NavigationDrawer(toolbar, mDrawerLayout, mNavigationView, this);
        navigationDrawer.setNavigationDrawer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.stock_menu, menu);
        return true;
    }

    private void setRecycleView() {
        mRecycleView = (RecyclerView) findViewById(R.id.recycle_view_stock_activity);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));
        ProgressDialog mProgessDialog = CustumProgressDialog.getProgressDialog(Stock_activity.this);
        mProgessDialog.show();
        mStockApi.listStock(mProgessDialog);
        mRecycleView.setAdapter(mStockAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.stock_menu_filter:
                Intent intent = new Intent(getBaseContext(), Activity_filter_intent.class);
                startActivity(intent);
                break;
            case R.id.stock_menu_add_new_stock:
                Intent create_stock = new Intent(getBaseContext(), Activity_create_new_stock.class);
                startActivity(create_stock);
                break;
            case R.id.stock_menu_sort:
                bottomSheetHelper();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void bottomSheetHelper() {
        BottomSheetHelper mBottomSheetHelper = new BottomSheetHelper(Stock_activity.this, mStockApi, getLayoutInflater());
        mBottomSheetHelper.showButtomSheet();
    }
}

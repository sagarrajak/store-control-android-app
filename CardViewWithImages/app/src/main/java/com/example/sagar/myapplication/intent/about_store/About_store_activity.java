package com.example.sagar.myapplication.intent.about_store;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.sagar.myapplication.utill.NavigationDrawer;
import com.example.sagar.myapplication.R;

public class About_store_activity extends AppCompatActivity{

    private Toolbar toolbar;
    private NavigationDrawer navigationDrawer;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_store_activity);


        toolbar = (Toolbar) findViewById(R.id.about_store_toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.about_store_drawer_layout);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setNavigationView();
    }
    private void setNavigationView(){
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        NavigationDrawer navigationDrawer = new NavigationDrawer(toolbar,mDrawerLayout,mNavigationView,this);
        navigationDrawer.setNavigationDrawer();
    }
}

package com.example.sagar.myapplication.intent;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.sagar.myapplication.NavigationDrawer;
import com.example.sagar.myapplication.R;

public class Setting_activity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationDrawer mNavigationDrawer;
    private  NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        toolbar = (Toolbar) findViewById(R.id.setting_toolbar);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setNavigationView();
    }
    private void setNavigationView(){
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        NavigationDrawer navigationDrawer = new NavigationDrawer(toolbar,mDrawerLayout,mNavigationView,this);
        navigationDrawer.setNavigationDrawer();
    }
}

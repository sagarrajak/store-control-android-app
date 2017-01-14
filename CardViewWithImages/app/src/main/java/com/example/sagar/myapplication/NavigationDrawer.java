package com.example.sagar.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.sagar.myapplication.intent.About_store_activity;
import com.example.sagar.myapplication.intent.MainActivity;
import com.example.sagar.myapplication.intent.Product_activity;
import com.example.sagar.myapplication.intent.Setting_activity;
import com.example.sagar.myapplication.intent.Work_profile_activity;

public class NavigationDrawer{

    private DrawerLayout  mDrawerLayout;
    private NavigationView mNavigationView;
    private Activity mActivity;
    private Toolbar mToolbar;

    public  NavigationDrawer(Toolbar mToolbar , DrawerLayout mDrawerLayout,NavigationView mNavigationView,Activity mActivity){
            this.mDrawerLayout = mDrawerLayout;
            this.mNavigationView = mNavigationView;
            this.mActivity = mActivity;
            this.mToolbar = mToolbar;
    }

    public  void setNavigationDrawer(){

            mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case  R.id.admin_employee :
                            Intent intent = new Intent(mActivity.getApplicationContext() , MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            mDrawerLayout.closeDrawers();
                            mActivity.startActivity(intent);
                            break;
                        case R.id.store_Product :
                            Intent intent1 = new Intent(mActivity.getApplicationContext() , Product_activity.class);
                            intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            mActivity.startActivity(intent1);
                            mDrawerLayout.closeDrawers();
                            break;
                        case  R.id.store_details :
                            Intent detials = new Intent( mActivity.getApplicationContext() , About_store_activity.class);
                            mActivity.startActivity(detials);
                            break;
                        case  R.id.store_workProfile :
                            Intent work_profile = new Intent( mActivity.getApplicationContext() , Work_profile_activity.class );
                            mActivity.startActivity(work_profile);
                            break;
                        case R.id.store_Setting :
                            Intent setting  = new Intent(mActivity.getApplicationContext() , Setting_activity.class);
                            mActivity.startActivity(setting);
                            break;
                    }
                    return false;
                }
            });

            ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle( mActivity , mDrawerLayout, mToolbar , R.string.open , R.string.close ){
                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                }
                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                }
            };
            mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
            mActionBarDrawerToggle.syncState();
    }
}

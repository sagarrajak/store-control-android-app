package com.example.sagar.myapplication.intent;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.sagar.myapplication.NavigationDrawer;
import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.fragment.Category_fragment;
import com.example.sagar.myapplication.fragment.Product_fragment;

import java.util.ArrayList;

public class Product_activity extends AppCompatActivity{

    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate( Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        mToolbar        =  (Toolbar) findViewById(R.id.toolbar);
        mNavigationView =  (NavigationView) findViewById(R.id.navigation_view);
        mViewPager      =  (ViewPager)findViewById(R.id.product_view_pager);
        mTabLayout      =  (TabLayout)findViewById(R.id.product_tablayout);
        mDrawerLayout   = (DrawerLayout) findViewById(R.id.product_drawer_layout);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setNavigationView();
        setTabLayout();
    }
    private void setNavigationView(){
        NavigationDrawer mNavigationDrawer = new NavigationDrawer(mToolbar , mDrawerLayout , mNavigationView ,this);
        mNavigationDrawer.setNavigationDrawer();
    }
    private void setTabLayout(){
        CustumViewPager custumViewPager = new CustumViewPager(getSupportFragmentManager());
        custumViewPager.addNewFragment(new Category_fragment(),"CATEGORY");
        custumViewPager.addNewFragment(new Product_fragment(),"PRODUCT");
        mViewPager.setAdapter(custumViewPager);
        mTabLayout.setupWithViewPager(mViewPager);
    }
   class  CustumViewPager extends FragmentPagerAdapter{
       ArrayList<Fragment> frag = new ArrayList<>();
       ArrayList<String>   tag = new ArrayList<>();
       public CustumViewPager(FragmentManager fm) {
           super(fm);
       }
       @Override
       public Fragment getItem(int i) {
           return frag.get(i);
       }
       @Override
       public int getCount() {
           return frag.size();
       }
       public void addNewFragment( Fragment mFragment , String  mTag ){
           frag.add(mFragment);
           tag.add(mTag);
       }
       @Override
       public CharSequence getPageTitle(int i) {
           return tag.get(i);
       }
   }
}

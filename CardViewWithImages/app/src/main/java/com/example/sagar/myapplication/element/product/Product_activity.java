package com.example.sagar.myapplication.element.product;

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

import com.example.sagar.myapplication.api.ProductApi;
import com.example.sagar.myapplication.utill.ui.NavigationDrawer;
import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.adapter.product.ProductGridAdapter;
import com.example.sagar.myapplication.element.product.fragment.Brand_fragment;
import com.example.sagar.myapplication.element.product_category.fragment.Category_fragment;
import com.example.sagar.myapplication.element.product.fragment.Product_fragment;

import java.util.ArrayList;

public class Product_activity extends AppCompatActivity{

    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private DrawerLayout mDrawerLayout;

    private ProductApi mProductApi ;
    private ProductGridAdapter mProductGridAdapter;


    @Override
    protected void onCreate( Bundle savedInstanceState ){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        setUiElement();
        setToolbar();

    }

    private void setUiElement(){

        mToolbar        =  (Toolbar) findViewById(R.id.toolbar);
        mViewPager      =  (ViewPager)findViewById(R.id.product_view_pager);
        mTabLayout      =  (TabLayout)findViewById(R.id.product_tablayout);
        mDrawerLayout   =  (DrawerLayout) findViewById(R.id.product_drawer_layout);
        mProductGridAdapter = ProductGridAdapter.getProductAdapter(this);
        mProductApi  = ProductApi.getmProductApi(mProductGridAdapter);
    }

    private void setToolbar(){

        setSupportActionBar(mToolbar);
        setTitle("Product");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setNavigationView();
        setTabLayout();

    }

    private void setNavigationView(){

        mNavigationView =  (NavigationView)findViewById(R.id.navigation_view);
        NavigationDrawer mNavigationDrawer = new NavigationDrawer(mToolbar , mDrawerLayout , mNavigationView ,this);
        mNavigationDrawer.setNavigationDrawer();

    }

    private void setTabLayout(){

        CustumViewPager custumViewPager = new CustumViewPager(getSupportFragmentManager());
        custumViewPager.addNewFragment(new Category_fragment(),"CATEGORY");
        custumViewPager.addNewFragment(new Product_fragment(),"PRODUCT");
        custumViewPager.addNewFragment(new Brand_fragment(),"BRAND");
        mViewPager.setAdapter(custumViewPager);
        mViewPager.setOffscreenPageLimit(3);
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

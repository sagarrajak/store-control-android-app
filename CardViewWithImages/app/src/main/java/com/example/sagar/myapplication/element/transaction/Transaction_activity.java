package com.example.sagar.myapplication.element.transaction;
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

import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.element.transaction.fragment.Check_in_stocks_fragment;
import com.example.sagar.myapplication.element.transaction.fragment.Check_out_items_fragment;
import com.example.sagar.myapplication.element.transaction.fragment.Transaction_history_fragment;
import com.example.sagar.myapplication.utill.ui.NavigationDrawer;

import java.util.ArrayList;

public class Transaction_activity extends AppCompatActivity{
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private TabLayout mTablayout;
    private ViewPager mViewPager;
    private NavigationView mNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_transection_intent);
            setUiElement();
            setToolbar();
            setNavigationView();
            setTabLayout();
    }
    private void setUiElement(){
            toolbar         = (Toolbar)        findViewById(R.id.toolbar);
            mDrawerLayout   = (DrawerLayout)   findViewById(R.id.drawer_layout_transaction);
            mTablayout      = (TabLayout)      findViewById(R.id.tab_layout_transcation);
            mViewPager      = (ViewPager)      findViewById(R.id.view_pager_transaction);
            mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
    }
    private void setNavigationView(){
            NavigationDrawer navigationDrawer =  new NavigationDrawer(toolbar,mDrawerLayout,mNavigationView,this);
            navigationDrawer.setNavigationDrawer();
    }
    private void setToolbar(){
            setTitle("Transaction");
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    private void setTabLayout(){
           ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
           Fragment f1,f2,f3;
           f1 = new Check_out_items_fragment();
           f2 = new Transaction_history_fragment();
           f3 = new Check_in_stocks_fragment();
           mViewPagerAdapter.addFragment(f1 , "History");
           mViewPagerAdapter.addFragment(f3 , "Checkin stocks");
           mViewPagerAdapter.addFragment(f2 , "Checkout items");
           mViewPager.setAdapter(mViewPagerAdapter);
           mViewPager.setOffscreenPageLimit(2);
           mTablayout.setupWithViewPager(mViewPager);
    }
    public  class  ViewPagerAdapter extends FragmentPagerAdapter{
            ArrayList<Fragment> mListFragment = new ArrayList<>();
            ArrayList<String> mListString = new ArrayList<>();
            public ViewPagerAdapter(FragmentManager fm) {
                super(fm);
            }
            @Override
            public Fragment getItem(int position) {
                return  mListFragment.get(position);
            }
            public void addFragment(Fragment fragment , String  title ){
                mListFragment.add(fragment);
                mListString.add(title);
            }
            @Override
            public CharSequence getPageTitle(int position) {
                return mListString.get(position);
            }
            @Override
            public int getCount() {
                return mListString.size();
            }
    }

}

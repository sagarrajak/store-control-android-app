package com.example.sagar.myapplication.element.employee;

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

import com.example.sagar.myapplication.element.employee.employee.Employee_fragment;
import com.example.sagar.myapplication.utill.ui.NavigationDrawer;
import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.element.customer.Customer_fragment;
import com.example.sagar.myapplication.element.retailer.Retailer_fragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();
        setTitle("Employee");
        setUiElement();
        setNavigationView();
        setTabLayout();
    }

    private void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setUiElement() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
    }

    private void setNavigationView() {
        NavigationView mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        NavigationDrawer navigationDrawer = new NavigationDrawer(mToolbar, mDrawerLayout, mNavigationView, this);
        navigationDrawer.setNavigationDrawer();
    }

    private void setTabLayout() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        Fragment f1, f2, f3;
        f1 = new Employee_fragment();
        f2 = new Retailer_fragment();
        f3 = new Customer_fragment();
        adapter.addFragemnt("Employee", f1);
        adapter.addFragemnt("Retailer", f2);
        adapter.addFragemnt("Customer", f3);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> mListFrgament = new ArrayList<>();
        ArrayList<String> mListString = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return mListFrgament.get(i);
        }

        @Override
        public int getCount() {
            return mListFrgament.size();
        }

        public void addFragemnt(String name, Fragment mFragment) {
            mListFrgament.add(mFragment);
            mListString.add(name);
        }

        @Override
        public CharSequence getPageTitle(int i) {
            return mListString.get(i);
        }
    }
}

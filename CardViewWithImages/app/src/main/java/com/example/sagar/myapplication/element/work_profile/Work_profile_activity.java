package com.example.sagar.myapplication.element.work_profile;

import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.element.work_profile.fragment.Employee_right_fragment;
import com.example.sagar.myapplication.element.work_profile.fragment.Work_profile_fragment;
import com.example.sagar.myapplication.utill.ui.NavigationDrawer;

import java.util.ArrayList;

public class Work_profile_activity extends AppCompatActivity {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private TabLayout  mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_profile_activity);
        setTitle("Work profile");
        setToolbar();
        setUiElement();
        setNavigationView();
        setTabLayout();
    }

    private void  setToolbar(){
        mToolbar =  (Toolbar)findViewById(R.id.work_profile_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setNavigationView(){
        NavigationView mNavigationView = (NavigationView) findViewById(R.id.work_profile_navigation_view);
        NavigationDrawer navigationDrawer = new NavigationDrawer(mToolbar,mDrawerLayout,mNavigationView,this);
        navigationDrawer.setNavigationDrawer();
    }

    private void setUiElement(){
        mDrawerLayout = (DrawerLayout)findViewById(R.id.work_profile_drawer_layout);
        mTabLayout  =  (TabLayout) findViewById(R.id.work_profile_tab_layout);
        mViewPager  =  (ViewPager) findViewById(R.id.work_profile_view_pager);
    }

    private void setTabLayout(){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        Fragment f1,f2;
        f1  = new Employee_right_fragment();
        f2  = new Work_profile_fragment();;
        adapter.addFragemnt("Work profile",f1);
        adapter.addFragemnt("Employee right",f2);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private class  ViewPagerAdapter extends FragmentPagerAdapter{
        ArrayList<Fragment>  mListFrgament  =  new ArrayList<>();
        ArrayList<String>    mListString    =  new ArrayList<>();
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int i) {
            return mListFrgament.get(i);
        }
        @Override
        public int getCount(){
            return mListFrgament.size();
        }
        public  void addFragemnt(String name,Fragment mFragment){
            mListFrgament.add(mFragment);
            mListString.add(name);
        }
        @Override
        public CharSequence getPageTitle(int i) {
            return  mListString.get(i);
        }
    }
}

package com.example.sagar.myapplication.intent.employee;

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

import com.example.sagar.myapplication.utill.NavigationDrawer;
import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.fragment.Admin_fragment;
import com.example.sagar.myapplication.fragment.Employee_fragment;
import com.example.sagar.myapplication.fragment.Retailer_fragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private   Toolbar toolbar ;
    private   DrawerLayout drawerLayout;
    private   NavigationView mNavigationView ;
    private   TabLayout  mTabLayout;
    private   ViewPager    mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            toolbar       =  (Toolbar)findViewById(R.id.toolbar);
            drawerLayout  =  (DrawerLayout)findViewById(R.id.drawer_layout);
            mTabLayout   =   (TabLayout) findViewById(R.id.tab_layout);
            mViewPager   =   (ViewPager) findViewById(R.id.view_pager);

         setSupportActionBar(toolbar);
         setTitle("Employee");
         getSupportActionBar().setDisplayShowHomeEnabled(true);
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         setNavigationView();
         setTabLayout();
    }


    private void setNavigationView(){
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        NavigationDrawer navigationDrawer = new NavigationDrawer(toolbar,drawerLayout,mNavigationView,this);
        navigationDrawer.setNavigationDrawer();
    }


    private void setTabLayout(){
          ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
          Fragment f1,f2,f3;
          f1  = new Employee_fragment();
          f2  = new Retailer_fragment();
          f3  = new Admin_fragment();
          adapter.addFragemnt( "Employee" , f1 );
          adapter.addFragemnt( "Retailer" , f2 );
          adapter.addFragemnt( "Admin"    , f3 );
          mViewPager.setAdapter(adapter);
          mViewPager.setOffscreenPageLimit(3);
          mTabLayout.setupWithViewPager(mViewPager);
    }

    class  ViewPagerAdapter extends FragmentPagerAdapter{
        ArrayList<Fragment>    mListFrgament  =  new ArrayList<>();
        ArrayList<String>      mListString    =  new ArrayList<>();
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

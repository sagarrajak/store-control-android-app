package com.example.sagar.myapplication;

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

import com.example.sagar.myapplication.fragment.Admin_fragment;
import com.example.sagar.myapplication.fragment.Employee_fragment;
import com.example.sagar.myapplication.fragment.Retailer_fragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

   private   Toolbar toolbar ;
   private   DrawerLayout drawerLayout;
   private   NavigationView mNavigationView ;
   private   TabLayout  mTabLayout;
   private   ViewPager  mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            toolbar       =  (Toolbar)findViewById(R.id.toolbar);
            drawerLayout  =  (DrawerLayout)findViewById(R.id.drawer_layout);
            mTabLayout   =   (TabLayout) findViewById(R.id.tab_layout);
            mViewPager   =   (ViewPager) findViewById(R.id.view_pager);

         setSupportActionBar(toolbar);
         getSupportActionBar().setDisplayShowHomeEnabled(true);
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         setNavigationView();
         setTabLayout();

    }

    private void setNavigationView(){
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
//        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                   switch (item.getItemId()){
//                   case   R.id.admin_employee :
//                       Intent intent =  new Intent( getApplicationContext() ,Create_employee.class);
//                       startActivity(intent);
//                       break;
//                 }
//                return false;
//            }
//        });
//        ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle( this , drawerLayout , toolbar , R.string.open_drawer , R.string.close_drawer ){
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                super.onDrawerClosed(drawerView);
//            }
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//            }
//        };
//        drawerLayout.addDrawerListener(mActionBarDrawerToggle);
//        mActionBarDrawerToggle.syncState();
        NavigationDrawer navigationDrawer = new NavigationDrawer(toolbar,drawerLayout,mNavigationView,this);
        navigationDrawer.setNavigationDrawer();
    }

    private void setTabLayout(){
          ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
          adapter.addFragemnt( "Employee" , new Employee_fragment() );
          adapter.addFragemnt( "Retailer" , new Retailer_fragment() );
          adapter.addFragemnt( "Admin"  ,  new Admin_fragment());
          mViewPager.setAdapter(adapter);
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

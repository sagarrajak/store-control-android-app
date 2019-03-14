package com.example.sagar.myapplication.element.about_store;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.sagar.myapplication.utill.ui.NavigationDrawer;
import com.example.sagar.myapplication.R;

public class About_store_activity extends AppCompatActivity {

    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private TextView mName, mMail, mPhoneNumber;
    private TextView mAboutStore, mAddressMain, mAddressStreet;
    private TextView mAddressNeighbourHood, mAddressCity, mAddressState, mAddressZipCode;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_store_activity);
        mDrawerLayout = findViewById(R.id.about_store_drawer_layout);
        setUiElement();
        setToolbar();
        setNavigationView();
    }

    private void setUiElement() {
        mName = findViewById(R.id.view_about_store_name);
        mMail = findViewById(R.id.view_about_store_mail);
        mPhoneNumber = findViewById(R.id.view_about_store_phone_num);
        mAboutStore = findViewById(R.id.view_about_store_details);
        mAddressMain = findViewById(R.id.view_about_store_address);
        mAddressStreet = findViewById(R.id.view_about_store_address_street);
        mAddressNeighbourHood = findViewById(R.id.view_about_store_address_neighbourhood);
        mAddressCity = findViewById(R.id.view_about_store_address_city);
        mAddressState = findViewById(R.id.view_about_store_address_state);
        mAddressZipCode = findViewById(R.id.view_about_store_address_zipcode);
        mToolbar = findViewById(R.id.view_about_store_toolbar);
    }

    private void setNavigationView() {
        mNavigationView = findViewById(R.id.navigation_view);
        NavigationDrawer navigationDrawer = new NavigationDrawer(mToolbar, mDrawerLayout, mNavigationView, this);
        navigationDrawer.setNavigationDrawer();
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about_store, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_Store_details:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.example.sagar.myapplication.element.about_store;

import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.support.v7.widget.Toolbar;

import com.example.sagar.myapplication.R;

public class Edit_about_store_activity extends AppCompatActivity {

    private EditText name;
    private EditText phoneNumber;
    private EditText emailAdress;
    private EditText address, street, postOffice, neighbourHood, city, state, zipcode;
    private EditText details;
    private Spinner  emailSpinner, phoneNumberSpinner;
    private Toolbar  mToolbar;
    private CoordinatorLayout mCoordinateLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details_about_store);
        importUiElement();
        setToolbar();
    }

    private void importUiElement() {
        name = findViewById(R.id.edit_store_name);
        phoneNumber = findViewById(R.id.edit_store_phone_num);
        emailAdress = findViewById(R.id.edit_store_email_address);
        address = findViewById(R.id.edit_store_address);
        street = findViewById(R.id.edit_store_street);
        postOffice =  findViewById(R.id.edit_store_post_office);
        neighbourHood = findViewById(R.id.edit_store_neighbourhood);
        city = findViewById(R.id.edit_store_city);
        state = findViewById(R.id.edit_store_state);
        zipcode = findViewById(R.id.edit_store_zipcode);
        details = findViewById(R.id.about_Store_details);
        phoneNumberSpinner =  findViewById(R.id.edit_store_phone_num_spinner);
        emailSpinner = findViewById(R.id.edit_store_email_spinner);
        mToolbar = findViewById(R.id.edit_store_toolbar);
        mCoordinateLayout = findViewById(R.id.edit_store_coordinatorlayout);
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ok_cancel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ok :
                break;
           case  R.id.cancel:
                break;
        }
        return true;
    }
}

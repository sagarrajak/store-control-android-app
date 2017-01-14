package com.example.sagar.myapplication.intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.modal.Employee;

public class About_employee_activity extends AppCompatActivity {
    private Toolbar     toolbar;
    private ImageView    mImageView;
    private TextView mName , mEmail , mPhoneNumber  , mPanNumber , mDateOfJoin,mDateOfBirth;
//    private final String url = "http://res.cloudinary.com/droxr0kdp/image/upload/w_400,h_400,c_crop,g_face/w_400/v1482011353/";
    private final String url = "http://res.cloudinary.com/droxr0kdp/image/upload/v1482011353/";
    private Employee employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_activity);
        toolbar = (Toolbar) findViewById(R.id.about_employee_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mImageView = (ImageView) findViewById(R.id.about_employee_image);
        mName = (TextView) findViewById(R.id.about_employee_name);
        mEmail = (TextView) findViewById(R.id.about_employee_mail);
        mPhoneNumber = (TextView) findViewById(R.id.about_employee_phone_num);
        mPanNumber = (TextView) findViewById(R.id.about_employee_pan_num);
        mDateOfBirth    = (TextView) findViewById(R.id.about_employee_date_of_birth);
        mDateOfJoin = (TextView) findViewById(R.id.about_employee_date_of_join);

            employee = (Employee)getIntent().getSerializableExtra("Employee");

            if (employee != null ){
                    mName.setText(employee.getName());
                    mEmail.setText(employee.getMail());
                    mDateOfJoin.setText(employee.getDateOfJoin());
                    mDateOfBirth.setText(employee.getDateOfBirth());
                    mPanNumber.setText(employee.getPanNum());
                    mPhoneNumber.setText(employee.getPhoneNumber());
            }



        Glide.with(this.getApplicationContext())
                .load(url+employee.getImage())
                    .centerCrop()
                        .placeholder(R.drawable.employee)
                            .crossFade()
                                    .into(mImageView);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about_employee_menu , menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         switch (item.getItemId()){
             case R.id.about_employee_edit_menu :
                 Toast.makeText(getApplicationContext(),"WORKING" , Toast.LENGTH_LONG).show();
                 break;
         }
        return true;
    }

}

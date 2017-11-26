package com.example.sagar.myapplication.element.employee.employee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.modal.Employee;

public class About_employee_activity extends AppCompatActivity {


    private Toolbar       toolbar;
    private ImageView     mImageView;
    private TextView      mName,mEmail,mPhoneNumber,mDateOfJoin,mDateOfBirth;
    private final String url = "http://res.cloudinary.com/droxr0kdp/image/upload/v1482011353/";
    private Employee employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_activity);
        toolbar = (Toolbar) findViewById(R.id.about_employee_toolbar);

        setUiElement();
        setToolBar();
        employee = (Employee)getIntent().getSerializableExtra("Employee");
        if (employee != null ){
                setTitle(employee.getName().getName());
                mName.setText(employee.getName().getName()+" "+employee.getName().getLast());
                mEmail.setText(employee.getMail().getValue());
                mDateOfJoin.setText(employee.getDateOfJoin().toString());
                mDateOfBirth.setText(employee.getDateOfBirth().toString());
                if(employee.getPhoneNumber()!=null)
                    mPhoneNumber.setText(employee.getPhoneNumber().getValue());
                else
                    mPhoneNumber.setText("not added!");
        }
        else{
            finish();
        }
        Glide.with(this.getApplicationContext())
                .load(url+employee.getImage())
                    .centerCrop()
                        .placeholder(R.drawable.employee)
                            .crossFade()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(mImageView);

    }

    private void setToolBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setUiElement(){
        mImageView     = (ImageView)findViewById(R.id.about_employee_image);
        mName          = (TextView)findViewById(R.id.about_employee_name);
        mEmail         = (TextView)findViewById(R.id.about_employee_mail);
        mPhoneNumber   = (TextView)findViewById(R.id.about_employee_phone_num);
        mDateOfBirth   = (TextView)findViewById(R.id.about_employee_date_of_birth);
        mDateOfJoin    = (TextView)findViewById(R.id.about_employee_date_of_join);
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
                 Intent intent =  new Intent(About_employee_activity.this,Edit_employee_activity.class);
                 intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 intent.putExtra("Employee",employee);
                 startActivity(intent);
                 break;
         }
        return true;
    }

}

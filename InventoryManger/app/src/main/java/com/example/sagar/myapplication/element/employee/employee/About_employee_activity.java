package com.example.sagar.myapplication.element.employee.employee;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.modal.Address;
import com.example.sagar.myapplication.modal.Employee;
import com.example.sagar.myapplication.modal.Mail;
import com.example.sagar.myapplication.modal.PhoneNum;
import com.example.sagar.myapplication.utill.Err;


public class About_employee_activity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView mEmployeeImageView;
    private TextView mName, mFullName, mEmail, mEmailType, mPhoneNumber, mPhoneNumberType, mDateOfJoin, mDateOfBirth, mWorkProfile;
    private final String url = "http://res.cloudinary.com/droxr0kdp/image/upload/v1482011353/";
    private Employee employee;
    private TextView address, neighbourhood, city, state, zipcode, street;
    private ImageView mMailView, mPhoneNumberView, mWorkProfileView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_activity);
        toolbar = findViewById(R.id.about_employee_toolbar);
        setUiElement();
        setToolBar();
        employee = (Employee) getIntent().getSerializableExtra("Employee");
        setData();
        setOnClickListenerMail(employee.getMail());
        setOnClickListernerPhoneNumber(employee.getPhoneNumber());
        setOnClickListernerWorkProfile();
        Glide.with(this.getApplicationContext())
                .load(url + employee.getImage())
                .centerCrop()
                .placeholder(R.drawable.employee)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mEmployeeImageView);

    }

    private void setOnClickListernerWorkProfile() {
        //// TODO: 5/11/18
    }

    private void setOnClickListernerPhoneNumber(final PhoneNum mPhoneNumber) {
        if (mPhoneNumber.getValue() != null) {
            mPhoneNumberView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + mPhoneNumber.getValue()));
                    startActivity(intent);
                }
            });
        }
    }

    private void setOnClickListenerMail(final Mail mEmail) {
        if (mEmail.getValue() != null) {
            mMailView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", mEmail.getValue(), null));
                    startActivity(Intent.createChooser(intent, "Send email..."));
                }
            });
        }
    }

    private void setToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setUiElement() {
        /*Ui component for employee image*/
        mEmployeeImageView = findViewById(R.id.about_employee_image);
        /*Ui component for Name */
        mName = findViewById(R.id.about_employee_name);
        mFullName = findViewById(R.id.about_employee_full_name);
        /*Ui component for emial address*/
        mEmail = findViewById(R.id.about_employee_mail);
        mEmailType = findViewById(R.id.about_employee_email_type);
        mMailView = findViewById(R.id.about_employee_mail_button);
        /*Ui component for phone number*/
        mPhoneNumber = findViewById(R.id.about_employee_phone_num);
        mPhoneNumberType = findViewById(R.id.about_employee_phone_number_type);
        mPhoneNumberView = findViewById(R.id.about_employee_call_phone_number);
        /*date of birth and date of join*/
        mDateOfBirth = findViewById(R.id.about_employee_date_of_birth);
        mDateOfJoin = findViewById(R.id.about_employee_date_of_join);
        /*Ui element for address component*/
        address = findViewById(R.id.about_employee_address);
        neighbourhood = findViewById(R.id.about_employee_address_neighbourhood);
        city = findViewById(R.id.about_employee_address_city);
        zipcode = findViewById(R.id.about_employee_address_zipcode);
        state = findViewById(R.id.about_employee_address_state);
        street = findViewById(R.id.about_employee_address_street);
        /*Work profile component*/
        mWorkProfile = findViewById(R.id.about_employee_work_profile);
        mWorkProfileView = findViewById(R.id.about_employee_work_profile_info);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about_employee_menu, menu);
        return true;
    }

    private void setAddressHelper(String mAddressComponent, TextView mtextView) {
        if (mAddressComponent != null && !mAddressComponent.isEmpty())
            mtextView.setText(mAddressComponent);
        else
            mtextView.setText("");
    }

    private String getString(String tem) {
        if (tem == null || tem.isEmpty())
            return "";
        else
            return tem + " ";
    }

    private void setData() {
        setTitle(employee.getName().getName());
        //set Name
        mName.setText(employee.getName().getName() + " " + employee.getName().getLast());
        String build = getString(employee.getName().getName()) + getString(employee.getName().getMiddle()) +
                getString(employee.getName().getLast()) + getString(employee.getName().getSuffix());
        mFullName.setText(build);
        //set Email Address (can be nullable)
        if (employee.getMail() != null) {
            if (employee.getMail().getValue() != null)
                mEmail.setText(employee.getMail().getValue());
            if (employee.getMail().getSub() != null)
                mEmailType.setText(employee.getMail().getSub());
        }
        //Set Date of birth and Date of join (can be nullable)
        if (employee.getDateOfJoin() != null)
            mDateOfJoin.setText(employee.getDateOfJoin().toString());
        if (employee.getDateOfBirth() != null)
            mDateOfBirth.setText(employee.getDateOfBirth().toString());
        //set phone number type(can be nullable)
        if (employee.getPhoneNumber() != null) {
            if (employee.getPhoneNumber().getValue() != null)
                mPhoneNumber.setText(employee.getPhoneNumber().getValue());
            if (employee.getPhoneNumber().getSub() != null)
                mPhoneNumberType.setText(employee.getPhoneNumber().getSub());
        }
        //set Address can be nullable
        if (employee.getAddress() != null) {
            if (employee.getAddress().getAddress() != null)
                setAddressHelper(employee.getAddress().getAddress(), address);
            if (employee.getAddress().getCity() != null)
                setAddressHelper(employee.getAddress().getCity(), city);
            if (employee.getAddress().getState() != null)
                setAddressHelper(employee.getAddress().getState(), state);
            if (employee.getAddress().getNeighborhood() != null)
                setAddressHelper(employee.getAddress().getNeighborhood(), neighbourhood);
            if (employee.getAddress().getStreet() != null)
                setAddressHelper(employee.getAddress().getStreet(), street);
            if (employee.getAddress().getZipcode() != null)
                setAddressHelper(employee.getAddress().getZipcode(), zipcode);
            Err.e(employee.getAddress().toString());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_employee_edit_menu:
                Intent intent = new Intent(About_employee_activity.this, Edit_employee_activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Employee", employee);
                startActivity(intent);
                break;
        }
        return true;
    }

}

package com.example.sagar.myapplication.element.customer;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.modal.Customer;
import com.example.sagar.myapplication.modal.Mail;
import com.example.sagar.myapplication.modal.PhoneNum;

import butterknife.BindView;
import butterknife.ButterKnife;

public class About_customer_activity extends AppCompatActivity {
    //tollbar
    @BindView(R.id.about_customer_toolbar)
    Toolbar mToobar;
    @BindView(R.id.about_customer_name)
    TextView mName;
    @BindView(R.id.about_customer_full_name)
    TextView mFullName;
    //Email  component
    @BindView(R.id.about_customer_mail)
    TextView mEmail;
    @BindView(R.id.about_customer_mail_type)
    TextView mEmailType;
    @BindView(R.id.about_customer_mail_button)
    ImageView mMailButton;
    //Phone number component
    @BindView(R.id.about_customer_phone_num)
    TextView mPhoneNumber;
    @BindView(R.id.about_customer_phone_number_type)
    TextView mPhoneNumberType;
    @BindView(R.id.about_customer_phone_number_bottom)
    ImageView mImagePhoneNumberButton;
    //address component
    @BindView(R.id.about_customer_address)
    TextView mAddress;
    @BindView(R.id.about_customer_address_city)
    TextView mAddressCity;
    @BindView(R.id.about_customer_address_state)
    TextView mAddressState;
    @BindView(R.id.about_customer_address_street)
    TextView mAddressStreet;
    @BindView(R.id.about_customer_address_neighbourhood)
    TextView mAddressNeighbourHood;
    @BindView(R.id.about_customer_address_zipcode)
    TextView mAddressZipCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_customer_);
        ButterKnife.bind(this);
        Customer mCustomer = (Customer) getIntent().getSerializableExtra("Customer");
        setToolbar();
        setData(mCustomer);
        setMailClickListener(mCustomer.getMail());
        setPhoneNumberClickListener(mCustomer.getPhoneNum());
    }

    private void setToolbar() {
        setSupportActionBar(mToobar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about_employee_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private String getString(String tem) {
        if (tem == null || tem.isEmpty())
            return "";
        else
            return tem + " ";
    }

    private void addressHelperMethod(TextView mTextView, String text) {
        if (text != null && !text.isEmpty())
            mTextView.setText(text);
        else
            mTextView.setText("");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_employee_edit_menu:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setData(Customer mCustomer) {
        mName.setText(mCustomer.getName().getName() + " " + mCustomer.getName().getLast());
        //full Name required filed
        String mBuilder = getString(mCustomer.getName().getName()) +
                getString(mCustomer.getName().getMiddle()) +
                getString(mCustomer.getName().getLast()) +
                getString(mCustomer.getName().getSuffix());
        mFullName.setText(mBuilder);
        //set phone number(can be nullable)
        if (mCustomer.getPhoneNum() != null) {
            if (mCustomer.getPhoneNum().getValue() != null)
                mPhoneNumber.setText(mCustomer.getPhoneNum().getValue());
            if (mCustomer.getPhoneNum().getSub() != null)
                mPhoneNumberType.setText(mCustomer.getPhoneNum().getSub());
        }
        //set mail type (can be nullable)
        if (mCustomer.getMail() != null) {
            if (mCustomer.getMail().getValue() != null)
                mEmail.setText(mCustomer.getMail().getValue());
            if (mCustomer.getMail().getSub() != null)
                mEmailType.setText(mCustomer.getMail().getSub());
        }
        //addressComponent(can be nullable)
        if (mCustomer.getAddress() != null) {
            if (mCustomer.getAddress().getAddress() != null)
                addressHelperMethod(mAddress, mCustomer.getAddress().getAddress());
            if (mCustomer.getAddress().getCity() != null)
                addressHelperMethod(mAddressCity, mCustomer.getAddress().getCity());
            if (mCustomer.getAddress().getNeighborhood() != null)
                addressHelperMethod(mAddressNeighbourHood, mCustomer.getAddress().getNeighborhood());
            if (mCustomer.getAddress().getStreet() != null)
                addressHelperMethod(mAddressStreet, mCustomer.getAddress().getStreet());
            if (mCustomer.getAddress().getState() != null)
                addressHelperMethod(mAddressState, mCustomer.getAddress().getState());
            if (mCustomer.getAddress().getZipcode() != null)
                addressHelperMethod(mAddressZipCode, mCustomer.getAddress().getZipcode());
        }
    }

    private void setMailClickListener(final Mail mEmail) {
        mMailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", mEmail.getValue(), null));
                startActivity(Intent.createChooser(intent, "Send email..."));
            }
        });
    }

    private void setPhoneNumberClickListener(final PhoneNum mPhoneNum) {
        mImagePhoneNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + mPhoneNum.getValue()));
                startActivity(intent);
            }
        });
    }
}

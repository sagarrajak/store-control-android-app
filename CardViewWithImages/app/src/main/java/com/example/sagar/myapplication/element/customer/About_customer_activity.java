package com.example.sagar.myapplication.element.customer;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class About_customer_activity extends AppCompatActivity{


    @BindView(R.id.about_customer_name) TextView mName;
    @BindView(R.id.about_customer_full_name) TextView mFullName;
    //Email  component
    @BindView(R.id.about_customer_mail)  TextView mEmail;
    @BindView(R.id.about_customer_mail_type) TextView mEmailType;
    @BindView(R.id.about_customer_mail_button) ImageView mMailButton;
    //Phone number component
    @BindView(R.id.about_customer_phone_num) TextView mPhoneNumber;
    @BindView(R.id.about_customer_phone_number_type) TextView mPhoneNumberType;
    @BindView(R.id.about_customer_phone_number_bottom) ImageView mImagePhoneNumberButton;
    //address component
    @BindView(R.id.about_customer_address) TextView mAddress;
    @BindView(R.id.about_customer_address_city) TextView mAddressCity;
    @BindView(R.id.about_customer_address_state) TextView mAddressState;
    @BindView(R.id.about_customer_address_street) TextView mAddressStreet;
    @BindView(R.id.about_customer_address_neighbourhood) TextView mAddressNeighbourHood;
    @BindView(R.id.about_customer_address_zipcode) TextView mAddressZipCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_customer_);
        ButterKnife.bind(this);
        Customer mCustomer = (Customer) getIntent().getSerializableExtra("Customer");
        if(mCustomer!=null){
            setData(mCustomer);
            setMailClickListener(mCustomer.getMail());
            setPhoneNumberClickListener(mCustomer.getPhoneNum());
        }
    }

    private String getString(String tem){
        if(  tem == null||tem.isEmpty())
            return  "";
        else
            return  tem+" ";
    }

    private void addressHelperMethod(TextView mTextView , String text){
        if(text!=null && !text.isEmpty())
            mTextView.setText(text);
        else
            mTextView.setVisibility(View.GONE);

    }
    private void setData(Customer mCustomer){
        if(mCustomer.getName()!=null){
            mName.setText(mCustomer.getName().getName()+" "+mCustomer.getName().getLast());
        }
        //full Name
        String mBuilder = getString(mCustomer.getName().getName()) +
                getString(mCustomer.getName().getMiddle()) +
                getString(mCustomer.getName().getLast()) +
                getString(mCustomer.getName().getSuffix());
        mFullName.setText(mBuilder);
        //set phone number
        mPhoneNumber.setText(mCustomer.getPhoneNum().getValue());
        mPhoneNumberType.setText(mCustomer.getPhoneNum().getSub());
        //set mail  type
        mEmail.setText(mCustomer.getMail().getValue());
        mEmailType.setText(mCustomer.getMail().getSub());
        //addressComponent
        addressHelperMethod(mAddress,mCustomer.getAddress().getAddress());
        addressHelperMethod(mAddressCity,mCustomer.getAddress().getCity());
        addressHelperMethod(mAddressNeighbourHood,mCustomer.getAddress().getNeighborhood());
        addressHelperMethod(mAddressStreet,mCustomer.getAddress().getStreet());
        addressHelperMethod(mAddressState,mCustomer.getAddress().getState());
        addressHelperMethod(mAddressZipCode,mCustomer.getAddress().getZipcode());
    }

    private void setMailClickListener(final Mail mEmail){
        mMailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Intent.ACTION_SENDTO , Uri.fromParts("mailto" , mEmail.getValue() , null)) ;
                startActivity(Intent.createChooser(intent , "Send email..."));
            }
        });
    }

    private void setPhoneNumberClickListener(final PhoneNum mPhoneNum){
        mImagePhoneNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+mPhoneNum.getValue()));
                startActivity(intent);
            }
        });
    }
}

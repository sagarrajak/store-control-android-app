package com.example.sagar.myapplication.element.customer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.sagar.myapplication.Config;
import com.example.sagar.myapplication.CustumProgressDialog;
import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.adapter.customer.CustomerGridAdapter;
import com.example.sagar.myapplication.adapter.customer.CustomerListAdapter;
import com.example.sagar.myapplication.api.CustomerApi;
import com.example.sagar.myapplication.element.employee.employee.Edit_employee_activity;
import com.example.sagar.myapplication.modal.Address;
import com.example.sagar.myapplication.modal.Customer;
import com.example.sagar.myapplication.modal.Employee;
import com.example.sagar.myapplication.modal.Mail;
import com.example.sagar.myapplication.modal.Name;
import com.example.sagar.myapplication.modal.PhoneNum;
import com.example.sagar.myapplication.utill.ui.AddressFieldUiHelper;
import com.example.sagar.myapplication.utill.ui.BottomSheetImage;
import com.example.sagar.myapplication.utill.ui.MailFieldUiHelper;
import com.example.sagar.myapplication.utill.ui.NameFieldUiHelper;
import com.example.sagar.myapplication.utill.ui.PhoneNumFieldUiHelper;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class Edit_customer_activity extends Activity {

    //address ui element
    @BindView(R.id.edit_customer_address) EditText  mAddress;
    @BindView(R.id.edit_customer_street) EditText   mAddressStreet;
    @BindView(R.id.edit_customer_post_office) EditText  mAddressPostOffice;
    @BindView(R.id.edit_customer_neighborhood) EditText  mAddressNeighbourHood;
    @BindView(R.id.edit_customer_zipcode) EditText  mAddressZipcode;
    @BindView(R.id.edit_customer_city) EditText  mAddressCity;
    @BindView(R.id.edit_customer_state) EditText  mAddressState;
    //name ui element
    @BindView(R.id.edit_customer_first_name) EditText mName;
    @BindView(R.id.edit_customer_middle_name) EditText mMiddleName;
    @BindView(R.id.edit_customer_last_name) EditText  mLastName;
    @BindView(R.id.edit_customer_suffix_name) EditText mSuffixName;
    @BindView(R.id.edit_customer_family_name) EditText mFamilyName;
    //Phone number ui element
    @BindView(R.id.edit_customer_phon)  EditText mPhoneNumber;
    @BindView(R.id.edit_customer_phon_spinner) Spinner mPhoneSpinner;
    //Email Ui element
    @BindView(R.id.edit_customer_mail) EditText mEmailAddress;
    @BindView(R.id.edit_customer_mail_spinner)  Spinner mEmailSpinner;
    //FLoating action buttom
    @BindView(R.id.edit_customer_floating_action_buttom)  FloatingActionButton mFloatingActionBottom;

    private CustomerApi mCustomerApi;
    private Uri mSelectedImageUri;
    private NameFieldUiHelper mNameFieldUiHelper;
    private AddressFieldUiHelper mAddressFieldUiHelper;
    private MailFieldUiHelper mMailFieldUiHelper;
    private PhoneNumFieldUiHelper mPhoneNumberFildUiHleper;
    private BottomSheetImage mBottomSheetImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customer_activity);
        ButterKnife.bind(this);
        setUiElement();
        Customer mCustomer = (Customer) getIntent().getSerializableExtra("Customer");
        if(mCustomer==null){
          finish();
        }
        mBottomSheetImage = new BottomSheetImage(this , new BottomSheetImage.BottomSheetHelper() {
            @Override
            public void resetImageUri() {
                mBottomSheetImage.hideDeleteDialog();
                mSelectedImageUri = null;
            }
        });
        mNameFieldUiHelper = new NameFieldUiHelper(mName,mMiddleName,mLastName,mSuffixName,mFamilyName);
        mAddressFieldUiHelper = new AddressFieldUiHelper(mAddress,mAddressStreet,mAddressNeighbourHood,mAddressState,mAddressZipcode,mAddressPostOffice,mAddressCity);
        mMailFieldUiHelper =  new MailFieldUiHelper(this,mEmailAddress,mEmailSpinner,R.array.email_type);
        mPhoneNumberFildUiHleper = new PhoneNumFieldUiHelper(this,mPhoneNumber,mPhoneSpinner,R.array.phone_number_type);
        mFloatingActionBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetImage.showBottomSheet();
            }
        });
    }

    private void setUiElement(){
        Boolean isLinearLayout = getIntent().getBooleanExtra("isLinearLayout",false);
        if(!isLinearLayout)
            mCustomerApi =  CustomerApi.getCustomerApi(getBaseContext() , CustomerGridAdapter.getCustomerGridAdapter(getBaseContext()));
        else
            mCustomerApi = CustomerApi.getCustomerApi(getBaseContext() , CustomerListAdapter.getCustomerListAdapter(getBaseContext()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case  CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE :
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if ( resultCode == RESULT_OK &&  result.getUri() != null){
                    mSelectedImageUri = result.getUri();
                    mBottomSheetImage.showDeleteDialog();
                }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
                break;
            case  Config.SELECT_IMAGE_FROM_STORAGE :
                if(resultCode == RESULT_OK){
                    mSelectedImageUri = data.getData();
                    CropImage.activity(mSelectedImageUri)
                            .start(Edit_customer_activity.this);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private Customer getCustomerModified(){
        Customer mCustomer = new Customer();
        mCustomer.setName(mNameFieldUiHelper.getName());
        mCustomer.setAddress(mAddressFieldUiHelper.getAddress());
        mCustomer.setMail(mMailFieldUiHelper.getEmailAddress());
        mCustomer.setPhoneNum(mPhoneNumberFildUiHleper.getPhoneNumber());
        return  mCustomer;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ok_cancel,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.ok :
                ProgressDialog mProgressDialog = CustumProgressDialog.getProgressDialog(Edit_customer_activity.this);
                if(!checkIfError()){
                    Customer mCustomer = getCustomerModified();
                    if(mSelectedImageUri!=null){
                        mProgressDialog.show();
                        File file  = new File(mSelectedImageUri.getPath());
                        RequestBody body = RequestBody.create(MediaType.parse("image/") , file);
                        MultipartBody.Part image = MultipartBody.Part.createFormData("upload", file.getName() , body);
                        mProgressDialog.show();
                        mCustomerApi.updateCustomer(image,mCustomer,mProgressDialog);
                    }
                    else{
                        mProgressDialog.show();
                        mCustomerApi.updateCustomer( null,mCustomer,mProgressDialog);
                    }
                }
                break;
            case R.id.cancel :
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean checkIfError(){
        //// TODO: 28/10/17
        return false;
    }
}

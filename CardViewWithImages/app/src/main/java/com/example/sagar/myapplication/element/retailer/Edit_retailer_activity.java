package com.example.sagar.myapplication.element.retailer;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.sagar.myapplication.Config;
import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.adapter.retailer.RetailerGridAdapter;
import com.example.sagar.myapplication.adapter.retailer.RetailerListAdapter;
import com.example.sagar.myapplication.api.RetailerApi;
import com.example.sagar.myapplication.element.employee.employee.Edit_employee_activity;
import com.example.sagar.myapplication.modal.Mail;
import com.example.sagar.myapplication.modal.PhoneNum;
import com.example.sagar.myapplication.modal.Retailer;
import com.example.sagar.myapplication.utill.ui.AddressFieldUiHelper;
import com.example.sagar.myapplication.utill.ui.BottomSheetImage;
import com.example.sagar.myapplication.utill.ui.MailFieldUiHelper;
import com.example.sagar.myapplication.utill.ui.NameFieldUiHelper;
import com.example.sagar.myapplication.utill.ui.PhoneNumFieldUiHelper;
import com.example.sagar.myapplication.utill.ui.SpinnerHelper;
import com.theartofdev.edmodo.cropper.CropImage;

import butterknife.BindView;

public class Edit_retailer_activity extends AppCompatActivity {

    //address ui element
    @BindView(R.id.edit_retailer_address) EditText mAddress;
    @BindView(R.id.edit_retailer_street) EditText   mAddressStreet;
    @BindView(R.id.edit_retailer_post_office) EditText  mAddressPostOffice;
    @BindView(R.id.edit_retailer_neighborhood) EditText  mAddressNeighbourHood;
    @BindView(R.id.edit_retailer_zipcode) EditText  mAddressZipcode;
    @BindView(R.id.edit_retailer_city) EditText  mAddressCity;
    @BindView(R.id.edit_retailer_state) EditText  mAddressState;
    //name ui element
    @BindView(R.id.edit_retailer_first_name) EditText mName;
    @BindView(R.id.edit_retailer_middle_name) EditText mMiddleName;
    @BindView(R.id.edit_retailer_last_name) EditText  mLastName;
    @BindView(R.id.edit_retailer_suffix_name) EditText mSuffixName;
    @BindView(R.id.edit_retailer_family_name) EditText mFamilyName;
    //Phone number ui element
    @BindView(R.id.edit_retailer_phon)  EditText mPhoneNumber;
    @BindView(R.id.edit_retailer_phon_spinner) Spinner mPhoneSpinner;
    //Email Ui element
    @BindView(R.id.edit_retailer_mail) EditText mEmailAddress;
    @BindView(R.id.edit_retailer_mail_spinner)  Spinner mEmailSpinner;

    @BindView(R.id.edit_retailer_toolbar) Toolbar mToolbar;
    @BindView(R.id.edit_retailer_floating_action_bottom)  FloatingActionButton mFloatingActionButton;



    private RetailerApi mRetailerApi;
    private Uri mSelectedImageUri;
    private BottomSheetImage mBottomSheetImage;

    private AddressFieldUiHelper mAddressFieldUiHelper;
    private NameFieldUiHelper mNameFieldUiHelper;
    private MailFieldUiHelper mMailFieldUiHelper;
    private PhoneNumFieldUiHelper mPhoneNumberFiledUiHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_retailer);
        try {
            setUiElement();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUiElement() throws Exception {
        Boolean isLinearLayout = getIntent().getBooleanExtra("isLinearLayout", false);
        mBottomSheetImage =  new BottomSheetImage(this, new BottomSheetImage.BottomSheetHelper() {
            @Override
            public void resetImageUri() {
                mBottomSheetImage.hideDeleteDialog();
                mSelectedImageUri = null;
            }
        });
        mAddressFieldUiHelper = new AddressFieldUiHelper(mAddress,mAddressStreet,mAddressNeighbourHood,mAddressState,mAddressZipcode,mAddressPostOffice,mAddressCity);
        mNameFieldUiHelper    = new NameFieldUiHelper(mName,mMiddleName,mLastName,mSuffixName,mFamilyName);
        mMailFieldUiHelper    = new MailFieldUiHelper(this,mEmailAddress,mEmailSpinner,R.array.email_type);
        mPhoneNumberFiledUiHelper = new PhoneNumFieldUiHelper(this,mPhoneNumber,mPhoneSpinner,R.array.phone_number_type);
        setToolbar();
        if(!isLinearLayout)
            mRetailerApi = RetailerApi.getmReteilerApi(RetailerGridAdapter.getRetailerGridAdapter(this));
        else
            mRetailerApi = RetailerApi.getmReteilerApi(RetailerListAdapter.getRetailerListAdapter(this));
        Retailer mRetailer = (Retailer) getIntent().getSerializableExtra("Retailer");
        if(mRetailer ==null){
            finish();
        }
       mAddressFieldUiHelper.setAddress(mRetailer.getAddress());
       mNameFieldUiHelper.setName(mRetailer.getName());
       mPhoneNumberFiledUiHelper.setPhoneNum(mRetailer.getPhoneNum());
       mMailFieldUiHelper.setEmailAddress(mRetailer.getMail());
    }

    private void setToolbar(){
        setTitle("Edit retailer");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private Retailer getRetailer(){
        Retailer retailer = new Retailer();
        retailer.setName(mNameFieldUiHelper.getName());
        retailer.setAddress(mAddressFieldUiHelper.getAddress());
        retailer.setPhoneNum(mPhoneNumberFiledUiHelper.getPhoneNumber());
        retailer.setMail(mMailFieldUiHelper.getEmailAddress());
        return retailer;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case  R.id.ok :

                break;
            case R.id.cancel:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ok_cancel,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch(requestCode){
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
                if(resultCode == RESULT_OK ){
                    mSelectedImageUri = data.getData();
                    CropImage.activity(mSelectedImageUri)
                            .start(Edit_retailer_activity.this);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

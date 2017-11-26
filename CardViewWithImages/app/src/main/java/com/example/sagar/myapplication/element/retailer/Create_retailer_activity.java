package com.example.sagar.myapplication.element.retailer;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.sagar.myapplication.Config;
import com.example.sagar.myapplication.CustumProgressDialog;
import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.adapter.interfaces.RetailerAdapterInterface;
import com.example.sagar.myapplication.adapter.retailer.RetailerGridAdapter;
import com.example.sagar.myapplication.adapter.retailer.RetailerListAdapter;
import com.example.sagar.myapplication.api.RetailerApi;
import com.example.sagar.myapplication.modal.Address;
import com.example.sagar.myapplication.modal.Mail;
import com.example.sagar.myapplication.modal.Name;
import com.example.sagar.myapplication.modal.PhoneNum;
import com.example.sagar.myapplication.modal.Retailer;
import com.example.sagar.myapplication.utill.ui.BottomSheetImage;
import com.example.sagar.myapplication.utill.ui.SpinnerHelper;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class Create_retailer_activity extends AppCompatActivity  {

    // View associated with the  name
    @BindView(R.id.add_retailer_name) EditText mNameFirstName ;
    @BindView(R.id.add_retailer_middle_name) EditText mNameMiddle;
    @BindView(R.id.add_retailer_last_name) EditText  mNameLast;
    @BindView(R.id.add_retailer_family_name) EditText mNameFamily;
    @BindView(R.id.add_retailer_suffix) EditText mNameSuffix;
    @BindView(R.id.add_retailer_name_view_anchor) ImageView mNameViewAnchor;
    private  Boolean isHiddenName;


    //View associate with address
    @BindView(R.id.add_retailer_address) EditText mAddress;
    @BindView(R.id.add_retailer_street) EditText  mAddressStreet;
    @BindView(R.id.add_retailer_post_office) EditText mAddressPostOffice;
    @BindView(R.id.add_retailer_neighbourhood) EditText mAddressNeighbourHood;
    @BindView(R.id.add_retailer_city) EditText mAddressCity;
    @BindView(R.id.add_retailer_zipcode) EditText mAddressZipCode;
    @BindView(R.id.add_retailer_state) EditText mAddressState;
    @BindView(R.id.add_retailer_address_view_anchor) ImageView mAddressViewAnchor;
    private Boolean isHiddenAddress;

    //View Elements for mail
    @BindView(R.id.add_retailer_email_address) EditText mEmailAddress;
    @BindView(R.id.add_retailer_email_spinner) Spinner  mEmailTypeSpinner;
    private String mSelectedEmailTypeString;

    //View Elements for phone number
    @BindView(R.id.add_retailer_phone_num) EditText mPhoneNumber;
    @BindView(R.id.add_retailer_phone_num_spinner) Spinner mPhoneNumberTypeSpinner;
    private String mSelectedPhoneNumberTypeString;

    //set up floating action button
    @BindView(R.id.add_retailer_floating_action_bottom) FloatingActionButton mFloatingActionButton;

    //set Toolbar
    @BindView(R.id.add_retailer_toolbar) Toolbar mToolbar;

    @BindView(R.id.add_retailer_picture) ImageView mRetailerImageView;

    private BottomSheetDialog mBottomSheetDialog;
    private RetailerApi mRetailerApi;
    private RetailerAdapterInterface mRetailerAdapterInterface;
    private Boolean isListView;
    private Uri mSelectedImageUri;
    private BottomSheetImage mBottomSheetImage;
    private SpinnerHelper mEmailTypeHelper,mPhoneNumberTypeHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_create_retailer);
            ButterKnife.bind(this);
            setToolbar();
            setUiElement();
            setUpOnClickListener();
            setPhoneNumberTypeSpinner();
            setEmailTypeSpinner();
            setNameAnchor();
            setAddressAnchor();
    }

    private void setUiElement(){
            isListView =  getIntent().getBooleanExtra("isListView",false);
            if(isListView)
                mRetailerAdapterInterface = RetailerListAdapter.getRetailerListAdapter(Create_retailer_activity.this);
            else
                mRetailerAdapterInterface = RetailerGridAdapter.getRetailerGridAdapter(Create_retailer_activity.this);
            isHiddenAddress =  true;
            isHiddenName    =  true;
            mRetailerApi    = RetailerApi.getmReteilerApi(mRetailerAdapterInterface);
            mBottomSheetImage = new BottomSheetImage(this, new BottomSheetImage.BottomSheetHelper() {
                @Override
                public void resetImageUri() {
                    mBottomSheetImage.hideDeleteDialog();
                    mSelectedImageUri = null;
                }
            });
    }

    private void setToolbar(){
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            setTitle("Create Retailer");
    }

    private void setNameAnchor(){
        mNameViewAnchor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isHiddenName){
                    mNameViewAnchor.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                    mNameFamily.setVisibility(View.VISIBLE);
                    mNameMiddle.setVisibility(View.VISIBLE);
                    mNameSuffix.setVisibility(View.VISIBLE);
                }
                else{
                    mNameViewAnchor.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                    mNameFamily.setVisibility(View.GONE);
                    mNameMiddle.setVisibility(View.GONE);
                    mNameSuffix.setVisibility(View.GONE);
                }
                isHiddenName=!isHiddenName;
            }
        });
        if(isHiddenName){
            mNameViewAnchor.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
            mNameFamily.setVisibility(View.GONE);
            mNameMiddle.setVisibility(View.GONE);
            mNameSuffix.setVisibility(View.GONE);
        }
        else{
            mNameViewAnchor.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
            mNameFamily.setVisibility(View.VISIBLE);
            mNameMiddle.setVisibility(View.VISIBLE);
            mNameSuffix.setVisibility(View.VISIBLE);
        }
    }

    private void setAddressAnchor(){
        mAddressViewAnchor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isHiddenAddress){
                    mAddressViewAnchor.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                    mAddressStreet.setVisibility(View.VISIBLE);
                    mAddressPostOffice.setVisibility(View.VISIBLE);
                    mAddressNeighbourHood.setVisibility(View.VISIBLE);
                }
                else{
                    mAddressViewAnchor.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                    mAddressStreet.setVisibility(View.GONE);
                    mAddressPostOffice.setVisibility(View.GONE);
                    mAddressNeighbourHood.setVisibility(View.GONE);
                }
                isHiddenAddress = !isHiddenAddress;
            }
        });
        if(isHiddenAddress){
            mAddressViewAnchor.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
            mAddressStreet.setVisibility(View.GONE);
            mAddressPostOffice.setVisibility(View.GONE);
            mAddressNeighbourHood.setVisibility(View.GONE);
        }
        else{
            mAddressViewAnchor.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
            mAddressStreet.setVisibility(View.VISIBLE);
            mAddressPostOffice.setVisibility(View.VISIBLE);
            mAddressNeighbourHood.setVisibility(View.VISIBLE);
        }
    }


    private  void setName(Retailer mRetailer){
        Name nameObj = new Name();
        nameObj.setName(mNameFirstName.getText().toString());
        nameObj.setName(mNameLast.getText().toString());
        if(!isHiddenName){
            nameObj.setMiddle(mNameMiddle.getText().toString());
            nameObj.setFamilyName(mNameSuffix.getText().toString());
            nameObj.setFamilyName(mNameFamily.getText().toString());
        }
        mRetailer.setName(nameObj);
    }

    private void setAddress(Retailer mRetailer){
        Address addressObj = new Address();
        addressObj.setAddress(mAddress.getText().toString());
        addressObj.setZipcode(mAddressZipCode.getText().toString());
        addressObj.setState(mAddressState.getText().toString());
        addressObj.setCity(mAddressCity.getText().toString());
        if(!isHiddenAddress){
            addressObj.setStreet(mAddressStreet.getText().toString());
            addressObj.setPoBox(mAddressPostOffice.getText().toString());
            addressObj.setNeighborhood(mAddressNeighbourHood.getText().toString());
        }
        mRetailer.setAddress(addressObj);
    }

    private void setPhoneNumberTypeSpinner(){
        mPhoneNumberTypeHelper = new SpinnerHelper(this,R.array.phone_number_type,mPhoneNumberTypeSpinner,new SpinnerHelper.ItemSelectListener(){
            @Override
            public void selectedString(String str) {
                mSelectedPhoneNumberTypeString = str;
            }
        });
    }

    private void setEmailTypeSpinner(){
         mEmailTypeHelper = new SpinnerHelper(this, R.array.email_type, mEmailTypeSpinner, new SpinnerHelper.ItemSelectListener() {
             @Override
             public void selectedString(String str) {
                mSelectedEmailTypeString = str;
             }
         });
    }

    private  void setEmailAddress(Retailer mRetailer){
        Mail mailObj = new Mail();
        mailObj.setValue(mEmailAddress.getText().toString());
        mailObj.setSub(mSelectedEmailTypeString);
        mRetailer.setMail(mailObj);
    }

    private void setPhoneNumber(Retailer mRetailer){
        PhoneNum phoneNumObj = new PhoneNum();
        phoneNumObj.setValue(mPhoneNumber.getText().toString());
        phoneNumObj.setSub(mSelectedPhoneNumberTypeString);
        mRetailer.setPhoneNum(phoneNumObj);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menu_ok_cancel, menu );
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
             super.onActivityResult(requestCode, resultCode, data);
             switch (requestCode){
                 case  CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE :
                     CropImage.ActivityResult result = CropImage.getActivityResult(data);
                     if (resultCode == RESULT_OK && result.getUri() != null){
                         mSelectedImageUri = result.getUri();
                         mRetailerImageView.setImageURI(mSelectedImageUri);
                         mBottomSheetImage.showDeleteDialog();
                     } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                         Exception error = result.getError();
                         mSelectedImageUri = null;
                         mBottomSheetImage.hideDeleteDialog();
                     }
                     break;
                 case  Config.SELECT_IMAGE_FROM_STORAGE :
                     if(resultCode == RESULT_OK ){
                         mSelectedImageUri = data.getData();
                         CropImage.activity(mSelectedImageUri)
                                 .start(Create_retailer_activity.this);
                     }
                     break;
             }
    }

    private  void setUpOnClickListener(){
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetImage.showBottomSheet();
            }
        });
    }

    public void checkPermission(){
            if( ContextCompat.checkSelfPermission(
                    this ,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE  ) != PackageManager.PERMISSION_GRANTED ){
                ActivityCompat.requestPermissions( Create_retailer_activity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE },
                        1 );
            }
    }


    private Retailer getRetailer(){
            Retailer mRetailer = new Retailer();
            setPhoneNumber(mRetailer);
            setEmailAddress(mRetailer);
            setName(mRetailer);
            setAddress(mRetailer);
            return  mRetailer;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.ok:
                ProgressDialog dialog = CustumProgressDialog.getProgressDialog(Create_retailer_activity.this);
                if(checkError()){
                    dialog.show();
                    File  file  = new File(mSelectedImageUri.getPath());
                    RequestBody reqfile  =  RequestBody.create(MediaType.parse("image/*"),file);
                    MultipartBody.Part body = MultipartBody.Part.createFormData("upload",file.getName(),reqfile);
                    mRetailerApi.uploadRetailerImage( body , getRetailer() , dialog );
                }
                break;
            case R.id.cancel:
                finish();
                break;
        }
        return true;
    }

    private boolean checkError(){
    //        // TODO: 1/8/2017 to check error in filed
            //CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinate_layout_create_retailer);
//            if( file==null ){
//                Snackbar.make( coordinatorLayout , "Select image" , Snackbar.LENGTH_LONG).show();
//                return  false;
//            }
//            else if(name.getText().toString().isEmpty()){
//                Snackbar.make( coordinatorLayout , "Input  name" , Snackbar.LENGTH_LONG).show();
//                return  false;
//            }
//            else if( mail.getText().toString().isEmpty() ){
//                Snackbar.make( coordinatorLayout , "Fill mail adress" , Snackbar.LENGTH_LONG).show();
//                return  false;
//            }
//            else if( phone_num.getText().toString().isEmpty() ){
//                Snackbar.make( coordinatorLayout , "Fill phone number" , Snackbar.LENGTH_LONG).show();
//                return  false;
//            }
            return  true;
    }

}

package com.example.sagar.myapplication.element.customer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.sagar.myapplication.Config;
import com.example.sagar.myapplication.CustumProgressDialog;
import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.adapter.customer.CustomerGridAdapter;
import com.example.sagar.myapplication.adapter.customer.CustomerListAdapter;
import com.example.sagar.myapplication.api.CustomerApi;
import com.example.sagar.myapplication.modal.Address;
import com.example.sagar.myapplication.modal.Customer;
import com.example.sagar.myapplication.modal.Mail;
import com.example.sagar.myapplication.modal.Name;
import com.example.sagar.myapplication.modal.PhoneNum;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class Create_customer_activity extends AppCompatActivity {

    private EditText name, middleName, lastName, family_name, suffix_name, email, phoneNumber;
    private EditText address, addressStreet, addressPoBox, addressNeighborhood, addressZipcode, addressCity, addressState;
    private ImageView mCustomerImage, mNameAnchor, mAddressAnchor;
    private Spinner phoneNumberType, emailType;
    private boolean isHiddenName, isLinearLayout, isHiddenAddress;
    private FloatingActionButton mFloatingActionButton;
    private Uri imageResultUri;
    private CustomerApi mCustomerApi;
    private BottomSheetDialog mBottomSheetDialog;
    private String phoneNumberTypeSelectedString = null, emailTypeSelectedString = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_customer_activity);
        setUiElement();
        setToolbar();
        setNameAnchorController();
        setAddressAnchorController();
        setFloatingActionButton();
        setEmailTypeSpinner();
        setPhoneNumberTypeSpinner();
        setTitle("Add customer");
    }

    private void setUiElement() {
        isHiddenName = true;
        isHiddenAddress = true;
        // grab name ui element
        name = (EditText) findViewById(R.id.add_customer_first_name);
        middleName = (EditText) findViewById(R.id.add_customer_middle_name);
        lastName = (EditText) findViewById(R.id.add_customer_last_name);
        family_name = (EditText) findViewById(R.id.add_customer_family_name);
        suffix_name = (EditText) findViewById(R.id.add_customer_suffix_name);
        mNameAnchor = (ImageView) findViewById(R.id.add_customer_name_image_icon);
        // grab other types
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.add_button_float);
        email = (EditText) findViewById(R.id.add_customer_mail);
        emailType = (Spinner) findViewById(R.id.add_customer_mail_spinner);
        phoneNumber = (EditText) findViewById(R.id.add_customer_phon);
        phoneNumberType = (Spinner) findViewById(R.id.add_customer_phon_spinner);
        mCustomerImage = (ImageView) findViewById(R.id.add_customer_picture);
        // grab address element
        address = (EditText) findViewById(R.id.add_customer_address);
        addressStreet = (EditText) findViewById(R.id.add_customer_street);
        addressPoBox = (EditText) findViewById(R.id.add_customer_post_office);
        addressNeighborhood = (EditText) findViewById(R.id.add_customer_post_neighborhood);
        addressZipcode = (EditText) findViewById(R.id.add_customer_zipcode);
        addressCity = (EditText) findViewById(R.id.add_customer_post_city);
        addressState = (EditText) findViewById(R.id.add_customer_post_state);
        mAddressAnchor = (ImageView) findViewById(R.id.add_customer_address_image_icon);

        isLinearLayout = getIntent().getBooleanExtra("isLinearLayout", false);

        phoneNumberTypeSelectedString = getResources().getStringArray(R.array.phone_number_type)[0];
        emailTypeSelectedString = getResources().getStringArray(R.array.email_type)[0];

        if (isLinearLayout)
            mCustomerApi = CustomerApi.getCustomerApi(getBaseContext(), CustomerListAdapter.getCustomerListAdapter(getBaseContext()));
        else
            mCustomerApi = CustomerApi.getCustomerApi(getBaseContext(), CustomerGridAdapter.getCustomerGridAdapter(getBaseContext()));
    }

    private void setNameAnchorController() {
        mNameAnchor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isHiddenName) {
                    mNameAnchor.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                    middleName.setVisibility(View.VISIBLE);
                    family_name.setVisibility(View.VISIBLE);
                    suffix_name.setVisibility(View.VISIBLE);
                } else {
                    mNameAnchor.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                    middleName.setVisibility(View.GONE);
                    family_name.setVisibility(View.GONE);
                    suffix_name.setVisibility(View.GONE);
                }
                isHiddenName = !isHiddenName;
            }
        });
        if (isHiddenName) {
            mNameAnchor.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
            middleName.setVisibility(View.GONE);
            family_name.setVisibility(View.GONE);
            suffix_name.setVisibility(View.GONE);
        } else {
            mNameAnchor.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
            middleName.setVisibility(View.VISIBLE);
            family_name.setVisibility(View.VISIBLE);
            suffix_name.setVisibility(View.VISIBLE);
        }
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_create_customer_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setFloatingActionButton() {
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBottomSheet();
            }
        });
    }

    private void setAddressAnchorController() {
        mAddressAnchor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isHiddenAddress) {
                    mAddressAnchor.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                    addressStreet.setVisibility(View.VISIBLE);
                    addressPoBox.setVisibility(View.VISIBLE);
                    addressNeighborhood.setVisibility(View.VISIBLE);
                } else {
                    mAddressAnchor.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                    addressStreet.setVisibility(View.GONE);
                    addressPoBox.setVisibility(View.GONE);
                    addressNeighborhood.setVisibility(View.GONE);
                }
                isHiddenAddress = !isHiddenAddress;
            }
        });
        if (isHiddenAddress) {
            mAddressAnchor.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
            addressStreet.setVisibility(View.GONE);
            addressPoBox.setVisibility(View.GONE);
            addressNeighborhood.setVisibility(View.GONE);
        } else {
            mAddressAnchor.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
            addressStreet.setVisibility(View.VISIBLE);
            addressPoBox.setVisibility(View.VISIBLE);
            addressNeighborhood.setVisibility(View.VISIBLE);
        }
    }

    // A  method to add  name to customer object
    private void addName(Customer mCustomer) {
        Name name_obj = new Name();
        name_obj.setName(name.getText().toString());
        name_obj.setLast(lastName.getText().toString());
        if (!isHiddenName) {
            name_obj.setFamilyName(family_name.getText().toString());
            name_obj.setMiddle(middleName.getText().toString());
            name_obj.setSuffix(suffix_name.getText().toString());
        }
        mCustomer.setName(name_obj);
    }

    //a method to add email to the customer object
    private void addEmail(Customer mCustomer) {
        Mail mail = new Mail();
        mail.setValue(email.getText().toString());
        mail.setSub(emailTypeSelectedString);
        mCustomer.setMail(mail);

    }

    private void addPhoneNumber(Customer mCustomer) {
        PhoneNum phoneNum = new PhoneNum();
        phoneNum.setValue(phoneNumber.getText().toString());
        phoneNum.setSub(phoneNumberTypeSelectedString);
        mCustomer.setPhoneNum(phoneNum);
    }

    // A method to add address to the customer object
    private void addAddress(Customer mCustomer) {
        Address obj = new Address();
        obj.setAddress(address.getText().toString());
        obj.setZipcode(addressZipcode.getText().toString());
        obj.setCity(addressCity.getText().toString());
        obj.setState(addressState.getText().toString());
        if (!isHiddenAddress) {
            obj.setStreet(addressStreet.getText().toString());
            obj.setPoBox(addressPoBox.getText().toString());
            obj.setNeighborhood(addressNeighborhood.getText().toString());
        }
        mCustomer.setAddress(obj);
    }


    private void setEmailTypeSpinner() {
        ArrayAdapter<CharSequence> emailTypeAdapter = ArrayAdapter.createFromResource(getBaseContext(), R.array.email_type, android.R.layout.simple_spinner_item);
        emailTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        emailType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                emailTypeSelectedString = (String) adapterView.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void setPhoneNumberTypeSpinner() {
        ArrayAdapter<CharSequence> phoneNumberTypeAdapter = ArrayAdapter.createFromResource(getBaseContext(), R.array.phone_number_type, android.R.layout.simple_spinner_item);
        phoneNumberTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        phoneNumberType.setAdapter(phoneNumberTypeAdapter);
        phoneNumberType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                phoneNumberTypeSelectedString = (String) adapterView.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK && result.getUri() != null) {
                    imageResultUri = result.getUri();
                    mCustomerImage.setImageURI(imageResultUri);
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
                break;
            case Config.SELECT_IMAGE_FROM_STORAGE:
                if (resultCode == RESULT_OK) {
                    imageResultUri = data.getData();
                    CropImage.activity(imageResultUri)
                            .start(Create_customer_activity.this);
                }
                break;
        }
    }

    private Customer postData() {
        Customer mCustomer = new Customer();
        addName(mCustomer);
        addAddress(mCustomer);
        addEmail(mCustomer);
        addPhoneNumber(mCustomer);
        return mCustomer;
    }

    private void setBottomSheet() {
        mBottomSheetDialog = new BottomSheetDialog(this);
        mBottomSheetDialog.setTitle("Profile pictue");
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_image_select_dilaog, null);
        mBottomSheetDialog.setContentView(view);
        view.findViewById(R.id.bottom_sheet_gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, Config.SELECT_IMAGE_FROM_STORAGE);
            }
        });
        if (imageResultUri == null) {
            view.findViewById(R.id.bottom_sheet_delete).setVisibility(View.GONE);
        } else {
            view.findViewById(R.id.bottom_sheet_delete).setVisibility(View.VISIBLE);
            view.findViewById(R.id.bottom_sheet_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mBottomSheetDialog.dismiss();
                    imageResultUri = null;
                    mCustomerImage.setImageResource(R.drawable.employee);
                }
            });
        }
        view.findViewById(R.id.bottom_sheet_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(Create_customer_activity.this);
            }
        });
        mBottomSheetDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ok_cancel, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ok:
                Customer customer = postData();
                ProgressDialog mProgessDialog = CustumProgressDialog.getProgressDialog(Create_customer_activity.this);
                mProgessDialog.show();
                if (imageResultUri != null) {
                    File file = new File(imageResultUri.getPath());
                    RequestBody body = RequestBody.create(MediaType.parse("image/"), file);
                    MultipartBody.Part image = MultipartBody.Part.createFormData("upload", file.getName(), body);
                    mCustomerApi.addCustomer(image, customer, mProgessDialog);
                } else
                    mCustomerApi.addCustomer(null, customer, mProgessDialog);
                break;
            case R.id.cancel:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}

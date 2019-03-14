package com.example.sagar.myapplication.element.employee.employee;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.sagar.myapplication.Config;
import com.example.sagar.myapplication.CustumProgressDialog;
import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.adapter.employee.EmployeeGridAdapter;
import com.example.sagar.myapplication.adapter.employee.EmployeeListAdapter;
import com.example.sagar.myapplication.adapter.interfaces.EmployeeAdapterInterface;
import com.example.sagar.myapplication.api.EmployeeApi;
import com.example.sagar.myapplication.modal.Address;
import com.example.sagar.myapplication.modal.Employee;
import com.example.sagar.myapplication.modal.Mail;
import com.example.sagar.myapplication.modal.Name;
import com.example.sagar.myapplication.modal.PhoneNum;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class Create_employee_activity extends AppCompatActivity {

    //Name ui components
    @BindView(R.id.add_employee_name)
    EditText name;
    @BindView(R.id.add_employee_middle_name)
    EditText nameMiddle;
    @BindView(R.id.add_employee_last_name)
    EditText nameLast;
    @BindView(R.id.add_employee_family_name)
    EditText nameFamilyName;
    @BindView(R.id.add_employee_suffix)
    EditText nameSuffix;
    @BindView(R.id.add_employee_name_view_anchor)
    ImageView mNameAnchor;
    private Boolean isNameHidden;


    //Address ui components
    @BindView(R.id.add_employee_address)
    EditText address;
    @BindView(R.id.add_employee_street)
    EditText addressStreet;
    @BindView(R.id.add_employee_post_office)
    EditText addressPostOffice;
    @BindView(R.id.add_employee_neighbourhood)
    EditText addressNeighbour;
    @BindView(R.id.add_employee_city)
    EditText addressCity;
    @BindView(R.id.add_employee_state)
    EditText addressState;
    @BindView(R.id.add_employee_zipcode)
    EditText addressZipCode;
    @BindView(R.id.add_employee_address_view_anchor)
    ImageView mAddressAnchor;
    private Boolean isAddressHidden;

    //Email component
    @BindView(R.id.add_employee_email_address)
    EditText mEmailAddress;
    @BindView(R.id.add_employee_email_spinner)
    Spinner mEmailAddressTypeSelected;
    private String mEmailTypeSelectedString;

    //Phone number type
    @BindView(R.id.add_employee_phone_num)
    EditText mPhoneNumber;
    @BindView(R.id.add_employee_phone_num_spinner)
    Spinner mPhoneNumberTypeSelected;
    private String mPhoneNumberTypeSelectedString;

    //Date of birth
    @BindView(R.id.add_Employee_date_of_birth)
    EditText mDateOfBirth;
    //Date of join
    @BindView(R.id.add_Employee_date_of_join)
    EditText mDateOfJoin;
    //Work profile
    @BindView(R.id.add_employee_work_profile)
    EditText mWorkProfile;
    //Toolbar
    @BindView(R.id.add_employee_toolbar)
    Toolbar mToolbar;
    //Employee image
    @BindView(R.id.add_employee_picture)
    ImageView mEmployeeImage;

    @BindView(R.id.add_employee_floating_action_bottom)
    FloatingActionButton mFloatingActionButton;

    // temporary variable to store calender
    private Calendar mCalendarDateOfJoin, mCalendarDateOfBirth;
    private BottomSheetDialog mBottomSheetDialog;

    private EmployeeApi mEmployeeApi;
    private EmployeeAdapterInterface employeeAdapterInterface;

    // to store selected image uri
    private Uri mSelectedImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_employee);
        ButterKnife.bind(this);
        setUiElement();
        setToolbar();
        setPhoneNumberTypeSpinner();
        setEmailTypeSpinner();
        setNameAnchor();
        setAddressAnchor();
        setUpOnClickListener();
    }

    private void setNameAnchor() {
        mNameAnchor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNameHidden) {
                    mNameAnchor.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                    nameSuffix.setVisibility(View.VISIBLE);
                    nameMiddle.setVisibility(View.VISIBLE);
                    nameFamilyName.setVisibility(View.VISIBLE);
                } else {
                    mNameAnchor.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                    nameSuffix.setVisibility(View.GONE);
                    nameMiddle.setVisibility(View.GONE);
                    nameFamilyName.setVisibility(View.GONE);
                }
                isNameHidden = !isNameHidden;
            }
        });
        //setting up anchor for the first time
        if (isNameHidden) {
            mNameAnchor.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
            nameSuffix.setVisibility(View.GONE);
            nameMiddle.setVisibility(View.GONE);
            nameFamilyName.setVisibility(View.GONE);
        } else {
            mNameAnchor.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
            nameSuffix.setVisibility(View.VISIBLE);
            nameMiddle.setVisibility(View.VISIBLE);
            nameFamilyName.setVisibility(View.VISIBLE);
        }
    }

    private void setAddressAnchor() {
        mAddressAnchor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAddressHidden) {
                    mAddressAnchor.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                    addressStreet.setVisibility(View.VISIBLE);
                    addressPostOffice.setVisibility(View.VISIBLE);
                    addressNeighbour.setVisibility(View.VISIBLE);
                } else {
                    mAddressAnchor.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                    addressStreet.setVisibility(View.GONE);
                    addressPostOffice.setVisibility(View.GONE);
                    addressNeighbour.setVisibility(View.GONE);
                }
                isAddressHidden = !isAddressHidden;
            }
        });
        //setting anchor for the first time
        if (isAddressHidden) {
            mAddressAnchor.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
            addressStreet.setVisibility(View.GONE);
            addressPostOffice.setVisibility(View.GONE);
            addressNeighbour.setVisibility(View.GONE);
        } else {
            mAddressAnchor.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
            addressStreet.setVisibility(View.VISIBLE);
            addressPostOffice.setVisibility(View.VISIBLE);
            addressNeighbour.setVisibility(View.VISIBLE);
        }
    }

    private void setUpCalender(final EditText mEditText, final Calendar mCalender) {
        final DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dateOfMonth) {
                mCalender.set(Calendar.YEAR, year);
                mCalender.set(Calendar.MONTH, month);
                mCalender.set(Calendar.DAY_OF_MONTH, dateOfMonth);
                mEditText.setText(new SimpleDateFormat("dd/MM/yyyy").format(mCalender.getTime()));
            }
        };
        mEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Create_employee_activity.this, datePicker, mCalender.get(Calendar.YEAR), mCalender.get(Calendar.MONTH), mCalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocusGain) {
                if (isFocusGain)
                    new DatePickerDialog(Create_employee_activity.this, datePicker, mCalender.get(Calendar.YEAR), mCalender.get(Calendar.MONTH), mCalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void setUpOnClickListener() {
        mCalendarDateOfJoin = Calendar.getInstance();
        mCalendarDateOfBirth = Calendar.getInstance();
        setUpCalender(mDateOfBirth, mCalendarDateOfBirth);
        setUpCalender(mDateOfJoin, mCalendarDateOfJoin);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createBottomSheet();
            }
        });
        mWorkProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAlertForSelectingWorkProfile();
            }
        });
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Create Employee");
    }

    private void setName(Employee mEmployee) {
        Name nameObj = new Name();
        nameObj.setName(name.getText().toString());
        nameObj.setLast(nameLast.getText().toString());
        if (!isNameHidden) {
            nameObj.setFamilyName(nameFamilyName.getText().toString());
            nameObj.setMiddle(nameMiddle.getText().toString());
            nameObj.setSuffix(nameSuffix.getText().toString());
        }
        mEmployee.setName(nameObj);
    }

    private void setAddress(Employee mEmployee) {
        Address addressObj = new Address();
        addressObj.setAddress(address.getText().toString());
        addressObj.setZipcode(addressZipCode.getText().toString());
        addressObj.setState(addressState.getText().toString());
        addressObj.setCity(addressCity.getText().toString());
        if (!isAddressHidden) {
            addressObj.setStreet(addressStreet.getText().toString());
            addressObj.setPoBox(addressPostOffice.getText().toString());
            addressObj.setNeighborhood(addressNeighbour.getText().toString());
        }
        mEmployee.setAddress(addressObj);
    }

    private void setEmailTypeSpinner() {
        ArrayAdapter<CharSequence> emailTypeAdapter = ArrayAdapter.createFromResource(getBaseContext(), R.array.email_type, android.R.layout.simple_spinner_item);
        emailTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mEmailAddressTypeSelected.setAdapter(emailTypeAdapter);
        mEmailAddressTypeSelected.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mEmailTypeSelectedString = (String) adapterView.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void setPhoneNumberTypeSpinner() {
        ArrayAdapter<CharSequence> phoneNumberTypeAdapter = ArrayAdapter.createFromResource(getBaseContext(), R.array.phone_number_type, android.R.layout.simple_spinner_item);
        phoneNumberTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPhoneNumberTypeSelected.setAdapter(phoneNumberTypeAdapter);
        mPhoneNumberTypeSelected.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mPhoneNumberTypeSelectedString = (String) adapterView.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void setEmail(Employee mEmployee) {
        Mail mailObj = new Mail();
        mailObj.setValue(mEmailAddress.getText().toString());
        mailObj.setSub(mEmailTypeSelectedString);
        mEmployee.setMail(mailObj);
    }

    private void setPhoneNumber(Employee mEmployee) {
        PhoneNum mPhoneNumObj = new PhoneNum();
        mPhoneNumObj.setValue(mPhoneNumber.getText().toString());
        mPhoneNumObj.setSub(mPhoneNumberTypeSelectedString);
        mEmployee.setPhoneNumber(mPhoneNumObj);
    }

    private void setUiElement() {
        boolean isLinearLayout = getIntent().getBooleanExtra("isLinearLayout", false);
        if (!isLinearLayout)
            employeeAdapterInterface = EmployeeGridAdapter.getEmployeeGridAdapter(getBaseContext());
        else
            employeeAdapterInterface = EmployeeListAdapter.getEmployeeListAdapter(getBaseContext());
        mEmployeeApi = EmployeeApi.getEmployeeApi(employeeAdapterInterface, getBaseContext());
        isNameHidden = true;
        isAddressHidden = true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ok_cancel, menu);
        return true;
    }

    public void createAlertForSelectingWorkProfile() {
        //// TODO: 22/10/17 add work profile selection
    }

    private void createBottomSheet() {
        checkPermission();
        mBottomSheetDialog = new BottomSheetDialog(this);
        mBottomSheetDialog.setTitle("Profile picture");
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
        if (mSelectedImage == null) {
            view.findViewById(R.id.bottom_sheet_delete).setVisibility(View.GONE);
        } else {
            view.findViewById(R.id.bottom_sheet_delete).setVisibility(View.VISIBLE);
            view.findViewById(R.id.bottom_sheet_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mBottomSheetDialog.dismiss();
                    mSelectedImage = null;
                    mEmployeeImage.setImageResource(R.drawable.employee);
                }
            });
        }
        view.findViewById(R.id.bottom_sheet_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(Create_employee_activity.this);

            }
        });
        mBottomSheetDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK && result.getUri() != null) {
                    mSelectedImage = result.getUri();
                    mEmployeeImage.setImageURI(mSelectedImage);
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                    mSelectedImage = null;
                }
                break;
            case Config.SELECT_IMAGE_FROM_STORAGE:
                if (resultCode == RESULT_OK) {
                    mSelectedImage = data.getData();
                    CropImage.activity(mSelectedImage)
                            .start(Create_employee_activity.this);
                }
                break;
        }
    }

    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(Create_employee_activity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        }
    }

    private boolean checkIfError() {
////        // TODO: 1/8/2017 to check error in filed
//        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinate_layout_create_employee);
//        if( file==null ){
//            Snackbar.make( coordinatorLayout , "Select image" , Snackbar.LENGTH_LONG).show();
//            return  false;
//        }
//        else if(mName.getText().toString().isEmpty()){
//            Snackbar.make( coordinatorLayout , "Input  mName" , Snackbar.LENGTH_LONG).show();
//            return  false;
//        }
//        else if(date_of_birth.getText().toString().isEmpty() ){
//            Snackbar.make( coordinatorLayout , "Fill date of birth" , Snackbar.LENGTH_LONG).show();
//            return  false;
//        }
//        else if( date_of_join.getText().toString().isEmpty()){
//            Snackbar.make( coordinatorLayout , "Fill date of join" , Snackbar.LENGTH_LONG).show();
//            return  false;
//        }
//        else if( mail.getText().toString().isEmpty() ){
//            Snackbar.make( coordinatorLayout , "Fill mail adress" , Snackbar.LENGTH_LONG).show();
//            return  false;
//        }
//        else if( phone_num.getText().toString().isEmpty() ){
//            Snackbar.make( coordinatorLayout , "Fill phone number" , Snackbar.LENGTH_LONG).show();
//            return  false;
//        }
//        else if( pan_num.getText().toString().isEmpty() ){
//            Snackbar.make( coordinatorLayout , "Fill pan number" , Snackbar.LENGTH_LONG).show();
//            return  false;
//        }
//        else if( work_profile.getText().toString().isEmpty() ){
//            Snackbar.make( coordinatorLayout , "fill work profile" , Snackbar.LENGTH_LONG).show();
//            return  false;
//        }
        return false;
    }

    /**
     * helper method for post employee
     **/
    private Employee postEmployeeData() {
        Employee mEmployee = new Employee();
        setName(mEmployee);
        setAddress(mEmployee);
        setEmail(mEmployee);
        setPhoneNumber(mEmployee);
        mEmployee.setDateOfBirth(mCalendarDateOfBirth.getTime());
        mEmployee.setDateOfJoin(mCalendarDateOfJoin.getTime());
        return mEmployee;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ok:
                ProgressDialog mProgressDialog = CustumProgressDialog.getProgressDialog(Create_employee_activity.this);
                if (!checkIfError()) {
                    mProgressDialog.show();
                    Employee mEmployee = postEmployeeData();
                    if (mSelectedImage != null) {
                        File file = new File(mSelectedImage.getPath());
                        RequestBody body = RequestBody.create(MediaType.parse("image/"), file);
                        MultipartBody.Part image = MultipartBody.Part.createFormData("upload", file.getName(), body);
                        mEmployeeApi.createEmployee(image, mEmployee, mProgressDialog);
                    } else {
                        mEmployeeApi.createEmployee(null, mEmployee, mProgressDialog);
                    }
                }
                break;
            case R.id.cancel:
                finish();
                break;
        }
        return true;
    }
}

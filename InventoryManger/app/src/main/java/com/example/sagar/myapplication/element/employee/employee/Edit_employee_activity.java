package com.example.sagar.myapplication.element.employee.employee;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.sagar.myapplication.Config;
import com.example.sagar.myapplication.CustumProgressDialog;
import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.adapter.employee.EmployeeGridAdapter;
import com.example.sagar.myapplication.api.EmployeeApi;
import com.example.sagar.myapplication.modal.Employee;
import com.example.sagar.myapplication.modal.Mail;
import com.example.sagar.myapplication.modal.PhoneNum;
import com.example.sagar.myapplication.utill.ui.AddressFieldUiHelper;
import com.example.sagar.myapplication.utill.ui.BottomSheetImage;
import com.example.sagar.myapplication.utill.ui.MailFieldUiHelper;
import com.example.sagar.myapplication.utill.ui.NameFieldUiHelper;
import com.example.sagar.myapplication.utill.ui.PhoneNumFieldUiHelper;
import com.example.sagar.myapplication.utill.ui.SpinnerHelper;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class Edit_employee_activity extends AppCompatActivity {

    //Name ui components
    @BindView(R.id.edit_employee_name)
    EditText mName;
    @BindView(R.id.edit_employee_middle_name)
    EditText mNameMiddle;
    @BindView(R.id.edit_employee_last_name)
    EditText mNameLast;
    @BindView(R.id.edit_employee_family_name)
    EditText mNameFamilyName;
    @BindView(R.id.edit_employee_suffix)
    EditText mNameSuffix;


    //Address ui components
    @BindView(R.id.edit_employee_address)
    EditText mAddress;
    @BindView(R.id.edit_employee_street)
    EditText mAddressStreet;
    @BindView(R.id.edit_employee_post_office)
    EditText mAddressPostOffice;
    @BindView(R.id.edit_employee_neighbourhood)
    EditText mAddressNeighbour;
    @BindView(R.id.edit_employee_city)
    EditText mAddressCity;
    @BindView(R.id.edit_employee_state)
    EditText mAddressState;
    @BindView(R.id.edit_employee_zipcode)
    EditText mAddressZipCode;


    //Email component
    @BindView(R.id.edit_employee_email_address)
    EditText mEmailAddress;
    @BindView(R.id.edit_employee_email_spinner)
    Spinner mEmailSpinner;


    //Phone number type
    @BindView(R.id.edit_employee_phone_num)
    EditText mPhoneNumber;
    @BindView(R.id.edit_employee_phone_num_spinner)
    Spinner mPhoneSpinner;

    //Date of birth
    @BindView(R.id.edit_employee_date_of_birth)
    EditText mDateOfBirth;
    //Date of join
    @BindView(R.id.edit_employee_date_of_join)
    EditText mDateOfJoin;
    //Work profile
    @BindView(R.id.edit_employee_work_profile)
    EditText mWorkProfile;
    //Toolbar
    @BindView(R.id.edit_employee_toolbar)
    Toolbar mToolbar;
    //Employee image
    @BindView(R.id.edit_employee_picture)
    ImageView mEmployeeImage;

    @BindView(R.id.edit_employee_floating_action_bottom)
    FloatingActionButton mFloatingActionButton;

    // temporary variable to store calender
    private Calendar mCalendarDateOfJoin, mCalendarDateOfBirth;

    private EmployeeApi mEmployeeApi;
    // to store selected image uri
    private Uri mSelectedImage;

    // A object that contains that
    private Employee mEmployee;

    // Bottom sheet helper object
    private BottomSheetImage mBottomSheetImage;
    private AddressFieldUiHelper mAddressFieldUiHelper;
    private NameFieldUiHelper mNameFieldUiHelper;
    private MailFieldUiHelper mMailFieldUiHelper;
    private PhoneNumFieldUiHelper mPhoneNumberFileUiHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee);
        ButterKnife.bind(this);
        setUiElement();
        mEmployee = (Employee) getIntent().getSerializableExtra("Employee");
        mBottomSheetImage = new BottomSheetImage(this, new BottomSheetImage.BottomSheetHelper() {
            @Override
            public void resetImageUri() {
                mSelectedImage = null;
                mBottomSheetImage.hideDeleteDialog();
            }
        });
        mAddressFieldUiHelper = new AddressFieldUiHelper(mAddress, mAddressStreet, mAddressNeighbour, mAddressState, mAddressZipCode, mAddressPostOffice, mAddressCity);
        mNameFieldUiHelper = new NameFieldUiHelper(mName, mNameMiddle, mNameLast, mNameSuffix, mNameFamilyName);
        mPhoneNumberFileUiHelper = new PhoneNumFieldUiHelper(this, mPhoneNumber, mPhoneSpinner, R.array.phone_number_type);
        mMailFieldUiHelper = new MailFieldUiHelper(this, mEmailAddress, mPhoneSpinner, R.array.email_type);
        mAddressFieldUiHelper.setAddress(mEmployee.getAddress());
        mNameFieldUiHelper.setName(mEmployee.getName());
        mMailFieldUiHelper.setEmailAddress(mEmployee.getMail());
        mPhoneNumberFileUiHelper.setPhoneNum(mEmployee.getPhoneNumber());
        setDateOfBirth(mEmployee.getDateOfBirth());
        setDateOfJoin(mEmployee.getDateOfJoin());
        setToolbar();
        setUpOnClickListener();
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Create Employee");
    }


    private void setDateOfBirth(Date dateOfBirth) {
        mDateOfBirth.setText(new SimpleDateFormat("dd/MM/yyyy").format(dateOfBirth));
        mCalendarDateOfBirth = Calendar.getInstance();
        mCalendarDateOfBirth.setTime(dateOfBirth);
    }

    private void setDateOfJoin(Date dateOfJoin) {
        mDateOfBirth.setText(new SimpleDateFormat("dd/MM/yyyy").format(dateOfJoin));
        mCalendarDateOfBirth = Calendar.getInstance();
        mCalendarDateOfBirth.setTime(dateOfJoin);
    }

    private void setUiElement() {
        boolean isLinearLayout = getIntent().getBooleanExtra("isLinearLayout", false);
        if (!isLinearLayout)
            mEmployeeApi = EmployeeApi.getEmployeeApi(EmployeeGridAdapter.getEmployeeGridAdapter(getBaseContext()), getBaseContext());
        else
            mEmployeeApi = EmployeeApi.getEmployeeApi(EmployeeGridAdapter.getEmployeeGridAdapter(getBaseContext()), getBaseContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ok_cancel, menu);
        return true;
    }

    private Employee postEmployeeData() {
        Employee mEmployee = new Employee();
        mEmployee.setName(mNameFieldUiHelper.getName());
        mEmployee.setAddress(mAddressFieldUiHelper.getAddress());
        mEmployee.setMail(mMailFieldUiHelper.getEmailAddress());
        mEmployee.setPhoneNumber(mPhoneNumberFileUiHelper.getPhoneNumber());
        mEmployee.setDateOfBirth(mCalendarDateOfBirth.getTime());
        mEmployee.setDateOfJoin(mCalendarDateOfJoin.getTime());
        return mEmployee;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ok:
                ProgressDialog mProgressDialog = CustumProgressDialog.getProgressDialog(Edit_employee_activity.this);
                if (!checkIfError()) {
                    Employee mEmployee = postEmployeeData();
                    if (mSelectedImage != null) {
                        mProgressDialog.show();
                        File file = new File(mSelectedImage.getPath());
                        RequestBody body = RequestBody.create(MediaType.parse("image/"), file);
                        MultipartBody.Part image = MultipartBody.Part.createFormData("upload", file.getName(), body);
                        mProgressDialog.show();
                        //  mEmployeeApi.updateEmployee(mEmployee,image,mProgressDialog);
                    } else {
                        mProgressDialog.show();
                        //mEmployeeApi.updateEmployee(mEmployee,null,mProgressDialog);
                    }
                }
                break;
            case R.id.cancel:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean checkIfError() {
        //// TODO: 28/10/17
        return false;
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
                new DatePickerDialog(Edit_employee_activity.this, datePicker, mCalender.get(Calendar.YEAR), mCalender.get(Calendar.MONTH), mCalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocusGain) {
                if (isFocusGain)
                    new DatePickerDialog(Edit_employee_activity.this, datePicker, mCalender.get(Calendar.YEAR), mCalender.get(Calendar.MONTH), mCalender.get(Calendar.DAY_OF_MONTH)).show();
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
                    mSelectedImage = result.getUri();
                    mBottomSheetImage.showDeleteDialog();
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
                break;
            case Config.SELECT_IMAGE_FROM_STORAGE:
                if (resultCode == RESULT_OK) {
                    mSelectedImage = data.getData();
                    CropImage.activity(mSelectedImage)
                            .start(Edit_employee_activity.this);
                }
                break;
        }
    }

    private void setUpOnClickListener() {
        setUpCalender(mDateOfBirth, mCalendarDateOfBirth);
        setUpCalender(mDateOfJoin, mCalendarDateOfJoin);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetImage.showBottomSheet();
            }
        });
        mWorkProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAlertForSelectingWorkProfile();
            }

        });
    }

    private void createAlertForSelectingWorkProfile() {
        //// TODO: 27/10/17 create work profile selecting dialog
    }

}

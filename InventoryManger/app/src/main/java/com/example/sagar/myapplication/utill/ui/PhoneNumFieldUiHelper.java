package com.example.sagar.myapplication.utill.ui;

import android.app.Activity;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.sagar.myapplication.modal.Mail;
import com.example.sagar.myapplication.modal.PhoneNum;

public class PhoneNumFieldUiHelper {

    private Spinner mSpinner;
    private EditText mPhoneNum;
    private SpinnerHelper mSpinnerHelper;
    private Activity mActivity;
    private int arrResId;
    private String selectedString;

    public PhoneNumFieldUiHelper(Activity mActivity, EditText mPhone, Spinner mSpinner, int arrResId) {
        helper(mActivity, mPhone, mSpinner, arrResId);
    }

    public PhoneNumFieldUiHelper(Activity mActivity, EditText mPhone, Spinner mSpinner, int arrResId, String defaultValue) {
        helper(mActivity, mPhone, mSpinner, arrResId);
        mSpinnerHelper.setDefaultItem(defaultValue);
        selectedString = defaultValue;
    }

    private void helper(Activity mActivity, EditText mPhoneNum, Spinner mSpinner, int arrResId) {
        this.mSpinner = mSpinner;
        this.arrResId = arrResId;
        this.mPhoneNum = mPhoneNum;
        this.mActivity = mActivity;
        mSpinnerHelper = new SpinnerHelper(mActivity, arrResId, mSpinner, new SpinnerHelper.ItemSelectListener() {
            @Override
            public void selectedString(String str) {
                selectedString = str;
            }
        });
    }

    public PhoneNum getPhoneNumber() {
        return new PhoneNum(mPhoneNum.getText().toString(), selectedString);
    }

    public void setPhoneNum(PhoneNum mPhoneNum) {
        this.mPhoneNum.setText(mPhoneNum.getValue());
        mSpinnerHelper.setDefaultItem(mPhoneNum.getSub());
        selectedString = mPhoneNum.getSub();
    }

}

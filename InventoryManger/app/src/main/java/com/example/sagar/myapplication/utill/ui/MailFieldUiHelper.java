package com.example.sagar.myapplication.utill.ui;

import android.app.Activity;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.sagar.myapplication.modal.Mail;
import com.example.sagar.myapplication.utill.ui.SpinnerHelper;

public class MailFieldUiHelper {

    private Spinner mSpinner;
    private EditText mMail;
    private SpinnerHelper mSpinnerHelper;
    private Activity mActivity;
    private int arrResId;
    private String selectedString;

    public MailFieldUiHelper(Activity mActivity, EditText mMail, Spinner mSpinner, int arrResId) {
        helper(mActivity, mMail, mSpinner, arrResId);
    }

    public MailFieldUiHelper(Activity mActivity, EditText mMail, Spinner mSpinner, int arrResId, String defaultValue) {
        helper(mActivity, mMail, mSpinner, arrResId);
        mSpinnerHelper.setDefaultItem(defaultValue);
        selectedString = defaultValue;
    }

    private void helper(Activity mActivity, EditText mMail, Spinner mSpinner, int arrResId) {
        this.mSpinner = mSpinner;
        this.arrResId = arrResId;
        this.mMail = mMail;
        this.mActivity = mActivity;
        mSpinnerHelper = new SpinnerHelper(mActivity, arrResId, mSpinner, new SpinnerHelper.ItemSelectListener() {
            @Override
            public void selectedString(String str) {
                selectedString = str;
            }
        });
    }

    public Mail getEmailAddress() {
        return new Mail(mMail.getText().toString(), selectedString);
    }

    public void setEmailAddress(Mail mEmail) {
        this.mMail.setText(mEmail.getValue());
        mSpinnerHelper.setDefaultItem(mEmail.getSub());
        selectedString = mEmail.getSub();
    }

}

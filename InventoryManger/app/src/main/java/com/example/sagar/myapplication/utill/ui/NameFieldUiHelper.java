package com.example.sagar.myapplication.utill.ui;

import android.widget.EditText;

import com.example.sagar.myapplication.modal.Name;

public class NameFieldUiHelper {

    private EditText mName,mNameMiddle,mNameLast,mNameSuffix,mNameFamily;
    private Boolean hasAnchor;

    public NameFieldUiHelper(EditText mName, EditText mNameMiddle, EditText mNameLast, EditText mNameSuffix, EditText mNameFamily){
        this.mName = mName;
        this.mNameMiddle = mNameMiddle;
        this.mNameLast   = mNameLast;
        this.mNameFamily = mNameFamily;
        this.mNameSuffix = mNameSuffix;
    }

    public  void setHasAnchor(boolean hasAnchor){
        this.hasAnchor = hasAnchor;
    }

    public boolean getHasAnchor(){
        return hasAnchor;
    }

    public Name getName(){
        Name mName = new Name();
        mName.setName(this.mName.getText().toString());
        mName.setMiddle(mNameMiddle.getText().toString());
        mName.setSuffix(mNameSuffix.getText().toString());
        mName.setFamilyName(mNameFamily.getText().toString());
        mName.setLast(mNameLast.getText().toString());
        return mName;
    }

    public void setName(Name mName){
        this.mName.setText(mName.getName());
        mNameLast.setText(mName.getLast());
        if(mName.getSuffix()!=null)mNameSuffix.setText(mName.getSuffix());
        if(mName.getMiddle()!=null)mNameMiddle.setText(mName.getMiddle());
        if(mName.getFamilyName()!=null)mNameFamily.setText(mName.getFamilyName());
    }

}

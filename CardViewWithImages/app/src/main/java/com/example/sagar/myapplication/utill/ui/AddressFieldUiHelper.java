package com.example.sagar.myapplication.utill.ui;

import android.widget.EditText;

import com.example.sagar.myapplication.modal.Address;
import com.example.sagar.myapplication.modal.Customer;

public class AddressFieldUiHelper{

    private EditText mAddress,mStreet,mNeighbourHood,mState,mZipCode,mPostOffice,mCity;

    public AddressFieldUiHelper(EditText mAddress, EditText mStreet, EditText mNeighbourHood, EditText mState, EditText mZipCode, EditText mPostOffice, EditText mCity) {
        this.mAddress = mAddress;
        this.mStreet = mStreet;
        this.mNeighbourHood = mNeighbourHood;
        this.mState = mState;
        this.mZipCode = mZipCode;
        this.mPostOffice = mPostOffice;
        this.mCity = mCity;
    }

    public Address getAddress(){
        Address mAddressObj  = new Address();
        mAddressObj.setCity(mCity.getText().toString());
        mAddressObj.setNeighborhood(mNeighbourHood.getText().toString());
        mAddressObj.setPoBox(mPostOffice.getText().toString());
        mAddressObj.setState(mState.getText().toString());
        mAddressObj.setAddress(mAddress.getText().toString());
        mAddressObj.setZipcode(mZipCode.getText().toString());
        mAddressObj.setStreet(mStreet.getText().toString());
        return mAddressObj;
    }

    public void setAddress(Address mAddress){
        this.mAddress.setText(mAddress.getAddress());
        this.mCity.setText(mAddress.getCity());
        this.mZipCode.setText(mAddress.getZipcode());
        this.mState.setText(mAddress.getState());
        if(mAddress.getPoBox()!=null)
            this.mState.setText(mAddress.getPoBox());
        if(mAddress.getNeighborhood()!=null)
            this.mNeighbourHood.setText(mAddress.getNeighborhood());
        if(mAddress.getStreet()!=null)
            this.mStreet.setText(mAddress.getStreet());
    }



}

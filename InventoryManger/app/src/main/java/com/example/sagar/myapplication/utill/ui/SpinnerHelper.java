package com.example.sagar.myapplication.utill.ui;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Objects;

public class SpinnerHelper {

    private Spinner mSpinner;
    private int textArrResId;
    private String selectedString;
    private Activity mActivity;
    private String[] strArray;
    private ItemSelectListener mItemSelectListener;

    public SpinnerHelper(Activity mActivity, int textArrResId, Spinner mSpinner, ItemSelectListener mItemSelectListener) {
        this.mSpinner = mSpinner;
        this.textArrResId = textArrResId;
        this.mActivity = mActivity;
        strArray = mActivity.getResources().getStringArray(textArrResId);
        this.mItemSelectListener = mItemSelectListener;
        setSpinner();
    }

    public String getSelectedString() {
        return selectedString;
    }

    private void setSpinner() {
        ArrayAdapter<CharSequence> stringAdapter = ArrayAdapter.createFromResource(mActivity, textArrResId, android.R.layout.simple_spinner_item);
        stringAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(stringAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mItemSelectListener.selectedString((String) adapterView.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void setDefaultItem(String str) {
        if (str.isEmpty())
            return;
        int index = 0;
        for (String aStrArray : strArray) {
            if (Objects.equals(aStrArray, str)) {
                mSpinner.setSelection(index);
                break;
            }
            index++;
        }
    }

    public interface ItemSelectListener {
        public void selectedString(String str);
    }
}

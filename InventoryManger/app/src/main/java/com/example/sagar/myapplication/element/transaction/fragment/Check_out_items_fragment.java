package com.example.sagar.myapplication.element.transaction.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.sagar.myapplication.R;


public class Check_out_items_fragment extends Fragment {

    private EditText mCustomerEditText;

    public Check_out_items_fragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_check_out, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUiElement();
        mCustomerEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocusGain) {
                if (isFocusGain) {
                    setOnClickListenerDialogCreator();
                }
            }
        });
        mCustomerEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOnClickListenerDialogCreator();
            }
        });
    }

    private void setUiElement() {
        mCustomerEditText = (EditText) getView().findViewById(R.id.check_out_customer);
    }

    private void setOnClickListenerDialogCreator() {
        AlertDialog.Builder Builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_check_out_items, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view_dialog_check_out);

    }

}

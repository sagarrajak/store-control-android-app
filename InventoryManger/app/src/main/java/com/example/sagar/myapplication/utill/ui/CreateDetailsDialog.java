package com.example.sagar.myapplication.utill.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.element.product.Activity_create_product;
import com.example.sagar.myapplication.utill.Err;

public class CreateDetailsDialog {

    private Context mContext;
    private EditText mTargetEditText;
    private String setString;
    private LayoutInflater mLayoutInflater;


    public CreateDetailsDialog(Context context  , EditText mTargetEditText, LayoutInflater mLayoutInflater ){
        this.mContext   =  context;
        this.mTargetEditText = mTargetEditText;
        setString="";
        this.mLayoutInflater =  mLayoutInflater;
    }
    
    public void showDialog(){
            mTargetEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus)
                    helperMethod();
                }
            });
            mTargetEditText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                helperMethod();
                }
            });
    }

    private void helperMethod(){
                final AlertDialog.Builder builder = new AlertDialog.Builder( mContext )  ;
                final View inflate = mLayoutInflater.inflate(R.layout.details_dialog,null);
                final EditText mEditText = (EditText) inflate.findViewById(R.id.textView);
                final TextView mTextView = (TextView) inflate.findViewById(R.id.editTextView);
                mEditText.setText(mTargetEditText.getText());
                builder.setView(inflate);
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mTargetEditText.setText(mEditText.getText());
                    }
                });
                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){}
                });
                builder.show();
                mEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                    @Override
                    public void afterTextChanged(Editable editable) {
                        if( mEditText.getText().toString().length()>300){
                            Err.s( mContext.getApplicationContext() , "Text size above limit" );
                            String tem = mEditText.getText().toString();
                            mEditText.setText(tem.substring(0,tem.length()-1));
                            mEditText.setSelection(299);
                        }
                        mTextView.setText(mEditText.getText().toString().length()+"/300");
                    }
                });
    }


}

package com.example.sagar.myapplication.element.stock;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;

import com.example.sagar.myapplication.CustumProgressDialog;
import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.api.StockApi;
import com.example.sagar.myapplication.utill.Err;

public class BottomSheetHelper {

    private Context mContext;
    private StockApi mStockApi;
    private LayoutInflater mLayoutInflater;
    private BottomSheetDialog mBottomSheetDialog;
    private RadioButton stock_name, expire_date, created_date, selling_price, buyed_price, profit_per_item, stock_quantity, mSelectedRadioButtom = null;


    public BottomSheetHelper(Context mContext, StockApi mStockApi, LayoutInflater mLayoutInflater) {
        this.mContext = mContext;
        this.mStockApi = mStockApi;
        this.mLayoutInflater = mLayoutInflater;
        this.mSelectedRadioButtom = null;
        createSheet();
    }

    private void createSheet() {
        View view = mLayoutInflater.inflate(R.layout.buttom_sheet_stock_activity, null);
        mBottomSheetDialog = new BottomSheetDialog(mContext);
        mBottomSheetDialog.setContentView(view);
        stock_name = (RadioButton) view.findViewById(R.id.bottom_sheet_stock_name_radiobottom);
        expire_date = (RadioButton) view.findViewById(R.id.bottom_sheet_stock_expire_date_radiobuttm);
        created_date = (RadioButton) view.findViewById(R.id.bottom_sheet_stock_created_date_radiobuttom);
        selling_price = (RadioButton) view.findViewById(R.id.bottom_sheet_stock_selling_price_radiobuttom);
        buyed_price = (RadioButton) view.findViewById(R.id.bottom_sheet_stock_buyed_price_radiobuttom);
        profit_per_item = (RadioButton) view.findViewById(R.id.bottom_sheet_stock_profit_per_item_radiobuttom);
        stock_quantity = (RadioButton) view.findViewById(R.id.bottom_sheet_stock_quantity_radiobuttom);
        setOnClickListener(R.id.bottom_sheet_stock_name, stock_name);
        setOnClickListener(R.id.bottom_sheet_stock_expire_date, expire_date);
        setOnClickListener(R.id.bottom_sheet_stock_created_date, created_date);
        setOnClickListener(R.id.bottom_sheet_stock_selling_price, selling_price);
        setOnClickListener(R.id.bottom_sheet_stock_buyed_price, buyed_price);
        setOnClickListener(R.id.bottom_sheet_stock_profit_per_item, profit_per_item);
        setOnClickListener(R.id.bottom_sheet_stock_quantity, stock_quantity);
    }

    public void showButtomSheet() {
        mBottomSheetDialog.show();
    }

    private void setOnClickListener(final int id, final RadioButton mRadioButton) {
        mRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSelectedRadioButtom != null && mSelectedRadioButtom != mRadioButton)
                    mSelectedRadioButtom.setChecked(false);
                mSelectedRadioButtom = mRadioButton;
                ProgressDialog mProgressDialog = CustumProgressDialog.getProgressDialog(mContext);
                mProgressDialog.show();
                mStockApi.sortStock(id, mProgressDialog);
            }
        });
    }


}

package com.example.sagar.myapplication.element.stock.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.sagar.myapplication.CustumProgressDialog;
import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.adapter.StockAdapter;
import com.example.sagar.myapplication.api.ApiClient;
import com.example.sagar.myapplication.api.StockApi;
import com.example.sagar.myapplication.api.interfaces.ApiStockInterface;
import com.example.sagar.myapplication.element.stock.dialog.SelectProductCreateStock;
import com.example.sagar.myapplication.element.stock.dialog.SelectRetailerCreateStock;
import com.example.sagar.myapplication.modal.Product;
import com.example.sagar.myapplication.modal.Retailer;
import com.example.sagar.myapplication.modal.stock.Expire;
import com.example.sagar.myapplication.modal.stock.Notification;
import com.example.sagar.myapplication.modal.stock.Stock;
import com.example.sagar.myapplication.utill.Err;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Activity_create_new_stock extends AppCompatActivity {

    private  EditText name , quantity , product , buyed_price , retailer , expire_date , buy_date, selling_price, minimum_product , details;
    private  CheckBox notification_box , expire_box;
    private  boolean isNotificationChecked =false , isProductExpire=false;
    private  Calendar buyed_date_calender , expire_date_calender;
    private  Toolbar  mToolbar;
    private  StockApi mStockApi;
    private  StockAdapter mStockAdapter;
    private  Product  selectedProduct ;
    private  Retailer selectedRetailer;
    private   SelectRetailerCreateStock mSelectedRetailerCreateStock;
    private   SelectProductCreateStock  mSelectedProductCreateStock;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acitivity_create_new_stock);
        setUiElement();
        disableKeyBoardOnFocus();
        setDatePickerDialog();
        createProductPickerDialog();
        createRetailerPickerDialog();
        createDetailsEditDialog();
        setNotificationCheckBox();
        setExpireCheckBox();
        setToolbar();
    }

    private void createRetailerPickerDialog() {
        retailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                final AlertDialog.Builder mAlertDialogBuilder =  new AlertDialog.Builder(Activity_create_new_stock.this);
                mAlertDialogBuilder.setTitle("Select Retailer");
                LayoutInflater inflater = getLayoutInflater();
                View itemView = inflater.inflate(R.layout.genral_dialog , null);
                ProgressBar mProgressBar  =  (ProgressBar) itemView.findViewById(R.id.progressBar);
                RecyclerView mRecycleView = (RecyclerView) itemView.findViewById(R.id.recycle_view);
                mRecycleView.setLayoutManager(new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL , false));
                mSelectedRetailerCreateStock  = new SelectRetailerCreateStock( getBaseContext() , mProgressBar , selectedRetailer );
                mRecycleView.setAdapter(mSelectedRetailerCreateStock);
                mAlertDialogBuilder.setView(itemView);
                mAlertDialogBuilder.setPositiveButton("Ok" , new  DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selectedRetailer = mSelectedRetailerCreateStock.getSelectedRetailer();
                        if(selectedRetailer!=null){
                           retailer.setText(selectedRetailer.getName());
                        }
                        else{
                           retailer.setText("");
                        }
                    }
                });
                mAlertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){

                    }
                });
                mAlertDialogBuilder.show();
            }
        });
    }

    private void createProductPickerDialog(){
        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder mAlertDialogBuilder =  new AlertDialog.Builder(Activity_create_new_stock.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate( R.layout.genral_dialog , null );
                mAlertDialogBuilder.setTitle("Select product");
                mAlertDialogBuilder.setView(dialogView);
                RecyclerView mRecyclerView   = (RecyclerView) dialogView.findViewById(R.id.recycle_view);
                ProgressBar mProgressBar     = (ProgressBar) dialogView.findViewById(R.id.progressBar);
                mRecyclerView.setLayoutManager(new LinearLayoutManager( getBaseContext() , LinearLayoutManager.VERTICAL , false ) );
                mSelectedProductCreateStock =  new SelectProductCreateStock( getBaseContext() , mProgressBar , selectedProduct );
                mRecyclerView.setAdapter(mSelectedProductCreateStock);
                mAlertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selectedProduct = mSelectedProductCreateStock.getSelectedProduct();
                        if(selectedProduct!=null) {
                            product.setText(selectedProduct.getName());
                        }
                        else{
                            product.setText("");
                        }
                    }
                });
                mAlertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                mAlertDialogBuilder.show();
            }
        });
    }


    private void createDetailsEditDialog(){
        final AlertDialog.Builder mAlerDialogBuilder = new AlertDialog.Builder(Activity_create_new_stock.this);


    }

    private void setToolbar(){
        mToolbar  = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setNotificationCheckBox(){
        notification_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 isNotificationChecked = !isNotificationChecked;
                 if(isNotificationChecked) {
                     minimum_product.setVisibility(View.VISIBLE);
                     minimum_product.setText("");
                 }
                 else{
                     minimum_product.setVisibility(View.GONE);
                 }
            }
        });
    }

    private void setExpireCheckBox() {
        expire_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isProductExpire=!isProductExpire;
                if(isProductExpire){
                    expire_date.setVisibility(View.VISIBLE);
                }
                else{
                    expire_date.setVisibility(View.GONE);
                    expire_date_calender.clear();
                    expire_date.setText("");
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void disableKeyBoardOnFocus(){
        product.setShowSoftInputOnFocus(false);
        retailer.setShowSoftInputOnFocus(false);
        expire_date.setShowSoftInputOnFocus(false);
        buy_date.setShowSoftInputOnFocus(false);
    }

    private void setUiElement(){
        name             = (EditText) findViewById(R.id.create_stock_name);
        quantity         = (EditText) findViewById(R.id.create_stock_quantity_of_stock);
        product          = (EditText) findViewById(R.id.create_stock_product);
        buyed_price      = (EditText) findViewById(R.id.create_stock_buyed_price);
        retailer         = (EditText) findViewById(R.id.create_stock_retailer);
        expire_date      = (EditText) findViewById(R.id.create_stock_expire_date);
        buy_date         = (EditText) findViewById(R.id.create_stock_buyed_date);
        selling_price    = (EditText) findViewById(R.id.create_stock_selling_price);
        minimum_product  = (EditText) findViewById(R.id.create_stock_mini_product_notification);
        notification_box = (CheckBox) findViewById(R.id.create_stock_checkbox_notification);
        expire_box       = (CheckBox) findViewById(R.id.create_stock_checkbox_expire);
        details          = (EditText) findViewById(R.id.create_stock_details);
        mStockAdapter    = StockAdapter.getStockAdapter(getBaseContext());
        mStockApi        = new StockApi( getBaseContext());
        minimum_product.setVisibility(View.GONE);
        expire_date.setVisibility(View.GONE);
    }

    private void setDatePickerDialog(){
        buyed_date_calender  =  Calendar.getInstance();
        expire_date_calender =  Calendar.getInstance();
        datePickerDialogHelper( buyed_date_calender  , buy_date  );
        datePickerDialogHelper( expire_date_calender , expire_date );
    }

    private void datePickerDialogHelper(final  Calendar  calendar , final  EditText editText ){
       final DatePickerDialog.OnDateSetListener calenderPickerListener =   new DatePickerDialog.OnDateSetListener() {
           @Override
           public void onDateSet(DatePicker datePicker, int year , int month  , int day_of_month ) {
                calendar.set(Calendar.MONTH , month );
                calendar.set(Calendar.DAY_OF_MONTH , day_of_month );
                calendar.set(Calendar.YEAR , year );
                editText.setText( new SimpleDateFormat("dd/MM/yyyy").format( calendar.getTime() ) );
           }
       };
       editText.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                Calendar currentDateCalender = Calendar.getInstance();
                new DatePickerDialog( Activity_create_new_stock.this , calenderPickerListener , currentDateCalender.get(Calendar.YEAR) , currentDateCalender.get(Calendar.MONTH) , currentDateCalender.get(Calendar.DAY_OF_MONTH)  ).show();
           }
       });
    }

    private boolean isNumber(String str){
        for(int i=0;i<str.length();i++){
            if( str.charAt(i)>='0' && str.charAt(i) <='9') continue;
            else {
                Err.s(getBaseContext(),str.charAt(i)+"");
                return false;
            }
        }
        return true;
    }

    private boolean checkError(){
        CoordinatorLayout coordinateLayout = (CoordinatorLayout)findViewById(R.id.create_stock_coordinate_layout);
        if(name.getText().toString().isEmpty()){
            Snackbar.make(coordinateLayout,"Name can't be empty", Snackbar.LENGTH_LONG).show();
            return  false;
        }
        if(!isNumber(quantity.getText().toString())){
            Snackbar.make(coordinateLayout,"Quantity must be a number" , Snackbar.LENGTH_LONG).show();
            return false;
        }
        if(selectedProduct==null){
            Snackbar.make(coordinateLayout,"Must select product",Snackbar.LENGTH_LONG).show();
            return  false;
        }
        if(buyed_price.getText().toString().isEmpty()){
            Snackbar.make(coordinateLayout,"Price must not be empty" , Snackbar.LENGTH_LONG).show();
            return  false;
        }
        if(!isNumber(buyed_price.getText().toString())){
            Snackbar.make(coordinateLayout,"Price is a number" , Snackbar.LENGTH_LONG).show();
            return  false;
        }
        if(selectedRetailer==null){
            Snackbar.make(coordinateLayout,"Must select retailer",Snackbar.LENGTH_LONG).show();
            return false;
        }
        if(buy_date.getText().toString().isEmpty()){
            Snackbar.make(coordinateLayout,"Buy date must not be empty" , Snackbar.LENGTH_LONG).show();
            return  false;
        }
        if(expire_date.getText().toString().isEmpty()){
            Snackbar.make(coordinateLayout,"Expire date must not be empty",Snackbar.LENGTH_LONG).show();
            return false;
        }
        return  true;
    }


    private void postData(){
        if(checkError()){
            Stock temStock =  createStock();
            ProgressDialog dialog = CustumProgressDialog.getProgressDialog(Activity_create_new_stock.this);
            dialog.show();
            mStockApi.createStock(dialog,temStock);
        }
    }

    private Stock createStock(){
        Stock stock = new Stock();
        stock.setName(name.getText().toString());
        stock.setQuantity(Integer.parseInt(quantity.getText().toString()));
        stock.setSeller(selectedRetailer.getId());
        stock.setProduct(selectedProduct.getId());
        stock.setBuyedDate(buyed_date_calender.getTime().toString());
        stock.setSellingPrice(Integer.parseInt(selling_price.getText().toString()));
        stock.setExpire(new Expire(isProductExpire , expire_date_calender.getTime().toString()));
        stock.setNotification(new Notification( isNotificationChecked , Integer.parseInt(minimum_product.getText().toString())));
        stock.setBuyedDate(buyed_date_calender.getTime().toString());
        stock.setDetails(details.getText().toString());
        return  stock;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_ok_cancel , menu );
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.ok :
                postData();
                break;
            case R.id.cancel :
                finish();;
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

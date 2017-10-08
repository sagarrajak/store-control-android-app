package com.example.sagar.myapplication.element.stock.activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.api.ApiClient;
import com.example.sagar.myapplication.api.interfaces.ApiStockInterface;
import com.example.sagar.myapplication.element.stock.dialog.AlertDialogBuilderAdapterFilterStock;
import com.example.sagar.myapplication.modal.Product;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class Activity_filter_intent extends AppCompatActivity {

    private Button mButton;
    private EditText mProductList , adding_date_lower_bound, adding_date_upper_bound, expire_date_lower_bound, expire_date_upper_bound, item_count_lower_bound, item_count_upper_bound;
    private ApiStockInterface mApiStockInterface;
    private AlertDialogBuilderAdapterFilterStock mAlertDialogBuilderAdapterFilterStock;
    private Set<Product> mProductSelectedSet;
    private Calendar addingDateLowerCalender,addingDateUpperCalender,expireDateUpperCalender,expireDateLowerCalender;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_intent);
        mApiStockInterface = ApiClient.getClient().create(ApiStockInterface.class);
        mAlertDialogBuilderAdapterFilterStock = new AlertDialogBuilderAdapterFilterStock(getBaseContext());
        mProductSelectedSet = new HashSet<>();
        setUiElement();
        dateOnClickDialogSetup();
    }

    private void  setUiElement(){
        mProductList             = (EditText) findViewById(R.id.filter_stock_product_list);
        adding_date_lower_bound  = (EditText) findViewById(R.id.filter_stock_adding_date_lower_bound);
        adding_date_upper_bound  = (EditText) findViewById(R.id.filter_stock_adding_date_upper_bound);
        expire_date_lower_bound  = (EditText) findViewById(R.id.filter_stock_expire_date_lower_bound);
        expire_date_upper_bound  = (EditText) findViewById(R.id.filter_Stock_expire_date_upper_bound);
        item_count_lower_bound   = (EditText) findViewById(R.id.filter_stock_item_count_lower_bound);
        item_count_upper_bound   = (EditText) findViewById(R.id.filter_stock_item_count_greather_then);
    }

    private void setDatePicker(final Calendar calendar , final EditText mEditText  ){
        final DatePickerDialog.OnDateSetListener calenderPickerListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int  month , int  day_of_month ) {
                calendar.set(Calendar.MONTH , month);
                calendar.set(Calendar.YEAR , year);
                calendar.set(Calendar.DAY_OF_MONTH , day_of_month);
                mEditText.setText( new SimpleDateFormat("dd/MM/yyyy").format( calendar.getTime() ) );
            }
        };
        mEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Calendar temCalender =  Calendar.getInstance();
                 new DatePickerDialog( Activity_filter_intent.this , calenderPickerListener , temCalender.get(Calendar.YEAR)   , temCalender.get(Calendar.MONTH) , temCalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void dateOnClickDialogSetup(){
        addingDateUpperCalender  =  Calendar.getInstance();
        addingDateLowerCalender  =  Calendar.getInstance();
        expireDateLowerCalender  =  Calendar.getInstance();
        expireDateUpperCalender  =  Calendar.getInstance();
        setDatePicker( addingDateLowerCalender , adding_date_lower_bound );
        setDatePicker( addingDateUpperCalender , adding_date_upper_bound );
        setDatePicker( expireDateLowerCalender , expire_date_lower_bound );
        setDatePicker( expireDateUpperCalender , expire_date_upper_bound );
    }

    private void submit(){


    }


    private void alertDialogForSelectingProduct(){
          View itemView = getLayoutInflater().inflate(R.layout.recycleview,null);
          RecyclerView mRecyclerView = (RecyclerView) itemView.findViewById(R.id.recycle_view);
          mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false));
          mRecyclerView.setAdapter(mAlertDialogBuilderAdapterFilterStock);

          AlertDialog.Builder  builder = new AlertDialog.Builder(Activity_filter_intent.this);
          builder.setView(itemView);
          builder.setCancelable(false);
          builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialogInterface, int i) {
                    mProductSelectedSet = mAlertDialogBuilderAdapterFilterStock.getSelectedList();
              }
          });
          builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialogInterface, int i){
                    finish();
              }
          });
          builder.show();
    }




}

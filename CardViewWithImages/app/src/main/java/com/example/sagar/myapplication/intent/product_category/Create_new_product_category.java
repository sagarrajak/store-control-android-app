package com.example.sagar.myapplication.intent.product_category;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.adapter.ProductCategoryAdapter;
import com.example.sagar.myapplication.api.ApiClient;
import com.example.sagar.myapplication.api.ProductTypeApi;
import com.example.sagar.myapplication.api.interfaces.ApiProductInterface;
import com.example.sagar.myapplication.modal.Product;
import com.example.sagar.myapplication.utill.Err;
import com.example.sagar.myapplication.utill.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Create_new_product_category extends AppCompatActivity {

    private EditText name,details;
    private RecyclerView mRecyclearView;
    private ApiProductInterface mApiProductInterface;
    private ProductTypeApi mProductTypeApi;
    private ProgressBar mProgressbar;
    private Myadapter mMyadapter;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_product_category);
        setUiElement();
        setDialogCreator();
        setRecycleView();
        setToolbar();

    }

    private void setUiElement(){

        name                  = (EditText) findViewById(R.id.product_type_name);
        details               = (EditText) findViewById(R.id.product_type_details);
        mRecyclearView        = (RecyclerView) findViewById(R.id.recycle_view_create_category);
        mProgressbar          = (ProgressBar) findViewById(R.id.create_cateogry_progress_bar);
        mApiProductInterface  = ApiClient.getClient().create(ApiProductInterface.class);
        mMyadapter = new Myadapter();
        toolbar = (Toolbar) findViewById(R.id.activity_create_product_type_toolbar);
        mProductTypeApi       =   ProductTypeApi.getProductTypeApi(ProductCategoryAdapter.getCategoryAdapter(this));

    }

    private void setToolbar(){

        setTitle("Product category");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    private void setDialogCreator(){

        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(Create_new_product_category.this);
                LayoutInflater inflater = getLayoutInflater();
                View inflate = inflater.inflate(R.layout.details_dialog,null);
                final EditText editText = (EditText) inflate.findViewById(R.id.textView);
                editText.setText(details.getText());
                builder.setView(inflate);
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        details.setText(editText.getText());
                    }
                });
                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate( R.menu.menu_create_employee_activity , menu );
        return true;

    }

    private boolean checkError(){

        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.layout_create_new_product_category);

        if(name.getText().toString().isEmpty()){
            Snackbar.make(coordinatorLayout , "Name is empty" , Snackbar.LENGTH_LONG).show();
            return false;
        }
        else if(details.getText().toString().isEmpty()) {
            Snackbar.make(coordinatorLayout , "Details is empty" , Snackbar.LENGTH_LONG).show();
            return false;
        }

        return  true;
    }

    private void setRecycleView(){

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(  getBaseContext() , LinearLayoutManager.VERTICAL , false );
        mRecyclearView.setLayoutManager(mLinearLayoutManager);
        mRecyclearView.setAdapter(mMyadapter);
        getData();

    }

    private void getData(){

        mProgressbar.setVisibility(View.VISIBLE);
        mApiProductInterface.getProductList(Token.token).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                    if(response.code()==200){
                        setProductList(response.body());
                    }
                    else{
                        Err.e("Error in fetching data ");
                    }
            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                    t.printStackTrace();
                    Err.e("Error in gettig all products");
            }
        });

    }

    private ProgressDialog createProgressDialog(){
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        return  dialog;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.create_employee :
                ProgressDialog dialog = createProgressDialog();
                if(checkError()){
                    dialog.show();
                     mProductTypeApi.addProductTypeToMultipleProduct( name.getText().toString() , details.getText().toString() , dialog , mMyadapter.getSelectedItemList() );
                }
                break;
            case R.id.cancel_employee :
                finish();
                break;
        }
        return true;

    }

    private void setProductList(List<Product> mProduct){
        mProgressbar.setVisibility(View.GONE);
        Err.s(this,"what is going on");
        mMyadapter.setProductList(mProduct);
    }


    class Myadapter extends RecyclerView.Adapter<Myadapter.MyViewHolder>{

        List<Product> mProduct;
        HashMap<Product,Integer> productMap;

        public Myadapter(){
            mProduct    = new ArrayList<>();
            productMap  = new HashMap<>();
        }

        public  void setProductList(List<Product> mProduct){
            this.mProduct =  mProduct;
            notifyDataSetChanged();
        }

        @Override
        public Myadapter.MyViewHolder onCreateViewHolder(ViewGroup parent , int viewType) {
            View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.select_product_category , parent , false );
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(Myadapter.MyViewHolder holder, final int position) {
                holder.mTextView.setText(mProduct.get(position).getName());
                holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(productMap.containsKey(mProduct.get(position)))
                            productMap.remove(mProduct.get(position));
                        else
                            productMap.put(mProduct.get(position),1);
                    }
                });
        }

        public  ArrayList<Product> getSelectedItemList(){
            ArrayList<Product> temList = new ArrayList<>();
             for( Product obj : productMap.keySet() ){
                 temList.add(obj);
             }
            return temList;
        }

        @Override
        public int getItemCount() {
            return mProduct.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView ;
            public CheckBox mCheckBox ;
            public MyViewHolder(View itemView) {
                super(itemView);
                mTextView = (TextView) itemView.findViewById(R.id.product_name);
                mCheckBox = (CheckBox) itemView.findViewById(R.id.checkBox);
            }
        }
    }

}

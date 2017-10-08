package com.example.sagar.myapplication.element.product;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.api.ApiClient;
import com.example.sagar.myapplication.api.interfaces.ApiProductInterface;
import com.example.sagar.myapplication.modal.ProductPopulated;
import com.example.sagar.myapplication.modal.ProductType;
import com.example.sagar.myapplication.modal.Retailer;
import com.example.sagar.myapplication.utill.Err;
import com.example.sagar.myapplication.utill.Token;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class About_product_activity extends AppCompatActivity {

    private TextView  name,category,retailer,brand,price,details;
    private ImageView mImageView,brandView;
    private String productId;
    private Toolbar mToolbar;
    private ApiProductInterface mApiProductInterface;
    private LinearLayout mLinearLayout;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_product_activity);
        setUiElement();
        setToolbar();

    }

    private void setUiElement(){

        name     = (TextView) findViewById(R.id.about_product_name);
        category = (TextView) findViewById(R.id.about_product_category);
        retailer = (TextView) findViewById(R.id.about_product_retailer);
        brand    = (TextView) findViewById(R.id.about_product_brand);
        price    = (TextView) findViewById(R.id.about_product_price);
        details  = (TextView) findViewById(R.id.about_product_details);
        mImageView = (ImageView) findViewById(R.id.about_product_image_view);
        brandView  = (ImageView) findViewById(R.id.about_product_brand_view);
        mLinearLayout = (LinearLayout) findViewById(R.id.product_linear_layout);
        mApiProductInterface = ApiClient.getClient().create(ApiProductInterface.class);
        mProgressBar = (ProgressBar) findViewById(R.id.product_progress_bar);
        mLinearLayout.setVisibility(View.GONE);


    }

    private void setToolbar(){

        mToolbar = (Toolbar) findViewById(R.id.about_product_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        CallApi(getIntent().getStringExtra("id"));

    }

    private void CallApi( String id){

        mApiProductInterface.getProductPopulated( Token.token ,  id ).enqueue(new Callback<ProductPopulated>() {
            @Override
            public void onResponse(Call<ProductPopulated> call, Response<ProductPopulated> response) {
                    if( response.code() == 200 ){
                        setValue(response.body());
                    }
            }
            @Override
            public void onFailure(Call<ProductPopulated> call, Throwable t) {
                t.printStackTrace();
                Err.s( getBaseContext() , "Error in loading data" );
                mProgressBar.setVisibility(View.GONE);
            }
        });

    }

    private String getString(List<ProductType> pp ){

        StringBuilder build = new StringBuilder();
        for(int i=0; i < pp.size(); i++ ){
            if(i!=pp.size()-1)
                build.append(pp.get(i).getProductType().toString()+"," );
            else
                build.append(pp.get(i).getProductType().toString());
        }

        return  build.toString();
    }

    private String getRetailerList(List<Retailer> rt){

        StringBuilder build = new StringBuilder();
        for(int i=0; i < rt.size(); i++ ){
            if(i!=rt.size()-1)
                build.append(rt.get(i).getName().toString()+"," );
            else
                build.append(rt.get(i).getName().toString());
        }

        return  build.toString();
    }

    private void setValue(ProductPopulated pp){
         String url = "http://res.cloudinary.com/droxr0kdp/image/upload/v1482011353/";
         name.setText(pp.getName());
         category.setText(getString(pp.getType()));
         brand.setText(pp.getBrand().getBrand());
         price.setText(pp.getPrice());
         details.setText(pp.getDetail());
         retailer.setText(getRetailerList(pp.getRetailer()));
         Glide.with(this)
                .load(url+pp.getImage())
                        .centerCrop()
                            .placeholder(R.drawable.product)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .crossFade()
                                            .into(mImageView);
         mProgressBar.setVisibility(View.GONE);
         mLinearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate( R.menu.menu_about_product_menu , menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.edit_product :

                break;
            case R.id.delete_product :

                break;
            case  R.id.product_category :

                break;
            case  R.id.product_brand :


                break;
        }
        return false;
    }

}

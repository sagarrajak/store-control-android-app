package com.example.sagar.myapplication.element.product;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.sagar.myapplication.element.product.Adapter.AboutProductCategoryAdapter;
import com.example.sagar.myapplication.element.product.Adapter.AboutProductRetailerAdapter;
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
    private ImageView mProductImageView,brandView;
    private String productId;
    private Toolbar mToolbar;
    private ApiProductInterface mApiProductInterface;
    private LinearLayout mLinearLayout;
    private ProgressBar mProgressBar;
    private ImageView  mCategoryAnchor,mRetailerAnchor,mAboutBrand;
    private Boolean mCategoryHidden,mRetailerHidden;
    private RecyclerView mRecycleViewRetailer,mRecycleViewCategory;

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
        mProductImageView = (ImageView) findViewById(R.id.about_product_image_view);
        //different anchor image view
        mCategoryAnchor = (ImageView)findViewById(R.id.about_product_category_anchor);
        mRetailerAnchor = (ImageView)findViewById(R.id.about_product_retailer_anchor);
        mAboutBrand     = (ImageView)findViewById(R.id.about_product_brand_anchor);
        //anchor to hide and open category and retailer view
        mCategoryHidden = true;
        mRetailerHidden = true;
        // recycle view from courponding details section
        mRecycleViewCategory  = (RecyclerView)findViewById(R.id.about_product_category_recycle_view);
        mRecycleViewRetailer  = (RecyclerView)findViewById(R.id.about_product_retailer_recycle_view);

        //root of layout
        mLinearLayout = (LinearLayout) findViewById(R.id.product_linear_layout);
        mApiProductInterface = ApiClient.getClient().create(ApiProductInterface.class);
        mProgressBar = (ProgressBar) findViewById(R.id.product_progress_bar);
        // set initially layout to hidden untill data loaded
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
                    else{
                        Err.s(getBaseContext(),"Error in loading data");
                        finish();
                    }
            }
            @Override
            public void onFailure(Call<ProductPopulated> call, Throwable t) {
                t.printStackTrace();
                Err.s(getBaseContext() , "Error in loading data");
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    //set drop down menu for the category
    private void setRecycleViewForCategory(List<ProductType> mProductType){
        AboutProductCategoryAdapter categoryAdapter =  new AboutProductCategoryAdapter(About_product_activity.this , mProductType);
        mRecycleViewCategory.setAdapter(categoryAdapter);
        mRecycleViewCategory.setLayoutManager(new LinearLayoutManager(About_product_activity.this , LinearLayoutManager.VERTICAL , false));
    }

    //set drop down menu for the retailer
    private void setRecycleViewForRetailer(List<Retailer> mRetailer){
        AboutProductRetailerAdapter retailerAdapter = new AboutProductRetailerAdapter(About_product_activity.this , mRetailer);
        mRecycleViewRetailer.setAdapter(retailerAdapter);
        mRecycleViewRetailer.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
    }

    private void setCategoryAnchor(){
        mCategoryAnchor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategoryHidden = !mCategoryHidden;
                if(mCategoryHidden){
                    mCategoryAnchor.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                    mRecycleViewCategory.setVisibility(View.GONE);
                }
                else{
                    mCategoryAnchor.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                    mRecycleViewCategory.setVisibility(View.VISIBLE);
                }
            }
        });
        if(mCategoryHidden){
            mCategoryAnchor.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
            mRecycleViewCategory.setVisibility(View.GONE);
        }
        else{
            mCategoryAnchor.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
            mRecycleViewCategory.setVisibility(View.VISIBLE);
        }
    }

    private void setRetailerAnchor(){
        mRetailerAnchor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                mRetailerHidden = !mRetailerHidden;
                if(mRetailerHidden){
                    mRetailerAnchor.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                    mRecycleViewRetailer.setVisibility(View.GONE);
                }
                else{
                    mRetailerAnchor.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                    mRecycleViewRetailer.setVisibility(View.VISIBLE);
                }
            }
        });
        if(mRetailerHidden){
            mRetailerAnchor.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
            mRecycleViewRetailer.setVisibility(View.GONE);
        }
        else{
            mRetailerAnchor.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
            mRecycleViewRetailer.setVisibility(View.VISIBLE);
        }
    }

    private String getProductTypeList(List<ProductType> pp ){
        StringBuilder build = new StringBuilder();
        if(pp.size()>2){
            for(int i=0;i<2;i++){
                if(i!=1)
                    build.append(pp.get(i).getProductType()).append(",");
                else
                    build.append(pp.get(i).getProductType());
            }
            build.append("...");
            return  build.toString();
        }
        else{
            for(int i=0;i<pp.size();i++){
                if(i!=pp.size()-1)
                    build.append(pp.get(i).getProductType()).append(',');
                else
                    build.append(pp.get(i).getProductType());
            }
            return  build.toString();
        }
    }

    private String getRetailerList(List<Retailer> rt){
        StringBuilder build = new StringBuilder();
        if(rt.size()>2){
            for(int i=0;i<2;i++){
                if(i!=1)
                    build.append(rt.get(i).getName().getName()).append(",");
                else
                    build.append(rt.get(i).getName().getName());
            }
            build.append("...");
            return  build.toString();
        }
        else{
            for(int i=0;i<rt.size();i++){
                if(i!=rt.size()-1)
                    build.append(rt.get(i).getName().getName()).append(',');
                else
                    build.append(rt.get(i).getName().getName());
            }
            return  build.toString();
        }
    }

    private void setValue(ProductPopulated pp){
         String url = "http://res.cloudinary.com/droxr0kdp/image/upload/v1482011353/";
         setTitle(pp.getName());
         name.setText(pp.getName());
         category.setText(getProductTypeList(pp.getType()));
         brand.setText(pp.getBrand().getBrand());
         price.setText(pp.getPrice()+" Rupees");
         details.setText(pp.getDetail());
         retailer.setText(getRetailerList(pp.getRetailer()));
         Glide.with(this)
                .load(url+pp.getImage())
                        .centerCrop()
                            .placeholder(R.drawable.product)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .crossFade()
                                            .into(mProductImageView);
         setRetailerAnchor();
         setCategoryAnchor();
         setRecycleViewForRetailer(pp.getRetailer());
         setRecycleViewForCategory(pp.getType());
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
        }
        return false;
    }

}

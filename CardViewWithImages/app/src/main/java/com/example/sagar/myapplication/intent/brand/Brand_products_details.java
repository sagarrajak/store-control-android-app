package com.example.sagar.myapplication.intent.brand;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.modal.Brand;

public class Brand_products_details extends AppCompatActivity{

    private Toolbar toolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private Brand brand;
    private ImageView imageView;
    private final String url = "http://res.cloudinary.com/droxr0kdp/image/upload/v1482011353/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_products_details);
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            imageView = (ImageView) findViewById(R.id.brand_product_imageview);
            mCollapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar_layout);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            brand = (Brand) getIntent().getSerializableExtra("brand");
            if(brand!=null){
                    mCollapsingToolbarLayout.setTitle(brand.getBrand());
                            Glide.with(getApplicationContext())
                              .load(url+brand.getLogo())
                                .centerCrop()
                                    .placeholder(R.drawable.employee)
                                        .crossFade()
                                            .into(imageView);

            }
    }

    private void setRecycleView(){


    }
}

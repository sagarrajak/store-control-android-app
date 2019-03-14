package com.example.sagar.myapplication.element.brand;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.adapter.brand.BrandAssociatedProductAdapter;
import com.example.sagar.myapplication.modal.Brand;

public class Brand_products_details extends AppCompatActivity {

    private Toolbar toolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private Brand brand;
    private ImageView imageView;
    private ProgressBar mProgressBar;
    private BrandAssociatedProductAdapter mBrandAssociatedProductAdapter;
    private final String url = "http://res.cloudinary.com/droxr0kdp/image/upload/v1482011353/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_products_details);
        setToolbar();
        setUiElement();
        setRecycleView();
        if (brand != null) {
            Glide.with(getApplicationContext())
                    .load(url + brand.getLogo())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .placeholder(R.drawable.employee)
                    .crossFade()
                    .into(imageView);
            mCollapsingToolbarLayout.setTitle(brand.getBrand());
        }

    }

    private void setUiElement() {

        imageView = (ImageView) findViewById(R.id.brand_product_imageview);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        brand = (Brand) getIntent().getSerializableExtra("brand");
        mProgressBar = (ProgressBar) findViewById(R.id.brand_product_progressbar);

        mCollapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);

    }

    private void setRecycleView() {

        RecyclerView mRecycleView = (RecyclerView) findViewById(R.id.recycle_view);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycleView.setLayoutManager(mLinearLayoutManager);
        mBrandAssociatedProductAdapter = new BrandAssociatedProductAdapter(getBaseContext(), mProgressBar, brand);
        mRecycleView.setAdapter(mBrandAssociatedProductAdapter);

    }

    private void setToolbar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_brand_product_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_brand_product_delete:

                break;
            case R.id.menu_brand_product_add:

                break;
            case R.id.menu_brand_product_details:

                break;
            case R.id.menu_brand_product_edit:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteBrand() {


    }

    private void editBrand() {


    }

    private void details() {


    }

    private void addProduct() {


    }

}

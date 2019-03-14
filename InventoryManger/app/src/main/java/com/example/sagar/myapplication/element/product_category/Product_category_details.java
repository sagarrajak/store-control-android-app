package com.example.sagar.myapplication.element.product_category;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sagar.myapplication.CustumProgressDialog;
import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.api.ApiClient;
import com.example.sagar.myapplication.api.interfaces.ApiProductInterface;
import com.example.sagar.myapplication.modal.Data;
import com.example.sagar.myapplication.modal.ProductPopulated;
import com.example.sagar.myapplication.modal.ProductType;
import com.example.sagar.myapplication.utill.Err;
import com.example.sagar.myapplication.utill.Token;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Product_category_details extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private Toolbar toolbar;
    private TextView mCategoryDetails;
    private ApiProductInterface mApiProductInterface;
    private ProgressBar mProgressBar;
    private MyAdapter myAdapter;
    private ProgressDialog mProgressDialog;
    private ProductType mProductType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category_details);
        setUiElement();
        setToolbar();
        setRecycleView();
    }

    private void setUiElement() {

        mCategoryDetails = (TextView) findViewById(R.id.TextViewDetails);
        mProgressBar = (ProgressBar) findViewById(R.id.product_category_recycle_view);
        mApiProductInterface = ApiClient.getClient().create(ApiProductInterface.class);
        myAdapter = new MyAdapter();
        mProgressDialog = CustumProgressDialog.getProgressDialog(getBaseContext());
        mProductType = (ProductType) getIntent().getSerializableExtra("Category");

    }

    private void setToolbar() {

        setTitle(mProductType.getProductType());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void setRecycleView() {

        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_product_category_details);
        mRecyclerView.setAdapter(myAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//            mCategoryDetails.setText(mProductType.getDetials());
        mCategoryDetails.setMovementMethod(new ScrollingMovementMethod());
        myAdapter.listProduct();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.about_category_edit:


                //// TODO: 29/8/17 implement menu
                break;
            case R.id.about_category_delete:


                //// TODO: 29/8/17 implement menu
                break;
            case R.id.about_category_add_product:


                //// TODO: 29/8/17 implement menu
                break;
        }
        return true;
    }


    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        private List<ProductPopulated> mlist;

        public MyAdapter() {
            mlist = new ArrayList<>();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_product_category_details_list_card, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    mProgressDialog.show();
//                    deleteProduct(position);
                }
            });
            holder.name.setText(mlist.get(position).getName());
            holder.brand.setText(mlist.get(position).getBrand().getBrand());
        }

        @Override
        public int getItemCount() {
            return mlist.size();
        }

        public void listProduct() {

            mApiProductInterface.getProductPopulatedCategoryAssociatedList(Token.token, mProductType.getId())
                    .enqueue(new Callback<List<ProductPopulated>>() {
                        @Override
                        public void onResponse(Call<List<ProductPopulated>> call, Response<List<ProductPopulated>> response) {
                            if (response.code() == 200) {
                                setData(response.body());
                            } else {
                                Err.s(getBaseContext(), "Error in Loading Data");
                            }
                            mProgressBar.setVisibility(View.GONE);
                            mProgressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<List<ProductPopulated>> call, Throwable t) {
                            t.printStackTrace();
                            Err.s(getBaseContext(), "Error in Loading Data");
                        }
                    });

        }

        public void deleteProduct(int position) {

            mApiProductInterface.deleteCategoryFromPrticularProduct(Token.token, mlist.get(position).getId(), mProductType.getId())
                    .enqueue(new Callback<Data>() {
                        @Override
                        public void onResponse(Call<Data> call, Response<Data> response) {
                            if (response.code() == 200) {
                                Err.s(getBaseContext(), "Success in removing product from category");
                                listProduct();
                            } else {
                                Err.s(getBaseContext(), "Failed in removing product from category");
                                mProgressDialog.dismiss();
                            }

                        }

                        @Override
                        public void onFailure(Call<Data> call, Throwable t) {
                            t.printStackTrace();
                            Err.s(getBaseContext(), "Failed in removing product from category");
                            mProgressDialog.dismiss();
                        }
                    });

        }


        private void setData(List<ProductPopulated> mList) {
            this.mlist = mList;
            notifyDataSetChanged();
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView name, brand;
            public ImageView imageView;

            public MyViewHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.product_category_name);
                brand = (TextView) itemView.findViewById(R.id.product_category_details);
                imageView = (ImageView) itemView.findViewById(R.id.product_category_delete_view);
            }
        }


    }

}

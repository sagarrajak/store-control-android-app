package com.example.sagar.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.api.ApiClient;
import com.example.sagar.myapplication.api.interfaces.ApiProductInterface;
import com.example.sagar.myapplication.modal.Brand;
import com.example.sagar.myapplication.modal.ProductPopulated;
import com.example.sagar.myapplication.utill.Err;
import com.example.sagar.myapplication.utill.Token;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrandAssociatedProductAdapter extends RecyclerView.Adapter<BrandAssociatedProductAdapter.MyViewHolder> {

    private Context mContext;
    private List<ProductPopulated> mProduct;
    private ProgressBar bar;
    private Brand brand;
    private ApiProductInterface mApiProductInterface;

    public BrandAssociatedProductAdapter(Context mContext , ProgressBar bar , Brand brand ){
        this.mContext = mContext;
        this.mProduct = new ArrayList<>();
        this.bar = bar;
        mApiProductInterface = ApiClient.getClient().create(ApiProductInterface.class);
        this.brand = brand;
        setData(bar);
    }

    public void setData(final  ProgressBar bar){

        mApiProductInterface.getProductPopulatedBrandAssociatedList( Token.token , brand.getId()  )
                .enqueue(new Callback<List<ProductPopulated>>() {
                        @Override
                        public void onResponse(Call<List<ProductPopulated>> call, Response<List<ProductPopulated>> response) {
                            if(response.code() == 200 ){
                                mProduct = response.body();
                                notifyDataSetChanged();
                            }
                            else{
                                Err.s(mContext , "Error in fetching data");
                            }
                            bar.setVisibility(View.GONE);
                        }
                        @Override
                        public void onFailure(Call<List<ProductPopulated>> call, Throwable t) {
                                t.printStackTrace();
                                bar.setVisibility(View.GONE);
                        }
        });

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.product_list_card , parent , false );
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.price.setText(mProduct.get(position).getPrice());
            holder.name.setText(mProduct.get(position).getName());
            holder.mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //// TODO: 27/8/17  working on it
                    createOptionMenu(position);
                }
            });
    }

    private void  createOptionMenu(int postion){


    }

    @Override
    public int getItemCount() {
        return mProduct.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public  View view;
        public TextView name,price;
        public ImageView mImageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView ;
            name = (TextView) itemView.findViewById(R.id.product_category_name);
            price = (TextView) itemView.findViewById(R.id.product_category_details);
            mImageView = (ImageView) itemView.findViewById(R.id.product_list_view_image_view);
        }
    }

}

package com.example.sagar.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.api.BrandApi;
import com.example.sagar.myapplication.modal.Brand;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SAGAR on 1/15/2017.
 */
public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.MyViewHolder>{

    private   Context   mContext;
    private   BrandApi  mBrandApi;
    private List<Brand> mList ;
    private static  BrandAdapter brandAdapter;
    private final String url = "http://res.cloudinary.com/droxr0kdp/image/upload/v1482011353/";

    public BrandAdapter(Context mContext){
           this.mContext   = mContext;
           this.mBrandApi  = BrandApi.getBrandApi(this);
           mList = new ArrayList<>();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.brand_card , parent , false );
         return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){

            holder.brandName.setText(mList.get(position).getBrand());
            holder.brandDetails.setText(mList.get(position).getDetails());
            Glide.with(mContext.getApplicationContext())
                    .load(url+mList.get(position).getLogo())
                        .centerCrop()
                            .placeholder(R.drawable.employee)
                                .crossFade()
                                         .into(holder.imageView);


        }
    @Override
    public int getItemCount() {
        return mList.size();
    }
    public void addNewItems(List<Brand> mBrand){
        this.mList = mBrand;
        notifyDataSetChanged();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public  TextView brandName , brandDetails;
        public ImageView imageView;
        public MyViewHolder(View itemView){
            super(itemView);
            brandName = (TextView) itemView.findViewById(R.id.brand_card_brand_name);
            brandDetails = (TextView) itemView.findViewById(R.id.brand_card_brand_details);
            imageView = (ImageView) itemView.findViewById(R.id.brand_card_brand_logo);
        }
    }
    public  static BrandAdapter getmBrandAdapter(Context mContext){
        if(brandAdapter == null)
            brandAdapter = new BrandAdapter(mContext);
        return  brandAdapter;
    }
}

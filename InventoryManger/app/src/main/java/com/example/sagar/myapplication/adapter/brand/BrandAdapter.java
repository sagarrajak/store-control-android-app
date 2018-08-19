package com.example.sagar.myapplication.adapter.brand;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.api.BrandApi;
import com.example.sagar.myapplication.element.brand.Brand_products_details;
import com.example.sagar.myapplication.modal.Brand;

import java.util.ArrayList;
import java.util.List;
public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.MyViewHolder>{

    private   Context   mContext;
    private   BrandApi mBrandApi;
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
    public void onBindViewHolder(MyViewHolder holder, final int position){

            holder.brandName.setText(mList.get(position).getBrand());
            holder.brandDetails.setText(mList.get(position).getDetails());
            Glide.with(mContext.getApplicationContext())
                    .load(url+mList.get(position).getLogo())
                        .centerCrop()
                            .placeholder(R.drawable.employee)
                                .crossFade()
                                         .into(holder.imageView);
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(  mContext.getApplicationContext() ,  Brand_products_details.class  );
                    intent.putExtra("brand", mList.get(position) );
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            });

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
        public View view ;

        public MyViewHolder(View itemView){
            super(itemView);
            brandName = (TextView) itemView.findViewById(R.id.brand_card_brand_name);
            brandDetails = (TextView) itemView.findViewById(R.id.brand_card_brand_details);
            imageView = (ImageView) itemView.findViewById(R.id.brand_card_brand_logo);
            view = itemView;
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v){
                     final  CharSequence[] items  = { "Delete" , "Edit" , "Details" };
                    AlertDialog.Builder builder =  new AlertDialog.Builder(mContext);
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case  0 :
                                    //delete item ;
                                    break;
                                case  1 :
                                    //Edit items
                                    break;
                                case  2 :
                                    //go to brand
                                    break;
                            }
                        }
                    });
                    builder.show();
                    return false;
                }
            });
        }

    }
    public  static BrandAdapter getmBrandAdapter(Context mContext){
        if(brandAdapter == null)
            brandAdapter = new BrandAdapter(mContext);
        return  brandAdapter;
    }
}

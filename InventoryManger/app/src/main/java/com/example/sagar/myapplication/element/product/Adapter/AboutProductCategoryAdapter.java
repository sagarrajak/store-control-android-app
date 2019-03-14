package com.example.sagar.myapplication.element.product.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.modal.ProductType;

import java.util.List;

public class AboutProductCategoryAdapter extends RecyclerView.Adapter<AboutProductCategoryAdapter.MyViewHolder> {

    private Context mContext;
    private List<ProductType> mType;

    public AboutProductCategoryAdapter(Context mContext, List<ProductType> mType) {
        this.mContext = mContext;
        this.mType = mType;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.about_product_linear_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mInfoButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //// TODO: 24/10/17  open about cateogy intent
            }
        });
        holder.mTextView.setText(mType.get(position).getProductType());
    }


    @Override
    public int getItemCount() {
        return mType.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView mInfoButtom;
        public TextView mTextView;

        public MyViewHolder(View view) {
            super(view);
            mInfoButtom = (ImageView) view.findViewById(R.id.about_product_linear_view_image_bottom);
            mTextView = (TextView) view.findViewById(R.id.about_product_linear_view_text_view);
        }
    }
}

package com.example.sagar.myapplication.element.product.Adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.modal.Retailer;

import java.util.ArrayList;
import java.util.List;

public class AboutProductRetailerAdapter extends RecyclerView.Adapter<AboutProductRetailerAdapter.MyViewHolder>{

    private Context mContext;
    private List<Retailer> mRetailer;

    public AboutProductRetailerAdapter(Context mContext, List<Retailer> mRetailer) {
        this.mContext = mContext;
        this.mRetailer = mRetailer;
    }

    @Override
    public AboutProductRetailerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.about_product_linear_view , parent , false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AboutProductRetailerAdapter.MyViewHolder holder, int position) {
            holder.infoButtom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //// TODO: 24/10/17 open about that retailer
                }
            });
            holder.name.setText(mRetailer.get(position).getName().getName());
    }

    @Override
    public int getItemCount() {
        return mRetailer.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView infoButtom;
        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.about_product_linear_view_text_view);
            infoButtom = (ImageView) view.findViewById(R.id.about_product_linear_view_image_bottom);
        }
    }
}

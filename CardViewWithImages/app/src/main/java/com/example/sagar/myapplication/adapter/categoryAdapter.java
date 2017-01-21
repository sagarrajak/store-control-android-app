package com.example.sagar.myapplication.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.api.ProductTypeApi;
import com.example.sagar.myapplication.modal.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SAGAR on 1/15/2017.
 */
public class CategoryAdapter  extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder>{

    private Context mContext;
    private ProductTypeApi mProductTypeApi;
    private List<Category> mList;
    private static  CategoryAdapter mCategoryAdapter;
    String url = "http://res.cloudinary.com/droxr0kdp/image/upload/w_300,h_300,c_crop/w_200/v1482011353/";

    private CategoryAdapter(Context mContext){
          this.mContext = mContext;
          mProductTypeApi =  ProductTypeApi.getmProductTypeApi(this);
          mList  = new ArrayList<>();
    }
    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         View view  = LayoutInflater.from(parent.getContext()).inflate( R.layout.category_card , parent , false);
         return  new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(CategoryAdapter.MyViewHolder holder, int position) {
        holder.category_details.setText(mList.get(position).getCategory_name());
        holder.category_name.setText(mList.get(position).getCategory_name());
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView category_name , category_details;
        public MyViewHolder(View itemView) {
            super(itemView);
            category_name  = (TextView) itemView.findViewById(R.id.category_card_name);
            category_details = (TextView) itemView.findViewById(R.id.category_card_details);
        }
    }
    public  static  CategoryAdapter  getmCategoryAdapter(Context mContext){
         if(mCategoryAdapter == null)
             mCategoryAdapter = new CategoryAdapter(mContext);
        return  mCategoryAdapter;
    }
}

package com.example.sagar.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sagar.myapplication.Err;
import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.modal.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyProductViewHodler>{
    private Context mContext;
    private List<Product> mList;

    private  static  ProductAdapter mProductAdapter;

    public  ProductAdapter( Context  mContext ){
          this.mContext = mContext;
          mList = new ArrayList<>();
    }

    @Override
    public MyProductViewHodler onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.product_card, parent , false );
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Err.s(mContext.getApplicationContext() , "working");
            }
        });
        return  new MyProductViewHodler(view);
    }

    @Override
    public void onBindViewHolder(final MyProductViewHodler holder, int i){
        holder.mTextViewPrice.setText(mList.get(i).getPrice());
        holder.mTextViewName.setText(mList.get(i).getName());
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu(holder.mImageView);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }
    private void showMenu(View view){
        PopupMenu  popupMenu = new PopupMenu(mContext,view);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.menu_product,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item){
               // TODO: 12/13/2016
                switch (item.getItemId()){
                    case R.id.product_menu_details :
                        break;
                    case R.id.product_menu_delete :
                        break;
                    case R.id.product_menu_edit:
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    public void addProductList(List<Product> mList){
            this.mList = mList;
            notifyDataSetChanged();
    }

    public class MyProductViewHodler extends  RecyclerView.ViewHolder{
        public TextView mTextViewName ,mTextViewPrice;
        public ImageView mImageView;
        public MyProductViewHodler(View itemView){
            super(itemView);
            mTextViewName = (TextView) itemView.findViewById(R.id.product_name);
            mImageView   =   (ImageView) itemView.findViewById(R.id.ic_menu_dots);
            mTextViewPrice = (TextView) itemView.findViewById(R.id.product_price);
        }
    }

    public static ProductAdapter getProductAdapter(Context mContext){
        if(mProductAdapter == null )
            mProductAdapter = new ProductAdapter(mContext);

        return mProductAdapter;
    }

}

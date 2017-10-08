package com.example.sagar.myapplication.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sagar.myapplication.CustumProgressDialog;
import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.adapter.interfaces.ProductAdapterInterface;
import com.example.sagar.myapplication.api.ProductApi;
import com.example.sagar.myapplication.element.product.About_product_activity;
import com.example.sagar.myapplication.modal.ProductPopulated;

import java.util.ArrayList;
import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> implements ProductAdapterInterface {

    private static  ProductListAdapter mProductListAdapter;
    private Context mContext;
    private ProductApi mProductApi;
    private List<ProductPopulated> mList;


    public  ProductListAdapter(Context mContext){
        this.mContext = mContext;
        mList = new ArrayList<>();
        mProductApi =  ProductApi.getmProductApi(this);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate( R.layout.product_list_card , parent , false );
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.mTextViewBrand.setText(mList.get(position).getBrand().getBrand());
        holder.mTextViewName.setText(mList.get(position).getName());
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUpMenu(v,position);
            }
        });
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view){
                CharSequence[] sequences = { "Delete" , "Edit" };
                new AlertDialog.Builder(mContext)
                        .setItems(sequences, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                switch (i){
                                    case  0:

                                        break;
                                    case  1:

                                        break;
                                }
                            }
                        }).show();
                return false;
            }
        });

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( mContext , About_product_activity.class );
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id" ,mList.get(position).getId());
                mContext.startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void addNewproductList(List<ProductPopulated> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewName,mTextViewBrand;
        public ImageView mImageView ;
        public  View view ;
        public MyViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.product_list_view_image_view);
            mTextViewName = (TextView) itemView.findViewById(R.id.product_category_name);
            mTextViewBrand = (TextView) itemView.findViewById(R.id.product_category_details);
            view = itemView;
        }
    }

    private class MyMenuItemClickListener implements  PopupMenu.OnMenuItemClickListener{
        private int positon;
        private MyMenuItemClickListener(int positon){
            this.positon = positon;
        }
        @Override
        public boolean onMenuItemClick(MenuItem item){
            switch(item.getItemId()){
                case R.id.product_menu_delete :
                    DeleteProductDialog(positon);
                    break;
                case R.id.product_menu_edit :

                    break;
            }
            return false;
        }
    }

    private void showPopUpMenu(View view , int positon ){
            PopupMenu popupMenu = new PopupMenu(mContext , view);
            MenuInflater menuInflater = popupMenu.getMenuInflater();
            menuInflater.inflate( R.menu.menu_product, popupMenu.getMenu() );
            popupMenu.setOnMenuItemClickListener(new MyMenuItemClickListener(positon));
            popupMenu.show();
    }

    private void DeleteProductDialog(final int position){
            final ProgressDialog dialogProgress = CustumProgressDialog.getProgressDialog(mContext);
            dialogProgress.show();
            new  AlertDialog.Builder(mContext)
                        .setTitle("Are you sure to delete this item")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which){
                                    mProductApi.deleteProduct(mList.get(position).getId() , dialogProgress );
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                }
                            })
                            .show();

    }

    public void setContext(Context context){
        this.mContext = context;
    }

    public static ProductListAdapter  getProductListAdapter(Context mContext){
        if(mProductListAdapter==null)
            mProductListAdapter = new ProductListAdapter(mContext);
        else
            mProductListAdapter.setContext(mContext);
        return  mProductListAdapter;
    }

}

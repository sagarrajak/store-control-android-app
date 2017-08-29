package com.example.sagar.myapplication.adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.adapter.interfaces.ProductAdapterInterface;
import com.example.sagar.myapplication.api.ProductApi;
import com.example.sagar.myapplication.modal.Product;
import com.example.sagar.myapplication.modal.ProductPopulated;
import com.example.sagar.myapplication.utill.Err;

import java.util.ArrayList;
import java.util.List;

public class ProductGridAdapter extends RecyclerView.Adapter<ProductGridAdapter.MyProductViewHodler> implements ProductAdapterInterface {
    private Context mContext;
    private List<ProductPopulated> mList;
    private int position;
    private  static ProductGridAdapter mProductGridAdapter =null;
    private ProductApi mProductApi ;

    public ProductGridAdapter(Context  mContext ){
          this.mContext = mContext;
          mList = new ArrayList<>();
          mProductApi = ProductApi.getmProductApi(this);
    }

    @Override
    public MyProductViewHodler onCreateViewHolder(ViewGroup parent, int viewType){
            View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.product_grid_card, parent , false );
            return  new MyProductViewHodler(view);
    }

    @Override
    public void addNewproductList(List<ProductPopulated> mList) {
            this.mList = mList;
            notifyDataSetChanged();
    }

    class  MyMenuCLickListener implements  PopupMenu.OnMenuItemClickListener{
        int position ;
        public  MyMenuCLickListener(int position){
            this.position = position;
        }
        @Override
        public boolean onMenuItemClick(MenuItem item){
            switch(item.getItemId()){
                case R.id.product_menu_delete :
                    alertDialogDeleteBuilder(position);
                    break;
                case R.id.product_menu_edit:
                    break;
            }
            return false;
        }
    }

    @Override
    public void onBindViewHolder(final MyProductViewHodler holder,final int i){
            String url = "http://res.cloudinary.com/droxr0kdp/image/upload/v1482011353/";
            holder.mTextViewPrice.setText(mList.get(i).getPrice());
            holder.mTextViewName.setText(mList.get(i).getName());
            holder.mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showMenu(holder.mImageView , i);
                }
            });

            Glide
                .with(mContext.getApplicationContext())
                 .load(url+mList.get(i).getImage())
                    .centerCrop()
                      .placeholder(R.drawable.product)
                                .crossFade()
                                    .into(holder.mProductView);


    }

    private ProgressDialog createProgressDialog(){
        ProgressDialog dialog = new ProgressDialog(mContext);
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        return  dialog;
    }


    private void alertDialogDeleteBuilder(final int position){

        new AlertDialog
                .Builder(mContext)
                    .setMessage("Are you sure to want to delete this product")
                       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i){
                                    ProgressDialog mProgressDialog =  createProgressDialog();
                                    mProgressDialog.show();
                                    mProductApi.deleteProduct(mList.get(position).getId(),mProgressDialog);
                            }
                         })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                    Err.s(mContext,"Delete Canceled");
                            }
                        }).show();

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private void showMenu(View view , int position){
        PopupMenu  popupMenu = new PopupMenu(mContext,view);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.menu_product,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new MyMenuCLickListener(position));
        popupMenu.show();
    }

    public class MyProductViewHodler extends  RecyclerView.ViewHolder{
        public TextView mTextViewName ,mTextViewPrice;
        public ImageView mImageView,mProductView;
        public MyProductViewHodler(View itemView){
            super(itemView);
            mTextViewName = (TextView) itemView.findViewById(R.id.product_name);
            mImageView   =   (ImageView) itemView.findViewById(R.id.ic_menu_dots);
            mTextViewPrice = (TextView) itemView.findViewById(R.id.product_price);
            mProductView = (ImageView) itemView.findViewById(R.id.image_product);
        }
    }

    public void setmContext(Context mContext){
        this.mContext = mContext;
    }

    public static ProductGridAdapter getProductAdapter(Context Con){

        if(mProductGridAdapter == null )
            mProductGridAdapter = new ProductGridAdapter(Con);
        else
            mProductGridAdapter.setmContext(Con);

        return mProductGridAdapter;
    }

}

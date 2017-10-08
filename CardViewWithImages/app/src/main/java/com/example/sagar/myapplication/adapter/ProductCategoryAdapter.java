package com.example.sagar.myapplication.adapter;


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

import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.element.product_category.Product_category_details;
import com.example.sagar.myapplication.modal.ProductType;

import java.util.ArrayList;
import java.util.List;

public class ProductCategoryAdapter extends RecyclerView.Adapter<ProductCategoryAdapter.MyViewHolder>{

    private Context mContext;
    private List<ProductType> mList;
    private static ProductCategoryAdapter mProductCategoryAdapter;

    public ProductCategoryAdapter(Context mContext){
          this.mContext = mContext;
          mList  = new ArrayList<>();
    }


    @Override
    public ProductCategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         View view  = LayoutInflater.from(parent.getContext()).inflate( R.layout.category_card , parent , false);
         return  new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ProductCategoryAdapter.MyViewHolder holder, final int position) {


        holder.category_details.setText(mList.get(position).getDetials());
        holder.category_name.setText(mList.get(position).getProductType());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(  mContext.getApplicationContext() , Product_category_details.class );
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Category", mList.get(position) );
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {


        public  TextView category_name , category_details;
        public ImageView mImageView ;
        View view ;

        public MyViewHolder(View view) {


            super(view);
            category_name  = (TextView) view.findViewById(R.id.category_card_name);
            category_details = (TextView) view.findViewById(R.id.category_card_details);
            mImageView = (ImageView) view.findViewById(R.id.product_category_view_image_view_menu);
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopUpMenu(v);
                }
            });
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v){
                    final CharSequence[] items = {"Details" , "Delete" , "Edit" , "Products" , "Add new product" };
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case 0 :

                                    break;
                                case 1:

                                    break;
                                case 2:

                                    break;
                                case 3:
                                    break;
                                case 4:
                                    break;
                            }
                        }
                    });
                    builder.show();
                    return false;
                }
            });
            this.view = view ;


        }
    }

    private void showPopUpMenu(View view){
        PopupMenu popupMenu = new PopupMenu(mContext,view);
        MenuInflater mMenuInflater = popupMenu.getMenuInflater();
        mMenuInflater.inflate(R.menu.product_category_menu , popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item){
                switch (item.getItemId()){
                    case R.id.product_category_menu_add_new_product :
                        break;
                    case R.id.product_category_menu_delete :
                        break;
                    case R.id.product_category_menu_details :
                        break;
                    case R.id.product_category_menu_edit :
                        break;
                    case R.id.product_category_menu_product :
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    public  void  setmList( List<ProductType> categories){
        this.mList = categories;
        notifyDataSetChanged();
    }

    public void setContext(Context mContext){
        this.mContext = mContext;
     }

    public static ProductCategoryAdapter getCategoryAdapter(Context context){
        if(mProductCategoryAdapter ==null)
            mProductCategoryAdapter = new ProductCategoryAdapter(context);
        else
            mProductCategoryAdapter.setContext(context);
        return mProductCategoryAdapter;
    }
}

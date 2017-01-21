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
import com.example.sagar.myapplication.Err;
import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.adapter.interfaces.RetailerAdapterInterface;
import com.example.sagar.myapplication.api.RetailerApi;
import com.example.sagar.myapplication.modal.Retailer;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by SAGAR on 1/21/2017.
 */

public class RetailerGridAdapter  extends RecyclerView.Adapter<RetailerGridAdapter.MyViewHolder> implements RetailerAdapterInterface {


    private List<Retailer> retailer;
    private Context mContext;
    private RetailerApi mRetailerApi;
    private static RetailerGridAdapter mRetailerGridAdapter;

    public  RetailerGridAdapter(Context mContext){
         this.mContext = mContext;
         mRetailerApi = RetailerApi.getmReteilerApi(this);
         retailer = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.emploee_card,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int i) {
        String url = "http://res.cloudinary.com/droxr0kdp/image/upload/w_300,h_300,c_crop/w_200/v1482011353/";
//        String url = "http://res.cloudinary.com/droxr0kdp/image/upload/v1482011353/";
        holder.name.setText(retailer.get(i).getName());
        holder.email.setText(retailer.get(i).getMail());
        holder.mImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                showPopUpMenu(holder.mImageView , i);
            }
        });
        Glide.with(mContext.getApplicationContext())
                .load(url+retailer.get(i).getImage())
                   .centerCrop()
                     .placeholder(R.drawable.employee)
                         .crossFade()
                              .into(holder.employeePicture);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Err.s(mContext,"working");
            }
        });
    }

    @Override
    public int getItemCount() {
        return retailer.size();
    }


    @Override
    public void addNewReatilerList(List<Retailer> retailer) {
        this.retailer = retailer;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public  View view;
        public TextView name,email;
        public ImageView mImageView;
        public  ImageView employeePicture;
        public  int position;
        public MyViewHolder(View view){
            super(view);
            this.view = view;
            name  = (TextView) view.findViewById(R.id.name);
            email = (TextView) view.findViewById(R.id.mail);
            mImageView = (ImageView)view.findViewById(R.id.imageViewLog);
            employeePicture = (ImageView)view.findViewById(R.id.employee_picture);
        }
    }

    private void  showPopUpMenu(View view,int i){
        PopupMenu popupMenu = new PopupMenu(mContext,view);
        MenuInflater mMenuInflater = popupMenu.getMenuInflater();
        mMenuInflater.inflate(R.menu.grid_view_retailer_card_menu , popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new MyMenuOnClickListerner(i));
        popupMenu.show();
    }

    private ProgressDialog createProgressDialog(){
        ProgressDialog dialog = new ProgressDialog(mContext);
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        return  dialog;
    }

    class  MyMenuOnClickListerner implements  PopupMenu.OnMenuItemClickListener{
        private int position;
        public MyMenuOnClickListerner(int position ){
            this.position = position;
        }
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case  R.id.delete :
                    createDeleteDialog(position);
                    break;
                case  R.id.edit_employee :

                    break;
                case  R.id.send_message :

                    break;
                case R.id.retailer_products:

                    break;
            }
            return  false;
        }
        private void createDeleteDialog(final int position){

        /*Dialog   to  delete   employee*/
            new AlertDialog.Builder(mContext)
                    .setTitle("Delete Employee")
                    .setMessage("Are you sure to want to delete this employee")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .show();
        }
    }
    public static  RetailerGridAdapter getRetailerGridAdapter(Context mContext){
         if(mRetailerGridAdapter==null)
             mRetailerGridAdapter  = new RetailerGridAdapter(mContext);
        return  mRetailerGridAdapter;
    }

}


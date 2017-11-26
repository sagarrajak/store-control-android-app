package com.example.sagar.myapplication.adapter.retailer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sagar.myapplication.api.RetailerApi;
import com.example.sagar.myapplication.element.retailer.About_retailer_activity;
import com.example.sagar.myapplication.utill.Err;
import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.adapter.interfaces.RetailerAdapterInterface;
import com.example.sagar.myapplication.modal.Retailer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
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
        holder.name.setText(retailer.get(i).getName().getName()+" "+retailer.get(i).getName().getLast());
        holder.email.setText(retailer.get(i).getMail().getValue());
        holder.mImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                showPopUpMenu(holder.mImageView , i);
            }
        });
        Picasso.with(mContext)
                .load(url+retailer.get(i).getImage())
                .placeholder(R.drawable.employee)
                .into(holder.mImageView);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(mContext, About_retailer_activity.class);
                intent.putExtra("Retailer",retailer.get(i));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
    }

    public  void setmContext(Context mConetext){
        this.mContext = mConetext;
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
            employeePicture = (ImageView)view.findViewById(R.id.add_customer_picture);
        }
    }

    private void  showPopUpMenu( View view , int i){
        PopupMenu popupMenu = new PopupMenu(mContext,view);
        MenuInflater mMenuInflater = popupMenu.getMenuInflater();
        mMenuInflater.inflate(R.menu.grid_view_retailer_card_menu , popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new MyMenuOnClickListerner(i));
        popupMenu.show();
    }

    class  MyMenuOnClickListerner implements  PopupMenu.OnMenuItemClickListener{
        private int position;
        public MyMenuOnClickListerner(int position ){
            this.position = position;
        }
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){

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
         else
             mRetailerGridAdapter.setmContext(mContext);
        return  mRetailerGridAdapter;
    }
}


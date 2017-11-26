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

import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.adapter.interfaces.RetailerAdapterInterface;
import com.example.sagar.myapplication.api.RetailerApi;
import com.example.sagar.myapplication.element.retailer.About_retailer_activity;
import com.example.sagar.myapplication.modal.Retailer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RetailerListAdapter extends RecyclerView.Adapter<RetailerListAdapter.MyViewHolder> implements RetailerAdapterInterface{

        private List<Retailer> retailer;
        private Context mContext;
        private RetailerApi mRetailerApi;
        private  static   RetailerListAdapter mRetailerListAdapter;

        public RetailerListAdapter(Context mContext){
              this.mContext = mContext;
              retailer = new ArrayList<>();
              mRetailerApi = RetailerApi.getmReteilerApi(this);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_list_card,parent,false);
            return new MyViewHolder(view);
        }

        private String getName(Integer position){
            return   retailer.get(position).getName().getName() +" "+retailer.get(position).getName().getLast();
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position){
            String url = "http://res.cloudinary.com/droxr0kdp/image/upload/w_300,h_300,c_crop/w_200/v1482011353/";
            holder.name.setText(getName(position));
            holder.mail.setText(retailer.get(position).getMail().getValue());
            Picasso.with(mContext)
                    .load(url+retailer.get(position).getImage())
                    .placeholder(R.drawable.employee)
                    .into(holder.mImageView);
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, About_retailer_activity.class);
                    intent.putExtra("Retailer",retailer.get(position));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
             return retailer.size();
        }

        @Override
        public void addNewReatilerList(List<Retailer> retailer){
              this.retailer = retailer;
              notifyDataSetChanged();
        }

        public void setContext(Context context) {
            this.mContext = context;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{
            public TextView name,mail;
            public ImageView mImageView;
            public View view;
            public MyViewHolder(View itemView){
                super(itemView);
                this.view = itemView;
                name = (TextView) itemView.findViewById(R.id.employee_list_view_name);
                mail = (TextView) itemView.findViewById(R.id.employee_list_view_mail);
                mImageView = (ImageView)itemView.findViewById(R.id.employee_list_view_image_view);
            }
        }

      public static  RetailerListAdapter getRetailerListAdapter(Context mContext){
          if(mRetailerListAdapter == null)
              mRetailerListAdapter = new RetailerListAdapter(mContext);
          else
              mRetailerListAdapter.setContext(mContext);
          return  mRetailerListAdapter;
      }
}

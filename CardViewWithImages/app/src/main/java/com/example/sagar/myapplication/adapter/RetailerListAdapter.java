package com.example.sagar.myapplication.adapter;

import android.app.AlertDialog;
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

import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.adapter.interfaces.RetailerAdapterInterface;
import com.example.sagar.myapplication.api.RetailerApi;
import com.example.sagar.myapplication.modal.Retailer;

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
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position){
        holder.name.setText(retailer.get(position).getName());
        holder.mail.setText(retailer.get(position).getMail());
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUpMenu(view,position);
            }

        });
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //// TODO: 1/21/2017 about retailer
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

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name,mail;
        public ImageView mImageView;
        public View view;
        public MyViewHolder(View itemView){
            super(itemView);
            this.view = itemView;
            name = (TextView) itemView.findViewById(R.id.employee_list_view_name);
            mail = (TextView) itemView.findViewById(R.id.employee_list_view_mail);
            mImageView = (ImageView)itemView.findViewById(R.id.emloyee_list_view_image_view_menu);
        }
    }

    private void  showPopUpMenu(View view,int i){
        PopupMenu popupMenu = new PopupMenu(mContext,view);
        MenuInflater mMenuInflater = popupMenu.getMenuInflater();
        mMenuInflater.inflate(R.menu.grid_view_retailer_card_menu,popupMenu.getMenu());
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
                case  R.id.delete :
                    createDeleteDialog(position);
                    break;
                case  R.id.edit_employee :
                    // TODO: 1/21/2017 edit details
                    break;
                case  R.id.send_message :
                    //// TODO: 1/21/2017  send message
                    break;
                case R.id.retailer_products:
                    //// TODO: 1/21/2017 show all the product associated with that retailer
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
      public static  RetailerListAdapter getRetailerListAdapter(Context mContext){
          if(mRetailerListAdapter == null)
              mRetailerListAdapter = new RetailerListAdapter(mContext);
          return  mRetailerListAdapter;
      }
}

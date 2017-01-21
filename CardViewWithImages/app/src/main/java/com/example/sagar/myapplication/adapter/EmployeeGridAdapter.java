package com.example.sagar.myapplication.adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.sagar.myapplication.adapter.interfaces.EmployeeAdapterInterface;
import com.example.sagar.myapplication.api.EmployeeApi;
import com.example.sagar.myapplication.intent.employee.About_employee_activity;
import com.example.sagar.myapplication.modal.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeGridAdapter extends RecyclerView.Adapter<EmployeeGridAdapter.MyViewHolder> implements EmployeeAdapterInterface {

    private Context mContext;
    private  List<Employee> mlist;
    private  static EmployeeGridAdapter mEmployeeGridAdapter;
    private  EmployeeApi mEmployeeApi;

    public  static EmployeeGridAdapter getEmployeeAdapter(Context mContext){
        if(mEmployeeGridAdapter==null){
            mEmployeeGridAdapter=new EmployeeGridAdapter(mContext);
        }
        return mEmployeeGridAdapter;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
         View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.emploee_card, parent , false);
         return new MyViewHolder(itemView);
    }

    public EmployeeGridAdapter(Context mContext){
        this.mContext = mContext;
        mlist = new ArrayList<>();
        mEmployeeApi = EmployeeApi.getEmloyeeApi(this,mContext);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int i ){

        String url = "http://res.cloudinary.com/droxr0kdp/image/upload/w_300,h_300,c_crop/w_200/v1482011353/";
//        String url = "http://res.cloudinary.com/droxr0kdp/image/upload/v1482011353/";
        holder.name.setText(mlist.get(i).getName());
        holder.email.setText(mlist.get(i).getMail());
        holder.mImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
               showPopUpMenu(holder.mImageView , i);
            }
        });

        Glide.with(mContext.getApplicationContext())
                    .load(url+mlist.get(i).getImage())
                        .centerCrop()
                                .placeholder(R.drawable.employee)
                                  .crossFade()
                                    .into(holder.employeePicture);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                      Bundle bundle = new Bundle();
                      Intent intent = new Intent( mContext.getApplicationContext() , About_employee_activity.class );
                      intent.putExtra("Employee" , mlist.get(i));
                      mContext.startActivity(intent);
            }
        });
    }
    private void  showPopUpMenu(View view , int i){
        PopupMenu popupMenu = new PopupMenu(mContext,view);
        MenuInflater mMenuInflater = popupMenu.getMenuInflater();
        mMenuInflater.inflate(R.menu.grid_view_employee_card_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new MyMenuOnClickListerner(i));
        popupMenu.show();
    }

    public void addEmployeeList(List<Employee> body){
        mlist = body;
        notifyDataSetChanged();
    }

    private ProgressDialog createProgressDialog(){
        ProgressDialog dialog = new ProgressDialog(mContext);
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        return  dialog;
    }

    @Override
    public void addNewEmployeeList(List<Employee> mEmployee) {
        this.mlist = mEmployee;
        notifyDataSetChanged();
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
                            ProgressDialog progressDialog = createProgressDialog();
                            Err.e(mlist.get(position).getId());
                            progressDialog.show();
                            mEmployeeApi.deleteEmployeeImage( mlist.get(position).getId() , progressDialog );
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Err.e(mlist.get(position).getName());
                        }
                    })
                    .show();
        }
    }


    public  void setmContext(Context mContext){
        this.mContext = mContext;
    }
    @Override
    public int getItemCount(){
        return mlist.size();
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

    public void setFilter(ArrayList<Employee> mlist){
        this.mlist = mlist;
        notifyDataSetChanged();
    }

    public  void addData(Employee obj){
        mlist.add(obj);
        notifyDataSetChanged();
    }

    public List<Employee> getMlist(){
        return  mlist;
    }
  }

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

import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.adapter.interfaces.EmployeeAdapterInterface;
import com.example.sagar.myapplication.api.EmployeeApi;
import com.example.sagar.myapplication.element.employee.About_employee_activity;
import com.example.sagar.myapplication.modal.Employee;
import com.example.sagar.myapplication.utill.Err;

import java.util.ArrayList;
import java.util.List;

public class EmployeeListAdapter  extends RecyclerView.Adapter<EmployeeListAdapter.MyViewHolder> implements EmployeeAdapterInterface {

    private Context mContext;
    private EmployeeApi mEmployeeApi;
    private static  EmployeeListAdapter mEmployeeListAdapter;
    private List<Employee> employees;

    public EmployeeListAdapter( Context mContext ){
        this.mContext = mContext;
        mEmployeeApi = EmployeeApi.getEmloyeeApi(this);
        employees = new ArrayList<>();
    }

    public void setmContext(Context mContext){
        this.mContext = mContext;
    }
    @Override
    public EmployeeListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_list_card,parent,false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final EmployeeListAdapter.MyViewHolder holder, final int position) {
        holder.name.setText(employees.get(position).getName());
        holder.mail.setText(employees.get(position).getMail());
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUpMenu(view,position);
            }

        });
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent( mContext.getApplicationContext() , About_employee_activity.class );
                intent.putExtra("Employee" , employees.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount(){
        return employees.size();
    }

    @Override
    public void addNewEmployeeList(List<Employee> mEmployee) {
        if(mEmployee!=null)
            this.employees = mEmployee;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
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


    private ProgressDialog createProgressDialog(){
        ProgressDialog dialog = new ProgressDialog(mContext);
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        return  dialog;
    }

    private void  showPopUpMenu(View view , int i){
        PopupMenu popupMenu = new PopupMenu(mContext,view);
        MenuInflater mMenuInflater = popupMenu.getMenuInflater();
        mMenuInflater.inflate(R.menu.grid_view_employee_card_menu, popupMenu.getMenu());
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
                            Err.e(employees.get(position).getId());
                            progressDialog.show();
                            mEmployeeApi.deleteEmployeeImage( employees.get(position).getId() , progressDialog );
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Err.e(employees.get(position).getName());
                        }
                    })
                    .show();
        }

    }

    public static  EmployeeListAdapter getmEmployeeListAdapter(Context mContext){
        if(mEmployeeListAdapter == null )
            mEmployeeListAdapter =  new EmployeeListAdapter(mContext);
        else
            mEmployeeListAdapter.setmContext(mContext);

        return  mEmployeeListAdapter;
    }


}

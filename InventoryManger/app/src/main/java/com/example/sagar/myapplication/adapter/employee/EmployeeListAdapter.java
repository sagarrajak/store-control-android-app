package com.example.sagar.myapplication.adapter.employee;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.adapter.interfaces.EmployeeAdapterInterface;
import com.example.sagar.myapplication.api.EmployeeApi;
import com.example.sagar.myapplication.element.employee.employee.About_employee_activity;
import com.example.sagar.myapplication.modal.Employee;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class EmployeeListAdapter  extends RecyclerView.Adapter<EmployeeListAdapter.MyViewHolder> implements EmployeeAdapterInterface {

    private Context mContext;
    private EmployeeApi mEmployeeApi;
    private static  EmployeeListAdapter mEmployeeListAdapter;
    private List<Employee> employees;

    public EmployeeListAdapter( Context mContext ){
        this.mContext = mContext;
        mEmployeeApi = EmployeeApi.getEmployeeApi(this,mContext);
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
    public void onBindViewHolder(final EmployeeListAdapter.MyViewHolder holder, final int position){
        String url = "http://res.cloudinary.com/droxr0kdp/image/upload/w_300,h_300,c_crop/w_200/v1482011353/";
        holder.name.setText(employees.get(position).getName().getName()+" "+employees.get(position).getName().getLast());
        holder.mail.setText(employees.get(position).getMail().getValue());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent( mContext.getApplicationContext() , About_employee_activity.class );
                intent.putExtra("Employee" , employees.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

        Picasso.with(mContext)
                .load(url+employees.get(position).getImage())
                .placeholder(R.drawable.employee)
                .into(holder.mEmployeeImage);

        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view){
                CharSequence[] items = { "Edit details" , "Delete" , "Send message" , "Details" };
                new AlertDialog.Builder(mContext)
                        .setItems( items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                switch (i){
                                    case  0 :
                                        //// TODO: 18/10/17  
                                        break;
                                    case  1 :
                                        //// TODO: 18/10/17
                                        break;
                                    case  2:
                                        //// TODO: 18/10/17  
                                        break;
                                    case   3:
                                        //// TODO: 18/10/17
                                        break;
                                }
                            }
                        })
                        .show();
                return  false;
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
        public ImageView mEmployeeImage;
        public View view;
        public MyViewHolder(View itemView){
            super(itemView);
            this.view = itemView;
            name = (TextView) itemView.findViewById(R.id.employee_list_view_name);
            mail = (TextView) itemView.findViewById(R.id.employee_list_view_mail);
            mEmployeeImage = (ImageView)itemView.findViewById(R.id.employee_list_view_image_view);
        }
    }


    public static  EmployeeListAdapter getEmployeeListAdapter(Context mContext){
        if(mEmployeeListAdapter==null)
            mEmployeeListAdapter = new EmployeeListAdapter(mContext);
        else
            mEmployeeListAdapter.setmContext(mContext);
        return  mEmployeeListAdapter;
    }

}

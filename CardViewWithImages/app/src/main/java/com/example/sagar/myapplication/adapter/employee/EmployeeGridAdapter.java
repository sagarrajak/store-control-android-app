package com.example.sagar.myapplication.adapter.employee;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.adapter.interfaces.EmployeeAdapterInterface;
import com.example.sagar.myapplication.api.EmployeeApi;
import com.example.sagar.myapplication.element.employee.employee.About_employee_activity;
import com.example.sagar.myapplication.modal.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeGridAdapter extends RecyclerView.Adapter<EmployeeGridAdapter.MyViewHolder> implements EmployeeAdapterInterface {

    private Context mContext;
    private List<Employee> mlist;
    private EmployeeApi mEmployeeApi;
    private static  EmployeeGridAdapter mEmployeeGridAdapter;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
         View itemView = LayoutInflater.from(parent.getContext()).inflate( R.layout.emploee_card, parent , false);
         return new MyViewHolder(itemView);
    }

    public EmployeeGridAdapter(Context mContext){
        this.mContext = mContext;
        this.mlist = new ArrayList<>();
        this.mEmployeeApi = EmployeeApi.getEmployeeApi(this,mContext);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int i ){
        String url = "http://res.cloudinary.com/droxr0kdp/image/upload/w_300,h_300,c_crop/w_200/v1482011353/";
        holder.name.setText(mlist.get(i).getName().getName()+" "+mlist.get(i).getName().getLast());
        holder.email.setText(mlist.get(i).getMail().getValue());
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
                      Intent intent = new Intent( mContext.getApplicationContext() , About_employee_activity.class );
                      intent.putExtra("Employee" , mlist.get(i));
                      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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

    private ProgressDialog createProgressDialog(){
        ProgressDialog dialog = new ProgressDialog(mContext);
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        return  dialog;
    }

    @Override
    public void addNewEmployeeList(List<Employee> mEmployee){
        if(mEmployee!=null)
            this.mlist = mEmployee;
        notifyDataSetChanged();
    }

    private class  MyMenuOnClickListerner implements  PopupMenu.OnMenuItemClickListener{
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
                      //// TODO: 17/10/17  
                      break;
                case  R.id.send_message :
                      //// TODO: 17/10/17  
                      break;
            }
            return  false;
        }

        private void createDeleteDialog(final int position){
//            new AlertDialog.Builder(mContext)
//                    .setTitle("Delete Employee")
//                    .setMessage("Are you sure to want to delete this employee")
//                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            ProgressDialog progressDialog = createProgressDialog();
//                            Err.e(mlist.get(position).getId());
//                            progressDialog.show();
//                            mEmployeeApi.deleteEmployeeImage( mlist.get(position).getId() , progressDialog );
//                        }
//                    })
//                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//
//                        }
//                    })
//                    .show();
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
            public  TextView name,email;
            public  ImageView mImageView;
            public  ImageView employeePicture;
            public  int position;
            public MyViewHolder(View view){
                super(view);
                this.view = view;
                name  = (TextView)view.findViewById(R.id.name);
                email = (TextView)view.findViewById(R.id.mail);
                mImageView = (ImageView)view.findViewById(R.id.imageViewLog);
                employeePicture = (ImageView)view.findViewById(R.id.add_customer_picture);
            }
     }

     public  static EmployeeGridAdapter getEmployeeGridAdapter(Context mContext){
          if(mEmployeeGridAdapter == null)
              mEmployeeGridAdapter = new EmployeeGridAdapter(mContext);
          else
              mEmployeeGridAdapter.setmContext(mContext);
          return  mEmployeeGridAdapter;
     }

  }

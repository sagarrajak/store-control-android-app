package com.example.sagar.myapplication.adapter;
import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sagar.myapplication.storingClass.Employee;
import com.example.sagar.myapplication.R;

import java.util.ArrayList;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.MyViewHolder>{

    private Context mContext;
    private ArrayList<Employee> mlist;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

         View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.emploee_card, parent , false);
         return new MyViewHolder(itemView);

    }
    public EmployeeAdapter(Context mContext , ArrayList<Employee> mlist){

        this.mContext = mContext;
        this.mlist = mlist;

    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int i ){

        holder.name.setText(mlist.get(i).getName());
        holder.email.setText(mlist.get(i).getMail());
        holder.mImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
               showPopUpMenu(holder.mImageView);
            }
        });
    }
    private void  showPopUpMenu(View view){
        PopupMenu popupMenu = new PopupMenu(mContext,view);
        MenuInflater mMenuInflater = popupMenu.getMenuInflater();
        mMenuInflater.inflate(R.menu.menu , popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new MyMenuOnClickListerner());
        popupMenu.show();
    }
    class  MyMenuOnClickListerner implements  PopupMenu.OnMenuItemClickListener{
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case  R.id.delete :
                      break;
                case  R.id.edit_employee :
                      break;
                case  R.id.send_message :
                      break;
            }
            return  false;
        }
    }
    @Override
    public int getItemCount(){
        return mlist.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
            public TextView name,email;
            public ImageView mImageView;
            public MyViewHolder(View view){

                super(view);
                name  = (TextView) view.findViewById(R.id.name);
                email = (TextView) view.findViewById(R.id.mail);
                mImageView = (ImageView)view.findViewById(R.id.imageViewLog);

            }
     }

  }

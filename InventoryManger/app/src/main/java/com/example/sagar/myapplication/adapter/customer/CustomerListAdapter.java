package com.example.sagar.myapplication.adapter.customer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.adapter.interfaces.CustomerAdapterInterface;
import com.example.sagar.myapplication.api.CustomerApi;
import com.example.sagar.myapplication.element.customer.About_customer_activity;
import com.example.sagar.myapplication.modal.Customer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.MyViewHolder> implements CustomerAdapterInterface {

    private List<Customer> mCustomerList;
    private Context mContext;
    private CustomerApi mCustomerApi;
    private static CustomerListAdapter mCustomerListAdapter;

    public CustomerListAdapter(Context mContext) {
        this.mContext = mContext;
        mCustomerList = new ArrayList<>();
        mCustomerApi = CustomerApi.getCustomerApi(mContext, this);
    }

    @Override
    public void addCustomerList(List<Customer> mCustomer) {
        this.mCustomerList = mCustomer;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_linear_layout_card, parent, false);
        return new MyViewHolder(view);
    }

    private String getName(int position) {
        return mCustomerList.get(position).getName().getName() + " " + mCustomerList.get(position).getName().getLast();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        String url = "http://res.cloudinary.com/droxr0kdp/image/upload/w_300,h_300,c_crop/w_200/v1482011353/";
        holder.customer_name.setText(getName(position));
        holder.customer_email.setText(mCustomerList.get(position).getMail().getValue());
        Picasso.with(mContext)
                .load(url + mCustomerList.get(position).getImage())
                .placeholder(R.drawable.employee)
                .into(holder.customer_image);
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final CharSequence[] items = {"Details", "Delete", "Edit"};
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                //// TODO: 18/10/17  
                                break;
                            case 1:
                                //// TODO: 18/10/17  
                                break;
                            case 2:
                                //// TODO: 18/10/17
                                break;
                        }
                    }
                });
                builder.show();
                return false;
            }
        });
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, About_customer_activity.class);
                intent.putExtra("Customer", mCustomerList.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCustomerList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView customer_image;
        public TextView customer_name, customer_email;
        public View view;

        public MyViewHolder(View view) {
            super(view);
            this.view = view;
            customer_image = (ImageView) view.findViewById(R.id.customer_list_view_image_view);
            customer_name = (TextView) view.findViewById(R.id.customer_list_card_name);
            customer_email = (TextView) view.findViewById(R.id.customer_list_view_mail);
        }
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public static CustomerListAdapter getCustomerListAdapter(Context mContext) {
        if (mCustomerListAdapter == null)
            mCustomerListAdapter = new CustomerListAdapter(mContext);
        else
            mCustomerListAdapter.setContext(mContext);
        return mCustomerListAdapter;
    }
}

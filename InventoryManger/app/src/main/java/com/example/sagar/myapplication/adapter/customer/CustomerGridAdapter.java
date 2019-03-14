package com.example.sagar.myapplication.adapter.customer;

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

import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.adapter.interfaces.CustomerAdapterInterface;
import com.example.sagar.myapplication.api.CustomerApi;
import com.example.sagar.myapplication.element.customer.About_customer_activity;
import com.example.sagar.myapplication.modal.Customer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomerGridAdapter extends RecyclerView.Adapter<CustomerGridAdapter.MyViewHolder> implements CustomerAdapterInterface {

    private List<Customer> mCustomerList;
    private Context mContext;
    private CustomerApi mCustomerApi;
    private static CustomerGridAdapter mCustomerGridAdapter;

    public CustomerGridAdapter(Context mContext) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.emploee_card, null);
        return new MyViewHolder(view);
    }

    private String getCustomerName(int i) {
        return mCustomerList.get(i).getName().getName() + " " + mCustomerList.get(i).getName().getLast();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        String url = "http://res.cloudinary.com/droxr0kdp/image/upload/w_300,h_300,c_crop/w_200/v1482011353/";
        holder.menuImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUpMenuDialog(holder.menuImage, position);
            }
        });
        holder.mCustomerName.setText(getCustomerName(position));
        holder.mCustomerMail.setText(mCustomerList.get(position).getMail().getValue());
        Picasso.with(mContext)
                .load(url + mCustomerList.get(position).getImage())
                .placeholder(R.drawable.employee)
                .into(holder.mCustomerImage);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext.getApplicationContext(), About_customer_activity.class);
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
        public View mView;
        public ImageView mCustomerImage, menuImage;
        public TextView mCustomerName, mCustomerMail;

        public MyViewHolder(View view) {
            super(view);
            mView = view;
            mCustomerImage = (ImageView) view.findViewById(R.id.add_customer_picture);
            menuImage = (ImageView) view.findViewById(R.id.imageViewLog);
            mCustomerName = (TextView) view.findViewById(R.id.name);
            mCustomerMail = (TextView) view.findViewById(R.id.mail);
        }
    }

    public void setCustomerContext(Context mContext) {
        mCustomerApi.setContext(mContext);
    }

    public static CustomerGridAdapter getCustomerGridAdapter(Context mContext) {
        if (mCustomerGridAdapter == null)
            mCustomerGridAdapter = new CustomerGridAdapter(mContext);
        else
            mCustomerGridAdapter.setCustomerContext(mContext);
        return mCustomerGridAdapter;
    }

    private void showPopUpMenuDialog(View view, int i) {
        PopupMenu mPopUpMenu = new PopupMenu(mContext, view);
        MenuInflater mMenuInflater = mPopUpMenu.getMenuInflater();
        mMenuInflater.inflate(R.menu.customer_card_menu, mPopUpMenu.getMenu());
        mPopUpMenu.setOnMenuItemClickListener(new MyMenuOnClickListerner(i));
        mPopUpMenu.show();
    }

    private class MyMenuOnClickListerner implements PopupMenu.OnMenuItemClickListener {
        private int position;

        public MyMenuOnClickListerner(int position) {
            this.position = position;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.delete_customer:
                    //// TODO: 18/10/17
                    break;
                case R.id.edit_customer:
                    //// TODO: 17/10/17
                    break;
            }
            return false;
        }

    }
}

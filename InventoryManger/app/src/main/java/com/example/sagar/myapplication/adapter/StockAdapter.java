package com.example.sagar.myapplication.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.modal.stock.Stock;
import com.example.sagar.myapplication.modal.stock.StockPopulated;
import com.example.sagar.myapplication.utill.Err;


import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.MyViewHolder> {

    private List<StockPopulated> mList;
    private Context mContext;
    private static StockAdapter mStockAdapter;
    private Boolean isNotificationIsSet;

    private StockAdapter(Context mContext) {
        this.mContext = mContext;
        this.isNotificationIsSet = false;
        mList = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_card, parent, false);
        return new MyViewHolder(view);
    }


    private String dateFormat(Date date) {
        Format format = new SimpleDateFormat("dd MMM yyyy");
        return format.format(date);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        holder.name.setText(mList.get(position).getName());

        holder.stock_product_name.setText(mList.get(position).getName());

        holder.details_textView.setText(mList.get(position).getDetails());

        holder.item_count_label.setText(mList.get(position).getQuantity().toString());

        if (mList.get(position).getQuantity() <= 0)
            holder.stock_card.setCardBackgroundColor(mContext.getResources().getColor(R.color.deepred));

        if (mList.get(position).getBuyedDate() != null) {
            holder.buyed_date.setText(dateFormat(mList.get(position).getBuyedDate()));
        }
        if (mList.get(position).getExpire().getIsExpireable()) {
            holder.expire_date.setText(dateFormat(mList.get(position).getExpire().getExpireDate()));

        } else holder.expire_date.setText("Item don't expire");

        holder.edit_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // // TODO: 5/10/17 set on click listener
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //// TODO: 5/9/17  delete that stock form database
            }
        });

        holder.turn_on_notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // // TODO: 5/10/17 set notification dot
                isNotificationIsSet = !isNotificationIsSet;
                if (isNotificationIsSet)
                    holder.turn_on_notif.setImageResource(R.drawable.ic_notifications_active_black_24dp);
                else
                    holder.turn_on_notif.setImageResource(R.drawable.ic_notifications_black_24dp);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView delete, turn_on_notif, stockProductImageView, edit_icon;
        public TextView name, buyed_date, expire_date, details_textView, stock_product_name, item_count_label;
        public CardView stock_card;

        public MyViewHolder(View itemView) {
            super(itemView);
            stockProductImageView = (ImageView) itemView.findViewById(R.id.stock_prduct_image_view);
            delete = (ImageView) itemView.findViewById(R.id.delete_icon);
            turn_on_notif = (ImageView) itemView.findViewById(R.id.notification_icon);
            edit_icon = (ImageView) itemView.findViewById(R.id.edit_icon);
            name = (TextView) itemView.findViewById(R.id.stock_name);
            buyed_date = (TextView) itemView.findViewById(R.id.stock_brought_date);
            expire_date = (TextView) itemView.findViewById(R.id.stock_expire_date);
            details_textView = (TextView) itemView.findViewById(R.id.stock_details);
            item_count_label = (TextView) itemView.findViewById(R.id.stock_items_count);
            stock_product_name = (TextView) itemView.findViewById(R.id.stock_product_name);
            stock_card = (CardView) itemView.findViewById(R.id.card_view_stock);
        }
    }

    private void setImageViewProduct() {

        ////todo
    }

    public void setStockList(List<StockPopulated> mStockList) {
        this.mList = mStockList;
        notifyDataSetChanged();
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public static StockAdapter getStockAdapter(Context mContext) {
        if (mStockAdapter == null)
            mStockAdapter = new StockAdapter(mContext);
        else
            mStockAdapter.setContext(mContext);
        return mStockAdapter;
    }

}

package com.example.sagar.myapplication.element.stock.dialog;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.api.ApiClient;
import com.example.sagar.myapplication.api.interfaces.ApiProductInterface;
import com.example.sagar.myapplication.api.interfaces.ApiStockInterface;
import com.example.sagar.myapplication.modal.Product;
import com.example.sagar.myapplication.utill.Err;
import com.example.sagar.myapplication.utill.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlertDialogBuilderAdapterFilterStock extends RecyclerView.Adapter<AlertDialogBuilderAdapterFilterStock.MyViewHolder> {

    private List<Product> availAbleProductInStock;
    private Context mContext;
    private ApiProductInterface mApiProductInterface;
    private Map<Product, Integer> mProductMap;


    public AlertDialogBuilderAdapterFilterStock(Context mContext) {
        availAbleProductInStock = new ArrayList<>();
        this.mContext = mContext;
        mApiProductInterface = ApiClient.getClient().create(ApiProductInterface.class);
        mProductMap = new HashMap<>();
        fetchData();
    }

    public void fetchData() {
        mApiProductInterface.getProductList(Token.token)
                .enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        if (response.code() == 200) {
                            updateData(response.body());
                        } else {
                            Err.s(mContext, "Error in fetching product list");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        t.printStackTrace();
                        Err.s(mContext, "Error in fetching product list");
                    }
                });
    }

    private void updateData(List<Product> list) {
        availAbleProductInStock = list;
        notifyDataSetChanged();
    }

    @Override
    public AlertDialogBuilderAdapterFilterStock.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alert_dialog_linear_layout_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlertDialogBuilderAdapterFilterStock.MyViewHolder holder, final int position) {
        if (mProductMap.containsKey(availAbleProductInStock.get(position)))
            holder.checkBox.setChecked(true);
        holder.textView.setText(availAbleProductInStock.get(position).getName());
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mProductMap.containsKey(availAbleProductInStock.get(position)))
                    mProductMap.remove(availAbleProductInStock.get(position));
                else
                    mProductMap.put(availAbleProductInStock.get(position), 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return availAbleProductInStock.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkBox;
        public TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            checkBox = (CheckBox) itemView.findViewById(R.id.alert_dialog_linear_layout_checkbox);
            textView = (TextView) itemView.findViewById(R.id.alert_dialog_linear_layout_text_view);
        }
    }

    public Set<Product> getSelectedList() {
        return mProductMap.keySet();
    }

    public void resetSelectedList() {
        mProductMap.clear();
    }

}
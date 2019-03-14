package com.example.sagar.myapplication.adapter.product;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.sagar.myapplication.api.ApiClient;
import com.example.sagar.myapplication.utill.Err;
import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.utill.Token;
import com.example.sagar.myapplication.api.interfaces.ApiProductTypeInterface;
import com.example.sagar.myapplication.modal.ProductType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductCategoryPickerAdapter extends RecyclerView.Adapter<ProductCategoryPickerAdapter.MyViewHolder> {

    private Context context;
    private ApiProductTypeInterface mApiProductType;
    private List<ProductType> brands;
    private HashMap<ProductType, Integer> store_chekbox;

    public ProductCategoryPickerAdapter(Context context) {
        this.context = context;
        mApiProductType = ApiClient.getClient().create(ApiProductTypeInterface.class);
        brands = new ArrayList<>();
        store_chekbox = new HashMap<>();
        listProduct();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_fragment_brand_picker_layout_row, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.textView.setText(brands.get(position).getProductType());

        if (store_chekbox.containsKey(brands.get(position)))
            holder.checkBox.setChecked(true);

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (store_chekbox.containsKey(brands.get(position)))
                    store_chekbox.remove(brands.get(position));
                else
                    store_chekbox.put(brands.get(position), 1);
            }
        });
    }


    @Override
    public int getItemCount() {
        return brands.size();
    }

    private ProgressDialog creteDialog() {
        ProgressDialog mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCanceledOnTouchOutside(true);
        mProgressDialog.setCancelable(true);
        return mProgressDialog;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public CheckBox checkBox;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.dialog_fragment_brand_picker_textView);
            checkBox = (CheckBox) itemView.findViewById(R.id.dialog_fragment_brand_picker_checkbox);
        }
    }


    private void listProduct() {
        mApiProductType.getProductTypeList(Token.token)
                .enqueue(new Callback<List<ProductType>>() {
                    @Override
                    public void onResponse(Call<List<ProductType>> call, Response<List<ProductType>> response) {
                        if (response.code() == 200) {
                            setList(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ProductType>> call, Throwable t) {
                        t.printStackTrace();
                        Err.s(context, t.toString());

                    }
                });
    }


    private void setList(List<ProductType> brands) {
        this.brands = brands;
        notifyDataSetChanged();
    }


    public HashMap<ProductType, Integer> getHasMap() {
        return store_chekbox;
    }


}

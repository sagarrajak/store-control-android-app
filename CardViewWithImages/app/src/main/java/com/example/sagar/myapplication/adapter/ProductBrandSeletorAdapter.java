package com.example.sagar.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.api.ApiBrandInterface;
import com.example.sagar.myapplication.api.ApiClient;
import com.example.sagar.myapplication.modal.Brand;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SAGAR on 1/19/2017.
 */
public class ProductBrandSeletorAdapter extends RecyclerView.Adapter<ProductBrandSeletorAdapter.MyViewHolder>{

    private Context context;
    private List<Brand> brand;
    private ApiBrandInterface mApiBrandInterface;
    private  boolean checked;
    private RadioButton radiobutton;
    private Brand ans_brand;

    public  ProductBrandSeletorAdapter(Context context){
        brand = new ArrayList<>();
        mApiBrandInterface = ApiClient.getClient().create(ApiBrandInterface.class);
        this.context = context;
        createRecylceView();
        checked = false;
    }
    private void createRecylceView(){
        mApiBrandInterface.listBrand().enqueue(new Callback<List<Brand>>() {
            @Override
            public void onResponse(Call<List<Brand>> call, Response<List<Brand>> response) {
                    if(response.code()==200){
                       brand = response.body();
                    }
            }
            @Override
            public void onFailure(Call<List<Brand>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_brand_seletor_dialog_layout,parent,false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position){
        holder.textView.setText(brand.get(position).getBrand());
        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                if(!checked){
                    checked=true;
                    radiobutton = holder.radioButton;
                }
                else{
                    radiobutton.setChecked(false);
                    radiobutton = holder.radioButton;
                }
                ans_brand = brand.get(position);
            }
        });

    }
    @Override
    public int getItemCount() {
        return brand.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public  TextView textView ;
        public  RadioButton radioButton;
        public MyViewHolder(View itemView){
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.product_brand_dialog_textview);
            radioButton = (RadioButton) itemView.findViewById(R.id.product_brand_dialog_radioButton);
        }
    }
    public  Brand getProductBrand(){
        return  ans_brand;
    }
}

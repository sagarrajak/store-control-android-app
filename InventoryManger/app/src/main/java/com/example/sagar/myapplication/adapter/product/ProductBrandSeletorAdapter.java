package com.example.sagar.myapplication.adapter.product;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.api.ApiClient;
import com.example.sagar.myapplication.api.interfaces.ApiBrandInterface;
import com.example.sagar.myapplication.modal.Brand;
import com.example.sagar.myapplication.utill.Err;
import com.example.sagar.myapplication.utill.Token;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductBrandSeletorAdapter extends RecyclerView.Adapter<ProductBrandSeletorAdapter.MyViewHolder>{

    private Context context;
    private List<Brand> brand;
    private ApiBrandInterface mApiBrandInterface;
    private RadioButton mSelectedRadioButton=null;
    private Brand selectedBrand = null;
    private ProgressBar mProgressBar;

    public  ProductBrandSeletorAdapter( Context context , ProgressBar mProgressBar ){
        brand = new ArrayList<>();
        mApiBrandInterface = ApiClient.getClient().create(ApiBrandInterface.class);
        this.context = context;
        this.mProgressBar = mProgressBar;
        createRecylceView();
    }

    private void createRecylceView(){
        mApiBrandInterface.listBrand(
                Token.token
        ).enqueue(new Callback<List<Brand>>() {
            @Override
            public void onResponse(Call<List<Brand>> call, Response<List<Brand>> response) {
                    if(response.code()==200){
                        setData(response.body());
                    }
                    else{
                        Err.s(context,"Error in fetching data");
                        mProgressBar.setVisibility(View.GONE);
                    }
            }
            @Override
            public void onFailure(Call<List<Brand>> call, Throwable t){
                t.printStackTrace();
                mProgressBar.setVisibility(View.GONE);
                Err.s(context , t.getMessage() );
            }
        });
    }

    private void setData(List<Brand> mList){
          this.brand  = mList;
          notifyDataSetChanged();
          mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_single_selection_dialog_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position){
        holder.mTextView.setText(brand.get(position).getBrand());
        holder.mRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mSelectedRadioButton!=null){
                    mSelectedRadioButton.setChecked(false);
                }
                mSelectedRadioButton =  holder.mRadioButton;
                selectedBrand        =  brand.get(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return brand.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public RadioButton  mRadioButton;
        public TextView mTextView;
        public MyViewHolder(View itemView){
            super(itemView);
            mRadioButton = (RadioButton) itemView.findViewById(R.id.list_radio_button);
            mTextView    = (TextView) itemView.findViewById(R.id.list_text_view);
        }
    }

    @Nullable
    public  Brand getProductBrand(){
            return  selectedBrand;
    }
}

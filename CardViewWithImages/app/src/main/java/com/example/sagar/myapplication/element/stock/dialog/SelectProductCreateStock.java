package com.example.sagar.myapplication.element.stock.dialog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.sagar.myapplication.R;

import com.example.sagar.myapplication.api.ApiClient;
import com.example.sagar.myapplication.api.interfaces.ApiProductInterface;
import com.example.sagar.myapplication.modal.Product;
import com.example.sagar.myapplication.utill.Err;
import com.example.sagar.myapplication.utill.Token;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SelectProductCreateStock extends RecyclerView.Adapter<SelectProductCreateStock.MyViewHolder>  {

    private List<Product> mProductList;
    private Context mContext;
    private Product selectedProduct =null;
    private RadioButton mSelectedRadioButton=null;
    private ApiProductInterface mApiProductInterface;
    private ProgressBar mProgressbar;

    public SelectProductCreateStock(Context mContext , ProgressBar mProgressbar , Product selectedProduct ){
           mProductList  = new ArrayList<>();
           this.mContext = mContext;
           this.mProgressbar = mProgressbar;
           this.mApiProductInterface = ApiClient.getClient().create(ApiProductInterface.class);
           this.selectedProduct      = selectedProduct;
           getData();
    }

    @Override
    public SelectProductCreateStock.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
          View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_single_selection_dialog_layout , parent , false );
          return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SelectProductCreateStock.MyViewHolder holder, final int position ) {

          holder.mTextView.setText(mProductList.get(position).getName());

          if( selectedProduct !=null &&  Objects.equals( mProductList.get(position).getId(), selectedProduct.getId() ) ) {
              //if user previously selected  product
              holder.mRadioButton.setChecked(true);
              mSelectedRadioButton = holder.mRadioButton;
          }

          holder.mRadioButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  if( mSelectedRadioButton!=null && mSelectedRadioButton!=holder.mRadioButton ){
                      // when user is jumping back and forward between radio button
                      mSelectedRadioButton.setChecked(false);
                      mSelectedRadioButton  =  holder.mRadioButton;
                      selectedProduct       =  mProductList.get(position);
                  }
                  else if( mSelectedRadioButton!=null ){
                      //case when user click double at the same radio button
                      mSelectedRadioButton.setChecked(false);
                      selectedProduct=null;
                  }
                  else{
                     //when user is clicking first time in radio button
                     mSelectedRadioButton = holder.mRadioButton;
                     selectedProduct      = mProductList.get(position);
                  }
              }
          });
    }

    @Override
    public int getItemCount() {
         return mProductList.size();
    }

    public  Product getSelectedProduct(){
        return  selectedProduct;
    }
    private void getData() {
        mApiProductInterface.getProductList(Token.token)
                .enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        if(response.code()==200){
                            setList(response.body());
                        }else{
                            Err.s(mContext,"Error in fetching  product list");
                            mProgressbar.setVisibility(View.GONE);
                        }
                    }
                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        t.printStackTrace();
                        mProgressbar.setVisibility(View.GONE);
                        Err.s(mContext , t.getMessage() );
                    }
                });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public RadioButton mRadioButton;
        public TextView mTextView;
        public MyViewHolder(View itemView){
            super(itemView);
            mRadioButton = (RadioButton) itemView.findViewById(R.id.list_radio_button);
            mTextView    = (TextView) itemView.findViewById(R.id.list_text_view);
        }
    }

    private void setList(List<Product> mList){
        this.mProductList =  mList;
        notifyDataSetChanged();
        mProgressbar.setVisibility(View.GONE);
    }

}

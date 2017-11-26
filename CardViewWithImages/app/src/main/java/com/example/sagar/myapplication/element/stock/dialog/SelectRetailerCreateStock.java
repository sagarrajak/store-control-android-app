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
import com.example.sagar.myapplication.api.interfaces.ApiRetailerInterface;
import com.example.sagar.myapplication.modal.Retailer;
import com.example.sagar.myapplication.utill.Err;
import com.example.sagar.myapplication.utill.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectRetailerCreateStock extends RecyclerView.Adapter<SelectRetailerCreateStock.MyViewHolder>  {

    private Context mContext ;
    private List<Retailer> mRetailer;
    private RadioButton mSelectedRadioButton;
    private ApiRetailerInterface mApiRetailerInterface;
    private Retailer mSelectedRetailer;
    private ProgressBar mProgessBar;

    public SelectRetailerCreateStock(Context mContext , ProgressBar mProgressBar , Retailer  mSelectedRetailer ){
        mRetailer              =  new ArrayList<>();
        this.mSelectedRetailer =  mSelectedRetailer;
        mainConstructor(mContext,mProgressBar);
    }

    private void mainConstructor(Context mContext , ProgressBar mProgressBar){
        this.mContext =  mContext;
        mApiRetailerInterface  = ApiClient.getClient().create(ApiRetailerInterface.class);
        this.mProgessBar = mProgressBar;
        getData();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_single_selection_dialog_layout , parent , false );
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position){

        holder.mTextView.setText(mRetailer.get(position).getName().getName() + " "+mRetailer.get(position).getName().getLast());

        if(mSelectedRetailer!=null && Objects.equals(mSelectedRetailer.getId(), mRetailer.get(position).getId())) {
            // when previously button was selected
            holder.mRadioButton.setChecked(true);
            mSelectedRadioButton =  holder.mRadioButton;
        }

        holder.mRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 if( mSelectedRadioButton!=null && mSelectedRadioButton != holder.mRadioButton  ){
                     //when user jump between two radio button
                     mSelectedRadioButton.setChecked(false);
                     mSelectedRadioButton =  holder.mRadioButton;
                     mSelectedRetailer    =  mRetailer.get(position);
                 }
                 else if( mSelectedRetailer != null  && mSelectedRadioButton!=null ){
                     // when user is clicking same radio button
                     mSelectedRetailer=null;
                     mSelectedRadioButton.setChecked(false);
                     mSelectedRadioButton = null;
                 }
                 else{
                     // when user is clicking new radio button , no button was selected before
                     mSelectedRetailer =  mRetailer.get(position);
                     mSelectedRadioButton =  holder.mRadioButton;
                 }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRetailer.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public RadioButton mRadioButton;
        public TextView mTextView;
        public MyViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.list_text_view);
            mRadioButton = (RadioButton) itemView.findViewById(R.id.list_radio_button);
        }
    }

    public Retailer getSelectedRetailer(){
         return  mSelectedRetailer;
    }

    private void getData(){
        mApiRetailerInterface
                .getRetailer(Token.token)
                    .enqueue(new Callback<List<Retailer>>() {
                        @Override
                        public void onResponse(Call<List<Retailer>> call, Response<List<Retailer>> response) {
                            if(response.code() == 200 ){
                                 setList(response.body());
                            }
                            else{
                                Err.s( mContext , "Error in fetching data"  );
                            }
                            mProgessBar.setVisibility(View.GONE);
                        }
                        @Override
                        public void onFailure(Call<List<Retailer>> call, Throwable t) {
                            t.printStackTrace();
                            Err.s(mContext , t.getMessage() );
                            mProgessBar.setVisibility(View.GONE);
                        }
                    });
    }

    private void setList(List<Retailer> body){
        this.mRetailer = body;
        notifyDataSetChanged();
        mProgessBar.setVisibility(View.GONE);
    }
}

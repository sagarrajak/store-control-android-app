package com.example.sagar.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.api.ApiClient;
import com.example.sagar.myapplication.api.interfaces.ApiRetailerInterface;
import com.example.sagar.myapplication.modal.Retailer;
import com.example.sagar.myapplication.utill.Err;
import com.example.sagar.myapplication.utill.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetailerPickerAdapter extends RecyclerView.Adapter<RetailerPickerAdapter.MyViewHolder> {

    private List<Retailer> mRetailer;
    private HashMap<Retailer,Integer> mMap;
    private ApiRetailerInterface mApiRetailerInterface;
    private Context mContext;

    public RetailerPickerAdapter( Context mContext ){
        mRetailer = new ArrayList<>();
        mMap = new HashMap<>();
        this.mContext = mContext;
        mApiRetailerInterface = ApiClient.getClient().create(ApiRetailerInterface.class);
        listRetailer();
    }

    private void listRetailer(){
        mApiRetailerInterface.getRetailer(Token.token).enqueue(new Callback<List<Retailer>>() {
            @Override
            public void onResponse( Call<List<Retailer>> call, Response<List<Retailer>> response) {
                 if(response.code()==200) setList(response.body());
                 else Err.s( mContext , "Error in fetching data" );
            }
            @Override
            public void onFailure(Call<List<Retailer>> call, Throwable t) {
                    t.printStackTrace();
                    Err.s( mContext , "Error in fetching data" );
            }
        });
    }

    private void setList( List<Retailer> mReatiler ){
        this.mRetailer = mReatiler;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_fragment_brand_picker_layout_row , parent , false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder , final int position ) {

        holder.mTextView.setText(mRetailer.get(position).getName());
        holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMap.containsKey(mRetailer.get(position))){
                    mMap.remove(mRetailer.get(position));
                }
                else{
                    mMap.put(mRetailer.get(position),1);
                }
            }
        });

    }

    public List<Retailer> getSelectedList(){
        List<Retailer> temList = new ArrayList<>();
        for( Retailer obj : mMap.keySet()  ){
            temList.add(obj);
        }
        return  temList;
    }

    public HashMap< Retailer , Integer > getMap(){
        return  mMap;
    }

    @Override
    public int getItemCount() {
        return mRetailer.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public CheckBox mCheckBox ;
        public TextView mTextView;
        public MyViewHolder(View itemView){
            super(itemView);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.dialog_fragment_brand_picker_checkbox);
            mTextView = (TextView) itemView.findViewById(R.id.dialog_fragment_brand_picker_textView);
        }

    }

}

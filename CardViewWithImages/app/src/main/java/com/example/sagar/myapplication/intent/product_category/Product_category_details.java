package com.example.sagar.myapplication.intent.product_category;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.api.interfaces.ApiProductInterface;

public class Product_category_details extends AppCompatActivity {

    private  RecyclerView mRecyclerView;
    private  Toolbar toolbar;
    private TextView  mCategoryDetails;
    private ApiProductInterface mApiProductInterface;
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category_details);
        setToolbar();
        setUiElement();
        setRecycleView();
    }

    private void setUiElement(){
            mCategoryDetails  = (TextView) findViewById(R.id.TextViewDetails);

    }

    private void setToolbar(){
        toolbar  = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setRecycleView(){
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_product_category_details);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL , false ));
        mRecyclerView.setAdapter(new MyAdapter());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate( R.menu.menu_about_category , menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.about_category_edit :

                break;
            case R.id.about_category_delete :

                break;
            case R.id.about_category_add_product :

                break;
        }
        return true;
    }




    public class  MyAdapter  extends  RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public MyViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

}

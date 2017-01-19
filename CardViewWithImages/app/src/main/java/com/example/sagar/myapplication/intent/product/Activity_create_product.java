package com.example.sagar.myapplication.intent.product;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.sagar.myapplication.Err;
import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.adapter.ProductBrandSeletorAdapter;
import com.example.sagar.myapplication.adapter.ProductCategoryPickerAdapter;
import com.example.sagar.myapplication.modal.Brand;
import com.example.sagar.myapplication.modal.ProductType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Activity_create_product extends AppCompatActivity{

    private Toolbar toolbar;

    private  EditText name,brand,details,type,price,retialer;
    private ArrayAdapter<Brand> arrayAdapter;
    private List<String>  product_type;
    private BottomSheetDialog mBottomSheetDialog;
    private FloatingActionButton mFlaotingactionButton;
    private ImageView mImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_create_product);

        setTitle("Create new product");

        toolbar = (Toolbar) findViewById(R.id.activity_create_product_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        name = (EditText) findViewById(R.id.create_product_name);
        brand = (EditText) findViewById(R.id.create_product_brand);
        details = (EditText) findViewById(R.id.create_product_details);
        type  = (EditText) findViewById(R.id.create_product_type);
        price = (EditText) findViewById(R.id.create_product_price);
        retialer = (EditText) findViewById(R.id.create_product_retailer);
        mFlaotingactionButton = (FloatingActionButton) findViewById(R.id.create_product_floarinf_action_buttom);
        mImageView = (ImageView) findViewById(R.id.create_product_image_view);

        mFlaotingactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createBottomSheet();
            }
        });

        product_type = new ArrayList<>();

                type.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        LayoutInflater inflater = getLayoutInflater();
                        View dialog_view = inflater.inflate(R.layout.fragment_product_category_chooser_dialog_fragment,null);
                        RecyclerView recyclerView = (RecyclerView) dialog_view.findViewById(R.id.recycle_view);
                        final ProductCategoryPickerAdapter mProductCategoryPickerAdapter = new ProductCategoryPickerAdapter(getBaseContext());
                        recyclerView.setAdapter(mProductCategoryPickerAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_create_product.this);
                        builder.setTitle("Select productCategory");
                        builder.setView(dialog_view);
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                        builder.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i){
                                HashMap<ProductType,Integer> ma = mProductCategoryPickerAdapter.getHasMap();
                                StringBuilder stringBuilder = new StringBuilder();
                                if (ma!=null){
                                    for( ProductType obj : ma.keySet() ){
                                        stringBuilder.append(obj.getProductType()+" , ");
                                        product_type.add(obj.getId());
                                    }
                                }
                                type.setText(stringBuilder.toString());
                            }
                        });
                        builder.show();
                    }
                });


                brand.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view){

                        LayoutInflater inflater = getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.recycleview,null);
                        final ProductBrandSeletorAdapter mProductBrandSeletorAdapter = new ProductBrandSeletorAdapter(getBaseContext());
                        RecyclerView recyclerView = (RecyclerView) dialogView.findViewById(R.id.recycle_view);
                        recyclerView.setAdapter(mProductBrandSeletorAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));

                        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_create_product.this);
                        builder.setTitle("Select brand");
                        builder.setView(dialogView);
                        builder.setCancelable(false);
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                  Brand ans_brand =  mProductBrandSeletorAdapter.getProductBrand();
                                  if(ans_brand != null){
                                     brand.setText(ans_brand.getBrand());
                                  }
                            }
                        });
                        builder.show();
                    }
                });
                details.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(Activity_create_product.this);
                            LayoutInflater inflater = getLayoutInflater();
                            View inflate = inflater.inflate(R.layout.details_dialog,null);
                            final EditText editText = (EditText) inflate.findViewById(R.id.textView);
                            editText.setText(details.getText());
                            builder.setView(inflate);
                            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                  details.setText(editText.getText());
                                }
                            });
                            builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                          builder.show();
                    }
                });


            retialer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    //todo add reatiler dialog
                }
            });

        }

    private void createBottomSheet(){
            mBottomSheetDialog  = new BottomSheetDialog(this);
            mBottomSheetDialog.setTitle("Profile picture");
            View view = getLayoutInflater().inflate(R.layout.bottom_sheet_image_select_dilaog,null);
            mBottomSheetDialog.setContentView(view);
            view.findViewById(R.id.bottom_sheet_gallery).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mBottomSheetDialog.dismiss();
                    Intent intent = new Intent(Intent.ACTION_PICK , MediaStore.Images.Media.INTERNAL_CONTENT_URI  );
                    startActivityForResult(intent,12345);
                }
            });
            view.findViewById(R.id.bottom_sheet_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    // for deleting exsiting image
                }
            });
            view.findViewById(R.id.bottom_sheet_camera).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    mBottomSheetDialog.dismiss();
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,1234);
                }
            });
            mBottomSheetDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        switch (requestCode){
            case 12345 :
                Err.s(getBaseContext(),"working");
                checkPermission();
                Uri imageUri = data.getData();
                File file  = new File(getPath(imageUri));
                final InputStream imageStream;
                try{
                      imageStream = getContentResolver().openInputStream(imageUri);
                      final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                      mImageView.setImageBitmap(selectedImage);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();

                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private  void checkPermission(){
        if( ContextCompat.checkSelfPermission(
                this ,
                android.Manifest.permission.READ_EXTERNAL_STORAGE  ) != PackageManager.PERMISSION_GRANTED ){

            ActivityCompat.requestPermissions( Activity_create_product.this  ,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE },
                    1 );
        }
    }
    public String getPath(Uri uri){
        String res = null;
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if(cursor.moveToFirst()){;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
}

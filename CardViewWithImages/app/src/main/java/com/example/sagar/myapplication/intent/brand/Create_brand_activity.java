package com.example.sagar.myapplication.intent.brand;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.sagar.myapplication.CustumProgressDialog;
import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.adapter.BrandAdapter;
import com.example.sagar.myapplication.api.BrandApi;
import com.example.sagar.myapplication.intent.retailer.Create_retailer_activity;
import com.example.sagar.myapplication.modal.Brand;
import com.example.sagar.myapplication.utill.Err;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.example.sagar.myapplication.R.id.activity_create_brand_toolbar;
import static com.example.sagar.myapplication.R.id.cancel_employee;

public class Create_brand_activity extends AppCompatActivity {

    private BrandApi mBrandApi;
    private BrandAdapter mBrandAdapter;
    private FloatingActionButton mFloatingActionButton;
    private EditText name,details;
    private Toolbar toolbar;
    private ImageView imageView;
    private BottomSheetDialog mBottomSheetDialog;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_brand);
        setUiElement();
        setToolbar();
        setOnClickListener();
    }

    private void setUiElement(){
        name                   = (EditText) findViewById(R.id.create_brand_name);
        details                = (EditText) findViewById(R.id.create_brand_details);
        mFloatingActionButton  = (FloatingActionButton) findViewById(R.id.create_brand_button_float);
        toolbar                = (Toolbar) findViewById(activity_create_brand_toolbar);
        mBrandAdapter          = BrandAdapter.getmBrandAdapter(this);
        mBrandApi              = BrandApi.getBrandApi(mBrandAdapter);
        imageView              = (ImageView) findViewById(R.id.create_brand_image_view);
    }

    private void setToolbar(){
        setTitle("Add brand");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setOnClickListener(){
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createBottomSheet();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menu_create_employee_activity , menu );
        return true;
    }


    private boolean checkError(){
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinate_layout_create_brand);
        if( name.getText().toString().isEmpty() ){
            Snackbar.make( coordinatorLayout , "Input name" , Snackbar.LENGTH_LONG).show();
            return  false;
        }
        else if(details.getText().toString().isEmpty()){
            Snackbar.make( coordinatorLayout , "Set Deatils" , Snackbar.LENGTH_LONG).show();
            return  false;
        }
        return  true;
    }



    // function to create bottom sheet for setting image,
    private void createBottomSheet(){
        checkPermission();
        mBottomSheetDialog  = new BottomSheetDialog(this);
        mBottomSheetDialog.setTitle("Profile picture");
        View view = getLayoutInflater().inflate( R.layout.bottom_sheet_image_select_dilaog , null );
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
                //// TODO: 18/8/17  set file to null and stuf
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



    public void checkPermission(){
        if( ContextCompat.checkSelfPermission(
                this ,
                android.Manifest.permission.READ_EXTERNAL_STORAGE  ) != PackageManager.PERMISSION_GRANTED ){
            ActivityCompat.requestPermissions( Create_brand_activity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE },
                    1 );
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 12345 :
                if(resultCode == RESULT_OK ){
                    try{
                        checkPermission();
                        Uri imageUri = data.getData();
                        file  = new File(getPath(imageUri));
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        imageView.setImageBitmap(selectedImage);

                    }catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 1234 :
                if(resultCode == RESULT_OK){
                    Bitmap  photo = (Bitmap) data.getExtras().get("data");
                    imageView.setImageBitmap(photo);
                    if(photo!=null) {
                        file = new File(getApplicationContext().getCacheDir(), "tem");
                        try {
                            FileOutputStream fo = new FileOutputStream(file);
                            photo.compress(Bitmap.CompressFormat.PNG, 0, fo);
                            fo.flush();
                            fo.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            Err.s(getApplicationContext() , "Image not found");
                        } catch (IOException e) {
                            Err.s(getApplicationContext() , "OutPut stream exception");
                            e.printStackTrace();
                        }
                    }
                    else{
                        Err.s( getApplicationContext() ,"Image not captured");
                    }
                }
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

    private Brand  getBrand(){
        Brand brand = new Brand();
        brand.setDetails(details.getText().toString());
        brand.setBrand(name.getText().toString());
        return  brand;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case  R.id.create_employee :
                Dialog dialog = CustumProgressDialog.getProgressDialog(this);
                if(checkError()){
                    dialog.show();
                    RequestBody reqfile =  RequestBody.create(MediaType.parse("image/*"),file);
                    MultipartBody.Part body = MultipartBody.Part.createFormData("upload",file.getName(),reqfile);
                    mBrandApi.addNewBrandLogo( body , getBrand() , dialog );
                }
                break;
            case R.id.cancel_employee :
                finish();
                break;
        }
        return true;
    }


}

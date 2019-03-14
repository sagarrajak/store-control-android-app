package com.example.sagar.myapplication.element.product;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
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
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;


import com.example.sagar.myapplication.CustumProgressDialog;
import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.adapter.product.ProductGridAdapter;
import com.example.sagar.myapplication.adapter.product.ProductBrandSeletorAdapter;
import com.example.sagar.myapplication.adapter.product.ProductCategoryPickerAdapter;
import com.example.sagar.myapplication.adapter.retailer.RetailerPickerAdapter;
import com.example.sagar.myapplication.api.ProductApi;
import com.example.sagar.myapplication.modal.Brand;
import com.example.sagar.myapplication.modal.Product;
import com.example.sagar.myapplication.modal.ProductType;
import com.example.sagar.myapplication.modal.Retailer;
import com.example.sagar.myapplication.utill.ui.CreateDetailsDialog;
import com.example.sagar.myapplication.utill.Err;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class Activity_create_product extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText name, brand, details, type, price, retailer;
    private BottomSheetDialog mBottomSheetDialog;
    private FloatingActionButton mFlaotingactionButton;
    private ImageView mImageView;
    private CoordinatorLayout mCoordinatorLayout;
    private ProductApi mProductApi;
    private ProductGridAdapter mProductGridAdapter;
    private File file;
    private RetailerPickerAdapter mRetailerPickerAdapter;
    private ProductCategoryPickerAdapter mCategoryPickerAdapter;
    private Brand ansBrand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_create_product);
        setUiElement();
        setToolbar();
        productTypeOnClickDialogCreator();
        productBrandOnClickDialog();
        productDetailsOnClickDialog();
        showPopUpMenuForForRetailer();
        mFlaotingactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createBottomSheet();
            }
        });
        retailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo add reatiler dialog
            }
        });
    }


    private void productBrandOnClickDialog() {
        brand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.genral_dialog, null);
                RecyclerView recyclerView = (RecyclerView) dialogView.findViewById(R.id.recycle_view);
                ProgressBar mProgressbar = (ProgressBar) dialogView.findViewById(R.id.progressBar);
                final ProductBrandSeletorAdapter mProductBrandSeletorAdapter = new ProductBrandSeletorAdapter(getBaseContext(), mProgressbar);
                recyclerView.setAdapter(mProductBrandSeletorAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplication(), LinearLayoutManager.VERTICAL, false));
                final AlertDialog.Builder builder = new AlertDialog.Builder(Activity_create_product.this);
                builder.setTitle("Select brand");
                builder.setView(dialogView);
                builder.setCancelable(false);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ansBrand = mProductBrandSeletorAdapter.getProductBrand();
                        if (ansBrand != null) {
                            brand.setText(ansBrand.getBrand());
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });
    }

    private void productDetailsOnClickDialog() {
        CreateDetailsDialog mCreateDetailsDialog = new CreateDetailsDialog(Activity_create_product.this, details, getLayoutInflater());
        mCreateDetailsDialog.showDialog();
    }

    private void setToolbar() {
        setTitle("Create new product");
        toolbar = (Toolbar) findViewById(R.id.activity_create_product_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setUiElement() {
        name = (EditText) findViewById(R.id.create_product_name);
        brand = (EditText) findViewById(R.id.create_product_brand);
        details = (EditText) findViewById(R.id.create_product_details);
        type = (EditText) findViewById(R.id.create_product_type);
        price = (EditText) findViewById(R.id.create_product_price);
        retailer = (EditText) findViewById(R.id.create_product_retailer);
        mFlaotingactionButton = (FloatingActionButton) findViewById(R.id.create_product_floarinf_action_buttom);
        mImageView = (ImageView) findViewById(R.id.create_product_image_view);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.create_product_coordinate_layout);
        mProductGridAdapter = ProductGridAdapter.getProductAdapter(getBaseContext());
        mProductApi = ProductApi.getmProductApi(mProductGridAdapter);
        mRetailerPickerAdapter = new RetailerPickerAdapter(getBaseContext());
        mCategoryPickerAdapter = new ProductCategoryPickerAdapter(getBaseContext());
    }

    private void createBottomSheet() {
        mBottomSheetDialog = new BottomSheetDialog(this);
        mBottomSheetDialog.setTitle("Profile picture");
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_image_select_dilaog, null);
        mBottomSheetDialog.setContentView(view);
        view.findViewById(R.id.bottom_sheet_gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, 12345);
            }
        });
        view.findViewById(R.id.bottom_sheet_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // for deleting exsiting image
                //// TODO: 19/8/17 for deleting the existing image
            }
        });
        view.findViewById(R.id.bottom_sheet_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1234);
            }
        });
        mBottomSheetDialog.show();

    }


    private void showPopUpMenuForForRetailer() {
        retailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                View dialog_view = inflater.inflate(R.layout.fragment_product_category_chooser_dialog_fragment, null);
                RecyclerView recyclerView = (RecyclerView) dialog_view.findViewById(R.id.recycle_view);
                recyclerView.setAdapter(mRetailerPickerAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_create_product.this);
                builder.setTitle("Select Retailers");
                builder.setView(dialog_view);
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        HashMap<Retailer, Integer> mRetailerMap = mRetailerPickerAdapter.getMap();
                        StringBuilder stringBuilder = new StringBuilder();
                        for (Retailer obj : mRetailerMap.keySet()) {
                            stringBuilder.append(obj.getName() + " , ");
                        }
                        retailer.setText(stringBuilder.toString());
                    }
                });

                builder.show();

            }
        });
    }


    private void productTypeOnClickDialogCreator() {
        type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getLayoutInflater();
                View dialog_view = inflater.inflate(R.layout.fragment_product_category_chooser_dialog_fragment, null);
                RecyclerView recyclerView = (RecyclerView) dialog_view.findViewById(R.id.recycle_view);
                recyclerView.setAdapter(mCategoryPickerAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_create_product.this);

                builder.setTitle("Select product Category");
                builder.setView(dialog_view);
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        HashMap<ProductType, Integer> ma = mCategoryPickerAdapter.getHasMap();
                        StringBuilder stringBuilder = new StringBuilder();
                        if (ma != null) {
                            for (ProductType obj : ma.keySet()) {
                                stringBuilder.append(obj.getProductType() + " , ");
                            }
                        }
                        type.setText(stringBuilder.toString());
                    }
                });

                builder.show();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 12345:
                if (checkPermission() && resultCode == RESULT_OK) {
                    try {
                        Uri imageUri = data.getData();
                        file = new File(getPath(imageUri));
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        mImageView.setImageBitmap(selectedImage);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 1234:
                if (resultCode == RESULT_OK) {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    mImageView.setImageBitmap(photo);
                    if (photo != null) {
                        file = new File(getApplicationContext().getCacheDir(), "tem");
                        try {
                            FileOutputStream fo = new FileOutputStream(file);
                            photo.compress(Bitmap.CompressFormat.PNG, 0, fo);
                            fo.flush();
                            fo.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            Err.s(getApplicationContext(), "Image not found");
                        } catch (IOException e) {
                            Err.s(getApplicationContext(), "OutPut stream exception");
                            e.printStackTrace();
                        }
                    } else {
                        Err.s(getApplicationContext(), "Image not captured");
                    }
                }

                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(Activity_create_product.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        }
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ok_cancel, menu);
        return true;
    }

    public String getPath(Uri uri) {
        String res = null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    private boolean checkError() {
        if (name.getText().toString().isEmpty()) {
            Snackbar.make(mCoordinatorLayout, "Pls add name", Snackbar.LENGTH_LONG).show();
            return false;
        } else if (details.getText().toString().isEmpty()) {
            Snackbar.make(mCoordinatorLayout, "Add brand", Snackbar.LENGTH_LONG).show();
            return false;
        } else if (price.getText().toString().isEmpty()) {
            Snackbar.make(mCoordinatorLayout, "Price must not be empty", Snackbar.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private Product getProduct() {
        Product product = new Product();
        product.setBrand(ansBrand.getId());
        product.setDetail(details.getText().toString());
        product.setName(name.getText().toString());
        product.setPrice(price.getText().toString());
        List<String> temRetailerId = new ArrayList<>();
        for (Retailer obj : mRetailerPickerAdapter.getSelectedList()) {
            temRetailerId.add(obj.getId());
        }
        product.setRetailer(temRetailerId);
        List<String> temProductCategory = new ArrayList<>();
        for (ProductType obj : mCategoryPickerAdapter.getHasMap().keySet()) {
            temProductCategory.add(obj.getId());
        }
        product.setType(temProductCategory);
        return product;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ok:
                if (checkError()) {
                    Dialog dialog = CustumProgressDialog.getProgressDialog(this);
                    dialog.show();
                    RequestBody reqfile = RequestBody.create(MediaType.parse("image/*"), file);
                    MultipartBody.Part body = MultipartBody.Part.createFormData("upload", file.getName(), reqfile);
                    mProductApi.addImage(body, getProduct(), dialog);
                }
                break;
            case R.id.cancel:

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

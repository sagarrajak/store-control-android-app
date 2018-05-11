package com.example.sagar.myapplication.utill.ui;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;

import com.example.sagar.myapplication.Config;
import com.example.sagar.myapplication.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class BottomSheetImage  {

    private BottomSheetDialog mBottomSheetDialog;
    private View view;
    private Activity mActivity;
    private BottomSheetHelper mBottomSheetHelper;

    public BottomSheetImage(Activity mActivity, BottomSheetHelper mBottomSheetHeler){
        this.mActivity   =  mActivity;
        mBottomSheetDialog  =  new BottomSheetDialog(mActivity);
        this.mBottomSheetHelper =  mBottomSheetHeler;
        configureBottomSheet();
    }
    public BottomSheetImage() {}

    private void configureBottomSheet(){
        view = mActivity.getLayoutInflater().inflate(R.layout.bottom_sheet_image_select_dilaog,null);
        mBottomSheetDialog.setContentView(view);
        view.findViewById(R.id.bottom_sheet_gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_PICK , MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                mActivity.startActivityForResult(intent, Config.SELECT_IMAGE_FROM_STORAGE);
            }
        });
        view.findViewById(R.id.bottom_sheet_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();
                mBottomSheetHelper.resetImageUri();
            }
        });
        view.findViewById(R.id.bottom_sheet_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(mActivity);
            }
        });
    }

    public  void hideDeleteDialog(){
        view.findViewById(R.id.bottom_sheet_delete).setVisibility(View.GONE);
    }

    public void showDeleteDialog(){
        view.findViewById(R.id.bottom_sheet_delete).setVisibility(View.VISIBLE);
    }
    public  interface  BottomSheetHelper{
          public void resetImageUri();
    }
    public void showBottomSheet(){
        mBottomSheetDialog.show();
    }
}

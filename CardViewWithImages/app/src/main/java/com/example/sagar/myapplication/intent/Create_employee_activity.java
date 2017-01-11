package com.example.sagar.myapplication.intent;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.adapter.EmployeeAdapter;
import com.example.sagar.myapplication.api.EmployeeApi;
import com.example.sagar.myapplication.modal.Employee;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class Create_employee_activity extends AppCompatActivity  {

    private Toolbar toolbar;
    private EditText name, mail, date_of_birth, date_of_join, phone_num, pan_num, adress, work_profile;
    private ImageView imageView;
    private Calendar calendar;
    private FloatingActionButton mFloatingActionButton;
    private BottomSheetDialog mBottomSheetDialog;
    private File file ;
    private ArrayAdapter<String> adapter = null;



    private EmployeeApi mEmployeeApi;
    private EmployeeAdapter mEmployeeAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_employee);
        toolbar = (Toolbar) findViewById(R.id.activity_create_employee_toolbar);
        setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            calendar = Calendar.getInstance();

            name = (EditText) findViewById(R.id.add_employee_name);
            mail = (EditText) findViewById(R.id.add_employee_mail);
            date_of_birth = (EditText) findViewById(R.id.add_employee_layout_date_of_birth);
            date_of_join = (EditText) findViewById(R.id.add_employee_date_of_join);
            phone_num = (EditText) findViewById(R.id.add_employee_phone_num);
            pan_num = (EditText) findViewById(R.id.add_employee_pan_num);
            adress = (EditText) findViewById(R.id.add_employee_adress);
            work_profile = (EditText) findViewById(R.id.add_employee_work_profile);
            mFloatingActionButton = (FloatingActionButton) findViewById(R.id.add_button_float);
            imageView  = (ImageView) findViewById(R.id.add_employee_image_view);
            adapter = new ArrayAdapter<String>( Create_employee_activity.this,android.R.layout.select_dialog_item );

//          mEmployeeApi = (EmployeeApi) getIntent().getExtras().get("EmployeeApi");
//          mEmployeeAdapter = (EmployeeAdapter) getIntent().getExtras().get("EmployeeAdapter");

            mEmployeeAdapter =  EmployeeAdapter.getEmployeeAdapter(getApplicationContext());
            mEmployeeApi = EmployeeApi.getEmloyeeApi(mEmployeeAdapter,getApplicationContext());

            final DatePickerDialog.OnDateSetListener dateOfBirth = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day_of_month) {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, day_of_month);
                    date_of_birth.setText(new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime()));
                }
            };

            final DatePickerDialog.OnDateSetListener dateOfJoin = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day_of_month) {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, day_of_month);
                    date_of_join.setText(new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime()));
                }
            };

            date_of_birth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DatePickerDialog(Create_employee_activity.this, dateOfBirth, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });

            date_of_join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DatePickerDialog(Create_employee_activity.this, dateOfJoin, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });

            mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createBottomSheet();
                }
            });
            work_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                 public void onClick(View view) {
                     createAlertDialog();
                 }
            });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menu_create_employee_activity , menu );
        return true;
    }

    public void createAlertDialog(){

        adapter.add("Something");
        adapter.add("Something");
        adapter.add("Something");
        adapter.add("Something");
        adapter.add("Something");
        adapter.add("Something");

        final AlertDialog.Builder builder =  new AlertDialog.Builder(Create_employee_activity.this);

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });


                    builder.setAdapter(adapter, new DialogInterface.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                                work_profile.setText(adapter.getItem(i));
                        }
                    });

                    builder.show();

    }



    private void createBottomSheet(){

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case  12345 :
                if( resultCode == RESULT_OK ){
                    try{
                            checkPermission();
                            Uri imageUri = data.getData();
                            file  = new File(getPath(imageUri));
                            final InputStream  imageStream = getContentResolver().openInputStream(imageUri);
                            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                            imageView.setImageBitmap(selectedImage);

                    }catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            break;
        }
    }

    public void checkPermission(){
        if( ContextCompat.checkSelfPermission(
                this ,
                android.Manifest.permission.READ_EXTERNAL_STORAGE  ) != PackageManager.PERMISSION_GRANTED ){

            ActivityCompat.requestPermissions( Create_employee_activity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE },
                    1 );


        }

    }

    private boolean checkError(){
//        // TODO: 1/8/2017 to check error in filed
        Snackbar snackbar ;
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinate_layout_create_employee);
        if( file==null ){
            Snackbar.make( coordinatorLayout , "Select image" , Snackbar.LENGTH_LONG).show();
            return  false;
        }
        else if(name.getText().toString().isEmpty()){
            Snackbar.make( coordinatorLayout , "Input  name" , Snackbar.LENGTH_LONG).show();
            return  false;
        }
        else if(date_of_birth.getText().toString().isEmpty() ){
            Snackbar.make( coordinatorLayout , "Fill date of birth" , Snackbar.LENGTH_LONG).show();
            return  false;
        }
        else if( date_of_join.getText().toString().isEmpty()){
            Snackbar.make( coordinatorLayout , "Fill date of join" , Snackbar.LENGTH_LONG).show();
            return  false;
        }
        else if( mail.getText().toString().isEmpty() ){
            Snackbar.make( coordinatorLayout , "Fill mail adress" , Snackbar.LENGTH_LONG).show();
            return  false;
        }
        else if( phone_num.getText().toString().isEmpty() ){
            Snackbar.make( coordinatorLayout , "Fill phone number" , Snackbar.LENGTH_LONG).show();
            return  false;
        }
        else if( pan_num.getText().toString().isEmpty() ){
            Snackbar.make( coordinatorLayout , "Fill pan number" , Snackbar.LENGTH_LONG).show();
            return  false;
        }
        else if( work_profile.getText().toString().isEmpty() ){
            Snackbar.make( coordinatorLayout , "fill work profile" , Snackbar.LENGTH_LONG).show();
            return  false;
        }
        return  true;
    }

    /** helper method for post employee**/
    private Employee getEmployee(){
        Employee employee =  new Employee();
        employee.setDateOfBirth(date_of_birth.getText().toString());
        employee.setDateOfJoin(date_of_join.getText().toString());
        employee.setMail(mail.getText().toString());
        employee.setName(name.getText().toString());
        employee.setPanNum(pan_num.getText().toString());
        employee.setPhoneNumber(phone_num.getText().toString());
       return  employee;
    }

    private ProgressDialog createProgressDialog(){
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        return  dialog;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){

            case R.id.create_employee :
                    ProgressDialog dialog = createProgressDialog();
                        if(checkError()){
                                dialog.show();
                                RequestBody reqfile =  RequestBody.create(MediaType.parse("image/*"),file);
                                MultipartBody.Part body = MultipartBody.Part.createFormData("upload",file.getName(),reqfile);
                                if( mEmployeeApi.addEmployeeImage(body , getEmployee() , dialog )) {
                                    mEmployeeAdapter.notifyDataSetChanged();
                                }
                        }

                break;

            case R.id.cancel_employee :
                finish();
                break;
        }
        return true;
    }

}

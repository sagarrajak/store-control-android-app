package com.example.sagar.myapplication.fragment;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.SpaceItemDecoration;
import com.example.sagar.myapplication.adapter.EmployeeGridAdapter;
import com.example.sagar.myapplication.adapter.EmployeeListAdapter;
import com.example.sagar.myapplication.api.EmployeeApi;
import com.example.sagar.myapplication.intent.employee.Create_employee_activity;

public class Employee_fragment extends Fragment {

    private EmployeeGridAdapter mEmployeeGridAdapter;
    private EmployeeApi mEmployeeApi;
    private RecyclerView recyclerView;
    private boolean isListLayout ;
    private MenuItem listToGrid;

    private FloatingActionButton fab;
    public Employee_fragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_employee,container,false);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mEmployeeGridAdapter = EmployeeGridAdapter.getEmployeeAdapter(getContext());
        mEmployeeGridAdapter.setmContext(getContext());
        isListLayout = false;
        mEmployeeApi = EmployeeApi.getEmloyeeApi( mEmployeeGridAdapter ,getActivity());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        setRecycleView();
        super.onActivityCreated(savedInstanceState);
    }

    private ProgressDialog createProgressDialog(){
            ProgressDialog dialog = new ProgressDialog(getActivity());
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            return  dialog;
    }
    private void setRecycleView(){
             recyclerView = (RecyclerView) getView().findViewById(R.id.recycle_view);
             recyclerView.addItemDecoration(new SpaceItemDecoration(1));
             GridLayoutManager mGridLayoutManager = new GridLayoutManager( getActivity(),2,RecyclerView.VERTICAL , true );
             recyclerView.setAdapter(mEmployeeGridAdapter);
             recyclerView.setLayoutManager(mGridLayoutManager);
             Dialog dialog = createProgressDialog();
             dialog.show();
             mEmployeeApi.getEmployee(dialog);
    }

    private void changeListToGride(){
        GridLayoutManager mGridLayoutManager = new GridLayoutManager( getActivity(),2,RecyclerView.VERTICAL , true );
        recyclerView.setAdapter(mEmployeeGridAdapter);
        recyclerView.setLayoutManager(mGridLayoutManager);
        mEmployeeApi.addNewAdapter(mEmployeeGridAdapter);
        Dialog dialog = createProgressDialog();
        dialog.show();
        mEmployeeApi.getEmployee(dialog);
    }

    private void changeGridToList(){
        EmployeeListAdapter mEmployeeListAdapter = EmployeeListAdapter.getmEmployeeListAdapter(getContext());
        mEmployeeApi.addNewAdapter(mEmployeeListAdapter);
        recyclerView.setAdapter(mEmployeeListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Dialog dialog = createProgressDialog();
        dialog.show();
        mEmployeeApi.getEmployee(dialog);
    }

    @Override
    public void onCreateOptionsMenu( Menu menu , MenuInflater inflater ){
        inflater.inflate(R.menu.menu_employee_fragment ,menu);
        listToGrid = menu.findItem(R.id.list_to_grid);

        if(isListLayout)
            listToGrid.setIcon(R.drawable.ic_grid_24dp);
        else
            listToGrid.setIcon(R.drawable.ic_list_black_24dp);

        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case  R.id.list_to_grid :
                if(isListLayout){
                    changeListToGride();
                    if(listToGrid!=null){
                       listToGrid.setIcon(R.drawable.ic_list_black_24dp);
                    }
                    isListLayout = !isListLayout;
                }
                else{
                    changeGridToList();
                    if(listToGrid!=null){
                        listToGrid.setIcon(R.drawable.ic_grid_24dp);
                    }
                    isListLayout = !isListLayout;
                }
                break;
            case R.id.create_employee :
                Intent intent = new Intent( getActivity().getApplicationContext() , Create_employee_activity.class);
                startActivity(intent);
                break;
            case R.id.fragment_menu_sort :
                creteButtomSheet();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
   private void creteButtomSheet(){

        final RadioButton salary,name,date_of_join;
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        View view = getActivity().getLayoutInflater().inflate(R.layout.buttom_sheet_employee_fragment,null);
        bottomSheetDialog.setContentView(view);

        salary = (RadioButton) view.findViewById(R.id.bottom_sheet_employee_salery_radiobottom);
        name = (RadioButton) view.findViewById(R.id.bottom_sheet_employee_name_radiobottom);
        date_of_join = (RadioButton) view.findViewById(R.id.bottom_sheet_employee_date_of_join_radiobottm);


           name.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view){
                     Dialog dialog = createProgressDialog();
                     dialog.show();
                     mEmployeeApi.getSortByName(dialog);
                     date_of_join.setChecked(false);
                     salary.setChecked(false);
                 }
             });
            date_of_join.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Dialog dialog = createProgressDialog();
                   dialog.show();
                   mEmployeeApi.getSortByDateOfJoin(dialog);
                    name.setChecked(false);
                    salary.setChecked(false);
               }
           });
            salary.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                //// TODO: 1/20/2017
                 name.setChecked(false);
                 date_of_join.setChecked(false);
             }
         });
       bottomSheetDialog.show();
   }
}

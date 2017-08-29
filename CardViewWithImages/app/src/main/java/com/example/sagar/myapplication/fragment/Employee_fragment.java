package com.example.sagar.myapplication.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
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

import com.example.sagar.myapplication.CustumProgressDialog;
import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.adapter.EmployeeGridAdapter;
import com.example.sagar.myapplication.adapter.EmployeeListAdapter;
import com.example.sagar.myapplication.api.EmployeeApi;
import com.example.sagar.myapplication.intent.employee.Create_employee_activity;
import com.example.sagar.myapplication.utill.SpaceItemDecoration;

public class Employee_fragment extends Fragment {

    private EmployeeApi mEmployeeApi;
    private RecyclerView recyclerView;
    private boolean isListLayout ;
    private MenuItem listToGrid;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private FloatingActionButton fab;
    public  Employee_fragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){

        return inflater.inflate(  R.layout.fragment_employee , container,false);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){

            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

            super.onActivityCreated(savedInstanceState);
            isListLayout = false;
            mEmployeeApi = EmployeeApi.getEmloyeeApi(EmployeeGridAdapter.getEmployeeGridAdapter(getActivity()));
            mSwipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipe_to_refresh_employee);
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                            mEmployeeApi.listEmployee(mSwipeRefreshLayout);
                }
            });
            createRecycleView();

    }

    private void createRecycleView(){

            recyclerView = (RecyclerView)getView().findViewById(R.id.recycle_view);
            recyclerView.addItemDecoration(new SpaceItemDecoration(1));
            GridLayoutManager mGridLayoutManager = new GridLayoutManager(  getActivity() , 2 , RecyclerView.VERTICAL , true );
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter( EmployeeGridAdapter.getEmployeeGridAdapter( getActivity()) );
            recyclerView.setLayoutManager( mGridLayoutManager );
            mEmployeeApi.setAdapter( EmployeeGridAdapter.getEmployeeGridAdapter(getActivity()) );
            Dialog dialog = CustumProgressDialog.getProgressDialog( getContext() );
            dialog.show();
            mEmployeeApi.listEmployee( dialog );

    }

    private void changeListToGride(){

            recyclerView.addItemDecoration(new SpaceItemDecoration(1));
            GridLayoutManager mGridLayoutManager = new GridLayoutManager(  getActivity() , 2 , RecyclerView.VERTICAL , true );
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter( EmployeeGridAdapter.getEmployeeGridAdapter( getActivity()) );
            recyclerView.setLayoutManager( mGridLayoutManager );
            mEmployeeApi.setAdapter( EmployeeGridAdapter.getEmployeeGridAdapter(getActivity()) );
            Dialog dialog = CustumProgressDialog.getProgressDialog( getContext() );
            dialog.show();
            mEmployeeApi.listEmployee( dialog );

    }

    private void changeGridToList(){

            EmployeeListAdapter mEmployeeListAdapter = EmployeeListAdapter.getmEmployeeListAdapter(getContext());
            mEmployeeApi.addNewAdapter(mEmployeeListAdapter);
            recyclerView.setAdapter(mEmployeeListAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            Dialog dialog = CustumProgressDialog.getProgressDialog(getActivity());
            dialog.show();
            mEmployeeApi.listEmployee(dialog);
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
                    listToGrid.setIcon(R.drawable.ic_list_black_24dp);

                }
                else{
                    changeGridToList();
                    listToGrid.setIcon(R.drawable.ic_grid_24dp);
                }
                isListLayout=!isListLayout;
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
                     Dialog dialog = CustumProgressDialog.getProgressDialog(getActivity());
                     dialog.show();
                     mEmployeeApi.getSortByName(dialog);
                     date_of_join.setChecked(false);
                     salary.setChecked(false);
                 }
             });
            date_of_join.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                    Dialog dialog = CustumProgressDialog.getProgressDialog(getActivity());
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

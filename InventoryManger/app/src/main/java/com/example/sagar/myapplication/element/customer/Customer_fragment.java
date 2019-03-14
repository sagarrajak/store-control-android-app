package com.example.sagar.myapplication.element.customer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.example.sagar.myapplication.CustumProgressDialog;
import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.adapter.customer.CustomerGridAdapter;
import com.example.sagar.myapplication.adapter.customer.CustomerListAdapter;
import com.example.sagar.myapplication.api.CustomerApi;
import com.example.sagar.myapplication.utill.SpaceItemDecoration;

public class Customer_fragment extends Fragment {

    private CustomerApi mCustomerApi;
    private RecyclerView mRecycleView;
    private boolean isListView;
    private MenuItem listToGrid;
    private SwipeRefreshLayout mSwipeToRefresh;

    public Customer_fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isListView = false;
        mSwipeToRefresh = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipe_to_refresh_customer);
        mRecycleView = (RecyclerView) getActivity().findViewById(R.id.recycle_view_customer);
        mCustomerApi = CustomerApi.getCustomerApi(getContext(), CustomerGridAdapter.getCustomerGridAdapter(getContext()));
        changeListToGrid();
        mSwipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeToRefresh.setRefreshing(true);
                mCustomerApi.listCustomer(mSwipeToRefresh);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void changeListToGrid() {
        mRecycleView.addItemDecoration(new SpaceItemDecoration(1));
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, true);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mCustomerApi.setCustomerAdapter(CustomerGridAdapter.getCustomerGridAdapter(getContext()));
        mRecycleView.setAdapter(CustomerGridAdapter.getCustomerGridAdapter(getContext()));
        mRecycleView.setLayoutManager(mGridLayoutManager);
        ProgressDialog mProgressDialog = CustumProgressDialog.getProgressDialog(getContext());
        mProgressDialog.show();
        mCustomerApi.setCustomerAdapter(CustomerGridAdapter.getCustomerGridAdapter(getContext()));
        mCustomerApi.listCustomer(mProgressDialog);
    }

    private void changeGridToList() {
        mCustomerApi.setCustomerAdapter(CustomerListAdapter.getCustomerListAdapter(getContext()));
        mCustomerApi.setCustomerAdapter(CustomerListAdapter.getCustomerListAdapter(getContext()));
        mRecycleView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecycleView.setAdapter(CustomerListAdapter.getCustomerListAdapter(getContext()));
        ProgressDialog dialog = CustumProgressDialog.getProgressDialog(getContext());
        dialog.show();
        mCustomerApi.listCustomer(dialog);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_customer_fragment, menu);
        listToGrid = menu.findItem(R.id.list_to_grid);
        if (isListView)
            listToGrid.setIcon(R.drawable.ic_grid_24dp);
        else
            listToGrid.setIcon(R.drawable.ic_list_black_24dp);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.list_to_grid:
                if (isListView) {
                    changeListToGrid();
                    listToGrid.setIcon(R.drawable.ic_list_black_24dp);
                } else {
                    changeGridToList();
                    listToGrid.setIcon(R.drawable.ic_grid_24dp);
                }
                isListView = !isListView;
                break;
            case R.id.ok:
                Intent intent = new Intent(getActivity().getApplicationContext(), Create_customer_activity.class);
                intent.putExtra("isLinearLayout", isListView);
                startActivity(intent);
                break;
            case R.id.sort_items:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

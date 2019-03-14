package com.example.sagar.myapplication.element.transaction.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sagar.myapplication.R;

public class Check_in_stocks_fragment extends Fragment {
    public Check_in_stocks_fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_check_in_stocks_of_items, container, false);
    }
}

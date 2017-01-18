package com.example.sagar.myapplication.Dialog;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sagar.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Create_new_right extends Fragment {
    public Create_new_right() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_work_profile, container, false);
    }

}

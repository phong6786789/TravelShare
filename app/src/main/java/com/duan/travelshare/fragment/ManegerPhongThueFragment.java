package com.duan.travelshare.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duan.travelshare.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ManegerPhongThueFragment extends Fragment {

    public ManegerPhongThueFragment(){

    }
    FloatingActionButton btnAddPhongThue;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_maneger_phong_thue, container, false);
        btnAddPhongThue = view.findViewById(R.id.btnAddMngPhongThue);
        return view;
    }
}
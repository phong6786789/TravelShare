package com.duan.travelshare.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.duan.travelshare.R;

public class GiaoDichFragment extends Fragment {
    public GiaoDichFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_giao_dich, container, false);

        //Toolbar
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        TextView title =toolbar.findViewById(R.id.tbTitle);
        ImageView back = toolbar.findViewById(R.id.tbBack);
        title.setText("GIAO DỊCH");
        back.setVisibility(View.INVISIBLE);


        return view;
    }
}
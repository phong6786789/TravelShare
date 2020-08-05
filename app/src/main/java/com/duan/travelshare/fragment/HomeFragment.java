package com.duan.travelshare.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duan.travelshare.R;
import com.duan.travelshare.adapter.PhongHomeAdapter;
import com.duan.travelshare.firebasedao.PhongDao;
import com.duan.travelshare.model.ChiTietPhong;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment  {
    RecyclerView rcvPhong;
    public  static PhongHomeAdapter phongAdapter;
    List<ChiTietPhong> list;
    PhongDao phongDao;
    public HomeFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.color.blue);
        rcvPhong=view.findViewById(R.id.listPhong);
        list=new ArrayList<>();
        phongDao=new PhongDao(getActivity());
        try {
            list=phongDao.getAllPhongHome();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        phongAdapter=new PhongHomeAdapter(list,getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvPhong.setLayoutManager(linearLayoutManager);
        rcvPhong.setAdapter(phongAdapter);
        phongAdapter.notifyDataSetChanged();
        return view;
    }
}
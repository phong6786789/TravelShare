package com.duan.travelshare.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.duan.travelshare.MainActivity;
import com.duan.travelshare.R;
import com.duan.travelshare.adapter.ThongBaoAdapter;
import com.duan.travelshare.firebasedao.ThongBaoDao;
import com.duan.travelshare.model.ThongBao;

import java.util.ArrayList;

public class ThongBaoFragment extends Fragment {

    ThongBaoDao thongBaoDao;
    public static ThongBaoAdapter thongBaoAdapter;
    ArrayList<ThongBao> list = new ArrayList<>();
    RecyclerView recThongBao;
    String email = MainActivity.emailUser;

    public ThongBaoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thong_bao, container, false);
        //Toolbar
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.tbTitle);
        ImageView back = toolbar.findViewById(R.id.tbBack);
        title.setText("THÔNG BÁO");
        back.setVisibility(View.INVISIBLE);


        return view;
    }

    //Phân biệt thông báo của chủ và khách
    private void locUser() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Khai báo
        thongBaoDao = new ThongBaoDao(getActivity());
        list = thongBaoDao.getAll(email);
        recThongBao = view.findViewById(R.id.recThongBao);
        thongBaoAdapter = new ThongBaoAdapter(list, getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recThongBao.setLayoutManager(linearLayoutManager);
        recThongBao.setAdapter(thongBaoAdapter);
    }
}
package com.duan.travelshare.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

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
    private EditText textSearch;
    public HomeFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        //Toolbar
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        TextView title =toolbar.findViewById(R.id.tbTitle);
        ImageView back = toolbar.findViewById(R.id.tbBack);
        title.setText("TRANG CHỦ");
        back.setVisibility(View.INVISIBLE);



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

        //Tìm kiếm
        textSearch = view.findViewById(R.id.edtSearch);
        textSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int
                    count) {
                System.out.println("Text [" + s + "] - Start [" + start + "] - Before [" + before + "] - Count [" + count + "]");
                if (count < before) {
                    phongAdapter.resetData();
                }
                phongAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return view;
    }
}
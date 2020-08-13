package com.duan.travelshare.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.duan.travelshare.MainActivity;
import com.duan.travelshare.R;
import com.duan.travelshare.adapter.KhachGiaoDichAdapter;
import com.duan.travelshare.adapter.TongGiaoDichdapter;
import com.duan.travelshare.firebasedao.GiaoDichDao;
import com.duan.travelshare.model.ChiTietPhong;
import com.duan.travelshare.model.FullUser;
import com.duan.travelshare.model.GiaoDich;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class GiaoDichFragment extends Fragment {
    private GiaoDichDao giaoDichDao;
    public static ArrayList<GiaoDich> list = new ArrayList<>();
    public static ArrayList<GiaoDich> listDangGG = new ArrayList<>();
    public static ArrayList<GiaoDich> lisTongGG = new ArrayList<>();
    private RecyclerView DangGG, TongGG;
    public static KhachGiaoDichAdapter khachGiaoDichAdapter;
    public static TongGiaoDichdapter tongGiaoDichdapter;
    static LinearLayout lnDangGG, lnTongGG;

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
        TextView title = toolbar.findViewById(R.id.tbTitle);
        ImageView back = toolbar.findViewById(R.id.tbBack);
        title.setText("GIAO DỊCH");
        back.setVisibility(View.INVISIBLE);

        giaoDichDao = new GiaoDichDao(getActivity());
        list = giaoDichDao.getAll();
        //RECYCLEVIEW
        DangGG = view.findViewById(R.id.listDangGG);
        TongGG = view.findViewById(R.id.listTongGG);
        lnDangGG = view.findViewById(R.id.listDangGiaoDich);
        lnTongGG = view.findViewById(R.id.listTongGiaoDich);
        khachGiaoDichAdapter = new KhachGiaoDichAdapter(listDangGG, getActivity());
        DangGG.setAdapter(khachGiaoDichAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        DangGG.setLayoutManager(linearLayoutManager);


        tongGiaoDichdapter = new TongGiaoDichdapter(lisTongGG, getActivity());
        TongGG.setAdapter(tongGiaoDichdapter);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity());
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        TongGG.setLayoutManager(linearLayoutManager2);
        return view;
    }


    public static void locGiaoDich() {
        listDangGG.clear();
        lisTongGG.clear();
        String email = MainActivity.emailUser;
        for (int i = 0; i < list.size(); i++) {
            String emailUser = list.get(i).getFullUser().getEmailUser();
            if (emailUser.equalsIgnoreCase(email)) {
                listDangGG.add(list.get(i));
                Log.i("TAG", "email của người đặt: " + emailUser);
                khachGiaoDichAdapter.notifyDataSetChanged();
                break;
            }
        }

        for (int i = 0; i < list.size(); i++) {
            String emailChu = list.get(i).getChiTietPhong().getFullUser().getEmailUser();

            if (emailChu.equalsIgnoreCase(email)) {
                lisTongGG.add(list.get(i));
                Log.i("TAG", "email của chủ: " + emailChu);
                tongGiaoDichdapter.notifyDataSetChanged();
            }
        }

        if (!listDangGG.isEmpty()) {
            lnDangGG.setVisibility(View.VISIBLE);
        }
        if (!lisTongGG.isEmpty()) {
            lnTongGG.setVisibility(View.VISIBLE);
        }
    }
}
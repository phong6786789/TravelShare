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
import com.duan.travelshare.model.ChiTietPhong;
import com.duan.travelshare.model.FullUser;
import com.duan.travelshare.model.GiaoDich;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;

public class GiaoDichFragment extends Fragment {
    public static ArrayList<GiaoDich> list = new ArrayList<>();
    public static ArrayList<GiaoDich> listDangGG = new ArrayList<>();
    public static ArrayList<GiaoDich> lisTongGG = new ArrayList<>();
    private RecyclerView DangGG, TongGG;
    public static KhachGiaoDichAdapter khachGiaoDichAdapter;
    public static TongGiaoDichdapter tongGiaoDichdapter;
    static LinearLayout lnDangGG, lnTongGG;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReferencePhong = firebaseDatabase.getReference("Phong");
    DatabaseReference databaseReferenceGD = firebaseDatabase.getReference("GiaoDich");
    ShimmerFrameLayout container;
    String uID;
    private FirebaseAuth mAuth;
    FullUser fullUser;
    ChiTietPhong chiTietPhong;
    View view;

    public GiaoDichFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_giao_dich, container, false);

        init();
        return view;
    }

    private void init() {
        //Toolbar
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.tbTitle);
        ImageView back = toolbar.findViewById(R.id.tbBack);
        title.setText("GIAO DỊCH");
        back.setVisibility(View.INVISIBLE);
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
        container = (ShimmerFrameLayout) view.findViewById(R.id.shimmer_GD);
        container.startShimmerAnimation();
    }


    private void locGiaoDich() {

        for (int i = 0; i < list.size(); i++) {
            final int z = i;
            databaseReferencePhong.child(list.get(i).getIdPhong()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    listDangGG.clear();
                    lisTongGG.clear();
                    chiTietPhong = snapshot.getValue(ChiTietPhong.class);
                    String uIDChu = chiTietPhong.getuID();
                    if (list.get(z).getIdUser().equalsIgnoreCase(mAuth.getCurrentUser().getUid())) {
                        listDangGG.add(list.get(z));
                        lnDangGG.setVisibility(View.VISIBLE);
                        Log.i("TAG", "email của người đặt: " + mAuth.getCurrentUser().getUid());
                        khachGiaoDichAdapter.notifyDataSetChanged();
                    } else if (uIDChu.equalsIgnoreCase(mAuth.getCurrentUser().getUid())) {
                        Log.i("TAG", "email của chủ: " + uIDChu);
                        lisTongGG.add(list.get(z));
                        lnTongGG.setVisibility(View.VISIBLE);
                        tongGiaoDichdapter.notifyDataSetChanged();
                    }
                    Log.i("TAG", "ĐANG GG: " + listDangGG.size() + "//// TỔNG GG: " + lisTongGG.size());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }


    }

    private void getAllGiaoDich() {
        databaseReferenceGD.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                listDangGG.clear();
                lisTongGG.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    GiaoDich gd = postSnapshot.getValue(GiaoDich.class);
                    list.add(gd);
                }
                container.setVisibility(View.GONE);
                locGiaoDich();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            uID = mAuth.getCurrentUser().getUid();
            getAllGiaoDich();
        }
    }
}
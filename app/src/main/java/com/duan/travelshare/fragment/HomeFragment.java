package com.duan.travelshare.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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

import com.duan.travelshare.MainActivity;
import com.duan.travelshare.R;
import com.duan.travelshare.adapter.PhongHomeAdapter;
import com.duan.travelshare.firebasedao.PhongDao;
import com.duan.travelshare.model.ChiTietPhong;
import com.duan.travelshare.model.FullUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    RecyclerView rcvPhong;
    public static PhongHomeAdapter phongAdapter;
    List<ChiTietPhong> list;
    PhongDao phongDao;
    private EditText textSearch;
    FullUser fullUser;
    String emailUser = MainActivity.emailUser;
    ShowDialog showDialog;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //Toolbar
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.tbTitle);
        ImageView back = toolbar.findViewById(R.id.tbBack);
        title.setText("TRANG CHỦ");
        back.setVisibility(View.INVISIBLE);
        showDialog = new ShowDialog(getActivity());
        checkFullUser();

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvPhong = view.findViewById(R.id.listPhong);
        phongDao = new PhongDao(getActivity());
        try {
            list = phongDao.getAllPhongHome();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        phongAdapter = new PhongHomeAdapter(list, getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvPhong.setLayoutManager(linearLayoutManager);
        rcvPhong.setAdapter(phongAdapter);
    }


    public void checkFullUser() {
        //Lấy dữ liệu User
        Query query = FirebaseDatabase.getInstance().getReference("FullUser").orderByChild("emailUser").equalTo(emailUser);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String userName, cmndUser, emailUser, birtdayUser, phoneUser, addressUser, linkImage;
                    userName = "" + ds.child("userName").getValue();
                    cmndUser = "" + ds.child("cmndUser").getValue();
                    emailUser = "" + ds.child("emailUser").getValue();
                    birtdayUser = "" + ds.child("birtdayUser").getValue();
                    phoneUser = "" + ds.child("phoneUser").getValue();
                    addressUser = "" + ds.child("addressUser").getValue();
                    linkImage = "" + ds.child("linkImage").getValue();
                    fullUser = new FullUser(userName, cmndUser, emailUser, birtdayUser, phoneUser, addressUser, linkImage);
                }
                try {
                    if (fullUser.getCmndUser().isEmpty()) {
                        ShowUserFragment fragment = new ShowUserFragment();
                        showDialog.show("Vui lòng thêm thông tin tài khoản!");
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
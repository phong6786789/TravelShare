package com.duan.travelshare.fragment;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.duan.travelshare.MainActivity;
import com.duan.travelshare.R;

public class UserFragment extends Fragment {
    CardView fullInfo, roomMng, roomFav, partnerRoom, changePass, logOut;
    TextView name, email;
    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        //Khai báo các id

        fullInfo = view.findViewById(R.id.cvUserInfo);
        roomFav = view.findViewById(R.id.cvRoomFavorite);
        roomMng = view.findViewById(R.id.cvRoomManager);
        partnerRoom = view.findViewById(R.id.cvPartner);
        changePass = view.findViewById(R.id.cvChangePass);
        logOut = view.findViewById(R.id.cvLogOut);
        name = view.findViewById(R.id.tvNameUser);
        email = view.findViewById(R.id.tvEmail);
        name.setText(MainActivity.name);
        email.setText(MainActivity.email);
        //Khi chọn thông tin tài khoản
        fullInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        roomMng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        roomFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        partnerRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        return view;
    }

}
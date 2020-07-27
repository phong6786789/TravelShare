package com.duan.travelshare.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.duan.travelshare.MainActivity;
import com.duan.travelshare.R;
import com.duan.travelshare.firebasedao.PhongDao;
import com.duan.travelshare.model.ChiTietPhong;
import com.duan.travelshare.model.FullUser;
import com.duan.travelshare.model.HinhPhong;
import com.google.android.gms.common.util.Base64Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ManegerPhongThueFragment extends Fragment {
    private PhongDao phongDao;
    ShowDialog showDialog;
    FloatingActionButton btnAddPhongThue;
    ArrayList<ChiTietPhong> list;

    public ManegerPhongThueFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_maneger_phong_thue, container, false);
        //Khi ấn nút back
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserFragment userFragment = new UserFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, userFragment)
                        .commit();
            }
        });
        showDialog = new ShowDialog(getActivity());
        btnAddPhongThue = view.findViewById(R.id.btnAddMngPhongThue);

        btnAddPhongThue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRoom();
            }
        });

        //Đổ list phòng lên
        phongDao = new PhongDao(getActivity());
        list = phongDao.getAllPhong();

        return view;
    }

    private void addRoom() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.add_room);
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        final EditText tenPhong, giaPhong, diaChi, moTa;
        Button btnThem, btnNhapLai;
        btnThem = dialog.findViewById(R.id.btnTPThem);
        btnNhapLai = dialog.findViewById(R.id.btnTPNhapLai);
        tenPhong = dialog.findViewById(R.id.edtTPTieuDe);
        giaPhong = dialog.findViewById(R.id.edtTPGiaPhong);
        diaChi = dialog.findViewById(R.id.edtTPDiaChi);
        moTa = dialog.findViewById(R.id.edtTPMoTaChiTietPhong);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FullUser user = MainActivity.fullUserOne;
                HinhPhong hinhPhong = new HinhPhong("1", "http");
                String ten = tenPhong.getText().toString();
                String gia = giaPhong.getText().toString();
                String dc = diaChi.getText().toString();
                String mot = moTa.getText().toString();

                ChiTietPhong chiTietPhong = new ChiTietPhong("", ten, gia, dc, mot, hinhPhong, user);
                //Thêm phòng
                phongDao.insertPhong(chiTietPhong);
                dialog.dismiss();
            }
        });

        //Button nhập lại
        btnNhapLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tenPhong.setText("");
                giaPhong.setText("");
                diaChi.setText("");
                moTa.setText("");
            }
        });

        dialog.show();
    }
}
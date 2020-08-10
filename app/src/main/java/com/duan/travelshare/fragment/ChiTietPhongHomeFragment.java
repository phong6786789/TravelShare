package com.duan.travelshare.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.duan.travelshare.MainActivity;
import com.duan.travelshare.R;
import com.duan.travelshare.model.ChiTietChuChoThue;
import com.duan.travelshare.model.ChiTietKH;
import com.duan.travelshare.model.ChiTietPhong;
import com.duan.travelshare.model.FullUser;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

public class ChiTietPhongHomeFragment extends Fragment {
    final int SEND_SMS_PERMISSION_REQUEST_CODE = 111;
    private  Button sendSMS;
    private static final int REQUEST_CALL = 1;
    private ImageView phong, user, save, call, messenger;
    private LinearLayout star;
    private TextView tenPhong, giaPhong, tenUser, emailUser, moTa;
    private Button xem, datPhong;

    public ChiTietPhongHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chi_tiet_phong, container, false);
        //Ẩn navigation
        MainActivity.navigation.setVisibility(View.GONE);

        //Nhạn object
        Bundle bundle = getArguments();
        final ChiTietPhong chiTietPhong = (ChiTietPhong) bundle.getSerializable("list");
        //Khai báo
        phong = view.findViewById(R.id.ivPhong);
        user = view.findViewById(R.id.ivUser);
        save = view.findViewById(R.id.ivSave);
        call = view.findViewById(R.id.ivCall);
        messenger = view.findViewById(R.id.ivMessenger);
        star = view.findViewById(R.id.lnStar);
        tenPhong = view.findViewById(R.id.tvTenPhong);
        giaPhong = view.findViewById(R.id.tvGiaphong);
        tenUser = view.findViewById(R.id.tvUser);
        emailUser = view.findViewById(R.id.tvEmailP);
        moTa = view.findViewById(R.id.tvMota);
        xem = view.findViewById(R.id.btnXemUser);
        datPhong = view.findViewById(R.id.btnDatPhongChiTiet);

        if (!chiTietPhong.getImgPhong().get(0).isEmpty()) {
            Picasso.with(getActivity()).load(chiTietPhong.getImgPhong().get(0)).into(phong);
        } else {
            phong.setImageResource(R.drawable.phongtro);
        }
        if (!chiTietPhong.getFullUser().getLinkImage().isEmpty()) {
            Picasso.with(getActivity()).load(chiTietPhong.getFullUser().getLinkImage()).into(user);
        } else {
            user.setImageResource(R.drawable.userimg);
        }

        tenPhong.setText(chiTietPhong.getTenPhong());
        giaPhong.setText(chiTietPhong.getGiaPhong());
        tenUser.setText(chiTietPhong.getFullUser().getUserName());
        emailUser.setText(chiTietPhong.getFullUser().getEmailUser());
        moTa.setText(chiTietPhong.getMoTaPhong());
        //save, call, messenger, star , xem, datPhong;
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog=new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_callphone);
                dialog.setCancelable(true);
                final TextView phone=dialog.findViewById(R.id.number);
                phone.setText(chiTietPhong.getFullUser().getPhoneUser());
                dialog.findViewById(R.id.goi).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        makePhoneCall();
                    }
                });
                dialog.findViewById(R.id.huy).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        messenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final  Dialog dialog1=new Dialog(getActivity());
                dialog1.setContentView(R.layout.send_sms);
                dialog1.setCancelable(true);
               final EditText sms=dialog1.findViewById(R.id.edtsms);
                final TextView phone1=dialog1.findViewById(R.id.so);
                phone1.setText(chiTietPhong.getFullUser().getPhoneUser());

//
                dialog1.findViewById(R.id.sendsms).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(checkPermission(Manifest.permission.SEND_SMS)) {
                }else {
                    ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.SEND_SMS},
                            SEND_SMS_PERMISSION_REQUEST_CODE);
                }
                        String mesage=sms.getText().toString();
                        String phoneNo=phone1.getText().toString();
                        if(!TextUtils.isEmpty(mesage) && !TextUtils.isEmpty(phoneNo)) {
                            if(checkPermission(Manifest.permission.SEND_SMS)) {
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(phoneNo, null, mesage, null, null);
                            }else {
                                Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                });
                dialog1.findViewById(R.id.huysms).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog1.dismiss();
                    }
                });
                dialog1.show();
            }
        });
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        xem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        datPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //Khi ấn nút back
        //Toolbar
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.tbTitle);
        ImageView back = toolbar.findViewById(R.id.tbBack);
        title.setText("CHI TIẾT PHÒNG");
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.navigation.setVisibility(View.VISIBLE);
                HomeFragment userFragment = new HomeFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, userFragment)
                        .commit();
            }
        });
        return view;
    }
    private void makePhoneCall() {
        Bundle bundle = getArguments();
        final ChiTietPhong chiTietPhong = (ChiTietPhong) bundle.getSerializable("list");
        final Dialog dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_callphone);
        dialog.setCancelable(true);
        final TextView phone=dialog.findViewById(R.id.number);
        phone.setText(chiTietPhong.getFullUser().getPhoneUser());
        String number =phone.getText().toString();
        if (number.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        } else {
            Toast.makeText(getActivity(), "Enter Phone Number", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(getActivity(), "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
        switch (requestCode) {

            case SEND_SMS_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    final  Dialog dialog1=new Dialog(getActivity());
                    dialog1.setContentView(R.layout.send_sms);
                    dialog1.setCancelable(true);
                }
                return;
            }
        }
    }
    private boolean checkPermission(String permission) {
        int checkPermission = ContextCompat.checkSelfPermission(getActivity(), permission);
        return (checkPermission == PackageManager.PERMISSION_GRANTED);
    }

}
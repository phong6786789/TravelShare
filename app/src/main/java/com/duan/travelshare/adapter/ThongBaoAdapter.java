package com.duan.travelshare.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duan.travelshare.MainActivity;
import com.duan.travelshare.R;
import com.duan.travelshare.fragment.GiaoDichFragment;
import com.duan.travelshare.fragment.ThongBaoFragment;
import com.duan.travelshare.model.ChiTietPhong;
import com.duan.travelshare.model.FullUser;
import com.duan.travelshare.model.GiaoDich;
import com.duan.travelshare.model.ThongBao;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ThongBaoAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {
    List<ThongBao> list;
    List<ThongBao> listSort;
    GiaoDich giaoDich;
    String uID;
    FullUser fullUserChu;
    FullUser fullUserKhach;
    ChiTietPhong chiTietPhong;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReferenceTB = firebaseDatabase.getReference("ThongBao");
    DatabaseReference databaseReferencePhong = firebaseDatabase.getReference("Phong");
    DatabaseReference databaseReferenceFullUser = firebaseDatabase.getReference("FullUser");
    DatabaseReference databaseReferenceGD = firebaseDatabase.getReference("GiaoDich");
    private FirebaseAuth mAuth;
    Locale localeVN = new Locale("vi", "VN");
    NumberFormat fm = NumberFormat.getCurrencyInstance(localeVN);
    ProgressDialog processDiaglog;
    View mView;
    Context mContext;

    public ThongBaoAdapter(View view) {
        super(view);
        mView = view;
        mContext = view.getContext();
        this.list = list;
        this.listSort = list;
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            uID = mAuth.getCurrentUser().getUid();
        }
        processDiaglog = new ProgressDialog(mContext);
        view.setOnClickListener(this);
    }

    public void bindThongBao(ThongBao thongBao) {
        final TextView ten, trangThai, gio, ngay;
        final CardView cv;

        ten = mView.findViewById(R.id.nameTB);
        trangThai = mView.findViewById(R.id.chitietTB);
        gio = mView.findViewById(R.id.timeTB);
        ngay = mView.findViewById(R.id.dateTB);
        cv = mView.findViewById(R.id.cvOneTB);

        //Set dữ liệu
        final String idChu = thongBao.getIdChu();
        final String idKhach = thongBao.getIdUser();
        final String idGD = thongBao.getIdGG();
        final String idPhong = thongBao.getIdPhong();
        switch (thongBao.getTrangThai()) {
            case "0":
                trangThai.setText("CHỜ XÁC NHẬN");
                break;
            case "1":
                trangThai.setText("ĐÃ XÁC NHẬN");
                break;
            case "2":
                trangThai.setText("ĐÃ HỦY");
                break;
        }

        databaseReferenceFullUser.child(idKhach).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fullUserKhach = snapshot.getValue(FullUser.class);

                if (uID.equalsIgnoreCase(idKhach)) {
                    ten.setText("ĐẶT PHÒNG THÀNH CÔNG!");
                } else {
                    databaseReferenceFullUser.child(idChu).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            fullUserChu = snapshot.getValue(FullUser.class);
                            if (!uID.equalsIgnoreCase(idChu)) {
                                cv.setVisibility(View.GONE);
                            } else {
                                ten.setText("Bạn có một đơn hàng mới từ tài khoản " + fullUserKhach.getUserName());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Get toàn bộ giao dịch
        databaseReferenceGD.child(idGD).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                giaoDich = snapshot.getValue(GiaoDich.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        gio.setText(thongBao.getThoiGian());
        ngay.setText(thongBao.getNgay());

    }

    @Override
    public void onClick(View view) {
        processDiaglog.show();
        final int position = getLayoutPosition();

        final ArrayList<ThongBao> list = new ArrayList<>();

        databaseReferenceTB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    ThongBao thongBao = postSnapshot.getValue(ThongBao.class);
                    if (uID.equalsIgnoreCase(thongBao.getIdChu()) || uID.equalsIgnoreCase(thongBao.getIdUser())) {
                        list.add(thongBao);
                    }
                    final ThongBao listP = list.get(position);

                    final Dialog dialog = new Dialog(mContext);
                    dialog.setContentView(R.layout.giaodich);
                    dialog.setCancelable(true);
                    Window window = dialog.getWindow();
                    window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    final ImageView phong;
                    final TextView tenP, giaP, hten, cmnd, tu, den, ghichu, trangThai;
                    final LinearLayout erro, lnButton;
                    final Button ok, huy, dong;

                    phong = dialog.findViewById(R.id.ivimgPhongGG);
                    tenP = dialog.findViewById(R.id.tvTenPhongGG);
                    giaP = dialog.findViewById(R.id.tvGiaphongGG);
                    trangThai = dialog.findViewById(R.id.tvTrangThai);
                    hten = dialog.findViewById(R.id.tvHoTenGG);
                    cmnd = dialog.findViewById(R.id.tvCmndGG);
                    tu = dialog.findViewById(R.id.tvTuNgayGG);
                    den = dialog.findViewById(R.id.tvDenNgayGG);
                    ghichu = dialog.findViewById(R.id.tvGhiChuGG);
                    erro = dialog.findViewById(R.id.lnErro);
                    lnButton = dialog.findViewById(R.id.lnXacNhan);
                    ok = dialog.findViewById(R.id.btnOkGG);
                    huy = dialog.findViewById(R.id.btnCancleGG);
                    dong = dialog.findViewById(R.id.btnDongGD);
                    if (uID.matches(listP.getIdUser())) {
                        dong.setVisibility(View.VISIBLE);
                        ok.setVisibility(View.GONE);
                        huy.setVisibility(View.GONE);
                    }

                    databaseReferencePhong.child(listP.getIdPhong()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            chiTietPhong = snapshot.getValue(ChiTietPhong.class);
                            if (!chiTietPhong.getImgPhong().isEmpty()) {
                                Picasso.with(mContext).load(chiTietPhong.getImgPhong().get(0)).into(phong);
                            }
                            tenP.setText(chiTietPhong.getTenPhong());
                            giaP.setText(fm.format(Integer.parseInt(chiTietPhong.getGiaPhong())) + "/ngày");
                            hten.setText(giaoDich.getHoTen());
                            cmnd.setText(giaoDich.getCmnd());
                            tu.setText(giaoDich.getTuTime() + " " + giaoDich.getTuNgay());
                            den.setText(giaoDich.getDenTime() + " " + giaoDich.getDenNgay());
                            ghichu.setText(giaoDich.getGhiChu());
                            switch (giaoDich.getTrangThai()) {
                                case "0":
                                    ok.setVisibility(View.VISIBLE);
                                    huy.setVisibility(View.VISIBLE);
                                    trangThai.setText("CHỜ XÁC NHẬN");
                                    break;
                                case "1":
                                    dong.setVisibility(View.VISIBLE);
                                    ok.setVisibility(View.GONE);
                                    huy.setVisibility(View.GONE);
                                    trangThai.setText("ĐÃ XÁC NHẬN");
                                    break;
                                case "2":
                                    dong.setVisibility(View.VISIBLE);
                                    ok.setVisibility(View.GONE);
                                    huy.setVisibility(View.GONE);
                                    trangThai.setText("ĐÃ HỦY");
                                    break;
                            }
                            erro.setVisibility(View.GONE);
                            processDiaglog.dismiss();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            giaoDich.setTrangThai("1");
                            databaseReferenceTB.child(giaoDich.getIdGD()).child("trangThai").setValue("1");
                            databaseReferenceGD.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    databaseReferenceGD.child(listP.getIdGG()).setValue(giaoDich);
                                    trangThai.setText("ĐÃ XÁC NHẬN");
                                    dong.setVisibility(View.VISIBLE);
                                    ok.setVisibility(View.GONE);
                                    huy.setVisibility(View.GONE);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    });

                    huy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            processDiaglog.show();
                            processDiaglog.setMessage("Đang xác nhận...");
                            giaoDich.setTrangThai("2");

                            databaseReferenceGD.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    databaseReferenceGD.child(listP.getIdGG()).setValue(giaoDich);
                                    databaseReferenceTB.child(giaoDich.getIdGD()).child("trangThai").setValue("2");
                                    trangThai.setText("ĐÃ HỦY");
                                    dong.setVisibility(View.VISIBLE);
                                    ok.setVisibility(View.GONE);
                                    huy.setVisibility(View.GONE);
                                    processDiaglog.dismiss();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }
                    });

                    dong.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                           MainActivity.navigation.setSelectedItemId(R.id.thongbao);
                           dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

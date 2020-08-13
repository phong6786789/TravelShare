package com.duan.travelshare.adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duan.travelshare.R;
import com.duan.travelshare.firebasedao.GiaoDichDao;
import com.duan.travelshare.firebasedao.PhongDao;
import com.duan.travelshare.firebasedao.ThongBaoDao;
import com.duan.travelshare.model.GiaoDich;
import com.duan.travelshare.model.ThongBao;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ThongBaoAdapter extends RecyclerView.Adapter<ThongBaoAdapter.ViewHolder> {
    List<ThongBao> list;
    List<ThongBao> listSort;
    GiaoDichDao giaoDichDao;
    Context context;
    PhongDao phongDao;
    ThongBaoDao thongBaoDao;

    public ThongBaoAdapter(List<ThongBao> list, Context context) {
        this.list = list;
        this.context = context;
        phongDao = new PhongDao(context);
        giaoDichDao = new GiaoDichDao(context);
        this.listSort = list;
        thongBaoDao = new ThongBaoDao(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.one_thongbao, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThongBao thongBao = list.get(position);

        holder.ten.setText("Bạn có một đơn hàng mới từ tài khoản " + thongBao.getGiaoDich().getFullUser().getUserName());
        switch (thongBao.getGiaoDich().getTrangThai()) {
            case 0:
                holder.trangThai.setText("Trạng thái: CHỜ XÁC NHẬN");
                break;
            case 1:
                holder.trangThai.setText("Trạng thái: ĐÃ XÁC NHẬN");
                break;
            case 2:
                holder.trangThai.setText("Trạng thái: ĐÃ HỦY!");
                break;
        }
        holder.gio.setText(list.get(position).getThoiGian());
        holder.ngay.setText(list.get(position).getNgay());
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView ten, trangThai, gio, ngay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ten = itemView.findViewById(R.id.nameTB);
            trangThai = itemView.findViewById(R.id.chitietTB);
            gio = itemView.findViewById(R.id.timeTB);
            ngay = itemView.findViewById(R.id.dateTB);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getLayoutPosition();
            final GiaoDich listP = list.get(position).getGiaoDich();
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.giaodich);
            dialog.setCancelable(true);
            Window window = dialog.getWindow();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            ImageView phong;
            TextView tenP, giaP, hten, cmnd, tu, den, ghichu, trangThai;
            LinearLayout erro, lnButton;
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

            if (!listP.getChiTietPhong().getImgPhong().isEmpty()) {
                Picasso.with(context).load(listP.getChiTietPhong().getImgPhong().get(0)).into(phong);
            }
            tenP.setText(listP.getChiTietPhong().getTenPhong());
            giaP.setText(listP.getChiTietPhong().getGiaPhong());
            hten.setText(listP.getHoTen());
            cmnd.setText(listP.getCmnd());
            tu.setText(listP.getTuTime() + " " + listP.getTuNgay());
            den.setText(listP.getDenTime() + " " + listP.getDenNgay());
            ghichu.setText(listP.getGhiChu());
            switch (listP.getTrangThai()) {
                case 0:
                    ok.setVisibility(View.VISIBLE);
                    huy.setVisibility(View.VISIBLE);
                    trangThai.setText("CHỜ XÁC NHẬN");
                    break;
                case 1:
                    dong.setVisibility(View.VISIBLE);
                    ok.setVisibility(View.GONE);
                    huy.setVisibility(View.GONE);
                    trangThai.setText("ĐÃ XÁC NHẬN");
                    break;
                case 2:
                    dong.setVisibility(View.VISIBLE);
                    ok.setVisibility(View.GONE);
                    huy.setVisibility(View.GONE);
                    trangThai.setText("ĐÃ HỦY");
                    break;
            }
            erro.setVisibility(View.GONE);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    thongBaoDao.updateTT(listP.getChiTietPhong().getIdPhong(), 1);
                    listP.setTrangThai(1);
                    giaoDichDao.updateTrangThai(listP);
                    dong.setVisibility(View.VISIBLE);
                    ok.setVisibility(View.GONE);
                    huy.setVisibility(View.GONE);
                }

            });

            huy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    thongBaoDao.updateTT(listP.getChiTietPhong().getIdPhong(), 2);
                    listP.setTrangThai(2);
                    giaoDichDao.updateTrangThai(listP);
                    dong.setVisibility(View.VISIBLE);
                    ok.setVisibility(View.GONE);
                    huy.setVisibility(View.GONE);
                }
            });
            dong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }
}

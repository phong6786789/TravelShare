package com.duan.travelshare.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duan.travelshare.R;
import com.duan.travelshare.firebasedao.PhongDao;
import com.duan.travelshare.model.ChiTietPhong;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ChiTietPhongAdapter extends RecyclerView.Adapter<ChiTietPhongAdapter.ViewHolder> {
    List<ChiTietPhong> list;
    Context context;
    PhongDao phongDao;

    public ChiTietPhongAdapter(List<ChiTietPhong> list, Context context) {
        this.list = list;
        this.context = context;
        phongDao = new PhongDao(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.one_room, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ten.setText(list.get(position).getTenPhong());
        holder.gia.setText(list.get(position).getGiaPhong());
        holder.diachi.setText(list.get(position).getDiaChiPhong());
        Picasso.with(context).load(list.get(position).getImgPhong().getLinkHinh()).into(holder.img);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView ten, gia, diachi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgP);
            ten = itemView.findViewById(R.id.titleP);
            gia = itemView.findViewById(R.id.priceP);
            diachi = itemView.findViewById(R.id.addressP);
        }
    }
}

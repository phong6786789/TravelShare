package com.duan.travelshare.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.duan.travelshare.R;
import com.duan.travelshare.firebasedao.PhongDao;
import com.duan.travelshare.model.ChiTietPhong;

import java.util.List;

public class PhongAdapter extends RecyclerView.Adapter {
    List<ChiTietPhong> list;
    Context context;
    PhongDao phongDao;
    public PhongAdapter(List<ChiTietPhong> list,Context context){
    this.list=list;
    this.context=context;
    phongDao=new PhongDao(context);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.one_room,parent,false);
        phongDao=new PhongDao(context);
        return new PhongAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public  class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imgPhong;
        public TextView tenPhong,giaPhong, diachiPhong;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tenPhong=itemView.findViewById(R.id.titleP);
            giaPhong=itemView.findViewById(R.id.priceP);
            diachiPhong=itemView.findViewById(R.id.addressP);
            imgPhong=itemView.findViewById(R.id.imgP);
        }
    }
}

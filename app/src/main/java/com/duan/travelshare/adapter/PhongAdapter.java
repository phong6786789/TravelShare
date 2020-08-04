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
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhongAdapter extends RecyclerView.Adapter<PhongAdapter.ViewHolder> {
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.one_room,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tenPhong.setText(list.get(position).getTenPhong());
        holder.giaPhong.setText(list.get(position).getGiaPhong());
        holder.diachiPhong.setText(list.get(position).getDiaChiPhong());
        Picasso.with(context).load(list.get(position).getImgPhong().getLinkHinh()).into(holder.imgPhong);
//        holder.imgPhong.setImageResource(R.drawable.phongtro);
//
    }
    @Override
    public int getItemCount() {

        return list.size() ;
    }
    public  class ViewHolder extends RecyclerView.ViewHolder{
         ImageView imgPhong;
         TextView tenPhong,giaPhong, diachiPhong;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tenPhong=itemView.findViewById(R.id.titleP);
            giaPhong=itemView.findViewById(R.id.priceP);
            diachiPhong=itemView.findViewById(R.id.addressP);
            imgPhong=itemView.findViewById(R.id.imgP);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });
        }
    }
}

package com.duan.travelshare.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.duan.travelshare.R;
import com.duan.travelshare.firebasedao.PhongDao;
import com.duan.travelshare.model.ChiTietPhong;
import com.duan.travelshare.model.HinhPhong;
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

//
    }
    @Override
    public int getItemCount() {

        return list.size() ;
    }
    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
         ImageView imgPhong;
         TextView tenPhong,giaPhong, diachiPhong;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tenPhong=itemView.findViewById(R.id.titleP);
            giaPhong=itemView.findViewById(R.id.priceP);
            diachiPhong=itemView.findViewById(R.id.addressP);
            imgPhong=itemView.findViewById(R.id.imgP);
        }

        @Override
        public void onClick(View view) {
            int position = getLayoutPosition();
            Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
            ChiTietPhong ctPhong = list.get(position);
            HinhPhong hinhPhong = ctPhong.getImgPhong();
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.chitietphong);
            dialog.setCancelable(true);
            Window window = dialog.getWindow();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (dialog != null && dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            ImageView ivHinhPhong, ivHinhUser;
            TextView tenPhong, giaPhong, tenUser, gmailUser;
            Button btnDatPhong;
            ivHinhPhong = dialog.findViewById(R.id.ivChiTietPhongHinhPhong);
            ivHinhUser = dialog.findViewById(R.id.ivChiTietPhongHinhUser);
            tenPhong = dialog.findViewById(R.id.tvChiTietPhongTenPhong);
            giaPhong = dialog.findViewById(R.id.tvChiTietPhongGiaPhong);
            tenUser = dialog.findViewById(R.id.tvChiTietPhongTenUser);
            gmailUser = dialog.findViewById(R.id.tvChiTietPhongGmailUser);
            btnDatPhong = dialog.findViewById(R.id.btnChiTietPhongDatPhong);

            Picasso.with(context).load(hinhPhong.getLinkHinh()).into(ivHinhPhong);
            Picasso.with(context).load(ctPhong.getFullUser().getLinkImage()).into(ivHinhUser);
            tenPhong.setText(ctPhong.getTenPhong());
            giaPhong.setText(ctPhong.getGiaPhong());
            tenUser.setText(ctPhong.getFullUser().getUserName());
            gmailUser.setText(ctPhong.getFullUser().getEmailUser());

            //Dat phong trong Chi Tiet Phong
            btnDatPhong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Dat Phong", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}

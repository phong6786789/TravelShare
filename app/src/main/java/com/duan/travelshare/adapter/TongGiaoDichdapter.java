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
import com.duan.travelshare.model.GiaoDich;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TongGiaoDichdapter extends RecyclerView.Adapter<TongGiaoDichdapter.ViewHolder> {
    List<GiaoDich> list;
    Context context;
    PhongDao phongDao;

    public TongGiaoDichdapter(List<GiaoDich> list, Context context) {
        this.list = list;
        this.context = context;
        phongDao = new PhongDao(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.one_danggiaodich, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChiTietPhong chiTietPhong = list.get(position).getChiTietPhong();
        GiaoDich giaoDich = list.get(position);

        holder.tenPhong.setText(chiTietPhong.getTenPhong());
        holder.giaPhong.setText(chiTietPhong.getGiaPhong());
        holder.diachiPhong.setText(chiTietPhong.getDiaChiPhong());
        if (chiTietPhong.getImgPhong().size() != 0) {
            Picasso.with(context).load(chiTietPhong.getImgPhong().get(0)).into(holder.imgPhong);
        }
        switch (giaoDich.getTrangThai()){
            case 0:
                holder.trangThai.setText("ĐANG XÁC NHẬN");
                break;
            case 1:
                holder.trangThai.setText("ĐÃ XÁC NHẬN");
                break;
            case 2:
                holder.trangThai.setText("ĐÃ HỦY");
                break;
        }

    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgPhong;
        TextView tenPhong, giaPhong, diachiPhong, trangThai;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tenPhong = itemView.findViewById(R.id.titleP);
            giaPhong = itemView.findViewById(R.id.priceP);
            diachiPhong = itemView.findViewById(R.id.addressP);
            imgPhong = itemView.findViewById(R.id.imgP);
            trangThai = itemView.findViewById(R.id.tvTrangThaiGG);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getLayoutPosition();
            GiaoDich listP = list.get(position);




        }
    }

}

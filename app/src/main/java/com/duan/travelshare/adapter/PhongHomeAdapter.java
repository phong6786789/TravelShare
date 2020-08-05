package com.duan.travelshare.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duan.travelshare.R;
import com.duan.travelshare.firebasedao.PhongDao;
import com.duan.travelshare.fragment.ChiTietPhongFragment;
import com.duan.travelshare.model.ChiTietPhong;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhongHomeAdapter extends RecyclerView.Adapter<PhongHomeAdapter.ViewHolder> {
    List<ChiTietPhong> list;
    Context context;
    PhongDao phongDao;

    public PhongHomeAdapter(List<ChiTietPhong> list, Context context) {
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
        holder.tenPhong.setText(list.get(position).getTenPhong());
        holder.giaPhong.setText(list.get(position).getGiaPhong());
        holder.diachiPhong.setText(list.get(position).getDiaChiPhong());
        if (list.get(position).getImgPhong().size() != 0) {
            Picasso.with(context).load(list.get(position).getImgPhong().get(0)).into(holder.imgPhong);
        }

    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgPhong;
        TextView tenPhong, giaPhong, diachiPhong;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tenPhong = itemView.findViewById(R.id.titleP);
            giaPhong = itemView.findViewById(R.id.priceP);
            diachiPhong = itemView.findViewById(R.id.addressP);
            imgPhong = itemView.findViewById(R.id.imgP);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getLayoutPosition();
            ChiTietPhong listP = list.get(position);

            ChiTietPhongFragment chiTietPhong = new ChiTietPhongFragment();

            Bundle bundle = new Bundle();
            bundle.putSerializable("list", listP);
            chiTietPhong.setArguments(bundle);

            FragmentManager fragmentManager = ((AppCompatActivity) view.getContext()).getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame, chiTietPhong)
                    .commit();
        }
    }
}

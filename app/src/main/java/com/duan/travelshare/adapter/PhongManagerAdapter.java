package com.duan.travelshare.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duan.travelshare.R;
import com.duan.travelshare.firebasedao.PhongDao;
import com.duan.travelshare.fragment.ChiTietPhongHomeFragment;
import com.duan.travelshare.fragment.ChiTietPhongManagerFragment;
import com.duan.travelshare.model.ChiTietPhong;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PhongManagerAdapter extends RecyclerView.Adapter<PhongManagerAdapter.ViewHolder> {
    List<ChiTietPhong> list;
    Context context;
    PhongDao phongDao;
    List<ChiTietPhong> listSort;
    Filter filter;

    public PhongManagerAdapter(List<ChiTietPhong> list, Context context) {
        this.list = list;
        this.context = context;
        phongDao = new PhongDao(context);
        this.listSort = list;
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
        if (list.get(position).getImgPhong().size() != 0) {
            Picasso.with(context).load(list.get(position).getImgPhong().get(0)).into(holder.img);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView img;
        public TextView ten, gia, diachi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgP);
            ten = itemView.findViewById(R.id.titleP);
            gia = itemView.findViewById(R.id.priceP);
            diachi = itemView.findViewById(R.id.addressP);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getLayoutPosition();
            ChiTietPhong listP = list.get(position);

            ChiTietPhongManagerFragment chiTietPhong = new ChiTietPhongManagerFragment();

            Bundle bundle = new Bundle();
            bundle.putSerializable("list", listP);
            chiTietPhong.setArguments(bundle);

            FragmentManager fragmentManager = ((AppCompatActivity) view.getContext()).getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame, chiTietPhong)
                    .commit();

        }
    }

    public void resetData() {
        list = listSort;
    }

    public Filter getFilter() {
        if (filter == null)
            filter = new PhongManagerAdapter.CustomFilter();
        return filter;
    }

    private class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                results.values = listSort;
                results.count = listSort.size();
            } else {
                List<ChiTietPhong> lsChiTietPhong = new ArrayList<ChiTietPhong>();
                for (ChiTietPhong p : list) {
                    if
                    (p.getTenPhong().toUpperCase().contains(constraint.toString().toUpperCase()))
                        lsChiTietPhong.add(p);
                }
                results.values = lsChiTietPhong;
                results.count = lsChiTietPhong.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count == 0)
                notifyDataSetChanged();
            else {
                list = (List<ChiTietPhong>) results.values;
                notifyDataSetChanged();
            }
        }
    }
}

package com.duan.travelshare.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duan.travelshare.R;
import com.duan.travelshare.firebasedao.PhongDao;
import com.duan.travelshare.model.ThongBao;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ThongBaoAdapter extends RecyclerView.Adapter<ThongBaoAdapter.ViewHolder> {
    List<ThongBao> list;
    List<ThongBao> listSort;
//    Filter filter;
    Context context;
    PhongDao phongDao;

    public ThongBaoAdapter(List<ThongBao> list, Context context) {
        this.list = list;
        this.context = context;
        phongDao = new PhongDao(context);
        this.listSort = list;
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

        holder.ten.setText("GIAO DỊCH THÀNH CÔNG");
        switch (thongBao.getGiaoDich().getTrangThai()) {
            case 0:
                holder.trangThai.setText("Đang chờ xác nhận");
                break;
            case 1:
                holder.trangThai.setText("Đã xác nhận giao dịch");
                break;
            case 2:
                holder.trangThai.setText("Giao dịch đã bị hủy!");
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
            ThongBao listP = list.get(position);

            Toast.makeText(context, ""+listP.getGiaoDich().getChiTietPhong().getTenPhong(), Toast.LENGTH_SHORT).show();

//            ThongBaoHomeFragment chiTietPhong = new ThongBao()HomeFragment();
//
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("list", listP);
//            chiTietPhong.setArguments(bundle);
//
//            FragmentManager fragmentManager = ((AppCompatActivity) view.getContext()).getSupportFragmentManager();
//            fragmentManager.beginTransaction()
//                    .replace(R.id.frame, chiTietPhong)
//                    .commit();
        }
    }

    public void resetData() {
        list = listSort;
    }

//    public Filter getFilter() {
//        if (filter == null)
//            filter = new CustomFilter();
//        return filter;
//    }
//
//    private class CustomFilter extends Filter {
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            FilterResults results = new FilterResults();
//            // We implement here the filter logic
//            if (constraint == null || constraint.length() == 0) {
//                results.values = listSort;
//                results.count = listSort.size();
//            } else {
//                List<ThongBao> lsThongBao = new ArrayList<ThongBao>();
//                for (ThongBao p : list) {
//                    if
//                    (p.getTenPhong().toUpperCase().contains(constraint.toString().toUpperCase()))
//                        ThongBao.add(p);
//                }
//                results.values = ThongBao;
//                results.count = ThongBao.size();
//            }
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            if (results.count == 0)
//                notifyDataSetChanged();
//            else {
//                list = (List<ThongBao>) results.values;
//                notifyDataSetChanged();
//            }
//        }
//    }
}

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
import com.duan.travelshare.firebasedao.SaveDao;
import com.duan.travelshare.fragment.ChiTietPhongHomeFragment;
import com.duan.travelshare.model.Save;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SaveAdapter extends RecyclerView.Adapter<SaveAdapter.ViewHolder> {
    List<Save> list;
    List<Save> listSort;
    Filter filter;
    Context context;
    SaveDao SaveDao;

    public SaveAdapter(List<Save> list, Context context) {
        this.list = list;
        this.context = context;
        this.SaveDao = new SaveDao(context);
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
        holder.tenPhong.setText(list.get(position).getChiTietPhong().getTenPhong());
        holder.giaPhong.setText(list.get(position).getChiTietPhong().getGiaPhong());
        holder.diachiPhong.setText(list.get(position).getChiTietPhong().getDiaChiPhong());
        if (list.get(position).getChiTietPhong().getImgPhong().size() != 0) {
            Picasso.with(context).load(list.get(position).getChiTietPhong().getImgPhong().get(0)).into(holder.imgPhong);
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
            Save listP = list.get(position);

            ChiTietPhongHomeFragment Save = new ChiTietPhongHomeFragment();

            Bundle bundle = new Bundle();
            bundle.putSerializable("list", listP.getChiTietPhong());
            Save.setArguments(bundle);

            FragmentManager fragmentManager = ((AppCompatActivity) view.getContext()).getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame, Save)
                    .commit();
        }
    }

    public void resetData() {
        list = listSort;
    }

    public Filter getFilter() {
        if (filter == null)
            filter = new CustomFilter();
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
                List<Save> save = new ArrayList<Save>();
                for (Save p : list) {
                    if
                    (p.getChiTietPhong().getTenPhong().toUpperCase().contains(constraint.toString().toUpperCase()))
                        save.add(p);
                }
                results.values = save;
                results.count = save.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count == 0)
                notifyDataSetChanged();
            else {
                list = (List<Save>) results.values;
                notifyDataSetChanged();
            }
        }
    }
}

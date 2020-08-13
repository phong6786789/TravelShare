package com.duan.travelshare.firebasedao;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.duan.travelshare.adapter.ThongBaoAdapter;
import com.duan.travelshare.fragment.ShowDialog;
import com.duan.travelshare.fragment.ThongBaoFragment;
import com.duan.travelshare.model.ThongBao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class ThongBaoDao {
    Context context;
    DatabaseReference reference;
    String key = "";
    Boolean check;
    ShowDialog showDialog;

    public ThongBaoDao() {
    }

    public ThongBaoDao(Context context) {
        this.context = context;
        reference = FirebaseDatabase.getInstance().getReference("ThongBao");
        showDialog = new ShowDialog((Activity) context);
    }

    //Lấy toàn bộ phòng
    public ArrayList<ThongBao> getAll(final String email) {
        final ArrayList<ThongBao> list = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        list.clear();
                        Iterable<DataSnapshot> dataSnapshotIterable = dataSnapshot.getChildren();
                        Iterator<DataSnapshot> iterator = dataSnapshotIterable.iterator();
                        while (iterator.hasNext()) {
                            DataSnapshot next = (DataSnapshot) iterator.next();
                            ThongBao nd = next.getValue(ThongBao.class);
                            list.add(nd);
//                            Thông báo cho chủ thuê biết đang có đơn hàng cần xác nhận
                            if (email.equalsIgnoreCase(nd.getGiaoDich().getChiTietPhong().getFullUser().getEmailUser()) ||
                                    email.equalsIgnoreCase(nd.getGiaoDich().getFullUser().getEmailUser())) {
                                Log.i("TAG", "subi: 0+" + list.size());
                            }
                            //Khi đủ list sẽ notify
                            ThongBaoFragment.thongBaoAdapter.notifyDataSetChanged();
                        }
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return list;
    }


    //Thêm Phòng mới
    public void themTB(final ThongBao thongBao) {
        reference.push().setValue(thongBao).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()) {
//                    ManegerPhongThueFragment.datPhongAdapter.notifyDataSetChanged();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    //    Cập nhật Phòng
    public boolean updateTT(final String idPhong, final String id) {
        check = false;
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("giaoDich").child("chiTietPhong").child("idPhong").getValue(String.class).equalsIgnoreCase(idPhong)) {
                        key = data.getKey();
                        reference.child(key).child("giaoDich").child("trangThai").setValue(id);
                        check = true;
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                check = false;
            }
        });
        return check;
    }

}

package com.duan.travelshare.firebasedao;

import android.app.Activity;
import android.content.Context;

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
                try {
                    if (dataSnapshot.exists()) {
                        list.clear();
                        Iterable<DataSnapshot> dataSnapshotIterable = dataSnapshot.getChildren();
                        Iterator<DataSnapshot> iterator = dataSnapshotIterable.iterator();
                        while (iterator.hasNext()) {
                            DataSnapshot next = (DataSnapshot) iterator.next();
                            ThongBao nd = next.getValue(ThongBao.class);
//                            email.equalsIgnoreCase(nd.getGiaoDich().getChiTietPhong().getFullUser().getEmailUser())
                            if(email.equalsIgnoreCase(nd.getGiaoDich().getFullUser().getEmailUser())){
                                list.add(nd);
                            }
                        }
                        //Khi đủ list sẽ notify
                        ThongBaoFragment.thongBaoAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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

    //Cập nhật Phòng
//    public boolean updatePhong(final ThongBao giaoDich, final int id) {
//        check = false;
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot data : dataSnapshot.getChildren()) {
//                    if (data.child("chiTietPhong").child("idPhong").getValue(String.class).equalsIgnoreCase(giaoDich.getChiTietPhong().getIdPhong())) {
//                        key = data.getKey();
//                        reference.child(key).child("trangThai").setValue(id + "");
//                        check = true;
//                        break;
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                check = false;
//            }
//        });
//        return check;
//    }

}

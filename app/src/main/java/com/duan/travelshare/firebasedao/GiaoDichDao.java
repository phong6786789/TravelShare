//package com.duan.travelshare.firebasedao;
//
//import android.app.Activity;
//import android.content.Context;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//
//import com.duan.travelshare.fragment.GiaoDichFragment;
//import com.duan.travelshare.fragment.ShowDialog;
//import com.duan.travelshare.model.GiaoDich;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//
//public class GiaoDichDao {
//    Context context;
//    DatabaseReference reference;
//    String key = "";
//    Boolean check;
//    ShowDialog showDialog;
//
//    public GiaoDichDao() {
//    }
//
//    public GiaoDichDao(Context context) {
//        this.context = context;
//        reference = FirebaseDatabase.getInstance().getReference("GiaoDich");
//        showDialog = new ShowDialog((Activity) context);
//    }
//
//    //Lấy toàn bộ phòng
//    public ArrayList<GiaoDich> getAll() {
//        final ArrayList<GiaoDich> list = new ArrayList<>();
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                try {
//                    if (dataSnapshot.exists()) {
//                        list.clear();
//                        Iterable<DataSnapshot> dataSnapshotIterable = dataSnapshot.getChildren();
//                        Iterator<DataSnapshot> iterator = dataSnapshotIterable.iterator();
//                        while (iterator.hasNext()) {
//                            DataSnapshot next = (DataSnapshot) iterator.next();
//                            GiaoDich nd = next.getValue(GiaoDich.class);
//                            list.add(nd);
//                            GiaoDichFragment.locGiaoDich();
//                        }
//                        //Khi đủ list sẽ notify
//
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });
//        return list;
//    }
//
//
//    //Thêm Phòng mới
//    public void insertPhong(final GiaoDich giaoDich) {
//        reference.push().setValue(giaoDich).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isComplete()) {
////                    ManegerPhongThueFragment.datPhongAdapter.notifyDataSetChanged();
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//            }
//        });
//    }
//
//    //Cập nhật Phòng
//    public void updatePhong(final GiaoDich giaoDich, final int id) {
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot data : dataSnapshot.getChildren()) {
////                    if (data.child("chiTietPhong").child("idPhong").getValue(String.class).equalsIgnoreCase(giaoDich.getChiTietPhong().getIdPhong())) {
////                        key = data.getKey();
////                        reference.child(key).child("trangThai").setValue(id + "");
////                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//    //Cập nhật Phòng
//    public void updateTrangThai(final GiaoDich giaoDich) {
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot data : dataSnapshot.getChildren()) {
//                    if (data.child("chiTietPhong").child("idPhong").getValue(String.class).equalsIgnoreCase(giaoDich.getChiTietPhong().getIdPhong())) {
//                        key = data.getKey();
//                        reference.child(key).setValue(giaoDich);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//}

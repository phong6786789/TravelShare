package com.duan.travelshare.firebasedao;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.duan.travelshare.fragment.ShowUserFragment;
import com.duan.travelshare.model.ChiTietPhong;
import com.duan.travelshare.model.FullUser;
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

public class PhongDao {
    Context context;
    DatabaseReference reference;
    String key = "";

    public PhongDao() {
    }

    public PhongDao(Context context) {
        this.context = context;
        reference = FirebaseDatabase.getInstance().getReference("Phong");
    }

    //Lấy toàn bộ tài khoản mật khẩu
    public ArrayList<ChiTietPhong> getAllPhong() {
        final ArrayList<ChiTietPhong> list = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    list.clear();
                    Iterable<DataSnapshot> dataSnapshotIterable = dataSnapshot.getChildren();
                    Iterator<DataSnapshot> iterator = dataSnapshotIterable.iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot next = (DataSnapshot) iterator.next();
                        ChiTietPhong nd = next.getValue(ChiTietPhong.class);
                        list.add(nd);
                        ShowUserFragment.setUser();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, "Lấy thông tin thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
        return list;
    }

    //Thêm Phòng mới
    public boolean insertPhong(FullUser user) {
        reference.push().setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()) {
                    return;
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return true;
    }

    //Cập nhật Phòng
    public void updatePhong(final FullUser fullUser) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("emailUser").getValue(String.class).equalsIgnoreCase(fullUser.getEmailUser())) {
                        key = data.getKey();
                        reference.child(key).setValue(fullUser);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

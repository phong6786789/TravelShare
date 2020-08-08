package com.duan.travelshare.firebasedao;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.duan.travelshare.MainActivity;
import com.duan.travelshare.fragment.ShowDialog;
import com.duan.travelshare.fragment.ShowUserFragment;
import com.duan.travelshare.fragment.UserFragment;
import com.duan.travelshare.login.Login;
import com.duan.travelshare.model.User;
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

public class UserDao {
    Context context;
    DatabaseReference reference;
    String key = "";
    Login login;
    ShowDialog showDialog;

    public UserDao() {
    }

    public UserDao(Context context) {
        this.context = context;
        reference = FirebaseDatabase.getInstance().getReference("User");
        showDialog = new ShowDialog((Activity) context);
    }

    //Lấy toàn bộ tài khoản mật khẩu
    public ArrayList<User> getAll() {
        final ArrayList<User> list = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    list.clear();
                    Iterable<DataSnapshot> dataSnapshotIterable = dataSnapshot.getChildren();
                    Iterator<DataSnapshot> iterator = dataSnapshotIterable.iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot next = (DataSnapshot) iterator.next();
                        User nd = next.getValue(User.class);
                        list.add(nd);
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


    //Lấy toàn bộ tài khoản mật khẩu
    public void getAllFilter(final String email) {
        final ArrayList<User> list = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    list.clear();
                    Iterable<DataSnapshot> dataSnapshotIterable = dataSnapshot.getChildren();
                    Iterator<DataSnapshot> iterator = dataSnapshotIterable.iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot next = (DataSnapshot) iterator.next();
                        User nd = next.getValue(User.class);
                        if (nd.getUserName().matches(email)) {
                            UserFragment.users = nd;
                        }
                    }
                    UserFragment.filterUser();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, "Lấy thông tin thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Thêm User mới
    public boolean insert(User user) {
        reference.push().setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()) {
                    showDialog.show("Đăng ký thành công!");
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

    //Đổi loại user sang người cho thuê
    public void changeLoaiUser(final String user, final String loaiUser) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("userName").getValue(String.class).equalsIgnoreCase(user)) {
                        key = data.getKey();
                        reference.child(key).child("loaiUser").setValue(loaiUser);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Đổi mật khẩu ngươi dùng
    public void changePass(final String userName, final String password) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("userName").getValue(String.class).equalsIgnoreCase(userName)) {
                        User u = data.getValue(User.class);
                        key = data.getKey();
                        reference.child(key).child("password").setValue(password);
                        showDialog.show("Đổi mật khẩu thành công!");

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}

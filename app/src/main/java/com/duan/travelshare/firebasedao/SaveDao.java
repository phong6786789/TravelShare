package com.duan.travelshare.firebasedao;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.duan.travelshare.MainActivity;
import com.duan.travelshare.fragment.ChiTietPhongHomeFragment;
import com.duan.travelshare.fragment.SaveFragment;
import com.duan.travelshare.fragment.ShowDialog;
import com.duan.travelshare.model.ChiTietPhong;
import com.duan.travelshare.model.Save;
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

public class SaveDao  {

        Context context;
        DatabaseReference reference;
        String key = "";
        Boolean check;
        ShowDialog showDialog;
        public  SaveDao(){}
        public SaveDao(Context context) {
            this.context = context;
            reference = FirebaseDatabase.getInstance().getReference("Save");
            showDialog = new ShowDialog((Activity) context);
        }

        //Lấy toàn bộ phòng
        public ArrayList<Save> getAllSave() {
            final ArrayList<Save> list = new ArrayList<>();
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        list.clear();
                        Iterable<DataSnapshot> dataSnapshotIterable = dataSnapshot.getChildren();
                        Iterator<DataSnapshot> iterator = dataSnapshotIterable.iterator();
                        while (iterator.hasNext()) {
                            DataSnapshot next = (DataSnapshot) iterator.next();
                            Save nd = next.getValue(Save.class);
                            if(MainActivity.email.equalsIgnoreCase(nd.getEmail())){
                                list.add(nd);
                            }
                        }
                        SaveFragment.saveAdapter.notifyDataSetChanged();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(context, "Lấy thông tin thất bại!", Toast.LENGTH_SHORT).show();
                }
            });
            return list;
        }


    //Lấy toàn bộ phòng
    public ArrayList<Save> checkSave() {
        final ArrayList<Save> list = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    list.clear();
                    Iterable<DataSnapshot> dataSnapshotIterable = dataSnapshot.getChildren();
                    Iterator<DataSnapshot> iterator = dataSnapshotIterable.iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot next = (DataSnapshot) iterator.next();
                        Save nd = next.getValue(Save.class);
                        if(MainActivity.email.equalsIgnoreCase(nd.getEmail())){
                            list.add(nd);
                        }
                    }
                    ChiTietPhongHomeFragment.setsave();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, "Lấy thông tin thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
        return list;
    }
        //Lấy toàn bộ phòng
        //Tự sinh key có sẵn trước
        public String creatKey() {
            return reference.push().getKey();
        }

        //Thêm Save mới
        public void insertSave(final Save save) {
            reference.push().setValue(save).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isComplete()) {
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }
        }


package com.duan.travelshare.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.duan.travelshare.MainActivity;
import com.duan.travelshare.R;
import com.duan.travelshare.firebasedao.FullUserDao;
import com.duan.travelshare.firebasedao.UserDao;
import com.duan.travelshare.model.FullUser;
import com.duan.travelshare.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserFragment extends Fragment {
    static CardView fullInfo, roomMng, roomFav, partnerRoom, changePass, logOut;
    static ImageView avatar;
    static TextView name, email;
    public static User users;
    ShowDialog show;
    UserDao userDao;
    String emailUser = MainActivity.emailUser;
    public static FullUser list;

    public UserFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        //Toolbar
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.tbTitle);
        ImageView back = toolbar.findViewById(R.id.tbBack);
        title.setText("CÁ NHÂN");
        back.setVisibility(View.INVISIBLE);

        show = new ShowDialog(getActivity());
        //Khai báo các id
        avatar = view.findViewById(R.id.imgAvatar);
        fullInfo = view.findViewById(R.id.cvUserInfo);
        roomFav = view.findViewById(R.id.cvRoomFavorite);
        roomMng = view.findViewById(R.id.cvRoomManager);
        partnerRoom = view.findViewById(R.id.cvPartner);
        changePass = view.findViewById(R.id.cvChangePass);
        logOut = view.findViewById(R.id.cvDangXuat);
        name = view.findViewById(R.id.tvNameUser);
        email = view.findViewById(R.id.tvEmail);

        getFullUser();

        //Đổ tài khoản vào list
        userDao = new UserDao(getActivity());
        userDao.getAllFilter(emailUser);


        //Khi chọn thông tin tài khoản
        fullInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowUserFragment showUserFragment = new ShowUserFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame, showUserFragment)
                        .commit();
            }
        });
        //Quản lý phòng thuê
        roomMng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ManegerPhongThueFragment manegerPhongThueFragment = new ManegerPhongThueFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame, manegerPhongThueFragment)
                        .commit();
            }
        });
        roomFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //Người dùng đăng ký làm chủ cho thuê 0(khách), 1(chủ), 2(admin)
        partnerRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.show2);
                dialog.setCancelable(true);
                Window window = dialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                if (dialog != null && dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
                TextView text = dialog.findViewById(R.id.tvInfo2);
                Button ok = dialog.findViewById(R.id.btnOK);
                Button cancle = dialog.findViewById(R.id.btnCancle);

                text.setText("Bạn có chắc chắn đăng ký làm chủ cho thuê?");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            if (MainActivity.userName.matches("0")) {
                                userDao.changeLoaiUser(MainActivity.email, "1");
                            } else {
                                userDao.changeLoaiUser(MainActivity.userName, "1");
                            }
                            filterUser();
                            show.show("Chúc mừng bạn đã trở thành chủ cho thuê!");
                            dialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        //Đổi mật khẩu người dùng
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (MainActivity.userName.matches("0")) {
                    show.show("Bạn đang dùng Google hoặc Facebook nên không thể đổi mật khẩu!");
                } else {
                    final Dialog dialog = new Dialog(getActivity());
                    dialog.setContentView(R.layout.change_pass);
                    dialog.setCancelable(true);
                    Window window = dialog.getWindow();
                    window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    if (dialog != null && dialog.getWindow() != null) {
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    }

                    final EditText pass, newPass, newPass2;
                    final Button changePass, cancle;
                    pass = dialog.findViewById(R.id.edtOldPassword);
                    newPass = dialog.findViewById(R.id.edtPasswordNew);
                    newPass2 = dialog.findViewById(R.id.edtPasswordNew2);
                    changePass = dialog.findViewById(R.id.btnChangePass);
                    cancle = dialog.findViewById(R.id.btnCanclePass);

                    changePass.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String userName = MainActivity.name;


                            String passOld, passNew, passNew2;
                            passOld = pass.getText().toString();
                            passNew = newPass.getText().toString();
                            passNew2 = newPass2.getText().toString();
                            //Check mật khẩu cũ và mật khẩu mới
                            try {

                                if (passOld.isEmpty() || passNew.isEmpty() || passNew2.isEmpty()) {
                                    show.show("Bạn không được để trống!");
                                } else {

                                    if (passNew.matches(passNew2)) {
                                        if (users.getPassword().matches(passOld)) {
                                            userDao.changePass(MainActivity.name, passNew);
                                            show.show("Đổi mật khẩu thành công!");
                                            dialog.dismiss();
                                        } else {
                                            show.show("Mật khẩu cũ không đúng!");
                                        }
                                    } else {
                                        show.show("Mật khẩu mới không khớp nhau!");
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            cancle.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }
                    });
                }
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.show2);
                dialog.setCancelable(true);
                Window window = dialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                if (dialog != null && dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
                TextView text = dialog.findViewById(R.id.tvInfo2);
                Button ok = dialog.findViewById(R.id.btnOK);
                Button cancle = dialog.findViewById(R.id.btnCancle);

                text.setText("Bạn có chắc chắn muốn đăng xuất?");
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getActivity().finish();
                    }
                });

                cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        return view;
    }


    public static void filterUser() {
        String loaiUser = users.getLoaiUser();
        if (loaiUser.matches("0")) {
            roomMng.setVisibility(View.GONE);
            partnerRoom.setVisibility(View.VISIBLE);
        } else if (loaiUser.matches("1")) {
            roomMng.setVisibility(View.VISIBLE);
            partnerRoom.setVisibility(View.GONE);
        }
    }

    //Get full user one
    public void getFullUser() {
        //Lấy dữ liệu User
        Query query = FirebaseDatabase.getInstance().getReference("FullUser").orderByChild("emailUser").equalTo(emailUser);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String userName, cmndUser, emailUser, birtdayUser, phoneUser, addressUser, linkImage;
                    userName = "" + ds.child("userName").getValue();
                    cmndUser = "" + ds.child("cmndUser").getValue();
                    emailUser = "" + ds.child("emailUser").getValue();
                    birtdayUser = "" + ds.child("birtdayUser").getValue();
                    phoneUser = "" + ds.child("phoneUser").getValue();
                    addressUser = "" + ds.child("addressUser").getValue();
                    linkImage = "" + ds.child("linkImage").getValue();
                    list = new FullUser(userName, cmndUser, emailUser, birtdayUser, phoneUser, addressUser, linkImage);
                }
                try {
                    name.setText(list.getUserName());
                    email.setText(list.getEmailUser());
                    if (!list.getLinkImage().matches("")) {
                        Picasso.with(getActivity()).load(list.getLinkImage()).into(avatar);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
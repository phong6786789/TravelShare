package com.duan.travelshare.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.duan.travelshare.MainActivity;
import com.duan.travelshare.R;
import com.duan.travelshare.firebasedao.UserDao;
import com.duan.travelshare.model.User;

import java.util.ArrayList;

public class UserFragment extends Fragment {
    static CardView fullInfo, roomMng, roomFav, partnerRoom, changePass, logOut;
    TextView name, email;
    static ArrayList<User> users;
    InfoDialog show;

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        //Đổ tài khoản vào list
        final UserDao userDao = new UserDao(getActivity());
        users = userDao.getAllFilter();


        show = new InfoDialog(getActivity());
        //Khai báo các id
        fullInfo = view.findViewById(R.id.cvUserInfo);
        roomFav = view.findViewById(R.id.cvRoomFavorite);
        roomMng = view.findViewById(R.id.cvRoomManager);
        partnerRoom = view.findViewById(R.id.cvPartner);
        changePass = view.findViewById(R.id.cvChangePass);
        logOut = view.findViewById(R.id.cvLogOut);
        name = view.findViewById(R.id.tvNameUser);
        email = view.findViewById(R.id.tvEmail);

        name.setText(MainActivity.name);
        email.setText(MainActivity.email);
        //Set tên và email người dùng
        if (!MainActivity.tk.isEmpty()) {
            name.setText(MainActivity.tk);
        }

        //Lọc user là khách hay là chủ cho thuê
        filterUser();

        //Khi chọn thông tin tài khoản
        fullInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        roomMng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                            if (!MainActivity.tk.isEmpty()) {
                                userDao.changeLoaiUser(MainActivity.tk, "1");
                                show.toastInfo("Chúc mừng bạn đã trở thành chủ cho thuê!");
                                dialog.dismiss();
                            }
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
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.change_pass);
                dialog.setCancelable(true);
                Window window = dialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                if (dialog != null && dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }

                final EditText pass, newPass, newPass2;
                Button changePass, cancle;
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
//
//
                        //Check mk đúng trước\
                        Boolean checkPassOld = false;
                        try {
                            for (int i = 0; i < users.size(); i++) {
                                String tk = users.get(i).getUserName();
                                String mk = users.get(i).getPassword();
                                if (tk.matches(userName) && mk.matches(passOld)) {
                                    checkPassOld = true;
                                    break;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        //Check mật khẩu cũ và mật khẩu mới

                        try {
                            if (MainActivity.tk.isEmpty()) {
                                show.toastInfo("Bạn đang dùng Google hoặc Facebook nên không thể đổi mật khẩu!");
                                dialog.dismiss();
                            } else {
                                if (passOld.isEmpty() || passNew.isEmpty() || passNew2.isEmpty()) {
                                    show.toastInfo("Bạn không được để trống!");
                                } else {
                                    if (passNew.matches(passNew2)) {
                                        if (checkPassOld) {
                                            userDao.changePass(MainActivity.name, passNew);
                                            show.toastInfo("Đổi mật khẩu thành công!");
                                            dialog.dismiss();
                                        } else {
                                            show.toastInfo("Mật khẩu cũ không đúng!");
                                        }
                                    } else {
                                        show.toastInfo("Mật khẩu mới không khớp nhau!");
                                    }
                                }


                            }
//
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

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        return view;
    }

    public static void filterUser() {
        String loaiUser = "0";
        for (int i = 0; i < users.size(); i++) {
            String tk = users.get(i).getUserName();
            if (MainActivity.tk.matches(tk)) {
                loaiUser = users.get(i).getLoaiUser();
                break;
            }
        }

        if (loaiUser.matches("0")) {
            roomMng.setVisibility(View.GONE);
            partnerRoom.setVisibility(View.VISIBLE);
        } else if (loaiUser.matches("1")) {
            roomMng.setVisibility(View.VISIBLE);
            partnerRoom.setVisibility(View.GONE);
        }
    }

}
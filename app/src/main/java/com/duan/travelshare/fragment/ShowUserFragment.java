package com.duan.travelshare.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.duan.travelshare.MainActivity;
import com.duan.travelshare.R;
import com.duan.travelshare.firebasedao.FullUserDao;
import com.duan.travelshare.model.FullUser;
import com.duan.travelshare.model.User;

import java.util.ArrayList;
import java.util.Calendar;

public class ShowUserFragment extends Fragment {
    private ImageView ivEdit, ivCamera, ivStorage, ivAvatar;
    private DatePickerDialog datePickerDialog;
    private FullUserDao fullUserDao;
    private ShowDialog showDialog;
    static TextView name, cmnd, email, birthday, phone, address;
    private String namex, cmndx, emailx, birthdayx, phonex, addressx;
    private Dialog dialog;
    private FullUser u;
    private User user;

    public ShowUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_user, container, false);
        showDialog = new ShowDialog(getActivity());
        fullUserDao = new FullUserDao(getActivity());
        ivAvatar = view.findViewById(R.id.ivAvatar);
        ivCamera = view.findViewById(R.id.ivCamera);
        ivStorage = view.findViewById(R.id.ivStorage);
        ivEdit = view.findViewById(R.id.ivEdit);
        //Đổ dữ liệu
        u = MainActivity.fullUserOne;


        //Hiển thị thông tin lên
        name = view.findViewById(R.id.tvFullName);
        cmnd = view.findViewById(R.id.tvCmnd);
        email = view.findViewById(R.id.tvEmail);
        birthday = view.findViewById(R.id.tvBirthday);
        phone = view.findViewById(R.id.tvPhone);
        address = view.findViewById(R.id.tvAddress);

        //Set user
        setUser();

        //Set hình lên


        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.edit_user);
                dialog.setCancelable(true);
                Window window = dialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                if (dialog != null && dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
                final EditText name, cmnd, email, birthday, phone, address;
                Button edit, cancle;
                edit = dialog.findViewById(R.id.btnAddInfo);
                cancle = dialog.findViewById(R.id.btnCancleInfo);
                name = dialog.findViewById(R.id.edtName);
                cmnd = dialog.findViewById(R.id.edtCMND);
                email = dialog.findViewById(R.id.edtEmail);
                birthday = dialog.findViewById(R.id.edtBirthday);
                phone = dialog.findViewById(R.id.edtPhone);
                address = dialog.findViewById(R.id.edtAddress);
                email.setEnabled(false);
                birthday.setFocusable(false);

                //Set dữ liệu lên trước
                name.setText(u.getUserName());
                cmnd.setText(u.getCmndUser());
                email.setText(u.getEmailUser());
                birthday.setText(u.getBirtdayUser());
                phone.setText(u.getPhoneUser());
                address.setText(u.getAddressUser());

                //Phân biệt tài khoản đăng nhập bằng fb, gg hay firebase
                if (MainActivity.userName.matches("0")) {
                    name.setText(MainActivity.name);
                    email.setText(MainActivity.email);
                } else {
                    email.setText(MainActivity.userName);
                }

                //Khi chọn vào ngày
                birthday.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Calendar calendar = Calendar.getInstance();
                        int d = calendar.get(Calendar.DAY_OF_MONTH);
                        int m = calendar.get(Calendar.MONTH);
                        int y = calendar.get(Calendar.YEAR);
                        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String ngay = "";
                                if (String.valueOf(month).length() == 1) {
                                    ngay = dayOfMonth + "/" + "0" + (month + 1) + "/" + year;
                                } else {
                                    ngay = dayOfMonth + "/" + (month + 1) + "/" + year;
                                }
                                birthday.setText(ngay);
                            }
                        }, y, m, d);
                        datePickerDialog.show();
                    }
                });

                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            namex = name.getText().toString();
                            cmndx = cmnd.getText().toString();
                            emailx = email.getText().toString();
                            birthdayx = birthday.getText().toString();
                            phonex = phone.getText().toString();
                            addressx = address.getText().toString();

                            //Invailed Form
                            checkForm();

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
        return view;
    }

    private void checkForm() {
        try {
            if (namex.isEmpty() || cmndx.isEmpty() || emailx.isEmpty() || birthdayx.isEmpty() || phonex.isEmpty() || addressx.isEmpty()) {
                showDialog.toastInfo("Các trường không được để trống!");
            } else if (!namex.matches("^[a-zẠ-ỹA-Z\\s]{4,}")) {
                showDialog.toastInfo("Tên phải có ít nhất 5 ký tự!");
            } else if (cmndx.length() != 9 && cmndx.length() != 12) {
                showDialog.toastInfo("Chứng minh nhân dân phải đủ 9 số hoặc căn cước công dân phải đủ 12 số!");
            } else if (!phonex.matches("[0-9]{10,11}")) {
                showDialog.toastInfo("Vui lòng nhập đúng số điện thoại");
            } else if (addressx.length() < 10) {
                showDialog.toastInfo("Vui lòng nhập đầy đủ địa chỉ!");
            } else {
                FullUser fullUser = new FullUser(namex, cmndx, emailx, birthdayx, phonex, addressx);
                fullUserDao.updateUser(fullUser);
                showDialog.toastInfo("Sửa thành công!");
                dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Set info lên user
    public void setUser() {
        if (MainActivity.position != -1) {
            name.setText(u.getUserName());
            cmnd.setText(u.getCmndUser());
            email.setText(u.getEmailUser());
            birthday.setText(u.getBirtdayUser());
            phone.setText(u.getPhoneUser());
            address.setText(u.getAddressUser());
        }
    }
}
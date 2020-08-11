package com.duan.travelshare.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.duan.travelshare.MainActivity;
import com.duan.travelshare.R;
import com.duan.travelshare.model.ChiTietChuChoThue;
import com.duan.travelshare.model.ChiTietKH;
import com.duan.travelshare.model.ChiTietPhong;
import com.duan.travelshare.model.FullUser;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.duan.travelshare.firebasedao.GiaoDichDao;
import com.duan.travelshare.model.ChiTietPhong;
import com.duan.travelshare.model.GiaoDich;
import com.duan.travelshare.model.FullUser;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.Calendar;

public class ChiTietPhongHomeFragment extends Fragment {
    final int SEND_SMS_PERMISSION_REQUEST_CODE = 111;
    private Button sendSMS;
    private static final int REQUEST_CALL = 1;
    private ImageView phong, user, save, call, messenger;
    private LinearLayout star;
    private TextView tenPhong, giaPhong, tenUser, emailUser, moTa;
    private Button xem, datPhong;
    ChiTietPhong chiTietPhong;
    DecimalFormat fm = new DecimalFormat("#,###");
    private DatePickerDialog datePickerDialog;
    ImageView img;
    TextView tenPhongDat, gia;
    EditText hoten, cmnd, tungay, denngay, ghichu;
    Button datPhongDat, huyDat;
    private GiaoDichDao giaoDichDao;
    ShowDialog showDialog;
    private FullUser fullUser = MainActivity.fullUserOne;

    public ChiTietPhongHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chi_tiet_phong, container, false);
        //Ẩn navigation
        giaoDichDao = new GiaoDichDao(getActivity());
        MainActivity.navigation.setVisibility(View.GONE);
        showDialog = new ShowDialog(getActivity());
        //Nhạn object
        Bundle bundle = getArguments();
        chiTietPhong = (ChiTietPhong) bundle.getSerializable("list");
        //Khai báo
        phong = view.findViewById(R.id.ivPhong);
        user = view.findViewById(R.id.ivUser);
        save = view.findViewById(R.id.ivSave);
        call = view.findViewById(R.id.ivCall);
        messenger = view.findViewById(R.id.ivMessenger);
        star = view.findViewById(R.id.lnStar);
        tenPhong = view.findViewById(R.id.tvTenPhong);
        giaPhong = view.findViewById(R.id.tvGiaphong);
        tenUser = view.findViewById(R.id.tvUser);
        emailUser = view.findViewById(R.id.tvEmailP);
        moTa = view.findViewById(R.id.tvMota);
        xem = view.findViewById(R.id.btnXemUser);
        datPhong = view.findViewById(R.id.btnDatPhongChiTiet);

        if (!chiTietPhong.getImgPhong().get(0).isEmpty()) {
            Picasso.with(getActivity()).load(chiTietPhong.getImgPhong().get(0)).into(phong);
        } else {
            phong.setImageResource(R.drawable.phongtro);
        }
        if (!chiTietPhong.getFullUser().getLinkImage().isEmpty()) {
            Picasso.with(getActivity()).load(chiTietPhong.getFullUser().getLinkImage()).into(user);
        } else {
            user.setImageResource(R.drawable.userimg);
        }

        tenPhong.setText(chiTietPhong.getTenPhong());
        giaPhong.setText(chiTietPhong.getGiaPhong());
        tenUser.setText(chiTietPhong.getFullUser().getUserName());
        emailUser.setText(chiTietPhong.getFullUser().getEmailUser());
        moTa.setText(chiTietPhong.getMoTaPhong());
        //save, call, messenger, star , xem, datPhong;
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall();
            }
        });
        messenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSMS();
            }
        });
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        xem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog1 = new Dialog(getActivity());
                dialog1.setContentView(R.layout.fragment_show_user);
//                dialog1.setCancelable(true);
                Window window = dialog1.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                if (dialog1 != null && dialog1.getWindow() != null) {
                    dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
                FullUser userChuThue = chiTietPhong.getFullUser();
                ImageView ivQR, ivAvt;
                EditText name, email, birthday, phone, address, cmndUser;
                Button btnDong;

                ivQR = dialog1.findViewById(R.id.ivQRUser);
                ivAvt = dialog1.findViewById(R.id.ivAvatar);

                btnDong = dialog1.findViewById(R.id.btnUpdateUser);

                name = dialog1.findViewById(R.id.tvFullName);
                cmndUser = dialog1.findViewById(R.id.tvCmnd);
                email = dialog1.findViewById(R.id.tvEmail);
                birthday = dialog1.findViewById(R.id.tvBirthday);
                phone = dialog1.findViewById(R.id.tvPhone);
                address = dialog1.findViewById(R.id.tvAddress);
                if (!userChuThue.getLinkImage().isEmpty()) {
                    Picasso.with(getContext()).load(userChuThue.getLinkImage()).into(ivAvt);
                }
                name.setText(userChuThue.getUserName());
                cmndUser.setText(userChuThue.getCmndUser());
                email.setText(userChuThue.getEmailUser());
                birthday.setText(userChuThue.getBirtdayUser());
                phone.setText(userChuThue.getPhoneUser());
                address.setText(userChuThue.getAddressUser());
                btnDong.setText("Đóng");
                btnDong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog1.dismiss();
                    }
                });

                name.setFocusable(false);
                cmndUser.setFocusable(false);
                email.setFocusable(false);
                birthday.setFocusable(false);
                phone.setFocusable(false);
                address.setFocusable(false);

                dialog1.show();
            }
        });

        datPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chiTietPhong.getFullUser().getEmailUser().matches(fullUser.getEmailUser())) {
                    showDialog.show("Bạn không thể đặt phòng của chính bạn!");
                } else {
                    datPhong();
                }
            }
        });

        //Khi ấn nút back
        //Toolbar
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.tbTitle);
        ImageView back = toolbar.findViewById(R.id.tbBack);
        title.setText("CHI TIẾT PHÒNG");
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.navigation.setVisibility(View.VISIBLE);
                HomeFragment userFragment = new HomeFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, userFragment)
                        .commit();
            }
        });
        return view;
    }

    private void sendSMS() {
        final Dialog dialog1 = new Dialog(getActivity());
        dialog1.setContentView(R.layout.sendmasenger);
        dialog1.setCancelable(true);
        Window window = dialog1.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialog1 != null && dialog1.getWindow() != null) {
            dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        final EditText sms = dialog1.findViewById(R.id.edtsms);
        final TextView phone1 = dialog1.findViewById(R.id.so);
        final TextView sendsms = dialog1.findViewById(R.id.sendsms);
        phone1.setText(chiTietPhong.getFullUser().getPhoneUser());
        sms.setText("Xin chào, " + chiTietPhong.getTenPhong() + " còn không ạ?");
        sendsms.setEnabled(false);
        if (checkPermission(Manifest.permission.SEND_SMS)) {
            sendsms.setEnabled(true);
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS},
                    SEND_SMS_PERMISSION_REQUEST_CODE);
        }
        sendsms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = phone1.getText().toString();
                String ed = sms.getText().toString();
                if (s == null || s.length() == 0 || ed == null || ed.length() == 0) {
                    return;
                }
                if (checkPermission(Manifest.permission.SEND_SMS)) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(s, null, ed, null, null);
                    showDialog.show("Gửi tin nhắn thành công!");
                } else {
                    Toast.makeText(getActivity(), "Gửi tin nhắn thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog1.findViewById(R.id.huysms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });
        dialog1.show();
    }

    private void makePhoneCall() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.call);
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        final TextView phone = dialog.findViewById(R.id.number);
        phone.setText(chiTietPhong.getFullUser().getPhoneUser());
        final String number = phone.getText().toString();
        dialog.findViewById(R.id.goi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!number.trim().isEmpty()) {
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                    } else {
                        String dial = "tel:" + number;
                        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                    }
                } else {
                    showDialog.show("Không có số điện thoại!");
                }
            }
        });
        dialog.findViewById(R.id.huy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });
        dialog.show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(getActivity(), "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean checkPermission(String permission) {
        int checkPermission = ContextCompat.checkSelfPermission(getActivity(), permission);
        return (checkPermission == PackageManager.PERMISSION_GRANTED);
    }

    private void datPhong() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.datphong);
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        if (dialog != null && dialog.getWindow() != null) {
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        }


        //Tham chiếu id
        img = dialog.findViewById(R.id.ivDatPhong);
        tenPhongDat = dialog.findViewById(R.id.namephong);
        gia = dialog.findViewById(R.id.giaphong);
        hoten = dialog.findViewById(R.id.edtHTen);
        cmnd = dialog.findViewById(R.id.edtCmnd);
        tungay = dialog.findViewById(R.id.edtTuNgay);
        denngay = dialog.findViewById(R.id.edtDenNgay);
        ghichu = dialog.findViewById(R.id.edtGhiChu);
        datPhongDat = dialog.findViewById(R.id.btnDatPhong);
        huyDat = dialog.findViewById(R.id.btnHuyDatPhong);
        //Set dữ liệu
        if (!chiTietPhong.getImgPhong().get(0).isEmpty()) {
            Picasso.with(getActivity()).load(chiTietPhong.getImgPhong().get(0)).into(img);
        } else {
            img.setImageResource(R.drawable.phongtro);
        }
        tenPhong.setText(chiTietPhong.getTenPhong());
        gia.setText(fm.format(Integer.parseInt(chiTietPhong.getGiaPhong())));


        hoten.setText(fullUser.getUserName());
        cmnd.setText(fullUser.getCmndUser());

        tungay.setFocusable(false);
        //Khi chọn vào ngày
        tungay.setOnClickListener(new View.OnClickListener() {
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
                        tungay.setText(ngay);
                    }
                }, y, m, d);
                datePickerDialog.show();
            }
        });

        denngay.setFocusable(false);
        //Khi chọn vào ngày
        denngay.setOnClickListener(new View.OnClickListener() {
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
                        denngay.setText(ngay);
                    }
                }, y, m, d);
                datePickerDialog.show();
            }
        });

        datPhongDat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check email

                String ten, cm, tu, den, ghi;
                ten = hoten.getText().toString();
                cm = cmnd.getText().toString();
                tu = tungay.getText().toString();
                den = denngay.getText().toString();
                ghi = ghichu.getText().toString();
                //Check lỗi
                if (ten.isEmpty() || cm.isEmpty() || tu.isEmpty() || den.isEmpty() || ghi.isEmpty()) {
                    showDialog.show("Các trường không được để trống!");
                } else {
                    GiaoDich giaoDich = new GiaoDich(chiTietPhong, fullUser, ten, cm, tu, den, ghi, 0);
                    giaoDichDao.insertPhong(giaoDich);
                    showDialog.show("Đặt phòng thành công!");
                    dialog.dismiss();

                    MainActivity.navigation.setVisibility(View.VISIBLE);
                    GiaoDichFragment giaoDichFragment = new GiaoDichFragment();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame, giaoDichFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });

        huyDat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        //Khi ấn nút đặt
        dialog.show();
    }
}

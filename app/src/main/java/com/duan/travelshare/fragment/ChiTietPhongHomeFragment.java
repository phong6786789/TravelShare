package com.duan.travelshare.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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

import com.duan.travelshare.MainActivity;
import com.duan.travelshare.R;
import com.duan.travelshare.firebasedao.GiaoDichDao;
import com.duan.travelshare.model.ChiTietPhong;
import com.duan.travelshare.model.GiaoDich;
import com.duan.travelshare.model.FullUser;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.Calendar;

public class ChiTietPhongHomeFragment extends Fragment {
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

            }
        });
        messenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

            }
        });
        if (chiTietPhong.getFullUser().getEmailUser().matches(fullUser.getEmailUser())) {
            showDialog.show("Bạn không thể đặt phòng của chính bạn!");
        } else {
            datPhong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    datPhong();
                }
            });
        }
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
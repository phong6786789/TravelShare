package com.duan.travelshare.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.duan.travelshare.MainActivity;
import com.duan.travelshare.R;
import com.duan.travelshare.firebasedao.FullUserDao;
import com.duan.travelshare.firebasedao.UserDao;
import com.duan.travelshare.fragment.ShowDialog;
import com.duan.travelshare.model.FullUser;
import com.duan.travelshare.model.User;

import java.util.ArrayList;

public class Register extends AppCompatActivity {

    EditText txtRegTk, txtRegMk, txtRegMkk;
    Button btDangKy, btNhapLai;
    ArrayList<User> users = new ArrayList<>();
    UserDao userDao;
    FullUserDao fullUserDao;
    private ShowDialog showDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userDao = new UserDao(this);
        users = userDao.getAll();
        fullUserDao = new FullUserDao(this);
        showDialog = new ShowDialog(this);
        init();
        final Animation buttonClick = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);

        btDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tk = txtRegTk.getText().toString();
                String mk = txtRegMk.getText().toString();
                String mkk = txtRegMkk.getText().toString();
                boolean xetTk = true, xetMk = false;
//                TaiKhoanMatKhau tkmk = new TaiKhoanMatKhau(tk, mk);
                //Đổ tk, mk vào database
//                listTk = tkDao.getALl();

                //Xét trùng mk
                if (mk.matches(mkk)) {
                    xetMk = true;
                } else {
                    xetMk = false;
                }

                //Xét tên tk có trùng hay không
                for (int i = 0; i < users.size(); i++) {
                    User tkx = users.get(i);
                    if (tkx.getUserName().matches(tk)) {
                        xetTk = false;
                        break;
                    }
                }

                if (tk.isEmpty()) {
                    showDialog.toastInfo("Tên tài khoản không được để trống!");
                } else if (tk.length() < 5 || tk.length() > 10) {
                    showDialog.toastInfo("Tên tài khoản ít nhất có 5 ký tự và nhiều nhất là 10 ký tự!");
                } else {
                    if (mk.isEmpty() || mkk.isEmpty()) {
                        showDialog.toastInfo("Mật khẩu không được để trống!");
                    } else {
                        if (xetTk == true) {
                            if (xetMk == true) {
                                v.startAnimation(buttonClick);
                                User user = new User(tk, mk, "0","");
                                userDao.insert(user);
                                fullUserDao.insertFullUser(new FullUser(tk, "",tk,"","",""));
                                showDialog.toastInfo("Đăng ký tài khoản thành công!");
                                //Truyền dữ liệu về ô đăng nhập, mật khẩu trang Login
                                Intent i = new Intent();
                                i.putExtra("tk", tk);
                                i.putExtra("mk", mk);
                                setResult(RESULT_OK, i);
                                finish();
                            } else {
                                showDialog.toastInfo("Mật khẩu không khớp nhau!");
                            }
                        } else {
                            showDialog.toastInfo("Tên tài khoản không được trùng!");
                        }
                    }
                }
            }
        });
        //Nhập lại dữ liệu
        btNhapLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                finish();
            }
        });

    }

    private void init() {

        txtRegTk = findViewById(R.id.edtRegUser);
        txtRegMk = findViewById(R.id.edtRegPassword);
        txtRegMkk = findViewById(R.id.edtRePassword);
        btDangKy = findViewById(R.id.btnReg);
        btNhapLai = findViewById(R.id.btnRelay);
    }
}

package com.duan.travelshare.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.duan.travelshare.MainActivity;
import com.duan.travelshare.R;
import com.duan.travelshare.model.ChiTietPhong;
import com.duan.travelshare.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginFragment extends Fragment {
    private TextInputEditText tk, mk;
    private Button login;
    private CheckBox checkBox;
    private TextView dangKy, quenMk;
    private String tks, mks;
    private Boolean check;
    private ProgressDialog progressDialog;
    private ShowDialog showDialog;
    private TextInputLayout tilTk, tilMk;
    private String uID;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference("User");
    private FirebaseAuth mAuth;
    private User user;

    public LoginFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.tbTitle);
        ImageView back = toolbar.findViewById(R.id.tbBack);
        back.setVisibility(View.VISIBLE);
        MainActivity.navigation.setVisibility(View.GONE);
        title.setText("ĐĂNG NHẬP");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.navigation.setVisibility(View.VISIBLE);
                UserFragment userFragment = new UserFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, userFragment)
                        .commit();
            }
        });

        tk = view.findViewById(R.id.edtTk);
        mk = view.findViewById(R.id.edtMk);
        checkBox = view.findViewById(R.id.cbCheck);
        login = view.findViewById(R.id.btnDangNhap);
        dangKy = view.findViewById(R.id.tvDangKy);
        quenMk = view.findViewById(R.id.tvQuenMk);
        mAuth = FirebaseAuth.getInstance();
        showDialog = new ShowDialog(getActivity());
        progressDialog = new ProgressDialog(getContext());
        tk.addTextChangedListener(new ValidationTextWatcher(tk));
        mk.addTextChangedListener(new ValidationTextWatcher(mk));
        tilTk = view.findViewById(R.id.tilTK);
        tilMk = view.findViewById(R.id.tilMK);

        quenMk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.quenmk);
                dialog.setCancelable(true);
                Window window = dialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                if (dialog != null && dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
                final EditText email = dialog.findViewById(R.id.tvEmailReset);
                email.setText(tk.getText().toString());
                Button reset = dialog.findViewById(R.id.btnReset);
                Button huy = dialog.findViewById(R.id.btnHuyReset);
                huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                reset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mAuth.sendPasswordResetEmail(email.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                showDialog.show("Reset mật khẩu thành công!\nVui lòng kiểm tra email.");
                                dialog.dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                showDialog.show("Reset mật khẩu thất bại");
                            }
                        });

                    }
                });
                dialog.show();
            }
        });

        //nhận dữ liệu khi đăng ký thành công
        if (getArguments() != null) {
            tk.setText(getArguments().getSerializable("tk").toString());
            mk.setText(getArguments().getSerializable("mk").toString());
        }

        //Khi ấn đăng nhập
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateEmail() & validatePassword() == true) {

                    progressDialog.show();

                    progressDialog.setMessage("Đang đăng nhập...");
                    mAuth.signInWithEmailAndPassword(tks, mks)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        uID = mAuth.getCurrentUser().getUid();
                                        Toast.makeText(getActivity(), "Đăng nhập thành công",
                                                Toast.LENGTH_SHORT).show();
                                        UserFragment dangNhap = new UserFragment();
                                        getActivity().getSupportFragmentManager()
                                                .beginTransaction()
                                                .replace(R.id.frame, dangNhap)
                                                .commit();
                                        progressDialog.dismiss();
                                    } else {
                                        Toast.makeText(getActivity(), "Đăng nhập thất bại",
                                                Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                }
                            });

                }
            }
        });

        //Khi ấn đăng ký
        dangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterFragment dangKy = new RegisterFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame, dangKy)
                        .commit();
            }
        });


        return view;
    }


    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

//    private void getUser() {
//        SharedPreferences sharedPreferences = getSharedPreferences("subi", MODE_PRIVATE);
//        boolean check = sharedPreferences.getBoolean("check", false);
//        if (check) {
//            String tenNguoiDung = sharedPreferences.getString("tk", "");
//            String matKhau = sharedPreferences.getString("mk", "");
//            txtTk.setText(tenNguoiDung);
//            txtMk.setText(matKhau);
//        } else {
//            txtTk.setText("");
//            txtMk.setText("");
//        }
//        checkBox.setChecked(check);
//    }
//
//    private void saveUser() {
//        SharedPreferences sharedPreferences = getSharedPreferences("subi", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        String ten = txtTk.getText().toString();
//        String pass = txtMk.getText().toString();
//        boolean check = checkBox.isChecked();
//        if (!check) {
//            editor.clear();
//        } else {
//            editor.putString("tk", ten);
//            editor.putString("mk", pass);
//            editor.putBoolean("check", check);
//        }
//        editor.commit();
//    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    private boolean validateEmail() {
        tks = tk.getText().toString();

        if (tks.trim().isEmpty()) {
            tilTk.setError("Bạn không được để trống!");
            requestFocus(tk);
            return false;
        } else {
            Boolean isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(tks).matches();
            if (!isValid) {
                tilTk.setError("Sai định dạng Email, ex: abc@example.com");
                requestFocus(tk);
                return false;
            } else {
                tilTk.setErrorEnabled(false);
            }
        }
        return true;
    }

    private boolean validatePassword() {
        mks = mk.getText().toString();

        if (mks.trim().isEmpty()) {
            tilMk.setError("Bạn không được để trống!");
            requestFocus(tilMk);
            return false;
        } else if (mks.length() < 6) {
            tilMk.setError("Mật khẩu phải từ 6 ký tự trở lên");
            requestFocus(tilMk);
            return false;
        } else {
            tilMk.setErrorEnabled(false);
        }
        return true;
    }

    private class ValidationTextWatcher implements TextWatcher {

        private View view;

        private ValidationTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.edtTk:
                    validateEmail();
                    break;
                case R.id.edtMk:
                    validatePassword();
                    break;
            }
        }
    }
}
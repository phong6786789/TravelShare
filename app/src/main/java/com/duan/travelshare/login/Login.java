package com.duan.travelshare.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.duan.travelshare.MainActivity;
import com.duan.travelshare.R;
import com.duan.travelshare.firebasedao.UserDao;
import com.duan.travelshare.model.User;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Login extends AppCompatActivity {
    CheckBox checkBox;
    EditText txtTk, txtMk;
    Button btLogin;
    TextView btReg;
    ArrayList<User> users = new ArrayList<>();
    CallbackManager callbackManager;
    LoginButton loginFB;
    ImageView signInButton;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userDao = new UserDao(this);
        users = userDao.getAll();
        init();
        getUser();
        final Animation buttonClick = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean xetTk = false;
                String tenTK = txtTk.getText().toString();
                String mk = txtMk.getText().toString();

                for (int i = 0; i < users.size(); i++) {
                    User tkx = users.get(i);
                    if (tkx.getUserName().matches(tenTK) && tkx.getPassword().matches(mk)) {
                        xetTk = true;
                        break;
                    }
                }
                if (xetTk == true) {
                    saveUser();
                    Toast.makeText(Login.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    //Trang mới xuất hiện khi đã đúng tài khoản và mật khẩu
                    //Set tên tk vô cho LoginOk
                    v.startAnimation(buttonClick);
                    Intent i = new Intent(Login.this, MainActivity.class);
                    i.putExtra("name", tenTK);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else {
                    Toast.makeText(Login.this, "Tên tài khoản hoặc mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                Intent i = new Intent(Login.this, Register.class);
                startActivityForResult(i, 999);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });


        loginFB();
        loginGG();
    }

    private void loginGG() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        final GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(Login.this, gso);

        signInButton = findViewById(R.id.GGView);
//        signInButton.setSize(SignInButton.SIZE_STANDARD);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 0);
            }
        });
    }

    private void loginFB() {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(Login.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                response.getError();
                                String name = "", email="";
                                Log.v("LoginActivity", object.toString());
                                try {
                                    if(object.has("name")){
                                        name = object.getString("name");
                                    }
                                    if(object.has("email")){
                                        email = object.getString("name");
                                    }

                                    Intent i = new Intent(Login.this, MainActivity.class);
                                    i.putExtra("name", name);
                                    i.putExtra("email", email);
                                    startActivity(i);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
            }
        });
    }


    private void init() {
        txtTk = findViewById(R.id.edtUserName);
        txtMk = findViewById(R.id.edtPassword);
        btLogin = findViewById(R.id.btnLogin);
        btReg = findViewById(R.id.btnRegister);
        checkBox = findViewById(R.id.checkbox);
        loginFB = findViewById(R.id.loginFB);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == RESULT_OK) {
            String tk = data.getStringExtra("tk");
            String mk = data.getStringExtra("mk");
            txtTk.setText(tk);
            txtMk.setText(mk);
        }
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 0) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            // get info User
            GoogleSignInAccount acct = completedTask.getResult(ApiException.class);
            String personName = acct.getDisplayName();
//            String personGivenName = acct.getGivenName();
//            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
//            String personId = acct.getId();
            //Uri personPhoto = acct.getPhotoUrl();
            // Signed in successfully, show authenticated UI.
            Toast.makeText(Login.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Login.this, MainActivity.class);
            i.putExtra("email", personEmail);
            i.putExtra("name", personName);
            startActivity(i);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());

        }
    }


    private void getUser() {
        SharedPreferences sharedPreferences = getSharedPreferences("subi", MODE_PRIVATE);
        boolean check = sharedPreferences.getBoolean("check", false);
        if (check) {
            String tenNguoiDung = sharedPreferences.getString("tk", "");
            String matKhau = sharedPreferences.getString("mk", "");
            txtTk.setText(tenNguoiDung);
            txtMk.setText(matKhau);
        } else {
            txtTk.setText("");
            txtMk.setText("");
        }
        checkBox.setChecked(check);
    }

    private void saveUser() {
        SharedPreferences sharedPreferences = getSharedPreferences("subi", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String ten = txtTk.getText().toString();
        String pass = txtMk.getText().toString();
        boolean check = checkBox.isChecked();
        if (!check) {
            editor.clear();
        } else {
            editor.putString("tk", ten);
            editor.putString("mk", pass);
            editor.putBoolean("check", check);
        }
        editor.commit();

    }

    //Khi mới vào sẽ tự đăng xuất trước
    @Override
    protected void onStart() {
        LoginManager.getInstance().logOut();
        super.onStart();
    }

    public void loginFacebook(View view) {
        loginFB.performClick();
    }
}

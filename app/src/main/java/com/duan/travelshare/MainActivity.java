package com.duan.travelshare;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import com.duan.travelshare.fragment.GiaoDichFragment;
import com.duan.travelshare.fragment.HomeFragment;
import com.duan.travelshare.fragment.ShowDialog;
import com.duan.travelshare.fragment.ThongBaoFragment;
import com.duan.travelshare.fragment.UserFragment;
import com.duan.travelshare.model.FullUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    public static String name = "0", email = "0", userName = "0";

    public static ArrayList<FullUser> list = new ArrayList<>();
    public static int position = -1;
    public static FullUser fullUserOne;
    static ShowDialog showDialog;
    public static BottomNavigationView navigation;
    public static String emailUser = "";
    private String token;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference("User");
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        showDialog = new ShowDialog(this);
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null){
            getToken();
        }

        navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        //Tạo màn hình ban đầu là fragment home đầu tiên
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }
    }

    //Menu bottom
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment = null;
            switch (menuItem.getItemId()) {
                case R.id.home:
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.giaodich:
                    fragment = new GiaoDichFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.thongbao:
                    fragment = new ThongBaoFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.user:
                    fragment = new UserFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    //Chặn hành động bấm phím back của người dùng
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    //Set info lên user
    public static void setUser() {
        for (int i = 0; i < list.size(); i++) {
            String x = list.get(i).getEmailUser();
            if (email.matches(x) || userName.matches(x)) {
                position = i;
                break;
            }
        }
        fullUserOne = list.get(position);
    }

    //Kiểm tra đăng nhập bằng tài khoản hay không?
    public void checkLoginTk() {
        if (!MainActivity.userName.matches("0")) {
            emailUser = userName;
        }
        emailUser = email;
    }

    private void getToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        token = task.getResult().getToken();
                        //Cập nhật token
                        databaseReference.child(mAuth.getCurrentUser().getUid()).child("token").setValue(token);
                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
                    }
                });

    }

}
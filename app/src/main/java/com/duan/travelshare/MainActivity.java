package com.duan.travelshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.duan.travelshare.fragment.GiaoDichFragment;
import com.duan.travelshare.fragment.HomeFragment;
import com.duan.travelshare.fragment.ThongBaoFragment;
import com.duan.travelshare.fragment.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
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
                    toolbar.setTitle("TRANG CHỦ");
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.giaodich:
                    toolbar.setTitle("GIAO DỊCH");
                    fragment = new GiaoDichFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.thongbao:
                    toolbar.setTitle("THÔNG BÁO");
                    fragment = new ThongBaoFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.user:
                    toolbar.setTitle("CÁ NHÂN");
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIRgit
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
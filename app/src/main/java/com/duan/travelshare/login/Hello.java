package com.duan.travelshare.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.duan.travelshare.R;


public class Hello extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        //Dùng cài đặt sau 2.3 giây màn hình tự chuyển
        Thread bamgio = new Thread() {


            public void run() {
                try {
                    sleep(2300);
                } catch (Exception e) {

                } finally {
                    Intent i = new Intent(Hello.this, Login.class);
                    startActivity(i);
                }
            }
        };
        bamgio.start();
    }

    //sau khi chuyển sang màn hình chính, kết thúc màn hình chào
    protected void onPause() {
        super.onPause();
        finish();
    }
}


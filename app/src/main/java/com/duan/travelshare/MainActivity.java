package com.duan.travelshare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                String hashKey = new String(Base64.encode(md.digest(), 0));
//                Log.i("TAG", "printHashKey() Hash Key: " + hashKey);
//            }
//        } catch (NoSuchAlgorithmException e) {
//            Log.e("TAG", "printHashKey()", e);
//        } catch (Exception e) {
//            Log.e("TAG", "printHashKey()", e);
//        }

    }
}
package com.example.androidhms;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidhms.customer.CustomerActivity;
import com.example.androidhms.databinding.ActivityMainBinding;
import com.example.androidhms.reception.ReceptionActivity;
import com.example.androidhms.reception.ReceptionLoginActivity;
import com.example.androidhms.staff.StaffActivity;
import com.example.androidhms.staff.StaffLoginActivity;
import com.example.androidhms.staff.messenger.ChatActivity;
import com.example.androidhms.util.Util;
import com.example.conn.ApiClient;


public class MainActivity extends AppCompatActivity {
    String TAG = "로그";
    private ActivityMainBinding bind;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityMainBinding.inflate(getLayoutInflater());
        preferences = getSharedPreferences("staffLoginInfo", MODE_PRIVATE);
        setContentView(bind.getRoot());

        // Jungwon
       // ApiClient.setBASEURL("http://192.168.0.36/hms/");
        //ApiClient.setBASEURL("http://192.168.0.25/hms/");
        //ApiClient.setBASEURL("http://192.168.0.116/middle/");
        //ApiClient.setBASEURL("http://192.168.0.22/hms/"); //안드로이드 시작 점에 실시 *경로정확하게 지정*
        ApiClient.setBASEURL("http://211.223.59.99:3301/hms/");

       // 의료진 자동 로그인 정보가 있을경우 바로 StaffActivity 로 이동
        if (!preferences.getString("staffData", "").equals("")) {
            Util.getStaff(this);
            startActivity(new Intent(this, StaffActivity.class));
            if (getIntent().getStringExtra("title") != null) {
                Intent intent = new Intent(this, ChatActivity.class);
                intent.putExtra("title", getIntent().getStringExtra("title"));
                intent.putExtra("key", getIntent().getStringExtra("key"));
                startActivity(intent);
            }
            finish();
        }


        // 고객홈페이지로 이동
        bind.btnCustomer.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CustomerActivity.class);
            startActivity(intent);
        });

        // 직원홈페이지로 이동
        bind.btnStaff.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StaffLoginActivity.class);
            startActivity(intent);
            finish();
        });

        // 원무과홈페이지로 이동
        bind.btnReceptionoffice.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ReceptionLoginActivity.class);
            startActivity(intent);
        });



      /*  new RetrofitMethod().setParams("a", "1").setParams("b", "2").sendPost("hmstest", new RetrofitMethod.CallBackResult() {
            @Override
            public void result(boolean isResult, String data) {
                Log.d(TAG, "result: " + isResult);
                Log.d(TAG, "result: " + data);
            }
        });
*/
    }


}


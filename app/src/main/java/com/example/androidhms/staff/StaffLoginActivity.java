package com.example.androidhms.staff;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidhms.databinding.ActivityStaffLoginBinding;
import com.example.androidhms.staff.vo.StaffVO;
import com.example.androidhms.util.HmsFirebase;
import com.example.androidhms.util.Util;
import com.example.conn.ApiClient;
import com.example.conn.RetrofitMethod;
import com.google.gson.Gson;

import java.lang.reflect.Type;

public class StaffLoginActivity extends AppCompatActivity {

    private ActivityStaffLoginBinding bind;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApiClient.setBASEURL("http://211.223.59.99:3301/hms/");
        bind = ActivityStaffLoginBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        preferences = getSharedPreferences("staffLoginInfo", MODE_PRIVATE);
        editor = preferences.edit();

        bind.etId.setText(preferences.getString("id", ""));
        bind.etPw.setText(preferences.getString("pw", ""));
        if (preferences.getString("autoLogin", "N").equals("Y")) {
            bind.cbAutologin.setChecked(true);
        }

        bind.toolbar.imgvBefore.setOnClickListener(v -> finish());
        bind.toolbar.imgvMessenger.setVisibility(View.GONE);

        bind.btLogin.setOnClickListener(v -> new RetrofitMethod()
                .setParams("id", bind.etId.getText().toString())
                .setParams("pw", bind.etPw.getText().toString())
                .sendPost("staffLogin.ap", (isResult, data) -> {
                    if (data != null && data.equals("null")) {
                        Toast.makeText(StaffLoginActivity.this,
                                "사번 또는 비밀번호를 확인해 주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        if (bind.cbAutologin.isChecked()) {
                            editor.putString("id", bind.etId.getText().toString());
                            editor.putString("pw", bind.etPw.getText().toString());
                            editor.putString("autoLogin", "Y");
                            editor.putString("staffData", data);
                        } else {
                            editor.putString("id", "");
                            editor.putString("pw", "");
                            editor.putString("autoLogin", "N");
                            editor.putString("staffData", "");
                        }
                        editor.commit();
                        Util.staff = new Gson().fromJson(data, StaffVO.class);
                        new HmsFirebase(StaffLoginActivity.this).sendToken();
                        Intent intent = new Intent(StaffLoginActivity.this, StaffActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }));
    }

}
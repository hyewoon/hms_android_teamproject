package com.example.androidhms.reception;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidhms.databinding.ActivityReceptionLoginBinding;
import com.example.androidhms.staff.vo.StaffVO;
import com.example.androidhms.util.HmsFirebase;
import com.example.androidhms.util.Util;
import com.example.conn.RetrofitMethod;
import com.google.gson.Gson;

import java.lang.reflect.Type;

public class ReceptionLoginActivity extends AppCompatActivity {

    private ActivityReceptionLoginBinding bind;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityReceptionLoginBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        preferences = getSharedPreferences("receptionLoginInfo", MODE_PRIVATE);
        editor = preferences.edit();

        bind.etId.setText(preferences.getString("id_rep", ""));
        bind.etPw.setText(preferences.getString("pw_rep", ""));
        if (preferences.getString("autoLogin_rep", "N").equals("Y")) {
            bind.cbAutologin.setChecked(true);
        }

        bind.toolbar.ivLeft.setOnClickListener(v -> {
            onBackPressed();
        });

        bind.btLogin.setOnClickListener(v -> new RetrofitMethod()
                .setParams("id", bind.etId.getText().toString())
                .setParams("pw", bind.etPw.getText().toString())
                .sendPost("login.re", (isResult, data) -> {
                    if (data.equals("null")) {
                        Toast.makeText(ReceptionLoginActivity.this,
                                "사번 또는 비밀번호를 확인해 주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        if (bind.cbAutologin.isChecked()) {
                            editor.putString("id_rep", bind.etId.getText().toString());
                            editor.putString("pw_rep", bind.etPw.getText().toString());
                            editor.putString("autoLogin_rep", "Y");
                            editor.putString("staffData_rep", data);
                        } else {
                            editor.putString("id_rep", "");
                            editor.putString("pw_rep", "");
                            editor.putString("autoLogin_rep", "N");
                            editor.putString("staffData_rep", "");
                        }
                        editor.commit();
                        Util.staff = new Gson().fromJson(data, (Type) StaffVO.class);
                        Intent intent = new Intent(ReceptionLoginActivity.this, ReceptionActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }));
    }

}
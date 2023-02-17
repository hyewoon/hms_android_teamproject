package com.example.androidhms.reception;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.androidhms.R;
import com.example.androidhms.databinding.ActivityReceptionBinding;
import com.example.androidhms.reception.appointment.AppointmentActivity;

import com.example.androidhms.reception.search.SearchActivity;

import com.example.androidhms.reception.search.record.detailrecord.DetailRecordActivity;

import com.example.androidhms.staff.vo.StaffVO;
import com.example.androidhms.util.Util;
import com.example.conn.ApiClient;

public class ReceptionActivity extends AppCompatActivity  {

        ActivityReceptionBinding bind;
        private StaffVO staff = Util.staff;

    @SuppressLint("SuspiciousIndentation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind= ActivityReceptionBinding.inflate(getLayoutInflater());
       setContentView(bind.getRoot());

        if (staff == null) staff = Util.getStaff(this);
        bind.tvName.setText(staff.getName());

        //사원이름 받아오기
        /*   Intent intent =getIntent();
         staff_name =(StaffVO)intent.getSerializableExtra("staff_name");
        Log.d("로그", "onCreate: " + staff_name.getName());
        bind.tvName.setText(staff_name.getName());
        //로그인하면 상단바에 로그아웃뜨기
        bind.toolbar.tvLogin.setVisibility(View.VISIBLE);*/


        bind.toolbar.llLogo.setOnClickListener(v -> {
            onBackPressed();
        });
        bind.toolbar.llLogo.setOnClickListener(v -> {
            onBackPressed();
        });

        bind.llSearchAppointment.setOnClickListener(v -> {
            Intent intent = new Intent(ReceptionActivity.this,AppointmentActivity.class);
            startActivity(intent);
        });
        bind.llSearchPatient.setOnClickListener(v -> {
            Intent intent =new Intent(ReceptionActivity.this,SearchActivity.class);
            startActivity(intent);
        });
        bind.llSearchVisit.setOnClickListener(v -> {
            Intent intent = new Intent(ReceptionActivity.this, DetailRecordActivity.class);
            startActivity(intent);
        });

    }


    //intent 메소드
    public void changeActivity(Activity activity){
        Intent intent = new Intent(ReceptionActivity.this,activity.getClass());
        startActivity(intent);
    }
    //프래그먼트 연결
    public void changeFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
    }

}
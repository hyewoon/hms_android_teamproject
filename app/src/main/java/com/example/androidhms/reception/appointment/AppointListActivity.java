package com.example.androidhms.reception.appointment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.androidhms.R;
import com.example.androidhms.databinding.ActivityAppointListBinding;
import com.example.androidhms.reception.vo.MedicalReceiptVO;

import java.util.ArrayList;

public class AppointListActivity extends AppCompatActivity {

    ArrayList<MedicalReceiptVO> list;
    ActivityAppointListBinding bind;
    int postion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityAppointListBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        Intent intent = getIntent();
        list = (ArrayList<MedicalReceiptVO>)intent.getSerializableExtra("list");
        postion = intent.getIntExtra("position",-1);


        bind.tvName.setText(list.get(postion).getPatient_name());
        bind.tvDate.setText(list.get(postion).getTime());
        bind.tvDay.setText(list.get(postion).getReserve_today());
        bind.tvTime.setText(list.get(postion).getReserve_time());
        bind.tvDepartment.setText(list.get(postion).getDepartment_name());
        bind.tvDoctor.setText(list.get(postion).getDoctor_name());

        bind.toolbar.llLogo.setOnClickListener(v -> {
            onBackPressed();
        });
        bind.toolbar.ivLeft.setOnClickListener(v -> {
            onBackPressed();
        });


    }
}
package com.example.androidhms.reception.search.record.detailrecord;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.androidhms.R;
import com.example.androidhms.databinding.ActivityDetailBinding;
import com.example.androidhms.reception.vo.MedicalRecordVO;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    ActivityDetailBinding bind;
    ArrayList<MedicalRecordVO> recordList;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        bind.toolbar.ivLeft.setOnClickListener(v -> {
            onBackPressed();
        });
        bind.toolbar.llLogo.setOnClickListener(v -> {
            onBackPressed();
        });


        Intent intent = getIntent();
        recordList = (ArrayList<MedicalRecordVO>)intent.getSerializableExtra("recordList");
        position = (int)intent.getIntExtra("position", -1);
        Log.d("로그", "onCreate: " + "받은값" + position);
        Log.d("로그", "onCreate: " + "받은값" + recordList);

        bind.tvDate.setText(recordList.get(position).getRecord_date());
        bind.tvTime.setText(recordList.get(position).getRecord_time());
        bind.tvDepartment.setText(recordList.get(position).getDepartment_name());
        bind.tvDoctor.setText(recordList.get(position).getDoctor());
        bind.tvSymptom.setText(recordList.get(position).getTreatment_name());
        bind.tvMedication.setText(recordList.get(position).getPrescription_name());
        bind.tvMemo.setText(recordList.get(position).getMemo());

        if((recordList.get(position).getAdmission().equals("N"))) {
            bind.tvSelect.setText("일반외래");
        }else {
            bind.tvSelect.setText("입원");
        }


    }
}
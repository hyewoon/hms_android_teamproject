package com.example.androidhms.customer.info.medical;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidhms.customer.vo.AdmissionRecordVO;
import com.example.androidhms.customer.vo.MedicalRecordVO;
import com.example.androidhms.databinding.ActivityCustomerMedicalBinding;
import com.example.conn.ApiClient;
import com.example.conn.RetrofitMethod;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class MedicalRecordActivity extends AppCompatActivity {
    private ActivityCustomerMedicalBinding bind;
    private ArrayList<MedicalRecordVO> medical_record = new ArrayList<>();
    private ArrayList<AdmissionRecordVO> admission_record = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityCustomerMedicalBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        ApiClient.setBASEURL("http://211.223.59.99:3301/hms/");

        Intent intent = getIntent();

        bind.toolbar.tvPage.setText("진료·입원 기록");
        bind.toolbar.ivLeft.setOnClickListener(v -> {
            onBackPressed();
            finish();
        });


        //진료기록 받아오기
        new RetrofitMethod().setParams("patient_id", intent.getIntExtra("patient_id", 0)).sendPost("medical_record.cu", (isResult, data) -> {
            medical_record = new Gson().fromJson(data, new TypeToken<ArrayList<MedicalRecordVO>>(){}.getType());
            MedicalRecordAdapter medicalRecordAdapter = new MedicalRecordAdapter(getLayoutInflater(), MedicalRecordActivity.this, medical_record);
            if (medical_record.size() > 0) {
                bind.rcvMedicalRecord.setAdapter(medicalRecordAdapter);
                bind.rcvMedicalRecord.setLayoutManager(new LinearLayoutManager(MedicalRecordActivity.this, RecyclerView.VERTICAL, false));
            }else {
                bind.cvMedicalExist.setVisibility(View.GONE);
                bind.cvMedicalNone.setVisibility(View.VISIBLE);
            }
 

        });
        
        //입원기록 받아오기
        new RetrofitMethod().setParams("patient_id", intent.getIntExtra("patient_id", 0)).sendPost("admission_record.cu", (isResult, data) -> {
            admission_record = new Gson().fromJson(data, new TypeToken<ArrayList<AdmissionRecordVO>>(){}.getType());
            AdmissionRecordAdapter admissionRecordAdapter = new AdmissionRecordAdapter(getLayoutInflater(), MedicalRecordActivity.this, admission_record);
            if (admission_record.size() > 0) {
                bind.rcvAdmissionRecord.setAdapter(admissionRecordAdapter);
                bind.rcvAdmissionRecord.setLayoutManager(new LinearLayoutManager(MedicalRecordActivity.this, RecyclerView.VERTICAL, false));
            }else {
                bind.cvAdmissionExist.setVisibility(View.GONE);
                bind.cvAdmissionNone.setVisibility(View.VISIBLE);
            }
     

        });







    }
}
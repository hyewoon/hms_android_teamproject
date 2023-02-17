package com.example.androidhms.customer.info.reservation;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidhms.customer.vo.AdmissionRecordVO;
import com.example.androidhms.customer.vo.MedicalReceiptVO;
import com.example.androidhms.databinding.ActivityCustomerReservationRecordBinding;
import com.example.conn.ApiClient;
import com.example.conn.RetrofitMethod;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class ReservationScheduleActivity extends AppCompatActivity {
    private ActivityCustomerReservationRecordBinding bind;
    private ArrayList<MedicalReceiptVO> receipt = new ArrayList<>();
    private AdmissionRecordVO admission;
    private int patient_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityCustomerReservationRecordBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        ApiClient.setBASEURL("http://192.168.0.116/hms/");

        Intent intent = getIntent();

        bind.toolbar.tvPage.setText("예약 조회");
        bind.toolbar.ivLeft.setOnClickListener(v -> {
            onBackPressed();
            finish();
        });

       patient_id = intent.getIntExtra("patient_id", 0);

        //입원일정 조회
        new RetrofitMethod().setParams("patient_id", patient_id).sendPost("admission_schedule.cu", (isResult, data) -> {
            admission = new Gson().fromJson(data, AdmissionRecordVO.class);
            try {
                bind.tvDepartment.setText(admission.getDepartment_name());
                bind.tvName.setText(admission.getName());
                bind.tvAdmissionDate.setText(admission.getAdmission_date().substring(0, 10));
                bind.tvDischargeDate.setText(admission.getDischarge_date().substring(0, 10));
                bind.tvWardNumber.setText(admission.getWard_number() + "호");
                bind.tvBed.setText(admission.getBed() + "번 침대");

            }catch (Exception e) {
                Log.d("로그", "onCreate: " + e);
                bind.cvAdmissionExist.setVisibility(View.GONE);
                bind.cvAdmissionNone.setVisibility(View.VISIBLE);
            }
        });


        //예약일정 조회
        new RetrofitMethod().setParams("patient_id", patient_id).sendPost("receipt_record.cu", (isResult, data) -> {
            receipt = new Gson().fromJson(data, new TypeToken<ArrayList<MedicalReceiptVO>>(){}.getType());
            MedicalReservationAdapter adapter_medical = new MedicalReservationAdapter(getLayoutInflater(), ReservationScheduleActivity.this, receipt);
            if (receipt.size() == 0) {
                bind.rcvMedicalReservation.setVisibility(View.GONE);
                bind.cvMedicalNone.setVisibility(View.VISIBLE);
            }else {
                try {
                    bind.rcvMedicalReservation.setAdapter(adapter_medical);
                    bind.rcvMedicalReservation.setLayoutManager(new LinearLayoutManager(ReservationScheduleActivity.this, RecyclerView.VERTICAL, false));
                }catch (Exception e) {
                    Log.d(TAG, "에러" + e);
                }
            }
        });










    }
}
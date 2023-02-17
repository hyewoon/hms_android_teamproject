package com.example.androidhms.reception.search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionValues;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.androidhms.R;
import com.example.androidhms.databinding.ActivitySearchBinding;
import com.example.androidhms.databinding.ActivityStaffMyPageBinding;
import com.example.androidhms.databinding.DialogReceptionAppointmentBinding;
import com.example.androidhms.reception.search.record.detailrecord.DetailRecordActivity;
import com.example.androidhms.reception.vo.MedicalReceiptVO;
import com.example.androidhms.reception.vo.MedicalRecordVO;
import com.example.androidhms.reception.vo.PrescriptionVO;
import com.example.androidhms.reception.vo.WardVO;
import com.example.androidhms.staff.vo.PatientVO;
import com.example.conn.RetrofitMethod;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    //binding 오류시 싱크해주기
    ActivitySearchBinding bind;
    PatientVO vo;
    //MedicalReceiptVO vo1;
    //MedicalRecordVO vo2;
    //PrescriptionVO vo3;
    //WardVO vo4;
    String patient_id;
    String from;
    String to;
    ArrayList<MedicalReceiptVO> appointList;
    ArrayList<MedicalRecordVO> recordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        Intent intent = getIntent();
        //환자정보검색
        bind.btnSearch.setOnClickListener(v -> {
            new RetrofitMethod().setParams("name",bind.editPatient.getText().toString()).sendPost("patient.re", new RetrofitMethod.CallBackResult() {
                @Override
                public void result(boolean isResult, String data) {
                    Log.d("로그", "result: "+ data + "인적사항");
                    ArrayList<PatientVO> patientList = new Gson().fromJson(data, new TypeToken<ArrayList<PatientVO>>() {
                    }.getType());
                    if(patientList == null || patientList.size()== 0){
                        Toast.makeText(SearchActivity.this, "환자 정보가 없습니다", Toast.LENGTH_SHORT).show();

                    }else if(patientList.size() == 1){
                        Log.d("로그", "result: " + "클릭이벤트");
                        patient_id =patientList.get(0).getPatient_id()+"";
                        Log.d("로그", "result: " +patient_id);
                        searchPatientInfo();
                        searchAppointment();
                        searchMedicalRecord();

                    } else{
                        Log.d("로그", "result: " + "동명이인");
                        bind.llNameList.setVisibility(View.VISIBLE);
                        bind.recvNameList.setAdapter(new PatientNameAdapter(getLayoutInflater(),vo,patientList , SearchActivity.this));
                        bind.recvNameList.setLayoutManager(new LinearLayoutManager(SearchActivity.this,RecyclerView.VERTICAL,false));
                    }
                }
            });
        });
       /* //새로고침
        bind.refresh.setOnClickListener(v -> {
            finish();
            overridePendingTransition(0,0);
            Intent refresh_intent= getIntent();
            startActivity(refresh_intent);
            overridePendingTransition(0,0);
        });*/

        //qr스캐너
        bind.qrScanner.setOnClickListener(v -> {
            Intent scan_intent = new Intent(SearchActivity.this, QrSacnnerActivity.class);
            startActivity(scan_intent);
        });


        //예약정보 상세보기 클릭
        bind.llAppointment.setOnClickListener(v -> {
            Dialog dialog;
            dialog = new Dialog(SearchActivity.this);
            dialog.setContentView(R.layout.dialog_reception_appointment);
            DialogReceptionAppointmentBinding dBind = DialogReceptionAppointmentBinding.inflate(getLayoutInflater());
            dialog.setContentView(dBind.getRoot());
            dBind.tvPatientName.setText(appointList.get(0).getPatient_name());
            dBind.tvReserveDate.setText(appointList.get(0).getReserve_date());
            dBind.tvReserveTime.setText(appointList.get(0).getReserve_time());
            dBind.tvReserveDay.setText(appointList.get(0).getReserve_day());
            dBind.tvReserveDepartment.setText(appointList.get(0).getDepartment_name());
            dBind.tvDoctorName.setText(appointList.get(0).getDoctor_name());
            dBind.tvFloor.setText(appointList.get(0).getLocation());

           dialog.create();
            dialog.show();

            dBind.ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   dialog.dismiss();
                }
            });
        });

        //상세진료정보로 이동
        bind.ivMore.setOnClickListener(v -> {
            Intent record_intent =new Intent(SearchActivity.this,DetailRecordActivity.class);
            record_intent.putExtra("recordList",recordList);
            Log.d("로그", "onCreate: " + recordList);
            startActivity(record_intent);
        });

        bind.toolbar.ivLeft.setOnClickListener(this);
        bind.toolbar.llLogo.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_search) {
        } else if (v.getId() == R.id.iv_left) {
            onBackPressed();
        } else if (v.getId() == R.id.ll_logo) {
            onBackPressed();
        }
    }
    //환자 인적사항 조회
    public void searchPatientInfo() {
        new RetrofitMethod().setParams("id",patient_id).sendPost("id.re", new RetrofitMethod.CallBackResult() {
            @Override
            public void result(boolean isResult, String data) {
                Log.d("로그", "result: " + patient_id + "받은 값");
                ArrayList<PatientVO> patientList = new Gson().fromJson(data, new TypeToken<ArrayList<PatientVO>>() {
                }.getType());
                    bind.tvPatentId.setText(patientList.get(0).getPatient_id()+"");
                    bind.tvName.setText(patientList.get(0).getName());
                    bind.tvGender.setText(patientList.get(0).getGender());
                    bind.tvSocialId.setText(patientList.get(0).getSocial_id());
                    bind.tvPhone.setText(patientList.get(0).getPhone_number());
                  // bind.tvAdmission.setText(patientList.get(0).);
            }
        });
    }
    //예약정보 조회- 최신 한 껀 조회
   public void searchAppointment(){
        new RetrofitMethod().setParams("id",patient_id ).sendPost("appointment.re", new RetrofitMethod.CallBackResult() {
            @Override
            public void result(boolean isResult, String data) {
                 appointList = new Gson().fromJson(data, new TypeToken<ArrayList<MedicalReceiptVO>>() {
                }.getType());
                if(appointList == null || appointList.size() ==0){
                    bind.llAppointment.setVisibility(View.GONE);
                }else {
                    bind.date.setText(appointList.get(0).getReserve_date());
                    bind.day.setText(appointList.get(0).getReserve_day());
                    bind.time.setText(appointList.get(0).getReserve_time());
                    bind.departmentName.setText(appointList.get(0).getDepartment_name());
                    bind.doctorName.setText(appointList.get(0).getDoctor_name());
                    bind.llAppointment.setVisibility(View.VISIBLE);

                }
            }

        });
    }
    //진료기록 조회
    public void searchMedicalRecord(){
        new RetrofitMethod().setParams("id", patient_id).sendPost("medical_record_id.re", new RetrofitMethod.CallBackResult() {
            @Override
            public void result(boolean isResult, String data) {
                Log.d("로그", "result: " + "진료기록" + data);
                 recordList=  new Gson().fromJson(data, new TypeToken<ArrayList<MedicalRecordVO>>(){}.getType());
                   if(recordList == null || recordList.size()==0) {
                       bind.llMedicalRecord.setVisibility(View.INVISIBLE);

                   }else{
                       String select =recordList.get(0).getAdmission();
                       if(select.equals("N")){
                           bind.tvSelect.setText("일반외래");
                       }else{
                           bind.tvSelect.setText("입원");
                       }
                       bind.tvDate.setText(recordList.get(0).getRecord_date());
                       bind.tvTime.setText(recordList.get(0).getRecord_time());
                       bind.tvSymptom.setText(recordList.get(0).getTreatment_name());
                       bind.tvMemo.setText(recordList.get(0).getMemo());
                       bind.tvDoctor.setText(recordList.get(0).getDoctor());
                       bind.tvDepartment.setText(recordList.get(0).getDepartment_name());
                       bind.tvMedication.setText(recordList.get(0).getPrescription_name());
                       bind.llMedicalRecord.setVisibility(View.VISIBLE);
                                /* bind.recvMedicalRecord.setAdapter(new SearchMedicalRecordAdapter(getLayoutInflater(),vo2,recordList));
                                bind.recvMedicalRecord.setLayoutManager(new LinearLayoutManager(SearchActivity.this, RecyclerView.VERTICAL,false));*/
                   }
            }
        });
    }


}
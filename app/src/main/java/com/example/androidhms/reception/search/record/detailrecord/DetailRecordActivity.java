package com.example.androidhms.reception.search.record.detailrecord;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.androidhms.R;
import com.example.androidhms.databinding.ActivityDetailRecordBinding;
import com.example.androidhms.reception.appointment.AppointmentActivity;
import com.example.androidhms.reception.search.PatientNameAdapter;
import com.example.androidhms.reception.search.SearchActivity;
import com.example.androidhms.reception.search.SearchMedicalRecordAdapter;
import com.example.androidhms.reception.vo.MedicalReceiptVO;
import com.example.androidhms.reception.vo.MedicalRecordVO;
import com.example.androidhms.staff.vo.PatientVO;
import com.example.conn.RetrofitMethod;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DetailRecordActivity extends AppCompatActivity {

    ActivityDetailRecordBinding bind;
    DatePickerDialog datePickerDialog;
    ArrayList<MedicalRecordVO> recordList;
    MedicalRecordVO vo;
    PatientVO patientVO;
    String from;
    String to;
    String patient_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityDetailRecordBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        Intent record_intent = getIntent();
        recordList = (ArrayList<MedicalRecordVO>) record_intent.getSerializableExtra("recordList");

        if (recordList == null || recordList.size() == 0) {
            Log.d("로그", "onCreate: " + "연결");
        } else {
            bind.recvDetailRecord.setAdapter(new SearchMedicalRecordAdapter(getLayoutInflater(), vo, recordList));
            bind.recvDetailRecord.setLayoutManager(new LinearLayoutManager(DetailRecordActivity.this, RecyclerView.VERTICAL, false));
        }

        bind.toolbar.llLogo.setOnClickListener(v -> {
            onBackPressed();
        });
        bind.toolbar.ivLeft.setOnClickListener(v -> {
            onBackPressed();
        });

        //검색버튼 클릭
        bind.btnSearch.setOnClickListener(v -> {
            new RetrofitMethod().setParams("name", bind.editPatient.getText().toString()).sendPost("patient.re", new RetrofitMethod.CallBackResult() {
                @Override
                public void result(boolean isResult, String data) throws Exception {
                    ArrayList<PatientVO> patientList = new Gson().fromJson(data, new TypeToken<ArrayList<PatientVO>>() {
                    }.getType());
                    if (patientList.size() == 0 || patientList == null) {
                        Toast.makeText(DetailRecordActivity.this, "검색 기록이 없습니다", Toast.LENGTH_SHORT).show();
                    } else if (patientList.size() == 1) {
                        patient_id = patientList.get(0).getPatient_id() + "";
                        new RetrofitMethod().setParams("id", patient_id).sendPost("medical_record_id.re", new RetrofitMethod.CallBackResult() {
                            @Override
                            public void result(boolean isResult, String data) {
                                Log.d("로그", "result: " + "진료기록" + data);
                                ArrayList<MedicalRecordVO> recordList = new Gson().fromJson(data, new TypeToken<ArrayList<MedicalRecordVO>>() {
                                }.getType());
                                if (recordList == null || recordList.size() == 0) {
                                    Toast.makeText(DetailRecordActivity.this, "진료이력이 없습니다", Toast.LENGTH_SHORT).show();
                                } else {
                                    bind.recvDetailRecord.setAdapter(new SearchMedicalRecordAdapter(getLayoutInflater(), vo, recordList));
                                    bind.recvDetailRecord.setLayoutManager(new LinearLayoutManager(DetailRecordActivity.this, RecyclerView.VERTICAL, false));

                                }
                            }

                        });

                    }
                }

            });
        });

      /*  bind.ivFrom.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            int year = c.get(c.YEAR);
            int month = c.get(c.MONTH);
            int day = c.get(c.DAY_OF_MONTH);
            datePickerDialog = new DatePickerDialog(DetailRecordActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int day) {
                    month = month + 1;
                    //  String date = year + "  년  " + month + " 월  " + day + "일";
                    from = year + "-" + String.format("%02d", month) + "-" + String.format("%02d", day);
                    bind.tvFrom.setText(from);
                    //java 숫자 왼쪽에 0으로 채우기
                    bind.ivTo.setOnClickListener(v -> {
                        Calendar c = Calendar.getInstance();
                        int year2 = c.get(c.YEAR);
                        int month2 = c.get(c.MONTH);
                        int day2 = c.get(c.DAY_OF_MONTH);
                        datePickerDialog = new DatePickerDialog(DetailRecordActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year2, int month2, int day2) {
                                month2 = month2 + 1;
                                //  String date = year + "  년  " + month + " 월  " + day + "일";
                                to = year2 + "-" + String.format("%02d", month2) + "-" + String.format("%02d", day2);
                                bind.tvTo.setText(to);
                                //java 숫자 왼쪽에 0으로 채우기
                                new RetrofitMethod().setParams("id", patient_id).setParams("from", from).setParams("to", to).sendPost("medical_reocrd.re", new RetrofitMethod.CallBackResult() {
                                    @Override
                                    public void result(boolean isResult, String data) throws Exception {
                                        ArrayList<MedicalRecordVO> recordList = new Gson().fromJson(data, new TypeToken<ArrayList<MedicalRecordVO>>() {
                                        }.getType());
                                        Log.d("로그", "result: " +data );
                                        if (recordList == null || recordList.size() == 0) {
                                            Toast.makeText(DetailRecordActivity.this, "진료이력이 없습니다", Toast.LENGTH_SHORT).show();

                                        } else {
                                            bind.recvDetailRecord.setAdapter(new SearchMedicalRecordAdapter(getLayoutInflater(), vo, recordList));
                                            bind.recvDetailRecord.setLayoutManager(new LinearLayoutManager(DetailRecordActivity.this, RecyclerView.VERTICAL, false));
                                        }
                                    }
                                });
                            }
                        }, year2, month2, day2);
                        datePickerDialog.show();
                    });

                }
            }, year, month, day);
            datePickerDialog.show();

        });*/


    }
}

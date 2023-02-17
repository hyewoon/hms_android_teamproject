package com.example.androidhms.reception.appointment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.androidhms.R;
import com.example.androidhms.databinding.ActivityAppointmentBinding;
import com.example.androidhms.reception.vo.MedicalReceiptVO;
import com.example.conn.ApiClient;
import com.example.conn.RetrofitMethod;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AppointmentActivity extends AppCompatActivity {

    ActivityAppointmentBinding bind;
    DatePickerDialog datePickerDialog;
    ArrayList<MedicalReceiptVO> list;
    String date2;
    String doctor_name;
    int doctor_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityAppointmentBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        Intent intent = getIntent();

        bind.toolbar.ivLeft.setOnClickListener(v -> {
            onBackPressed();
        });
        bind.toolbar.llLogo.setOnClickListener(v -> {
            onBackPressed();
        });

        bind.llConfirmDate.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            int year = c.get(c.YEAR);
            int month = c.get(c.MONTH);
            int day = c.get(c.DAY_OF_MONTH);
            datePickerDialog = new DatePickerDialog(AppointmentActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int day) {
                    month = month + 1;
                    String date = year + "  년  " + month + " 월  " + day + "일";
                    date2 = year + "-" + String.format("%02d", month) + "-" + String.format("%02d", day);
                    bind.tvShowDate.setText(date);
                    //java 숫자 왼쪽에 0으로 채우기
                    getAppointment();
                }
            }, year, month, day);
            datePickerDialog.show();
        });




     /*   ArrayAdapter department_list = ArrayAdapter.createFromResource(this, R.array.department_list, android.R.layout.simple_spinner_dropdown_item);
        //내가 지정한 리스트department_list, 기본 제공하는 드롬다운 simple_spinner_dropdown_item
        department_list.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bind.spinnerDoctor.setAdapter(department_list);

        bind.spinnerDoctor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                doctor_id = position;

               // doctor_name =  bind.spinnerDoctor.getSelectedItem().toString();
               // Log.d("로그", "onItemSelected: " +doctor_name );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });*/


    }

    public void getAppointment(){
        new RetrofitMethod().setParams("time",date2 ).sendPost("apointmentList.re", (isResult, data) -> {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();
            list = gson.fromJson(data, new TypeToken<ArrayList<MedicalReceiptVO>>() {}.getType());
            Log.d("로그", "getAppointment: "+ "이름값" + data);

                if (list == null || list.size() == 0) {
                    bind.cardvAppointmentList.setVisibility(View.INVISIBLE);
                    bind.tvCountAll.setVisibility(View.INVISIBLE);
                    bind.tvCountWaiting.setVisibility(View.INVISIBLE);
                } else {
                    int a = list.size();
                    bind.cardvAppointmentList.setVisibility(View.VISIBLE);
                    bind.recvAppointmentList.setAdapter(new AppointmentAdapter(getLayoutInflater(), list, AppointmentActivity.this));
                    bind.recvAppointmentList.setLayoutManager(new LinearLayoutManager(AppointmentActivity.this, RecyclerView.VERTICAL, false));
                    //int all= list.size();
                    //Log.d("로그", "onDateSet: " +list.size() );
                    bind.tvCountAll.setVisibility(View.VISIBLE);
                    bind.tvCountWaiting.setVisibility(View.VISIBLE);
                    bind.tvCountAll.setText(list.size() + "");
                    int count = 0;
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getReserve_time_count().compareTo(list.get(i).getCurrent_time()) > 0) {
                            count++;
                        }
                    }
                    bind.tvCountWaiting.setText(count + "");
                }
            });
        }
    }

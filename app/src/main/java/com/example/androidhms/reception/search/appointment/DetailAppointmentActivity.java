package com.example.androidhms.reception.search.appointment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.androidhms.R;
import com.example.androidhms.reception.search.SearchActivity;
import com.example.androidhms.reception.search.record.detailrecord.DetailRecordActivity;
import com.example.androidhms.staff.vo.PatientVO;

import java.util.ArrayList;

public class DetailAppointmentActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_appointment);

        Intent appoint_intent =getIntent();

    }
}
package com.example.androidhms.reception.search;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.androidhms.R;
import com.example.androidhms.databinding.ActivityQrSacnnerBinding;
import com.google.zxing.integration.android.IntentIntegrator;
import com.journeyapps.barcodescanner.CaptureActivity;

public class QrSacnnerActivity extends AppCompatActivity {

   ActivityQrSacnnerBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind= ActivityQrSacnnerBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        Intent scan_intent = getIntent();


        IntentIntegrator integrator = new IntentIntegrator(this);

        //바코드 안의 텍스트
        integrator.setPrompt("바코드를 사각형 안에 비춰주세요");

        //바코드 인식시 소리 여부
        integrator.setBeepEnabled(false);


        integrator.setBarcodeImageEnabled(true);

        integrator.setCaptureActivity(CaptureActivity.class);

        //바코드 스캐너 시작
        integrator.initiateScan();
    }

}
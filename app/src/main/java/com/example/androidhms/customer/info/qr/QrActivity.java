package com.example.androidhms.customer.info.qr;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidhms.databinding.ActivityCustomerQrBinding;
import com.example.conn.RetrofitMethod;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QrActivity extends AppCompatActivity {
    private ActivityCustomerQrBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityCustomerQrBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        bind.toolbar.tvPage.setText("QR 접수");

        bind.toolbar.ivLeft.setOnClickListener(v -> {
            onBackPressed();
        });

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt("가까이 대주세요");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();

        bind.btnReceipt.setOnClickListener(v -> {
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
            integrator.setPrompt("가까이 대주세요");
            integrator.setCameraId(0);
            integrator.setBeepEnabled(true);
            integrator.setBarcodeImageEnabled(true);
            integrator.initiateScan();
        });






    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        Intent intent = getIntent();
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                // todo
            } else {
                onBackPressed();
                new RetrofitMethod().setParams("patient_id", intent.getIntExtra("patient_id", 0))
                        .setParams("staff_id", result.getContents())
                        .sendPost("qr_receipt.cu", (isResult, data1) -> {
                            Log.d("로그", "접수완료 : " + "환자 " + intent.getIntExtra("patient_id", 0) + " 의사 " + result.getContents());
                            Toast.makeText(this, "접수가 완료 되었습니다", Toast.LENGTH_SHORT).show();
                        });
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}
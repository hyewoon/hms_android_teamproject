package com.example.androidhms.customer.info.card;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidhms.customer.vo.CustomerVO;
import com.example.androidhms.databinding.ActivityCustomerCardBinding;

public class CardActivity extends AppCompatActivity {
    private ActivityCustomerCardBinding bind;
    private CustomerVO customer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityCustomerCardBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        //고객정보 받아오기
        Intent intent = getIntent();
        customer = (CustomerVO) intent.getSerializableExtra("customer");

      /*  bind.tvName.setText(customer.getName());
        bind.tvPatientId.setText(customer.getPatient_id() + "");
        bind.tvSocialId.setText(customer.getSocial_id() + "");
        bind.tvBloodtype.setText(customer.getBlood_type());
        bind.tvHeight.setText(customer.getHeight() + "cm");
        bind.tvWeight.setText(customer.getWeight() + "kg");
        if (customer.getAllergy() == null) {
            bind.tvAllergy.setText("없음");
        }else {
            bind.tvAllergy.setText(customer.getAllergy());
        }
        if (customer.getUnderlying_disease() == null) {
            bind.tvUnderlying.setText("없음");
        }else {
            bind.tvUnderlying.setText(customer.getUnderlying_disease());
        }*/



    }
}
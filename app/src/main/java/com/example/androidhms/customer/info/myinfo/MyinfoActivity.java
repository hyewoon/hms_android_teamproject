package com.example.androidhms.customer.info.myinfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.androidhms.R;
import com.example.androidhms.customer.vo.CustomerVO;
import com.example.androidhms.databinding.ActivityCustomerMyinfoBinding;

public class MyinfoActivity extends AppCompatActivity {
    private ActivityCustomerMyinfoBinding bind;
    private CustomerVO customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityCustomerMyinfoBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());



        //고객정보 받아오기
        Intent intent = getIntent();
        customer = (CustomerVO) intent.getSerializableExtra("customer");

        bind.btnBack.setVisibility(View.GONE);
        bind.btnOk.setVisibility(View.GONE);

        //고객정보 조회창
        changeFragment(new SelectFragment(customer));

        bind.btnBack.setOnClickListener(v -> {
            changeFragment(new SelectFragment(customer));
            bind.btnUpdate.setVisibility(View.VISIBLE);
            bind.btnBack.setVisibility(View.GONE);
            bind.btnOk.setVisibility(View.GONE);
        });

        bind.btnUpdate.setOnClickListener(v -> {
            changeFragment(new UpdateFragment(customer));
            bind.btnUpdate.setVisibility(View.GONE);
            bind.btnBack.setVisibility(View.VISIBLE);
            bind.btnOk.setVisibility(View.VISIBLE);
        });


    }

    public void changeFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
    }
}
package com.example.androidhms.customer.join;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidhms.customer.vo.CustomerVO;
import com.example.androidhms.databinding.ActivityCustomerCheckBinding;
import com.example.conn.RetrofitMethod;
import com.google.gson.Gson;

public class CustomerCheckActivity extends AppCompatActivity {
    private ActivityCustomerCheckBinding bind;
    private CustomerVO customer;
    private String name, socail_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityCustomerCheckBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());




        bind.btnCheck.setOnClickListener(v -> {
            name = bind.etName.getText().toString();
            socail_id = bind.etSocialId.getText().toString();
            new RetrofitMethod().setParams("name", name).setParams("social_id", socail_id)
                    .sendPost("customer_check.cu", (isResult, data) -> {
                        if (data.equals("null")) {
                            Intent intent = new Intent(CustomerCheckActivity.this, PatientRegisterActivity.class);
                            intent.putExtra("name", bind.etName.getText().toString());
                            intent.putExtra("social_id", bind.etSocialId.getText().toString());
                            startActivity(intent);
                            finish();
                        }else {
                            customer = new Gson().fromJson(data, CustomerVO.class);
                            Intent intent = new Intent(CustomerCheckActivity.this, JoinActivity.class);
                            intent.putExtra("customer", customer);
                            startActivity(intent);
                            finish();
                        }
                    });

        });



    }
}
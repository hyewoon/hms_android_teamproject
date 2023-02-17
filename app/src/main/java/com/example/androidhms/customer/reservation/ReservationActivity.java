package com.example.androidhms.customer.reservation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.androidhms.R;
import com.example.androidhms.databinding.ActivityCustomerReservationBinding;

public class ReservationActivity extends AppCompatActivity {
    private ActivityCustomerReservationBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityCustomerReservationBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        bind.tvCancel.setOnClickListener(v -> {
            onBackPressed();
        });

        Intent intent = getIntent();

        if (intent.getIntExtra("search", 0) == 1) {
            StepCnt.cnt = 3;
            changeStep();
            ReservationSelect.selectedStaff_id = intent.getIntExtra("staff_id", 0);
            ReservationSelect.selectedDepartment_id = intent.getIntExtra("department_id", 0);
        }else {
            StepCnt.cnt = 1;
            changeStep();
        }



        bind.imgvBefore.setOnClickListener(v -> {
            StepCnt.cnt = StepCnt.cnt - 1;
            changeStep();
        });
    }

    public void changeFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
    }

    public void changeStep() {
            if (StepCnt.cnt == 1) {
                changeFragment(new StepOneFragment());
                bind.tvStep.setText("STEP 1");
                bind.tvSelect.setText("진료과 선택");
                bind.imgvBefore.setVisibility(View.INVISIBLE);
            } else if (StepCnt.cnt == 2) {
                changeFragment(new StepTwoFragment());
                bind.tvStep.setText("STEP 2");
                bind.tvSelect.setText("의료진 선택");
                bind.imgvBefore.setVisibility(View.VISIBLE);
            } else if (StepCnt.cnt == 3) {
                changeFragment(new StepThreeFragment());
                bind.tvStep.setText("STEP 3");
                bind.tvSelect.setText("날짜 선택");
                bind.imgvBefore.setVisibility(View.VISIBLE);
            } else if (StepCnt.cnt == 4) {
                changeFragment(new StepFourFragment());
                bind.tvStep.setText("STEP 4");
                bind.tvSelect.setText("시간 선택");
                bind.imgvBefore.setVisibility(View.VISIBLE);
            }
    }


}
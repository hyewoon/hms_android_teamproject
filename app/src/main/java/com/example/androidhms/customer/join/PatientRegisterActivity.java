package com.example.androidhms.customer.join;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.androidhms.R;
import com.example.androidhms.customer.common.FragmentControl;
import com.example.androidhms.databinding.ActivityCustomerPatientRegisterBinding;

public class PatientRegisterActivity extends AppCompatActivity {
    private ActivityCustomerPatientRegisterBinding bind;
    private FragmentControl control;
    private PatientRegisterFragment patientRegister;
    private AdditionalFragment additional;
    int social_id;
    String name;
    private String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityCustomerPatientRegisterBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        Intent intent = getIntent();

        if (intent.getStringExtra("email") == null) {
            Log.d(TAG, "일반회원 환자등록");
            Toast.makeText(PatientRegisterActivity.this,  "신규환자등록 후 가입이 가능합니다.", Toast.LENGTH_LONG).show();
            RegisterFactor.getName = intent.getStringExtra("name");
            RegisterFactor.getSocial = intent.getStringExtra("social_id");
        }else if (intent.getStringExtra("email") != null) {
            Log.d(TAG, "소셜회원 환자등록");
            Toast.makeText(PatientRegisterActivity.this,  "신규환자등록 후 가입이 가능합니다.", Toast.LENGTH_LONG).show();
            email = intent.getStringExtra("email");
        }

        control = new FragmentControl(this);
        patientRegister = new PatientRegisterFragment();
        additional = new AdditionalFragment(email);




        change(1);

    }

    public void change(int test){
        switch (test) {
            case 1:
                control.addFragment(R.id.register_container, patientRegister);
                control.showFragment(patientRegister);
                break;
            case 2:
                control.addFragment(R.id.register_container, additional);
                control.hideFragment(patientRegister);
                control.showFragment(additional);
        }

    }

    public void changeFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
    }

}
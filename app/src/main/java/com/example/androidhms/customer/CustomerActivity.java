package com.example.androidhms.customer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidhms.R;
import com.example.androidhms.customer.common.FragmentControl;
import com.example.androidhms.customer.home.HomeFragment;
import com.example.androidhms.customer.hospital.HospitalFragment;
import com.example.androidhms.customer.info.InfoFragment;
import com.example.androidhms.customer.reservation.ReservationActivity;
import com.example.androidhms.customer.vo.CustomerVO;
import com.example.androidhms.databinding.ActivityCustomerBinding;
import com.example.conn.ApiClient;
import com.example.conn.RetrofitMethod;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

public class CustomerActivity extends AppCompatActivity {
    private ActivityCustomerBinding bind;
    private CustomerVO customer;
    private HomeFragment homeFragment;
    private HospitalFragment hospitalFragment;
    private InfoFragment infoFragment;
    private FragmentControl control;
    static final String TAG = "로그";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityCustomerBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        ApiClient.setBASEURL("http://211.223.59.99:3301/hms/");

        homeFragment = new HomeFragment();
        hospitalFragment = new HospitalFragment();

        control = new FragmentControl(this);
        control.addFragment(R.id.customer_container, homeFragment);
        control.addFragment(R.id.customer_container, hospitalFragment);

        control.hideFragment(hospitalFragment);
        control.showFragment(homeFragment);


        Log.d(TAG, "onCreate");









        //뒤로가기
        bind.toolbar.ivLeft.setOnClickListener(v -> {
            onBackPressed();
        });

        //로그아웃
        bind.toolbar.ivLogout.setOnClickListener(v -> {
            LoginInfo.check_id = 0;
            LoginInfo.token = null;
            LoginInfo.push_check = 0;
            this.finish();
        });

        //로그인 액티비티 띄우기
        bind.toolbar.tvLogin.setOnClickListener(v -> {
            Intent intent = new Intent(CustomerActivity.this, CustomerLoginActivity.class);
            activityResultLauncher.launch(intent);
        });

        //로그인시 화면변경
        checkLogin();



        //바텀네비게이션
        BottomNavigationView btm_nav = bind.btmNav;
        btm_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.btm_item1){
                    //홈 프래그먼트 전환
                    control.showFragment(homeFragment);
                    control.hideFragment(hospitalFragment);
                    if (LoginInfo.check_id > 0) {
                        control.hideFragment(infoFragment);

                    }
                } else if(item.getItemId() == R.id.btm_item2){
                    if (LoginInfo.check_id > 0) {
                        //예약 액티비티 전환
                        Intent intent = new Intent(CustomerActivity.this, ReservationActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(CustomerActivity.this,
                                "로그인시 이용 가능합니다.", Toast.LENGTH_SHORT).show();
                    }
                    return false;
                } else if(item.getItemId() == R.id.btm_item3){
                    //병원정보 프래그먼트 전환
                    control.showFragment(hospitalFragment);
                    control.hideFragment(homeFragment);
                    if (LoginInfo.check_id > 0) {
                        control.hideFragment(infoFragment);
                    }
                } else if(item.getItemId() == R.id.btm_item4){
                    //인포 프래그먼트 전환
                    if (LoginInfo.check_id > 0) {
                        control.showFragment(infoFragment);
                        control.hideFragment(homeFragment);
                        control.hideFragment(hospitalFragment);
                    } else {
                        Toast.makeText(CustomerActivity.this,
                                "로그인시 이용 가능합니다.", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
                return true;
            }
        });

    }

    //로그인 액티비티 전환 메소드
    ActivityResultLauncher<Intent> activityResultLauncher
            = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == Activity.RESULT_OK ) {
                Intent intent = result.getData();
                customer = (CustomerVO) intent.getSerializableExtra("customer");
                LoginInfo.check_id = customer.getPatient_id();
                infoFragment = new InfoFragment(customer);
                control.addFragment(R.id.customer_container, infoFragment);
                control.showFragment(homeFragment);
                control.hideFragment(hospitalFragment);
                control.hideFragment(infoFragment);
                homeFragment.changeWelcom(LoginInfo.check_id);

                Log.d(TAG, "patient_id : " + LoginInfo.check_id);
                Log.d(TAG, "push_check : " + LoginInfo.push_check);

                FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
                    new RetrofitMethod().setParams("patient_id", customer.getPatient_id())
                            .setParams("token", task.getResult())
                            .sendPost("token_update.cu", (isResult, data) -> {
                                Log.d(TAG, "토큰 : " + task.getResult());
                                LoginInfo.token = task.getResult();
                                Log.d(TAG, "토큰값 저장");
                            });
                });



                checkLogin();
            }
        }
    });

    //로그인 확인
    public void checkLogin(){
        if (LoginInfo.check_id > 0) {
            new RetrofitMethod().setParams("patient_id", LoginInfo.check_id)
                            .sendPost("customer_info.cu", (isResult, data) -> {
                                customer = new Gson().fromJson(data, CustomerVO.class);
                                bind.toolbar.tvLogin.setVisibility(View.GONE);
                                bind.toolbar.ivLeft.setVisibility(View.GONE);
                                bind.toolbar.ivLogout.setVisibility(View.VISIBLE);
                                bind.toolbar.tvAccount.setVisibility(View.VISIBLE);
                                bind.toolbar.tvAccount.setText(customer.getName() + "님");
                            });
        }
    }
}
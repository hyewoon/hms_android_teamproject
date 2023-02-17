package com.example.androidhms.customer;

import static com.example.androidhms.customer.CustomerActivity.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidhms.customer.join.CustomerCheckActivity;
import com.example.androidhms.customer.join.PatientRegisterActivity;
import com.example.androidhms.customer.vo.CustomerVO;
import com.example.androidhms.databinding.ActivityCustomerLoginBinding;
import com.example.conn.ApiClient;
import com.example.conn.RetrofitMethod;
import com.google.gson.Gson;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;
import com.navercorp.nid.NaverIdLoginSDK;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class CustomerLoginActivity extends AppCompatActivity {

    private ActivityCustomerLoginBinding bind;
    private CustomerVO customer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityCustomerLoginBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        ApiClient.setBASEURL("http://211.223.59.99:3301/hms/");

        NaverIdLoginSDK.INSTANCE.initialize(this, "E_Ez5z_oN4y33fRnHolh", "jPBPbuocrK", "LastProject");
        KakaoSdk.init(this, "c1d3d1509c7d3139f08c5b05080e86d9");



        UserApiClient.getInstance().logout(throwable -> {
            return null;
                });
        UserApiClient.getInstance().unlink(error->{
            return null;
        });


        //일반 로그인
        bind.btnLogin.setOnClickListener(v -> {
            new RetrofitMethod().setParams("email", bind.etEmail.getText().toString())
                    .setParams("pw", bind.etPw.getText().toString())
                    .sendPost("customer_login.cu", (isResult, data) -> {
                        

                        if (data.equals("null")) {
                            Toast.makeText(CustomerLoginActivity.this,
                                    "사번 또는 비밀번호를 확인해 주세요.", Toast.LENGTH_SHORT).show();
                        } else {
                            // CustomerVO에 DB에서 불러온 정보 주입
                            Log.d("로그", "로그인 결과 : " + isResult);
                            Log.d("로그", "로그인 정보 : " + data);
                            customer = new Gson().fromJson(data, CustomerVO.class);
                            LoginInfo.check_id = customer.getPatient_id();
                            Intent intent = new Intent();
                            intent.putExtra("customer", customer);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    });
        });

        //카톡 로그인
        bind.btnKakao.setOnClickListener(v -> {
            Log.d(TAG, "카카오톡 로그인");
            kakaoLogin();
        });

        //회원가입
        bind.btnJoin.setOnClickListener(v -> {
            Intent intent = new Intent(CustomerLoginActivity.this, CustomerCheckActivity.class);
            startActivity(intent);
        });


    }


    // 크롬이 안켜지면 → 크롬 사용정지 후 진행
    private void kakaoLogin() {
        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if (oAuthToken != null)
                    Log.d("로그", "invoke: " + oAuthToken.toString());
                    UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
                    @Override
                    public Unit invoke(User user, Throwable throwable) {
                        socialLogin(user.getKakaoAccount().getEmail());
                        return null;
                    }
                });
                return null;
            }
        };
        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(this)) {
            UserApiClient.getInstance().loginWithKakaoTalk(this, callback);
        }else{
            UserApiClient.getInstance().loginWithKakaoAccount(this, callback);
        }
    }


    public void socialLogin(String email) {
        //소셜 로그인을 통해서 가져온 정보를 Spring 으로 전송하기 (oracle 테이블에 해당하는 메일로 가입한 정보)
        Log.d("로그", "socialLogin: " + email);
        // setParams → 파라미터에 담는다  /  sendPost & senGet → 전송한다
        new RetrofitMethod().setParams("email" , email).sendPost("social.cu", (isResult, data) -> {
            Log.d(TAG, "환자정보 : " + data);
            customer = new Gson().fromJson(data, CustomerVO.class);
            try {
                customer = new Gson().fromJson(data, CustomerVO.class);
                LoginInfo.check_id = customer.getPatient_id();
                Intent intent = new Intent();
                intent.putExtra("customer", customer);
                setResult(RESULT_OK, intent);
                finish();
            }catch (NullPointerException e) {
                Log.d(TAG, "Exception : " + e);
                Intent intent = new Intent(CustomerLoginActivity.this, PatientRegisterActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
                finish();

            }
            if (data == null) {

            }
        });
        // 1. 가입한 정보가 있다면 로그인 성공 처리
        // 2. 가입한 정보가 없다면 회원가입 처리
    }



}
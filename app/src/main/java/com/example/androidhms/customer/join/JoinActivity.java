package com.example.androidhms.customer.join;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidhms.customer.CustomerLoginActivity;
import com.example.androidhms.customer.vo.CustomerVO;
import com.example.androidhms.databinding.ActivityJoinBinding;
import com.example.conn.RetrofitMethod;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JoinActivity extends AppCompatActivity {
    private ActivityJoinBinding bind;
    private int social;
    private String email, pw, getEmail;
    private int validEmail, validPassword, validPasswordCheck;
    private CustomerVO customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityJoinBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        Intent intent = getIntent();

        customer = (CustomerVO) intent.getSerializableExtra("customer");

        Log.d(TAG, "테스트 : " + intent.getStringExtra("email"));

        if (intent.getStringExtra("email").equals("")) {
            getEmail = bind.etEmail.getText().toString();
            bind.tvName.setText(customer.getName());
            bind.tvSocialId.setText(customer.getSocial_id()+"");
            if (customer.getGender().equals("M")) {
                bind.tvGender.setText("남자");
            }else if (customer.getGender().equals("F")) {
                bind.tvGender.setText("여자");
            }
            bind.tvPhone.setText(customer.getPhone_number());
        }else {
            getEmail = intent.getStringExtra("email");
            social = 2;
            Log.d(TAG, "소셜회원 가입 : " + getEmail);
            bind.etEmail.setVisibility(View.GONE);
            bind.btnCheck.setVisibility(View.GONE);
            bind.etPassword.setVisibility(View.GONE);
            bind.etPasswordCheck.setVisibility(View.GONE);
            bind.tvMessage.setVisibility(View.VISIBLE);
            bind.tvEmail.setVisibility(View.VISIBLE);
            bind.tvEmail.setText(getEmail);
        }



        bind.btnCheck.setOnClickListener(v -> {
            if (intent.getStringExtra("email").equals("")) {
                getEmail = bind.etEmail.getText().toString();
            }else {
                getEmail = intent.getStringExtra("email");
                Log.d(TAG, "소셜회원 가입 : " + getEmail);
            }


            if (isValidEmail(getEmail) == true) {
                new RetrofitMethod().setParams("email", getEmail)
                        .sendPost("email_check.cu", (isResult, data) -> {
                            Log.d("로그", data);
                            if (data.equals("null")) {
                                Log.d("로그", "중복된 아이디가 없습니다");
                                bind.tvEmailCheck.setText("사용가능한 이메일입니다");
                                validEmail = 1;
                                email = bind.etEmail.getText().toString();
                            }else {
                                Log.d("로그", "중복된 아이디입니다");
                                bind.tvEmailCheck.setText("이미 가입된 이메일입니다");
                                bind.etEmail.setText(null);
                            }
                        });
            }else {
                bind.tvEmailCheck.setText("사용할 수 없는 이메일입니다");
            }

        });

        //비밀번호 사용가능여부 확인
        bind.etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                bind.tvPasswordCheck.setText(checkPassword(bind.etPassword.getText().toString(), bind.etEmail.getText().toString()));
            }
        });

        //비밀번호 확인 일치 여부
        bind.etPasswordCheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (bind.etPasswordCheck.getText().toString().equals(bind.etPassword.getText().toString())) {
                    bind.tvPasswordDouble.setText("일치합니다");
                    pw = bind.etPassword.getText().toString();
                    validPasswordCheck = 1;
                }else {
                    bind.tvPasswordDouble.setText("일치하지 않습니다");
                }
            }
        });

        bind.btnJoin.setOnClickListener(v -> {
            if (social == 2) {
                email = getEmail;
                pw = "0000";
                new RetrofitMethod().setParams("email", email)
                        .setParams("pw", pw)
                        .setParams("patient_id", customer.getPatient_id())
                        .sendPost("customer_join.cu", (isResult, data) -> {
                            Log.d("로그", "회원가입 완료 : " + email + " " + pw);
                            Intent joinAfter = new Intent(JoinActivity.this, CustomerLoginActivity.class);
                            startActivity(joinAfter);
                        });

            }else {
                if (validEmail == 0) {
                    bind.tvJoinCheck.setText("이메일을 확인해주세요");
                }else if (validPassword == 0) {
                    bind.tvJoinCheck.setText("비밀번호를 확인해주세요");
                }else if (validPasswordCheck == 0) {
                    bind.tvJoinCheck.setText("비밀번호가 일치하지 않습니다");
                }else if (validEmail == 1 && validPassword == 1 && validPasswordCheck == 1) {
                    new RetrofitMethod().setParams("email", email)
                            .setParams("pw", pw)
                            .setParams("patient_id", customer.getPatient_id())
                            .sendPost("customer_join.cu", (isResult, data) -> {
                                Log.d("로그", "회원가입 완료 : " + email + " " + pw);
                                Intent joinAfter = new Intent(JoinActivity.this, CustomerLoginActivity.class);
                                startActivity(joinAfter);
                            });
                }
            }




        });


    }


    //이메일 검증
    private boolean isValidEmail(String email) {
        boolean err = false;
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if(m.matches()) {
            err = true;
        }
        return err;
    }


    //패스워드 검증
    private String checkPassword(String pwd, String id){

        // 비밀번호 포맷 확인(영문, 특수문자, 숫자 포함 8자 이상)
        Pattern passPattern1 = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$");
        Matcher passMatcher1 = passPattern1.matcher(pwd);

        if(!passMatcher1.find()){
            return "비밀번호는 영문과 특수문자 숫자를 포함하며 8자 이상이어야 합니다.";
        }

        // 반복된 문자 확인
        Pattern passPattern2 = Pattern.compile("(\\w)\\1\\1\\1");
        Matcher passMatcher2 = passPattern2.matcher(pwd);

        if(passMatcher2.find()){
            return "비밀번호에 동일한 문자를 과도하게 연속해서 사용할 수 없습니다.";
        }

        // 아이디 포함 확인
        if(pwd.contains(id)){
            return "비밀번호에 ID를 포함할 수 없습니다.";
        }

        // 특수문자 확인
        Pattern passPattern3 = Pattern.compile("\\W");
        Pattern passPattern4 = Pattern.compile("[!@#$%^*+=-]");

        for(int i = 0; i < pwd.length(); i++){
            String s = String.valueOf(pwd.charAt(i));
            Matcher passMatcher3 = passPattern3.matcher(s);

            if(passMatcher3.find()){
                Matcher passMatcher4 = passPattern4.matcher(s);
                if(!passMatcher4.find()){
                    return "비밀번호에 특수문자는 !@#$^*+=-만 사용 가능합니다.";
                }
            }
        }

        //연속된 문자 확인
        int ascSeqCharCnt = 0; // 오름차순 연속 문자 카운트
        int descSeqCharCnt = 0; // 내림차순 연속 문자 카운트

        char char_0;
        char char_1;
        char char_2;

        int diff_0_1;
        int diff_1_2;

        for(int i = 0; i < pwd.length()-2; i++){
            char_0 = pwd.charAt(i);
            char_1 = pwd.charAt(i+1);
            char_2 = pwd.charAt(i+2);

            diff_0_1 = char_0 - char_1;
            diff_1_2 = char_1 - char_2;

            if(diff_0_1 == 1 && diff_1_2 == 1){
                ascSeqCharCnt += 1;
            }

            if(diff_0_1 == -1 && diff_1_2 == -1){
                descSeqCharCnt -= 1;
            }
        }

        if(ascSeqCharCnt > 1 || descSeqCharCnt > 1){
            return "비밀번호에 연속된 문자열을 사용할 수 없습니다.";
        }
        validPassword = 1;
        return "사용가능한 비밀번호 입니다";
    }



}
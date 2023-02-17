package com.example.androidhms.staff.lookup;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Toast;

import com.example.androidhms.databinding.ActivityStaffLookupBinding;
import com.example.androidhms.staff.StaffBaseActivity;
import com.example.androidhms.staff.lookup.adapter.LookupAdapter;
import com.example.androidhms.staff.vo.PatientVO;
import com.example.androidhms.util.Util;
import com.example.conn.RetrofitMethod;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class LookupActivity extends StaffBaseActivity {

    private ActivityStaffLookupBinding bind;
    private PatientVO vo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getIntExtra("patient_id", 0) != 0) {
            new RetrofitMethod().setParams("id", getIntent().getIntExtra("patient_id", 0))
                    .sendGet("getPatientFromId.ap", (isResult, data) -> {
                        if (isResult) {
                            vo = new Gson().fromJson(data, PatientVO.class);
                            if (vo == null) {
                                Toast.makeText(LookupActivity.this, "검색 결과를 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                bindPatientInfo(vo);
                                bind.etName.setText(vo.getName());
                            }
                        } else
                            Toast.makeText(LookupActivity.this, "검색 결과를 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                    });
        }

        // 환자 검색
        bind.btnSearch.setOnClickListener(onSearchClick());
        // 키보드의 검색 버튼 눌렀을때 앱의 검색 버튼 클릭
        bind.etName.setOnEditorActionListener((v, actionId, event) -> {
            bind.btnSearch.performClick();
            return false;
        });

        // 전화걸기
        bind.tvPhone.setOnClickListener(onPhoneClick());

        // 메모저장
        bind.btnMemosave.setOnClickListener(onMemoSaveClick());

        // 공유버튼
        bind.imgvShare.setOnClickListener(onShareClick());
    }

    @Override
    protected View getLayoutResource() {
        bind = ActivityStaffLookupBinding.inflate(getLayoutInflater());
        return bind.getRoot();
    }

    @Override
    protected Activity getActivity() {
        return this;
    }

    private View.OnClickListener onPhoneClick() {
        return v -> {
            String tel = bind.tvPhone.getText().toString();
            if (!tel.equals("")) {
                tel = "tel:" + tel;
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(tel)));
            }
        };
    }

    private View.OnClickListener onSearchClick() {
        return v -> {
            if (bind.etName.getText().toString().trim().equals("")) {
                Toast.makeText(this, "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                Util.keyboardDown(LookupActivity.this);
                new RetrofitMethod().setParams("name", bind.etName.getText().toString())
                        .sendGet("getPatient.ap", (isResult, data) -> {
                            if (isResult) {
                                ArrayList<PatientVO> patientList =
                                        new Gson().fromJson(data, new TypeToken<ArrayList<PatientVO>>() {
                                        }.getType());
                                if (patientList.isEmpty()) {
                                    Toast.makeText(LookupActivity.this, "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show();
                                    bind.rvSearchResult.setVisibility(View.GONE);
                                } else {
                                    Util.setRecyclerView(LookupActivity.this, bind.rvSearchResult,
                                            new LookupAdapter(patientList, LookupActivity.this), true);
                                    bind.rvSearchResult.post(() -> bind.rvSearchResult.setVisibility(View.VISIBLE));
                                }
                            }
                        });
            }
        };
    }

    private View.OnClickListener onMemoSaveClick() {
        return v -> {
            Util.keyboardDown(LookupActivity.this);
            new RetrofitMethod().setParams("id", vo.getPatient_id())
                    .setParams("memo", bind.etMemo.getText().toString())
                    .sendPost("updatePatientMemo.ap", (isResult, data) -> {
                        if (isResult && data.equals("1")) {
                            Toast.makeText(LookupActivity.this, "메모가 저장되었습니다.", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(LookupActivity.this, "메모저장을 실패했습니다.", Toast.LENGTH_SHORT).show();
                    });
        };
    }

    public void selectPatient(PatientVO vo) {
        this.vo = vo;
        bindPatientInfo(vo);
        bind.rvSearchResult.setVisibility(View.GONE);
    }

    private void bindPatientInfo(PatientVO vo) {
        bind.tvName.setText(vo.getName());
        // 밑줄
        SpannableString content = new SpannableString(vo.getPhone_number());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        bind.tvPhone.setText(content);
        bind.tvBirthday.setText(Util.getBirthDay(vo.getSocial_id()));
        bind.tvAge.setText(Util.getAge(vo.getSocial_id()) + "세");
        bind.tvHeight.setText(vo.getHeight() + "cm");
        bind.tvWeight.setText(vo.getWeight() + "kg");
        bind.tvBloodtype.setText(vo.getBlood_type());
        String gender = vo.getGender().equals("M") ? "남" : "여";
        bind.tvGender.setText(gender);
        bind.tvAllergy.setText(vo.getAllergy());
        bind.tvUnderlying.setText(vo.getUnderlying_disease());
        bind.etMemo.setText(vo.getMemo());
    }

    private View.OnClickListener onShareClick() {
        return v -> {
            if (vo != null) {
                Util.sharedContent = "##patient##" + vo.getPatient_id() + "##" + vo.getName();
                Toast.makeText(this, "환자 정보가 저장되었습니다.\n메신저를 통해 다른 의료진들과 공유할 수 있습니다.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "공유할 내용이 없습니다.", Toast.LENGTH_SHORT).show();
            }
        };
    }

}
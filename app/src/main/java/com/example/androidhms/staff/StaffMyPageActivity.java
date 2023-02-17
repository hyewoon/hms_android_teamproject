package com.example.androidhms.staff;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.androidhms.databinding.ActivityStaffMyPageBinding;
import com.example.androidhms.staff.vo.StaffVO;
import com.example.androidhms.util.HmsFirebase;
import com.example.androidhms.util.Util;
import com.example.androidhms.util.dialog.EditDialog;
import com.example.conn.RetrofitMethod;

public class StaffMyPageActivity extends StaffBaseActivity {

    private ActivityStaffMyPageBinding bind;
    private StaffVO staff = Util.staff;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("staffLoginInfo", MODE_PRIVATE);
        editor = preferences.edit();

        // 마이페이지
        bind.tvName.setText(staff.getName());
        bind.tvStaffId.setText(String.valueOf(staff.getStaff_id()));
        String department = staff.getDepartment_name();
        if (staff.getStaff_level() == 1) department += " 의사";
        else department += " 간호사";
        bind.tvStaffInfo.setText(department);
        bind.tvBirthday.setText(Util.getBirthDay(staff.getSocial_id()));
        bind.tvEmail.setText(staff.getEmail());
        bind.tvPhoneNumber.setText(staff.getPhone_number());
        bind.tvHireDate.setText(String.valueOf(staff.getHire_date()));
        bind.tvIntroduction.setText(staff.getIntroduction());

        // 자기소개 수정
        bind.btnModifyIntroduction.setOnClickListener(v -> {
            new EditDialog(this, getLayoutInflater(), "자기소개", staff.getIntroduction(), new EditDialog.OnSaveClickListener() {
                @Override
                public void onSaveClick(EditDialog dialog, String content) {
                    new RetrofitMethod().setParams("id", staff.getStaff_id())
                            .setParams("introduction", content)
                            .sendPost("updateStaffIntroduction.ap", new RetrofitMethod.CallBackResult() {
                                @Override
                                public void result(boolean isResult, String data) {
                                    if (isResult && data.equals("1")) {
                                        Toast.makeText(StaffMyPageActivity.this, "자기소개를 저장했습니다.", Toast.LENGTH_SHORT).show();
                                        Util.staff.setIntroduction(content);
                                        bind.tvIntroduction.setText(content);
                                        dialog.dismiss();
                                    } else
                                        Toast.makeText(StaffMyPageActivity.this, "자기소개 저장을 실패했습니다.", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }).show();
        });

        // 로그아웃
        bind.btnLogout.setOnClickListener(v -> {
            Toast.makeText(StaffMyPageActivity.this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
            new HmsFirebase(this).deleteToken();
            Util.staff = null;
            editor.putString("id", "");
            editor.putString("pw", "");
            editor.putString("staffData", "");
            editor.putString("autoLogin", "N");
            editor.commit();
            finish();
        });
    }

    @Override
    protected View getLayoutResource() {
        bind = ActivityStaffMyPageBinding.inflate(getLayoutInflater());
        return bind.getRoot();
    }

    @Override
    protected Activity getActivity() {
        return this;
    }

}
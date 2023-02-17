package com.example.androidhms.staff;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.androidhms.R;
import com.example.androidhms.databinding.ActivityStaffBinding;
import com.example.androidhms.staff.lookup.LookupActivity;
import com.example.androidhms.staff.messenger.MessengerActivity;
import com.example.androidhms.staff.outpatient.OutpatientActivity;
import com.example.androidhms.staff.schedule.ScheduleActivity;
import com.example.androidhms.staff.vo.StaffVO;
import com.example.androidhms.staff.ward.WardActivity;
import com.example.androidhms.util.Util;
import com.example.conn.ApiClient;

public class StaffActivity extends StaffBaseActivity {

    private ActivityStaffBinding bind;
    private StaffVO staff = Util.staff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApiClient.setBASEURL("http://211.223.59.99:3301/hms/");
        if (staff == null) staff = Util.getStaff(this);

        // 상단 페이지
        bind.tvName.setText(staff.getName());

        // 항목 클릭
        bind.clLookup.setOnClickListener(onMenuClick());
        bind.clOutpatient.setOnClickListener(onMenuClick());
        bind.clWard.setOnClickListener(onMenuClick());
        bind.clSchedule.setOnClickListener(onMenuClick());
        bind.clMessanger.setOnClickListener(onMenuClick());

        // 마이 페이지
        bind.rlMypage.setOnClickListener(v ->
                startActivity(new Intent(this, StaffMyPageActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Util.isStaffActivityForeground = true;
        // 로그아웃시 finish
        if (Util.staff == null) {
            startActivity(new Intent(this, StaffLoginActivity.class));
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getNotificationTime = null;
        Util.isStaffActivityForeground = false;
    }

    @Override
    protected View getLayoutResource() {
        bind = ActivityStaffBinding.inflate(getLayoutInflater());
        return bind.getRoot();
    }

    @Override
    protected Activity getActivity() {
        return this;
    }

    private View.OnClickListener onMenuClick() {
        return v -> {
            Intent intent = null;
            if (v.getId() == R.id.cl_lookup) {
                intent = new Intent(StaffActivity.this, LookupActivity.class);
            } else if (v.getId() == R.id.cl_outpatient) {
                intent = new Intent(StaffActivity.this, OutpatientActivity.class);
            } else if (v.getId() == R.id.cl_ward) {
                intent = new Intent(StaffActivity.this, WardActivity.class);
            } else if (v.getId() == R.id.cl_messanger) {
                intent = new Intent(StaffActivity.this, MessengerActivity.class);
            } else if (v.getId() == R.id.cl_schedule) {
                intent = new Intent(StaffActivity.this, ScheduleActivity.class);
            }
            startActivity(intent);
        };
    }
}
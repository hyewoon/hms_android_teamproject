package com.example.androidhms.staff.outpatient;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.androidhms.R;
import com.example.androidhms.databinding.ActivityStaffOutpatientBinding;
import com.example.androidhms.staff.StaffBaseActivity;
import com.google.android.material.tabs.TabLayout;

public class OutpatientActivity extends StaffBaseActivity {

    private ActivityStaffOutpatientBinding bind;
    private MedicalRecordFragment recordFragment;
    private ReceiptFragment receiptFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recordFragment = new MedicalRecordFragment();
        receiptFragment = new ReceiptFragment();
        addFragment(R.id.fl_container, recordFragment);
        addFragment(R.id.fl_container, receiptFragment);
        hideFragment(recordFragment);
        bind.tlOutpatient.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        showFragment(receiptFragment);
                        hideFragment(recordFragment);
                        break;
                    case 1:
                        showFragment(recordFragment);
                        hideFragment(receiptFragment);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    protected View getLayoutResource() {
        bind = ActivityStaffOutpatientBinding.inflate(getLayoutInflater());
        return bind.getRoot();
    }

    @Override
    protected Activity getActivity() {
        return this;
    }

}
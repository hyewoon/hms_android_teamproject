package com.example.androidhms.staff.ward;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.androidhms.R;
import com.example.androidhms.databinding.ActivityStaffWardBinding;
import com.example.androidhms.staff.StaffBaseActivity;
import com.google.android.material.tabs.TabLayout;

public class WardActivity extends StaffBaseActivity {

    private ActivityStaffWardBinding bind;
    private MyPatientFragment myPatientFragment;
    private WardFragment wardFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 탭
        myPatientFragment = new MyPatientFragment();
        wardFragment = new WardFragment();
        addFragment(R.id.fl_container, myPatientFragment);
        addFragment(R.id.fl_container, wardFragment);
        hideFragment(wardFragment);
        bind.tlWard.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        showFragment(myPatientFragment);
                        hideFragment(wardFragment);
                        break;
                    case 1:
                        showFragment(wardFragment);
                        hideFragment(myPatientFragment);
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
        bind = ActivityStaffWardBinding.inflate(getLayoutInflater());
        return bind.getRoot();
    }

    @Override
    protected Activity getActivity() {
        return this;
    }

}
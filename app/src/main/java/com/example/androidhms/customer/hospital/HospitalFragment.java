package com.example.androidhms.customer.hospital;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.androidhms.R;
import com.example.androidhms.customer.common.FragmentControl;
import com.example.androidhms.databinding.FragmentCustomerHospitalBinding;
import com.google.android.material.tabs.TabLayout;

public class HospitalFragment extends Fragment {
    private FragmentCustomerHospitalBinding bind;
    private StaffSearchFragment staffSearchFragment;
    private DepartmentSearchFragment departmentSearchFragment;
    private FragmentControl control;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       bind = FragmentCustomerHospitalBinding.inflate(inflater, container, false);

       staffSearchFragment = new StaffSearchFragment();
       departmentSearchFragment = new DepartmentSearchFragment();

       addInFragment(R.id.hospital_container, staffSearchFragment);
       addInFragment(R.id.hospital_container, departmentSearchFragment);

       showInFragment(departmentSearchFragment);
       hideInFragment(staffSearchFragment);

       bind.tlHospitalInfo.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
           @Override
           public void onTabSelected(TabLayout.Tab tab) {
               switch (tab.getPosition()) {
                   case 0:
                       showInFragment(departmentSearchFragment);
                       hideInFragment(staffSearchFragment);
                       break;
                   case 1:
                       showInFragment(staffSearchFragment);
                       hideInFragment(departmentSearchFragment);
                       departmentSearchFragment.onDestroyView();
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






        return bind.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind = null;
    }

    private void addInFragment(int id, Fragment fragment) {
        getChildFragmentManager().beginTransaction().add(id, fragment).commit();
    }

    private void showInFragment(Fragment fragment) {
        getChildFragmentManager().beginTransaction().show(fragment).commit();
    }

    private void hideInFragment(Fragment fragment) {
        getChildFragmentManager().beginTransaction().hide(fragment).commit();
    }

}
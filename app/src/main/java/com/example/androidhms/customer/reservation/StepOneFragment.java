package com.example.androidhms.customer.reservation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.androidhms.databinding.FragmentCustomerStepOneBinding;


public class StepOneFragment extends Fragment {
    private FragmentCustomerStepOneBinding bind;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentCustomerStepOneBinding.inflate(inflater, container, false);


        bind.tvDepartment1.setOnClickListener(v -> {
            selectDepartment(1);
            StepCnt.cnt = 2;
            ((ReservationActivity)getActivity()).changeStep();
        });

        bind.tvDepartment2.setOnClickListener(v -> {
            selectDepartment(2);
            StepCnt.cnt = 2;
            ((ReservationActivity)getActivity()).changeStep();
        });

        bind.tvDepartment3.setOnClickListener(v -> {
            selectDepartment(3);
            StepCnt.cnt = 2;
            ((ReservationActivity)getActivity()).changeStep();
        });

        bind.tvDepartment4.setOnClickListener(v -> {
            selectDepartment(4);
            StepCnt.cnt = 2;
            ((ReservationActivity)getActivity()).changeStep();
        });

        bind.tvDepartment5.setOnClickListener(v -> {
            selectDepartment(5);
            StepCnt.cnt = 2;
            ((ReservationActivity)getActivity()).changeStep();
        });

        bind.tvDepartment6.setOnClickListener(v -> {
            selectDepartment(6);
            StepCnt.cnt = 2;
            ((ReservationActivity)getActivity()).changeStep();
        });

        bind.tvDepartment7.setOnClickListener(v -> {
            selectDepartment(7);
            StepCnt.cnt = 2;
            ((ReservationActivity)getActivity()).changeStep();
        });

        bind.tvDepartment8.setOnClickListener(v -> {
            selectDepartment(8);
            StepCnt.cnt = 2;
            ((ReservationActivity)getActivity()).changeStep();
        });

        bind.tvDepartment9.setOnClickListener(v -> {
            selectDepartment(9);
            StepCnt.cnt = 2;
            ((ReservationActivity)getActivity()).changeStep();
        });

        return bind.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind = null;
    }

    public void selectDepartment(int selected_department) {
        ReservationSelect.selectedDepartment_id = selected_department;
    }
}
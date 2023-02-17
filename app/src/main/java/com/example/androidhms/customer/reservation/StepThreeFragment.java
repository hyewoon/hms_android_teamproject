package com.example.androidhms.customer.reservation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.androidhms.databinding.FragmentCustomerStepThreeBinding;

import java.text.SimpleDateFormat;
import java.util.Date;


public class StepThreeFragment extends Fragment {
    private FragmentCustomerStepThreeBinding bind;
    private String setYear;
    private String setMonth;
    private String setDate;
    private int selectedDate;
    private int nowDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        bind = FragmentCustomerStepThreeBinding.inflate(inflater, container, false);

        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String date = dateFormat.format(now);
        Log.d("로그", "현재날짜 : " + date);
        nowDate = Integer.parseInt(date);

        bind.calvDate.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            setYear = Integer.toString(year);
            if (month < 10) {
                setMonth = "0" + Integer.toString(month + 1);
            }else {
                setMonth = Integer.toString(month + 1);
            }
            if (dayOfMonth < 10) {
                setDate = "0" + Integer.toString(dayOfMonth);
            }else {
                setDate = Integer.toString(dayOfMonth);
            }




            bind.btnSelect.setOnClickListener(v -> {
                selectedDate = Integer.parseInt(setYear + setMonth + setDate);
                if (selectedDate >= nowDate && selectedDate < nowDate + 10000) {
                    ReservationSelect.selectedDate = setYear + setMonth + setDate;
                    StepCnt.cnt = 4;
                    ((ReservationActivity)getActivity()).changeStep();
                }else {
                    Toast.makeText(getActivity(), "해당 날짜는 예약할 수 없습니다.", Toast.LENGTH_SHORT).show();
                }

            });

        });





        return bind.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind = null;
    }

}
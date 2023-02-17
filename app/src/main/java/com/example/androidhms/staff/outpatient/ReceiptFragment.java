package com.example.androidhms.staff.outpatient;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.androidhms.databinding.FragmentStaffReceiptBinding;
import com.example.androidhms.staff.outpatient.adapter.ReceiptAdapter;
import com.example.androidhms.staff.vo.MedicalReceiptVO;
import com.example.androidhms.staff.vo.StaffVO;
import com.example.androidhms.util.dialog.CalendarDialog;
import com.example.androidhms.util.Util;
import com.example.conn.RetrofitMethod;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

public class ReceiptFragment extends Fragment {

    private FragmentStaffReceiptBinding bind;
    private StaffVO staff = Util.staff;
    private Timestamp tsDate = new Timestamp(System.currentTimeMillis());
    private ArrayList<MedicalReceiptVO> mrList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentStaffReceiptBinding.inflate(inflater, container, false);
        bind.etDate.setText(Util.getDate(tsDate));
        bind.btnNextday.setOnClickListener(onDayClick(true));
        bind.btnPreday.setOnClickListener(onDayClick(false));
        Util.setEditTextDate(getContext(), inflater, bind.etDate, (date, dialog) -> {
            tsDate = Timestamp.valueOf(date.getYear() + "-" + date.getMonth() + "-" + date.getDay() + " 00:00:00");
            setEtdateText();
            dialog.dismiss();
            getReceipt();
        });
        getReceipt();
        return bind.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind = null;
    }

    private void setEtdateText() {
        bind.etDate.setText(Util.getDate(tsDate));
    }

    private View.OnClickListener onDayClick(boolean plus) {
        return v -> {
            if (plus) tsDate = Util.timestampOperator(tsDate, Calendar.DAY_OF_MONTH, 1);
            else tsDate = Util.timestampOperator(tsDate, Calendar.DAY_OF_MONTH, -1);
            setEtdateText();
            getReceipt();
        };
    }

    private void getReceipt() {
        bind.rlProgress.view.setVisibility(View.VISIBLE);
        bind.clNotfound.view.setVisibility(View.GONE);
        new RetrofitMethod().setParams("id", staff.getStaff_id())
                .setParams("time", Util.getDate(tsDate))
                .sendGet("getMedicalReceipt.ap", (isResult, data) -> {
                    if (isResult && data != null) {
                        mrList = new Gson().fromJson(data, new TypeToken<ArrayList<MedicalReceiptVO>>(){}.getType());
                        Util.setRecyclerView(getContext(), bind.rvMedicalRecord, new ReceiptAdapter(ReceiptFragment.this, mrList), true);
                        if (mrList.isEmpty()) bind.clNotfound.view.setVisibility(View.VISIBLE);
                        else bind.rvMedicalRecord.post(() ->  bind.rlProgress.view.setVisibility(View.GONE));
                    } else if (!isResult) {
                        Toast.makeText(requireActivity(), "검색결과를 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                        bind.clNotfound.view.setVisibility(View.VISIBLE);
                    }
                    bind.rlProgress.view.setVisibility(View.GONE);
                });
    }
}
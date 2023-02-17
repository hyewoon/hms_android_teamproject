package com.example.androidhms.staff.ward;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.androidhms.databinding.FragmentStaffMyPatientBinding;
import com.example.androidhms.staff.vo.AdmissionMemoVO;
import com.example.androidhms.staff.vo.AdmissionRecordVO;
import com.example.androidhms.staff.vo.StaffVO;
import com.example.androidhms.staff.ward.adapter.AdmissionMemoAdapter;
import com.example.androidhms.staff.ward.adapter.AdmissionRecordAdapter;
import com.example.androidhms.util.Util;
import com.example.androidhms.util.dialog.AlertDialog;
import com.example.conn.RetrofitMethod;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class MyPatientFragment extends Fragment {

    private FragmentStaffMyPatientBinding bind;
    private StaffVO staff = Util.staff;
    private ArrayList<AdmissionRecordVO> arList;
    private ArrayList<AdmissionMemoVO> amList;
    private Context context;
    private int selectedPosition = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentStaffMyPatientBinding.inflate(inflater, container, false);
        context = getContext();

        if (staff.getStaff_level() == 2 && staff.getDepartment_id() > 100) {
            bind.clNotfound.tvNotfound.setText("진료과에 속한 의료진만 조회할 수 있습니다.");
            bind.clNotfound.view.setVisibility(View.VISIBLE);
            bind.swDepartment.setEnabled(false);
        } else {
            if (staff.getStaff_level() == 1) getAdmissionRecord("doctor");
            else {
                getAdmissionRecord("department");
                bind.swDepartment.setChecked(true);
                bind.swDepartment.setEnabled(false);
            }
        }

        // switch
        bind.swDepartment.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) getAdmissionRecord("department");
            else getAdmissionRecord("doctor");
        });

        // 메모 입력
        bind.etMemo.setOnEditorActionListener((v, actionId, event) -> {
            bind.btnSend.performClick();
            return false;
        });
        bind.btnSend.setOnClickListener(onSendClick());
        return bind.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind = null;
    }

    private void getAdmissionRecord(String option) {
        bind.clNotfound.view.setVisibility(View.GONE);
        bind.rlProgress.view.setVisibility(View.VISIBLE);
        selectedPosition = -1;
        new RetrofitMethod().setParams("id", staff.getStaff_id())
                .setParams("option", option)
                .sendPost("getAdmissionRecordMypatient.ap", (isResult, data) -> {
                    if (isResult && data != null) {
                        arList = new Gson().fromJson(data, new TypeToken<ArrayList<AdmissionRecordVO>>() {
                        }.getType());
                        if (arList.isEmpty()) bind.clNotfound.view.setVisibility(View.VISIBLE);
                        Util.setRecyclerView(context, bind.rvMypatient,
                                new AdmissionRecordAdapter(MyPatientFragment.this, arList), true);
                        Util.setRecyclerView(context, bind.rvAdmissionMemo,
                                new AdmissionMemoAdapter(MyPatientFragment.this, new ArrayList<>()), true);
                    } else
                        Toast.makeText(context, "환자 목록을 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                    bind.rlProgress.view.setVisibility(View.GONE);
                });
    }

    public void onAdmissionRecordClick(int position) {
        bind.rlProgress.view.setVisibility(View.VISIBLE);
        bind.rvMypatient.scrollToPosition(position);
        selectedPosition = position;
        getAdmissionMemo(arList.get(position).getAdmission_record_id());
    }

    private void getAdmissionMemo(int id) {
        new RetrofitMethod().setParams("id", id)
                .sendGet("getAdmissionMemo.ap", (isResult, data) -> {
                    if (isResult) {
                        amList = new Gson().fromJson(data, new TypeToken<ArrayList<AdmissionMemoVO>>() {
                        }.getType());
                        Util.setRecyclerView(context, bind.rvAdmissionMemo, new AdmissionMemoAdapter(MyPatientFragment.this, amList), true);
                        bind.rvAdmissionMemo.scrollToPosition(amList.size() - 1);
                    } else
                        Toast.makeText(context, "환자상태기록을 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                    bind.rlProgress.view.setVisibility(View.GONE);
                });
    }

    public View.OnClickListener onSendClick() {
        return v -> {
            if (selectedPosition != -1) {
                Util.keyboardDown(requireActivity());
                new RetrofitMethod().setParams("admission_record_id", arList.get(selectedPosition).getAdmission_record_id())
                        .setParams("staff_id", staff.getStaff_id())
                        .setParams("memo", bind.etMemo.getText().toString())
                        .sendPost("insertAdmissionMemo.ap", (isResult, data) -> {
                            if (isResult && data.equals("1")) {
                                getAdmissionMemo(arList.get(selectedPosition).getAdmission_record_id());
                                bind.etMemo.setText("");
                            } else
                                Toast.makeText(context, "메모를 저장하는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                        });
            }
        };
    }

    public void deleteAdmissionMemo(int id) {
        new AlertDialog(getContext(), getLayoutInflater(), "환자상태기록 삭제", "정말 삭제하시겠습니까?", new AlertDialog.OnAlertDialogClickListener() {
            @Override
            public void setOnClickYes(AlertDialog dialog) {
                bind.rlProgress.view.setVisibility(View.VISIBLE);
                new RetrofitMethod().setParams("admission_record_id", id)
                        .sendPost("deleteAdmissionMemo.ap", (isResult, data) -> {
                            if (isResult && data.equals("1")) {
                                getAdmissionMemo(arList.get(selectedPosition).getAdmission_record_id());
                                Toast.makeText(context, "메모를 삭제했습니다.", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(context, "메모를 삭제하는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                            bind.rlProgress.view.setVisibility(View.GONE);
                        });
                dialog.dismiss();
            }
            @Override
            public void setOnClickNo(AlertDialog dialog) {
                dialog.dismiss();
            }
        }).setYesText("삭제").show();
    }

}
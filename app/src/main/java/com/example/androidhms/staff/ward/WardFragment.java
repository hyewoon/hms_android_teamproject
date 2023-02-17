package com.example.androidhms.staff.ward;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.androidhms.R;
import com.example.androidhms.databinding.FragmentStaffWardBinding;
import com.example.androidhms.staff.lookup.LookupActivity;
import com.example.androidhms.staff.vo.AdmissionMemoVO;
import com.example.androidhms.staff.vo.AdmissionRecordVO;
import com.example.androidhms.staff.vo.StaffVO;
import com.example.androidhms.staff.ward.adapter.AdmissionMemoAdapter;
import com.example.androidhms.util.Util;
import com.example.androidhms.util.dialog.AlertDialog;
import com.example.conn.RetrofitMethod;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

public class WardFragment extends Fragment {

    private FragmentStaffWardBinding bind;
    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ArrayList<AdmissionRecordVO> arList;
    private ArrayList<AdmissionMemoVO> amList;
    private TextView[] tvArr;
    private StaffVO staff = Util.staff;
    private int selectedPosition = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentStaffWardBinding.inflate(inflater, container, false);
        context = getContext();
        preferences = requireActivity().getSharedPreferences("wardInfo", MODE_PRIVATE);
        editor = preferences.edit();
        tvArr = new TextView[]{bind.tvBed1, bind.tvBed2, bind.tvBed3, bind.tvBed4};

        // Spinner 설정
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.ward, android.R.layout.simple_spinner_dropdown_item);
        bind.spWard.setAdapter(adapter);
        bind.spWard.setSelection(preferences.getInt("ward", 0));
        bind.spWard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editor.putInt("ward", position);
                editor.commit();
                bind.rlProgress.view.setVisibility(View.VISIBLE);
                for (TextView tv : tvArr) {
                    tv.setText("빈 침대");
                    tv.setTextColor(ContextCompat.getColor(context, R.color.gray));
                }
                if (selectedPosition != -1) {
                    tvArr[selectedPosition].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.white)));
                    bind.tvTreatmentName.setText("");
                    bind.tvStaffInfo.setText("");
                    bind.tvAdmissionDate.setText("");
                    bind.etDischargeDate.setText("");
                }
                selectedPosition = -1;
                getArList(position);
                setDischargeDateClickListener();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 침대 클릭
        bind.tvBed1.setOnClickListener(onBedClickListener(0));
        bind.tvBed2.setOnClickListener(onBedClickListener(1));
        bind.tvBed3.setOnClickListener(onBedClickListener(2));
        bind.tvBed4.setOnClickListener(onBedClickListener(3));

        // 환자 상세정보 조회
        bind.tvLookup.setOnClickListener(v -> {
            if (selectedPosition != -1 && arList.get(selectedPosition) != null) {
                Intent intent = new Intent(requireActivity(), LookupActivity.class);
                intent.putExtra("patient_id", arList.get(selectedPosition).getPatient_id());
                startActivity(intent);
            }
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

    // index 0 -> 501
    private int getWard(int index) {
        return 500 + (index / 5) * 100 + index % 5 + 1;
    }

    private void getArList(int position) {
        new RetrofitMethod().setParams("ward_id", getWard(position))
                .sendGet("getAdmissionRecordWard.ap", (isResult, data) -> {
                    if (isResult) {
                        Util.setRecyclerView(context, bind.rvAdmissionMemo, new AdmissionMemoAdapter(WardFragment.this, new ArrayList<>()), true);
                        arList = new Gson().fromJson(data, new TypeToken<ArrayList<AdmissionRecordVO>>() {
                        }.getType());
                        ArrayList<Integer> indexList = new ArrayList<>();
                        for (AdmissionRecordVO vo : arList) {
                            indexList.add((vo.getWard_id() + 3) % 4);
                            tvArr[(vo.getWard_id() + 3) % 4].setText(vo.getPatient_name());
                            tvArr[(vo.getWard_id() + 3) % 4].setTextColor(ContextCompat.getColor(context, R.color.text_color));
                        }
                        // arList 와 tvArr 의 인덱스번호를 맞춤
                        for (int i = 0; i < 4; i++) {
                            if (!indexList.contains(i)) arList.add(i, null);
                        }
                        bind.rlProgress.view.setVisibility(View.GONE);
                        // 선택 상태에서 환자입원정보를 다시 불러올 경우
                        if (selectedPosition != -1) tvArr[selectedPosition].performClick();
                    } else
                        Toast.makeText(context, "환자 목록을 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                });
    }

    private void getAdmissionMemo(int id) {
        bind.rlProgress.view.setVisibility(View.VISIBLE);
        new RetrofitMethod().setParams("id", id)
                .sendGet("getAdmissionMemo.ap", (isResult, data) -> {
                    if (isResult) {
                        amList = new Gson().fromJson(data, new TypeToken<ArrayList<AdmissionMemoVO>>() {
                        }.getType());
                        Util.setRecyclerView(context, bind.rvAdmissionMemo, new AdmissionMemoAdapter(WardFragment.this, amList), true);
                        bind.rvAdmissionMemo.scrollToPosition(amList.size() - 1);
                    } else
                        Toast.makeText(context, "환자상태기록을 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                    bind.rlProgress.view.setVisibility(View.GONE);
                });
    }

    private View.OnClickListener onBedClickListener(int index) {
        return v -> {
            if (!tvArr[index].getText().toString().equals("빈 침대")) {
                if (selectedPosition != -1) {
                    tvArr[selectedPosition].setTextColor(ContextCompat.getColor(context, R.color.text_color));
                    tvArr[selectedPosition].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.white)));
                }
                tvArr[index].setTextColor(ContextCompat.getColor(context, R.color.white));
                tvArr[index].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.main_color)));
                AdmissionRecordVO vo = arList.get(index);
                bind.tvTreatmentName.setText(vo.getTreatment_name());
                String staffInfo = vo.getStaff_name() + "\n(" + vo.getDepartment_name() + ")";
                bind.tvStaffInfo.setText(staffInfo);
                bind.tvAdmissionDate.setText(vo.getAdmission_date());
                if (vo.getDischarge_date() != null)
                    bind.etDischargeDate.setText(vo.getDischarge_date());
                selectedPosition = index;
                getAdmissionMemo(arList.get(selectedPosition).getAdmission_record_id());
                setDischargeDateClickListener();
            }
        };
    }

    private void setDischargeDateClickListener() {
        if (selectedPosition != -1 && Util.staff.getStaff_id() == arList.get(selectedPosition).getStaff_id()) {
            Util.setEditTextDate(getContext(), getLayoutInflater(), bind.etDischargeDate, (date, dialog) -> {
                bind.rlProgress.view.setVisibility(View.VISIBLE);
                Timestamp tsDate = Timestamp.valueOf(date.getYear() + "-" + date.getMonth() + "-" + date.getDay() + " 00:00:00");
                bind.etDischargeDate.setText(Util.getDate(tsDate));
                new RetrofitMethod().setParams("id", arList.get(selectedPosition).getAdmission_record_id())
                        .setParams("date", Util.getDate(tsDate))
                        .sendPost("updateDischargeDate.ap", (isResult, data) -> {
                            if (isResult && data.equals("1")) {
                                Toast.makeText(context,
                                        arList.get(selectedPosition).getPatient_name() + " 환자의 퇴원(예정)일을 수정했습니다.", Toast.LENGTH_SHORT).show();
                                getArList(bind.spWard.getSelectedItemPosition());
                            } else {
                                Toast.makeText(context,
                                        arList.get(selectedPosition).getPatient_name() + " 환자의 퇴원(예정)일을 수정하는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                dialog.dismiss();
            }, null, Util.timestampOperator(new Timestamp(System.currentTimeMillis()), Calendar.DAY_OF_MONTH, 1));
        } else if (selectedPosition != -1) {
            bind.etDischargeDate.setOnClickListener(v -> {
                Toast.makeText(context, "환자의 담당의만 퇴원(예정)일을 수정할 수 있습니다.", Toast.LENGTH_SHORT).show();
            });
        } else bind.etDischargeDate.setOnClickListener(null);
    }

    public View.OnClickListener onSendClick() {
        return v -> {
            if (selectedPosition != -1) {
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
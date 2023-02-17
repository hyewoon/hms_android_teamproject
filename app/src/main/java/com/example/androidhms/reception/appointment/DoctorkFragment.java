package com.example.androidhms.reception.appointment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.androidhms.R;
import com.example.androidhms.databinding.FragmentDoctorkBinding;
import com.example.androidhms.reception.vo.StaffVO;
import com.example.androidhms.staff.vo.PatientVO;
import com.example.conn.RetrofitMethod;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class DoctorkFragment extends Fragment {

    FragmentDoctorkBinding bind;
    String department_id;
    String doctor_name;
    DoctorNameAdapter doctorNameAdapter;
    ArrayList<StaffVO> list;
    StaffVO vo;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        bind =FragmentDoctorkBinding.inflate(inflater,container,false);
        // Inflate the layout for this fragment



        ArrayAdapter department = ArrayAdapter.createFromResource(getContext(), R.array.department_list, android.R.layout.simple_spinner_dropdown_item);
        //내가 지정한 리스트department_list, 기본 제공하는 드롬다운 simple_spinner_dropdown_item
        department.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bind.spinnerDeparatment.setAdapter(department);

        bind.spinnerDeparatment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("로그", "onItemSelected: " + id);
                Log.d("로그", "onItemSelected: " + position);
                department_id = id + "";

                new RetrofitMethod().setParams("id", department_id).sendPost("doctor.re", new RetrofitMethod.CallBackResult() {
                    @Override
                    public void result(boolean isResult, String data) throws Exception {
                        Log.d("로그", "result: " + data);
                        Log.d("로그", "result: " + department_id);

                        ArrayList<StaffVO> stafflist = new Gson().fromJson(data, new TypeToken<ArrayList<StaffVO>>() {
                        }.getType());
                  /*      bind.item1.setText(stafflist.get(0).getName());
                        bind.item2.setText(stafflist.get(1).getName());
                        bind.item3.setText(stafflist.get(2).getName());
                        bind.item4.setText(stafflist.get(3).getName());
                        bind.llDocotorName.setVisibility(View.VISIBLE);
                        */
                          new RetrofitMethod().setParams("id",department_id).sendPost("doctor.re", new RetrofitMethod.CallBackResult() {
                              @Override
                              public void result(boolean isResult, String data) throws Exception {
                                  list = new Gson().fromJson(data, new TypeToken<ArrayList<StaffVO>>() {
                                  }.getType());
                                  doctorNameAdapter = new DoctorNameAdapter(inflater, list, vo);
                                  bind.recvDoctorName.setAdapter(doctorNameAdapter);
                                  bind.recvDoctorName.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                              }
                          });


                    }

                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        bind.recvDoctorName.setOnClickListener(v -> {

        });
        return bind.getRoot();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind = null;
    }
}
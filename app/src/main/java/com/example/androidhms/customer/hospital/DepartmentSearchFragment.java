package com.example.androidhms.customer.hospital;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidhms.customer.vo.StaffSearchVO;
import com.example.androidhms.databinding.FragmentCustomerDepartmentSearchBinding;
import com.example.conn.ApiClient;
import com.example.conn.RetrofitMethod;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;


public class DepartmentSearchFragment extends Fragment {
    private FragmentCustomerDepartmentSearchBinding bind;
    private DepartmentSearchAdapter departmentSearchAdapter;
    private String result;
    private ArrayList<StaffSearchVO> staff;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentCustomerDepartmentSearchBinding.inflate(inflater, container, false);

        ApiClient.setBASEURL("http://211.223.59.99:3301/hms/");


        bind.spSearchDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                result = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bind.btnSearch.setOnClickListener(v -> {
            if (result.equals("진료과 선택")) {
                Log.d("로그", "처리하지 않음");
            }else {
                Log.d("로그", "선택한 부서 : " + result);
                new RetrofitMethod().setParams("searchWord", result)
                        .sendPost("staffsearch_by_department.cu", (isResult, data) -> {
                            staff = new Gson().fromJson(data, new TypeToken<ArrayList<StaffSearchVO>>(){}.getType());
                            departmentSearchAdapter = new DepartmentSearchAdapter(inflater, getContext(), staff);
                            bind.rcvStaffSearch.setAdapter(departmentSearchAdapter);
                            bind.rcvStaffSearch.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                        });
            }
        });




        return bind.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind = null;
    }
}
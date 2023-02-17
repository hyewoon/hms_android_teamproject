package com.example.androidhms.customer.reservation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.androidhms.customer.vo.RecieptVO;
import com.example.androidhms.databinding.FragmentCustomerStepTwoBinding;
import com.example.conn.ApiClient;
import com.example.conn.RetrofitMethod;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;


public class StepTwoFragment extends Fragment {
    private FragmentCustomerStepTwoBinding bind;
    private ArrayList<RecieptVO> reciept = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentCustomerStepTwoBinding.inflate(inflater, container, false);



        ApiClient.setBASEURL("http://211.223.59.99:3301/hms/");




        new RetrofitMethod().setParams("department_id", ReservationSelect.selectedDepartment_id)
                .sendPost("receipt_info.cu", (isResult, data) -> {
                    reciept = new Gson().fromJson(data, new TypeToken<ArrayList<RecieptVO>>(){}.getType());

                    StepTwoAdapter adapter = new StepTwoAdapter(inflater, getContext(), reciept);

                    bind.rcvStaff.setAdapter(adapter);
                    bind.rcvStaff.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                });



        return bind.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind = null;
    }

}
package com.example.androidhms.customer.join;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.androidhms.customer.vo.CustomerVO;
import com.example.androidhms.databinding.FragmentCustomerAdditionalBinding;
import com.example.conn.RetrofitMethod;
import com.google.gson.Gson;

public class AdditionalFragment extends Fragment {
    private FragmentCustomerAdditionalBinding bind;
    private CustomerVO customer;
    private int social_id;
    private String name;
    private String email;

    public AdditionalFragment(String email) {
        this.email = email;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentCustomerAdditionalBinding.inflate(inflater, container, false);

        social_id = ((PatientRegisterActivity)getActivity()).social_id;
        name = ((PatientRegisterActivity)getActivity()).name;

        Intent intent = new Intent(getActivity(), JoinActivity.class);

        bind.btnLater.setOnClickListener(v -> {
            new RetrofitMethod().setParams("social_id", social_id)
                    .setParams("name", name)
                    .sendPost("customer_check.cu", (isResult, data) -> {
                        customer = new Gson().fromJson(data, CustomerVO.class);
                        intent.putExtra("customer", customer);
                        intent.putExtra("email", email);
                        startActivity(intent);
                    });

        });

        bind.btnRegister.setOnClickListener(v -> {
            new RetrofitMethod().setParams("social_id", social_id)
                    .setParams("name", name)
                    .sendPost("customer_check.cu", (isResult, data) -> {
                        customer = new Gson().fromJson(data, CustomerVO.class);
                        intent.putExtra("customer", customer);
                        intent.putExtra("email", email);
                        startActivity(intent);
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
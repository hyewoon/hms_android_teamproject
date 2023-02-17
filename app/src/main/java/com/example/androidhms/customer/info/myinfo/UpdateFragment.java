package com.example.androidhms.customer.info.myinfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.androidhms.customer.vo.CustomerVO;
import com.example.androidhms.databinding.FragmentCustomerMyinfoUpdateBinding;

public class UpdateFragment extends Fragment {
    private FragmentCustomerMyinfoUpdateBinding bind;
    private CustomerVO customer;

    public UpdateFragment(CustomerVO customer) {
        this.customer = customer;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentCustomerMyinfoUpdateBinding.inflate(inflater, container, false);

        bind.tvName.setText(customer.getName());
        bind.tvGender.setText(customer.getGender());
        bind.tvEmail.setText(customer.getEmail());
        bind.etPhone.setHint(customer.getPhone_number());
        bind.tvDate.setText(customer.getJoin_date());
        bind.tvBloodtype.setText(customer.getBlood_type());
        bind.tvHeight.setText(customer.getHeight() + "cm");
        bind.etWeight.setHint(customer.getWeight() + "");
        try {bind.etAllergy.setHint(customer.getAllergy());}catch (Exception e){}
        try {bind.etUnderlying.setHint(customer.getUnderlying_disease());}catch (Exception e){}


        return bind.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind = null;
    }

}
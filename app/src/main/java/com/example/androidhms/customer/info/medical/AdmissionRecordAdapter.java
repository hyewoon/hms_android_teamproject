package com.example.androidhms.customer.info.medical;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidhms.R;
import com.example.androidhms.customer.vo.AdmissionRecordVO;
import com.example.androidhms.databinding.ItemCustomerAddmissionRecordBinding;

import java.util.ArrayList;

public class AdmissionRecordAdapter extends RecyclerView.Adapter<AdmissionRecordAdapter.ViewHolder> {
    LayoutInflater inflater;
    Context context;
    private ArrayList<AdmissionRecordVO> admission_record = new ArrayList<>();

    public AdmissionRecordAdapter(LayoutInflater inflater, Context context, ArrayList<AdmissionRecordVO> admission_record) {
        this.inflater = inflater;
        this.context = context;
        this.admission_record = admission_record;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_customer_addmission_record, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int i) {
        h.bind.tvAdmissionDate.setText(admission_record.get(i).getAdmission_date().substring(2, 10));
        h.bind.tvDischargeDate.setText(admission_record.get(i).getDischarge_date().substring(2, 10));
        h.bind.tvName.setText(admission_record.get(i).getName());
        h.bind.tvTreatmentName.setText(admission_record.get(i).getTreatment_name());
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return admission_record.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemCustomerAddmissionRecordBinding bind;

        public ViewHolder(@NonNull View v) {
            super(v);
            bind = ItemCustomerAddmissionRecordBinding.bind(v);
        }

    }

}

package com.example.androidhms.customer.info.medical;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidhms.R;
import com.example.androidhms.customer.info.DetailActivity;
import com.example.androidhms.customer.vo.MedicalRecordVO;
import com.example.androidhms.databinding.ItemCustomerMedicalRecordBinding;

import java.util.ArrayList;
import java.util.Date;

public class MedicalRecordAdapter extends RecyclerView.Adapter<MedicalRecordAdapter.ViewHolder> {
    LayoutInflater inflater;
    Context context;
    private ArrayList<MedicalRecordVO> medical_record = new ArrayList<>();
    private Date nowDate = new Date();
    private Intent intent;

    public MedicalRecordAdapter(LayoutInflater inflater, Context context, ArrayList<MedicalRecordVO> medical_record) {
        this.inflater = inflater;
        this.context = context;
        this.medical_record = medical_record;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_customer_medical_record, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int i) {

        h.bind.tvTreatmentDate.setText(medical_record.get(i).getTreatment_date().substring(2, 10));
        h.bind.tvDepartment.setText(medical_record.get(i).getDepartment_name());
        h.bind.tvName.setText(medical_record.get(i).getStaff_name());
        h.bind.tvTreatmentName.setText(medical_record.get(i).getTreatment_name());

        h.bind.llDetail.setOnClickListener(v -> {
            MedicalRecordVO vo = new MedicalRecordVO();
            vo.setMedical_record_id(medical_record.get(i).getMedical_record_id());
            vo.setStaff_id(medical_record.get(i).getStaff_id());
            vo.setPatient_id(medical_record.get(i).getPatient_id());
            vo.setPrescription_record_id(medical_record.get(i).getPrescription_record_id());
            vo.setTreatment_name(medical_record.get(i).getTreatment_name());
            vo.setTreatment_date(medical_record.get(i).getTreatment_date());
            vo.setAdmission(medical_record.get(i).getAdmission());
            vo.setMemo(medical_record.get(i).getMemo());
            vo.setStaff_name(medical_record.get(i).getStaff_name());
            vo.setDepartment_name(medical_record.get(i).getDepartment_name());
            vo.setPrescription_name(medical_record.get(i).getPrescription_name());
            vo.setPatient_name(medical_record.get(i).getPatient_name());

            intent = new Intent(context, DetailActivity.class);
            intent.putExtra("vo", vo);
            context.startActivity(intent);
        });
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
        return medical_record.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {

        public ItemCustomerMedicalRecordBinding bind;

        public ViewHolder(@NonNull View v) {
            super(v);
            bind = ItemCustomerMedicalRecordBinding.bind(v);
        }
    }
}

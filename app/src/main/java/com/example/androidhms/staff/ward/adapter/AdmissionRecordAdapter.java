package com.example.androidhms.staff.ward.adapter;

import android.content.res.ColorStateList;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidhms.R;
import com.example.androidhms.databinding.ItemStaffMypatientBinding;
import com.example.androidhms.staff.vo.AdmissionRecordVO;
import com.example.androidhms.staff.ward.MyPatientFragment;
import com.example.androidhms.util.Util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AdmissionRecordAdapter extends RecyclerView.Adapter<AdmissionRecordAdapter.AdmissionRecordViewHolder> {

    private final MyPatientFragment fragment;
    private final List<AdmissionRecordVO> arList;
    private int selectedPosition = -1;

    public AdmissionRecordAdapter(MyPatientFragment fragment, List<AdmissionRecordVO> arList) {
        this.fragment = fragment;
        this.arList = arList;
    }

    @NonNull
    @Override
    public AdmissionRecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdmissionRecordViewHolder(fragment.getLayoutInflater()
                .inflate(R.layout.item_staff_mypatient, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdmissionRecordViewHolder holder, int position) {
        AdmissionRecordVO vo = arList.get(position);
        holder.bind.tvName.setText(vo.getPatient_name());
        holder.bind.tvWard.setText(getWard(vo.getWard_id()));
        holder.bind.tvTreatmentName.setText(vo.getTreatment_name());
        holder.bind.tvStaffName.setText(vo.getStaff_name());
        holder.bind.tvAdmissionDate.setText(vo.getAdmission_date());
        if (vo.getDischarge_date() == null) holder.bind.tvDischargeDate.setText("미정");
        else holder.bind.tvDischargeDate.setText(vo.getDischarge_date());
        if (position == selectedPosition) {
            holder.bind.view.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(fragment.getContext(), R.color.second_color)));
            setColor(holder.bind, ContextCompat.getColor(fragment.getContext(), R.color.white));
        } else {
            holder.bind.view.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(fragment.getContext(), R.color.white)));
            setColor(holder.bind, ContextCompat.getColor(fragment.getContext(), R.color.text_color));
        }
    }

    @Override
    public int getItemCount() {
        return arList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private String getWard(int id) {
        int ward_number = ((id - 1) / 20) * 100 + 500 + ((id - 1) % 20) / 4 + 1;
        int bed = id % 4 == 0 ? 4 : id % 4;
        return ward_number + "호 " + bed + "번";
    }

    private void setColor(ItemStaffMypatientBinding bind, int color) {
        TextView[] tvArr = {bind.tvName, bind.tvWard, bind.tvTreatmentName, bind.tvStaffName, bind.tvAdmissionDate, bind.tvDischargeDate};
        for (TextView tv : tvArr) {
            tv.setTextColor(color);
        }
    }

    public class AdmissionRecordViewHolder extends RecyclerView.ViewHolder {

        private final ItemStaffMypatientBinding bind;

        public AdmissionRecordViewHolder(@NonNull View itemView) {
            super(itemView);
            bind = ItemStaffMypatientBinding.bind(itemView);
            itemView.setOnClickListener(v -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    if (selectedPosition == getAdapterPosition()) {
                        fragment.onAdmissionRecordClick(getAdapterPosition());
                        selectedPosition = -1;
                    } else {
                        fragment.onAdmissionRecordClick(getAdapterPosition());
                        selectedPosition = getAdapterPosition();
                    }
                    notifyDataSetChanged();
                }
            });
        }
    }
}

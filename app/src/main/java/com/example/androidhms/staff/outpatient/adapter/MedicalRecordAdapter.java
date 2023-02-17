package com.example.androidhms.staff.outpatient.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidhms.R;
import com.example.androidhms.databinding.ItemStaffMedicalRecordBinding;
import com.example.androidhms.staff.outpatient.MedicalRecordFragment;
import com.example.androidhms.staff.vo.MedicalRecordVO;

import java.util.List;

public class MedicalRecordAdapter extends RecyclerView.Adapter<MedicalRecordAdapter.MedicalRecordViewHolder> {

    private final MedicalRecordFragment fragment;
    private final List<MedicalRecordVO> mrList;
    private final Context context;
    private int selectedPosition = -1;

    public MedicalRecordAdapter(MedicalRecordFragment fragment, List<MedicalRecordVO> mrList) {
        this.fragment = fragment;
        this.mrList = mrList;
        context = fragment.getContext();
    }

    @NonNull
    @Override
    public MedicalRecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MedicalRecordViewHolder(fragment.getLayoutInflater()
                .inflate(R.layout.item_staff_medical_record, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MedicalRecordViewHolder holder, int position) {
        MedicalRecordVO vo = mrList.get(position);
        holder.bind.tvDate.setText(vo.getTreatment_date());
        holder.bind.tvPatientName.setText(vo.getPatient_name());
        holder.bind.tvStaffName.setText(vo.getStaff_name());
        holder.bind.tvTreatmentName.setText(vo.getTreatment_name());
        if (vo.getPrescription_record_id() != 0)
            holder.bind.ivPrescription.setImageResource(R.drawable.icon_search);
        else if (vo.getAdmission().equals("Y"))
            holder.bind.ivPrescription.setImageResource(R.drawable.icon_bed);
        if (vo.getMemo() != null && !vo.getMemo().equals("")) {
            holder.bind.imgvMemo.setVisibility(View.VISIBLE);
        }
        if (position == selectedPosition) {
            holder.bind.view.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.second_color)));
            setColor(holder.bind, ContextCompat.getColor(context, R.color.white));
            holder.bind.ivPrescription.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.white)));
            holder.bind.imgvMemo.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.white)));
        } else {
            holder.bind.view.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.white)));
            setColor(holder.bind, ContextCompat.getColor(context, R.color.text_color));
            holder.bind.ivPrescription.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.text_color)));
            holder.bind.imgvMemo.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.text_color)));
        }
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
        return mrList.size();
    }

    private void setColor(ItemStaffMedicalRecordBinding bind, int color) {
        TextView[] tvArr = {bind.tvPatientName, bind.tvStaffName, bind.tvTreatmentName, bind.tvDate};
        for (TextView tv : tvArr) {
            tv.setTextColor(color);
        }
    }

    public class MedicalRecordViewHolder extends RecyclerView.ViewHolder {

        private final ItemStaffMedicalRecordBinding bind;

        public MedicalRecordViewHolder(@NonNull View itemView) {
            super(itemView);
            bind = ItemStaffMedicalRecordBinding.bind(itemView);
            itemView.setOnClickListener(v -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    if (selectedPosition == getAdapterPosition()) {
                        fragment.onMedicalRecordClick(getAdapterPosition(), true);
                        selectedPosition = -1;
                    } else {
                        selectedPosition = getAdapterPosition();
                        fragment.onMedicalRecordClick(getAdapterPosition(), false);
                    }
                    notifyDataSetChanged();
                }
            });
        }
    }

}

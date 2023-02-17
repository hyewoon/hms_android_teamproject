package com.example.androidhms.staff.outpatient.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidhms.R;
import com.example.androidhms.databinding.ItemStaffMedicalReceiptBinding;
import com.example.androidhms.staff.outpatient.ReceiptFragment;
import com.example.androidhms.staff.vo.MedicalReceiptVO;
import com.example.androidhms.util.Util;

import java.sql.Timestamp;
import java.util.List;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.ReceiptViewHolder> {

    private final ReceiptFragment fragment;
    private final List<MedicalReceiptVO> mrList;

    public ReceiptAdapter(ReceiptFragment fragment, List<MedicalReceiptVO> mrList) {
        this.fragment = fragment;
        this.mrList = mrList;
    }

    @NonNull
    @Override
    public ReceiptViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReceiptViewHolder(fragment.getLayoutInflater()
                .inflate(R.layout.item_staff_medical_receipt, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiptViewHolder holder, int position) {
        MedicalReceiptVO vo = mrList.get(position);
        holder.bind.tvTime.setText(Util.dateFormat(Timestamp.valueOf(vo.getTime()), "HH:mm"));
        holder.bind.tvPatientName.setText(vo.getPatient_name());
        holder.bind.tvStaffName.setText(vo.getStaff_name());
        if (vo.getMemo() == null) holder.bind.tvMemo.setText("-");
        else holder.bind.tvMemo.setText(vo.getMemo());
    }

    @Override
    public int getItemCount() {
        return mrList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ReceiptViewHolder extends RecyclerView.ViewHolder {

        private final ItemStaffMedicalReceiptBinding bind;

        public ReceiptViewHolder(@NonNull View itemView) {
            super(itemView);
            bind = ItemStaffMedicalReceiptBinding.bind(itemView);
        }
    }
}

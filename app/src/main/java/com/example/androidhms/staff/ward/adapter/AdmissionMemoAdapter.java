package com.example.androidhms.staff.ward.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidhms.R;
import com.example.androidhms.databinding.ItemStaffAdmissionMemoBinding;
import com.example.androidhms.staff.vo.AdmissionMemoVO;
import com.example.androidhms.staff.ward.MyPatientFragment;
import com.example.androidhms.staff.ward.WardActivity;
import com.example.androidhms.staff.ward.WardFragment;
import com.example.androidhms.util.Util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AdmissionMemoAdapter extends RecyclerView.Adapter<AdmissionMemoAdapter.AdmissionMemoAdapterViewHolder> {

    private final Fragment fragment;
    private final List<AdmissionMemoVO> amList;

    public AdmissionMemoAdapter(Fragment fragment, List<AdmissionMemoVO> amList) {
        this.fragment = fragment;
        this.amList = amList;
    }

    @NonNull
    @Override
    public AdmissionMemoAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdmissionMemoAdapterViewHolder(fragment.getLayoutInflater()
                .inflate(R.layout.item_staff_admission_memo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdmissionMemoAdapterViewHolder holder, int position) {
        AdmissionMemoVO vo = amList.get(position);
        if (position != 0 &&
                Util.getDate(Timestamp.valueOf(vo.getWrite_date())).equals(Util.getDate(Timestamp.valueOf(amList.get(position -1).getWrite_date())))) {
            holder.bind.llDate.setVisibility(View.GONE);
        } else {
            holder.bind.tvDate.setText(Util.getDate(Timestamp.valueOf(vo.getWrite_date())));
        }
        holder.bind.tvTime.setText(Util.getTime(vo.getWrite_date()));
        holder.bind.tvStaffName.setText(vo.getName());
        holder.bind.tvMemo.setText(vo.getMemo());
        if (Util.staff.getStaff_id() != vo.getStaff_id()) {
            holder.bind.imgvDelete.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return amList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class AdmissionMemoAdapterViewHolder extends RecyclerView.ViewHolder {

        private final ItemStaffAdmissionMemoBinding bind;

        public AdmissionMemoAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            bind = ItemStaffAdmissionMemoBinding.bind(itemView);
            bind.imgvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        if (fragment instanceof WardFragment) {
                           ((WardFragment) fragment).deleteAdmissionMemo(amList.get(getAdapterPosition()).getAdmission_memo_id());
                        } else if (fragment instanceof MyPatientFragment) {
                            ((MyPatientFragment) fragment).deleteAdmissionMemo(amList.get(getAdapterPosition()).getAdmission_memo_id());
                        }
                    }
                }
            });
        }
    }
}

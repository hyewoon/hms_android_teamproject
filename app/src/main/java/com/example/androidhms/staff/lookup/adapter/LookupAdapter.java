package com.example.androidhms.staff.lookup.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidhms.R;
import com.example.androidhms.databinding.ItemStaffLookupBinding;
import com.example.androidhms.staff.lookup.LookupActivity;
import com.example.androidhms.staff.vo.PatientVO;
import com.example.androidhms.util.Util;

import java.util.List;

public class LookupAdapter extends RecyclerView.Adapter<LookupAdapter.LookupViewHolder> {

    private final List<PatientVO> patientList;
    private final LookupActivity activity;

    public LookupAdapter(List<PatientVO> patientList, LookupActivity activity) {
        this.patientList = patientList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public LookupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LookupViewHolder(activity.getLayoutInflater()
                .inflate(R.layout.item_staff_lookup, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LookupViewHolder holder, int position) {
        PatientVO vo = patientList.get(position);
        holder.bind.tvName.setText(vo.getName());
        String[] date = Util.getBirthDay(vo.getSocial_id()).split("-");
        StringBuilder info = new StringBuilder().append(date[0]).append("년 ")
                .append(date[1]).append("월 ").append(date[2]).append("일 (")
                .append(vo.getGender().equals("M") ? "남" : "여").append(")");
        holder.bind.tvInfo.setText(info);
        holder.itemView.setOnClickListener(v -> activity.selectPatient(vo));
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class LookupViewHolder extends RecyclerView.ViewHolder {

        private final ItemStaffLookupBinding bind;

        public LookupViewHolder(@NonNull View itemView) {
            super(itemView);
            bind = ItemStaffLookupBinding.bind(itemView);
        }

    }
}

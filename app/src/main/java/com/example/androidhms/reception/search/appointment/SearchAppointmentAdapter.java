package com.example.androidhms.reception.search.appointment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidhms.R;
import com.example.androidhms.databinding.ItemReceptionAppointmentBinding;
import com.example.androidhms.reception.vo.MedicalReceiptVO;
import com.example.androidhms.reception.vo.MedicalRecordVO;

import java.util.ArrayList;

public class SearchAppointmentAdapter extends RecyclerView.Adapter<SearchAppointmentAdapter.ViewHolder> {
    LayoutInflater inflater;
    MedicalReceiptVO vo;
    ArrayList<MedicalReceiptVO> appointmentList;

    public SearchAppointmentAdapter(LayoutInflater inflater, MedicalReceiptVO vo, ArrayList<MedicalReceiptVO> appointmentList) {
        this.inflater = inflater;
        this.vo = vo;
        this.appointmentList = appointmentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_reception_appointment,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int i) {
        h.bind.date.setText(appointmentList.get(i).getReserve_date());
        h.bind.time.setText(appointmentList.get(i).getReserve_time());
        h.bind.day.setText(appointmentList.get(i).getReserve_day());
        h.bind.departmentName.setText(appointmentList.get(i).getDepartment_name());
        h.bind.doctorName.setText(appointmentList.get(i).getDoctor_name());
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ItemReceptionAppointmentBinding bind;
        public ViewHolder(@NonNull View v) {

            super(v);
            bind=ItemReceptionAppointmentBinding.bind(v);
        }
    }
}

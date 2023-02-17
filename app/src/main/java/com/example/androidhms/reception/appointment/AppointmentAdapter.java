package com.example.androidhms.reception.appointment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidhms.R;
import com.example.androidhms.databinding.ItemReceptionAppointmentListBinding;
import com.example.androidhms.reception.vo.MedicalReceiptVO;

import java.util.ArrayList;


public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AViewHolder> {
    LayoutInflater inflater;
    ArrayList<MedicalReceiptVO> list;
    AppointmentActivity activity;
    int position= 0;

    public AppointmentAdapter(LayoutInflater inflater, ArrayList<MedicalReceiptVO> list, AppointmentActivity alistFragment) {
        this.inflater = inflater;
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_reception_appointment_list,parent,false);
        AViewHolder viewHolder = new AViewHolder(v);
        return viewHolder;
    }
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull AViewHolder h, int i) {
        h.bind.tvReserveTime.setText(list.get(i).getReserve_time());
        h.bind.tvReserveName.setText(list.get(i).getPatient_name());
        h.bind.tvDepartment.setText(list.get(i).getDepartment_name());
        h.bind.tvDoctorName.setText(list.get(i).getDoctor_name());
        String no =i+1+"";
        h.bind.tvNo.setText(no);

        String current_time =list.get(i).getCurrent_time();
        String reserve_time = list.get(i).getReserve_time_count();
        String time = list.get(i).getTime();
        String reserve_day = list.get(i).getReserve_today();


        if(current_time.compareTo(reserve_time) >= 0) {
       /*     h.bind.tvReserveTime.setTextColor(Color.parseColor("#123456"));*/
            h.bind.tvNo.setTextColor(R.color.gray);
            h.bind.tvReserveTime.setTextColor(R.color.back_color);
            h.bind.tvReserveTime.setTextColor(R.color.back_color);
            h.bind.tvReserveName.setTextColor(R.color.back_color);
            h.bind.tvDepartment.setTextColor(R.color.back_color);
            h.bind.tvDoctorName.setTextColor(R.color.back_color);

        }
        h.bind.llText.setOnClickListener(v -> {
            position = h.getAdapterPosition();

            Intent intent = new Intent(v.getContext(),AppointListActivity.class);
            intent.putExtra("list", list);
            intent.putExtra("position", position);
            v.getContext().startActivity(intent);

        });
    }
    @Override
    public int getItemCount() {
        //mainactivity와 연결되지 않아서
        return list.size();
    }
    public class AViewHolder extends RecyclerView.ViewHolder {
        //어댑터에서 바인딩하는 방법
        public ItemReceptionAppointmentListBinding bind;

        public AViewHolder(@NonNull View v) {
            super(v);
            bind = ItemReceptionAppointmentListBinding.bind(v);
        }
    }
}

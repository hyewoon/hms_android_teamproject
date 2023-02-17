package com.example.androidhms.reception.appointment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidhms.R;
import com.example.androidhms.databinding.ItemReceptionDoctorNameBinding;
import com.example.androidhms.reception.vo.StaffVO;
import com.example.conn.RetrofitMethod;

import java.util.ArrayList;

public class DoctorNameAdapter extends RecyclerView.Adapter<DoctorNameAdapter.DViewHolder> {
    LayoutInflater inflater;
    ArrayList<StaffVO> stafflist;
    StaffVO vo;
    String doctor_name;

    public DoctorNameAdapter(LayoutInflater inflater, ArrayList<StaffVO> stafflist, StaffVO vo) {
        this.inflater = inflater;
        this.stafflist = stafflist;
        this.vo = vo;
    }
    @NonNull
    @Override
    public DViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_reception_doctor_name,parent,false);
        DViewHolder viewHolder =new DViewHolder(v);
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

    @Override
    public void onBindViewHolder(@NonNull DViewHolder h, int i) {
        h.bind.doctorName.setText(stafflist.get(i).getName());

        h.bind.doctorName.setOnClickListener(v -> {
            Log.d("로그", "onBindViewHolder: " +"클릭");
            doctor_name= h.bind.doctorName.getText().toString();
        //    Log.d("로그", "onBindViewHolder: " +fragment.department_id );
            int a = h.getAdapterPosition();

        });

    }

    @Override
    public int getItemCount() {
        return stafflist.size();
    }

    public class DViewHolder  extends RecyclerView.ViewHolder {

        public ItemReceptionDoctorNameBinding bind;

        public DViewHolder(@NonNull View v) {
            super(v);

            bind=ItemReceptionDoctorNameBinding.bind(v);
        }
    }
}
